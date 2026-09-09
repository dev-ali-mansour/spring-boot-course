import AdminLayout, {AdminLayoutProps} from "@/components/admin/AdminLayout";
import React from "react";
import AuthGuard from "@/components/shared/AuthGuard";

export default function AdminRouteLayout({children}: AdminLayoutProps) {
    return (
        <AuthGuard adminOnly>
            <AdminLayout>
                {children}
            </AdminLayout>
        </AuthGuard>
    );
}

