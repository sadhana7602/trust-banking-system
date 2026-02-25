import { useNavigate } from "react-router-dom";

function Dashboard() {
  const navigate = useNavigate();
  const name = localStorage.getItem("name");

  const logout = () => {
    localStorage.clear();
    navigate("/");
  };

  return (
    <div className="p-10">
      <h1 className="text-2xl font-bold">Welcome {name} 👋</h1>

      <button
        onClick={logout}
        className="mt-5 bg-red-500 text-white px-4 py-2"
      >
        Logout
      </button>
    </div>
  );
}

export default Dashboard;