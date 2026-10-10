import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

export default function Register(){
  const [form,setForm]=useState({name:"",email:"",password:"",role:"TENANT"});
  const navigate=useNavigate();
  function change(e){setForm({...form,[e.target.name]:e.target.value});}
  async function submit(e){
    e.preventDefault();
    try{await api.post("/auth/register",form);alert("Registration successful");navigate("/login");}
    catch(e){alert(e.response?.data?.message||"Registration failed");}
  }
  return <div className="auth"><form onSubmit={submit}><h2>Create Account</h2>
    <input name="name" placeholder="Full name" onChange={change} required/>
    <input name="email" type="email" placeholder="Email" onChange={change} required/>
    <input name="password" type="password" placeholder="Password" onChange={change} required/>
    <select name="role" value={form.role} onChange={change}><option value="TENANT">Tenant</option><option value="OWNER">Property Owner</option></select>
    <button>Create Account</button>
  </form></div>;
}
