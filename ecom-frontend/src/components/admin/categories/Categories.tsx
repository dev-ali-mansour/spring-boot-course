"use client";
import React, {useState} from "react";
import useCategoryFilter from "@/hooks/useCategoryFilter";
import {getErrorMessage, useCategories, useDeleteCategory} from "@/hooks/useQueries";
import Loader from "@/components/shared/Loader";
import ErrorPage from "@/components/shared/ErrorPage";
import {FaThList} from "react-icons/fa";
import CategoriesTable from "@/components/admin/categories/CategoriesTable";
import {Category} from "@/types";
import DeleteModal from "@/components/shared/DeleteModel";
import toast from "react-hot-toast";
import truncateText from "@/utils/truncateText";
import Modal from "@/components/shared/Modal";
import AddCategoryForm from "@/components/admin/categories/AddCategoryForm";

const Categories: React.FC = () => {
    const queryString = useCategoryFilter();
    const {data: categoriesData, isLoading, error} = useCategories(queryString);
    const deleteCategoryMutation = useDeleteCategory();
    const categories = categoriesData?.content || [];
    const pagination = categoriesData;
    const [selectedCategory, setSelectedCategory] = useState<Category | null>(null);
    const [isAddModalOpen, setIsAddModalOpen] = useState(false);
    const [isEditModalOpen, setIsEditModalOpen] = useState(false);
    const [isDeleteModalOpen, setIsDeleteModalOpen] = useState(false);

    const isEmptyCategories = !categoriesData || categoriesData?.content?.length === 0;

    if (isLoading) return <Loader/>;
    if (error) return <ErrorPage message={getErrorMessage(error)}/>;

    return (
        <div>
            <div className={"pt-6 pb-10 flex justify-end"}>
                <button
                    onClick={() => setIsAddModalOpen(true)}
                    className={`bg-custom-blue hover:bg-blue-800 text-white font-semibold py-2 px-4 flex 
                        items-center gap-2 rounded-md shadow-md transition-colors hover:text-slate-300 duration-300`}>
                    <FaThList className={"text-xl"}/>
                    Add Category
                </button>
            </div>

            {isLoading ? (
                <Loader/>
            ) : (
                isEmptyCategories ? (
                    <div className={"flex flex-col items-center justify-center text-gray-600 py-10"}>
                        <FaThList size={50} className={"mb-3"}/>
                        <h2 className={"text-2xl font-semibold"}>
                            No Categories created yet
                        </h2>
                    </div>
                ) : (
                    <CategoriesTable
                        categories={categories}
                        pagination={pagination}
                        handleEdit={(category) => {
                            setSelectedCategory(category);
                            setIsEditModalOpen(true);
                        }}
                        handleDelete={(category) => {
                            setSelectedCategory(category);
                            setIsDeleteModalOpen(true);
                        }}/>
                )
            )}

            <Modal
                open={isAddModalOpen || isEditModalOpen}
                setOpen={isEditModalOpen ? setIsEditModalOpen : setIsAddModalOpen}
                title={isEditModalOpen ? "Update Category" : "Add Category"}>
                <AddCategoryForm
                    setIsOpen={isEditModalOpen ? setIsEditModalOpen : setIsAddModalOpen}
                    isForUpdate={isEditModalOpen}
                    category={selectedCategory}
                />
            </Modal>

            <DeleteModal
                open={isDeleteModalOpen}
                setOpen={setIsDeleteModalOpen}
                title={`Delete Category ${selectedCategory ? truncateText(selectedCategory?.name, 50) : ""}`}
                onDeleteHandler={async () => {
                    try {
                        if (selectedCategory?.id) {
                            await deleteCategoryMutation.mutateAsync(selectedCategory.id);
                            toast.success("Category deleted successfully");
                            setIsDeleteModalOpen(false);
                        }
                    } catch (error: unknown) {
                        console.error(error);
                        toast.error(getErrorMessage(error));
                    }
                }}
                isLoading={deleteCategoryMutation.isPending}/>
        </div>
    );
};

export default Categories;
