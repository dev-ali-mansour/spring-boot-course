"use client";

import {FaExclamationTriangle} from "react-icons/fa";
import ProductCard from "../shared/ProductCard";
import ProductViewModal from "../shared/ProductViewModal";
import {Product} from "../../types";
import Filter from "./Filter";
import useProductFilter from "../../hooks/useProductFilter";
import Loader from "../shared/Loader";
import PaginationComponent from "../shared/PaginationComponent";
import {useGetProducts, useGetCategories, getErrorMessage} from "../../hooks/useQueries";
import {useProductModalStore} from "../../store";

export default function Products() {
    const queryString = useProductFilter();
    const { data: productsData, isLoading: isProductsLoading, error: productsError } = useGetProducts(queryString);
    const { data: categoriesData } = useGetCategories();
    
    const { selectedProduct, isModalOpen, openModal, closeModal } = useProductModalStore();

    const products = productsData?.content;
    const pagination = productsData || {};
    const categories = categoriesData?.content || [];
    const productsErrorMessage = productsError ? getErrorMessage(productsError) : null;

    return (
        <div className="lg:px-14 sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
            <Filter categories={categories}/>
            {isProductsLoading ? (
                <Loader text={"Loading products..."}/>
            ) : productsErrorMessage ? (
                <div className="flex justify-center items-center h-50">
                    <FaExclamationTriangle className="text-slate-800 text-3xl mr-2"/>
                    <span className="text-slate-800 text-lg font-medium">
                        {productsErrorMessage}
                    </span>
                </div>
            ) : (
                <div className="min-h-175">
                    <div
                        className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-2 gap-y-6 gap-x-6">
                        {products &&
                            products.map((product: Product) => (
                                <ProductCard
                                    key={product.id}
                                    product={product}
                                    onView={() => openModal(product)}
                                />
                            ))}
                    </div>
                    <div className={"flex justify-center pt-10"}>
                        <PaginationComponent
                            pagination={pagination}
                        />
                    </div>
                </div>
            )}

            <ProductViewModal
                isOpen={isModalOpen}
                setIsOpen={(open) => !open && closeModal()}
                product={selectedProduct || ""}
                isAvailable={!!(selectedProduct?.quantity && Number(selectedProduct.quantity) > 0)}
            />
        </div>
    );
}
