import { useEffect, useState } from "react";
import authFetch from "../utils/authFetch";

export default function AdminTransactionsPage() {

  const [transactions, setTransactions] = useState([]);

  useEffect(() => {
    loadTransactions();
  }, []);

  const loadTransactions = async () => {
    const res = await authFetch("/api/admin/transactions");
    const data = await res.json();
    setTransactions(data);
  };

  return (
    <div className="p-6">
      <h2 className="text-xl font-bold mb-4">All Transactions</h2>

      <table className="w-full border">
        <thead className="bg-gray-100">
          <tr>
            <th>Type</th>
            <th>From</th>
            <th>To</th>
            <th>Amount</th>
            <th>Date</th>
          </tr>
        </thead>

        <tbody>
          {transactions.map(tx => (
            <tr key={tx.id} className="border-t text-center">
              <td>{tx.type}</td>
              <td>{tx.fromAccount || "-"}</td>
              <td>{tx.toAccount || "-"}</td>
              <td>₹ {tx.amount}</td>
              <td>{new Date(tx.createdAt).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}