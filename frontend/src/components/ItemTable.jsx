import { Link } from "react-router-dom";

export default function ItemTable({ items }) {
    return (
    <>
    <table className="table">
      <thead>
        <tr>
          <th>Name</th>
          <th>Location</th>
          <th>Count</th>
          <th>Tags</th>
        </tr>
      </thead>
      <tbody>
        {items.map(item => (
          <tr key={item.id}>
            <td>
                <Link to={`/items/${item.id}`}>{item.name}</Link></td>
            <td>{item.location?.name}</td>
            <td>{item.count}</td>
            <td>{item.tags?.map(tag => (
                <span key={tag.id} className="badge text-bg-light"> {tag.name}</span>
                ))}
            </td>
          </tr>
        ))}
      </tbody>

    </table>
    </>
  );
}