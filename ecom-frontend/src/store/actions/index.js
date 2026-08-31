import {api} from "../../api/api.js";

export const fetchProducts = () => async (dispatch) => {
    try {
        dispatch({type: "IS_FETCHING"});
        const {data} = await api.get(`/public/products`);
        dispatch({
            type: "FETCH_PRODUCTS",
            payload: data.content,
            pageNumber: data.pageNumber,
            pageSize: data.pageSize,
            totalElements: data.totalElements,
            totalPages: data.totalPages,
            lastPage: data.lastPage,
        });
        dispatch({type: "IS_SUCCESS"});
    } catch (error) {
        console.log(error);
        const backendMessage =
            error?.response?.data?.message ||
            error?.response?.data?.error ||
            (Array.isArray(error?.response?.data?.errors) ? error.response.data.errors.join(", ") : null) ||
            error?.message ||
            "Failed to fetch products!";
        dispatch({
            type: "IS_ERROR",
            payload: backendMessage,
        });
    }
};
