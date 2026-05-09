import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";
import  CreatableSelect from 'react-select/creatable'

function ItemPage () {
    const { id } = useParams();

    const [item, setItem] = useState(null);
    const [isEditing, setEditing] = useState(false);
    const [locations, setLocations] = useState([]);
    const [tags, setTags] = useState([]);

    function fetchLocations() {
        fetch("http://localhost:8080/location")
        .then(res => res.json())
        .then((i) => {console.log(i); setLocations(i)});
    }

    function fetchTags() {
        fetch("http://localhost:8080/tag")
        .then(res => res.json())
        .then((i) => {console.log(i); setTags(i)});
    }  

    function fetchItem() {
        fetch(`http://localhost:8080/item/${item.id}`)
        .then(res => res.json())
        .then(setItem);
    }
    
    function createOptionTags(createdOption) {
        fetch("http://localhost:8080/tag?name="+createdOption, {
          method: "POST",
        })
        .then(fetchTags);
    }

    function createOptionLocations(createdOption) {
        fetch("http://localhost:8080/location?name="+createdOption, {
          method: "POST",
        })
        .then(fetchLocations);
    }
    
    function changeLocation(location) {
        setItem(prevItem => ({
            ...prevItem,
            location: {
                id: location.value,
                name: location.label
            }
        }))
    }

    function changeTags(tags) {
        setItem(prevItem => ({
            ...prevItem,
            tags: tags.map((tag) => {return {"id": tag.value, "name": tag.label}})
        }))
    }

    function changeCount(event) {
        const newCount = event.target.value;
        if (Number.isInteger(Number(newCount))) {
            setItem(prevItem => ({
                ...prevItem,
                count: newCount
            }));
        }
    }

    function changeName(event) {
        const newName = event.target.value;

        setItem(prevItem => ({
            ...prevItem,
            name: newName
        }));
    }

    function updateItem(){
        const {
            location,
            tags,
            ...itemCreate
        } = item;

        itemCreate.tagIds = item.tags?.map((tag) => tag.id);
        itemCreate.locationId = item.location?.id;

        fetch("http://localhost:8080/item", {
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
        fetch(`http://localhost:8080/item/${id}`)
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
                                                     options={locations.map((location) => {return {'value': location.id, 'label': location.name}})} 
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
                                    <input type="text" placeholder={item.count} onChange={changeCount} className="form-control text-end"></input>
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