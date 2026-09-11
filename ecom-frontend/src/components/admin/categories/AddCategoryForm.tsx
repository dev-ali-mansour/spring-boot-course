import {Category} from "@/types";
import React from "react";
import InputField from "@/components/shared/InputField";
import {useForm} from "react-hook-form";
import {getErrorMessage, useCreateCategory, useUpdateCategory} from "@/hooks/useQueries";
import toast from "react-hot-toast";
import {Oval} from "react-loader-spinner";

export interface CategoryFormData {
    name: string;
}

interface AddCategoryFormProps {
    setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
    category?: Category | null;
    isForUpdate?: boolean;
}

const AddCategoryForm: React.FC<AddCategoryFormProps> = ({setIsOpen, category, isForUpdate}) => {
    const createCategoryMutation = useCreateCategory();
    const updateCategoryMutation = useUpdateCategory();
    const isPending = createCategoryMutation.isPending || updateCategoryMutation.isPending;

    const {
        register,
        handleSubmit,
        reset,
        formState: {errors}
    } = useForm<CategoryFormData>({
        mode: "onTouched",
        values: (isForUpdate && category) ? {
            name: category?.name || "",
        } : {
            name: "",
        }
    });

    const addNewCategoryHandler = async (data: CategoryFormData) => {
        try {
            if (isForUpdate && category) {
                await updateCategoryMutation.mutateAsync({id: category.id, categoryData: data});
                toast.success("Category updated successfully");
            } else {
                await createCategoryMutation.mutateAsync(data);
                toast.success("Category created successfully");
            }
            reset();
            setIsOpen(false);
        } catch (error: unknown) {
            console.error(error);
            toast.error(getErrorMessage(error));
        }
    };

    return (
        <div className={"py-5 relative h-full"}>
            <form className={"space-y-4"} onSubmit={handleSubmit(addNewCategoryHandler)}>
                <div className={"flex md:flex-row flex-col gap-4 w-full"}>
                    <InputField
                        label={"Category Name"}
                        required
                        id={"name"}
                        type={"text"}
                        message="This field is required*"
                        register={register}
                        placeholder={"Product Name"}
                        errors={errors}
                    />
                </div>

                <div className={"flex w-full justify-between items-center absolute bottom-14"}>
                    <button
                        disabled={isPending}
                        onClick={() => setIsOpen(false)}
                        type={"button"}
                        className={`border border-borderColor rounded-[5px] font-metropolis text-textColor py-2.5
                            px-4 text-sm font-medium cursor-pointer`}>
                        Cancel
                    </button>
                    <button
                        disabled={isPending}
                        type={"submit"}
                        className={`font-metropolis rounded-[5px] bg-custom-blue hover:bg-blue-800 text-white 
                            py-2.5 px-4 text-sm font-medium cursor-pointer`}>
                        {isPending ? (
                            <div className={"flex gap-2 items-center"}>
                                <Oval
                                    visible={true}
                                    height={"20"}
                                    width={"20"}
                                    color={"#FFFFFF"}
                                    ariaLabel={"oval-loading"}
                                />
                                Loading...
                            </div>
                        ) : (
                            "Save"
                        )}
                    </button>
                </div>
            </form>
        </div>);
};

export default AddCategoryForm;