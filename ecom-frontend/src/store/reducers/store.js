import {configureStore} from "@reduxjs/toolkit";
import {productReducer} from "./productReducer.js";
import {errorReducer} from "./errorReducer.js";

export const store = configureStore(
    {
        reducer: {
            products: productReducer,
            errors: errorReducer,
        },
        preloadedState: {}
    });