export default function Searchbar ({setName}) {
    return <input className="form-control" type="text" placeholder="Search Name..." onChange={(e) => {setName(e.target.value)}}></input>
}