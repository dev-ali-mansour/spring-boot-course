import {OrderItem} from "./OrderItem";

export interface Order {
    id: number;
    email: string;
    orderItems: OrderItem[];
    orderDate: string;
    payment?: {
        id: number | string;
        paymentMethod: string;
        pgPaymentId: string;
        pgStatus: string;
        pgResponseMessage: string;
        pgName: string;
    };
    totalAmount: number;
    orderStatus: string;
    addressId?: number | string;
}