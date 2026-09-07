import type { Metadata } from "next";
import Home from "../components/home/Home";

export const metadata: Metadata = {
  title: "Home",
  description: "Discover our handpicked selection of top-rated items just for you at E-Shop.",
  openGraph: {
    title: "E-Shop - Best Deals & Top Rated Products",
    description: "Discover our handpicked selection of top-rated items just for you at E-Shop.",
    type: "website",
  },
};

export default function Page() {
  return <Home />;
}
