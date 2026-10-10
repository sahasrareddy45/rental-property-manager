import { useEffect,useState } from "react";
import api from "../services/api";

export default function OwnerDashboard(){
  const user=JSON.parse(localStorage.getItem("user")||"null");
  const [properties,setProperties]=useState([]); const [requests,setRequests]=useState([]);
  const empty={title:"",description:"",location:"",monthlyRent:"",propertyType:"Apartment",bedrooms:1,bathrooms:1,imageUrl:""};
  const [form,setForm]=useState(empty);
  async function load(){setProperties((await api.get("/properties/owner/"+user.userId)).data);setRequests((await api.get("/rentals/owner/"+user.userId)).data);}
  useEffect(()=>{load()},[]);
  async function add(e){e.preventDefault();await api.post("/properties/owner/"+user.userId,{...form,monthlyRent:Number(form.monthlyRent),bedrooms:Number(form.bedrooms),bathrooms:Number(form.bathrooms)});alert("Property submitted for admin verification");setForm(empty);load();}
  async function status(id,status){await api.put(`/rentals/${id}/status?status=${status}`);load();}
  return <div className="container"><h1>Owner Dashboard</h1>
    <div className="split"><form className="formbox" onSubmit={add}><h2>Add Property</h2>
      <input placeholder="Title" value={form.title} onChange={e=>setForm({...form,title:e.target.value})} required/>
      <textarea placeholder="Description" value={form.description} onChange={e=>setForm({...form,description:e.target.value})} required/>
      <input placeholder="Location" value={form.location} onChange={e=>setForm({...form,location:e.target.value})} required/>
      <input type="number" placeholder="Monthly rent" value={form.monthlyRent} onChange={e=>setForm({...form,monthlyRent:e.target.value})} required/>
      <select value={form.propertyType} onChange={e=>setForm({...form,propertyType:e.target.value})}><option>Apartment</option><option>House</option><option>Villa</option><option>Room</option></select>
      <input type="number" placeholder="Bedrooms" value={form.bedrooms} onChange={e=>setForm({...form,bedrooms:e.target.value})}/>
      <input type="number" placeholder="Bathrooms" value={form.bathrooms} onChange={e=>setForm({...form,bathrooms:e.target.value})}/>
      <input placeholder="Image URL (optional)" value={form.imageUrl} onChange={e=>setForm({...form,imageUrl:e.target.value})}/>
      <button>Add Property</button>
    </form><div><h2>My Properties</h2>{properties.map(p=><div className="mini" key={p.id}><b>{p.title}</b><span>{p.status}</span><p>₹{p.monthlyRent} · {p.location}</p></div>)}</div></div>
    <h2>Rental Requests</h2>{requests.map(r=><div className="row" key={r.id}><span>{r.property.title} — {r.tenant.name}</span><b>{r.status}</b>{r.status==="PENDING"&&<><button onClick={()=>status(r.id,"APPROVED")}>Approve</button><button className="danger" onClick={()=>status(r.id,"REJECTED")}>Reject</button></>}</div>)}
  </div>;
}
