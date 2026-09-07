import * as React from "react";
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import {Toaster} from "react-hot-toast";

import Navbar from "./components/shared/Navbar.tsx";
import PrivateRouter from "./components/PrivateRouter.tsx";
import Loader from "./components/shared/Loader.tsx";

const Home = React.lazy(() => import("./components/home/Home.tsx"));
const Products = React.lazy(() => import("./components/products/Products.tsx"));
const About = React.lazy(() => import("./components/About.tsx"));
const Contact = React.lazy(() => import("./components/Contact.tsx"));
const Cart = React.lazy(() => import("./components/cart/Cart.tsx"));
const Login = React.lazy(() => import("./components/auth/Login.tsx"));
const Register = React.lazy(() => import("./components/auth/Register.tsx"));
const Checkout = React.lazy(() => import("./components/checkout/Checkout.tsx"));

const App: React.FC = () => {
    return (
        <React.Fragment>
            <Router>
                <Navbar/>
                <React.Suspense fallback={<Loader />}>
                    <Routes>
                        <Route path={"/"} element={<Home/>}/>
                        <Route path={"/products"} element={<Products/>}/>
                        <Route path={"/about"} element={<About/>}/>
                        <Route path={"/contact"} element={<Contact/>}/>
                        <Route path={"/cart"} element={<Cart/>}/>

                        <Route path={"/"} element={<PrivateRouter/>}>
                            <Route path={"/checkout"} element={<Checkout/>}/>
                        </Route>

                        <Route path={"/"} element={<PrivateRouter isPublicPage/>}>
                            <Route path="/login" element={<Login/>}/>
                            <Route path="/register" element={<Register/>}/>
                        </Route>
                    </Routes>
                </React.Suspense>
            </Router>
            <Toaster position={"bottom-center"}/>
        </React.Fragment>
    );
};

export default App;
