import {CartItem} from "../types";
import toast from "react-hot-toast";
import {create} from "zustand";
import {CART_STORAGE_KEY} from "../utils/constant.ts";
import {devtools} from "zustand/middleware";

export interface CartState {
    cart: CartItem[],
    totalPrice: number,
    cartId: number | string | null,
    addToCart: (cartItem: CartItem, quantity?: number, customToast?: typeof toast) => void,
    increaseCartQuantity: (cartItem: CartItem, customToast?: typeof toast, currentQuantity?: number, setCurrentQuantity?: (q: number) => void) => void,
    decreaseCartQuantity: (cartItem: CartItem, newQuantity: number) => void,
    removeFromCart: (cartItem: CartItem, customToast?: typeof toast) => void,
    setCart: (cart: CartItem[], totalPrice: number, cartId: number | string | null) => void,
    clearCart: () => void
}

const getInitialCart = (): CartItem[] => {
    try {
        const item = localStorage.getItem(CART_STORAGE_KEY);
        return item ? JSON.parse(item) : [];
    } catch {
        return [];
    }
};

const calculateTotalPrice = (items: CartItem[]): number => {
    return items.reduce((acc, item) => {
        const price = item.specialPrice ? Number(item.specialPrice) : Number(item.price);
        return acc + price * Number(item.quantity);
    }, 0);
};

export const useCartStore = create<CartState>()(
    devtools(
        (set, get) => ({
            cart: getInitialCart(),
            totalPrice: calculateTotalPrice(getInitialCart()),
            cartId: null,
            addToCart: (cartItem, quantity = 1, customToast) => {
                const activeToast = customToast || toast;
                const currentCart = get().cart;
                const existingItem = currentCart.find((item) => (item.id) === cartItem.id);
                const existingQuantity = existingItem ? existingItem.quantity : 0;
                const totalRequestedQuantity = existingQuantity + quantity;
                const availableQuantity = cartItem.quantity ? Number(cartItem.quantity) : 0;

                if (availableQuantity < totalRequestedQuantity) {
                    activeToast.error("Out of stock");
                    return;
                }

                let updatedCart: CartItem[];
                if (existingItem) {
                    updatedCart = currentCart.map((item) => {
                        if (item.id === cartItem.id) {
                            return {...item, quantity: totalRequestedQuantity}
                        }
                        return item;
                    })
                } else {
                    const newItem: CartItem = {
                        ...cartItem,
                        stock: cartItem.quantity ? Number(cartItem.quantity) : 0,
                        quantity: quantity
                    };
                    updatedCart = [...currentCart, newItem];
                }

                const newTotalPrice = calculateTotalPrice(updatedCart);
                localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(updatedCart));
                activeToast.success("Added to the cart");
                set({cart: updatedCart, totalPrice: newTotalPrice}, false, "addToCart");
            },
            increaseCartQuantity: (cartItem, customToast, currentQuantity, setCurrentQuantity) => {
                const activeToast = customToast || toast;
                const currentCart = get().cart;
                const existingItem = currentCart.find((item) => item.id == cartItem.id);
                const currentQty = currentQuantity !== undefined
                    ? currentQuantity : existingItem ? Number(existingItem.quantity) : 1;

                const availableQuantity = cartItem.stock !== undefined
                    ? Number(cartItem.stock)
                    : (cartItem.quantity ? Number(cartItem.quantity) : 0);

                if (availableQuantity < currentQty + 1) {
                    activeToast.error("Quantity reached to limit");
                    return;
                }
                const newQuantity = currentQty + 1;
                if (setCurrentQuantity) {
                    setCurrentQuantity(newQuantity);
                }

                const updatedCart = currentCart.map((item) => {
                    if (item.id == cartItem.id) {
                        return {...item, quantity: newQuantity};
                    }
                    return item;
                });
                const newTotalPrice = calculateTotalPrice(updatedCart);
                localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(updatedCart));
                set({cart: updatedCart, totalPrice: newTotalPrice}, false, "increaseCartQuantity");
            },
            decreaseCartQuantity: (cartItem, newQuantity) => {
                const currentCart = get().cart;

                const updatedCart = currentCart.map((item) => {
                    if (item.id === cartItem.id) {
                        return {...item, quantity: newQuantity};
                    }
                    return item;
                });

                const newTotalPrice = calculateTotalPrice(updatedCart);
                localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(updatedCart));
                set({cart: updatedCart, totalPrice: newTotalPrice}, false, "decreaseCartQuantity");
            },
            removeFromCart: (cartItem, customToast) => {
                const activeToast = customToast || toast;
                const currentCart = get().cart;

                const updatedCart = currentCart.filter((item) => item.id !== cartItem.id);

                const newTotalPrice = calculateTotalPrice(updatedCart);
                localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(updatedCart));
                activeToast.success(`${cartItem.name} Removed from the cart`);
                set({cart: updatedCart, totalPrice: newTotalPrice}, false, "removeFromCart");
            },
            setCart: (cart, totalPrice, cartId) => {
                localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(cart));
                set({cart: cart, totalPrice: totalPrice, cartId: cartId}, false, "setCart");
            },
            clearCart: () => {
                localStorage.removeItem(CART_STORAGE_KEY);
                set({cart: [], totalPrice: 0, cartId: null}, false, "clearCart");
            }
        }),
        {name: "CartStore"}
    ),
);