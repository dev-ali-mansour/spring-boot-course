export interface Product {
    id: number | string;
    name: string;
    image: string;
    description: string;
    quantity: number;
    price: number | string;
    discount?: number;
    specialPrice?: number | string;
}
