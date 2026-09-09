import {GridColDef} from "@mui/x-data-grid";
import {FaEdit} from "react-icons/fa";

export const adminOrderTableColumn = (handleEdit: any): GridColDef[] => [
    {
        field: "id",
        headerName: "Order ID",
        minWidth: 180,
        headerAlign: "center",
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
        width: 250,
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
        width: 200,
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
        width: 200,
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
        width: 200,
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
        width: 250,
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
