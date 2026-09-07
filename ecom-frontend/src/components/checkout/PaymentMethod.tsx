import React, {useEffect, useRef} from "react";
import {FormControl, FormControlLabel, Radio, RadioGroup} from "@mui/material";
import {useCartStore, usePaymentStore} from "../../store";
import {getErrorMessage, useCreateUserCart} from "../../hooks/useQueries";
import toast from "react-hot-toast";

const PaymentMethod: React.FC = () => {
    const {paymentMethod, setPaymentMethod} = usePaymentStore();
    const {cartItems, cartId} = useCartStore();
    const createUserCartMutation = useCreateUserCart();
    const hasInitialized = useRef(false);

    useEffect(() => {
        const createCart = async () => {
            if (cartItems.length > 0 && !cartId && !hasInitialized.current) {
                hasInitialized.current = true;
                const sendCartItems = cartItems.map((item) => {
                    return {
                        productId: item.id,
                        quantity: item.quantity,
                    };
                });

                try {
                    await createUserCartMutation.mutateAsync(sendCartItems);
                } catch (error: unknown) {
                    console.error("Failed to create user cartItems:", error);
                    toast.error(getErrorMessage(error) || "Failed to create user cartItems. Please try again.");
                }
            }
        };

        createCart().then(() => console.log("Cart creation process completed."));
    }, [cartId, cartItems, createUserCartMutation]);

    const paymentMethodHandler = (method: string) => {
        setPaymentMethod(method);
    }
    
    return (
        <div className={"max-w-md mx-auto p-5 bg-white shadow-md rounded-lg mt-16 border"}>
            <h1 className={"text-2xl font-semibold mb-4"}>Select Payment Method</h1>
            <FormControl>
                <RadioGroup
                    aria-label={"payment method"}
                    name={"paymentMethod"}
                    value={paymentMethod}
                    onChange={(e) => paymentMethodHandler(e.target.value)}
                >
                    <FormControlLabel
                        value={"Stripe"}
                        control={<Radio color={"primary"}/>}
                        label={"Stripe"}
                        className={"text-gray-700"}/>

                    <FormControlLabel
                        value={"PayPal"}
                        control={<Radio color={"primary"}/>}
                        label={"PayPal"}
                        className={"text-gray-700"}/>
                </RadioGroup>
            </FormControl>
        </div>
    );
};

export default PaymentMethod;