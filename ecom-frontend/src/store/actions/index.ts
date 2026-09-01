import {Dispatch} from "redux";
import {api} from "../../api/api";

export const fetchProducts = (queryString: string = "") => async (dispatch: Dispatch) => {
    try {
        dispatch({type: "PRODUCTS_FETCHING"});
        const {data} = await api.get(`/public/products${queryString ? `?${queryString}` : ""}`);
        dispatch({
            type: "FETCH_PRODUCTS",
            payload: data.content,
            pageNumber: data.pageNumber,
            pageSize: data.pageSize,
            totalElements: data.totalElements,
            totalPages: data.totalPages,
            lastPage: data.lastPage,
        });
        dispatch({type: "PRODUCTS_SUCCESS"});
    } catch (error: any) {
        console.log(error);
        const backendMessage =
            error?.response?.data?.message ||
            error?.response?.data?.error ||
            (Array.isArray(error?.response?.data?.errors) ? error.response.data.errors.join(", ") : null) ||
            error?.message ||
            "Failed to fetch products!";
        dispatch({
            type: "PRODUCTS_ERROR",
            payload: backendMessage,
        });
    }
};

export const fetchCategories = () => async (dispatch: Dispatch) => {
    try {
        dispatch({type: "CATEGORIES_FETCHING"});
        const {data} = await api.get("/public/categories");
        dispatch({
            type: "FETCH_CATEGORIES",
            payload: data.content,
        });
        dispatch({type: "CATEGORIES_SUCCESS"});
    } catch (error: any) {
        console.log(error);
        const backendMessage =
            error?.response?.data?.message ||
            error?.response?.data?.error ||
            (Array.isArray(error?.response?.data?.errors) ? error.response.data.errors.join(", ") : null) ||
            error?.message ||
            "Failed to fetch categories!";
        dispatch({
            type: "CATEGORIES_ERROR",
            payload: backendMessage,
        });
    }
};
