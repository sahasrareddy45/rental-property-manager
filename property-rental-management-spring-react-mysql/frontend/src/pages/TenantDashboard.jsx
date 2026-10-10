import { useEffect,useState } from "react";
import api from "../services/api";

export default function TenantDashboard(){
  const user=JSON.parse(localStorage.getItem("user")||"null");
  const [rentals,setRentals]=useState([]); const [payments,setPayments]=useState([]);
  const [complaint,setComplaint]=useState({subject:"",description:""});
  async function load(){const a=await api.get("/rentals/tenant/"+user.userId);setRentals(a.data);const b=await api.get("/payments/tenant/"+user.userId);setPayments(b.data);}
  useEffect(()=>{load()},[]);
  async function pay(r){const amount=prompt("Enter rent amount",r.property.monthlyRent);if(!amount)return;await api.post(`/payments?rentalId=${r.id}&amount=${amount}&method=UPI`);alert("Payment recorded");load();}
  async function complain(e){e.preventDefault();await api.post(`/complaints?userId=${user.userId}`,complaint);alert("Complaint submitted");setComplaint({subject:"",description:""});}
  return <div className="container"><h1>Tenant Dashboard</h1>
    <h2>My Rental Requests</h2><div className="table">{rentals.map(r=><div className="row" key={r.id}><b>{r.property.title}</b><span>{r.status}</span>{r.status==="APPROVED"&&<button onClick={()=>pay(r)}>Pay Rent</button>}</div>)}</div>
    <h2>Payment History</h2><div className="table">{payments.map(p=><div className="row" key={p.id}>₹{p.amount}<span>{p.paymentMethod}</span><span>{p.paymentStatus}</span></div>)}</div>
    <form className="formbox" onSubmit={complain}><h2>Submit Complaint</h2><input placeholder="Subject" value={complaint.subject} onChange={e=>setComplaint({...complaint,subject:e.target.value})} required/><textarea placeholder="Description" value={complaint.description} onChange={e=>setComplaint({...complaint,description:e.target.value})} required/><button>Submit Complaint</button></form>
  </div>;
}
