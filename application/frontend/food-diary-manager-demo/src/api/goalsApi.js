import { api } from "./client";

export const apiAddClientGoal = async (clientId, goalSubject, startDate, endDate, minTarget, maxTarget) => {
    try {
        let response = await api.post(`/client/${clientId}/goal`, {
            goalSubject: goalSubject,
            startDate: startDate,
            endDate: endDate,
            minTarget: minTarget,
            maxTarget: maxTarget
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

export const apiUpdateClientGoal = async (clientId, goalId, goalSubject, startDate, endDate, minTarget, maxTarget) => {
    try {
        let response = await api.put(`/client/${clientId}/goal/${goalId}`, {
            goalSubject: goalSubject,
            startDate: startDate,
            endDate: endDate,
            minTarget: minTarget,
            maxTarget: maxTarget
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

export const apiDeleteClientGoal = async (clientId, goalId) => {
    try {
        let response = await api.delete(`/client/${clientId}/goal/${goalId}`);

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