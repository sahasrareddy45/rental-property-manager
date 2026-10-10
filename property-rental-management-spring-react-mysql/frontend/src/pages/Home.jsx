import { useEffect,useState } from "react";
import { Link } from "react-router-dom";
import api from "../services/api";

export default function Home(){
  const [properties,setProperties]=useState([]);
  const [location,setLocation]=useState("");
  const [maxRent,setMaxRent]=useState("");
  const [type,setType]=useState("");

  async function load(){
    try{
      const params={};
      if(location)params.location=location;
      if(maxRent)params.maxRent=maxRent;
      if(type)params.type=type;
      const r=await api.get("/properties",{params});
      setProperties(r.data);
    }catch(e){alert("Unable to load properties");}
  }
  useEffect(()=>{load()},[]);
  return <div>
    <section className="hero">
      <div><h1>Find a place you can call home.</h1>
      <p>Search verified rental properties, send requests and manage your rental online.</p></div>
    </section>
    <div className="searchbar">
      <input placeholder="Location" value={location} onChange={e=>setLocation(e.target.value)}/>
      <input type="number" placeholder="Maximum rent" value={maxRent} onChange={e=>setMaxRent(e.target.value)}/>
      <select value={type} onChange={e=>setType(e.target.value)}>
        <option value="">All types</option><option>Apartment</option><option>House</option><option>Villa</option><option>Room</option>
      </select>
      <button onClick={load}>Search</button>
    </div>
    <section className="container"><h2>Available Properties</h2>
      <div className="grid">{properties.map(p=><article className="card" key={p.id}>
        <img src={p.imageUrl || "https://images.unsplash.com/photo-1560185008-b033106af5c3"} />
        <div className="cardbody"><span className="tag">{p.propertyType}</span>
        <h3>{p.title}</h3><p>📍 {p.location}</p><p>🛏 {p.bedrooms} Bedrooms &nbsp; 🚿 {p.bathrooms} Bathrooms</p>
        <strong>₹{p.monthlyRent}/month</strong><br/><Link className="btn" to={"/property/"+p.id}>View Details</Link></div>
      </article>)}</div>
    </section>
  </div>;
}
