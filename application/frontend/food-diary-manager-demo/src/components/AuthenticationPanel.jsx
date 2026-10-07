import { apiSignIn, apiSignOut, apiSignUp } from "../api/authenticationApi"

export default function AuthenticationPanel() {
    
    const signUpAsClient = async () => {
        await apiSignUp(
            "exampleClient",
            "password",
            "client",
            "Client Fname",
            "Client Lname"
        );
    }

    const signUpAsConsultant = async () => {
        await apiSignUp(
            "exampleConsultant",
            "password",
            "consultant",
            "Consultant Fname",
            "Consultant Lname"
        );
    }

    const signInAsClient = async () => {
        await apiSignIn(
            "exampleClient",
            "password"
        );
    }

    const signInAsConsultant = async () => {
        await apiSignIn(
            "exampleConsultant",
            "password"
        );
    }

    const signOut = async () => {
        await apiSignOut();
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