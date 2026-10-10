import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

export default function Login(){
  const [email,setEmail]=useState(""); const [password,setPassword]=useState("");
  const navigate=useNavigate();
  async function submit(e){
    e.preventDefault();
    try{
      const r=await api.post("/auth/login",{email,password});
      localStorage.setItem("user",JSON.stringify(r.data));
      navigate(r.data.role==="ADMIN"?"/admin":r.data.role==="OWNER"?"/owner":"/tenant");
    }catch(e){alert(e.response?.data?.message||"Login failed");}
  }
  return <div className="auth"><form onSubmit={submit}><h2>Login</h2>
    <input type="email" placeholder="Email" value={email} onChange={e=>setEmail(e.target.value)} required/>
    <input type="password" placeholder="Password" value={password} onChange={e=>setPassword(e.target.value)} required/>
    <button>Login</button><p>Demo: admin@rental.com / admin123</p>
  </form></div>;
}
