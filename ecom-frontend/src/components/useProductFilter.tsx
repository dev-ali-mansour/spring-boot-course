import {useSearchParams} from "react-router-dom";
import {useDispatch} from "react-redux";
import {useEffect} from "react";
import {fetchProducts} from "../store/actions";
import {AppDispatch} from "../store/reducers/store";

export default function useProductFilter() {
    const [searchParams] = useSearchParams();
    const dispatch = useDispatch<AppDispatch>();

    useEffect(() => {
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

        const queryString = decodeURIComponent(params.toString());
        console.log("Query String:", queryString);

        dispatch(fetchProducts(queryString) as any);
    }, [dispatch, searchParams]);
}