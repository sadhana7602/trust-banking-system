import { useState } from "react";
import { API_URL } from "../services/api";
import { useNavigate } from "react-router-dom";

function RegisterPage() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    fullName: "",
    email: "",
    password: "",
    phoneNumber: "",
    age: "",
    addressLine1: "",
    addressLine2: "",
    city: "",
    state: "",
    postalCode: ""
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleRegister = async (e) => {
    e.preventDefault();

    const res = await fetch(`${API_URL}/api/users/register`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        ...form,
        age: Number(form.age)
      })
    });

    if (res.ok) {
      alert("Registration successful 🎉");
      navigate("/login");
    } else {
      const text = await res.text();
      alert("Registration failed: " + text);
    }
  };

  return (
    <div className="flex justify-center py-10">
      <form
        onSubmit={handleRegister}
        className="bg-white p-8 rounded-lg shadow w-[420px] space-y-3"
      >
        <h2 className="text-xl font-bold text-center mb-3">
          Create Your Account
        </h2>

        <input name="fullName" placeholder="Full Name"
          onChange={handleChange} className="w-full border p-2 rounded" required />

        <input name="email" placeholder="Email"
          onChange={handleChange} className="w-full border p-2 rounded" required />

        <input type="password" name="password" placeholder="Password"
          onChange={handleChange} className="w-full border p-2 rounded" required />

        <input name="phoneNumber" placeholder="Phone Number"
          onChange={handleChange} className="w-full border p-2 rounded" required />

        <input name="age" placeholder="Age"
          onChange={handleChange} className="w-full border p-2 rounded" required />

        <input name="addressLine1" placeholder="Address Line 1"
          onChange={handleChange} className="w-full border p-2 rounded" />

        <input name="addressLine2" placeholder="Address Line 2"
          onChange={handleChange} className="w-full border p-2 rounded" />

        <input name="city" placeholder="City"
          onChange={handleChange} className="w-full border p-2 rounded" />

        <input name="state" placeholder="State"
          onChange={handleChange} className="w-full border p-2 rounded" />

        <input name="postalCode" placeholder="Postal Code"
          onChange={handleChange} className="w-full border p-2 rounded" />

        <button className="bg-indigo-600 text-white w-full py-2 rounded mt-2">
          Register
        </button>
      </form>
    </div>
  );
}

export default RegisterPage;