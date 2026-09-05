import React from "react";
import {useForm} from "react-hook-form";
import {Link, useNavigate} from "react-router-dom";
import {useAuthStore} from "../../store";
import {AiOutlineLogin} from "react-icons/ai";
import InputField from "../shared/InputField.tsx";
import {getErrorMessage, LoginCredentials, useLogin} from "../../hooks/useQueries.ts";
import toast from "react-hot-toast";
import {Oval,} from "react-loader-spinner";

const Login: React.FC = () => {

        const navigate = useNavigate();
        const setUser = useAuthStore((state) => state.setUser);
        const loginMutation = useLogin();

        const {
            register,
            handleSubmit,
            reset,
            formState: {errors},
        } = useForm<LoginCredentials>({
            mode: "onTouched",
        });

        const loginHandler = async (credentials: LoginCredentials) => {
            try {
                const userData = await loginMutation.mutateAsync(credentials);
                setUser(userData);
                reset();
                toast.success("Successful Login");
                navigate("/");
            } catch (error) {
                console.error(error);
                toast.error(getErrorMessage(error));
            }
        };

        return (
            <div className={" min-h-[calc(100vh-64px)] flex justify-center items-center"}>
                <form
                    onSubmit={handleSubmit(loginHandler)}
                    className={"sm:w-112.5 w-90 shadow-custom py-8 sm:px-8 px-4 rounded-md"}>
                    <div className={"flex flex-col items-center justify-center space-y-4"}>
                        <AiOutlineLogin className={"text-slate-800 text-5xl"}/>
                        <h1 className={"text-slate-800 text-center font-montserrat lg:text-3xl text-2xl font-bold"}>
                            Login Here
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
                        disabled={loginMutation.isPending}
                        className={`bg-button-gradient flex gap-2 items-center justify-center font-semibold 
                                text-white w-full py-2 hover:text-slate-400 transition-colors 
                                duration-100 rounded-xs my-3 cursor-pointer`}
                        type={"submit"}>
                        {loginMutation.isPending ? (
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
                            <>Login</>
                        )}
                    </button>

                    <p className={"text-center text-sm text-slate-700 mt-6"}>
                        Don't have an account?
                        <Link
                            className={"font-semibold underline hover:text-black"}
                            to="/register">
                            <span className={"ml-1"}>Register</span>
                        </Link>
                    </p>
                </form>
            </div>
        );
    }
;

export default Login;
