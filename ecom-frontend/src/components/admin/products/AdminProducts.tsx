"use client";
import React, {useState} from "react";
import {getErrorMessage, useDashboardProducts, useDeleteProduct} from "@/hooks/useQueries";
import Loader from "@/components/shared/Loader";
import ErrorPage from "@/components/shared/ErrorPage";
import ProductsTable from "@/components/admin/products/ProductsTable";
import Modal from "@/components/shared/Modal";
import ProductViewModal from "@/components/shared/ProductViewModal";
import {Product} from "@/types";
import {MdAddShoppingCart} from "react-icons/md";
import AddProductForm from "@/components/admin/products/AddProductForm";
import DeleteModal from "@/components/shared/DeleteModel";
import {useDashboardProductFilter} from "@/hooks/useProductFilter";
import {useAuthStore} from "@/store";
import {FaBoxOpen} from "react-icons/fa";
import toast from "react-hot-toast";
import truncateText from "@/utils/truncateText";
import ImageUploadForm from "@/components/admin/products/ImageUploadForm";

const AdminProducts: React.FC = () => {
    const user = useAuthStore((state) => state.user);
    const isAdmin = !!((user && user?.roles?.includes("ROLE_ADMIN")));
    const queryString = useDashboardProductFilter();
    const {data: productsData, isLoading, error} = useDashboardProducts(queryString, isAdmin);
    const products = productsData?.content || [];
    const pagination = productsData;
    const deleteProductMutation = useDeleteProduct(isAdmin);

    const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [isUpdateModalOpen, setIsUpdateModalOpen] = useState(false);
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);
    const [isImageUploadModalOpen, setIsImageUploadModalOpen] = useState(false);
    const [isViewProductModalOpen, setIsViewProductModalOpen] = useState(false);

    const isEmptyProducts = !productsData || productsData?.content?.length === 0;

    if (isLoading) return <Loader/>;
    if (error) return <ErrorPage message={getErrorMessage(error)}/>;

    return (
        <div>
            <div className={"pt-6 pb-10 flex justify-end"}>
                <button
                    onClick={() => setIsAddModalOpen(true)}
                    className={"bg-custom-blue hover:bg-blue-800 text-white font-semibold py-2 px-4 flex items-center gap-2 rounded-md shadow-md transition-colors hover:text-slate-300 duration-300"}>
                    <MdAddShoppingCart className={"text-xl"}/>
                    Add Product
                </button>
            </div>

            {isEmptyProducts ? (
                <div className={"flex flex-col items-center justify-center text-gray-600 py-10"}>
                    <FaBoxOpen size={50} className={"mb-3"}/>
                    <h2 className={"text-2xl font-semibold"}>
                        No products created yet
                    </h2>
                </div>
            ) : (
                <ProductsTable
                    products={products}
                    pagination={pagination}
                    handleEdit={(product) => {
                        setSelectedProduct(product);
                        setIsUpdateModalOpen(true);
                    }}
                    handleDelete={(product) => {
                        setSelectedProduct(product);
                        setIsDeleteModalOpen(true);
                    }}
                    handleImageUpload={(product) => {
                        setSelectedProduct(product);
                        setIsImageUploadModalOpen(true);
                    }}
                    handleProductView={(product) => {
                        setSelectedProduct(product);
                        setIsViewProductModalOpen(true);
                    }}
                />
            )}

            <Modal
                open={isAddModalOpen || isUpdateModalOpen}
                setOpen={(open) => {
                    setIsAddModalOpen(open);
                    setIsUpdateModalOpen(open);
                }}
                title={isUpdateModalOpen ? "Update Product" : "Add Product"}>
                <AddProductForm
                    setIsOpen={isAddModalOpen ? setIsAddModalOpen : setIsUpdateModalOpen}
                    product={selectedProduct}
                    isForUpdate={isUpdateModalOpen}/>
            </Modal>

            <DeleteModal
                open={isDeleteModalOpen}
                setOpen={setIsDeleteModalOpen}
                title={`Delete Product ${selectedProduct ? truncateText(selectedProduct?.name, 50) : ""}`}
                onDeleteHandler={async () => {
                    try {
                        if (selectedProduct?.id) {
                            await deleteProductMutation.mutateAsync(selectedProduct.id);
                            toast.success("Product deleted successfully");
                            setIsDeleteModalOpen(false);
                        }
                    } catch (err: unknown) {
                        console.error(err);
                        toast.error(getErrorMessage(err));
                    }
                }}
                isLoading={deleteProductMutation.isPending}/>

            <Modal
                open={isImageUploadModalOpen}
                setOpen={setIsImageUploadModalOpen}
                title={"Add Product Image"}>
                <ImageUploadForm
                    product={selectedProduct}
                    setIsOpen={setIsImageUploadModalOpen}/>
            </Modal>

            <ProductViewModal
                isOpen={isViewProductModalOpen}
                setIsOpen={setIsViewProductModalOpen}
                product={selectedProduct!}
                isAvailable={!!(selectedProduct?.quantity && Number(selectedProduct.quantity) > 0)}/>
        </div>
    );
};

export default AdminProducts;
