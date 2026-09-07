import {CartItem} from "@/types/CartItem";

export interface Cart {
    cartId: number | string;
    products: CartItem[];
    totalPrice: number;
};
