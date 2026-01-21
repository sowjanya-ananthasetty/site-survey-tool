function showMessage(msg, color = "green") {
  const el = document.getElementById("message");
  if (el) {
    el.innerText = msg;
    el.style.color = color;
  }
}

function loadSummary() {
  authFetch(`${BASE_URL}/api/checklists/responses/summary`)
    .then(res => res.json())
    .then(data => {
      document.getElementById("totalCount").innerText = data.total;
      document.getElementById("draftCount").innerText = data.draft;
      document.getElementById("submittedCount").innerText = data.submitted;
    });
}

function loadSpaces() {
  authFetch(`${BASE_URL}/api/spaces`)
    .then(res => res.json())
    .then(data => {
      const tbody = document.getElementById("spaceTableBody");
      tbody.innerHTML = "";

      data.forEach(space => {
        const row = document.createElement("tr");

        row.innerHTML = `
          <td>${space.id}</td>
          <td>${space.spaceName}</td>
          <td>${space.spaceType}</td>
          <td>${space.floor?.floorNumber ?? "-"}</td>
          <td>${space.floor?.building?.buildingName ?? "-"}</td>
        `;

        row.onclick = () => selectSpace(space);
        tbody.appendChild(row);
      });

      showMessage("Select a space to continue");
    });
}

function selectSpace(space) {
  document.getElementById("targetId").value = space.id;
  document.getElementById("targetType").value = "SPACE";
  showMessage(`Selected: ${space.spaceName}`);
}

if (window.location.pathname.includes("dashboard.html")) {
  if (window.location.pathname.includes("dashboard.html")) {
  if (!localStorage.getItem("loggedIn")) {
    window.location.href = "login.html";
  } else {
    loadSummary();
    document.getElementById("welcomeUser").innerText =
      "Logged in as: " + localStorage.getItem("userEmail");
  }
}
}