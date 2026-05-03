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
          <th>Count</th>
        </tr>
      </thead>
      <tbody>
        {items.map(item => (
          <tr key={item.id}>
            <td>{item.name}</td>
            <td>{item.id}</td>
            <td>{item.imageUrl}</td>
            <td>{item.count}</td>
          </tr>
        ))}
      </tbody>

    </table>
    </>
  );
}