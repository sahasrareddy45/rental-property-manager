import {BrowserRouter,Routes,Route} from "react-router-dom";
import Navbar from "./components/Navbar";
import Home from "./pages/Home";
import Login from "./pages/Login";
import Register from "./pages/Register";
import PropertyDetails from "./pages/PropertyDetails";
import TenantDashboard from "./pages/TenantDashboard";
import OwnerDashboard from "./pages/OwnerDashboard";
import AdminDashboard from "./pages/AdminDashboard";

export default function App(){
 return <BrowserRouter><Navbar/><Routes>
   <Route path="/" element={<Home/>}/><Route path="/login" element={<Login/>}/><Route path="/register" element={<Register/>}/>
   <Route path="/property/:id" element={<PropertyDetails/>}/><Route path="/tenant" element={<TenantDashboard/>}/>
   <Route path="/owner" element={<OwnerDashboard/>}/><Route path="/admin" element={<AdminDashboard/>}/>
 </Routes></BrowserRouter>
}
