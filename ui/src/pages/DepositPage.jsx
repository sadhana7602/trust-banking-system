import { useState } from "react";
import { authFetch } from "../services/api";

function DepositPage() {
  const [amount, setAmount] = useState("");

  const handleDeposit = async () => {
    const accountNumber = localStorage.getItem("accountNumber");

    const res = await authFetch(
      `/api/accounts/deposit?accountNumber=${accountNumber}&amount=${amount}`,
      { method: "POST" }
    );

    if (res.ok) alert("Deposit successful");
    else alert("Deposit failed");
  };

  return (
    <div className="p-10">
      <h2 className="text-xl font-bold mb-4">Deposit Money</h2>

      <input
        type="number"
        placeholder="Enter amount"
        value={amount}
        onChange={(e) => setAmount(e.target.value)}
        className="border p-2 rounded mr-3"
      />

      <button
        onClick={handleDeposit}
        className="bg-green-600 text-white px-4 py-2 rounded"
      >
        Deposit
      </button>
    </div>
  );
}

export default DepositPage;