import type { Metadata } from "next";
import About from "../../components/About";

export const metadata: Metadata = {
  title: "About Us",
  description: "Learn more about our mission, values, and dedication to quality products at E-Shop.",
};

export default function AboutPage() {
  return <About />;
}
