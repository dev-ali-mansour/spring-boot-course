"use client";
import React, {useState} from "react";
import useSellerFilter from "@/hooks/useSellerFilter";
import {getErrorMessage, useSellers} from "@/hooks/useQueries";
import Loader from "@/components/shared/Loader";
import ErrorPage from "@/components/shared/ErrorPage";
import {FaThList, FaUserPlus} from "react-icons/fa";
import Modal from "@/components/shared/Modal";
import SellersTable from "@/components/admin/sellers/SellersTable";
import AddSellerForm from "@/components/admin/sellers/AddSellerForm";

const Sellers: React.FC = () => {
    const queryString = useSellerFilter();
    const {data: sellersData, isLoading, error} = useSellers(queryString);
    const sellers = sellersData?.content || [];
    const pagination = sellersData;
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const isEmptySellers = !sellersData || sellersData?.content?.length === 0;

    if (isLoading) return <Loader/>;
    if (error) return <ErrorPage message={getErrorMessage(error)}/>;

    return (
        <div>
            <div className={"pt-6 pb-10 flex justify-end"}>
                <button
                    onClick={() => setIsAddModalOpen(true)}
                    className={`bg-custom-blue hover:bg-blue-800 text-white font-semibold py-2 px-4 flex 
                        items-center gap-2 rounded-md shadow-md transition-colors hover:text-slate-300 duration-300`}>
                    <FaUserPlus className={"text-xl"}/>
                    Add Seller
                </button>
            </div>

            {isLoading ? (
                <Loader/>
            ) : (
                isEmptySellers ? (
                    <div className={"flex flex-col items-center justify-center text-gray-600 py-10"}>
                        <FaThList size={50} className={"mb-3"}/>
                        <h2 className={"text-2xl font-semibold"}>
                            No Sellers created yet
                        </h2>
                    </div>
                ) : (
                    <SellersTable
                        sellers={sellers}
                        pagination={pagination}/>
                )
            )}

            <Modal
                open={isAddModalOpen}
                setOpen={setIsAddModalOpen}
                title={"Add Seller"}>
                <AddSellerForm setIsOpen={setIsAddModalOpen}/>
            </Modal>
        </div>
    );
}

export default Sellers;
