import { useState } from "react";
import BarcodeScanner from "./BarcodeScanner";

export default function AddFoodsWidget() {

    const [barcode, setBarcode] = useState("");

    return (<>
        <div>
            {/* Add by Scanner */}
            <div>
                <BarcodeScanner 
                    setBarcode={setBarcode}
                />
            </div>
        </div>
    </>);
}