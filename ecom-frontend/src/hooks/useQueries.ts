import {useMutation, UseMutationResult, useQuery, useQueryClient, UseQueryResult} from "@tanstack/react-query";
import {api} from "@/api/api";
import {Address, Cart, Category, Pagination, Product, User} from "@/types";
import {useCartStore} from "@/store";
import {AnalyticsResponse} from "@/types/AnalyticsResponse";
import {Order} from "@/types/Order";

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
    roles?: string[];
}

export interface AddressMutationParams {
    addressId?: number;
    addressData: Partial<Address>;
}

export interface CreateUserCartItem {
    productId?: number;
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

export interface OrderStatusUpdateParams {
    orderId: number;
    status: string;
}

export interface CreateProductParams {
    categoryId: number;
    productData: Partial<Product> | FormData | Record<string, unknown>;
}

export interface UpdateProductParams {
    id?: number;
    productData: Partial<Product> | Record<string, unknown>;
}

export interface UpdateProductImageParams {
    productId: number;
    formData: FormData;
}

export interface CreateCategoryParams {
    name?: string;
}

export interface UpdateCategoryParams {
    id: number;
    categoryData: Partial<Category> | Record<string, unknown>;
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
            const response = await api.get<PaginatedResponse<Product>>(`/public/products${queryString ? `?${queryString}` : ""}`);
            return response.data;
        }
    });
};

export const useCategories = (queryString: string = ""): UseQueryResult<PaginatedResponse<Category>, Error> => {
    return useQuery<PaginatedResponse<Category>, Error>({
        queryKey: ["categories", queryString],
        queryFn: async () => {
            const response = await api.get<PaginatedResponse<Category>>(`/public/categories${queryString ? `?${queryString}` : ""}`);
            return response.data;
        }
    });
};

export const useLogin = (): UseMutationResult<User, Error, LoginCredentials> => {
    return useMutation<User, Error, LoginCredentials>({
        mutationKey: ["login"],
        mutationFn: async (credentials: LoginCredentials) => {
            const response = await api.post<User>("/auth/login", credentials);
            return response.data;
        }
    })
};

export const useRegister = (): UseMutationResult<{ message?: string }, Error, RegistrationData> => {
    return useMutation<{ message?: string }, Error, RegistrationData>({
        mutationKey: ["register"],
        mutationFn: async (registrationData: RegistrationData) => {
            const response = await api.post<{ message?: string }>("/auth/register", registrationData);
            return response.data;
        }
    })
};

export const useLogout = (): UseMutationResult<unknown, Error, void> => {
    const queryClient = useQueryClient();
    return useMutation<unknown, Error, void>({
        mutationKey: ["logout"],
        mutationFn: async () => {
            const response = await api.post("/auth/logout");
            return response.data;
        },
        onSuccess: () => {
            queryClient.clear();
        }
    })
};

export const useGetUserAddresses = (): UseQueryResult<Address[], Error> => {
    return useQuery<Address[], Error>({
        queryKey: ["userAddresses"],
        queryFn: async () => {
            const response = await api.get<Address[]>("/users/addresses");
            return response.data;
        }
    });
};

export const useAddUpdateAddress = (): UseMutationResult<Address, Error, AddressMutationParams> => {
    const queryClient = useQueryClient();
    return useMutation<Address, Error, AddressMutationParams>({
        mutationKey: ["addUpdateAddress"],
        mutationFn: async ({addressId, addressData}: AddressMutationParams) => {
            if (!addressId) {
                const response = await api.post<Address>("/addresses", addressData);
                return response.data;
            } else {
                const response = await api.put<Address>(`/addresses/${addressId}`, addressData);
                return response.data;
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
            const response = await api.delete(`/addresses/${addressId}`);
            return response.data;
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
            const response = await api.get<Cart>("/carts/users/cart");
            return response.data;
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
            const response = await api.post("/orders/stripe-client-secret", paymentData);
            return typeof response.data === "string" ? {clientSecret: response.data} : response.data;
        },
        enabled: !!paymentData,
        staleTime: Infinity,
        gcTime: 0,
    });
};

export const useStripePaymentConfirmation = (): UseMutationResult<unknown, Error, StripeConfirmationParams> => {
    return useMutation({
        mutationFn: async (confirmationData: StripeConfirmationParams) => {
            const response = await api.post("/orders/users/payments/online", confirmationData);
            return response.data;
        },
    });
};

export const useGetAnalyticsData = (): UseQueryResult<AnalyticsResponse, Error> => {
    return useQuery<AnalyticsResponse, Error>({
        queryKey: ["analyticsData"],
        queryFn: async () => {
            const response = await api.get<AnalyticsResponse>("/admin/app/analytics");
            return response.data;
        }
    });
}

export const useOrders = (queryString: string = "", isAdmin: boolean = true): UseQueryResult<PaginatedResponse<Order>, Error> => {
    return useQuery<PaginatedResponse<Order>, Error>({
        queryKey: ["orders", queryString, isAdmin],
        queryFn: async () => {
            const endpoint = isAdmin ? "/admin/orders" : "/seller/orders";
            const response = await api.get<PaginatedResponse<Order>>(`${endpoint}${queryString ? `?${queryString}` : ""}`);
            return response.data;
        }
    });
};

export const useUpdateOrderStatus = (isAdmin: Boolean = true): UseMutationResult<Order, Error, OrderStatusUpdateParams> => {
    const queryClient = useQueryClient();
    return useMutation<Order, Error, OrderStatusUpdateParams>({
            mutationFn: async (params: OrderStatusUpdateParams) => {
                console.log("Updating order status for orderId:", params.orderId, "to status:", params.status);
                const endpoint = isAdmin ? `/admin/orders/${params.orderId}/status` : `/seller/orders/${params.orderId}/status`;
                const response = await api.put<Order>(endpoint, params);
                return response.data;
            },
            onSuccess: () => {
                return queryClient.invalidateQueries({queryKey: ["orders"]})
            }
        }
    );
};

export const useDashboardProducts = (queryString: string = "", isAdmin: boolean = true): UseQueryResult<PaginatedResponse<Product>, Error> => {
    return useQuery({
        queryKey: ["dashboardProducts", queryString, isAdmin],
        queryFn: async () => {
            const endpoint = isAdmin ? "/admin/products" : "/seller/products";
            const {data} = await api.get(`${endpoint}${queryString ? `?${queryString}` : ""}`);
            return data;
        },
    });
};

export const useCreateProduct = (isAdmin: boolean = true): UseMutationResult<Product, Error, CreateProductParams> => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async (params: CreateProductParams) => {
            const endpoint = isAdmin ? `/admin/categories/${params.categoryId}/product` : `/seller/categories/${params.categoryId}/product`;
            const response = await api.post(endpoint, params.productData);
            return response.data;
        },
        onSuccess: () => {
            return queryClient.invalidateQueries({queryKey: ["dashboardProducts"]})
                .then(() => queryClient.invalidateQueries({queryKey: ["products"]}));
        },
    });
};

