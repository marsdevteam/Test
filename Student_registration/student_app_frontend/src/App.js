import { Route, Routes } from 'react-router-dom';
import './App.css';
import AboutUs from './components/AboutUs';
import ApplyForm from './components/ApplyForm';
import DashBoard from './components/DashBoard';
import Footer from './components/Footer';
import ForgotPassword from './components/ForgotPassword';
import Home from './components/Home';
import LoginPage from './components/LoginPage';
import Registration from './components/Registration';

function App() {
  return (
    <>
      <Routes>
        <Route exact path="/" element={<LoginPage />} />
        <Route path="/registration" element={<Registration />} />
        <Route path="/forgot_password" element={<ForgotPassword />} />
        <Route path="/home" element={<Home />} >
          <Route path="" element={<DashBoard />} />
          <Route path="apply" element={<ApplyForm />} />
          <Route path="about" element={<AboutUs />} />
        </Route>
      </Routes>
      <Footer />
    </>
  );
}

export default App;
