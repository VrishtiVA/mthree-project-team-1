import { useState } from "react";
import { apiGetAllClientDiaryEntriesByDate } from "../api/foodDiaryApi";

export default function FoodDiaryWidget({showNotification}) {

    const [dateTimeValue, setDateTimeValue] = useState("");
    const [dateValue, setDateValue] = useState("");
    const [diaryEntries, setDiaryEntries] = useState([]);

    const getAllDiaryEntriesByDate = async () => {
        try {
            let response = await apiGetAllClientDiaryEntriesByDate(dateValue.split("T")[0]);
            if (!response) throw Error();

            //If successful response
            setDiaryEntries(response);

        } catch (error) {
            setDiaryEntries([]);
        }
    }

    return (
        <div className="grid">
            <h3 className="mb-4 text-center">View Food Diary</h3>
            
            <div className="row mx-auto gap-4">
                <div className="card col-md">
                    <div className="card-body py-4 d-flex flex-column gap-2">
                        
                        <div className="input-group rounded-2 overflow-hidden">
                            <input 
                                className="form-control" 
                                placeholder="Enter date and time"
                                type="date"
                                value={dateValue}
                                onInput={(e) => setDateValue(e.target.value)}
                            />
                            <button className="btn btn-primary" onClick={getAllDiaryEntriesByDate}>
                                View Diary Entries
                            </button>
                        </div>

                        {diaryEntries?.length > 0 && 
                            <div className="d-flex flex-column gap-3 mt-3">
                                {diaryEntries?.map((entry, i) => 
                                    <div className="card" key={"diary-entry-"+i}>
                                        <div className="card-header">
                                            <li className="list-group-item">Entry ID: {entry.diaryEntryId}</li>
                                        </div>
                                        <ul className="list-group list-group-flush">
                                            <li className="list-group-item">Name: {entry.name}</li>
                                            <li className="list-group-item">Amount: {entry.amount}g</li>
                                            <li className="list-group-item">Calories: {entry.calories}kcal</li>
                                            <li className="list-group-item">Carboyhrates: {entry.carbohydrates}g</li>
                                            <li className="list-group-item">Fat: {entry.fat}g</li>
                                            <li className="list-group-item">Protein: {entry.protein}g</li>
                                            <li className="list-group-item">Sugars: {entry.sugars}g</li>
                                            <li className="list-group-item">Salt: {entry.salt}g</li>
                                            <li className="list-group-item">Fibre: {entry.fibre}g</li>
                                        </ul>
                                    </div>
                                )}
                            </div>
                        }

                    </div>
                </div>
            </div>
        </div>
    );
}