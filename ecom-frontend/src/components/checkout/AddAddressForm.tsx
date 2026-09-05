import React from "react";
import InputField from "../shared/InputField.tsx";
import {Oval} from "react-loader-spinner";
import {useForm} from "react-hook-form";
import {getErrorMessage, useAddUpdateAddress} from "../../hooks/useQueries.ts";
import toast from "react-hot-toast";
import {FaAddressCard} from "react-icons/fa";
import {Address} from "../../types";

export interface AddAddressFormProps {
    address?: Address | null;
    setOpenAddressModal: React.Dispatch<React.SetStateAction<boolean>>;
}

const AddAddressForm: React.FC<AddAddressFormProps> = ({address, setOpenAddressModal}) => {
    const addUpdateAddressMutation = useAddUpdateAddress();

    const {
        register,
        handleSubmit,
        reset,
        formState: {errors},
    } = useForm<Address>({
        mode: "onTouched",
        values: address || {
            buildingName: "",
            city: "",
            street: "",
            state: "",
            pinCode: "",
            country: ""
        }
    });

    const onSaveAddressHandler = async (data: Address) => {
        try {
            await addUpdateAddressMutation.mutateAsync({
                addressId: address?.id,
                addressData: data,
            });
            reset();
            toast.success("Address saved successfully");
            setOpenAddressModal(false);
        } catch (error: unknown) {
            console.error(error);
            toast.error(getErrorMessage(error));
        }
    };

    return (
        <div className={""}>
            <form
                onSubmit={handleSubmit(onSaveAddressHandler)}
                className={""}>
                <div
                    className={"flex justify-center items-center mb-4 font-semibold text-2xl text-slate-800 py-2 px-4"}>
                    <FaAddressCard className={"mr-2 text-2xl"}/>
                    {address ? "Update Address" : "Add Address"}
                </div>
                <div className={"flex flex-col gap-4"}>
                    <InputField
                        label="Building Name"
                        required
                        id="buildingName"
                        type="text"
                        message="*Building Name is required"
                        placeholder="Enter Building Name"
                        register={register}
                        errors={errors}
                    />

                    <InputField
                        label="City"
                        required
                        id="city"
                        type="text"
                        message="*City is required"
                        placeholder="Enter City"
                        register={register}
                        errors={errors}
                    />

                    <InputField
                        label="State"
                        required
                        id="state"
                        type="text"
                        message="*State is required"
                        placeholder="Enter State"
                        register={register}
                        errors={errors}
                    />

                    <InputField
                        label="Pin Code"
                        required
                        id="pinCode"
                        type="text"
                        message="*Pin Code is required"
                        placeholder="Enter Pin Code"
                        register={register}
                        errors={errors}/>
                    <InputField
                        label="Street"
                        required
                        id="street"
                        type="text"
                        message="*Street is required"
                        placeholder="Enter Street"
                        register={register}
                        errors={errors}/>

                    <InputField
                        label="Country"
                        required
                        id="country"
                        type="text"
                        message="*Country is required"
                        placeholder="Enter Country"
                        register={register}
                        errors={errors}/>
                </div>

                <button
                    disabled={addUpdateAddressMutation.isPending}
                    className={`text-white bg-custom-blue px-4 py-2 rounded-md mt-4 flex items-center 
                                justify-center min-w-20 cursor-pointer`}
                    type={"submit"}>
                    {addUpdateAddressMutation.isPending ? (
                        <div className={"flex items-center gap-2"}>
                            <Oval
                                visible={true}
                                height="20"
                                width="20"
                                color="#FFFFFF"
                                ariaLabel="oval-loading"
                                wrapperStyle={{}}
                                wrapperClass=""
                            />
                            Loading...
                        </div>
                    ) : (
                        <>Save</>
                    )}
                </button>
            </form>
        </div>
    );
};

export default AddAddressForm;
