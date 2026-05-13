interface SearchbarProps {
    setName: React.Dispatch<React.SetStateAction<String>>;
}

export default function Searchbar ({setName}: SearchbarProps) {
    return <input className="form-control" type="text" placeholder="Search Name..." onChange={(e) => {setName(e.target.value)}}></input>
}