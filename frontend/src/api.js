const API_URL = import.meta.env.VITE_API_URL || "http://localhost:8080";

async function request(path, options) {
  const response = await fetch(API_URL + path, {
    headers: { "Content-Type": "application/json" },
    ...options
  });
  if (!response.ok) {
    const error = await response.json().catch(() => ({ message: "Request failed" }));
    throw new Error(error.message || "Request failed");
  }
  return response.status === 204 ? null : response.json();
}

export const runsApi = {
  list: () => request("/api/runs"),
  create: (payload) => request("/api/runs", { method: "POST", body: JSON.stringify(payload) }),
  setStatus: (id, value) => request("/api/runs/" + id + "/status?value=" + value, { method: "PATCH" }),
  remove: (id) => request("/api/runs/" + id, { method: "DELETE" })
};
