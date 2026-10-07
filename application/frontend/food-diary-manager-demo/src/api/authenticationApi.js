
import {api, API_JWT_KEY} from "./client";

export const apiSignIn = async (username, password) => {

    try {
        let response = await api.post("/auth/signin", {
            userName: username,
            password: password
        });

        //Success if reached here.
        console.log(response.data);
        sessionStorage.setItem(API_JWT_KEY, response.data.jwt);
        console.log("Successfully Signed In");
        return response.data;

    } catch (error) {
        if (error.response?.data) {
            console.log(error.response);
        } else {
            console.log(error);
        }
    }

}

export const apiSignUp = async (username, password, role, firstname, lastname) => {

    try {
        let response = await api.post("/auth/signup", {
            userName: username,
            password: password,
            firstName: firstname,
            lastName: lastname,
            role: role
        });

        //Success if reached here.
        console.log(response.data);
        console.log("Successfully Signed Up");
        return response.data;

    } catch (error) {
        if (error.response?.data) {
            console.log(error.response);
        } else {
            console.log(error);
        }
    }

}

export const apiSignOut = async () => {
    sessionStorage.clear(API_JWT_KEY);
    console.log("Signed Out");
}