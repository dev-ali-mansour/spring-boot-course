import React from "react";
import {usePathname} from "next/navigation";
import {useAuthStore} from "@/store";
import {FaTachometerAlt} from "react-icons/fa";
import {adminNavigation, sellerNavigation} from "@/utils";
import Link from "next/link";
import classNames from 'classnames';

interface SidebarProps {
    isProfileLayout?: Boolean;
}

const Sidebar: React.FC<SidebarProps> = ({isProfileLayout}) => {
    const pathName = usePathname();
    const user = useAuthStore((state) => state.user);

    const isAdmin = user?.roles?.includes("ROLE_ADMIN");
    const sideBarLayout = isAdmin ? adminNavigation : sellerNavigation;

    return (
        <div className={"flex grow flex-col gap-y-7 overflow-auto bg-custom-gradient px-6 pb-4"}>
            <div className={"flex h-16 shrink-0 gap-x-3 pt-2"}>
                <FaTachometerAlt className={"h-8 w-8 text-indigo-500"}/>
                <h1 className={"text-white text-xl font-bold"}>
                    {isAdmin ? "Admin Panel" : "Seller Panel"}
                </h1>
            </div>
            <nav className={"flex flex-1 flex-col"}>
                <ul role={"list"} className={"flex flex-1 flex-col gap-y-7"}>
                    <li>
                        <ul role={"list"} className={"-mx-2 space-y-4"}>
                            {sideBarLayout.map((item) => (
                                <li key={item.name}>
                                    <Link
                                        href={item.href}
                                        className={classNames(
                                            pathName === item.href
                                                ? "bg-custom-blue text-white"
                                                : "text-gray-400 hover:bg-gray-800 hover:text-white",
                                            "group flex gap-x-3 rounded-md p-2 text-sm font-semibold leading-6"
                                        )}>
                                        <item.icon className="text-2xl"/>
                                        {item.name}
                                    </Link>
                                </li>
                            ))}
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    );
};

export default Sidebar;