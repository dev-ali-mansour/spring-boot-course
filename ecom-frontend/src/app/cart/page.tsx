import type { Metadata } from "next";
import Cart from "@/components/cart/Cart";

export const metadata: Metadata = {
  title: "Your Shopping Cart",
  description: "View and manage items currently in your shopping cart.",
};

export default function CartPage() {
  return <Cart />;
}
