import { useEffect, useState } from "react";
import authFetch from "../utils/authFetch";

export default function AdminUsersPage() {

  const [users, setUsers] = useState([]);

  useEffect(() => {
    loadUsers();
  }, []);

  const loadUsers = async () => {
    const res = await authFetch("/api/admin/users");
    const data = await res.json();
    setUsers(data);
  };

  return (
    <div className="p-6">
      <h2 className="text-xl font-bold mb-4">All Users</h2>

      <table className="w-full border">
        <thead className="bg-gray-100">
          <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Phone</th>
            <th>City</th>
          </tr>
        </thead>

        <tbody>
          {users.map(u => (
            <tr key={u.id} className="border-t text-center">
              <td>{u.fullName}</td>
              <td>{u.email}</td>
              <td>{u.phoneNumber}</td>
              <td>{u.city}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}