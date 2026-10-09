import './App.css';
import AuthenticationPanel from './components/AuthenticationPanel';
import AddFoodsWidget from './components/AddFoodsWidget';
import AppHeader from './components/AppHeader';
import { useEffect, useState } from 'react';
import Notification from './components/Notification';
import FoodDiaryWidget from './components/FoodDiaryWidget';
import GoalsWidget from './components/GoalsWidget';

function App() {

  const [notification, setNotification] = useState(null);
  const [currentUser, setCurrentUser] = useState(null);

  function showNotification(message, type) {
    setNotification({message, type});
    
    //Hide notification after 3 seconds
    setTimeout(() => {
      setNotification(null);
    }, 3000);
  }

  return (<>
    <Notification notification={notification}/>

    <div className="container-slim my-5 d-flex flex-column" style={{gap: "5rem"}}>
      
      {/* {currentUser != null &&
        <div className='position-absolute d-flex gap-1 bg-info py-1 px-2 rounded-2'>
          <i className="bi bi-person-fill"></i>
          {currentUser}
        </div>
      } */}
      <AppHeader />

      <AuthenticationPanel 
        setCurrentUser={setCurrentUser} 
        showNotification={showNotification} 
      />

      <AddFoodsWidget showNotification={showNotification} />
      <FoodDiaryWidget showNotification={showNotification} />
      <GoalsWidget showNotification={showNotification} />
    </div>
  </>);
}

export default App;
