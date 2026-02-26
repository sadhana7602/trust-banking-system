import { useNavigate } from "react-router-dom";

export default function AdminDashboard() {

  const navigate = useNavigate();

  return (
    <div className="p-6">

      <h2 className="text-xl font-bold mb-6">Admin Dashboard</h2>

      <div className="grid grid-cols-2 gap-4">

        <button
          onClick={() => navigate("/admin/users")}
          className="bg-blue-500 text-white p-4 rounded"
        >
          View Users
        </button>

        <button
          onClick={() => navigate("/admin/accounts")}
          className="bg-green-500 text-white p-4 rounded"
        >
          View Accounts
        </button>

        <button
          onClick={() => navigate("/admin/transactions")}
          className="bg-purple-500 text-white p-4 rounded"
        >
          View Transactions
        </button>

        <button
          onClick={() => navigate("/admin/tickets")}
          className="bg-orange-500 text-white p-4 rounded"
        >
          View Tickets
        </button>

      </div>
    </div>
  );
}