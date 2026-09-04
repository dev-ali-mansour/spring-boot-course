export interface User {
    id: number | String;
    firstName: string;
    lastName: string;
    username: string;
    email: string;
    roles?: string[];
    jwtToken?: string;
}
