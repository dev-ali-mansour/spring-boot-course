"use client";

import React, {useEffect, useState} from "react";
import {useAuthStore} from "@/store";
import {usePathname, useRouter} from "next/navigation";
import Loader from "@/components/shared/Loader";

interface AuthGuardProps {
    children: React.ReactNode;
    requireGuest?: boolean;
    adminOnly?: boolean;
}

const AuthGuard = ({children, requireGuest = false, adminOnly = false}: AuthGuardProps) => {
    const user = useAuthStore((state) => state.user);
    const router = useRouter();
    const pathname = usePathname();
    const [isMounted, setIsMounted] = useState(false);

    useEffect(() => {
        setIsMounted(true);
    }, []);

    useEffect(() => {
            if (!isMounted) return;

            if (requireGuest && user) {
                router.replace("/");
            } else if (!requireGuest && !user) {
                router.replace("/login");
            }

            if (adminOnly) {
                const isAdmin = !!(user && user?.roles?.includes("ROLE_ADMIN"));
                const isSeller = user?.roles?.includes("ROLE_SELLER");

                if (isSeller && !isAdmin) {
                    const sellerAllowedPaths = ["/admin/orders", "/admin/products"];
                    const sellerAllowed = sellerAllowedPaths.some(path =>
                        pathname.startsWith(path));

                    if (!sellerAllowed) {
                        router.replace("/");
                    }
                } else if (!isAdmin && !isSeller) {
                    router.replace("/");
                }
            }

        }, [isMounted, user, requireGuest, adminOnly, pathname, router]
    )
    ;

    if (!isMounted) {
        return <Loader text="Loading..."/>;
    }

    if (requireGuest && user) {
        return <Loader text="Redirecting..."/>;
    }

    if (!requireGuest && !user) {
        return <Loader text="Redirecting..."/>;
    }

    if (adminOnly && user) {
        const isAdmin = !!(user && user?.roles?.includes("ROLE_ADMIN"));
        const isSeller = user?.roles?.includes("ROLE_SELLER");

        if (isSeller && !isAdmin) {
            const sellerAllowedPaths = ["/admin/orders", "/admin/products"];
            const sellerAllowed = sellerAllowedPaths.some(path =>
                pathname.startsWith(path));

            if (!sellerAllowed) {
                return <Loader text="Redirecting..."/>;
            }
        } else if (!isAdmin && !isSeller) {
            return <Loader text="Redirecting..."/>;
        }
    }

    return <>{children}</>;
}

export default AuthGuard;
