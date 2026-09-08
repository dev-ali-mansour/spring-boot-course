import PaymentConfirmation from "@/components/checkout/PaymentConfirmation";
import {Suspense} from "react";
import AuthGuard from "@/components/shared/AuthGuard";

export const metadata = {
    title: "Order Confirmation",
    description: "Thank you for your order! Your payment has been successfully processed.",
};

export default function OrderConfirmPage() {
    return (
        <Suspense fallback={<div>Loading...</div>}>
            <AuthGuard>
                <PaymentConfirmation/>
            </AuthGuard>
        </Suspense>
    );
}
