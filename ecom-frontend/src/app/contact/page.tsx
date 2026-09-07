import type { Metadata } from "next";
import Contact from "../../components/Contact";

export const metadata: Metadata = {
  title: "Contact Us",
  description: "Get in touch with the E-Shop customer support team. We're here to help.",
};

export default function ContactPage() {
  return <Contact />;
}
