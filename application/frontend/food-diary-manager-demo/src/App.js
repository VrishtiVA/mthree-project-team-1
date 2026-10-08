import './App.css';
import AuthenticationPanel from './components/AuthenticationPanel';
import AddFoodsWidget from './components/AddFoodsWidget';
import AppHeader from './components/AppHeader';

function App() {

  return (
    <div className="container-slim my-5 d-flex flex-column gap-4">
      <AppHeader />

      <AuthenticationPanel />

      <AddFoodsWidget />
      
      {/* <GoalsWidget />

      <DiaryWidget /> */}

    </div>
  );
}

export default App;
