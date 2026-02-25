import { useEffect, useState } from "react";
import { authFetch } from "../services/api";
import { useNavigate } from "react-router-dom";

function Dashboard() {
  const name = localStorage.getItem("name");
  const navigate = useNavigate();

  const [account, setAccount] = useState(null);
  const [loading, setLoading] = useState(true);
  const [notFound, setNotFound] = useState(false);

  useEffect(() => {
    const fetchAccount = async () => {
      try {
        const res = await authFetch("/api/accounts/my-account");

        if (res.status === 404) {
          setNotFound(true);
          return;
        }

        if (res.ok) {
          const data = await res.json();
          setAccount(data);
        }
      } catch (err) {
        console.error(err);
      } finally {
        setLoading(false);
      }
    };

    fetchAccount();
  }, []);

  if (loading) return <div className="p-10">Loading...</div>;

  return (
    <div className="p-10 space-y-6">
      <h1 className="text-2xl font-bold">Welcome {name} 👋</h1>

      {/* ❌ ACCOUNT NOT FOUND */}
      {notFound && (
  <div className="bg-yellow-100 p-6 rounded">
    <p className="font-semibold">No account found.</p>

    <button
      onClick={() => navigate("/create-account")}
      className="mt-3 bg-indigo-600 text-white px-4 py-2 rounded"
    >
      Create Account
    </button>
  </div>
)}

      {/* ✅ ACCOUNT EXISTS */}
      {account && (
        <>
          <div className="bg-indigo-600 text-white p-6 rounded-lg">
            <h2>Current Balance</h2>
            <p className="text-3xl font-bold">₹ {account.balance}</p>
          </div>

          <div className="bg-white p-6 rounded shadow">
            <h2 className="font-semibold mb-2">Account Info</h2>
            <p>Account Number: {account.accountNumber}</p>
            <p>Account Type: {account.accountType}</p>
            <p>Branch: {account.branchName}</p>
          </div>

          {/* QUICK LINKS */}
          <div className="bg-white p-6 rounded shadow">
            <h2 className="font-semibold mb-3">Quick Actions</h2>
            <div className="flex gap-3">
                <button onClick={() => navigate("/deposit")} className="bg-green-500 text-white px-4 py-2 rounded">
                Deposit
              </button>

              <button onClick={() => navigate("/withdraw")} className="bg-yellow-500 text-white px-4 py-2 rounded">
                Withdraw
              </button>

              <button onClick={() => navigate("/transfer")} className="bg-blue-500 text-white px-4 py-2 rounded">
                Transfer
              </button>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

export default Dashboard;