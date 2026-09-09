"use client";
import React from "react";
import {getErrorMessage, useGetAnalyticsData} from "@/hooks/useQueries";
import Loader from "@/components/shared/Loader";
import ErrorPage from "@/components/shared/ErrorPage";
import DashboardOverview from "@/components/admin/dashboard/DashboardOverview";
import {FaBoxOpen, FaDollarSign, FaShoppingCart, FaUser} from "react-icons/fa";

const Dashboard: React.FC = () => {
    const {data: analytics, isLoading, error} = useGetAnalyticsData();
    const totalUsers = analytics?.totalUsers ?? 0;
    const totalProducts = analytics?.totalProducts ?? 0;
    const totalOrders = analytics?.totalOrders ?? 0;
    const totalRevenue = analytics?.totalRevenue ?? 0;

    if (isLoading) {
        return <Loader/>;
    }

    if (error) {
        return <ErrorPage message={getErrorMessage(error)}/>;
    }

    return (
        <div>
            <div className={`flex md:flex-row mt-8 flex-col lg:justify-between
                border border-slate-400 rounded-lg bg-linear-to-r
                from-blue-50 to-blue-100 shadow-lg`}>
                <DashboardOverview
                    title={"Total Users"}
                    value={totalUsers}
                    Icon={FaUser}/>

                <DashboardOverview
                    title={"Total Products"}
                    value={totalProducts}
                    Icon={FaBoxOpen}/>

                <DashboardOverview
                    title={"Total Orders"}
                    value={totalOrders}
                    Icon={FaShoppingCart}/>

                <DashboardOverview
                    title={"Total Revenue"}
                    value={totalRevenue}
                    Icon={FaDollarSign}
                    revenue/>
            </div>
        </div>
    );
}

export default Dashboard;