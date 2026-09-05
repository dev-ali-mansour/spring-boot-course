import React, {useState} from "react";
import {Step, StepLabel, Stepper} from "@mui/material";
import AddressInfo from "./AddressInfo.tsx";
import {useGetUserAddresses} from "../../hooks/useQueries.ts";

const steps = [
    "Address",
    "Payment Method",
    "Order Summary",
    "Payment",
];

const Checkout: React.FC = () => {
    const [activeStep] = useState(0);
    const { data: addresses = [] } = useGetUserAddresses();

    return (
        <div className={"py-14 min-h-[calc(100vh-100px)]"}>
            <Stepper activeStep={activeStep} alternativeLabel>
                {steps.map((label, index) => (
                    <Step key={index}>
                        <StepLabel>{label}</StepLabel>
                    </Step>
                ))}
            </Stepper>

            <div className={"mt-5"}>
                {activeStep === 0 && <AddressInfo addresses={addresses}/>}
            </div>
        </div>
    );
};

export default Checkout;