"use client";

import React from "react";
import { FaShoppingCart, FaCheckCircle, FaTimesCircle } from "react-icons/fa";
import { formatPrice } from "@/utils/formatPrice";
import { useCartStore } from "@/store";
import toast from "react-hot-toast";
import {Product} from "@/types";

interface ProductDetailClientProps {
  product: Product;
}

const ProductDetailClient = ({ product }: ProductDetailClientProps) => {
  const addToCart = useCartStore((state) => state.addToCart);
  const isAvailable = product.quantity && Number(product.quantity) > 0;

  const handleAddToCart = () => {
    addToCart(product, 1, toast);
  };

  return (
    <div className="grid md:grid-cols-2 gap-12 bg-white p-8 rounded-2xl shadow-sm border border-slate-100">
      {/* Product Image */}
      <div className="flex justify-center items-center bg-gray-50 rounded-xl p-8 min-h-80">
        {product.image ? (
          <img
            src={product.image}
            alt={product.name}
            className="max-h-96 w-auto object-contain rounded-lg shadow-sm"
          />
        ) : (
          <div className="text-slate-400 font-medium">No Image Available</div>
        )}
      </div>

      {/* Product Details */}
      <div className="flex flex-col justify-between">
        <div>
          <h1 className="text-3xl font-bold text-slate-900 mb-4">
            {product.name}
          </h1>

          <div className="flex items-center gap-4 mb-6">
            <span className="text-3xl font-bold text-custom-blue">
              {formatPrice(Number(product.specialPrice || product.price))}
            </span>
            {product.specialPrice && (
              <span className="text-xl text-slate-400 line-through">
                {formatPrice(Number(product.price))}
              </span>
            )}
          </div>

          <div className="flex items-center gap-2 mb-6">
            {isAvailable ? (
              <span className="inline-flex items-center gap-1.5 text-emerald-600 bg-emerald-50 px-3 py-1 rounded-full text-sm font-semibold">
                <FaCheckCircle /> In Stock ({product.quantity} units)
              </span>
            ) : (
              <span className="inline-flex items-center gap-1.5 text-rose-600 bg-rose-50 px-3 py-1 rounded-full text-sm font-semibold">
                <FaTimesCircle /> Out of Stock
              </span>
            )}
          </div>

          <p className="text-slate-600 leading-relaxed mb-8">
            {product.description || "No description provided for this item."}
          </p>
        </div>

        <button
          disabled={!isAvailable}
          onClick={handleAddToCart}
          className={`w-full py-4 rounded-xl flex items-center justify-center gap-3 text-lg font-semibold text-white transition duration-200 shadow-md ${
            isAvailable
              ? "bg-button-gradient hover:opacity-90 cursor-pointer"
              : "bg-slate-300 cursor-not-allowed"
          }`}
        >
          <FaShoppingCart />
          {isAvailable ? "Add to Cart" : "Out of Stock"}
        </button>
      </div>
    </div>
  );
};

export default ProductDetailClient;
