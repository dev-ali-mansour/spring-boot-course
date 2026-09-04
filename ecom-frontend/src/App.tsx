import * as React from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import Home from "./components/home/Home.tsx";
import Products from "./components/products/Products.tsx";
import Navbar from "./components/shared/Navbar.tsx";
import About from "./components/About.tsx";
import Contact from "./components/Contact.tsx";
import {Toaster} from "react-hot-toast";
import Cart from "./components/cart/Cart.tsx";
import SignIn from "./components/auth/SignIn.tsx";
import PrivateRouter from "./components/PrivateRouter.tsx";

const App: React.FC = () => {
    return (
        <React.Fragment>
            <Router>
                <Navbar/>
                <Routes>
                    <Route path={"/"} element={<Home/>}/>
                    <Route path={"/products"} element={<Products/>}/>
                    <Route path={"/about"} element={<About/>}/>
                    <Route path={"/contact"} element={<Contact/>}/>
                    <Route path={"/cart"} element={<Cart/>}/>
                    <Route path={"/"} element={<PrivateRouter isPublicPage={true}/>}>
                        <Route path="/signin" element={<SignIn/>}/>
                    </Route>
                </Routes>
            </Router>
            <Toaster position={"bottom-center"}/>
        </React.Fragment>
    );
};

export default App;
