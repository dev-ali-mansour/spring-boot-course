
import { IconType } from "react-icons";

interface StatusProps {
    text: string;
    icon: IconType;
    backgroundColor: string;
    textColor: string;
}

export default function Status({ text, icon: Icon, backgroundColor, textColor }: StatusProps) {
    return (
        <div
            className={`${backgroundColor} ${textColor} px-2 py-2 font-medium rounded flex items-center gap-1`}>
            {text} <Icon size={15}/>
        </div>
    );
}
