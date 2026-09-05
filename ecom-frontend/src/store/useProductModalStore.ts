import {create} from "zustand";
import {Product} from "../types";
import {devtools} from "zustand/middleware";

interface ProductModalState {
    isModalOpen: boolean;
    selectedProduct: Product | null;
    openModal: (product: Product) => void;
    closeModal: () => void;
}

export const useProductModalStore = create<ProductModalState>()(
    devtools(
        (set) => ({
            isModalOpen: false,
            selectedProduct: null,
            openModal: (product) => {
                set({isModalOpen: true, selectedProduct: product}, false, "openModal");
            },
            closeModal: () => {
                set({isModalOpen: false, selectedProduct: null}, false, "closeModal");
            }
        }),
        {name: "ProductModalStore"}
    ),
);
