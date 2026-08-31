import {useState} from "react";
import {FiArrowUp, FiRefreshCw, FiSearch} from "react-icons/fi";
import {Button, FormControl, InputLabel, MenuItem, Select, SelectChangeEvent, Tooltip} from "@mui/material";

export default function Filter() {
    const categories = [
        {id: 1, name: "Electronics"},
        {id: 2, name: "Clothing"},
        {id: 3, name: "Furniture"},
        {id: 4, name: "Books"},
        {id: 5, name: "Toys"},
    ];

    const [category, setCategory] = useState<string>("all");

    const handleCategoryChange = (event: SelectChangeEvent) => {
        setCategory(event.target.value);
    };

    return (
        <div className="flex lg:flex-row flex-col-reverse lg:justify-between justify-center items-center gap-4">
            {/* SEARCH BAR */}
            <div className={"relative flex items-center 2xl:w-112.5 w-full sm:w-105"}>
                <input
                    type={"text"}
                    placeholder={"Search Products"}
                    className={"border border-gray-400 text-slate-800 rounded-md py-2 pl-10 pr-4 w-full focus:outline-none focus:ring-2 focus:ring-[#1976d2]"}
                />
                <FiSearch className={"absolute left-3 text-slate-800"} size={20}/>
            </div>

            {/* CATEGORY SELECTION */}
            <div className={"flex sm:flex-row flex-col gap-4 items-center"}>
                <FormControl
                    className={"text-slate-800 border-slate-700 "}
                    variant={"outlined"}
                    size={"small"}>
                    <InputLabel id={"category-select-label"}>Category</InputLabel>
                    <Select
                        labelId={"category-select-label"}
                        id={"category-select"}
                        value={category}
                        onChange={handleCategoryChange}
                        label={"Category"}
                        className={"min-w-30 text-slate-800 border-slate-700"}>
                        <MenuItem value={"all"}>All</MenuItem>
                        {categories.map((category) => (
                            <MenuItem key={category.id} value={category.name}>
                                {category.name}
                            </MenuItem>
                        ))}
                    </Select>
                </FormControl>

                {/* SORT BUTTON & CLEAN FILTERS */}
                <Tooltip title={"Sorted by price: asc"}>
                    <Button variant={"contained"} color={"primary"} className={"flex items-center gap-2 h-10"}>
                        Sort By
                        <FiArrowUp size={20}/>
                    </Button>
                </Tooltip>
                <button
                    className={"flex items-center gap-2 bg-rose-900 text-white px-3 py-2 rounded-md transition duration-300 ease-in shadow-md focus:outline-none"}>
                    <FiRefreshCw className={"font-semibold"} size={16}/>
                    <span className={"font-semibold"}>Clear Filter</span>
                </button>
            </div>
        </div>
    );
}
