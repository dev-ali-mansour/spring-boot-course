import React from "react";
import {FieldErrors, FieldValues, UseFormRegister} from "react-hook-form";

export interface InputFieldProps {
    label: string;
    id: string;
    type?: string;
    errors: FieldErrors;
    register: UseFormRegister<FieldValues>;
    required?: boolean;
    message?: string;
    className?: string;
    min?: number;
    max?: number;
    placeholder?: string;
}

const InputField: React.FC<InputFieldProps> = (
    {
        label,
        id,
        type,
        errors,
        register,
        required,
        message,
        className,
        min,
        max,
        placeholder,
    }: InputFieldProps
) => {
    return (
        <div className={"flex flex-col gap-1 w-full"}>
            <label
                htmlFor={id}
                className={`${className ? className : ""} font-semibold text-sm text-slate-800`}>
                {label}
            </label>

            <input
                type={type}
                id={id}
                placeholder={placeholder}
                className={`${className ? className : ""} px-2 py-2 border outline-none bg-transparent 
                text-slate-800 rounded-md ${errors[id]?.message ? "border-red-500" : "border-slate-700"}`}
                {...register(id, {
                        required: required ? {value: true, message: message || "This field is required"} : false,
                        minLength: min
                            ? {value: min, message: `Minimum ${min} character is required`}
                            : undefined,
                        maxLength: max
                            ? {value: max, message: `Maximum ${max} character is allowed`}
                            : undefined,
                        pattern:
                            type === "email"
                                ? {
                                    value: /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/,
                                    message: "Invalid email"
                                } : type === "password"
                                    ? {
                                        value: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,120}$/,
                                        message: `Password must be at least 8 characters,at most 120 characters, 
                                                    contain at least one lowercase, at least one uppercase, 
                                                    at least one digit, and at least one special character!`
                                    } : type === "url"
                                        ? {
                                            value: /^(https?:\/\/)?(([a-zA-Z0-9\u00a1-\uffff-]+\.)+[a-zA-Z\u00a1-\uffff]{2,})(:\d{2,5})?(\/[^\s]*)?$/,
                                            message: "Please enter a valid URL"
                                        } : undefined
                    }
                )}/>
            {errors[id]?.message && (
                <p className={"text-sm font=semibold text-red-600 mt-0"}>
                    {errors[id]?.message as string}
                </p>
            )}
        </div>
    );
};

export default InputField;
