import xior from "xior";

export const api = xior.create({
    baseURL: `${process.env.NEXT_PUBLIC_BACK_END_URL || "http://localhost:8080"}/api`,
    credentials: "include",
});
