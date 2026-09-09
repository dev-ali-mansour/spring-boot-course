import {useMutation, UseMutationResult, useQuery, useQueryClient, UseQueryResult} from "@tanstack/react-query";
import {api} from "@/api/api";
import {Address, Cart, Category, Pagination, Product, User} from "@/types";
import {useCartStore} from "@/store";
import {AnalyticsResponse} from "@/types/AnalyticsResponse";

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

export interface CreateUserCartItem {
    productId?: number | string;
    quantity: number;
}

export interface StripePaymentParams {
    amount: number;
    currency: string;
    email: string;
    name: string;
    address: Address | null;
    description: string;
    metadata: Record<string, string>;
}

export interface StripeConfirmationParams {
    addressId?: number | string;
    pgName: string;
    pgPaymentId: string;
    pgStatus: string;
    pgResponseMessage: string;
}


export const getErrorMessage = (error: any) => {
    return error?.response?.data?.message ||
        error?.response?.data?.error ||
        (Array.isArray(error?.response?.data?.errors) ? error.response.data.errors.join(", ") : null) ||
        error?.message ||
        "An error occurred!";
};

export const useGetProducts = (queryString: string = ""): UseQueryResult<PaginatedResponse<Product>, Error> => {
    return useQuery<PaginatedResponse<Product>, Error>({
        queryKey: ["products", queryString],
        queryFn: async () => {
            const {data} = await api.get<PaginatedResponse<Product>>(`/public/products${queryString ? `?${queryString}` : ""}`);
            return data;
        }
    });
};

export const useGetCategories = (): UseQueryResult<PaginatedResponse<Category>, Error> => {
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
            const {data} = await api.get<Address[]>("/users/addresses");
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


export const useDeleteAddress = (): UseMutationResult<string, Error, number | string> => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async (addressId: number | string) => {
            const {data} = await api.delete(`/addresses/${addressId}`);
            return data;
        },
        onSuccess: () => {
            return queryClient.invalidateQueries({queryKey: ["userAddresses"]});
        },
    });
};

export const useCreateUserCart = (): UseMutationResult<Cart, Error, CreateUserCartItem[]> => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async (cartItems: CreateUserCartItem[]) => {
            await api.post("/carts/users/cart", cartItems);
            // Immediately fetch the created cart to get the cartId
            const {data} = await api.get<Cart>("/carts/users/cart");
            return data;
        },
        onSuccess: (data) => {
            // 1. Update React Query Cache
            queryClient.setQueryData(["userCart"], data);
            // 2. Event-driven sync to Zustand store
            useCartStore.getState().setCart(data.products || [], data.totalPrice || 0, data.cartId);
        },
    });
};

export const useCreateStripeClientSecret = (paymentData: StripePaymentParams | null): UseQueryResult<{
    clientSecret: string
}, Error> => {
    return useQuery({
        queryKey: ["stripeClientSecret", paymentData?.amount, paymentData?.address?.id],
        queryFn: async () => {
            if (!paymentData) return null;
            const {data} = await api.post("/orders/stripe-client-secret", paymentData);
            return typeof data === "string" ? {clientSecret: data} : data;
        },
        enabled: !!paymentData,
        staleTime: Infinity,
        gcTime: 0,
    });
};

export const useStripePaymentConfirmation = (): UseMutationResult<unknown, Error, StripeConfirmationParams> => {
    return useMutation({
        mutationFn: async (confirmationData: StripeConfirmationParams) => {
            const {data} = await api.post("/orders/users/payments/online", confirmationData);
            return data;
        },
    });
};

export const useGetAnalyticsData = (): UseQueryResult<AnalyticsResponse, Error> => {
    return useQuery<AnalyticsResponse, Error>({
        queryKey: ["analyticsData"],
        queryFn: async () => {
            const {data} = await api.get<AnalyticsResponse>("/admin/app/analytics");
            return data;
        }
    });
}