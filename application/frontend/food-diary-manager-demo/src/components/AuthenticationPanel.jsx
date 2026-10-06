import { apiSignIn } from "../api/authenticationApi"

export default function AuthenticationPanel() {
    
    const signInAsClient1 = async () => {

        let response = await apiSignIn(
            "vrishti",
            "password"
        );

        console.log("Signed in as Client 1");
    }

    return (<>
        <button onClick={signInAsClient1}>
            Sign In As Client 1
        </button>
    </>)
}