import { useEffect, useState } from "react";
import { authFetch } from "../services/api";
import { useNavigate } from "react-router-dom";
import jsPDF from "jspdf";

function TransactionsPage() {
  const navigate = useNavigate();

  const downloadPDF = () => {
    const doc = new jsPDF();
    doc.setFontSize(16);
    doc.text("Transaction History", 14, 20);
    doc.setFontSize(12);
    let y = 30;
    txs.forEach((t, index) => {
      const date = new Date(t.createdAt || t.timestamp).toLocaleString();
      const line = `${index + 1}. ${t.type || t.transactionType} - ₹${t.amount} - ${date}`;
      doc.text(line, 14, y);
      y += 10;
      if (y > 280) {
        doc.addPage();
        y = 20;
      }
    });
    doc.save("transactions.pdf");
  };
  const [txs, setTxs] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const load = async () => {
      try {
        const res = await authFetch("/api/transactions/my");
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
      <div className="flex items-center justify-between mb-4">
        <button
          onClick={() => navigate("/dashboard")}
          className="text-blue-500 hover:underline"
        >
          &larr; Back to Dashboard
        </button>
        <button
          onClick={downloadPDF}
          className="bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700"
        >
          Download PDF
        </button>
      </div>
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