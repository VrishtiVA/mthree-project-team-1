
import {Html5QrcodeScanner} from "html5-qrcode";
import { useEffect } from "react";

/**
 * Supporting documentation for html5-qrcode library:
 * https://www.npmjs.com/package/html5-qrcode/v/2.3.6
 */
export default function BarcodeScanner() {

    //Create a new scanner instance when rendered/mounted.
    useEffect(() => {

        /**
         * Handle the scanned code as you like, for example
         */
        function onScanSuccess(decodedText, decodedResult) {
            console.log(`Code matched = ${decodedText}`, decodedResult);
        }

        /**
         *  Handle scan failure, usually better to ignore and keep scanning.
         */
        function onScanFailure(error) {
            // console.warn(`Code scan error = ${error}`);
        }

        let html5QrcodeScanner = new Html5QrcodeScanner(
            "reader",
            { 
                fps: 10, 
                qrbox: {width: 250, height: 250} 
            },
            /* verbose= */ false
        );
        html5QrcodeScanner.render(onScanSuccess, onScanFailure);
    
        //Remove scanner when unmounted.
        return () => {
            html5QrcodeScanner.clear().catch(error => {
                console.log("Failed to clear scanner:", error);
            });
        }

    }, []);

    return (<>
        <div id="reader" width="600px">

        </div>
    </>);

}