"use client";
import React from "react";
import {Pagination, User} from "@/types";
import {usePathname, useRouter, useSearchParams} from "next/navigation";
import {DataGrid, GridPaginationModel} from "@mui/x-data-grid";
import {sellerTableColumns} from "@/components/helper/tableColumn";

interface SellersTableProps {
    sellers: User[];
    pagination?: Pagination;
}

const SellersTable: React.FC<SellersTableProps> = ({sellers, pagination}) => {
    const router = useRouter();
    const searchParams = useSearchParams();
    const pathName = usePathname();

    const tableRecords = sellers.map((seller) => ({
        id: seller.id,
        name: `${seller.firstName} ${seller.lastName}`,
        username: seller.username,
        email: seller.email,
    }));

    const handlePaginationChange = (paginationModal: GridPaginationModel) => {
        const page = paginationModal.page + 1;
        const params = new URLSearchParams(searchParams.toString());
        params.set("page", page.toString());
        router.push(`${pathName}?${decodeURIComponent(params.toString())}`);
    };

    return (
        <div>
            <h1 className={"text-slate-800 text-3xl text-center font-bold pb-6 uppercase"}>
                All Sellers
            </h1>

            <div className={"max-w-5xl mx-auto w-full"}>
                <DataGrid
                    className={"w-full"}
                    columns={sellerTableColumns}
                    rows={tableRecords}
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

export default SellersTable;