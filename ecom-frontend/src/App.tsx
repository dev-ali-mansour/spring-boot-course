import * as React from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Home from "./components/home/Home.tsx";
import Products from "./components/products/Products.tsx";
import Navbar from "./components/shared/Navbar.tsx";
import About from "./components/about/About.tsx";
import Contact from "./components/contact/Contact.tsx";
import {Toaster} from "react-hot-toast";

const App: React.FC = () => {
    return (
        <React.Fragment>
            <Router>
                <Navbar/>
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/products" element={<Products/>}/>
                    <Route path="/about" element={<About/>}/>
                    <Route path="/contact" element={<Contact/>}/>
                </Routes>
            </Router>
            <Toaster position={"bottom-center"}/>
        </React.Fragment>
    );
};

export default App;
