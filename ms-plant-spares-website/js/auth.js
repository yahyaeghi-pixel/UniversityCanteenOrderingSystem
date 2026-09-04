/*
 * auth.js
 * Very basic client-side "gate" for the trade login page.
 * NOTE: This is a demo-level login only (credentials are checked in the
 * browser against js/data.js). It is NOT secure and must not be used to
 * protect real customer data or pricing in production. See README.md.
 */

function handleLoginSubmit(event) {
  event.preventDefault();

  const usernameInput = document.getElementById("username");
  const passwordInput = document.getElementById("password");
  const errorBox = document.getElementById("loginError");

  const username = usernameInput.value.trim();
  const password = passwordInput.value;

  const account = TRADE_ACCOUNTS.find(
    (acc) => acc.username.toLowerCase() === username.toLowerCase() && acc.password === password
  );

  if (!account) {
    errorBox.textContent = "Incorrect username or password. Please try again.";
    errorBox.classList.add("show");
    return;
  }

  errorBox.classList.remove("show");

  sessionStorage.setItem(
    "msps_trade_session",
    JSON.stringify({ username: account.username, company: account.company })
  );

  window.location.href = "lookup.html";
}

function getTradeSession() {
  const raw = sessionStorage.getItem("msps_trade_session");
  if (!raw) return null;
  try {
    return JSON.parse(raw);
  } catch (e) {
    return null;
  }
}

function requireTradeSession() {
  const session = getTradeSession();
  if (!session) {
    window.location.href = "index.html";
    return null;
  }
  return session;
}

function logout() {
  sessionStorage.removeItem("msps_trade_session");
  window.location.href = "index.html";
}

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("loginForm");
  if (form) {
    form.addEventListener("submit", handleLoginSubmit);
  }
});
