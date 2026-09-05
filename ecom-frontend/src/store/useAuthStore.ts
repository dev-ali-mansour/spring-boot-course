import {Address, User} from "../types";
import {create} from "zustand";
import {
    AUTH_STORAGE_KEY,
    CART_STORAGE_KEY,
    CHECKOUT_ADDRESS_STORAGE_KEY,
    CLIENT_SECRET_STORAGE_KEY
} from "../utils/constant.ts";
import {devtools} from "zustand/middleware";

interface AuthState {
    user: User | null;
    selectedUserCheckoutAddress: Address | null;
    clientSecret: string | null;
    setUser: (user: User | null) => void;
    clearUser: () => void;
    setSelectedUserCheckoutAddress: (address: Address | null) => void;
    clearCheckoutAddress: () => void;
    setClientSecret: (clientSecret: string | null) => void;
    clearClientSecret: () => void;
    clearCheckoutSession: () => void;
}

const getInitialUser = (): User | null => {
    try {
        const item = localStorage.getItem(AUTH_STORAGE_KEY);
        return item ? JSON.parse(item) : null;
    } catch {
        return null;
    }
};

const getInitialAddress = (): Address | null => {
    try {
        const item = localStorage.getItem(CHECKOUT_ADDRESS_STORAGE_KEY);
        return item ? JSON.parse(item) : null;
    } catch {
        return null;
    }
};

const getInitialClientSecret = (): string | null => {
    try {
        const item = localStorage.getItem(CLIENT_SECRET_STORAGE_KEY);
        return item ? JSON.parse(item) : null;
    } catch {
        return null;
    }
};

export const useAuthStore = create<AuthState>()(
    devtools(
        (set) => ({
            user: getInitialUser(),
            selectedUserCheckoutAddress: getInitialAddress(),
            clientSecret: getInitialClientSecret(),
            setUser: (user) => {
                if (user) {
                    localStorage.setItem(AUTH_STORAGE_KEY, JSON.stringify(user));
                } else {
                    localStorage.removeItem(AUTH_STORAGE_KEY);
                }
                set({user}, false, "setUser");
            },
            clearUser: () => {
                localStorage.removeItem(AUTH_STORAGE_KEY);
                localStorage.removeItem(CHECKOUT_ADDRESS_STORAGE_KEY);
                localStorage.removeItem(CLIENT_SECRET_STORAGE_KEY);
                set({user: null, selectedUserCheckoutAddress: null, clientSecret: null}, false, "clearUser");
            },
            setSelectedUserCheckoutAddress: (address) => {
                if (address) {
                    localStorage.setItem(CHECKOUT_ADDRESS_STORAGE_KEY, JSON.stringify(address));
                } else {
                    localStorage.removeItem(CHECKOUT_ADDRESS_STORAGE_KEY);
                }
                set({selectedUserCheckoutAddress: address}, false, "setSelectedUserCheckoutAddress");

            },
            clearCheckoutAddress: () => {
                localStorage.removeItem(CHECKOUT_ADDRESS_STORAGE_KEY);
                set({selectedUserCheckoutAddress: null}, false, "clearCheckoutAddress");
            },
            setClientSecret: (clientSecret) => {
                if (clientSecret) {
                    localStorage.setItem(CLIENT_SECRET_STORAGE_KEY, JSON.stringify(clientSecret));
                } else {
                    localStorage.removeItem(CLIENT_SECRET_STORAGE_KEY);
                }
                set({clientSecret: clientSecret}, false, "setClientSecret");
            },
            clearClientSecret: () => {
                localStorage.removeItem(CLIENT_SECRET_STORAGE_KEY);
                set({clientSecret: null}, false, "clearClientSecret");
            },
            clearCheckoutSession: () => {
                localStorage.removeItem(CHECKOUT_ADDRESS_STORAGE_KEY);
                localStorage.removeItem(CLIENT_SECRET_STORAGE_KEY);
                localStorage.removeItem(CART_STORAGE_KEY);
                set({selectedUserCheckoutAddress: null, clientSecret: null}, false, "clearCheckoutSession");
            },
        }),
        {name: "AuthStore"}
    ),
)