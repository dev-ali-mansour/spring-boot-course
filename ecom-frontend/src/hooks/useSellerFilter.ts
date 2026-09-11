"use client";
import { useMemo } from "react";
import { useSearchParams } from "next/navigation";

export function useSellerFilter() {
    const searchParams = useSearchParams();

    return useMemo(() => {
        const params = new URLSearchParams();
        const currentPage = searchParams.get("page")
            ? Number(searchParams.get("page"))
            : 1;

        const page = (currentPage - 1).toString();
        params.set("page", page);

        return decodeURIComponent(params.toString());
    }, [searchParams]);
}

export default useSellerFilter;
