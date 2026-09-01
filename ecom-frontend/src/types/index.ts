export interface Product {
    id: number | string;
    name: string;
    image: string;
    description: string;
    quantity: number;
    price: number | string;
    discount?: number;
    specialPrice?: number | string;
}

export interface Pagination {
    pageNumber: number;
    pageSize: number;
    totalElements: number;
    totalPages: number;
    lastPage: boolean;
}

export interface Category {
    id: number | string;
    name: string;
}
