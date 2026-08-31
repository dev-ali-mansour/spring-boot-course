export interface ErrorState {
    isLoading: boolean;
    errorMessage: string | null;
}

const initialState: ErrorState = {
    isLoading: false,
    errorMessage: null
};

export const errorReducer = (state = initialState, action: any): ErrorState => {
    switch (action.type) {
        case "IS_FETCHING":
            return {
                ...state,
                isLoading: true,
                errorMessage: null
            };
        case "IS_SUCCESS":
            return {
                ...state,
                isLoading: false,
                errorMessage: null
            };
        case "IS_ERROR":
            return {
                ...state,
                isLoading: false,
                errorMessage: action.payload
            };
        default:
            return state;
    }
};
