import {GridColDef} from "@mui/x-data-grid";
import {FaEdit, FaEye, FaImage, FaTrashAlt} from "react-icons/fa";
import {Category, Product, User} from "@/types";
import {Order} from "@/types/Order";
import {MdOutlineEmail} from "react-icons/md";

export const adminProductTableColumn = (
    handleEdit: (product: Product) => void,
    handleDelete: (product: Product) => void,
    handleImageUpload: (product: Product) => void,
    handleProductView: (product: Product) => void,
) => [
    {
        disableColumnMenu: true,
        sortable: false,
        field: "id",
        headerName: "Product ID",
        minWidth: 80,
        flex: 0.5,
        headerAlign: "center",
        align: "center",
        editable: false,
        headerClassName: "text-black font-semibold border",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <span className="text-center">Product ID</span>,
    },
    {
        disableColumnMenu: true,
        field: "name",
        headerName: "Product Name",
        align: "center",
        minWidth: 180,
        flex: 1.2,
        editable: false,
        sortable: false,
        headerAlign: "center",
        headerClassName: "text-black font-semibold text-center border ",
        cellClassName: "text-slate-700 font-normal border text-center",
        renderHeader: () => <span>Product Name</span>,
    },

    {
        disableColumnMenu: true,
        field: "price",
        headerName: "Price",
        minWidth: 100,
        flex: 0.8,
        headerAlign: "center",
        align: "center",
        editable: false,
        headerClassName: "text-black font-semibold border",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <span className="text-center">Price</span>,
    },
    {
        disableColumnMenu: true,
        field: "quantity",
        headerName: "Quantity",
        minWidth: 90,
        flex: 0.6,
        headerAlign: "center",
        align: "center",
        editable: false,
        headerClassName: "text-black font-semibold border",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <span className="text-center">Quantity</span>,
    },
    {
        disableColumnMenu: true,
        field: "specialPrice",
        headerName: "Price",
        minWidth: 110,
        flex: 0.8,
        headerAlign: "center",
        align: "center",
        editable: false,
        headerClassName: "text-black font-semibold border",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => (
            <span className="text-center">Special Price</span>
        ),
    },
    {
        sortable: false,
        field: "description",
        headerName: "Description",
        headerAlign: "center",
        align: "center",
        minWidth: 150,
        flex: 1,
        editable: false,
        disableColumnMenu: true,
        headerClassName: "text-black font-semibold border ",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <span className="ps-10">Description</span>,
    },
    {
        sortable: false,
        field: "image",
        headerName: "Image",
        headerAlign: "center",
        align: "center",
        minWidth: 120,
        flex: 0.8,
        editable: false,
        disableColumnMenu: true,
        headerClassName: "text-black font-semibold border ",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <span className="ps-10">Image</span>,
    },

    {
        field: "action",
        headerName: "Action",
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold text-center",
        cellClassName: "text-slate-700 font-normal",
        sortable: false,
        minWidth: 400,
        flex: 1.8,
        renderHeader: () => <span>Action</span>,
        renderCell: (params) => {
            return (
                <div className="flex justify-center items-center space-x-2 h-full pt-2">
                    <button
                        onClick={() => handleImageUpload(params.row)}
                        className="flex items-center bg-green-500 hover:bg-green-600 text-white px-4 h-9 rounded-md"
                    >
                        <FaImage className="mr-2"/>
                        Image
                    </button>
                    <button
                        onClick={() => handleEdit(params.row)}
                        className="flex items-center bg-blue-500 text-white px-4 h-9 rounded-md "
                    >
                        <FaEdit className="mr-2"/>
                        Edit
                    </button>

                    <button
                        onClick={() => handleDelete(params.row)}
                        className="flex items-center bg-red-500 text-white px-4   h-9 rounded-md"
                    >
                        <FaTrashAlt className="mr-2"/>
                        Delete
                    </button>
                    <button
                        onClick={() => handleProductView(params.row)}
                        className="flex items-center bg-slate-800 text-white px-4   h-9 rounded-md"
                    >
                        <FaEye className="mr-2"/>
                        View
                    </button>
                </div>
            );
        },
    },
] as GridColDef[];

