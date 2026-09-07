import {Product} from "@/types/Product";

export interface CartItem extends Product {
    cartItemId?: number | string;
    cartId?: number | string;
    stock?: number;
}
