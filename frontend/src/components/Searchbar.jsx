export default function Searchbar ({searchName}) {
    return <input type="text" placeholder="Click me" onChange={(e) => searchName(e.target.value)}></input>
}