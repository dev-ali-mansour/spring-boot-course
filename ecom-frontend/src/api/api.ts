import xior from "xior";

export const api = xior.create({
    baseURL: `${import.meta.env.VITE_BACK_END_URL}/api`,
});
