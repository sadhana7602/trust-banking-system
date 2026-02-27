import { BrowserRouter, Routes, Route } from "react-router-dom";
import Layout from "./components/Layout";
import HomePage from "./pages/HomePage";
import LoginPage from "./pages/LoginPage";
import AdminLoginPage from "./pages/AdminLoginPage";
import Dashboard from "./pages/Dashboard";
import RegisterPage from "./pages/RegisterPage";
import CreateAccountPage from "./pages/CreateAccountPage";
import DepositPage from "./pages/DepositPage";
import WithdrawPage from "./pages/WithdrawPage";
import TransferPage from "./pages/TransferPage";
import TransactionsPage from "./pages/TransactionsPage";
import AdminDashboard from "./pages/AdminDashboard";
import AdminUsersPage from "./pages/AdminUsersPage";
import AdminAccountsPage from "./pages/AdminAccountsPage";
import AdminTransactionsPage from "./pages/AdminTransactionsPage";
import AdminTicketsPage from "./pages/AdminTicketsPage";
import SupportPage from "./pages/SupportPage";


function App() {
  return (
    
      <Routes>
        <Route element={<Layout />}>
          <Route path="/" element={<HomePage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/admin-login" element={<AdminLoginPage />} />
          <Route path="/dashboard" element={<Dashboard />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/create-account" element={<CreateAccountPage />} />
          <Route path="/deposit" element={<DepositPage />} />
          <Route path="/withdraw" element={<WithdrawPage />} />
          <Route path="/transfer" element={<TransferPage />} />
          <Route path="/transactions" element={<TransactionsPage />} />
          <Route path="/admin" element={<AdminDashboard />} />
          <Route path="/admin/users" element={<AdminUsersPage />} />
          <Route path="/admin/accounts" element={<AdminAccountsPage />} />
          <Route path="/admin/transactions" element={<AdminTransactionsPage />} />
          <Route path="/admin/tickets" element={<AdminTicketsPage />} />
          <Route path="/support" element={<SupportPage />} />
        </Route>
      </Routes>
    
  );
}

export default App;