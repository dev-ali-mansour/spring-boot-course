import HeroBanner from "./HeroBanner.tsx";
import {Product} from "../../types/Product.ts";
import ProductCard from "../shared/ProductCard.tsx";
import ProductViewModal from "../shared/ProductViewModal.tsx";
import Loader from "../shared/Loader.tsx";
import {FaExclamationTriangle} from "react-icons/fa";
import {getErrorMessage, useProducts} from "../../hooks/useQueries.ts";
import {useProductModalStore} from "../../store";
import React from "react";

const Home: React.FC = () => {
    const {data, isLoading: isProductsLoading, error} = useProducts("");
    const {selectedProduct, isModalOpen, openModal, closeModal} = useProductModalStore();

    const products: Product[] | undefined = data?.content;
    const productsErrorMessage = error ? getErrorMessage(error) : null;

    return (
        <div className={"lg:px-14 sm:px-8 px-4"}>
            <div className={"py-6"}>
                <HeroBanner/>
            </div>

            <div className={"py-5"}>
                <div className={"flex flex-col justify-center items-center space-x-2"}>
                    <h1 className={"text-slate-800 text-4xl font-bold"}>Products</h1>
                    <span className={"text-slate-700"}>
                        Discover our handpicked selection of top-rated items just for you!
                    </span>
                </div>
            </div>

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
                <div
                    className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-2 gap-y-6 gap-x-6">
                    {products &&
                        products?.slice(0, 4).map((product: Product) => (
                            <ProductCard
                                key={product.id}
                                product={product}
                                onView={() => openModal(product)}
                            />
                        ))}
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
};

export default Home;
