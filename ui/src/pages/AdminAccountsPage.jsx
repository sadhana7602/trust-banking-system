import { useEffect, useState } from "react";
import authFetch from "../utils/authFetch";

export default function AdminAccountsPage() {

  const [accounts, setAccounts] = useState([]);

  useEffect(() => {
    loadAccounts();
  }, []);

  const loadAccounts = async () => {
    const res = await authFetch("/api/admin/accounts");
    const data = await res.json();
    setAccounts(data);
  };

  return (
    <div className="p-6">
      <h2 className="text-xl font-bold mb-4">All Accounts</h2>

      <table className="w-full border">
        <thead className="bg-gray-100">
          <tr>
            <th>Account Number</th>
            <th>Type</th>
            <th>Branch</th>
            <th>Balance</th>
          </tr>
        </thead>

        <tbody>
          {accounts.map(acc => (
            <tr key={acc.id} className="border-t text-center">
              <td>{acc.accountNumber}</td>
              <td>{acc.accountType}</td>
              <td>{acc.branchName}</td>
              <td>₹ {acc.balance}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}