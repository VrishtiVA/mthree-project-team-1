
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

export const apiGetAllClientDiaryEntries = async () => {
    try {
        let response = await api.get(`/food/diary`);

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


export const apiGetAllClientDiaryEntriesByDate = async (date) => {
    try {
        let response = await api.get(`/food/diary/day/${date}`);

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

export const apiUpdateDiaryEntry = async (id, barcode, amount, time, date) => {
    try {
        let response = await api.put(`/food/diary/${id}`, {
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

export const apiDeleteDiaryEntry = async (id) => {
    try {
        let response = await api.delete(`/food/diary/${id}`);

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