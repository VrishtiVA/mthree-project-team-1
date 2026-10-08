import { apiSignIn, apiSignOut, apiSignUp } from "../api/authenticationApi"

export default function AuthenticationPanel({showNotification}) {
    
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
            showNotification("Signed in successfully", "success");
        }
    }

    const signInAsConsultant = async () => {
        let response = await apiSignIn(
            "exampleConsultant",
            "password"
        );
        if (response) {
            showNotification("Signed in successfully", "success");
        }
    }

    const signOut = async () => {
        await apiSignOut();
        showNotification("Signed out successfully", "success");
    }

    return (
        <div className="card">
            <div className="card-body d-flex gap-1 flex-wrap items-center">
                <button onClick={signUpAsClient} className="btn btn-primary btn-sm">
                    Sign Up As Example Client
                </button>
                <button onClick={signInAsClient} className="btn btn-primary btn-sm">
                    Sign In As Example Client
                </button>
                <button onClick={signUpAsConsultant} className="btn btn-primary btn-sm">
                    Sign Up As Example Consultant
                </button>
                <button onClick={signInAsConsultant} className="btn btn-primary btn-sm">
                    Sign In As Example Consultant
                </button>
                <button onClick={signOut} className="btn btn-primary btn-sm">
                    Sign Out
                </button>
            </div>
        </div>
    )
}