"use client";

import React, { useEffect, useState } from "react";
import { useAuthStore } from "../../store";
import { useRouter } from "next/navigation";
import Loader from "./Loader";

interface AuthGuardProps {
  children: React.ReactNode;
  isPublicPage?: boolean;
}

export default function AuthGuard({ children, isPublicPage = false }: AuthGuardProps) {
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
    return <Loader text="Loading..." />;
  }

  if (isPublicPage && user) {
    return null;
  }

  if (!isPublicPage && !user) {
    return null;
  }

  return <>{children}</>;
}
