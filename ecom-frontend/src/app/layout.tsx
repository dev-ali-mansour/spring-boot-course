import type { Metadata } from "next";
import "@/index.css";
import Providers from "@/app/providers";
import Navbar from "@/components/shared/Navbar";

export const metadata: Metadata = {
  metadataBase: new URL(process.env.NEXT_PUBLIC_SITE_URL || "http://localhost:3000"),
  title: {
    default: "E-Shop | Modern E-Commerce Store",
    template: "%s | E-Shop",
  },
  description: "Discover our handpicked selection of top-rated items with secure checkout and fast delivery.",
  keywords: ["ecommerce", "online shopping", "electronics", "fashion", "deals"],
  openGraph: {
    type: "website",
    locale: "en_US",
    siteName: "E-Shop",
    title: "E-Shop | Modern E-Commerce Store",
    description: "Discover our handpicked selection of top-rated items with secure checkout and fast delivery.",
  },
  twitter: {
    card: "summary_large_image",
    title: "E-Shop | Modern E-Commerce Store",
    description: "Discover our handpicked selection of top-rated items with secure checkout and fast delivery.",
  },
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en">
      <body 
        className="font-montserrat antialiased bg-gray-50 text-slate-900 min-h-screen flex flex-col"
        suppressHydrationWarning
      >
        <Providers>
          <Navbar />
          <main className="flex-1">{children}</main>
        </Providers>
      </body>
    </html>
  );
}
