import {configureStore} from "@reduxjs/toolkit";
import {productReducer} from "./ProductReducer.js";

export const store = configureStore(
    {
        reducer: {
            products: productReducer,
        },
        preloadedState: {}
    });