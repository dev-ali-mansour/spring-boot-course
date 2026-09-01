import { useEffect, useState } from "react";
import { FiArrowDown, FiArrowUp, FiRefreshCw, FiSearch } from "react-icons/fi";
import { Button, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent, Tooltip } from "@mui/material";
import { useLocation, useNavigate, useSearchParams } from "react-router-dom";
import { Category } from "../types";

export default function Filter({ categories }: { categories: Category[] }) {
    const [searchParams] = useSearchParams();
    const pathName = useLocation().pathname;
    const navigate = useNavigate();

    // Derived values from URL (no duplicate state / avoids extra re-render cycles)
    const category = searchParams.get("category") || "all";
    const sort = searchParams.get("sort") || "price,asc";

    // Local state ONLY for debounced text input
    const [searchTerm, setSearchTerm] = useState<string>(searchParams.get("keyword") || "");

    // Sync input box when URL changes externally (e.g. browser navigation or clear filters)
    useEffect(() => {
        setSearchTerm(searchParams.get("keyword") || "");
    }, [searchParams]);

    // Debounced search effect
    useEffect(() => {
        const handler = setTimeout(() => {
            const currentKeyword = searchParams.get("keyword") || "";
            if (searchTerm === currentKeyword) return;

            const params = new URLSearchParams(searchParams);
            if (searchTerm.trim()) {
                params.set("keyword", searchTerm.trim());
            } else {
                params.delete("keyword");
            }
            navigate(`${pathName}?${decodeURIComponent(params.toString())}`);
        }, 700);

        return () => clearTimeout(handler);
    }, [searchTerm, searchParams, navigate, pathName]);

    const handleCategoryChange = (event: SelectChangeEvent) => {
        const selectedCategory = event.target.value;
        const params = new URLSearchParams(searchParams);
        if (selectedCategory === "all") {
            params.delete("category");
        } else {
            params.set("category", selectedCategory);
        }
        navigate(`${pathName}?${decodeURIComponent(params.toString())}`);
    };

    const toggleSortOrder = () => {
        const newOrder = sort.endsWith("asc") ? "price,desc" : "price,asc";
        const params = new URLSearchParams(searchParams);
        params.set("sort", newOrder);
        navigate(`${pathName}?${decodeURIComponent(params.toString())}`);
    };

    const handleClearFilters = () => {
        setSearchTerm("");
        navigate({ pathname: pathName });
    };

    return (
        <div className="flex lg:flex-row flex-col-reverse lg:justify-between justify-center items-center gap-4">
            {/* SEARCH BAR */}
            <div className="relative flex items-center 2xl:w-112.5 w-full sm:w-105">
                <input
                    type="text"
                    placeholder="Search Products"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="border border-gray-400 text-slate-800 rounded-md py-2 pl-10 pr-4 w-full focus:outline-none focus:ring-2 focus:ring-[#1976d2]"
                />
                <FiSearch className="absolute left-3 text-slate-800" size={20} />
            </div>

            {/* CATEGORY SELECTION & ACTIONS */}
            <div className="flex sm:flex-row flex-col gap-4 items-center">
                <FormControl
                    className="text-slate-800 border-slate-700"
                    variant="outlined"
                    size="small"
                >
                    <InputLabel id="category-select-label">Category</InputLabel>
                    <Select
                        labelId="category-select-label"
                        id="category-select"
                        value={category}
                        onChange={handleCategoryChange}
                        label="Category"
                        className="min-w-30 text-slate-800 border-slate-700"
                    >
                        <MenuItem value="all">All</MenuItem>
                        {categories.map((item) => (
                            <MenuItem key={item.id} value={item.name}>
                                {item.name}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                {/* SORT BUTTON & CLEAR FILTERS */}
                <Tooltip title={`Sorted by price: ${sort.endsWith("asc") ? "asc" : "desc"}`}>
                    <Button
                        variant="contained"
                        color="primary"
                        className="flex items-center gap-2 h-10"
                        onClick={toggleSortOrder}
                    >
                        Sort By
                        {sort.endsWith("asc") ? <FiArrowUp size={20} /> : <FiArrowDown size={20} />}
                    </Button>
                </Tooltip>

                <button
                    className="flex items-center gap-2 bg-rose-900 text-white px-3 py-2 rounded-md transition duration-300 ease-in shadow-md focus:outline-none cursor-pointer"
                    onClick={handleClearFilters}
                >
                    <FiRefreshCw className="font-semibold" size={16} />
                    <span className="font-semibold">Clear Filter</span>
                </button>
            </div>
        </div>
    );
}

