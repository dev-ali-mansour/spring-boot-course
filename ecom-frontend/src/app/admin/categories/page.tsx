import {Metadata} from "next";
import {Suspense} from "react";
import Categories from "@/components/admin/categories/Categories";

export const metadata: Metadata = {
    title: "Admin Categories",
    description: "Manage your product categories in the Admin Panel.",
};

export default function AdminCategoriesPage() {
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <Categories/>
        </Suspense>
    );
}
