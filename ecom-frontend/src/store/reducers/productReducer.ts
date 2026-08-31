import { Product, Pagination } from "../../types";

export interface ProductState {
    products: Product[] | null;
    categories: any[] | null; // Placeholder for categories type if needed
    pagination: Partial<Pagination>;
}

const initialState: ProductState = {
    products: null,
    categories: null,
    pagination: {},
};

export const productReducer = (state = initialState, action: any): ProductState => {
    switch (action.type) {
        case "FETCH_PRODUCTS":
            return {
                ...state,
                products: action.payload,
                pagination: {
                    ...state.pagination,
                    pageNumber: action.pageNumber,
                    pageSize: action.pageSize,
                    totalElements: action.totalElements,
                    totalPages: action.totalPages,
                    lastPage: action.lastPage,
                },
            };
        default:
            return state;
    }
};
