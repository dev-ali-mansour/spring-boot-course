import React from "react";
import {Avatar, IconButton, Menu, MenuItem} from "@mui/material";
import {Link, useNavigate} from "react-router-dom";
import {BiUser} from "react-icons/bi";
import {useAuthStore} from "../../store";
import {FaShoppingCart} from "react-icons/fa";
import {useSignOut} from "../../hooks/useQueries.ts";
import {IoExitOutline} from "react-icons/io5";
import truncateText from "../../utils/truncateText.tsx";

const UserMenu: React.FC = () => {
    const id = React.useId();
    const buttonId = `${id}-button`;
    const menuId = `${id}-menu`;
    const [anchorEl, setAnchorEl] = React.useState<null | HTMLElement>(null);
    const open = Boolean(anchorEl);
    const {user, clearUser} = useAuthStore();
    const signOutMutation = useSignOut();
    const navigate = useNavigate();

    const handleClick = (event: React.MouseEvent<HTMLButtonElement>) => {
        setAnchorEl(event.currentTarget);
    };
    const handleClose = () => {
        setAnchorEl(null);
    };

    const signOutHandler = async () => {
        try {
            await signOutMutation.mutateAsync();
        } catch (error) {
            console.log("Failed to sign out on the server", error);
        }
        clearUser();
        navigate("/signin");
    }

    return (
        <div className={"relative z-30"}>
            <IconButton
                className={`sm:border sm:border-slate-400 flex flex-row items-center gap-1 rounded-full 
                            cursor-pointer hover:shadow-md transition text-slate-700`}
                onClick={handleClick}>
                <Avatar
                    alt={"Menu"}>
                    {user?.firstName?.charAt(0).toUpperCase() || user?.username?.charAt(0).toUpperCase() || ""}
                </Avatar>
            </IconButton>
            <Menu
                sx={{width: "400px"}}
                id={menuId}
                anchorEl={anchorEl}
                open={open}
                onClose={handleClose}
                slotProps={{
                    list: {
                        'aria-labelledby': buttonId,
                    },
                }}
            >
                <Link to={"/profile"}>
                    <MenuItem
                        className={"flex gap-2"}
                        onClick={handleClose}>
                        <BiUser className={"text-xl"}/>
                        <span className={"font-bold text-[16px] mt-1"}>
                        {truncateText(`${user?.firstName} ${user?.lastName}`)}
                        </span>
                    </MenuItem>
                </Link>
                <Link to={"/order"}>
                    <MenuItem
                        className={"flex gap-2"}
                        onClick={handleClose}>
                        <FaShoppingCart className={"text-xl"}/>
                        <span className={"font-semibold"}>
                        Order
                        </span>
                    </MenuItem>
                </Link>
                <MenuItem
                    className={"flex gap-2"}
                    onClick={signOutHandler}>
                    <div
                        className={`font-semibold w-full flex gap-2 items-center bg-button-gradient px-4 py-1 
                                    text-white rounded-xs`}>
                        <IoExitOutline className={"text-xl"}/>
                        <span className={"font-semibold"}>
                            Sign Out
                        </span>
                    </div>
                </MenuItem>
            </Menu>
        </div>
    );
};

export default UserMenu;