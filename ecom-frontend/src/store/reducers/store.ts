import { configureStore } from "@reduxjs/toolkit";
import { productReducer } from "./productReducer";
import { errorReducer } from "./errorReducer";

export const store = configureStore({
    reducer: {
        products: productReducer,
        errors: errorReducer,
    },
    preloadedState: {}
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
