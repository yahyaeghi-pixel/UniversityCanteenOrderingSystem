/*
 * lookup.js
 * Drives the cascading Machine Lookup (Make -> Model -> Version 1 -> Version 2)
 * and the parts search/results against MACHINES / PARTS in js/data.js.
 * Also drives the standalone Part Number / Cross Reference search.
 */

let session = null;

const makeSelect = () => document.getElementById("make");
const modelSelect = () => document.getElementById("model");
const version1Select = () => document.getElementById("version1");
const version2Select = () => document.getElementById("version2");

function uniqueValues(list, key) {
  return [...new Set(list.map((item) => item[key]))].sort();
}

function filterMachines({ make, model, version1 }) {
  return MACHINES.filter(
    (m) =>
      (!make || m.make === make) &&
      (!model || m.model === model) &&
      (!version1 || m.version1 === version1)
  );
}

function fillSelect(selectEl, values, placeholder) {
  selectEl.innerHTML = "";
  const opt = document.createElement("option");
  opt.value = "";
  opt.textContent = placeholder;
  selectEl.appendChild(opt);

  values.forEach((v) => {
    const o = document.createElement("option");
    o.value = v;
    o.textContent = v === "-" ? "N/A" : v;
    selectEl.appendChild(o);
  });

  selectEl.disabled = values.length === 0;
}

function resetDownstream(fromLevel) {
  // fromLevel: 1 = reset model+, 2 = reset version1+, 3 = reset version2
  if (fromLevel <= 1) {
    fillSelect(modelSelect(), [], "Select Model");
  }
  if (fromLevel <= 2) {
    fillSelect(version1Select(), [], "Select Version 1");
  }
  if (fromLevel <= 3) {
    fillSelect(version2Select(), [], "Select Version 2");
  }
  updateSearchButtonState();
  clearResults();
}

function onMakeChange() {
  const make = makeSelect().value;
  resetDownstream(1);
  if (!make) return;
  const models = uniqueValues(filterMachines({ make }), "model");
  fillSelect(modelSelect(), models, "Select Model");
}

function onModelChange() {
  const make = makeSelect().value;
  const model = modelSelect().value;
  resetDownstream(2);
  if (!model) return;
  const versions1 = uniqueValues(filterMachines({ make, model }), "version1");
  fillSelect(version1Select(), versions1, "Select Version 1");
}

function onVersion1Change() {
  const make = makeSelect().value;
  const model = modelSelect().value;
  const version1 = version1Select().value;
  resetDownstream(3);
  if (!version1) return;
  const versions2 = uniqueValues(filterMachines({ make, model, version1 }), "version2");
  fillSelect(version2Select(), versions2, "Select Version 2");
}

function onVersion2Change() {
  updateSearchButtonState();
}

function updateSearchButtonState() {
  const btn = document.getElementById("searchBtn");
  const ready =
    makeSelect().value && modelSelect().value && version1Select().value && version2Select().value;
  btn.disabled = !ready;
}

function clearResults() {
  const summary = document.getElementById("resultsSummary");
  const tableWrap = document.getElementById("resultsTableWrap");
  summary.textContent = "";
  tableWrap.innerHTML = "";
}

function findMachine(make, model, version1, version2) {
  return MACHINES.find(
    (m) =>
      m.make === make && m.model === model && m.version1 === version1 && m.version2 === version2
  );
}

function formatPrice(price) {
  return typeof price === "number" ? `€${price.toFixed(2)}` : "POA";
}

function renderPartsTable(parts, summaryText, emptyText) {
  const summary = document.getElementById("resultsSummary");
  const tableWrap = document.getElementById("resultsTableWrap");

  summary.textContent = summaryText;

  if (parts.length === 0) {
    tableWrap.innerHTML = `<p class="empty-state">${emptyText}</p>`;
    return;
  }

  const rows = parts
    .map((p) => {
      const xref = p.crossReference && p.crossReference.length ? p.crossReference.join(", ") : "—";
      return `
      <tr>
        <td>${p.sku}${p.msPartNumber ? `<br><span class="muted">MS: ${p.msPartNumber}</span>` : ""}</td>
        <td>${p.title}</td>
        <td>${p.subCategory}</td>
        <td class="xref">${xref}</td>
        <td>${p.stockStatus || ""}</td>
        <td class="price">${formatPrice(p.price)}</td>
      </tr>`;
    })
    .join("");

  tableWrap.innerHTML = `
    <table class="parts-table">
      <thead>
        <tr>
          <th>Part Number</th>
          <th>Part Name</th>
          <th>Category</th>
          <th>Cross Reference / OEM No.</th>
          <th>Stock</th>
          <th>Trade Price</th>
        </tr>
      </thead>
      <tbody>${rows}</tbody>
    </table>`;
}

