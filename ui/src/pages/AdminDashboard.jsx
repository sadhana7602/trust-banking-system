import { useNavigate } from "react-router-dom";

export default function AdminDashboard() {

  const navigate = useNavigate();

  return (
    <div className="p-6">

      <h1 className="text-2xl font-bold mb-6">
        Admin Dashboard
      </h1>

      <div className="grid grid-cols-2 md:grid-cols-4 gap-4">

        <button
          onClick={() => navigate("/admin/users")}
          className="bg-blue-600 text-white p-4 rounded shadow"
        >
          View Users
        </button>

        <button
          onClick={() => navigate("/admin/accounts")}
          className="bg-green-600 text-white p-4 rounded shadow"
        >
          View Accounts
        </button>

        <button
          onClick={() => navigate("/admin/transactions")}
          className="bg-purple-600 text-white p-4 rounded shadow"
        >
          View Transactions
        </button>

        <button
          onClick={() => navigate("/admin/tickets")}
          className="bg-orange-600 text-white p-4 rounded shadow"
        >
          Support Tickets
        </button>

      </div>

    </div>
  );
}