import {Metadata} from "next";
import {Suspense} from "react";
import Orders from "@/components/admin/oreders/Orders";

export const metadata: Metadata = {
    title: "Admin Orders",
    description: "Manage and view all orders placed on the e-commerce platform.",
};

export default function AdminOrdersPage() {
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <Orders/>
        </Suspense>
    );
}
