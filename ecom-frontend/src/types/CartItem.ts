import {Product} from "./Product.ts";

export interface CartItem extends Product {
    cartItemId?: number | string;
    cartId?: number | string;
}
