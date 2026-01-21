const BASE_URL = "http://localhost:8082";

/* =============================
   BASIC FETCH
============================= */
function authFetch(url, options = {}) {
  return fetch(url, {
    ...options,
    headers: {
      "Content-Type": "application/json",
      ...(options.headers || {})
    }
  });
}

/* =============================
   COMMON MESSAGE
============================= */
function showMessage(msg, color = "green") {
  const el = document.getElementById("message");
  if (el) {
    el.innerText = msg;
    el.style.color = color;
  }
}

/* =============================
   AUTH
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
      if (!res.ok) throw new Error("Invalid credentials");
      return res.json();
    })
    .then(data => {
      localStorage.setItem("loggedIn", "true");
      localStorage.setItem("userEmail", data.email);
      window.location.href = "dashboard.html";
    })
    .catch(() => alert("Login failed"));
}

function register() {
  const name = regName.value;
  const email = regEmail.value;
  const password = regPassword.value;
  const confirm = regConfirmPassword.value;

  if (password !== confirm) {
    alert("Passwords do not match");
    return;
  }

  fetch(`${BASE_URL}/api/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, email, password })
  })
    .then(res => {
      if (!res.ok) throw new Error();
      alert("Registration successful");
      window.location.href = "login.html";
    })
    .catch(() => alert("Registration failed"));
}

function logout() {
  localStorage.clear();
  window.location.href = "login.html";
}

/* =============================
   DASHBOARD INIT
============================= */
if (window.location.pathname.includes("dashboard.html")) {
  if (!localStorage.getItem("loggedIn")) {
    window.location.href = "login.html";
  } else {
    loadSummary();
    document.getElementById("welcomeUser").innerText =
      "Logged in as: " + localStorage.getItem("userEmail");
    autoSelectSpaceFromURL();
  }
}

/* =============================
   DASHBOARD
============================= */
function loadSummary() {
  authFetch(`${BASE_URL}/api/checklists/responses/summary`)
    .then(res => res.json())
    .then(data => {
      totalCount.innerText = data.total ?? "-";
      draftCount.innerText = data.draft ?? "-";
      submittedCount.innerText = data.submitted ?? "-";
    });
}

/* =============================
   SPACES
============================= */
function loadSpaces() {
  authFetch(`${BASE_URL}/api/spaces`)
    .then(res => res.json())
    .then(data => {
      const tbody = document.getElementById("spaceTableBody");
      tbody.innerHTML = "";

      if (!data.length) {
        tbody.innerHTML = `<tr><td colspan="5">No spaces found</td></tr>`;
        return;
      }

      data.forEach(space => {
        const row = document.createElement("tr");
        row.innerHTML = `
          <td>${space.id}</td>
          <td>${space.spaceName}</td>
          <td>${space.spaceType}</td>
          <td>${space.floor?.floorNumber ?? "-"}</td>
          <td>${space.floor?.building?.buildingName ?? "-"}</td>
        `;
        row.style.cursor = "pointer";
        row.onclick = () => {
          window.location.href = `dashboard.html?spaceId=${space.id}`;
        };
        tbody.appendChild(row);
      });
    })
    .catch(() => showMessage("Failed to load spaces", "red"));
}

function selectSpace(space) {
  targetId.value = space.id;
  targetType.value = "SPACE";
  showMessage(`Selected space: ${space.spaceName}`);
}

function autoSelectSpaceFromURL() {
  const spaceId = getParam("spaceId");
  if (!spaceId) return;

  authFetch(`${BASE_URL}/api/spaces`)
    .then(res => res.json())
    .then(data => {
      const space = data.find(s => s.id == spaceId);
      if (space) {
        selectSpace(space);
      }
    });
}

/* =============================
   CHECKLIST (MANUAL)
============================= */
function saveDraft() {
  if (!targetId.value) {
    showMessage("Select a space first", "red");
    return;
  }

  const body = {
    template: { id: Number(templateId.value) },
    targetType: "SPACE",
    targetId: Number(targetId.value),
    answersJson: JSON.stringify({
      powerAvailable: answer.value === "true"
    })
  };

  authFetch(`${BASE_URL}/api/checklists/responses`, {
    method: "POST",
    body: JSON.stringify(body)
  })
    .then(res => res.text())
    .then(msg => showMessage(msg));
}

function submitChecklist() {
  if (!responseId.value) {
    showMessage("Response ID required", "red");
    return;
  }

  authFetch(`${BASE_URL}/api/checklists/responses/${responseId.value}/submit`, {
    method: "POST"
  })
    .then(res => res.text())
    .then(msg => showMessage(msg));
}

