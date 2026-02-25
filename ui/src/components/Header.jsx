import { Link, useNavigate } from "react-router-dom";

function Header() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const name = localStorage.getItem("fullname");

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <header className="bg-white shadow-md">
      <div className="max-w-6xl mx-auto px-6 py-4 flex justify-between items-center">

        <h1 className="text-xl font-bold text-indigo-600">
          Trust Banking
        </h1>

        <nav className="flex items-center gap-6">
          <Link to="/" className="text-gray-600 hover:text-indigo-600">
            Home
          </Link>

          {!token && (
            <>
              <Link to="/login" className="text-gray-600 hover:text-indigo-600">
                User Login
              </Link>
              <Link to="/admin-login" className="text-gray-600 hover:text-indigo-600">
                Admin Login
              </Link>
            </>
          )}

          {token && (
            <>
              {name && (
        <div className="flex items-center gap-3">
          <span className="font-medium">{name}</span>

          {/* Profile Icon */}
          <div className="w-9 h-9 rounded-full bg-white text-blue-600 flex items-center justify-center font-bold">
            {name.charAt(0).toUpperCase()}
          </div>
        </div>
      )}
              <button
                onClick={logout}
                className="bg-indigo-600 text-white px-4 py-1 rounded"
              >
                Logout
              </button>
            </>
          )}

          
        </nav>
      </div>
    </header>
  );
}

export default Header;