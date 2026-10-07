
import { api } from "./client";

export const apiFindFoodByBarcode = async (barcode) => {
    try {
        let response = await api.get(`/food/barcode/${barcode}`);

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

export const apiFindFoodByName = async (name) => {
    try {
        let response = await api.get(`/food/name/${name}`);

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
