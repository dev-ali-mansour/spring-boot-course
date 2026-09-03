export interface User {
    id: number | String;
    username: string;
    email: string;
    roles?: string[];
    jwtToken?: string;
}
