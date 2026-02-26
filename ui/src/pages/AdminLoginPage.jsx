import { useState } from "react";
import { useNavigate } from "react-router-dom";

function AdminLoginPage() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {

    const response = await fetch(
      "http://localhost:8082/api/admin/login",
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify({
          username,
          password
        })
      }
    );

    if (!response.ok) {
      alert("Invalid admin credentials");
      return;
    }

    const data = await response.json();

    // store admin session
    localStorage.setItem("token", data.token);
    localStorage.setItem("fullName", data.name);
    localStorage.setItem("role", "ADMIN");

    navigate("/admin");
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">
      <div className="bg-white p-8 rounded shadow-md w-80">
        <h2 className="text-xl font-bold mb-4">Admin Login</h2>

        <input
          className="border p-2 w-full mb-3"
          placeholder="Username"
          onChange={(e) => setUsername(e.target.value)}
        />

        <input
          type="password"
          className="border p-2 w-full mb-3"
          placeholder="Password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button
          onClick={handleLogin}
          className="bg-purple-600 text-white px-4 py-2 w-full"
        >
          Login
        </button>

        <div className="mt-4 text-sm text-center">
          <span>User? </span>
          <a href="/login" className="text-blue-600 underline">
            Login here
          </a>
        </div>
      </div>
    </div>
  );
}

export default AdminLoginPage;