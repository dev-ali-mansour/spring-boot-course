import React from "react";
import {useForm} from "react-hook-form";
import {Link, useNavigate} from "react-router-dom";
import InputField from "../shared/InputField.tsx";
import {getErrorMessage, RegistrationData, useRegister} from "../../hooks/useQueries.ts";
import toast from "react-hot-toast";
import {FaUserPlus} from "react-icons/fa";
import {Oval} from "react-loader-spinner";

const Register: React.FC = () => {
    const navigate = useNavigate();
    const registerMutation = useRegister();

    const {
        register,
        handleSubmit,
        reset,
        formState: {errors},
    } = useForm<RegistrationData>({
        mode: "onTouched",
    });

    const registrationHandler = async (registrationData: RegistrationData) => {
        try {
            const response = await registerMutation.mutateAsync(registrationData);
            reset();
            toast.success(response?.message || "User Registered Successfully! Please login to continue.",
                {duration: 5000}
            );
            navigate("/login");
        } catch (error: unknown) {
            console.error(error);
            toast.error(getErrorMessage(error));
        }
    };

    return (
        <div className={" min-h-[calc(100vh-64px)] flex justify-center items-center"}>
            <form
                onSubmit={handleSubmit(registrationHandler)}
                className={"sm:w-112.5 w-90 shadow-custom py-8 sm:px-8 px-4 rounded-md"}>
                <div className={"flex flex-col items-center justify-center space-y-4"}>
                    <FaUserPlus className={"text-slate-800 text-5xl"}/>
                    <h1 className={"text-slate-800 text-center font-montserrat lg:text-3xl text-2xl font-bold"}>
                        Register Here
                    </h1>
                </div>
                <hr className={"mt-2 mb-5 text-black"}/>
                <div className={"flex flex-col gap-3"}>
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
                        errors={errors}
                    />
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
                        errors={errors}
                    />
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
                        errors={errors}
                    />
                    <InputField
                        label={"Email"}
                        required
                        id={"email"}
                        type={"email"}
                        message={"*Email is required"}
                        placeholder={"Enter your email"}
                        register={register}
                        errors={errors}
                    />

                    <InputField
                        label={"Password"}
                        required
                        id={"password"}
                        type={"password"}
                        message={"*Password is required"}
                        placeholder={"Enter your password"}
                        register={register}
                        errors={errors}
                    />
                </div>

                <button
                    disabled={registerMutation.isPending}
                    className={`bg-button-gradient flex gap-2 items-center justify-center font-semibold 
                                text-white w-full py-2 hover:text-slate-400 transition-colors 
                                duration-100 rounded-xs my-3 cursor-pointer`}
                    type={"submit"}>
                    {registerMutation.isPending ? (
                        <>
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
                        </>
                    ) : (
                        "Register"
                    )}
                </button>

                <p className={"text-center text-sm text-slate-700 mt-6"}>
                    Already have an account?
                    <Link
                        className={"font-semibold underline hover:text-black"}
                        to="/login">
                        <span className={"ml-1"}>Login</span>
                    </Link>
                </p>
            </form>
        </div>
    );
};

export default Register;