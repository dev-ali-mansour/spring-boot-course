import {useSearchParams} from "next/navigation";
import {useMemo} from "react";

const useCategoryFilter = () => {
    const searchParams = useSearchParams();

    return useMemo(() => {
        const params = new URLSearchParams();
        const page = searchParams.get("page")
            ? (Number(searchParams.get("page")) - 1).toString()
            : "0";
        const sort = searchParams.get("sort") || "id,asc";

        params.set("page", page);
        params.set("sort", sort);

        return decodeURIComponent(params.toString());
    }, [searchParams]);
};

export default useCategoryFilter;