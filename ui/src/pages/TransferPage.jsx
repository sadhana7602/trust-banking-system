import { useState } from "react";
import { authFetch } from "../services/api";
import { useNavigate } from "react-router-dom";

function TransferPage() {
  const [toAccount, setToAccount] = useState("");
  const [amount, setAmount] = useState("");
  const navigate = useNavigate();

  const handleTransfer = async () => {
    const fromAccount = localStorage.getItem("accountNumber");
    const res = await authFetch("/api/accounts/transfer", {
      method: "POST",
      body: JSON.stringify({
        fromAccount,
        toAccount,
        amount
      })
    });

    if (res.ok) {alert("Transfer successful");
        navigate("/dashboard");
    }
    else alert("Transfer failed");
  };

  return (
    <div className="p-10">
      <button
        onClick={() => navigate("/dashboard")}
        className="mb-4 text-blue-500 hover:underline"
      >
        &larr; Back to Dashboard
      </button>
      <h2 className="text-xl font-bold mb-4">Transfer Money</h2>

      <input
        placeholder="To Account Number"
        value={toAccount}
        onChange={(e) => setToAccount(e.target.value)}
        className="border p-2 rounded mr-3"
      />

      <input
        type="number"
        placeholder="Amount"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
        className="border p-2 rounded mr-3"
      />

      <button
        onClick={handleTransfer}
        className="bg-blue-600 text-white px-4 py-2 rounded"
      >
        Transfer
      </button>
    </div>
  );
}

export default TransferPage;