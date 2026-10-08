
export default function Notification({notification = "success"}) {
    
    return (notification != null && 
        <div className="position-fixed top-0 end-0 m-3" style={{zIndex: 20}}>
            <div className={`alert alert-${notification.type} text-center shadow-md`} role="alert">
                {notification.message}
            </div>
        </div>
    );
}