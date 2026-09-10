"use client";
import React from "react";
import useOrderFilter from "@/hooks/useOrderFilter";
import {getErrorMessage, useOrders} from "@/hooks/useQueries";
import {FaShoppingCart} from "react-icons/fa";
import OrdersTable from "@/components/admin/oreders/OrdersTable";
import Loader from "@/components/shared/Loader";
import ErrorPage from "@/components/shared/ErrorPage";

const Orders: React.FC = () => {
    const queryString = useOrderFilter();
    const {data: orderData, isLoading, error} = useOrders(queryString);
    const orders = orderData?.content || [];
    const pagination = orderData;

    if (isLoading) return <Loader/>;
    if (error) return <ErrorPage message={getErrorMessage(error)}/>;

    const isEmptyOrders = !orderData || orderData?.content?.length === 0;

    return (
        <div className={"pb-6 pt-20"}>
            {isEmptyOrders ? (
                <div className={"flex flex-col items-center justify-center text-gray-600 py-10"}>
                    <FaShoppingCart size={50} className={"mb-3"}/>
                    <h2 className={"text-2xl font-semibold"}>No Orders Placed Yet</h2>
                </div>
            ) : (
                <OrdersTable orders={orders} pagination={pagination}/>
            )}
        </div>
    );
};

export default Orders;
