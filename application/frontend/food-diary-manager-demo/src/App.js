import './App.css';
import AuthenticationPanel from './components/AuthenticationPanel';
import AddFoodsWidget from './components/AddFoodsWidget';
import AppHeader from './components/AppHeader';
import { useState } from 'react';
import Notification from './components/Notification';
import FoodDiaryWidget from './components/FoodDiaryWidget';

function App() {

  const [notification, setNotification] = useState(null);

  function showNotification(message, type) {
    setNotification({message, type});
    
    //Hide notification after 3 seconds
    setTimeout(() => {
      setNotification(null);
    }, 3000);
  }

  return (<>
    <Notification notification={notification}/>

    <div className="container-slim my-5 d-flex flex-column gap-4">
      <AppHeader />

      <AuthenticationPanel showNotification={showNotification} />
      <AddFoodsWidget showNotification={showNotification} />
      {/* <GoalsWidget showNotification={showNotification} /> */}
      <FoodDiaryWidget showNotification={showNotification} />
    </div>
  </>);
}

export default App;
