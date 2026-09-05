import aboutUsImage from "../assets/images/about-us.jpg";
import ProductCard from "./shared/ProductCard.tsx";
import {Product} from "../types";
import React from "react";

const products: Product[] = [
    {
        id: 1,
        image: "http://localhost:8080/images/6f4237b8-545f-445f-8c41-3e68643f23a6.webp",
        name: "iPhone 13 Pro",
        description:
            "The iPhone 13 Pro is a cutting-edge smartphone with a powerful camera system, high-performance chip, and stunning display. It offers advanced features for users who demand top-notch technology.",
        specialPrice: 720,
        price: 780,
        quantity: 10,
    },
    {
        id: 2,
        image: "http://localhost:8080/images/f6e8c16a-2282-412d-a0cf-d222f70733f1.webp",
        name: "Samsung Galaxy S10",
        description:
            "The Samsung Galaxy S10 is a flagship device featuring a dynamic AMOLED display, versatile camera system, and powerful performance. It represents innovation and excellence in smartphone technology.",
        specialPrice: 699,
        price: 799,
        quantity: 8,
    },
    {
        id: 3,
        image: "http://localhost:8080/images/7f3ab88a-75d5-435d-8705-ae4f545bcdbc.webp",
        name: "Durango SXT RWD",
        description:
            "The Durango SXT RWD is a spacious and versatile SUV, known for its strong performance and family-friendly features.",
        price: 599,
        specialPrice: 400,
        quantity: 5,
    }
];

const About: React.FC = () => {

    return (
        <div className={"max-w-7xl mx-auto px-4 py-8"}>
            <h1 className={"text-slate-800 text-4xl font-bold text-center mb-12"}>
                About Us
            </h1>
            <div className={"flex flex-col lg:flex-row justify-between items-center mb-12"}>
                <div className={"w-full md:w-1/2 text-center md:text-left"}>
                    <p className={"text-lg mb-4"}>
                        Welcome to our e-commerce store! We are dedicated to providing the
                        best products and services to our customers. Our mission is to offer
                        a seamless shopping experience while ensuring the highest quality of
                        our offerings.
                    </p>
                </div>

                <div className={"w-full md:w-1/2 mb-6 md:mb-0"}>
                    <img
                        src={aboutUsImage}
                        alt={"About Us"}
                        className={`w-full h-auto rounded-lg shadow-lg transform transition-transform 
                                    duration-300 hover:scale-105`}/>
                </div>
            </div>

            <div className={"py-7 space-y-8"}>
                <h1 className={"text-slate-800 text-4xl font-bold text-center mb-12"}>
                    Our Products
                </h1>
                <div className={"grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"}>
                    {products.map((product) => (
                        <ProductCard
                            key={product.id}
                            product={product}
                            isCompact
                        />
                    ))}
                </div>
            </div>
        </div>
    );
};

export default About;