export const adminOrderTableColumn = (handleEdit: (order: Order) => void): GridColDef[] => [
    {
        field: "id",
        headerName: "Order ID",
        minWidth: 90,
        flex: 0.6,
        headerAlign: "center",
        align: "center",
        editable: false,
        headerClassName: "text-black font-semibold border",
        cellClassName: "text-slate-700 font-normal border",
        sortable: false,
        disableColumnMenu: true,
        renderHeader: () => <span className={"text-center"}>Order ID</span>,
    },
    {
        field: "email",
        headerName: "Email",
        align: "center",
        minWidth: 200,
        flex: 1.5,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold text-center border ",
        cellClassName: "text-slate-700 font-normal border text-center",
        sortable: false,
        disableColumnMenu: true,
        renderHeader: () => <span>Email</span>,
    },
    {
        field: "totalAmount",
        headerName: "Total Amount",
        align: "center",
        minWidth: 120,
        flex: 0.9,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold text-center border ",
        cellClassName: "text-slate-700 font-normal border text-center",
        sortable: false,
        disableColumnMenu: true,
        renderHeader: () => <span>Total Amount</span>,
    },
    {
        field: "status",
        headerName: "Status",
        align: "center",
        minWidth: 120,
        flex: 0.9,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold text-center border ",
        cellClassName: "text-slate-700 font-normal border text-center",
        sortable: false,
        disableColumnMenu: true,
        renderHeader: () => <span>Status</span>,
    },
    {
        field: "date",
        headerName: "Order Date",
        align: "center",
        minWidth: 140,
        flex: 1,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold text-center border ",
        cellClassName: "text-slate-700 font-normal border text-center",
        sortable: false,
        disableColumnMenu: true,
        renderHeader: () => <span>Order Date</span>,
    },
    {

        field: "action",
        headerName: "Action",
        minWidth: 120,
        flex: 0.8,
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold text-center",
        cellClassName: "text-slate-700 font-normal",
        sortable: false,
        disableColumnMenu: true,
        renderHeader: () => <span>Action</span>,
        renderCell: (params) => {
            return (
                <div className='flex justify-center items-center space-x-2 h-full pt-2'>
                    <button
                        onClick={() => handleEdit(params.row)}
                        className={"flex items-center bg-blue-500 text-white px-4 h-9 rounded-md"}>
                        <FaEdit className={"mr-2"}/>
                        Edit
                    </button>
                </div>
            );
        },
    },
] as GridColDef[];

export const categoryTableColumns = (handleEdit: (category: Category) => void, handleDelete: (category: Category) => void) => [
    {
        sortable: false,
        disableColumnMenu: true,
        field: "id",
        headerName: "Category ID",
        minWidth: 90,
        flex: 0.6,
        headerAlign: "center",
        align: "center",
        editable: false,
        headerClassName: "text-black font-semibold border",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <span className="text-center">Category ID</span>,
    },
    {
        disableColumnMenu: true,
        field: "name",
        headerName: "Category Name",
        align: "center",
        minWidth: 200,
        flex: 1.5,
        editable: false,
        sortable: false,
        headerAlign: "center",
        headerClassName: "text-black font-semibold text-center border ",
        cellClassName: "text-slate-700 font-normal border text-center",
        renderHeader: () => <span>Category Name</span>,
    },

    {
        field: "action",
        headerName: "Action",
        headerAlign: "center",
        editable: false,
        headerClassName: "text-black font-semibold text-center",
        cellClassName: "text-slate-700 font-normal",
        sortable: false,
        minWidth: 220,
        flex: 1,
        renderHeader: () => <span>Action</span>,
        renderCell: (params) => {
            return (
                <div className="flex justify-center space-x-2 h-full pt-2">
                    <button
                        onClick={() => handleEdit(params.row)}
                        className="flex items-center bg-blue-500 text-white px-4 h-9 rounded-md "
                    >
                        <FaEdit className="mr-2"/>
                        Edit
                    </button>

                    <button
                        onClick={() => handleDelete(params.row)}
                        className="flex items-center bg-red-500 text-white px-4   h-9 rounded-md">
                        <FaTrashAlt className="mr-2"/>
                        Delete
                    </button>
                </div>
            );
        },
    },
] as GridColDef[];

export const sellerTableColumns =  [
    {
        disableColumnMenu: true,
        field: "id",
        headerName: "ID",
        minWidth: 70,
        flex: 0.5,
        headerAlign: "center",
        align: "center",
        editable: false,

        headerClassName: "text-black font-semibold border",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <span className="text-center">ID</span>,
    },
    {
        disableColumnMenu: true,
        field: "name",
        headerName: "Full Name",
        minWidth: 180,
        flex: 1,
        headerAlign: "center",
        align: "center",
        editable: false,
        sortable: false,
        headerClassName: "text-black font-semibold border",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <span className="text-center">Full Name</span>,
    },
    {
        disableColumnMenu: true,
        field: "username",
        headerName: "UserName",
        minWidth: 180,
        flex: 1,
        headerAlign: "center",
        align: "center",
        editable: false,
        sortable: false,
        headerClassName: "text-black font-semibold border",
        cellClassName: "text-slate-700 font-normal border",
        renderHeader: () => <span className="text-center">UserName</span>,
    },
    {
        disableColumnMenu: true,
        field: "email",
        headerName: "Email",
        align: "center",
        minWidth: 220,
        flex: 1.5,
        editable: false,
        sortable: false,
        headerAlign: "center",
        headerClassName: "text-black font-semibold text-center border ",
        cellClassName: "text-slate-700 font-normal border text-center",
        renderHeader: () => <span>Email</span>,
        renderCell: (params) => {
            return (
                <div className="flex items-center justify-center gap-1">
          <span>
            <MdOutlineEmail className="text-slate-700 text-lg"/>
          </span>
                    <span>{params?.row?.email}</span>
                </div>
            );
        },
    },
] as GridColDef[];