function renderParts(machine) {
  if (!machine) {
    renderPartsTable([], "No matching machine found for that combination.", "");
    return;
  }

  const parts = PARTS.filter((p) => p.compatibleMachineIds.includes(machine.id));
  const label = [machine.make, machine.model, machine.version1, machine.version2]
    .map((v) => (v === "-" ? null : v))
    .filter(Boolean)
    .join(" ");

  renderPartsTable(
    parts,
    `${label} — ${parts.length} compatible part(s) found.`,
    "No parts currently listed for this machine."
  );
}

function onSearch(event) {
  event.preventDefault();
  const make = makeSelect().value;
  const model = modelSelect().value;
  const version1 = version1Select().value;
  const version2 = version2Select().value;

  if (!make || !model || !version1 || !version2) return;

  const machine = findMachine(make, model, version1, version2);
  renderParts(machine);
}

function onResetForm() {
  makeSelect().value = "";
  resetDownstream(1);
}

/* ---------- Part Number / Cross Reference search ---------- */

function onPartSearch(event) {
  event.preventDefault();
  const term = document.getElementById("partSearchInput").value.trim().toLowerCase();
  const summary = document.getElementById("partSearchSummary");
  const tableWrap = document.getElementById("partSearchTableWrap");

  if (!term) {
    summary.textContent = "";
    tableWrap.innerHTML = "";
    return;
  }

  const matches = PARTS.filter((p) => {
    if (p.sku.toLowerCase().includes(term)) return true;
    if (p.msPartNumber && p.msPartNumber.toLowerCase().includes(term)) return true;
    if (p.title.toLowerCase().includes(term)) return true;
    return (p.crossReference || []).some((code) => code.toLowerCase().includes(term));
  });

  summary.textContent = `${matches.length} result(s) for "${term}".`;

  if (matches.length === 0) {
    tableWrap.innerHTML = '<p class="empty-state">No parts matched that part number or cross reference / OEM number.</p>';
    return;
  }

  const rows = matches
    .map((p) => {
      const xref = p.crossReference && p.crossReference.length ? p.crossReference.join(", ") : "—";
      return `
      <tr>
        <td>${p.sku}${p.msPartNumber ? `<br><span class="muted">MS: ${p.msPartNumber}</span>` : ""}</td>
        <td>${p.title}</td>
        <td>${p.subCategory}</td>
        <td class="xref">${xref}</td>
        <td>${p.stockStatus || ""}</td>
        <td class="price">${formatPrice(p.price)}</td>
      </tr>`;
    })
    .join("");

  tableWrap.innerHTML = `
    <table class="parts-table">
      <thead>
        <tr>
          <th>Part Number</th>
          <th>Part Name</th>
          <th>Category</th>
          <th>Cross Reference / OEM No.</th>
          <th>Stock</th>
          <th>Trade Price</th>
        </tr>
      </thead>
      <tbody>${rows}</tbody>
    </table>`;
}

document.addEventListener("DOMContentLoaded", () => {
  session = requireTradeSession();
  if (!session) return;

  document.getElementById("welcomeText").textContent = `Welcome, ${session.company}`;
  document.getElementById("logoutBtn").addEventListener("click", logout);

  fillSelect(makeSelect(), uniqueValues(MACHINES, "make"), "Select Make");
  fillSelect(modelSelect(), [], "Select Model");
  fillSelect(version1Select(), [], "Select Version 1");
  fillSelect(version2Select(), [], "Select Version 2");

  makeSelect().addEventListener("change", onMakeChange);
  modelSelect().addEventListener("change", onModelChange);
  version1Select().addEventListener("change", onVersion1Change);
  version2Select().addEventListener("change", onVersion2Change);

  document.getElementById("lookupForm").addEventListener("submit", onSearch);
  document.getElementById("resetBtn").addEventListener("click", onResetForm);

  document.getElementById("partSearchForm").addEventListener("submit", onPartSearch);

  updateSearchButtonState();
});
