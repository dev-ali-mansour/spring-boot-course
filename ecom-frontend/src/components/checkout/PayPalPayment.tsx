import React from "react";
import {Alert, AlertTitle} from "@mui/material";

const PayPalPayment: React.FC = () => {
    return (
        <div className={"h-96 flex justify-center items-center"}>
            <Alert severity={"warning"} variant={"filled"} style={{maxWidth: "400px"}}>
                <AlertTitle>PayPal Unavailable</AlertTitle>
                PayPal payment is unavailable at the moment. Please use another payment method.
            </Alert>
        </div>
    );
};
export default PayPalPayment;