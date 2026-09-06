import React from "react";
import {Alert, AlertTitle} from "@mui/material";

const StripePayment: React.FC = () => {
    return (
        <div className={"h-96 flex justify-center items-center"}>
            <Alert severity={"warning"} variant={"filled"} style={{maxWidth: "400px"}}>
                <AlertTitle>Stripe Unavailable</AlertTitle>
                Stripe payment is unavailable at the moment. Please use another payment method.
            </Alert>
        </div>
    );
};

export default StripePayment;