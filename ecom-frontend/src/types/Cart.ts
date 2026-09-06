import {CartItem} from "./CartItem.ts";

export interface Cart {
    cartId: number | string;
    products: CartItem[];
    totalPrice: number;
};
