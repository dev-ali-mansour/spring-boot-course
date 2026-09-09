"use client";
import {Order} from "@/types/Order";
import React, {useState} from "react";
import {Pagination} from "@/types";
import {DataGrid, GridPaginationModel} from '@mui/x-data-grid';
import {adminOrderTableColumn} from "@/components/helper/tableColumn";
import {usePathname, useRouter, useSearchParams} from "next/navigation";

interface OrderTableProps {
    orders: Order[];
    pagination?: Pagination;
}

const OrderTable: React.FC<OrderTableProps> = ({orders, pagination}: OrderTableProps) => {
    const [selectedOrder, setSelectedOrder] = useState<Order | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);
    const router = useRouter();
    const searchParams = useSearchParams();
    const pathName = usePathname();

    const tableRecords = orders.map((order) => ({
        id: order.id,
        email: order.email,
        totalAmount: order.totalAmount,
        status: order.orderStatus,
        date: order.orderDate,
    }));

    const handlePaginationChange = (paginationModel: GridPaginationModel) => {
        const page = paginationModel.page + 1;
        const params = new URLSearchParams(searchParams.toString());
        params.set("page", page.toString());
        router.push(`${pathName}?${decodeURIComponent(params.toString())}`);
    };

    const handleEdit = (order: Order) => {
        setSelectedOrder(order);
        setIsModalOpen(true);
    };

    return (
        <div>
            <h1 className={"text-slate-800 text-3xl text-center font-bold pb-6 uppercase"}>
                All Orders
            </h1>

            <div>
                <DataGrid
                    className={"w-full"}
                    rows={tableRecords}
                    columns={adminOrderTableColumn(handleEdit)}
                    paginationMode={"server"}
                    rowCount={pagination?.totalElements ?? 0}
                    paginationModel={{
                        pageSize: pagination?.pageSize ?? 10,
                        page: pagination?.pageNumber ?? 0,
                    }}
                    onPaginationModelChange={handlePaginationChange}
                    disableRowSelectionOnClick
                    disableColumnResize
                    pageSizeOptions={[pagination?.pageSize || 10]}
                    pagination
                    slotProps={{
                        pagination: {
                            showFirstButton: true,
                            showLastButton: true,
                        }
                    }}
                />
            </div>

        </div>
    );
};

export default OrderTable;