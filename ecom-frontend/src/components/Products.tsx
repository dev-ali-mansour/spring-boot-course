import { useEffect } from "react";
import { FaExclamationTriangle } from "react-icons/fa";
import ProductCard from "./ProductCard";
import { useDispatch, useSelector } from "react-redux";
import { fetchProducts } from "../store/actions";
import { RootState, AppDispatch } from "../store/reducers/store";
import { Product } from "../types";

export default function Products() {
    const { isLoading, errorMessage } = useSelector((state: RootState) => state.errors);
    const { products } = useSelector((state: RootState) => state.products);
    const dispatch = useDispatch<AppDispatch>();

    useEffect(() => {
        dispatch(fetchProducts() as any);
    }, [dispatch]);

    return (
        <div className="lg:px-14 sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
            {isLoading ? (
                <p>Loading...</p>
            ) : errorMessage ? (
                <div className="flex justify-center items-center h-50">
                    <FaExclamationTriangle className="text-slate-800 text-3xl mr-2"/>
                    <span className="text-slate-800 text-lg font-medium">
            {errorMessage}
          </span>
                </div>
            ) : (
                <div className="min-h-175">
                    <div
                        className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-2 gap-y-6 gap-x-6">
                        {products &&
                            products.map((product: Product, index: number) => (
                                <ProductCard key={index} {...product} />
                            ))}
                    </div>
                </div>
            )}
        </div>
    );
}
