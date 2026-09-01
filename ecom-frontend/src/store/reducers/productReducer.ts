import {Category, Pagination, Product} from "../../types/Product.ts";

export interface ProductState {
    products: Product[] | null;
    categories: Category[];
    pagination: Partial<Pagination>;
}

const initialState: ProductState = {
    products: null,
    categories: [],
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
        case "FETCH_CATEGORIES":
            return {
                ...state,
                categories: action.payload,
            };
        default:
            return state;
    }
};
