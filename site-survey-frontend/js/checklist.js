function saveDraft() {
  const targetId = document.getElementById("targetId").value;

  if (!targetId) {
    showMessage("Target ID required", "red");
    return;
  }

  const body = {
    template: { id: Number(document.getElementById("templateId").value) },
    targetType: document.getElementById("targetType").value,
    targetId: Number(targetId),
    answersJson: JSON.stringify({
      powerAvailable: document.getElementById("answer").value === "true"
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
  const id = document.getElementById("responseId").value;

  authFetch(`${BASE_URL}/api/checklists/responses/${id}/submit`, {
    method: "POST"
  })
    .then(res => res.text())
    .then(msg => showMessage(msg));
}

function downloadReport() {
  const id = document.getElementById("responseId").value;
  window.open(`${BASE_URL}/api/checklists/responses/${id}/report`);
}
