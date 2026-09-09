import {Metadata} from "next";
import {Suspense} from "react";
import Sellers from "@/components/admin/sellers/Sellers";

export const metadata: Metadata = {
    title: "Admin Sellers",
    description: "Manage and view all sellers registered on the e-commerce platform.",
};

export default function AdminSellersPage() {
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <Sellers/>
        </Suspense>
    );
}
