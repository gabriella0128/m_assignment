import './App.css';
import { BrowserRouter } from 'react-router-dom';
import Home from "./components/Home/Home";
import MainRoute from './components/MainRoutes';

function App() {
  return (
      <BrowserRouter>
        <MainRoute>
          <Home/>
        </MainRoute>
      </BrowserRouter>

  );
}

export default App;
