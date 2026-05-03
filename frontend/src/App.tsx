import { useState, useEffect } from 'react'
import ItemTable from "./components/ItemTable";

import './App.css'

function App() {
  const [items, setItems] = useState([])

  useEffect(() => {
    fetch("http://localhost:8080/item")
    .then(res => res.json())
    .then(setItems);
  }, []);

  return (
    <div className="container">
      <div className="row">
        <div className="col-3"></div>
        <div className="col-6">
        <ItemTable items={items}/>
        </div>
        <div className="col-3"></div>
      </div>
    </div>
  );
}

export default App
