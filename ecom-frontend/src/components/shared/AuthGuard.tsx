"use client";

import React, {useEffect, useState} from "react";
import {useAuthStore} from "@/store";
import {useRouter} from "next/navigation";
import Loader from "@/components/shared/Loader";

interface AuthGuardProps {
    children: React.ReactNode;
    isPublicPage?: boolean;
    adminOnly?: boolean;
}

const AuthGuard = ({children, isPublicPage = false, adminOnly = false}: AuthGuardProps) => {
    const user = useAuthStore((state) => state.user);
    const router = useRouter();
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
    }, [isMounted, user, isPublicPage, router]);

    if (!isMounted) {
        return <Loader text="Loading..."/>;
    }

    if (isPublicPage && user) {
        return null;
    }

    if (!isPublicPage && !user) {
        return null;
    }

    return <>{children}</>;
}

export default AuthGuard;
