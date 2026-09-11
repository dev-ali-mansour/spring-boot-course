"use client";

import React, {useState} from 'react'
import {useForm} from 'react-hook-form'
import InputField from '@/components/shared/InputField';
import {Button} from '@mui/material';
import toast from 'react-hot-toast';
import SelectTextField from '@/components/shared/SelectTextField';
import Skeleton from '@/components/shared/Skeleton';
import ErrorPage from '@/components/shared/ErrorPage';
import {useAuthStore} from '@/store';
import {getErrorMessage, useCategories, useCreateProduct, useUpdateProduct} from '@/hooks/useQueries';
import {Category, Product} from '@/types';
import {Oval} from "react-loader-spinner";

export interface ProductFormData {
    name: string;
    price: number | string;
    quantity: number | string;
    discount?: number | string;
    specialPrice?: number | string;
    description: string;

    [key: string]: unknown;
}

export interface AddProductFormProps {
    setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
    product?: Product | null;
    isForUpdate?: boolean;
}

const AddProductForm: React.FC<AddProductFormProps> = ({setIsOpen, product, isForUpdate = false}) => {
    const user = useAuthStore((state) => state.user);
    const isAdmin = !!(user && user?.roles?.includes("ROLE_ADMIN"));

    const createProductMutation = useCreateProduct(isAdmin);
    const updateProductMutation = useUpdateProduct(isAdmin);
    const {data: categoriesData, isLoading: isCategoriesLoading, error: categoriesError} = useCategories();
    const categories = categoriesData?.content || [];

    const [selectedCategory, setSelectedCategory] = useState<Category | null>(null);
    const isPending = createProductMutation.isPending || updateProductMutation.isPending;
    const activeCategory = selectedCategory || (categories && categories.length > 0 ? categories[0] : null);

    const {
        register,
        handleSubmit,
        reset,
        formState: {errors}
    } = useForm<ProductFormData>({
        mode: "onTouched",
        values: (isForUpdate && product) ? {
            name: product?.name || "",
            price: product?.price ?? "",
            quantity: product?.quantity ?? "",
            discount: product?.discount ?? "",
            description: product?.description || "",
        } : {
            name: "",
            price: "",
            quantity: "",
            discount: "",
            description: "",
        }
    });

    const saveProductHandler = async (data: ProductFormData) => {
        try {
            if (!isForUpdate) {
                const catId = activeCategory?.id;
                if (!catId) {
                    toast.error("Please select a category");
                    return;
                }
                await createProductMutation.mutateAsync({
                    categoryId: catId,
                    productData: data,
                });
                toast.success("Product added successfully");
            } else {
                await updateProductMutation.mutateAsync({id: product?.id, productData: data});
                toast.success("Product updated successfully");
            }
            reset();
            setIsOpen(false);
        } catch (error: unknown) {
            console.error(error);
            toast.error(getErrorMessage(error));
        }
    };

    if (isCategoriesLoading) return <Skeleton/>
    if (categoriesError) return <ErrorPage message={getErrorMessage(categoriesError)}/>

    return (
        <div className={"py-5 relative h-full"}>
            <form className="space-y-4"
                  onSubmit={handleSubmit(saveProductHandler)}>
                <div className={"flex md:flex-row flex-col gap-4 w-full"}>
                    <InputField
                        label={"Product Name"}
                        required
                        id={"name"}
                        type={"text"}
                        message="This field is required*"
                        register={register}
                        placeholder={"Product Name"}
                        errors={errors}
                    />

                    {!isForUpdate && (
                        <SelectTextField
                            label={"Select Categories"}
                            select={activeCategory}
                            setSelect={setSelectedCategory}
                            lists={categories}
                        />
                    )}
                </div>

                <div className={"flex md:flex-row flex-col gap-4 w-full"}>
                    <InputField
                        label={"Price"}
                        required
                        id={"price"}
                        type={"number"}
                        step={"0.01"}
                        min={0}
                        message={"This field is required*"}
                        placeholder={"Product Price"}
                        register={register}
                        errors={errors}
                    />

                    <InputField
                        label={"Quantity"}
                        required
                        id={"quantity"}
                        type={"number"}
                        step={"1"}
                        min={0}
                        message={"This field is required*"}
                        register={register}
                        placeholder={"Product Quantity"}
                        errors={errors}
                    />
                </div>
                <div className={"flex md:flex-row flex-col gap-4 w-full"}>
                    <InputField
                        label={"Discount"}
                        id={"discount"}
                        type={"number"}
                        step={"0.01"}
                        min={0}
                        max={100}
                        message={"This field is required*"}
                        placeholder={"Product Discount"}
                        register={register}
                        errors={errors}
                    />
                    {/* <InputField
                        label={"Special Price"}
                        id={"specialPrice"}
                        type={"number"}
                        step={"0.01"}
                        min={0}
                        message={"This field is required*"}
                        placeholder={"Product Special Price"}
                        register={register}
                        errors={errors}
                    />*/}
                </div>

                <div className={"flex flex-col gap-2 w-full"}>
                    <label htmlFor={"desc"}
                           className={"font-semibold text-sm text-slate-800"}>
                        Description
                    </label>

                    <textarea
                        rows={5}
                        placeholder={"Add product description...."}
                        className={`px-4 py-2 w-full border outline-hidden bg-transparent text-slate-800 
                        rounded-md ${
                            errors["description"]?.message ? "border-red-500" : "border-slate-700"
                        }`}
                        maxLength={255}
                        {...register("description", {
                            required: {value: true, message: "Description is required"},
                        })}
                    />

                    {errors["description"]?.message && (
                        <p className={"text-sm font-semibold text-red-600 mt-0"}>
                            {errors["description"]?.message as string}
                        </p>
                    )}
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

export default AddProductForm