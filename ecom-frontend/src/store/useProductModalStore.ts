import { create } from "zustand";
import { Product } from "../types/Product";

interface ProductModalState {
    isModalOpen: boolean;
    selectedProduct: Product | null;
    openModal: (product: Product) => void;
    closeModal: () => void;
}

export const useProductModalStore = create<ProductModalState>((set) => ({
    isModalOpen: false,
    selectedProduct: null,
    openModal: (product) => set({ isModalOpen: true, selectedProduct: product }),
    closeModal: () => set({ isModalOpen: false, selectedProduct: null })
}));
