import React, {useState} from "react";
import Skeleton from "../shared/Skeleton.tsx";
import {FaAddressBook} from "react-icons/fa";
import AddressInfoModal from "./AddressInfoModal.tsx";
import AddAddressForm from "./AddAddressForm.tsx";
import {Address} from "../../types";
import AddressList from "./AddressList.tsx";
import DeleteAddressModal from "./DeleteAddressModal.tsx";
import {getErrorMessage, useDeleteAddress} from "../../hooks/useQueries.ts";
import {useAuthStore} from "../../store";
import toast from "react-hot-toast";

interface AddressInfoProps {
    addresses: Address[];
}

const AddressInfo: React.FC<AddressInfoProps> = ({addresses}) => {
    const [openAddressModal, setOpenAddressModal] = useState(false);
    const [openDeleteModal, setOpenDeleteModal] = useState(false);
    const [selectedAddress, setSelectedAddress] = useState<Address | null>(null);
    const deleteAddressMutation = useDeleteAddress();
    const clearCheckoutAddress = useAuthStore((state) => state.clearCheckoutAddress);

    const addNewAddressHandler = () => {
        setSelectedAddress(null);
        setOpenAddressModal(true);
    };

    const deleteAddressHandler = async () => {
        try {
            if (selectedAddress?.id) {
                await deleteAddressMutation.mutateAsync(selectedAddress.id);
                clearCheckoutAddress();
                toast.success("Address deleted successfully");
                setOpenDeleteModal(false);
            }
        } catch (error: unknown) {
            console.error(error);
            toast.error(getErrorMessage(error));
        }
    };

    const noAddressExist = !addresses || addresses.length === 0;
    const isLoading = false;
    return (
        <div className={"pt-4"}>
            {noAddressExist ? (
                <div className={"p-6 rounded-lg max-w-md mx-auto flex flex-col items-center justify-center"}>
                    <FaAddressBook
                        size={50}
                        className={"text-gray-500 mb-4"}/>
                    <h1 className={"mb-2 text-slate-900 text-center font-semibold text-2xl"}>
                        No Address Added Yet
                    </h1>
                    <p className={"mb-6 text-slate-800 text-center"}>
                        Please add your address to complete purchase.
                    </p>

                    <button
                        onClick={addNewAddressHandler}
                        className={`px-4 py-2 bg-blue-600 text-white font-medium rounded-sm hover:bg-blue-700
                         transition-all`}>
                        Add Address
                    </button>
                </div>
            ) : (
                <div className={"relative p-6 rounded-lg max-w-md mx-auto"}>
                    <h1 className={"text-slate-800 text-center  font-bold text-2xl"}>
                        Select Address
                    </h1>
                    {isLoading ? (
                        <div className='lg:w-[80%] mx-auto py-5'>
                            <Skeleton/>
                        </div>
                    ) : (
                        <>
                            <div className={"space-y-4 pt-6"}>
                                <AddressList
                                    addresses={addresses}
                                    setSelectedAddress={setSelectedAddress}
                                    setOpenAddressModal={setOpenAddressModal}
                                    setOpenDeleteModal={setOpenDeleteModal}
                                />
                            </div>
                            {addresses.length > 0 && (
                                <div className={"mt-4 "}>
                                    <button
                                        onClick={addNewAddressHandler}
                                        className={`px-4 py-2 bg-blue-600 text-white font-medium rounded-sm 
                                            hover:bg-blue-700 transition-all`}>
                                        Add More
                                    </button>
                                </div>
                            )}
                        </>
                    )}
                </div>
            )}

            <AddressInfoModal
                isOpen={openAddressModal}
                setIsOpen={setOpenAddressModal}>
                <AddAddressForm
                    address={selectedAddress}
                    setOpenAddressModal={setOpenAddressModal}/>
            </AddressInfoModal>

            <DeleteAddressModal
                isOpen={openDeleteModal}
                setIsOpen={setOpenDeleteModal}
                title={"Delete Address"}
                isLoading={isLoading}
                onDeleteHandler={deleteAddressHandler}
            />
        </div>
    );
};

export default AddressInfo;
