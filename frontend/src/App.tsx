import { useState, useEffect } from 'react'
import { Routes, Route } from "react-router-dom";
import HomePage from "./pages/HomePage";
import ItemPage from "./pages/ItemPage";

import ItemTable from "./components/ItemTable";
import Searchbar from "./components/Searchbar";
import TagList from "./components/TagList";
import  Select from 'react-select'
import  CreatableSelect from 'react-select/creatable'

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
