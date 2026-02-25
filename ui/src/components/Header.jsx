import { Link, useNavigate } from "react-router-dom";

function Header() {
  const navigate = useNavigate();
  const token = localStorage.getItem("token");
  const name = localStorage.getItem("name");

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
              <span className="text-gray-700 font-medium">
                Hi, {name}
              </span>
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