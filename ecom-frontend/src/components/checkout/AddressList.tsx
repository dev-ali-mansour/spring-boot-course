import React from "react";
import {Address} from "../../types";
import {FaBuilding, FaCheckCircle, FaEdit, FaStreetView, FaTrash} from "react-icons/fa";
import {MdLocationCity, MdPinDrop, MdPublic} from "react-icons/md";
import {useAuthStore} from "../../store";

interface AddressListProps {
    addresses: Address[];
    setSelectedAddress: React.Dispatch<React.SetStateAction<Address | null>>;
    setOpenAddressModal: React.Dispatch<React.SetStateAction<boolean>>;
    setOpenDeleteModal: React.Dispatch<React.SetStateAction<boolean>>;
}

const AddressList: React.FC<AddressListProps> = (
    {addresses, setSelectedAddress, setOpenAddressModal, setOpenDeleteModal}
) => {
    const {selectedUserCheckoutAddress, setSelectedUserCheckoutAddress} = useAuthStore();

    const handleAddressSelection = (address: Address) => {
        setSelectedUserCheckoutAddress(address);
    };

    const onEditButtonHandler = (address: Address) => {
        setSelectedAddress(address);
        setOpenAddressModal(true);

    };
    const onDeleteButtonHandler = (address: Address) => {
        setSelectedAddress(address);
        setOpenDeleteModal(true);
    };

    return (
        <div className={"space-y-4"}>
            {addresses.map((address) => (
                <div
                    key={address.id}
                    onClick={() => handleAddressSelection(address)}
                    className={`p-4 border rounded-md cursor-pointer relative 
                        ${selectedUserCheckoutAddress?.id === address.id
                        ? "bg-green-100"
                        : "bg-white"}`}>
                    <div className="flex items-start">
                        <div className="space-y-1">
                            <div className="flex items-center ">
                                <FaBuilding size={14} className='mr-2 text-gray-600'/>
                                <p className='font-semibold'>{address.buildingName}</p>
                                {selectedUserCheckoutAddress?.id === address.id && (
                                    <FaCheckCircle className='text-green-500 ml-2'/>
                                )}
                            </div>

                            <div className="flex items-center ">
                                <FaStreetView size={17} className='mr-2 text-gray-600'/>
                                <p>{address.street}</p>
                            </div>

                            <div className="flex items-center ">
                                <MdLocationCity size={17} className='mr-2 text-gray-600'/>
                                <p>{address.city}, {address.state}</p>
                            </div>

                            <div className="flex items-center ">
                                <MdPinDrop size={17} className='mr-2 text-gray-600'/>
                                <p>{address.pinCode}</p>
                            </div>

                            <div className="flex items-center ">
                                <MdPublic size={17} className='mr-2 text-gray-600'/>
                                <p>{address.country}</p>
                            </div>
                        </div>
                    </div>

                    <div className="flex gap-3 absolute top-4 right-2">
                        <button onClick={() => onEditButtonHandler(address)}>
                            <FaEdit size={18} className="text-teal-700"/>
                        </button>
                        <button onClick={() => onDeleteButtonHandler(address)}>
                            <FaTrash size={17} className="text-rose-600"/>
                        </button>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default AddressList;
