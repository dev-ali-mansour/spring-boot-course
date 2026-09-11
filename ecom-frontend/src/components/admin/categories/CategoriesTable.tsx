"use client";
import React from "react";
import {Category, Pagination} from "@/types";
import {usePathname, useRouter, useSearchParams} from "next/navigation";
import {DataGrid, GridPaginationModel} from "@mui/x-data-grid";
import {categoryTableColumns} from "@/components/helper/tableColumn";

interface CategoriesTableProps {
    categories: Category[];
    pagination?: Pagination;
    handleEdit: (category: Category) => void;
    handleDelete: (category: Category) => void;
}

const CategoriesTable: React.FC<CategoriesTableProps> = ({categories, pagination, handleEdit, handleDelete}) => {
    const router = useRouter();
    const searchParams = useSearchParams();
    const pathName = usePathname();

    const tableRecords = categories.map((category) => ({
        id: category.id,
        name: category.name,
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
                All Categories
            </h1>

            <div className={"max-w-fit mx-auto"}>
                <DataGrid
                    className={"w-full"}
                    columns={categoryTableColumns(handleEdit, handleDelete)}
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

export default CategoriesTable;