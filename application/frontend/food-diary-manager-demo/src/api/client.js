
import axios from "axios";

export const api = axios.create({
    baseURL: "http://localhost:8080/api"
})

export const API_JWT_KEY = "apiJwt"

//Request interceptor to add JWT header to requests
api.interceptors.request.use(

    (config) => {
        //Set JWT as bearer token in header
        const token = sessionStorage.getItem(API_JWT_KEY);
        if (token) config.headers.Authorization = `Bearer ${token}`;

        //Continue with modified configuration
        return config;
    },

    (error) => Promise.reject(error)
);