export const useUpdateProduct = (isAdmin: boolean = true): UseMutationResult<Product, Error, UpdateProductParams> => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async (params: UpdateProductParams) => {
            const endpoint = isAdmin ? `/admin/products/${params.id}` : `/seller/products/${params.id}`;
            const response = await api.put(endpoint, params.productData);
            return response.data;
        },
        onSuccess: () => {
            queryClient.invalidateQueries({queryKey: ["dashboardProducts"]})
                .then(() => queryClient.invalidateQueries({queryKey: ["products"]}));
        },
    });
};

export const useDeleteProduct = (isAdmin: boolean = true): UseMutationResult<string, Error, number | string> => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async (productId: number | string) => {
            const endpoint = isAdmin ? `/admin/products/${productId}` : `/seller/products/${productId}`;
            const response = await api.delete(endpoint);
            return response.data;
        },
        onSuccess: () => {
            return queryClient.invalidateQueries({queryKey: ["dashboardProducts"]})
                .then(() => queryClient.invalidateQueries({queryKey: ["products"]}));
        },
    });
};

export const useUpdateProductImage = (isAdmin: boolean = true): UseMutationResult<Product, Error, UpdateProductImageParams> => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async (params: UpdateProductImageParams) => {
            const endpoint = isAdmin ? `/admin/products/${params.productId}/image` : `/seller/products/${params.productId}/image`;
            const response = await api.put(endpoint, params.formData);
            return response.data;
        },
        onSuccess: () => {
            return queryClient.invalidateQueries({queryKey: ["dashboardProducts"]})
                .then(() => queryClient.invalidateQueries({queryKey: ["products"]}));
        },
    });
};

export const useCreateCategory = (): UseMutationResult<Category, Error, CreateCategoryParams> => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async (categoryData: CreateCategoryParams) => {
            const response = await api.post("/admin/categories", categoryData);
            return response.data;
        },
        onSuccess: () => {
            return queryClient.invalidateQueries({queryKey: ["categories"]});
        },
    });
};

export const useUpdateCategory = (): UseMutationResult<Category, Error, UpdateCategoryParams> => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async (params: UpdateCategoryParams) => {
            const response = await api.put(`/admin/categories/${params.id}`, params.categoryData);
            return response.data;
        },
        onSuccess: () => {
            return queryClient.invalidateQueries({queryKey: ["categories"]});
        },
    });
};

export const useDeleteCategory = (): UseMutationResult<string, Error, number> => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async (id: number) => {
            const response = await api.delete(`/admin/categories/${id}`);
            return response.data;
        },
        onSuccess: () => {
            return queryClient.invalidateQueries({queryKey: ["categories"]});
        },
    });
};

export const useSellers = (queryString: string = ""): UseQueryResult<PaginatedResponse<User>, Error> => {
    return useQuery({
        queryKey: ["sellers", queryString],
        queryFn: async () => {
            const {data} = await api.get(`/auth/sellers${queryString ? `?${queryString}` : ""}`);
            return data;
        },
    });
};


export const useCreateSeller = (): UseMutationResult<{ message?: string }, Error, RegistrationData> => {
    const queryClient = useQueryClient();
    return useMutation({
        mutationFn: async (sellerData: RegistrationData) => {
            const {data} = await api.post("/auth/register", sellerData);
            return data;
        },
        onSuccess: () => {
            return queryClient.invalidateQueries({queryKey: ["sellers"]});
        },
    });
};
