import { FaExclamationTriangle } from "react-icons/fa";
import ProductCard from "./ProductCard";

const Products = () => {
  const isLoading = false;
  const errorMessage = "";
  const products = [
    {
      id: 652,
      name: "Iphone XS Max",
      image: "https://placehold.co/600x400",
      description:
        "Experience the latest in mobile technology with advanced cameras, powerful processing, and all-day battery.",
      quantity: 10,
      price: 1450.0,
      discount: 10.0,
      specialPrice: 1305.0,
    },
    {
      id: 654,
      name: "MacBook Air M2s",
      image: "https://placehold.co/600x400",
      description:
        "Ultra-thin laptop with Apple's M2 chip, providing fast performance in a lightweight, portable design.",
      quantity: 0,
      price: 2250.0,
      discount: 20.0,
      specialPrice: 2040.0,
    },
  ];
  return (
    <div className="lg:px-14 sm:px-8 px-4 py-14 2xl:w-[90%] 2xl:mx-auto">
      {isLoading ? (
        <p>Loading...</p>
      ) : errorMessage ? (
        <div className="flex justify-center items-center h-50">
          <FaExclamationTriangle className="text-slate-800 text-3xl mr-2" />
          <span className="text-slate-800 text-lg font-medium">
            {errorMessage}
          </span>
        </div>
      ) : (
        <div className="min-h-175">
          <div className="pb-6 pt-14 grid 2xl:grid-cols-4 lg:grid-cols-3 md:grid-cols-2 sm:grid-cols-2 gap-y-6 gap-x-6">
            {products &&
              products.map((product, index) => (
                <ProductCard key={index} {...product} />
              ))}
          </div>
        </div>
      )}
    </div>
  );
};
export default Products;
