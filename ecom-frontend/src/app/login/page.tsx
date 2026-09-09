import type { Metadata } from "next";
import Login from "@/components/auth/Login";
import AuthGuard from "@/components/shared/AuthGuard";

export const metadata: Metadata = {
  title: "Login",
  description: "Sign in to your E-Shop account to manage orders and addresses.",
};

export default function LoginPage() {
  return (
    <AuthGuard requireGuest={true}>
      <Login />
    </AuthGuard>
  );
}
