import { useEffect } from 'react'
import { Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import ItemPage from "./pages/ItemPage";

import './App.css'

function App() {
  useEffect(() => {
  }, [])

  return (
    <Routes>
      <Route path="/" element={<HomePage/>}/>
      <Route path="/items/:id" element={<ItemPage/>}/>
    </Routes>
  );
}

export default App
