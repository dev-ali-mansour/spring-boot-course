"use client";

import React from 'react'
import {useForm} from 'react-hook-form'
import InputField from '@/components/shared/InputField';
import {Button} from '@mui/material';
import toast from 'react-hot-toast';
import Skeleton from '@/components/shared/Skeleton';
import ErrorPage from '@/components/shared/ErrorPage';
import {getErrorMessage, RegistrationData, useCreateSeller} from '@/hooks/useQueries';
import {Oval} from "react-loader-spinner";

export interface AddSellerFormProps {
    setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

const AddSellerForm: React.FC<AddSellerFormProps> = ({setIsOpen}) => {
    const createSellerMutation = useCreateSeller();
    const isPending = createSellerMutation.isPending;
    const error = createSellerMutation.error;

    const {
        register,
        handleSubmit,
        reset,
        formState: {errors}
    } = useForm<RegistrationData>({
        mode: "onTouched",
        values: {
            firstName: "",
            lastName: "",
            username: "",
            email: "",
            password: "",
            roles: ["seller"],
        }
    });

    const saveSellerHandler = async (registrationData: RegistrationData) => {
        try {
            await createSellerMutation.mutateAsync(registrationData);
            toast.success("Seller added successfully");
            reset();
            setIsOpen(false);
        } catch (error: unknown) {
            console.error(error);
            toast.error(getErrorMessage(error));
        }
    };

    if (isPending) return <Skeleton/>
    if (error) return <ErrorPage message={getErrorMessage(error)}/>

    return (
        <div className={"py-5 relative h-full"}>
            <form className="space-y-4"
                  onSubmit={handleSubmit(saveSellerHandler)}>
                <div className={"flex md:flex-row flex-col gap-4 w-full"}>
                    <InputField
                        label={"First Name"}
                        required
                        id={"firstName"}
                        type={"text"}
                        message={"*First Name is required"}
                        min={2}
                        max={30}
                        placeholder={"Enter your first name"}
                        register={register}
                        errors={errors}/>
                </div>

                <div className={"flex md:flex-row flex-col gap-4 w-full"}>
                    <InputField
                        label={"Last Name"}
                        required
                        id={"lastName"}
                        type={"text"}
                        message={"*Last Name is required"}
                        min={2}
                        max={30}
                        placeholder={"Enter your last name"}
                        register={register}
                        errors={errors}/>
                </div>

                <div className={"flex md:flex-row flex-col gap-4 w-full"}>
                    <InputField
                        label={"UserName"}
                        required
                        id={"username"}
                        type={"text"}
                        message={"*UserName is required"}
                        min={4}
                        max={30}
                        placeholder={"Enter your username"}
                        register={register}
                        errors={errors}/>
                </div>

                <div className={"flex md:flex-row flex-col gap-4 w-full"}>
                    <InputField
                        label={"Email"}
                        required
                        id={"email"}
                        type={"email"}
                        message={"*Email is required"}
                        placeholder={"Enter your email"}
                        register={register}
                        errors={errors}/>
                </div>

                <div className={"flex md:flex-row flex-col gap-4 w-full"}>
                    <InputField
                        label={"Password"}
                        required
                        id={"password"}
                        type={"password"}
                        message={"*Password is required"}
                        placeholder={"Enter your password"}
                        register={register}
                        errors={errors}/>
                </div>

                <div className={"flex w-full justify-between items-center absolute bottom-14"}>
                    <Button disabled={isPending}
                            onClick={() => setIsOpen(false)}
                            variant="outlined"
                            className={"text-white py-2.5 px-4 text-sm font-medium"}>
                        Cancel
                    </Button>

                    <Button
                        disabled={isPending}
                        type={"submit"}
                        variant={"contained"}
                        color={"primary"}
                        className={"bg-custom-blue text-white py-2.5 px-4 text-sm font-medium"}>
                        {isPending ? (
                            <div className={"flex gap-2 items-center"}>
                                <Oval
                                    visible={true}
                                    height={"20"}
                                    width={"20"}
                                    color={"#FFFFFF"}
                                    ariaLabel={"oval-loading"}
                                    wrapperStyle={{}}
                                    wrapperClass={""}
                                />
                                Loading...
                            </div>
                        ) : (
                            "Save"
                        )}
                    </Button>
                </div>
            </form>
        </div>
    )
}

export default AddSellerForm;