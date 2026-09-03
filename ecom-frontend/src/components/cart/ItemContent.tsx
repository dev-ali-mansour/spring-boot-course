import React, {useState} from "react";
import {CartItem} from "../../types";
import truncateText from "../../utils/truncateText.tsx";
import {HiOutlineTrash} from "react-icons/hi";
import SetQuantity from "./SetQuantity.tsx";
import {useCartStore} from "../../store";
import toast from "react-hot-toast";
import {formatPrice} from "../../utils/formatPrice.ts";

const ItemContent: React.FC<CartItem> = (item) => {
    const [currentQuantity, setCurrentQuantity] = useState<number>(item.quantity);
    const increaseCartQuantity = useCartStore((state) => state.increaseCartQuantity);
    const decreaseCartQuantity = useCartStore((state) => state.decreaseCartQuantity);
    const removeFromCart = useCartStore((state) => state.removeFromCart);

    const activePrice = item.specialPrice ? Number(item.specialPrice) : Number(item.price);

    const handleQuantityIncrease = () => {
        increaseCartQuantity(item, toast, currentQuantity, setCurrentQuantity);
    };

    const handleQuantityDecrease = () => {
        if (currentQuantity > 1) {
            const newQuantity = currentQuantity - 1;
            setCurrentQuantity(newQuantity);
            decreaseCartQuantity(item, newQuantity);
        }
    };

    const removeItemFromCart = () => {
        removeFromCart(item, toast);
    };

    return (
        <div
            className={`grid md:grid-cols-5 grid-cols-4 md:text-md text-sm gap-4 items-center border 
                        border-slate-200 rounded-md lg:px-4 py-4 p-2`}>
            <div className={"md:col-span-2 justify-self-start flex flex-col gap-2"}>
                <div className={"flex md:flex-row flex-col lg:gap-4 sm:gap-3 gap-0 items-start"}>
                    <h3 className={"lg:text-[17px] text-sm font-semibold text-slate-600"}>
                        {truncateText(item.name || "")}
                    </h3>
                </div>

                <div className={"md:w-36 sm:w-24 w-12"}>
                    <img src={item.image}
                         alt={item.name}
                         className={"md:h-36 sm:h-24 h-12 w-full object-cover rounded-md"}/>

                    <div className={"flex items-start gap-5 mt-3"}>
                        <button
                            onClick={removeItemFromCart}
                            className={`flex items-center font-semibold space-x-2 px-4 py-1 text-xs border 
                                    border-rose-600 text-rose-600 rounded-md hover:bg-red-50 transition-colors 
                                    duration-200 cursor-pointer`}>
                            <HiOutlineTrash className={"text-rose-600"}/>
                            Remove
                        </button>
                    </div>
                </div>
            </div>

            <div className="justify-self-center lg:text-[17px] text-sm text-slate-600 font-semibold">
                {formatPrice(activePrice)}
            </div>

            <div className="justify-self-center">
                <SetQuantity
                    quantity={currentQuantity}
                    cardCounter={true}
                    handleQuantityIncrease={handleQuantityIncrease}
                    handleQuantityDecrease={handleQuantityDecrease}/>
            </div>

            <div className="justify-self-center lg:text-[17px] text-sm text-slate-600 font-semibold">
                {formatPrice(activePrice * currentQuantity)}
            </div>

        </div>
    )
        ;
};

export default ItemContent;