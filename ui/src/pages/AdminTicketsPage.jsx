import { useEffect, useState } from "react";

import { authFetch } from "../services/api";

export default function AdminTicketsPage() {

  const [tickets, setTickets] = useState([]);

  const [selectedTicket, setSelectedTicket] = useState(null);

  const [comments, setComments] = useState([]);

  const [newComment, setNewComment] = useState("");

  useEffect(() => {

    loadTickets();

  }, []);

  const loadTickets = async () => {

    const res = await authFetch("/api/support/all");

    const data = await res.json();

    setTickets(data);

  };

  const openTicket = async (ticket) => {

    setSelectedTicket(ticket);

    const res = await authFetch(`/api/support/comments/${ticket.id}`);

    const data = await res.json();

    setComments(data);

  };

  const addComment = async () => {

  if (!newComment.trim()) return;

  await authFetch("/api/support/comment", {

    method: "POST",

    body: JSON.stringify({

      ticketId: selectedTicket.id,

      commentText: newComment,

      commentedBy: "ADMIN"

    })

  });

  setNewComment(""); // clear input

  // reload comments

  const res = await authFetch(`/api/support/comments/${selectedTicket.id}`);

  const data = await res.json();

  setComments(data);

};

  const updateStatus = async (id, status) => {

    await authFetch(`/api/support/status/${id}?status=${status}`, {

      method: "PUT"

    });

    loadTickets();

  };

  return (

    <div className="p-6 flex gap-6">

      {/* LEFT — TICKET LIST */}

      <div className="w-1/2">

        <h2 className="text-xl font-bold mb-4">Support Tickets</h2>

        {tickets.map(t => (

          <div

            key={t.id}

            onClick={() => openTicket(t)}

            className="bg-white p-3 mb-2 shadow rounded cursor-pointer"

          >

            <p><b>{t.subject}</b></p>

            <p className="text-sm text-gray-600">

              User: {t.user?.fullName} ({t.user?.email})

            </p>


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

      {/* RIGHT — COMMENTS PANEL */}

      {selectedTicket && (

        <div className="w-1/2 bg-gray-50 p-4 rounded shadow">

          <h3 className="font-semibold mb-2">

            Comments — {selectedTicket.subject}

          </h3>

          <div className="h-64 overflow-y-auto mb-3">

            {comments.map(c => (

              <div key={c.id} className="mb-2">

                <p className="text-sm text-gray-600">{c.commentedBy}</p>

                <div className="bg-white p-2 rounded shadow">

                  {c.commentText}

                </div>

              </div>

            ))}

          </div>

          <textarea

            className="border p-2 w-full mb-2"

            placeholder="Write comment..."

            value={newComment}

            onChange={e => setNewComment(e.target.value)}

          />

          <button

            onClick={addComment}

            className="bg-indigo-600 text-white px-4 py-2 rounded"

          >

            Send Reply

          </button>

        </div>

      )}

    </div>

  );

}