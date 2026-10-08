
export default function Notification({notification = "success"}) {
    
    return (notification != null && 
        <div className="position-fixed top-0 end-0 m-3 ">
            <div className={`alert alert-${notification.type} text-center z-20 shadow-md`} role="alert">
                {notification.message}
            </div>
        </div>
    );
}