# M&S Plant Spares — Trade Portal (Demo Build)

A basic static website for M&S Plant Spares trade customers to log in, look up
their machine via cascading **Make → Model → Version 1 → Version 2** dropdowns,
and search for compatible parts.

## Status

This is a working scaffold using **sample placeholder data** so the full flow
(login → machine lookup → search → results) can be reviewed before real data
is added. Nothing here is real M&S Plant Spares product, pricing, or customer
data.

## Structure

```
ms-plant-spares-website/
├── index.html        Trade login page
├── lookup.html        Machine Lookup + parts search/results (login required)
├── css/style.css      All styling
├── js/data.js          Sample trade accounts, machines, and parts data
├── js/auth.js          Login handling + simple session gate
└── js/lookup.js        Cascading dropdown logic + parts search/results
```

## Running it

No build step or server required — open `index.html` in a browser, or serve
the folder with any static file server, e.g.:

```
cd ms-plant-spares-website
python3 -m http.server 8000
```

Then visit `http://localhost:8000`.

## Demo login

- `trade1` / `trade123`
- `demo` / `demo123`

## Important limitations (by design, for this first pass)

- **Login is not secure.** Credentials are checked in the browser against
  `js/data.js` and the "session" is just `sessionStorage`. This is fine for
  demonstrating the flow, but must **not** be used to protect real trade
  pricing or customer data. A production version needs a real backend with
  hashed passwords and server-side sessions.
- **Data is placeholder.** `MACHINES` and `PARTS` in `js/data.js` are made up.

## Replacing the placeholder data

To go live with real data, replace the contents of `js/data.js`:

- **`MACHINES`** — one entry per real Make / Model / Version 1 / Version 2
  combination you sell parts for.
- **`PARTS`** — real part numbers, names, descriptions, trade prices, and
  `compatibleMachineIds` listing which machine(s) (by `id` from `MACHINES`)
  each part fits. This is the compatibility / cross-reference data.
- **`TRADE_ACCOUNTS`** — real trade customer login list (only if staying with
  the simple client-side gate; otherwise this moves server-side).

Send over the real Make/Model/Version1/Version2 list and the parts +
compatibility/cross-reference data whenever you're ready and it can be
dropped straight in.
