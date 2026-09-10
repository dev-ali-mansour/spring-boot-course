import React from "react";
import {FaCloudUploadAlt} from "react-icons/fa";
import {Button} from "@mui/material";
import {Oval} from "react-loader-spinner";
import {getErrorMessage, useUpdateProductImage} from "@/hooks/useQueries";
import {useAuthStore} from "@/store";
import toast from "react-hot-toast";
import {Product} from "@/types";


export interface ImageUploadFormProps {
    product?: Product | null;
    setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
}

const ImageUploadForm: React.FC<ImageUploadFormProps> = ({product, setIsOpen}) => {
    const fileInputRef = React.useRef<HTMLInputElement>(null);
    const [previewImage, setPreviewImage] = React.useState<string | null>(null);
    const [selectedFile, setSelectedFile] = React.useState<File | null>(null);

    const user = useAuthStore((state) => state.user);
    const isAdmin = !!(user && user?.roles?.includes("ROLE_ADMIN"));
    const updateProductImageMutation = useUpdateProductImage(isAdmin);

    const addNewImageHandler = async (event: React.SubmitEvent<HTMLFormElement>) => {
        event.preventDefault();
        if (!selectedFile) {
            toast.error("Please select an image file to upload");
            return;
        }
        if (!product?.id) {
            toast.error("Invalid product selected");
            return;
        }
        const formData = new FormData();
        formData.append("image", selectedFile);

        try {
            await updateProductImageMutation.mutateAsync({
                productId: product.id,
                formData: formData,
            });
            toast.success("Image uploaded successfully");
            setIsOpen(false);
        } catch (error: unknown) {
            console.error(error);
            toast.error(getErrorMessage(error));
        }
    };

    const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files?.[0];
        if (file && ["image/jpeg", "image/jpg", "image/png"].includes(file.type)) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setPreviewImage(reader.result as string);
            };
            reader.readAsDataURL(file);
            setSelectedFile(file);
        } else {
            toast.error("Please select a valid image file (JPEG, JPG, PNG)");
            setPreviewImage(null);
            setSelectedFile(null);
        }
    };

    const handleClearImage = () => {
        setPreviewImage(null);
        setSelectedFile(null);
        if (fileInputRef.current) {
            fileInputRef.current.value = "";
        }
    };

    return (
        <div className={"py-5 relative h-full"}>
            <form className={"space-y-4"} onSubmit={addNewImageHandler}>
                <div className={"flex flex-col gap-4 w-full"}>
                    <label className={`flex items-center gap-2 cursor-pointer text-custom-blue border 
                        border-dashed border-custom-blue rounded-md p-3 w-full justify-center`}>
                        <FaCloudUploadAlt size={"24"}/>
                        <span>Upload Product Image</span>
                        <input type="file"
                               ref={fileInputRef}
                               onChange={handleImageChange}
                               className={"hidden"}
                               accept={".jpeg, .jpg, .png"}/>
                    </label>

                    {previewImage && (
                        <div>
                            <img src={previewImage} alt={"Image Preview"} className={"h-60 rounded-md mb-2"}/>

                            <button
                                onClick={handleClearImage}
                                className={`bg-rose-600 text-white px-2 py-1 rounded-md hover:bg-rose-700 
                                    cursor-pointer`}>
                                Clear Image
                            </button>
                        </div>
                    )}
                </div>

                <div className={"flex w-full justify-between items-center absolute bottom-14"}>
                    <Button disabled={updateProductImageMutation.isPending}
                            onClick={() => setIsOpen(false)}
                            variant={"outlined"}
                            className={"text-white py-2.5 px-4 text-sm font-medium"}>
                        Cancel
                    </Button>

                    <Button
                        disabled={updateProductImageMutation.isPending}
                        type={"submit"}
                        variant={"contained"}
                        color={"primary"}
                        className={"bg-custom-blue text-white py-2.5 px-4 text-sm font-medium"}>
                        {updateProductImageMutation.isPending ? (
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
                            "Update"
                        )}
                    </Button>
                </div>
            </form>
        </div>
    );
};

export default ImageUploadForm;