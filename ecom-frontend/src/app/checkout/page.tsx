import type { Metadata } from "next";
import Checkout from "../../components/checkout/Checkout";
import AuthGuard from "../../components/shared/AuthGuard";

export const metadata: Metadata = {
  title: "Secure Checkout",
  description: "Complete your order with fast shipping and secure payments.",
};

export default function CheckoutPage() {
  return (
    <AuthGuard isPublicPage={false}>
      <Checkout />
    </AuthGuard>
  );
}
