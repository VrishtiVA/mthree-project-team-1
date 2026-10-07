import { useState } from 'react';
import './App.css';
import AuthenticationPanel from './components/AuthenticationPanel';
import AddFoodsWidget from './components/AddFoodsWidget';

function App() {

  return (
    <div className="App">
      {/* App Header */}

      <AuthenticationPanel />

      <AddFoodsWidget />
      
      {/* <GoalsWidget />

      <DiaryWidget /> */}

    </div>
  );
}

export default App;
