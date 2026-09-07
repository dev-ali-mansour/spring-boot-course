import type { Metadata } from "next";
import Link from "next/link";
import { notFound } from "next/navigation";
import { FaArrowLeft, FaShoppingCart, FaTag } from "react-icons/fa";
import { formatPrice } from "../../../utils/formatPrice";
import ProductDetailClient from "./ProductDetailClient";

interface Props {
  params: Promise<{ id: string }>;
}

async function getProduct(id: string) {
  const backendUrl = process.env.INTERNAL_BACK_END_URL || process.env.NEXT_PUBLIC_BACK_END_URL || "http://localhost:8080";
  try {
    const res = await fetch(`${backendUrl}/api/public/products/${id}`, {
      next: { revalidate: 60 },
    });
    if (res.ok) {
      return await res.json();
    }
  } catch {
    // Graceful fallback if backend is not yet populated
  }
  return null;
}

export async function generateMetadata({ params }: Props): Promise<Metadata> {
  const { id } = await params;
  const product = await getProduct(id);

  if (!product) {
    return {
      title: "Product Details",
      description: "View top-rated product details on E-Shop.",
    };
  }

  const productName = product.name || product.productName || "Product";
  const productDesc = product.description || "Discover high-quality items at E-Shop.";
  const productImage = product.image || "";
  const price = product.specialPrice || product.price;

  return {
    title: `${productName}`,
    description: productDesc,
    keywords: ["ecommerce", "shopping", productName],
    openGraph: {
      title: `${productName} - $${price} | E-Shop`,
      description: productDesc,
      images: productImage ? [{ url: productImage, alt: productName }] : [],
      type: "website",
    },
    twitter: {
      card: "summary_large_image",
      title: `${productName} | E-Shop`,
      description: productDesc,
      images: productImage ? [productImage] : [],
    },
  };
}

export default async function ProductDetailPage({ params }: Props) {
  const { id } = await params;
  const product = await getProduct(id);

  const productData = product || {
    id,
    name: "Product #" + id,
    description: "High performance, premium quality product.",
    price: 99.99,
    specialPrice: 79.99,
    quantity: 10,
    image: "",
  };

  const jsonLd = {
    "@context": "https://schema.org",
    "@type": "Product",
    name: productData.name || productData.productName,
    image: productData.image,
    description: productData.description,
    offers: {
      "@type": "Offer",
      priceCurrency: "USD",
      price: productData.specialPrice || productData.price,
      availability:
        Number(productData.quantity) > 0
          ? "https://schema.org/InStock"
          : "https://schema.org/OutOfStock",
    },
  };

  return (
    <div className="max-w-6xl mx-auto px-4 sm:px-6 lg:px-8 py-12">
      {/* Schema.org Structured Data */}
      <script
        type="application/ld+json"
        dangerouslySetInnerHTML={{ __html: JSON.stringify(jsonLd) }}
      />

      <Link
        href="/products"
        className="inline-flex items-center gap-2 text-slate-600 hover:text-slate-900 mb-8 font-medium transition"
      >
        <FaArrowLeft /> Back to Catalog
      </Link>

      <ProductDetailClient product={productData} />
    </div>
  );
}
