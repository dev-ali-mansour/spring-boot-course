"use client";

import React, {useEffect, useState} from "react";
import {useAuthStore} from "@/store";
import {usePathname, useRouter} from "next/navigation";
import Loader from "@/components/shared/Loader";

interface AuthGuardProps {
    children: React.ReactNode;
    isPublicPage?: boolean;
    adminOnly?: boolean;
}

const AuthGuard = ({children, isPublicPage = false, adminOnly = false}: AuthGuardProps) => {
    const user = useAuthStore((state) => state.user);
    const router = useRouter();
    const pathname = usePathname();
    const [isMounted, setIsMounted] = useState(false);

    useEffect(() => {
        setIsMounted(true);
    }, []);

    useEffect(() => {
            if (!isMounted) return;

            if (isPublicPage && user) {
                router.replace("/");
            } else if (!isPublicPage && !user) {
                router.replace("/login");
            }

            if (adminOnly) {
                const isAdmin = user?.roles?.includes("ROLE_ADMIN");
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

        }, [isMounted, user, isPublicPage, adminOnly, pathname, router]
    )
    ;

    if (!isMounted) {
        return <Loader text="Loading..."/>;
    }

    if (isPublicPage && user) {
        return null;
    }

    if (!isPublicPage && !user) {
        return null;
    }

    if (adminOnly && user) {
        const isAdmin = user?.roles?.includes("ROLE_ADMIN");
        const isSeller = user?.roles?.includes("ROLE_SELLER");

        if (isSeller && !isAdmin) {
            const sellerAllowedPaths = ["/admin/orders", "/admin/products"];
            const sellerAllowed = sellerAllowedPaths.some(path =>
                pathname.startsWith(path));

            if (!sellerAllowed) {
                return null;
            }
        } else if (!isAdmin && !isSeller) {
            return null;
        }
    }

    return <>{children}</>;
}

export default AuthGuard;
