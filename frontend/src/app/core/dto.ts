export class Page<T> {
    content: T[];
    currentPage: number;
    totalPages: number;
    totalElements: number;

    constructor(content: T[], currentPage: number, totalPages: number, totalElements: number) {
        this.content = content;
        this.currentPage = currentPage;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }
}

export class PageRequest {
    page: number;
    size: number;

    constructor(page:number, size: number) {
        this.page = page;
        this.size = size;
    }
}

export interface UserParams {
    email?: string;
    password?: string;
    login?: string;
}

export interface ProductParams {
    price?: number;
    title?: string;
    description?: string;
}
