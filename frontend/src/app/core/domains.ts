export interface User {
    id: string | number;
    email?: string;
    login?: string;
    avatarHash?: string;
    userStats: UserStats;
}

export interface UserStats {
    productCount: number;
}

export interface Product {
    id: number;
    price?: number;
    title?: string;
    description?: string;
    isMyProduct?: boolean;
}
