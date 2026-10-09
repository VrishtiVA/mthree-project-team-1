import { useState } from "react";
import BarcodeScanner from "./BarcodeScanner";
import { apiAddToDiaryByBarcode, apiDeleteDiaryEntry, apiFindFoodByBarcode, apiFindFoodByName, apiUpdateDiaryEntry } from "../api/foodDiaryApi";

export default function AddFoodsWidget({showNotification}) {

    const [lookupMode, setLookupMode] = useState("barcode");
    const [lookupValue, setLookupValue] = useState("");
    const [foodItem, setFoodItem] = useState(null);
    const [amountValue, setAmountValue] = useState(null);
    const [dateTimeValue, setDateTimeValue] = useState("");
    const [diaryMode, setDiaryMode] = useState("addEntry");
    const [entryIdValue, setEntryIdValue] = useState(null);

    const goLookupItem = async () => {
        try {
            let response = null;
            
            //Try fetch food item
            if (lookupMode === "barcode") {
                response = await apiFindFoodByBarcode(lookupValue);
            } else {
                response = await apiFindFoodByName(lookupValue);
            }
            
            //Set if got a successful response
            if (response) 
                setFoodItem(response);
            else throw Error();

        } catch (error) {
            setFoodItem(null);
        }
    }

    const addToDiaryByBarcode = async () => {
        try {
            let response = null;
            
            let datetime = dateTimeValue?.split("T") || null;
            if (!datetime) return;
            let time = datetime[1] + ":00";
            let date = datetime[0];
            
            response = await apiAddToDiaryByBarcode(lookupValue, amountValue, time, date);
            
            //Set if got a successful response
            if (response) {
                setFoodItem(response); 
                showNotification("Successfully added food to diary", "success");
            } else throw Error();

        } catch (error) {
            setFoodItem(null);
        }
    }

    const updateDiaryEntry = async () => {
        try {
            let response = null;
            
            let datetime = dateTimeValue?.split("T") || null;
            if (!datetime) return;
            let time = datetime[1] + ":00";
            let date = datetime[0];
            
            response = await apiUpdateDiaryEntry(entryIdValue, lookupValue, amountValue, time, date);
            
            //Set if got a successful response
            if (response) {
                showNotification("Successfully updated diary entry", "success");
            } else throw Error();

        } catch (error) {}
    }

    const deleteDiaryEntry = async () => {
        try {
            await apiDeleteDiaryEntry(entryIdValue);
            
            //Set if got a successful response
            showNotification("Successfully deleted diary entry", "success");

        } catch (error) {}
    }

    return (<>
        <div className="grid">
            <h3 className="mb-4 text-center">Food Lookup & Diary Management</h3>

            <div className="row mx-auto gap-4">
                <div className="card col-md">
                    <div className="card-body py-4 d-flex flex-column gap-2">
                        
                        <div>
                            <select 
                                className="form-select"
                                defaultValue={"barcode"}
                                value={lookupMode}
                                onChange={(e) => setLookupMode(e.target.value)}
                            >
                                <option value="barcode"> Barcode </option>
                                <option value="name"> Search </option>
                            </select>
                        </div>

                        <div className="input-group rounded-2 overflow-hidden">
                            <input 
                                className="form-control" 
                                placeholder={lookupMode === "barcode" ? "Scan or Enter Barcode ..." : "Enter Food Name ..."}
                                value={lookupValue}
                                onInput={(e) => setLookupValue(e.target.value)}
                            />
                            {lookupMode === "barcode" && <BarcodeScanner 
                                setBarcode={setLookupValue}
                            />}
                        </div>

                        <div className="d-flex align-self-center mt-2">
                            <button className="btn btn-success" onClick={goLookupItem}>
                                Lookup
                            </button>
                        </div>

                        {lookupMode === "barcode" && <>
                            {/* <hr className="my-1"/> */}

                            <div className="input-group mt-2">
                                <select 
                                    className="form-select" 
                                    defaultValue={"addEntry"}
                                    value={diaryMode}
                                    onChange={(e) => setDiaryMode(e.target.value)}
                                >
                                    <option value="addEntry"> Add Entry </option>
                                    <option value="updateEntry"> Update Entry </option>
                                    <option value="deleteEntry"> Delete Entry </option>
                                </select>

                                {diaryMode !== "addEntry" && 
                                    <input 
                                        className="form-control" 
                                        placeholder="Enter Entry ID"
                                        type="number"
                                        value={entryIdValue}
                                        onInput={(e) => setEntryIdValue(e.target.value)}
                                    />
                                }
                            </div>

                            {diaryMode !== "deleteEntry" && <>
                                <div className="input-group">
                                    <input 
                                        className="form-control" 
                                        placeholder="Enter amount in grams"
                                        type="number"
                                        value={amountValue}
                                        onInput={(e) => setAmountValue(e.target.value)}
                                    />
                                </div>

                                <div className="input-group">
                                    <input 
                                        className="form-control" 
                                        placeholder="Enter date and time"
                                        type="datetime-local"
                                        value={dateTimeValue}
                                        onInput={(e) => setDateTimeValue(e.target.value)}
                                    />
                                </div>
                            </>}

                            <div className="d-flex align-self-center mt-2">
                                {diaryMode === "addEntry" &&
                                    <button className="btn btn-success" onClick={addToDiaryByBarcode}>
                                        Add to Diary
                                    </button>
                                }
                                {diaryMode === "updateEntry" &&
                                    <button className="btn btn-success" onClick={updateDiaryEntry}>
                                        Update Diary Entry
                                    </button>
                                }
                                {diaryMode === "deleteEntry" &&
                                    <button className="btn btn-danger" onClick={deleteDiaryEntry}>
                                        Delete Diary Entry
                                    </button>
                                }
                            </div>
                        </>}
                    </div>
                </div>

                {foodItem != null && 
                    <div className="card col-md">
                        <div className="card-body py-4">
                            <ul className="list-group">
                                <li className="list-group-item">Name: {foodItem.name}</li>
                                <li className="list-group-item">Barcode: {foodItem.barcode || "N/A"}</li>
                                <li className="list-group-item">Calories: {foodItem.calories}kcal</li>
                                <li className="list-group-item">Carboyhrates: {foodItem.carbohydrates}g</li>
                                <li className="list-group-item">Fat: {foodItem.fat}g</li>
                                <li className="list-group-item">Protein: {foodItem.protein}g</li>
                                <li className="list-group-item">Sugars: {foodItem.sugars}g</li>
                                <li className="list-group-item">Salt: {foodItem.salt}g</li>
                                <li className="list-group-item">Fibre: {foodItem.fibre}g</li>
                            </ul>
                        </div>
                    </div>
                }
            </div>
        </div>
    </>);
}