import React from "react";
import {useForm} from "react-hook-form";
import {Link, useNavigate} from "react-router-dom";
import {useAuthStore} from "../../store/useAuthStore.ts";
import {AiOutlineLogin} from "react-icons/ai";
import InputField from "../shared/InputField.tsx";
import {useSignIn} from "../../hooks/useQueries.ts";

const SignIn: React.FC = () => {

    const navigate = useNavigate();
    const setUser = useAuthStore((state) => state.setUser);
    const signInMutation = useSignIn();

    const {
        register,
        handleSubmit,
        reset,
        formState: {errors},
    } = useForm({
        mode: "onTouched",
    });

    const signInHandler = async (data: { username?: String, password?: String }) => {
        console.log("Sign In data:", data);
    };

    return (
        <div className={" min-h-[calc(100vh-64px)] flex justify-center items-center"}>
            <form
                onSubmit={handleSubmit(signInHandler)}
                className={"sm:w-112.5 w-90 shadow-custom py-8 sm:px-8 px-4 rounded-md"}>
                <div className={"flex flex-col items-center justify-center space-y-4"}>
                    <AiOutlineLogin className={"text-slate-800 text-5xl"}/>
                    <h1 className={"text-slate-800 text-center font-montserrat lg:text-3xl text-2xl font-bold"}>
                        Sign In Here
                    </h1>
                </div>
                <hr className={"mt-2 mb-5 text-black"}/>
                <div className={"flex flex-col gap-3"}>
                    <InputField
                        label={"UserName"}
                        required
                        id={"username"}
                        type={"text"}
                        message={"*UserName is required"}
                        placeholder={"Enter your username"}
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
                    disabled={signInMutation.isPending}
                    className={`bg-button-gradient flex gap-2 items-center justify-center font-semibold 
                                text-white w-full py-2 hover:text-slate-400 transition-colors 
                                duration-100 rounded-xs my-3 cursor-pointer`}
                    type={"submit"}>
                    {signInMutation.isPending ? "Loading..." : "Sign In"}
                </button>

                <p className={"text-center text-sm text-slate-700 mt-6"}>
                    Don't have an account?
                    <Link
                        className={"font-semibold underline hover:text-black"}
                        to="/signup">
                        <span className={"ml-1"}>SignUp</span>
                    </Link>
                </p>
            </form>
        </div>
    );
};

export default SignIn;
