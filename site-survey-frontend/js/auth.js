/* =============================
   LOGIN
============================= */
function login() {
  const email = document.getElementById("loginEmail").value.trim();
  const password = document.getElementById("loginPassword").value.trim();

  fetch(`${BASE_URL}/api/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ email, password })
  })
    .then(res => {
      if (!res.ok) {
        throw new Error("Invalid credentials");
      }
      return res.json();
    })
    .then(data => {
      localStorage.setItem("loggedIn", "true");
      localStorage.setItem("userEmail", data.email);

      /* ==================================
         ROLE HANDLING (IMPORTANT FIX)
         ----------------------------------
         1. If backend sends role → use it
         2. Else, decide role by email
      ================================== */

      if (data.role) {
        // Backend-driven role (preferred)
        localStorage.setItem("role", data.role);
      } else {
        // Fallback (DEMO SAFE)
        if (email.toLowerCase().includes("admin")) {
          localStorage.setItem("role", "ADMIN");
        } else {
          localStorage.setItem("role", "ENGINEER");
        }
      }

      window.location.href = "dashboard.html";
    })
    .catch(err => {
      alert("Login failed");
      console.error(err);
    });
}

/* =============================
   REGISTER
============================= */
function register() {
  const name = document.getElementById("regName").value.trim();
  const email = document.getElementById("regEmail").value.trim();
  const password = document.getElementById("regPassword").value.trim();
  const confirm = document.getElementById("regConfirmPassword").value.trim();

  if (!name || !email || !password || !confirm) {
    alert("All fields are required");
    return;
  }

  if (password !== confirm) {
    alert("Passwords do not match");
    return;
  }

  fetch(`${BASE_URL}/api/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, email, password })
  })
    .then(async res => {
      const text = await res.text();

      if (!res.ok) {
        alert(text); // shows backend message (e.g., Email already exists)
        return;
      }

      alert(text); // Registration successful
      window.location.href = "login.html";
    })
    .catch(() => alert("Registration error"));
}

/* =============================
   LOGOUT
============================= */
function logout() {
  localStorage.clear();
  window.location.href = "login.html";
}
