import {Metadata} from "next";
import {Suspense} from "react";
import AdminProducts from "@/components/admin/products/AdminProducts";

export const metadata: Metadata = {
    title: "Admin Products",
    description: "Manage your products in the admin panel.",
};

export default function AdminProductsPage() {
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <AdminProducts/>
        </Suspense>
    );
}
