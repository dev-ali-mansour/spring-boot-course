import React, {useState} from "react";
import {PaymentElement, useElements, useStripe} from "@stripe/react-stripe-js";
import Skeleton from "@/components/shared/Skeleton";

interface PaymentFormProps {
    clientSecret: string;
    totalPrice: number;
}

const PaymentForm: React.FC<PaymentFormProps> = ({clientSecret, totalPrice}) => {
    const stripe = useStripe();
    const elements = useElements();
    const [errorMessage, setErrorMessage] = useState<string | null>(null);
    const isLoading = !clientSecret || !stripe || !elements;
    const paymentElementOptions = {
        layout: "tabs" as any,
    }

    const handleSubmit = async (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!stripe || !elements) {
            return;
        }
        const {error: submitError} = await elements.submit();
        if (submitError) {
            setErrorMessage(submitError.message || "An error occurred with payment submission.");
            return false;
        }
        const {error} = await stripe.confirmPayment({
            elements,
            clientSecret,
            confirmParams: {
                return_url: `${process.env.NEXT_PUBLIC_FRONTEND_URL}/order-confirm`,
            },
        });

        if (error) {
            setErrorMessage(error.message || "An unexpected error occurred.");
            return false;
        }

        

    };

    return (
        <form onSubmit={handleSubmit}
              className={"max-w-lg mx-auto p-4"}>
            <h2 className={"text-xl font-semibold mb-4"}>Payment Information</h2>
            {isLoading ? (
                <Skeleton/>
            ) : (
                <>
                    {clientSecret && <PaymentElement options={paymentElementOptions}/>}
                    {errorMessage && (
                        <div className={"text-red-500 mt-2"}>{errorMessage}</div>
                    )}

                    <button
                        className={`text-white w-full px-5 py-2.5 bg-black mt-2 rounded-md font-bold 
                                disabled:opacity-50 disabled:animate-pulse`}
                        disabled={!stripe || isLoading}>
                        {!isLoading ? `Pay $${Number(totalPrice).toFixed(2)}`
                            : "Processing"}
                    </button>
                </>
            )}
        </form>
    );
};

export default PaymentForm;