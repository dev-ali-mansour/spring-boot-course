import {useMutation, UseMutationResult, useQuery, useQueryClient, UseQueryResult} from "@tanstack/react-query";
import {api} from "../api/api";
import {Address, Category, Pagination, Product, User} from "../types";

export interface PaginatedResponse<T> extends Pagination {
    content: T[];
}

export interface LoginCredentials {
    username?: string;
    password?: string;
}

export interface RegistrationData {
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    password: string;
    role?: string[];
}

export interface AddressMutationParams {
    addressId?: number | string;
    addressData: Partial<Address>;
}

export const getErrorMessage = (error: any) => {
    return error?.response?.data?.message ||
        error?.response?.data?.error ||
        (Array.isArray(error?.response?.data?.errors) ? error.response.data.errors.join(", ") : null) ||
        error?.message ||
        "An error occurred!";
};

export const useProducts = (queryString: string = ""): UseQueryResult<PaginatedResponse<Product>, Error> => {
    return useQuery<PaginatedResponse<Product>, Error>({
        queryKey: ["products", queryString],
        queryFn: async () => {
            const {data} = await api.get<PaginatedResponse<Product>>(`/public/products${queryString ? `?${queryString}` : ""}`);
            return data;
        }
    });
};

export const useCategories = (): UseQueryResult<PaginatedResponse<Category>, Error> => {
    return useQuery<PaginatedResponse<Category>, Error>({
        queryKey: ["categories"],
        queryFn: async () => {
            const {data} = await api.get<PaginatedResponse<Category>>("/public/categories");
            return data;
        }
    });
};

export const useLogin = (): UseMutationResult<User, Error, LoginCredentials> => {
    return useMutation<User, Error, LoginCredentials>({
        mutationKey: ["login"],
        mutationFn: async (credentials: LoginCredentials) => {
            const {data} = await api.post<User>("/auth/signin", credentials);
            return data;
        }
    })
};

export const useRegister = (): UseMutationResult<{ message?: string }, Error, RegistrationData> => {
    return useMutation<{ message?: string }, Error, RegistrationData>({
        mutationKey: ["register"],
        mutationFn: async (registrationData: RegistrationData) => {
            const {data} = await api.post<{ message?: string }>("/auth/signup", registrationData);
            return data;
        }
    })
};

export const useLogout = (): UseMutationResult<unknown, Error, void> => {
    return useMutation<unknown, Error, void>({
        mutationKey: ["logout"],
        mutationFn: async () => {
            const {data} = await api.post("/auth/signout");
            return data;
        }
    })
};

export const useGetUserAddresses = (): UseQueryResult<Address[], Error> => {
    return useQuery<Address[], Error>({
        queryKey: ["userAddresses"],
        queryFn: async () => {
            const {data} = await api.get<Address[]>("/addresses");
            return data;
        }
    });
};

export const useAddUpdateAddress = (): UseMutationResult<Address, Error, AddressMutationParams> => {
    const queryClient = useQueryClient();
    return useMutation<Address, Error, AddressMutationParams>({
        mutationFn: async ({addressId, addressData}: AddressMutationParams) => {
            if (!addressId) {
                const {data} = await api.post<Address>("/addresses", addressData);
                return data;
            } else {
                const {data} = await api.put<Address>(`/addresses/${addressId}`, addressData);
                return data;
            }
        },
        onSuccess: () => {
            return queryClient.invalidateQueries({queryKey: ["userAddresses"]});
        },
    });
};
