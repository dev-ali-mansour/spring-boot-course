import React from "react";
import {formatRevenue} from "@/utils/formatPrice";

export interface DashboardOverviewProps {
    title: string;
    value: number;
    Icon: React.ElementType;
    revenue?: boolean;
}

const DashboardOverview: React.FC<DashboardOverviewProps> = ({title, value, Icon, revenue}) => {
    const displayAmount = revenue ? formatRevenue(value) : value;

    return (
        <>
            <div className={"xl:w-80 w-full space-y-4 text-center px-5 py-8"}>
                <div className={"flex justify-center items-center gap-2"}>
                    <h3 className={"uppercase text-2xl text-slate-700 font-semibold"}>{title}</h3>
                    <Icon className='text-slate-800 text-2xl'/>
                </div>

                <h1 className={"font-bold text-slate-800 text-3xl text-center"}>
                    {revenue ? "$" : null}
                    {displayAmount}
                </h1>
            </div>
        </>
    );
};

export default DashboardOverview;