import {create} from "zustand";

interface PaymentState {
    paymentMethod: string | null;
    setPaymentMethod: (method: string | null) => void;
}

export const usePaymentStore = create<PaymentState>((set) => ({
    paymentMethod: null,
    setPaymentMethod: (method) => set({paymentMethod: method}),
}));