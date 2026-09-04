import {useMutation, useQuery} from "@tanstack/react-query";
import {api} from "../api/api";


export interface LoginCredentials {
    username?: string;
    password?: string;
}

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
            const {data} = await api.get(`/public/products${queryString ? `?${queryString}` : ""}`);
            return data;
        }
    });
};

export const useCategories = () => {
    return useQuery({
        queryKey: ["categories"],
        queryFn: async () => {
            const {data} = await api.get("/public/categories");
            return data;
        }
    });
};

export const useLogin = () => {
    return useMutation({
        mutationFn: async (credentials: LoginCredentials) => {
            const {data} = await api.post("/auth/signin", credentials);
            return data;
        }
    })
};

export const useLogout = () => {
    return useMutation({
        mutationFn: async () => {
            const {data} = await api.post("/auth/signout");
            return data;
        }
    })
};
