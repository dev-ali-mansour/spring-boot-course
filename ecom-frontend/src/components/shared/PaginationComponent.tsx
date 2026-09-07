"use client";

import {Pagination as MuiPagination} from "@mui/material";
import {usePathname, useRouter, useSearchParams} from "next/navigation";
import * as React from "react";
import {Pagination} from "@/types";

const PaginationComponent: React.FC<{ pagination: Partial<Pagination> }> = ({pagination}) => {
    const searchParams = useSearchParams();
    const params = new URLSearchParams(searchParams.toString());
    const pathName = usePathname();
    const router = useRouter();
    const paramValue = searchParams.get("page")
        ? Number(searchParams.get("page"))
        : 1;

    const onChangeHandler = (_: React.ChangeEvent<unknown>, value: number) => {
        params.set("page", value.toString());
        router.push(`${pathName}?${decodeURIComponent(params.toString())}`);
    }

    return (
        <div>
            <MuiPagination
                count={pagination.totalPages}
                page={paramValue}
                defaultPage={6}
                siblingCount={1}
                boundaryCount={2}
                shape={"rounded"}
                onChange={onChangeHandler}
            />
        </div>
    );
};

export default PaginationComponent;

