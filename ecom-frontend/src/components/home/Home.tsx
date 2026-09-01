import HeroBanner from "./HeroBanner.tsx";
import {Product} from "../../types/Product.ts";
import ProductCard from "../shared/ProductCard.tsx";
import {useDispatch, useSelector} from "react-redux";
import {AppDispatch, RootState} from "../../store/reducers/store.ts";
import {useEffect, useState} from "react";
import {fetchProducts} from "../../store/actions";
import ProductViewModal from "../shared/ProductViewModal.tsx";
import Loader from "../shared/Loader.tsx";
import {FaExclamationTriangle} from "react-icons/fa";

export default function Home() {
    const dispatch = useDispatch<AppDispatch>();
    const {isProductsLoading, productsErrorMessage} = useSelector((state: RootState) => state.errors);
    const {products} = useSelector((state: any) => state.products);
    const [selectedProduct, setSelectedProduct] = useState<Product | null>(null);
    const [isModalOpen, setIsModalOpen] = useState(false);

    useEffect(() => {
        dispatch(fetchProducts() as any);
    }, [dispatch])

    const handleViewProduct = (product: Product) => {
        setSelectedProduct(product);
        setIsModalOpen(true);
    };

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
                                onView={() => handleViewProduct(product)}
                            />
                        ))}
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