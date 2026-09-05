import React from "react";
import {Dialog, DialogBackdrop, DialogPanel, DialogTitle} from "@headlessui/react";
import {FaExclamationTriangle, FaTimes} from "react-icons/fa";

interface DeleteAddressModalProps {
    isOpen: boolean;
    setIsOpen: (open: boolean) => void;
    title: string;
    isLoading: boolean;
    onDeleteHandler: () => void;
}

const DeleteAddressModal: React.FC<DeleteAddressModalProps> = (
    {isOpen, setIsOpen, title, isLoading, onDeleteHandler}
) => {
    return (
        <Dialog open={isOpen} onClose={setIsOpen} className="relative z-50">
            <DialogBackdrop
                transition
                className="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity data-closed:opacity-0 data-enter:duration-300 data-leave:duration-200 data-enter:ease-out data-leave:ease-in"
            />
            <div className="fixed inset-0 z-10 w-screen overflow-y-auto">
                <div className="flex min-h-full items-end justify-center p-4 text-center sm:items-center sm:p-0">
                    <DialogPanel
                        transition
                        className="relative transform overflow-hidden rounded-lg bg-white px-4 pb-4 pt-5 text-left shadow-xl transition-all data-closed:translate-y-4 data-closed:opacity-0 data-enter:duration-300 data-leave:duration-200 data-enter:ease-out data-leave:ease-in sm:my-8 sm:w-full sm:max-w-lg sm:p-6 sm:data-closed:translate-y-0 sm:data-closed:scale-95"
                    >
                        <div className="absolute right-0 top-0 hidden pr-4 pt-4 sm:block">
                            <button
                                disabled={isLoading}
                                type="button"
                                onClick={() => setIsOpen(false)}
                                className="rounded-md bg-white text-gray-400 hover:text-gray-500 focus:outline-hidden focus:ring-2 focus:ring-indigo-500 focus:ring-offset-2"
                            >
                                <span className="sr-only">Close</span>
                                <FaTimes className="h-6 w-6"/>
                            </button>
                        </div>
                        <div className="sm:flex sm:items-start">
                            <div
                                className="mx-auto flex h-12 w-12 shrink-0 items-center justify-center rounded-full bg-red-100 sm:mx-0 sm:h-10 sm:w-10">
                                <FaExclamationTriangle className=" text-red-600"/>
                            </div>
                            <div className="mt-3 text-center sm:ml-4 sm:mt-0 sm:text-left">
                                <DialogTitle
                                    as="h3"
                                    className="text-base font-semibold font-metropolis leading-6 text-textColor"
                                >
                                    {title}
                                </DialogTitle>
                                <div className="mt-2">
                                    <p className="text-sm text-textColor2 font-metropolis">
                                        Are you sure you want to delete?
                                    </p>
                                </div>
                            </div>
                        </div>
                        <div className="mt-5 sm:mt-4 sm:flex sm:flex-row-reverse">
                            <button
                                disabled={isLoading}
                                type="button"
                                onClick={onDeleteHandler}
                                className="inline-flex w-full bg-customRed justify-center rounded-md bg-red-600 px-3 py-2 text-sm font-semibold text-white shadow-xs hover:bg-red-500 sm:ml-3 sm:w-auto"
                            >
                                {isLoading ? "Loading..." : "Delete"}
                            </button>
                            <button
                                disabled={isLoading}
                                type="button"
                                onClick={() => setIsOpen(false)}
                                className="mt-3 inline-flex w-full justify-center rounded-md bg-white px-3 py-2 text-sm font-semibold text-gray-900 shadow-xs ring-1 ring-inset ring-gray-300 hover:bg-gray-50 sm:mt-0 sm:w-auto"
                            >
                                Cancel
                            </button>
                        </div>
                    </DialogPanel>
                </div>
            </div>
        </Dialog>
    );
};

export default DeleteAddressModal;
