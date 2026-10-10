import { useEffect,useState } from "react";
import api from "../services/api";

export default function AdminDashboard(){
  const [reports,setReports]=useState({}); const [properties,setProperties]=useState([]);
  const [complaints,setComplaints]=useState([]);
  async function load(){setReports((await api.get("/admin/reports")).data);setProperties((await api.get("/admin/properties")).data);setComplaints((await api.get("/admin/complaints")).data);}
  useEffect(()=>{load()},[]);
  async function verify(id,status){await api.put(`/admin/properties/${id}/verify?status=${status}`);load();}
  async function complaint(id,status){await api.put(`/complaints/${id}/status?status=${status}`);load();}
  return <div className="container"><h1>Administrator Dashboard</h1>
    <div className="stats">{Object.entries(reports).map(([k,v])=><div className="stat" key={k}><b>{v}</b><span>{k}</span></div>)}</div>
    <h2>Property Verification</h2>{properties.map(p=><div className="row" key={p.id}><span><b>{p.title}</b> — {p.location} — {p.owner.name}</span><b>{p.status}</b>{p.status==="PENDING"&&<><button onClick={()=>verify(p.id,"VERIFIED")}>Verify</button><button className="danger" onClick={()=>verify(p.id,"REJECTED")}>Reject</button></>}</div>)}
    <h2>Complaints</h2>{complaints.map(c=><div className="row" key={c.id}><span>{c.subject} — {c.user.name}</span><b>{c.status}</b>{c.status==="OPEN"&&<button onClick={()=>complaint(c.id,"RESOLVED")}>Resolve</button>}</div>)}
  </div>;
}
