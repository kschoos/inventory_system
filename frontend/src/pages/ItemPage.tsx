import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import  CreatableSelect from 'react-select/creatable'
import type { Item } from "../types/Item";
import type { Location } from "../types/Location";
import type { Tag } from "../types/Tag";
import type { SelectItem } from "../types/SelectItem";
import type { SingleValue } from "react-select";

function ItemPage () {
    const { id } = useParams();

    const [item, setItem] = useState<Item | null>(null);
    const [isEditing, setEditing] = useState(false);
    const [locations, setLocations] = useState<Location[]>([]);
    const [tags, setTags] = useState<Tag[]>([]);

    function fetchLocations() {
        fetch("/api/location")
        .then(res => res.json())
        .then((i) => {console.log(i); setLocations(i)});
    }

    function fetchTags() {
        fetch("/api/tag")
        .then(res => res.json())
        .then((i) => {console.log(i); setTags(i)});
    }  

    function fetchItem() {
        fetch(`/api/item/${item?.id}`)
        .then(res => res.json())
        .then(setItem);
    }
    
    function createOptionTags(createdOption: String) {
        fetch("/api/tag?name="+createdOption, {
          method: "POST",
        })
        .then(fetchTags);
    }

    function createOptionLocations(createdOption: String) {
        fetch("/api/location?name="+createdOption, {
          method: "POST",
        })
        .then(fetchLocations);
    }
    
    function changeLocation(location: SingleValue<SelectItem>) {
        if (location === null) {
            return;
        }

        setItem(prevItem => {
            if (!prevItem) return null;

            return {...prevItem,
                location: {
                    id: location?.value,
                    name: location.label
                }
            }
        })
    }

    function changeTags(tags: readonly SelectItem[]) {
        setItem(prevItem => {
            return prevItem ? {
                ...prevItem,
                tags: tags.map((tag) => {return {"id": tag.value, "name": tag.label}})
            } : null;
        })
    }

    function changeCount(event: React.ChangeEvent<HTMLInputElement>) {
        const newCount = event.target.value;
        if (Number.isInteger(Number(newCount))) {
            setItem(prevItem => ( prevItem ? {
                ...prevItem,
                count: Number(newCount)
            } : null));
        }
    }

    function changeName(event: React.ChangeEvent<HTMLInputElement>) {
        const newName = event.target.value;

        setItem(prevItem => ( prevItem ? {
            ...prevItem,
            name: newName
        } : null));
    }

    function updateItem(){
        if (item == null) {
            return;
        }

        const {
            location,
            tags,
            ...rest
        } = item;

        const itemCreate = {
            ...rest,
            tagIds: item.tags?.map((tag) => tag.id),
            locationId: item.location?.id
        }


        fetch("/api/item", {
            method: "PUT",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify(itemCreate)
        })
        .then(fetchItem);
    }

    useEffect(() => {
        fetchLocations();
        fetchTags();
    }, []);

    useEffect(() => {
        fetch(`/api/item/${id}`)
        .then(res => res.json())
        .then(data => setItem(data));
    }, [id]);

    function clickEditSave() {
        if (isEditing) {
            updateItem();
        }

        setEditing(!isEditing);

        fetchItem();
    }

    if (!item) return <p>Loading...</p>

    return (
        <div className="container">
            <div className="row pt-3">
                <div className="col-3"></div>
                <div className="col-6">
                    <div className="card">
                        <div className="card-body">
                            <h5 className="card-title">
                                {item.name}
                            </h5>
                            <img src={item.imageUrl} className="card-img mb-3"></img>
                            <div className="input-group mb-3">
                                <span className="input-group-text">Name: </span>
                                {
                                    isEditing ?
                                    <input type="text" placeholder={item.name} onChange={changeName} className="form-control text-end"></input>
                                    :
                                    <span className="form-control text-end">{item.name}</span>
                                }
                            </div>
                            <div className="input-group mb-3">
                                <span className="input-group-text">Location: </span>
                                {
                                    isEditing ?
                                    <CreatableSelect className="form-control" 
                                                     closeMenuOnSelect={false} 
                                                     onCreateOption={createOptionLocations}
                                                     onChange={changeLocation}
                                                     options={locations.map((location: Location) => {return {'value': location.id, 'label': location.name}})} 
                                                     placeholder="Select Location..."
                                                     defaultValue={{'value': item.location?.id, 'label': item.location?.name}}
                                    />
                                    :
                                    <span className="form-control text-end">{item.location?.name}</span>
                                }
                            </div>
                            <div className="input-group mb-3">
                                <span className="input-group-text">Count: </span>
                                {
                                    isEditing ?
                                    <input type="text" placeholder={String(item.count)} onChange={changeCount} className="form-control text-end"></input>
                                    :
                                    <span className="form-control text-end">{item.count}</span>
                                }
                            </div>
                            <div className="input-group mb-3">
                                <span className="input-group-text">Tags: </span>
                                {
                                    isEditing ? 
                                    <CreatableSelect className="form-control"
                                                     closeMenuOnSelect={false} 
                                                     onCreateOption={createOptionTags}
                                                     onChange={changeTags}
                                                     isMulti 
                                                     options={tags.map((tag) => {return {'value': tag.id, 'label': tag.name}})} 
                                                     placeholder="Select Tags..."
                                                     defaultValue={item.tags.map((tag) => {return {'value': tag.id, 'label': tag.name}})} 
                                    />
                                    :
                                    <span className="form-control text-end"> 
                                        {item.tags?.map(tag => (
                                            <span key={tag.id} className="badge text-bg-light"> {tag.name}</span>
                                        ))}
                                    </span>
                                }
                            </div>
                            <div className="text-end">
                                <a href="#" className="btn btn-primary" onClick={clickEditSave}>{isEditing ? "Save" : "Edit"}</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div className="col-3"></div>
            </div>
            <div className="row">

            </div>
        </div>
    );
}

export default ItemPage