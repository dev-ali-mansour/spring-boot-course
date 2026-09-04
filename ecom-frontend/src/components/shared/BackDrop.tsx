import React from "react";

const BackDrop: React.FC = () => {
    return (
        <div className={`z-20 transition-all duration-200 opacity-60 w-screen 
                        h-screen bg-slate-300 fixed top-0 left-0`}>
        </div>
    );
};

export default BackDrop;
