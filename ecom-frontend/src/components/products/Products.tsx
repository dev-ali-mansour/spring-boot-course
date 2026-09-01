import {useEffect, useState} from "react";
import {FaExclamationTriangle} from "react-icons/fa";
import ProductCard from "../shared/ProductCard.tsx";
import ProductViewModal from "../shared/ProductViewModal.tsx";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, RootState} from "../../store/reducers/store.ts";
import {Product} from "../../types/Product.ts";
import Filter from "./Filter.tsx";
import useProductFilter from "../../hooks/useProductFilter.ts";
import {fetchCategories} from "../../store/actions";
import Loader from "../shared/Loader.tsx";
import PaginationComponent from "../shared/PaginationComponent.tsx";

export default function Products() {
    const {isProductsLoading, productsErrorMessage} = useSelector((state: RootState) => state.errors);
    const {products, categories, pagination} = useSelector((state: RootState) => state.products);
    const dispatch = useDispatch<AppDispatch>();

    const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);

    useProductFilter();

    useEffect(() => {
        dispatch(fetchCategories() as any);
    }, [dispatch]);

    const handleViewProduct = (product: Product) => {
        setSelectedProduct(product);
        setIsModalOpen(true);
    };

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
                                    onView={() => handleViewProduct(product)}
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
                setIsOpen={setIsModalOpen}
                product={selectedProduct || ""}
                isAvailable={!!(selectedProduct?.quantity && Number(selectedProduct.quantity) > 0)}
            />
        </div>
    );
}
