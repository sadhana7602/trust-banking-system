import { useEffect, useState } from "react";

import { authFetch } from "../services/api";

export default function SupportPage() {

  const [tickets, setTickets] = useState([]);

  const [subject, setSubject] = useState("");

  const [description, setDescription] = useState("");

  const [comments, setComments] = useState([]);
  const userId = localStorage.getItem("userId");

  useEffect(() => {

    loadTickets();

  }, []);

  const loadTickets = async () => {

    const res = await authFetch(`/api/support/user/${userId}`);

    const data = await res.json();

    setTickets(data);

  };

  const loadComments = async (ticketId) => {

  try {

    const res = await authFetch(`/api/support/comments/${ticketId}`);

    const data = await res.json();

    setComments(data);   // MUST be array

  } catch (err) {

    console.error("Error loading comments", err);

  }

};
  const createTicket = async () => {

    await authFetch("/api/support/create", {

      method: "POST",

      body: JSON.stringify({ userId, subject, description })

    });

    setSubject("");

    setDescription("");

    loadTickets();

  };

  return (

    <div className="p-6">

      <h2 className="text-xl font-bold mb-4">Customer Support</h2>

      <div className="bg-white p-4 rounded shadow mb-6">

        <input

          className="border p-2 w-full mb-2"

          placeholder="Subject"

          value={subject}

          onChange={e => setSubject(e.target.value)}

        />

        <textarea

          className="border p-2 w-full mb-2"

          placeholder="Describe your issue"

          value={description}

          onChange={e => setDescription(e.target.value)}

        />

        <button

          onClick={createTicket}

          className="bg-indigo-600 text-white px-4 py-2 rounded"

        >

          Submit Ticket

        </button>

      </div>

      {tickets.map(t => (

        <div key={t.id} className="bg-gray-100 p-3 mb-2 rounded">

          <p><b>{t.subject}</b></p>

          <p>Status: {t.status}</p>


                {comments.length === 0 ? (

          <p>No comments yet</p>

        ) : (

          comments.map((c) => (

            <div key={c.id} className="border p-2 rounded mb-2">

              <p className="text-sm">{c.commentText}</p>

              <span className="text-xs text-gray-500">

                {c.commentedBy} • {new Date(c.createdAt).toLocaleString()}

              </span>

            </div>

          ))

        )}

        </div>

      ))}

    </div>

  );

}