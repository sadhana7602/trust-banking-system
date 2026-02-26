import React, { useEffect, useState } from "react";
import { Link, useNavigate, useLocation } from "react-router-dom";

function Header() {
  const navigate = useNavigate();
  const location = useLocation();

  const [name, setName] = useState("");
  const token = localStorage.getItem("token");

  function extractNameFromRaw(raw) {
    if (!raw) return "";
    try {
      const parsed = JSON.parse(raw);
      if (typeof parsed === "string") return parsed;
      if (parsed && typeof parsed === "object") {
        return (
          parsed.fullName ||
          parsed.name ||
          parsed.firstName ||
          parsed.username ||
          ""
        );
      }
    } catch {
      return raw.replace(/^"|"$/g, "").trim();
    }
    return "";
  }

  function findNameInStorage() {
    const keys = ["fullName", "name", "user", "profile"];
    for (const k of keys) {
      const raw = localStorage.getItem(k);
      if (!raw) continue;
      const extracted = extractNameFromRaw(raw);
      if (extracted) return extracted;
    }
    return "";
  }

  // 🔥 IMPORTANT FIX — run whenever route changes or token changes
  useEffect(() => {
    setName(findNameInStorage());
  }, [token, location.pathname]);

  const logout = () => {
    localStorage.clear();
    setName(""); // immediately reset UI
    navigate("/");
  };

  const initial = name ? name.charAt(0).toUpperCase() : "U";

  return (
    <header className="bg-white shadow-md">
      <div className="max-w-6xl mx-auto px-6 py-4 flex items-center">
        <h1 className="text-xl font-bold text-indigo-600">Trust Banking</h1>

        {/* left links */}
        <nav className="flex items-center gap-6 ml-8">
          <Link to="/" className="text-gray-600 hover:text-indigo-600">
            Home
          </Link>

          {!token && (
            <>
              <Link to="/login" className="text-gray-600 hover:text-indigo-600">
                User Login
              </Link>
              <Link
                to="/admin-login"
                className="text-gray-600 hover:text-indigo-600"
              >
                Admin Login
              </Link>
            </>
          )}
        </nav>

        {/* right side */}
        <div className="ml-auto flex items-center gap-4">
          {token && (
            <>
              <span className="hidden sm:inline font-medium text-gray-700">
                {name || "User"}
              </span>

              <div
                title={name || "User"}
                className="w-9 h-9 rounded-full bg-indigo-600 text-white flex items-center justify-center font-bold"
              >
                {initial}
              </div>

              <button
                onClick={logout}
                className="bg-indigo-600 text-white px-4 py-1 rounded"
              >
                Logout
              </button>
            </>
          )}
        </div>
      </div>
    </header>
  );
}

export default Header;