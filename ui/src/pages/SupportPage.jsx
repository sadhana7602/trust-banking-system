import { useEffect, useState } from "react";

import { authFetch } from "../services/api";
import { useNavigate } from "react-router-dom";




export default function SupportPage() {



  const navigate = useNavigate();
  const [tickets, setTickets] = useState([]);

  const [subject, setSubject] = useState("");

  const [description, setDescription] = useState("");




  const [comments, setComments] = useState([]);

  const [activeTicketId, setActiveTicketId] = useState(null);




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




  // 👉 If already open → close it

  if (activeTicketId === ticketId) {

    setActiveTicketId(null);

    setComments([]);

    return;

  }




  try {

    const res = await authFetch(`/api/support/comments/${ticketId}`);

    const data = await res.json();




    setComments(data);

    setActiveTicketId(ticketId);




  } catch (err) {

    console.error("Error loading comments", err);

  }

};




  const createTicket = async () => {

    await authFetch("/api/support/create", {

      method: "POST",

      headers: { "Content-Type": "application/json" },

      body: JSON.stringify({ userId, subject, description })

    });




    setSubject("");

    setDescription("");

    loadTickets();

  };




  return (

    <div className="p-6">

      <h2 className="text-xl font-bold mb-4">Customer Support</h2>
      <button
          onClick={() => navigate("/dashboard")}
          className="text-blue-500 hover:underline"
        >
          &larr; Back to Dashboard
        </button>



      {/* CREATE TICKET */}

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




      {/* TICKET LIST */}

      {tickets.map(t => (

        <div key={t.id} className="bg-gray-100 p-4 mb-3 rounded">

          <p className="font-semibold">{t.subject}</p>

          <p className="text-sm mb-2">Status: {t.status}</p>




          <button

            onClick={() => loadComments(t.id)}

            className="bg-indigo-500 text-white px-3 py-1 rounded text-sm"

          >

            View Comments

          </button>




          {/* COMMENTS FOR SELECTED TICKET */}

          {activeTicketId === t.id && (

            <div className="mt-3 bg-white p-3 rounded">

              {comments.length === 0 ? (

                <p className="text-sm text-gray-500">No comments yet</p>

              ) : (

                comments.map(c => (

                  <div key={c.id} className="border p-2 rounded mb-2">

                    <p className="text-sm">{c.commentText}</p>

                    <span className="text-xs text-gray-500">

                      {c.commentedBy} • {new Date(c.createdAt).toLocaleString()}

                    </span>

                  </div>

                ))

              )}

            </div>

          )}

        </div>

      ))}

    </div>

  );

}