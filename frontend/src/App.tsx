import { useState, useEffect } from 'react'
import ItemTable from "./components/ItemTable";
import Searchbar from "./components/Searchbar";

import './App.css'

function App() {
  const [items, setItems] = useState([])

  useEffect(() => {
    fetch("http://localhost:8080/item")
    .then(res => res.json())
    .then(setItems);
  }, []);

  function searchName(name) {
    fetch("http://localhost:8080/item?name=" + name)
    .then(res => res.json())
    .then(setItems);
  }

  return (
    <div className="container">
      <div className="row">
        <Searchbar searchName={searchName}/>
      </div>
      <div className="row">
        <div className="col-3"></div>
        <div className="col-6">'
        <ItemTable items={items}/>
        </div>
        <div className="col-3"></div>
      </div>
    </div>
  );
}

export default App
