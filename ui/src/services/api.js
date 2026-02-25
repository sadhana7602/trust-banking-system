export const API_URL = "http://localhost:8082";

export const authFetch = (url, options = {}) => {
  const token = localStorage.getItem("token");

  return fetch(`${API_URL}${url}`, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
      ...(options.headers || {})
    }
  });
};