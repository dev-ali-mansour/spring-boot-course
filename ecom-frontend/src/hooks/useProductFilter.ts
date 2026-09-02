import {useSearchParams} from "react-router-dom";
import {useMemo} from "react";

export default function useProductFilter() {
    const [searchParams] = useSearchParams();

    return useMemo(() => {
        const params = new URLSearchParams();
        const page = searchParams.get("page")
            ? (Number(searchParams.get("page")) - 1).toString()
            : "0";
        const category = searchParams.get("category");
        const sort = searchParams.get("sort") || "price,asc";
        const keyword = searchParams.get("keyword");

        params.set("page", page);
        params.set("sort", sort);

        if (category) {
            params.set("category", category);
        }

        if (keyword) {
            params.set("keyword", keyword);
        }

        return decodeURIComponent(params.toString());
    }, [searchParams]);
}