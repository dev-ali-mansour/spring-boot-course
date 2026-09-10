import React, {useState} from "react";
import {Order} from "@/types/Order";
import {useAuthStore} from "@/store";
import {getErrorMessage, useUpdateOrderStatus} from "@/hooks/useQueries";
import {Button, FormControl, FormHelperText, InputLabel, MenuItem, Select} from "@mui/material";
import {Oval} from "react-loader-spinner";
import toast from "react-hot-toast";

export interface UpdateOrderFormProps {
    setIsOpen: React.Dispatch<React.SetStateAction<boolean>>;
    selectedId?: number | null;
    selectedItem?: Order | null;
}

const ORDER_STATUSES = [
    "Accepted",
    "Pending",
    "Processing",
    "Shipped",
    "Delivered",
    "Cancelled",
];

const UpdateOrderForm: React.FC<UpdateOrderFormProps> = ({setIsOpen, selectedId, selectedItem}) => {
    const [orderStatus, setOrderStatus] = useState<string>(selectedItem?.orderStatus || "Accepted");
    const [error, setError] = useState<string | null>(null);
    const user = useAuthStore((state) => state.user);
    const isAdmin = user?.roles?.includes("ROLE_ADMIN") || false;
    const updateOrderStatusMutation = useUpdateOrderStatus(isAdmin);

    const updateOrderStatus = async (e: React.SubmitEvent<HTMLFormElement>) => {
        e.preventDefault();
        if (!orderStatus) {
            setError("Order status is required");
            return;
        }

        try {
            await updateOrderStatusMutation.mutateAsync({
                orderId: Number(selectedId),
                status: orderStatus,
            });
            toast.success("Order status updated successfully");
            setIsOpen(false);
        } catch (error: Error) {
            console.error(error);
            toast.error(getErrorMessage(error));
        }
    };
    return (
        <div className={"py-5 relative h-full"}>
            <form className={"space-y-4"} onSubmit={updateOrderStatus}>
                <FormControl fullWidth variant={"outlined"} error={!!error}>
                    <InputLabel id={"order-status-label"}>Order Status</InputLabel>
                    <Select
                        labelId="order-status-label"
                        label="Order Status"
                        value={orderStatus}
                        onChange={(e) => {
                            setOrderStatus(e.target.value)
                            setError(null);
                        }}>
                        {ORDER_STATUSES.map((status) => (
                            <MenuItem key={status} value={status}>
                                {status}
                            </MenuItem>
                        ))
                        }
                    </Select>

                    {error && <FormHelperText>{error}</FormHelperText>}
                </FormControl>

                <div className={"flex w-full justify-between items-center absolute bottom-14"}>
                    <Button
                        disabled={updateOrderStatusMutation.isPending}
                        onClick={() => setIsOpen(false)}
                        variant={"outlined"}
                        className={"text-white py-2.5 px-4 text-sm font-medium"}>
                        Cancel
                    </Button>
                    <Button
                        disabled={updateOrderStatusMutation.isPending}
                        type={"submit"}
                        variant={"contained"}
                        color={"primary"}
                        className={"bg-custom-blue text-white  py-2.5 px-4 text-sm font-medium"}>
                        {updateOrderStatusMutation.isPending ? (
                            <div className={"flex gap-2 items-center"}>
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
                        ) : ("Update")}
                    </Button>
                </div>
            </form>
        </div>
    );
};

export default UpdateOrderForm;