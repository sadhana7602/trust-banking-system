const API_BASE = "http://localhost:8082";

export default function authFetch(url, options = {}) {

  const token = localStorage.getItem("token");

  return fetch(API_BASE + url, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      Authorization: `Bearer ${token}`,
      ...options.headers
    }
  });
}