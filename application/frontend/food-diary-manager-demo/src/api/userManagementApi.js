
import { api } from "./client";

export const apiGetClientById = async (clientId) => {
    try {
        let response = await api.get(`/client/${clientId}`);

        //Success if reached here.
        console.log(response.data);
        return response.data;

    } catch (error) {
        if (error.response?.data) {
            console.log(error.response);
        } else {
            console.log(error);
        }
    }
}

export const apiGetConsultantById = async (consultantId) => {
    try {
        let response = await api.get(`/consultant/${consultantId}`);

        //Success if reached here.
        console.log(response.data);
        return response.data;

    } catch (error) {
        if (error.response?.data) {
            console.log(error.response);
        } else {
            console.log(error);
        }
    }
}

export const apiGetConsultantClients = async (consultantId) => {
    try {
        let response = await api.get(`/consultant/${consultantId}/clients`);

        //Success if reached here.
        console.log(response.data);
        return response.data;

    } catch (error) {
        if (error.response?.data) {
            console.log(error.response);
        } else {
            console.log(error);
        }
    }
}

export const apiAddConsultantClient = async (consultantId, clientId) => {
    try {
        let response = await api.post(`/consultant/${consultantId}/client/${clientId}`);

        //Success if reached here.
        console.log(response.data);
        return response.data;

    } catch (error) {
        if (error.response?.data) {
            console.log(error.response);
        } else {
            console.log(error);
        }
        throw error;
    }
}

export const apiRemoveConsultantClient = async (consultantId, clientId) => {
    try {
        let response = await api.delete(`/consultant/${consultantId}/client/${clientId}`);

        //Success if reached here.
        console.log(response.data);
        return response.data;

    } catch (error) {
        if (error.response?.data) {
            console.log(error.response);
        } else {
            console.log(error);
        }
        throw error;
    }
}