import { useState } from "react";
import BarcodeScanner from "./BarcodeScanner";
import { apiAddToDiaryByBarcode, apiFindFoodByBarcode, apiFindFoodByName } from "../api/foodDiaryApi";

export default function AddFoodsWidget() {

    const [lookupMode, setLookupMode] = useState("barcode");
    const [lookupValue, setLookupValue] = useState("");
    const [foodItem, setFoodItem] = useState(null);
    const [amountValue, setAmountValue] = useState(null);
    const [dateTimeValue, setDateTimeValue] = useState(null);

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
            
            //Try fetch food item
            response = await apiAddToDiaryByBarcode(lookupValue, amountValue, time, date);
            
            //Set if got a successful response
            if (response) {
                setFoodItem(response); 
            } else {
                throw Error();
            }

        } catch (error) {
            setFoodItem(null);
        }
    }

    return (<>
        <div className="grid">
            <h3 className="mb-4 text-center">Food Lookup</h3>

            <div className="row mx-auto gap-4">
                <div className="card col-md">
                    <div className="card-body py-4 d-flex flex-column gap-2">
                        
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

                        <div>
                            <select className="form-select" onChange={(e) => setLookupMode(e.target.value)}>
                                <option 
                                    value="barcode" 
                                    selected={lookupMode === "barcode"}
                                >
                                    By Barcode
                                </option>
                                <option 
                                    value="name"
                                    selected={lookupMode === "name"}
                                >
                                    By Search
                                </option>
                            </select>
                        </div>

                        <button className="btn btn-success" onClick={goLookupItem}>
                            Lookup
                        </button>

                        {lookupMode === "barcode" && <>
                            <hr className="my-1"/>

                            <div className="input-group rounded-2 overflow-hidden">
                                <input 
                                    className="form-control" 
                                    placeholder="Enter amount in grams"
                                    type="number"
                                    value={amountValue}
                                    onInput={(e) => setAmountValue(e.target.value)}
                                />
                            </div>

                            <div className="input-group rounded-2 overflow-hidden">
                                <input 
                                    className="form-control" 
                                    placeholder="Enter date and time"
                                    type="datetime-local"
                                    value={dateTimeValue}
                                    onInput={(e) => setDateTimeValue(e.target.value)}
                                />
                            </div>

                            <button className="btn btn-success" onClick={addToDiaryByBarcode}>
                                Add to Diary
                            </button>
                        </>}
                    </div>
                </div>

                {foodItem != null && 
                    <div className="card col-md">
                        <div className="card-body py-4">
                            <ul className="list-group">
                                <li className="list-group-item">Name: {foodItem.name}</li>
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