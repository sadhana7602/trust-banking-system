import { useEffect, useState } from "react";

import { authFetch } from "../services/api";




export default function AdminTicketsPage() {




  const [tickets, setTickets] = useState([]);




  useEffect(() => {

    loadTickets();

  }, []);




  const loadTickets = async () => {

    const res = await authFetch("/api/support/all");

    const data = await res.json();

    setTickets(data);

  };




  const updateStatus = async (id, status) => {

    await authFetch(`/api/support/status/${id}?status=${status}`, {

      method: "PUT"

    });

    loadTickets();

  };




  return (

    <div className="p-6">

      <h2 className="text-xl font-bold mb-4">Support Tickets</h2>




      {tickets.map(t => (

        <div key={t.id} className="bg-white p-4 mb-3 shadow rounded">

          <p><b>{t.subject}</b></p>

          <p>Status: {t.status}</p>




          <div className="flex gap-2 mt-2">

            <button onClick={() => updateStatus(t.id, "IN_PROGRESS")}

                    className="bg-yellow-500 text-white px-2 py-1">

              In Progress

            </button>




            <button onClick={() => updateStatus(t.id, "RESOLVED")}

                    className="bg-green-600 text-white px-2 py-1">

              Resolve

            </button>

          </div>

        </div>

      ))}

    </div>

  );

}