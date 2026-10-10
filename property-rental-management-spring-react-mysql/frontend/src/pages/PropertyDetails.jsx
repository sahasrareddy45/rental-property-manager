import { useEffect,useState } from "react";
import { useParams,useNavigate } from "react-router-dom";
import api from "../services/api";

export default function PropertyDetails(){
  const {id}=useParams(); const [p,setP]=useState(null); const [message,setMessage]=useState("");
  const user=JSON.parse(localStorage.getItem("user")||"null"); const navigate=useNavigate();
  useEffect(()=>{api.get("/properties/"+id).then(r=>setP(r.data)).catch(()=>alert("Property not found"));},[id]);
  async function request(){
    if(!user){navigate("/login");return;}
    try{await api.post(`/rentals/request?tenantId=${user.userId}&propertyId=${id}`,{message});
      alert("Rental request submitted");navigate("/tenant");
    }catch(e){alert(e.response?.data?.message||"Request failed");}
  }
  if(!p)return <div className="container"><p>Loading...</p></div>;
  return <div className="container detail">
    <img src={p.imageUrl}/><div><span className="tag">{p.propertyType}</span><h1>{p.title}</h1>
    <h2>₹{p.monthlyRent}/month</h2><p>📍 {p.location}</p><p>{p.description}</p>
    <p>🛏 {p.bedrooms} Bedrooms &nbsp; 🚿 {p.bathrooms} Bathrooms</p>
    {user?.role==="TENANT" && <><textarea placeholder="Message to owner" value={message} onChange={e=>setMessage(e.target.value)}/><button onClick={request}>Request to Rent</button></>}
    </div></div>;
}
