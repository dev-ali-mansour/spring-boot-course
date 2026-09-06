import React, {useState} from "react";
import {Button, Step, StepLabel, Stepper} from "@mui/material";
import AddressInfo from "./AddressInfo.tsx";
import {getErrorMessage, useGetUserAddresses} from "../../hooks/useQueries.ts";
import {useAuthStore, useCartStore, usePaymentStore} from "../../store";
import toast from "react-hot-toast";
import Skeleton from "../shared/Skeleton.tsx";
import ErrorPage from "../shared/ErrorPage.tsx";
import PaymentMethod from "./PaymentMethod.tsx";
import OrderSummary from "./OrderSummary.tsx";
import StripePayment from "./StripePayment.tsx";
import PayPalPayment from "./PayPalPayment.tsx";

const steps = [
    "Address",
    "Payment Method",
    "Order Summary",
    "Payment",
];

const Checkout: React.FC = () => {
    const [activeStep, setActiveStep] = useState(0);
    const {data: addresses = [], isLoading, error} = useGetUserAddresses();
    const errorMessage = error ? getErrorMessage(error) : null;
    const {cartItems, totalPrice} = useCartStore();
    const {selectedUserCheckoutAddress} = useAuthStore();
    const {paymentMethod} = usePaymentStore();

    const handleBack = () => {
        setActiveStep((prevStep) => prevStep - 1);
    };

    const handleNext = () => {
        if (activeStep === 0 && !selectedUserCheckoutAddress) {
            toast.error("Please select checkout address before proceeding.");
            return;
        }
        if (activeStep === 1 && (!selectedUserCheckoutAddress || !paymentMethod)) {
            toast.error("Please select payment method before proceeding.");
            return;
        }
        setActiveStep((prevStep) => prevStep + 1);
    };

    return (
        <div className={"py-14 min-h-[calc(100vh-100px)]"}>
            <Stepper activeStep={activeStep} alternativeLabel>
                {steps.map((label, index) => (
                    <Step key={index}>
                        <StepLabel>{label}</StepLabel>
                    </Step>
                ))}
            </Stepper>

            {isLoading ? (
                <div className={"lg:w-[80%] mx-auto py-5"}>
                    <Skeleton/>
                </div>
            ) : (
                <div className={"mt-5"}>
                    {activeStep === 0 && <AddressInfo addresses={addresses}/>}
                    {activeStep === 1 && <PaymentMethod/>}
                    {activeStep === 2 &&
                        <OrderSummary
                            totalPrice={totalPrice}
                            cartItems={cartItems}
                            address={selectedUserCheckoutAddress}
                            paymentMethod={paymentMethod}
                        />}
                    {activeStep === 3 &&
                        <>
                            {paymentMethod === "Stripe" ? (
                                <StripePayment/>
                            ) : (
                                <PayPalPayment/>
                            )}
                        </>
                    }
                </div>
            )}
            <div
                className={`flex justify-between items-center px-4 fixed z-50 h-24 bottom-0 bg-white left-0 
                    w-full py-4 border-slate-200`}
                style={{boxShadow: "0 -2px 4px rgba(100, 100, 100, 0.15)"}}>
                <Button
                    variant='outlined'
                    disabled={activeStep === 0}
                    onClick={handleBack}>
                    Back
                </Button>

                {activeStep !== steps.length - 1 && (
                    <button
                        disabled={errorMessage || (
                            activeStep === 0 ? !selectedUserCheckoutAddress
                                : activeStep === 1 ? !paymentMethod
                                    : false
                        )}
                        className={`bg-custom-blue font-semibold px-6 h-10 rounded-md text-white cursor-pointer
                       ${
                            errorMessage ||
                            (activeStep === 0 && !selectedUserCheckoutAddress) ||
                            (activeStep === 1 && !paymentMethod)
                                ? "opacity-60"
                                : ""
                        }`}
                        onClick={handleNext}>
                        Proceed
                    </button>
                )}
            </div>

            {errorMessage && <ErrorPage message={errorMessage}/>}
        </div>
    );
};

export default Checkout;