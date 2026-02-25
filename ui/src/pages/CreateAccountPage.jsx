import { useState } from "react";
import { authFetch } from "../services/api";
import { useNavigate } from "react-router-dom";

function CreateAccountPage() {
  const navigate = useNavigate();

  const [accountType, setAccountType] = useState("");
  const [branchName, setBranchName] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();

    const userId = localStorage.getItem("userId");

    const res = await authFetch("/api/accounts/create", {
      method: "POST",
      body: JSON.stringify({
        userId,
        accountType,
        branchName
      })
    });

    if (res.ok) {
      alert("Account created successfully 🎉");
      navigate("/dashboard");
    } else {
      const text = await res.text();
      alert(text);
    }
  };

  return (
    <div className="flex justify-center py-20">
      <form
        onSubmit={handleSubmit}
        className="bg-white p-8 rounded shadow w-96 space-y-4"
      >
        <h2 className="text-xl font-bold text-center">Create Account</h2>

        <select
          value={accountType}
          onChange={(e) => setAccountType(e.target.value)}
          className="w-full border p-2 rounded"
          required
        >
          <option value="">Select Account Type</option>
          <option value="SAVINGS">Savings</option>
          <option value="CURRENT">Current</option>
        </select>

        <input
          type="text"
          placeholder="Branch Name"
          value={branchName}
          onChange={(e) => setBranchName(e.target.value)}
          className="w-full border p-2 rounded"
          required
        />

        <button className="bg-indigo-600 text-white w-full py-2 rounded">
          Create Account
        </button>
      </form>
    </div>
  );
}

export default CreateAccountPage;