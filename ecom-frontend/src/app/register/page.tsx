import type { Metadata } from "next";
import Register from "@/components/auth/Register";
import AuthGuard from "@/components/shared/AuthGuard";

export const metadata: Metadata = {
  title: "Create an Account",
  description: "Register for an E-Shop account to enjoy personalized shopping.",
};

export default function RegisterPage() {
  return (
    <AuthGuard isPublicPage={true}>
      <Register />
    </AuthGuard>
  );
}
