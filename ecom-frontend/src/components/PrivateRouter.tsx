import React from "react";
import {useAuthStore} from "../store";
import {Navigate, Outlet} from "react-router-dom";

export interface PrivateRouterProps {
    isPublicPage?: boolean;
}

const PrivateRouter: React.FC<PrivateRouterProps> = ({isPublicPage = false}: PrivateRouterProps) => {
    const user = useAuthStore((state) => state.user);

    if (isPublicPage) {
        return user ? <Navigate to={"/"}/> : <Outlet/>;
    }

    return user ? <Outlet/> : <Navigate to={"/login"}/>;
};

export default PrivateRouter;