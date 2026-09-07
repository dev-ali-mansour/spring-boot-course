import type { Metadata } from "next";
import { Suspense } from "react";
import Products from "../../components/products/Products";
import Loader from "../../components/shared/Loader";

export const metadata: Metadata = {
  title: "Products",
  description: "Browse our complete catalog of electronics, gadgets, and apparel with fast shipping.",
  openGraph: {
    title: "Browse Products | E-Shop",
    description: "Browse our complete catalog of electronics, gadgets, and apparel with fast shipping.",
    type: "website",
  },
};

export default function ProductsPage() {
  return (
    <Suspense fallback={<Loader text="Loading catalog..." />}>
      <Products />
    </Suspense>
  );
}
