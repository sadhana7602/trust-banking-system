import { useEffect, useState } from "react";
import authFetch from "../utils/authFetch";

export default function AdminTicketsPage() {

  const [tickets, setTickets] = useState([]);

  useEffect(() => {
    loadTickets();
  }, []);

  const loadTickets = async () => {
    const res = await authFetch("/api/admin/tickets");
    const data = await res.json();
    setTickets(data);
  };

  return (
    <div className="p-6">
      <h2 className="text-xl font-bold mb-4">Support Tickets</h2>

      <table className="w-full border">
        <thead className="bg-gray-100">
          <tr>
            <th>User</th>
            <th>Subject</th>
            <th>Status</th>
          </tr>
        </thead>

        <tbody>
          {tickets.map(t => (
            <tr key={t.id} className="border-t text-center">
              <td>{t.user?.fullName}</td>
              <td>{t.subject}</td>
              <td>{t.status}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}