import { useQuery } from "@tanstack/react-query";
import { api } from "../api/api";

export const getErrorMessage = (error: any) => {
    return error?.response?.data?.message ||
        error?.response?.data?.error ||
        (Array.isArray(error?.response?.data?.errors) ? error.response.data.errors.join(", ") : null) ||
        error?.message ||
        "An error occurred!";
};

export const useProducts = (queryString: string = "") => {
    return useQuery({
        queryKey: ["products", queryString],
        queryFn: async () => {
            const { data } = await api.get(`/public/products${queryString ? `?${queryString}` : ""}`);
            return data;
        }
    });
};

export const useCategories = () => {
    return useQuery({
        queryKey: ["categories"],
        queryFn: async () => {
            const { data } = await api.get("/public/categories");
            return data;
        }
    });
};
