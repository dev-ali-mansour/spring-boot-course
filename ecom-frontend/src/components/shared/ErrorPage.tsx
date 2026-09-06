import React from "react";
import {FaExclamationTriangle} from "react-icons/fa";

interface ErrorPageProps {
    message?: string;
}

const ErrorPage: React.FC<ErrorPageProps> = ({message}) => {
    return (
        <div className={"flex flex-col items-center justify-center px-6 py-14"}>
            <FaExclamationTriangle className={"text-red-500 text-6xl mb-4"}/>
            <p className={"text-gray-600 mb-6 text-center"}>
                {message ? message : "An unexpected error has been occurred."}
            </p>
        </div>
    );
};

export default ErrorPage;
