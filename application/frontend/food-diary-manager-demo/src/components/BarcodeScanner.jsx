
import {Html5Qrcode, Html5QrcodeSupportedFormats} from "html5-qrcode";
import { useRef, useState } from "react";

/**
 * Supporting documentation for html5-qrcode library:
 * https://www.npmjs.com/package/html5-qrcode/v/2.3.6
 */
export default function BarcodeScanner({setBarcode}) {

    //A reference to the scanner object.
    const scanner = useRef(null);
    const scannerRunning = useRef(false);

    const [showScanner, setShowScanner] = useState(false);

    /**
     * Start scanner
     */
    async function startScanner() {
        setShowScanner(true);
        
        //Create scanner object
        const scannerObject = new Html5Qrcode("reader", { 
            formatsToSupport: [
                Html5QrcodeSupportedFormats.EAN_13,
                Html5QrcodeSupportedFormats.EAN_8,
                Html5QrcodeSupportedFormats.UPC_A,
                Html5QrcodeSupportedFormats.UPC_E,
                Html5QrcodeSupportedFormats.CODE_128,
                Html5QrcodeSupportedFormats.QR_CODE
            ]
        });
        scanner.current = scannerObject;

        //Start scanner
        await scannerObject.start(

            { //Camera configuration
                facingMode: "environment"
            },

            { //General Configuration
                fps: 10,
                qrbox: { 
                    width: 300, 
                    height: 300
                },
                disableFlip: false
            },

            //Successful Scan
            onScanSuccess
        )

        //Scanner is readily running now
        scannerRunning.current = true;
    }

    /**
     * Handle scanned code
     */
    function onScanSuccess(decodedText, decodedResult) {
        console.log("Detected Barcode:", decodedText);
        stopScanner();
    }

    /**
     * Stop scanner
     */
    function stopScanner() {
        setShowScanner(false);
        if (scanner.current && scannerRunning.current) {
            scanner.current.stop()
                .then(() => scanner.current.clear())
                .catch(error => console.log(error));
        }
        scannerRunning.current = false;
    }

    return (<>

        {(!showScanner) && 
            <button onClick={startScanner}>
                Scan Barcode
            </button>
        }

        <div id="scannerContainer" style={{
            display: showScanner ? "block" : "none"
        }}>
            <button id="stopScanner" onClick={stopScanner}>
                Stop Scanning
            </button>

            <div id="reader" width="600px"></div>
        </div>
    </>);
}