"use client";

import React, {useEffect, useRef} from "react";
import {useSearchParams} from "next/navigation";
import {useAuthStore, useCartStore} from "@/store";
import {getErrorMessage, useStripePaymentConfirmation} from "@/hooks/useQueries";
import {CHECKOUT_ADDRESS_STORAGE_KEY} from "@/utils/constant";
import toast from "react-hot-toast";
import {Skeleton} from "@mui/material";
import {FaCheckCircle} from "react-icons/fa";
import {Address} from "@/types";

const PaymentConfirmation: React.FC = () => {
    const searchParams = useSearchParams();
    const [errorMessage, setErrorMessage] = React.useState<string | null>(null);
    const {cartItems, clearCart} = useCartStore();
    const {clearCheckoutSession, selectedUserCheckoutAddress} = useAuthStore();
    const stripeConfirmation = useStripePaymentConfirmation();

    const paymentIntent = searchParams.get("payment_intent");
    const clientSecret = searchParams.get("payment_intent_client_secret");
    const redirectStatus = searchParams.get("redirect_status");

    const hasInitialized = useRef(false);

    useEffect(() => {
        if (paymentIntent &&
            clientSecret &&
            redirectStatus &&
            cartItems &&
            cartItems?.length > 0 &&
            !hasInitialized.current
        ) {
            hasInitialized.current = true;
            const address: Address = selectedUserCheckoutAddress || (localStorage.getItem(CHECKOUT_ADDRESS_STORAGE_KEY)
                ? JSON.parse(localStorage.getItem(CHECKOUT_ADDRESS_STORAGE_KEY)!)
                : null);

            const sendData = {
                addressId: address?.id,
                pgName: "Stripe",
                pgPaymentId: paymentIntent,
                pgStatus: "succeeded",
                pgResponseMessage: "Payment successful"
            };

            stripeConfirmation.mutate(sendData, {
                onSuccess: () => {
                    clearCart();
                    clearCheckoutSession();
                    toast.success("Payment successful!");
                },
                onError: (error: unknown) => {
                    const msg = getErrorMessage(error);
                    setErrorMessage(msg);
                    toast.error(msg);
                }
            });
        }
    }, [paymentIntent, clientSecret, redirectStatus, cartItems, clearCart, clearCheckoutSession, selectedUserCheckoutAddress, stripeConfirmation]);


    return (
        <div className={"min-h-screen flex items-center justify-center"}>
            {stripeConfirmation.isPending ? (
                <div className="max-w-xl mx-auto">
                    <Skeleton/>
                </div>
            ) : (
                <div className={"p-8 rounded-lg shadow-lg text-center max-w-md mx-auto border border-gray-200"}>
                    <div className={"text-green-500 mb-4 flex  justify-center"}>
                        <FaCheckCircle size={64}/>
                    </div>
                    <h2 className={"text-3xl font-bold text-gray-800 mb-2"}>Payment Successful!</h2>
                    <p className={"text-gray-600 mb-6"}>
                        Thank you for your purchase! Your payment was successful, and we’re
                        processing your order.
                    </p>
                    {errorMessage && (
                        <p className="text-red-500 font-semibold">{errorMessage}</p>
                    )}
                </div>
            )}
        </div>
    );
};

export default PaymentConfirmation;