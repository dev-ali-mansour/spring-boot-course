import React from "react";

interface SetQuantityProps {
    quantity: number;
    cardCounter: boolean;
    handleQuantityIncrease: () => void;
    handleQuantityDecrease: () => void;
}

const btnStyles = "border-[1.2px] border-slate-800 px-3 py-1 rounded cursor-pointer disabled:cursor-not-allowed disabled:opacity-50";

const SetQuantity: React.FC<SetQuantityProps> = (
    {
        quantity,
        cardCounter,
        handleQuantityIncrease,
        handleQuantityDecrease
    }: SetQuantityProps) => {
    return (
        <div className={"flex gap-8 items-center"}>
            {cardCounter ? null : <div className={"font-semibold"}>QUANTITY</div>}
            <div className={"flex md:flex-row flex-col gap-4 items-center lg:text-[22px] text-sm"}>
                <button
                    disabled={quantity <= 1}
                    className={btnStyles}
                    onClick={handleQuantityDecrease}>
                    -
                </button>
                <div className={"text-red-500"}>{quantity}</div>
                <button
                    className={btnStyles}
                    onClick={handleQuantityIncrease}>
                    +
                </button>
            </div>
        </div>
    );
};

export default SetQuantity;
