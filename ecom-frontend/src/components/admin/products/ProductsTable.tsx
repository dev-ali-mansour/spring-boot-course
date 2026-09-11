"use client";
import React from "react";
import {Pagination, Product} from "@/types";
import {DataGrid, GridPaginationModel} from '@mui/x-data-grid';
import {adminProductTableColumn} from "@/components/helper/tableColumn";
import {usePathname, useRouter, useSearchParams} from "next/navigation";

interface ProductsTableProps {
    products: Product[];
    pagination?: Pagination;
    handleEdit: (product: Product) => void;
    handleDelete: (product: Product) => void;
    handleImageUpload: (product: Product) => void;
    handleProductView: (product: Product) => void;
}

const ProductsTable: React.FC<ProductsTableProps> = (
    {products, pagination, handleEdit, handleDelete, handleImageUpload, handleProductView}: ProductsTableProps) => {
    const router = useRouter();
    const searchParams = useSearchParams();
    const pathName = usePathname();

    const tableRecords = products.map((product) => ({
        id: product.id,
        name: product.name,
        description: product.description,
        discount: product.discount,
        image: product.image,
        price: product.price,
        quantity: product.quantity,
        specialPrice: product.specialPrice,
    }));

    const handlePaginationChange = (paginationModel: GridPaginationModel) => {
        const page = paginationModel.page + 1;
        const params = new URLSearchParams(searchParams.toString());
        params.set("page", page.toString());
        router.push(`${pathName}?${decodeURIComponent(params.toString())}`);
    };

    return (
        <div>
            <h1 className={"text-slate-800 text-3xl text-center font-bold pb-6 uppercase"}>
                All Products
            </h1>

            <div className={"max-w-fit mx-auto"}>
                <DataGrid
                    className={"w-full"}
                    rows={tableRecords}
                    columns={adminProductTableColumn(handleEdit, handleDelete, handleImageUpload, handleProductView)}
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

export default ProductsTable;