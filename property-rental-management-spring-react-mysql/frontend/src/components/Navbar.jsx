import { Link, useNavigate } from "react-router-dom";

export default function Navbar(){
  const navigate=useNavigate();
  const user=JSON.parse(localStorage.getItem("user")||"null");
  function logout(){localStorage.removeItem("user");navigate("/login");}
  return <nav className="nav">
    <Link className="brand" to="/">🏠 RentEasy</Link>
    <div className="navlinks">
      <Link to="/">Properties</Link>
      {user?.role==="TENANT" && <Link to="/tenant">Tenant Dashboard</Link>}
      {user?.role==="OWNER" && <Link to="/owner">Owner Dashboard</Link>}
      {user?.role==="ADMIN" && <Link to="/admin">Admin Dashboard</Link>}
      {!user ? <><Link to="/login">Login</Link><Link className="btn" to="/register">Register</Link></> :
        <button className="btn danger" onClick={logout}>Logout</button>}
    </div>
  </nav>;
}
