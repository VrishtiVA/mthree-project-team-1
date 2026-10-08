
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

export const apiAddToDiaryByBarcode = async (barcode, amount, time, date) => {
    try {
        let response = await api.post(`/food/diary`, {
            barcode: barcode,
            amount: amount,
            time: time,
            date: date
        });

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