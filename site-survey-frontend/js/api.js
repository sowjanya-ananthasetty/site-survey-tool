function authFetch(url, options = {}) {
  const token = localStorage.getItem("accessToken");

  if (!token) {
    window.location.href = "login.html";
    return;
  }

  return fetch(url, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`,
      ...(options.headers || {})
    }
  });
}
