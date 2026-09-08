import React, {useMemo} from "react";
import {useAuthStore, useCartStore} from "@/store";
import {Elements} from "@stripe/react-stripe-js";
import {loadStripe} from "@stripe/stripe-js";
import PaymentForm from "@/components/checkout/PaymentForm";
import {getErrorMessage, useCreateStripeClientSecret} from "@/hooks/useQueries";
import Skeleton from "@/components/shared/Skeleton";
import ErrorPage from "@/components/shared/ErrorPage";

const stripePromise = loadStripe(process.env.NEXT_PUBLIC_STRIPE_PUBLISHABLE_KEY!);

const StripePayment: React.FC = () => {
    const {user, selectedUserCheckoutAddress} = useAuthStore((state) => state);
    const {totalPrice} = useCartStore();

    const sendData = useMemo(() => {
        if (!user || !selectedUserCheckoutAddress) return null;

        return {
            amount: Math.round(Number(totalPrice) * 100),
            currency: "usd",
            email: user.email,
            name: `${user.firstName} ${user.lastName}`,
            address: selectedUserCheckoutAddress,
            description: `Order for ${user.email}`,
            metadata: {
                test: "1",
            },
        };
    }, [user, selectedUserCheckoutAddress, totalPrice]);

    const {data, isPending, isError, error} = useCreateStripeClientSecret(sendData);
    const clientSecret = data?.clientSecret;

    if (isPending) {
        return (
            <div className={"max-w-lg mx-auto"}>
                <Skeleton/>
            </div>
        )
    }

    if (isError) {
        return (
            <div className={"max-w-lg mx-auto"}>
                <ErrorPage message={getErrorMessage(error)} />
            </div>
        );
    }

    return (
        <>
            {clientSecret && (
                <Elements stripe={stripePromise} options={{clientSecret}}>
                    <PaymentForm clientSecret={clientSecret} totalPrice={totalPrice}/>
                </Elements>
            )}
        </>
    );
};

export default StripePayment;