function downloadReport() {
  if (!responseId.value) {
    showMessage("Response ID required", "red");
    return;
  }
  window.open(`${BASE_URL}/api/checklists/responses/${responseId.value}/report`);
}

/* =============================
   CHECKLIST HISTORY
============================= */
function loadChecklistHistory() {
  authFetch(`${BASE_URL}/api/checklists/responses`)
    .then(res => res.json())
    .then(data => {
      const table = document.getElementById("checklistTable");
      table.innerHTML = "";

      data.forEach(r => {
        table.innerHTML += `
          <tr>
            <td>${r.id}</td>
            <td>${r.targetType}</td>
            <td>${r.targetId}</td>
            <td>${r.status}</td>
            <td>
              ${
                r.status === "DRAFT"
                  ? `<button onclick="submitChecklistById(${r.id})">Submit</button>`
                  : `<button onclick="downloadChecklistReport(${r.id})">Report</button>`
              }
            </td>
          </tr>`;
      });
    });
}

function submitChecklistById(id) {
  if (!confirm("Submit this checklist?")) return;

  fetch(`${BASE_URL}/api/checklists/responses/${id}/submit`, {
    method: "POST"
  })
    .then(res => res.text())
    .then(msg => {
      alert(msg);
      loadChecklistHistory();
    });
}

function downloadChecklistReport(id) {
  window.open(`${BASE_URL}/api/checklists/responses/${id}/report`);
}

/* =============================
   NAVIGATION HELPERS
============================= */
function getParam(name) {
  return new URLSearchParams(window.location.search).get(name);
}

function goDashboard() {
  window.location.href = "dashboard.html";
}

/* =============================
   PROPERTIES → BUILDINGS → FLOORS
============================= */
function loadProperties() {
  authFetch(`${BASE_URL}/api/properties`)
    .then(res => res.json())
    .then(data => {
      const table = document.getElementById("propertyTable");
      table.innerHTML = "";

      data.forEach(p => {
        table.innerHTML += `
          <tr onclick="goBuildings(${p.id})">
            <td>${p.id}</td>
            <td>${p.propertyName}</td>
            <td>${p.city ?? "-"}</td>
          </tr>`;
      });
    });
}

function goBuildings(id) {
  window.location.href = `buildings.html?propertyId=${id}`;
}

function loadBuildings() {
  const propertyId = getParam("propertyId");
  if (!propertyId) return;

  authFetch(`${BASE_URL}/api/buildings/property/${propertyId}`)
    .then(res => res.json())
    .then(data => {
      const table = document.getElementById("buildingTable");
      table.innerHTML = "";

      data.forEach(b => {
        table.innerHTML += `
          <tr onclick="goFloors(${b.id})">
            <td>${b.id}</td>
            <td>${b.buildingName}</td>
          </tr>`;
      });
    });
}

function goFloors(buildingId) {
  window.location.href = `floors.html?buildingId=${buildingId}`;
}

function loadFloors() {
  const buildingId = getParam("buildingId");
  if (!buildingId) return;

  authFetch(`${BASE_URL}/api/floors/building/${buildingId}`)
    .then(res => res.json())
    .then(data => {
      const table = document.getElementById("floorTable");
      table.innerHTML = "";

      data.forEach(f => {
        table.innerHTML += `
          <tr>
            <td>${f.id}</td>
            <td>${f.floorNumber}</td>
            <td><input type="file" onchange="uploadPlan(${f.id}, this)"></td>
            <td><button onclick="viewFloorPlan(${f.id})">View</button></td>
          </tr>`;
      });
    });
}

function uploadPlan(floorId, input) {
  const file = input.files[0];
  const formData = new FormData();
  formData.append("file", file);

  fetch(`${BASE_URL}/api/floors/${floorId}/upload-plan`, {
    method: "POST",
    body: formData
  })
    .then(() => alert("Floor plan uploaded"));
}

function viewFloorPlan(floorId) {
  window.open(`${BASE_URL}/api/floors/${floorId}/plan`, "_blank");
}

/* =============================
   AUTO LOAD PER PAGE
============================= */
if (window.location.pathname.includes("properties.html")) loadProperties();
if (window.location.pathname.includes("buildings.html")) loadBuildings();
if (window.location.pathname.includes("floors.html")) loadFloors();
if (window.location.pathname.includes("spaces.html")) loadSpaces();
