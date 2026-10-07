import './App.css';
import AuthenticationPanel from './components/AuthenticationPanel';
import BarcodeScanner from './components/BarcodeScanner';

function App() {
  return (
    <div className="App">

      <AuthenticationPanel />

      <BarcodeScanner />
      
    </div>
  );
}

export default App;
