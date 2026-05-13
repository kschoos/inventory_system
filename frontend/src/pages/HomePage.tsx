import { useState, useEffect } from 'react'
import ItemTable from "../components/ItemTable";
import Searchbar from "../components/Searchbar";
import  Select from 'react-select';
import  CreatableSelect from 'react-select/creatable';
import type { SingleValue } from "react-select";

import type { SelectItem } from "../types/SelectItem";
import type { Location } from "../types/Location";
import type { Tag } from "../types/Tag";
import type { Item } from "../types/Item";

function HomePage() {
  const [items, setItems] = useState<Item[]>([])
  const [tags, setTags] = useState<Tag[]>([])
  const [activeTags, setActiveTags] = useState<number[]>([])
  const [name, setName] = useState<String>("")
  const [locations, setLocations] = useState<Location[]>([])
  const [activeLocation, setActiveLocation] = useState<number>(0)

  function fetchAll() {
    fetch("/item")
    .then(res => res.json())
    .then((i) => {console.log(i); setItems(i)});

    fetch("/tag")
    .then(res => res.json())
    .then((i) => {console.log(i); setTags(i)});

    fetch("/location")
    .then(res => res.json())
    .then((i) => {console.log(i); setLocations(i)});
  }

  useEffect(() => {
    fetchAll();
  }, []);


  function selectChangeLocation(activeItem: SingleValue<SelectItem>) {
    if (activeItem === null) {
      return;
    }

    setActiveLocation(activeItem.value);
  }

  function selectChangeTags(activeItems: readonly SelectItem[]) {
    setActiveTags(activeItems.map((item) => item.value));
  }

  function createOptionTags(createdOption: String) {
    console.log(createdOption);

    fetch("/tag?name="+createdOption, {
      method: "POST",
    })
    .then(fetchAll);
  }

  function search() {
    const base_url = "/item";
    const name_param = "name="
    const tag_param = "tag_ids="
    const location_param = "location_id="

    var url = base_url + "?" + name_param + name;
    url    += activeTags && activeTags.length > 0 ? "&" + activeTags.reduce((p, t, i) => p + (i == 0 ? "" : "&") + tag_param + t, "") : "";
    url    += activeLocation ? "&" + location_param + activeLocation : "";

    console.log(url)


    fetch(url)
    .then(res => res.json())
    .then(setItems);
  }

  useEffect(() => {
    search();
  }, [name, activeTags, activeLocation])

  return (
    <div className="container">
      <div className="row">
          <h1>Items</h1>
      </div>
      <div className="row">
        <div className="col-3"></div>
        <div className="col-3">
          <Searchbar setName={setName}/> 
        </div>
        <div className="col-4">
          <Select isClearable onChange={selectChangeLocation} options={locations.map((location) => {return {'value': location.id, 'label': location.name}})}/>
        </div>
      </div>
      <div className="row pt-3">
        <div className="col-3"></div>
        <div className="col-7">
          <CreatableSelect closeMenuOnSelect={false} isMulti onCreateOption={createOptionTags} onChange={selectChangeTags} options={tags.map((tag) => {return {'value': tag.id, 'label': tag.name}})} placeholder="Select Tags..."/>
        </div>
        <div className="col-2"></div>
      </div>
      <div className="row pt-3">
        <div className="col-3"></div>
        <div className="col-7">
          <ItemTable items={items}/>
        </div>
        <div className="col-2">
          {/* <TagList tags={tags} activeTags={activeTags} setActiveTags={setActiveTagsWrap}/> */}
        </div>
      </div>
    </div>
  );
}

export default HomePage
