import { Link } from "react-router-dom";

function HomePage() {
  return (
    <div className="min-h-screen flex flex-col md:flex-row bg-gradient-to-r from-indigo-500 via-purple-500 to-pink-500 text-white">

      {/* LEFT SECTION */}
      <div className="flex flex-col justify-center items-start p-10 md:w-1/2">
        <h1 className="text-5xl font-bold mb-6">
          Trust Banking System
        </h1>

        <p className="text-lg mb-8 opacity-90">
          Secure. Fast. Reliable banking experience.
          Manage accounts, transfer funds, and track transactions effortlessly.
        </p>

        <div className="flex gap-4">
          <Link
            to="/login"
            className="bg-white text-indigo-600 px-6 py-3 rounded-lg shadow-lg hover:scale-105 transition"
          >
            User Login
          </Link>

          <Link
            to="/admin-login"
            className="bg-black bg-opacity-20 px-6 py-3 rounded-lg shadow-lg hover:scale-105 transition"
          >
            Admin Login
          </Link>
        </div>
      </div>

      {/* RIGHT SECTION (ILLUSTRATION) */}
      <div className="flex justify-center items-center md:w-1/2 p-10">
        <img
          src="https://cdn-icons-png.flaticon.com/512/3144/3144456.png"
          alt="bank illustration"
          className="w-96 drop-shadow-2xl"
        />
      </div>
    </div>
  );
}

export default HomePage;