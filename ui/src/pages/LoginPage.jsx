import { useState } from "react";
import { useNavigate } from "react-router-dom";

function LoginPage() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const handleLogin = async () => {
    const response = await fetch(
      `http://localhost:8082/api/users/login?email=${email}&password=${password}`,
      { method: "POST" }
    );

    if (!response.ok) {
      const error = await response.text();
      alert(error);
      return;
    }

    const data = await response.json();

    localStorage.setItem("token", data.token);
    localStorage.setItem("email", data.email);
    localStorage.setItem("name", data.fullName); // 👈 NEW

    navigate("/dashboard");
  };

  return (
    <div className="flex items-center justify-center h-screen bg-gray-100">
      <div className="bg-white p-8 rounded shadow-md w-80">
        <h2 className="text-xl font-bold mb-4">Login</h2>

        <input
          className="border p-2 w-full mb-3"
          placeholder="Email"
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          type="password"
          className="border p-2 w-full mb-3"
          placeholder="Password"
          onChange={(e) => setPassword(e.target.value)}
        />

        <button
          onClick={handleLogin}
          className="bg-blue-500 text-white px-4 py-2 w-full"
        >
          Login
        </button>
              <div className="mt-4 text-sm text-center">
          <span>Admin? </span>
          <a href="/admin-login" className="text-blue-600 underline">
          Login here
          </a>
          </div>
          <p className="text-center mt-3">
          Don’t have an account?
          <a href="/register" className="text-indigo-600 ml-1">Register</a>
        </p>
      </div>
    </div>
  );
}

export default LoginPage;