import {IconType} from "react-icons";
import React from "react";

interface StatusProps {
    text: string;
    icon: IconType;
    backgroundColor: string;
    textColor: string;
}

const Status: React.FC<StatusProps> = ({text, icon: Icon, backgroundColor, textColor}: StatusProps) => {
    return (
        <div
            className={`${backgroundColor} ${textColor} px-2 py-2 font-medium rounded flex items-center gap-1`}>
            {text} <Icon size={15}/>
        </div>
    );
};

export default Status;
