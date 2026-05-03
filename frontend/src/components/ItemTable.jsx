export default function ItemTable({ items }) {
    return (
    <>
    <h1>Items</h1>
    <table className="table">
      <thead>
        <tr>
          <th>Name</th>
          <th>ID</th>
          <th>Image</th>
          <th>Location</th>
          <th>Count</th>
          <th>Tags</th>
        </tr>
      </thead>
      <tbody>
        {items.map(item => (
          <tr key={item.id}>
            <td>{item.name}</td>
            <td>{item.id}</td>
            <td>{item.imageUrl}</td>
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