import React from "react";
import {Address, CartItem} from "../../types";
import {formatPriceCalculation} from "../../utils/formatPrice";


export interface OrderSummaryProps {
    totalPrice: number;
    cartItems: CartItem[];
    address: Address | null;
    paymentMethod: string | null;
}

const OrderSummary: React.FC<OrderSummaryProps> = ({totalPrice, cartItems, address, paymentMethod}) => {
    return (
        <div className={"container mx-auto px-4"}>
            <div className={"flex flex-wrap"}>
                <div className={"w-full lg:w-8/12 pr-4"}>
                    <div className={"space-y-4"}>
                        <div className={"p-4 border rounded-lg shadow-sm"}>
                            <h2 className={"text-2xl font-semibold mb-2"}>Billing Address</h2>
                            <p>
                                <strong>Building Name: </strong>
                                {address?.buildingName}
                            </p>
                            <p>
                                <strong>Street: </strong>
                                {address?.street}
                            </p>
                            <p>
                                <strong>City: </strong>
                                {address?.city}
                            </p>
                            <p>
                                <strong>State: </strong>
                                {address?.state}
                            </p>
                            <p>
                                <strong>Pin Code: </strong>
                                {address?.pinCode}
                            </p>
                            <p>
                                <strong>Country: </strong>
                                {address?.country}
                            </p>
                        </div>
                        <div className={"p-4 border rounded-lg shadow-sm"}>
                            <h2 className={"text-2xl font-semibold mb-2"}>Payment Method</h2>
                            <p>
                                <strong>Method: </strong>
                                {paymentMethod}
                            </p>
                        </div>

                        <div className={"p-4 border rounded-lg shadow-sm"}>
                            <h2 className={"text-2xl font-semibold mb-2"}>Order Items</h2>
                            <div className={"space-y-2"}>
                                {cartItems.map((item) => (
                                    <div key={item.id}
                                         className={"flex items-center"}>
                                        <img src={item?.image}
                                             alt={item?.name ? item.name : "Product Image"}
                                             className={"w-12 h-12 object-cover rounded mr-4"}/>
                                        <div className={"text-gray-500"}>
                                            <p>{item.name}</p>
                                            <p>{
                                                item.specialPrice
                                                    ? (`${item.quantity} x ${item.specialPrice} = ${formatPriceCalculation(item.quantity, Number(item.specialPrice))}`
                                                    ) : (
                                                        `${item.quantity} x ${item.price} = ${formatPriceCalculation(item.quantity, Number(item.price))}`
                                                    )
                                            }</p>
                                        </div>
                                    </div>
                                ))}
                            </div>
                        </div>
                    </div>
                </div>

                <div className="w-full lg:w-4/12 mt-4 lg:mt-0">
                    <div className="border rounded-lg shadow-xs p-4 space-y-4">
                        <h2 className="text-2xl font-semibold mb-2">Order Summary</h2>

                        <div className="space-y-2">
                            <div className="flex justify-between">
                                <span>Products</span>
                                <span>${formatPriceCalculation(1, totalPrice)}</span>
                            </div>
                            <div className="flex justify-between">
                                <span>Tax (0%)</span>
                                <span>$0.00</span>
                            </div>
                            <div className="flex justify-between font-semibold">
                                <span>SubTotal</span>
                                <span>${formatPriceCalculation(1, totalPrice)}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default OrderSummary;
