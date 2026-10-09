import { useState } from "react";
import { apiSignIn, apiSignOut, apiSignUp } from "../api/authenticationApi"
import { apiAddConsultantClient, apiGetClientById, apiGetConsultantById, apiGetConsultantClients, apiRemoveConsultantClient } from "../api/userManagementApi";

export default function AuthenticationPanel({setCurrentUser, showNotification}) {
    
    const [users, setUsers] = useState([]);
    const [viewMode, setViewMode] = useState(1);

    const signUpAsClient = async () => {
        let response = await apiSignUp(
            "exampleClient",
            "password",
            "client",
            "Client Fname",
            "Client Lname"
        );
        if (response) {
            showNotification("Signed up successfully", "success");
        }
    }

    const signUpAsConsultant = async () => {
        let response = await apiSignUp(
            "exampleConsultant",
            "password",
            "consultant",
            "Consultant Fname",
            "Consultant Lname"
        );
        if (response) {
            showNotification("Signed up successfully", "success");
        }
    }

    const signInAsClient = async () => {
        let response = await apiSignIn(
            "exampleClient",
            "password"
        );
        if (response) {
            setCurrentUser("exampleClient");
            showNotification("Signed in successfully", "success");
        }
    }

    const signInAsConsultant = async () => {
        let response = await apiSignIn(
            "exampleConsultant",
            "password"
        );
        if (response) {
            setCurrentUser("exampleClient");
            showNotification("Signed in successfully", "success");
        }
    }

    const signOut = async () => {
        await apiSignOut();
        setCurrentUser(null);
        showNotification("Signed out successfully", "success");
    }

    const getClient = async () => {
        let response = await apiGetClientById(1); //demo
        if (response) {
            setUsers([response]);
        }
    }

    const getConsultant = async () => {
        let response = await apiGetConsultantById(2); //demo
        if (response) {
            setUsers([response]);
        }
    }

    const getConsultantClients = async () => {
        let response = await apiGetConsultantClients(2); //demo
        if (response) {
            setUsers(response);
        }
    }

    const addConsultantClient = async () => {
        try {
            await apiAddConsultantClient(2, 1); //demo
            showNotification("Successfully added client to consultant", "success");
        } catch (error) {}
    }

     const removeConsultantClient = async () => {
        try {
            await apiRemoveConsultantClient(2, 1); //demo
            showNotification("Successfully removed client from consultant", "success");
            
        } catch (error) {}
    }

    return (
        <div className="grid">
            <h3 className="mb-4 text-center">Authentication & User Management</h3>

            <div className="row mx-auto gap-4">
                <div className="card col-md">
                    <div className="card-body py-4 d-flex flex-column gap-1 flex-wrap items-center">
                        
                        <div className="input-group mb-3">
                            <select 
                                className="form-select" 
                                value={viewMode}
                                onChange={(e) => setViewMode(Number(e.target.value))}
                            >
                                <option value={1}> Example Client </option>
                                <option value={2}> Example Consultant </option>
                                <option value={3}> Consultant Clients </option>
                            </select>
                            
                            <button onClick={signOut} className="btn btn-primary">
                                Sign Out
                            </button>
                        </div>
                        
                        {viewMode === 1 && <>
                            <div className={`d-flex ${users.length > 0 ? "flex-column" : "justify-content-center"} gap-2`}>
                                <button onClick={signUpAsClient} className="btn btn-primary">
                                    Sign Up
                                </button>
                                <button onClick={signInAsClient} className="btn btn-primary">
                                    Sign In
                                </button>
                                <button onClick={getClient} className="btn btn-primary">
                                    View
                                </button>
                            </div>
                        </>}

                        {viewMode === 2 && <>
                            <div className={`d-flex ${users.length > 0 ? "flex-column" : "justify-content-center"} gap-2`}>
                                <button onClick={signUpAsConsultant} className="btn btn-primary">
                                    Sign Up
                                </button>
                                <button onClick={signInAsConsultant} className="btn btn-primary">
                                    Sign In
                                </button>
                                <button onClick={getConsultant} className="btn btn-primary">
                                    View
                                </button>
                            </div>
                        </>}

                        {viewMode === 3 && <>
                            <div className={`d-flex ${users.length > 0 ? "flex-column" : "justify-content-center"} gap-2`}>
                                <button onClick={addConsultantClient} className="btn btn-success">
                                    Add Consultant Client
                                </button>
                                <button onClick={getConsultantClients} className="btn btn-primary">
                                    Get Consultant Clients
                                </button>
                                <button onClick={removeConsultantClient} className="btn btn-danger">
                                    Remove Consultant Client
                                </button>
                            </div>
                        </>}
                        
                    </div>
                </div>
                {users.length > 0 &&
                    <div className="card col-md">
                        <div className="card-body py-4">
                            {users?.map((user, i) => 
                                <div className="card" key={"user-"+i}>
                                    <div className="card-header">
                                        <li className="list-group-item">User ID: {user.userId}</li>
                                    </div>
                                    <ul className="list-group list-group-flush">
                                        <li className="list-group-item">Username: {user.userName}</li>
                                        <li className="list-group-item">First Name: {user.firstName}</li>
                                        <li className="list-group-item">Last Name: {user.lastName}</li>
                                        <li className="list-group-item">Role: {user.role}</li>
                                    </ul>
                                </div>
                            )}
                        </div>
                    </div>
                }
            </div>
        </div>
    )
}