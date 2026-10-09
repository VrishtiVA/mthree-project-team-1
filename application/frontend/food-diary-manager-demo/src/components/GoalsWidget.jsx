import { useState } from "react";
import { apiAddClientGoal, apiDeleteClientGoal, apiUpdateClientGoal } from "../api/goalsApi";

export default function GoalsWidget({showNotification}) {

    const [subjectValue, setSubjectValue] = useState("CALORIES");
    const [startDateValue, setStartDateValue] = useState("");
    const [endDateValue, setEndDateValue] = useState("");
    const [minimumValue, setMinimumValue] = useState("");
    const [maximumValue, setMaximumValue] = useState("");
    const [goalMode, setGoalMode] = useState("addGoal");
    const [goalIdValue, setGoalIdValue] = useState("");

    const clientIdValue = 1; //For demo, client will be created first to have ID 1.

    const addClientGoal = async () => {
        try {
            let response = await apiAddClientGoal(
                clientIdValue, 
                subjectValue,
                startDateValue,
                endDateValue,
                minimumValue,
                maximumValue
            );
            
            //Set if got a successful response
            if (response) {
                showNotification("Successfully added client goal with ID " + response.goalId, "success");
            } else throw Error();

        } catch (error) {}
    }

    const updateClientGoal = async () => {
        try {
            let response = await apiUpdateClientGoal(
                clientIdValue,
                goalIdValue,
                subjectValue,
                startDateValue,
                endDateValue,
                minimumValue,
                maximumValue
            );
            
            //Set if got a successful response
            if (response) {
                showNotification("Successfully updated client goal with ID " + response.goalId, "success");
            } else throw Error();

        } catch (error) {}
    }

    const deleteClientGoal = async () => {
        try {
            let response = await apiDeleteClientGoal(
                clientIdValue,
                goalIdValue
            );
            
            //Set if got a successful response
            showNotification("Successfully deleted client goal with ID " + goalIdValue, "success");

        } catch (error) {}
    }

    return (<div className="grid">
        <h3 className="mb-4 text-center">Client Goals Management</h3>
        
        <div className="row mx-auto gap-4">
            <div className="card col-md">
                <div className="card-body py-4 d-flex flex-column gap-2">
                    
                    <div className="d-flex flex-column gap-2">

                        <div className="input-group">
                            
                            <select 
                                className="form-select" 
                                defaultValue={"addGoal"}
                                value={goalMode}
                                onChange={(e) => setGoalMode(e.target.value)}
                            >
                                <option value="addGoal"> Add Goal </option>
                                <option value="updateGoal"> Update Goal </option>
                                <option value="deleteGoal"> Delete Goal </option>
                                {/* <option value="getGoals"> Get Goals </option> */}
                            </select>
                            
                            {goalMode !== "addGoal" && goalMode !== "getGoals" && 
                                <input 
                                    className="form-control" 
                                    placeholder="Enter Goal ID"
                                    type="number"
                                    value={goalIdValue}
                                    onInput={(e) => setGoalIdValue(e.target.value)}
                                />
                            }
                            {goalMode === "getGoals" &&
                                <button className="btn btn-primary" onClick={addClientGoal}>
                                    Get Client Goals
                                </button>
                            }
                        </div>

                        {goalMode !== "deleteGoal" && goalMode !== "getGoals" && <>
                            <div className="form-field">
                                <label className="form-label">Goal Focus</label>
                                <select 
                                    className="form-select" 
                                    defaultValue={"CALORIES"}
                                    value={subjectValue}
                                    onChange={(e) => setSubjectValue(e.target.value)}
                                >
                                    <option value="CALORIES"> Calories </option>
                                    <option value="PROTEIN"> Protein </option>
                                    <option value="FAT"> Fat </option>
                                    <option value="CARBOHYDRATES"> Carbohydrates </option>
                                    <option value="SUGARS"> Sugars </option>
                                    <option value="FIBER"> Fiber </option>
                                </select>
                            </div>

                            <div className="form-field">
                                <label className="form-label">Target Range</label>
                                <div className="input-group">
                                    <input 
                                        className="form-control" 
                                        placeholder="Enter minimum target"
                                        type="number"
                                        value={minimumValue}
                                        onInput={(e) => setMinimumValue(e.target.value)}
                                    />
                                    <input 
                                        className="form-control" 
                                        placeholder="Enter maximum target"
                                        type="number"
                                        value={maximumValue}
                                        onInput={(e) => setMaximumValue(e.target.value)}
                                    />
                                </div>
                            </div>

                            <div className="form-field">
                                <label className="form-label">Date Range</label>
                                <div className="input-group">
                                    <input 
                                        className="form-control" 
                                        placeholder="Enter start date"
                                        type="date"
                                        value={startDateValue}
                                        onInput={(e) => setStartDateValue(e.target.value)}
                                    />
                                    <input 
                                        className="form-control" 
                                        placeholder="Enter end date"
                                        type="date"
                                        value={endDateValue}
                                        onInput={(e) => setEndDateValue(e.target.value)}
                                    />
                                </div>
                            </div>
                        </>}
                    </div>
                    
                    <div className="d-flex align-self-center">
                        {goalMode === "addGoal" && 
                            <button className="btn btn-success mt-2" onClick={addClientGoal}>
                                Add Goal
                            </button>
                        }
                        {goalMode === "updateGoal" && 
                            <button className="btn btn-success mt-2" onClick={updateClientGoal}>
                                Update Goal
                            </button>
                        }
                        {goalMode === "deleteGoal" && 
                            <button className="btn btn-danger mt-2" onClick={deleteClientGoal}>
                                Delete Goal
                            </button>
                        }
                    </div>
                    
                </div>
            </div>
        </div>
    </div>);
}