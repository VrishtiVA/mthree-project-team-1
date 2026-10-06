
import {api} from "./client";

export const apiSignIn = async (username, password) => {

    try {
        let response = await api.post("/auth/signin", {
            userName: username,
            password: password
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