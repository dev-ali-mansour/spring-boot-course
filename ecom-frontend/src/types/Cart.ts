import {CartItem} from "./CartItem";

export interface Cart {
    cartId: number | string;
    products: CartItem[];
    totalPrice: number;
};
