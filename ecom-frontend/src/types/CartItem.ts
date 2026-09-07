import {Product} from "./Product";

export interface CartItem extends Product {
    cartItemId?: number | string;
    cartId?: number | string;
    stock?: number;
}
