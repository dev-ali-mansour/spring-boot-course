export interface ErrorState {
    isProductsLoading: boolean;
    isCategoriesLoading: boolean;
    productsErrorMessage: string | null;
    categoriesErrorMessage: string | null;
}

const initialState: ErrorState = {
    isProductsLoading: false,
    isCategoriesLoading: false,
    productsErrorMessage: null,
    categoriesErrorMessage: null
};

export const errorReducer = (state = initialState, action: any): ErrorState => {
    switch (action.type) {
        case "PRODUCTS_FETCHING":
            return {
                ...state,
                isProductsLoading: true,
                productsErrorMessage: null
            };
        case "PRODUCTS_SUCCESS":
            return {
                ...state,
                isProductsLoading: false,
                productsErrorMessage: null
            };
        case "PRODUCTS_ERROR":
            return {
                ...state,
                isProductsLoading: false,
                productsErrorMessage: action.payload
            };
        case "CATEGORIES_FETCHING":
            return {
                ...state,
                isCategoriesLoading: true,
                categoriesErrorMessage: null
            };
        case "CATEGORIES_SUCCESS":
            return {
                ...state,
                isCategoriesLoading: false,
                categoriesErrorMessage: null
            };
        case "CATEGORIES_ERROR":
            return {
                ...state,
                isCategoriesLoading: false,
                categoriesErrorMessage: action.payload
            };
        default:
            return state;
    }
};
