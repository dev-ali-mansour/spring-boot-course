import {FaShoppingCart} from "react-icons/fa";
import {Product} from "../../types/Product.ts";
import truncateText from "../../utils/truncateText.tsx";

interface ProductCardProps {
    product: Product;
    isCompact?: boolean;
    onView?: () => void;
}

export default function ProductCard({product, isCompact, onView}: ProductCardProps) {
    const btnLoader = false;
    const isAvailable = product.quantity && Number(product.quantity) > 0;

    return (
        <div className="border rounded-lg shadow-xl overflow-hidden transition-shadow duration-300">
            <div
                onClick={onView}
                className="w-full overflow-hidden aspect-3/2"
            >
                <img
                    className="w-full h-full cursor-pointer transition-transform duration-300 transform hover:scale-105"
                    src={product.image}
                    alt={product.name}
                ></img>
            </div>

            <div className="p-4">
                <h2
                    onClick={onView}
                    className="text-lg font-semibold mb-2 cursor-pointer"
                >
                    {truncateText(product.name, 50)}
                </h2>

                <div className="min-h-20 max-h-20">
                    <p className="text-gray-600 text-sm">{truncateText(product.description, 80)}</p>
                </div>

                {!isCompact &&
                    <div className="flex items-center justify-between">
                        {product.specialPrice ? (
                            <div className="flex flex-col">
                            <span className="text-gray-400 line-through">
                                ${Number(product.price).toFixed(2)}
                            </span>
                                <span className="text-xl font-bold text-slate-700">
                                ${Number(product.specialPrice).toFixed(2)}
                            </span>
                            </div>
                        ) : (
                            <span className="text-xl font-bold text-slate-700">
                            ${Number(product.price).toFixed(2)}
                        </span>
                        )}

                        <button
                            disabled={!isAvailable || btnLoader}
                            className={`bg-blue-500 ${isAvailable ? "opacity-100 hover:bg-blue-600 cursor-pointer"
                                : "opacity-70"} text-white py-2 px-3 rounded-lg items-center transition-colors duration-300 w-36 flex justify-center`}
                            onClick={() => {
                            }}
                        >
                            <FaShoppingCart className="mr-2"/>
                            {isAvailable ? "Add to Cart" : "Stock Out"}
                        </button>
                    </div>
                }
            </div>
        </div>
    );
}
