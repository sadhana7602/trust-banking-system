import { useEffect, useState } from "react";
import { authFetch } from "../services/api";

function TransactionsPage() {
  const [txs, setTxs] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const load = async () => {
      try {
        const res = await authFetch("/api/transactions/my-history");
        if (res.ok) {
          const data = await res.json();
          setTxs(data);
        } else {
          console.error("Failed to load transactions", res.status);
        }
      } catch (e) {
        console.error(e);
      } finally {
        setLoading(false);
      }
    };
    load();
  }, []);

  if (loading) return <div className="p-6">Loading transactions...</div>;

  return (
    <div className="p-6">
      <h1 className="text-2xl font-semibold mb-4">Transaction History</h1>
      {txs.length === 0 ? (
        <p>No transactions found.</p>
      ) : (
        <div className="space-y-2">
          {txs.map((t) => (
            <div key={t.id || t.transactionId} className="p-3 border rounded">
              <div className="flex justify-between">
                <div>
                  <div className="font-medium">{t.type || t.transactionType}</div>
                  <div className="text-sm text-gray-600">{t.description}</div>
                </div>
                <div className="text-right">
                  <div className="font-semibold">₹ {t.amount}</div>
                  <div className="text-sm text-gray-500">{new Date(t.createdAt || t.timestamp).toLocaleString()}</div>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default TransactionsPage;