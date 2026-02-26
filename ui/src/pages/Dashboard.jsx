import { useEffect, useState } from "react";
import { authFetch } from "../services/api";
import { useNavigate } from "react-router-dom";

function Dashboard() {
  const navigate = useNavigate();
  const name = localStorage.getItem("fullName");

  const [account, setAccount] = useState(null);
  const [notFound, setNotFound] = useState(false);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const loadAccount = async () => {
      try {
        const res = await authFetch("/api/accounts/my-account");

        if (res.ok) {
          const data = await res.json();

          // ✅ NEW FIX: backend returns null when no account
          if (!data) {
            setNotFound(true);
            return;
          }

          setAccount(data);
          localStorage.setItem("accountNumber", data.accountNumber);
        }
      } catch (err) {
        console.error("Error loading account", err);
      } finally {
        setLoading(false);
      }
    };

    loadAccount();
  }, []);

  if (loading) return <div className="p-10">Loading account details...</div>;

  return (
    <div className="p-10 space-y-6">
      <h1 className="text-2xl font-bold">Welcome {name} 👋</h1>

      {/* 🔴 NO ACCOUNT STATE */}
      {notFound && (
        <div className="bg-yellow-100 p-6 rounded shadow">
          <p className="font-semibold">No account found.</p>
          <p className="text-sm text-gray-600">
            Please create your bank account to continue.
          </p>

          <button
            onClick={() => navigate("/create-account")}
            className="bg-indigo-600 text-white px-4 py-2 mt-3 rounded"
          >
            Create Account
          </button>
        </div>
      )}

      {/* 🟢 ACCOUNT EXISTS */}
      {account && (
        <>
          <div className="bg-indigo-600 text-white p-6 rounded shadow">
            <h2 className="text-sm">Current Balance</h2>
            <p className="text-3xl font-bold">₹ {account.balance}</p>
          </div>

          <div className="bg-white p-6 rounded shadow">
            <h2 className="font-semibold mb-3">Account Details</h2>
            <p><strong>Account Number:</strong> {account.accountNumber}</p>
            <p><strong>Account Type:</strong> {account.accountType}</p>
            <p><strong>Branch:</strong> {account.branchName}</p>
          </div>

          <div className="bg-white p-6 rounded shadow">
            <h2 className="font-semibold mb-3">Quick Actions</h2>

            <div className="flex gap-3">
              <button
                onClick={() => navigate("/deposit")}
                className="bg-green-600 text-white px-4 py-2 rounded"
              >
                Deposit
              </button>

              <button
                onClick={() => navigate("/withdraw")}
                className="bg-yellow-600 text-white px-4 py-2 rounded"
              >
                Withdraw
              </button>

              <button
                onClick={() => navigate("/transfer")}
                className="bg-blue-600 text-white px-4 py-2 rounded"
              >
                Transfer
              </button>

              <button
                onClick={() => navigate("/transactions")}
                className="bg-indigo-600 text-white px-4 py-2 rounded"
              >
                Transaction History
              </button>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default Dashboard;