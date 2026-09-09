import { Product } from "./Product";

export interface OrderItem {
    id: number | string;
    product: Product;
    quantity: number;
    discount: number;
    orderedProductPrice: number;
}
