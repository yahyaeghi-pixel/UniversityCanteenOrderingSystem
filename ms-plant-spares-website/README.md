# M&S Plant Spares — Trade Portal

A static website for M&S Plant Spares trade customers to log in, look up
their machine via cascading **Make → Model → Version 1 → Version 2**
dropdowns, and search for compatible parts — plus a direct search by part
number or cross-reference / OEM number.

## Status

The product, compatibility and cross-reference data is **real**, generated
from the supplied spreadsheets covering 5 categories:

- Idlers
- Injectors
- Dummy Pins
- Bolt On Rubber Pads
- Rubber Tracks

That's 259 parts, ~3,940 unique machine Make/Model/Version1/Version2
combinations, ~7,365 machine-to-part compatibility links, and cross
reference/OEM numbers, all baked into `js/data.js`.

The **trade customer login is still a demo/placeholder** — see Limitations
below.

## Structure

```
ms-plant-spares-website/
├── index.html        Trade login page
├── lookup.html         Machine Lookup + parts search/results + part/cross-reference search (login required)
├── css/style.css       All styling
├── js/data.js           Trade accounts (placeholder) + real MACHINES and PARTS data
├── js/auth.js           Login handling + simple session gate
└── js/lookup.js         Cascading dropdown logic, parts search, part/cross-reference search
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

## How the data is structured (`js/data.js`)

- **`MACHINES`** — every real Make / Model / Version 1 / Version 2 /
  Machine Type combination found in the source spreadsheets. `version1`/
  `version2` of `"-"` means "not applicable / not specified" for that
  variant (shown as "N/A" in the dropdowns).
- **`PARTS`** — one entry per part (`sku`, category/subCategory, title,
  price, weight, stock status, MS part number, `crossReference` list of
  OEM/cross-reference numbers, `compatibleMachineIds` linking to
  `MACHINES`, plus any extra dimension/attribute fields from the source
  sheet).
- **`TRADE_ACCOUNTS`** — still sample/placeholder (see Limitations).

Note: Dummy Pins have no machine compatibility data in the source file (they
are sized by diameter/length, not machine-specific), so they won't appear
in the Machine Lookup results, but they are searchable via the Part
Number / Cross Reference search.

## Important limitations (by design, for this pass)

- **Login is not secure.** Credentials are checked in the browser against
  `TRADE_ACCOUNTS` in `js/data.js` and the "session" is just
  `sessionStorage`. This is fine for demonstrating the flow, but must
  **not** be used to protect real trade pricing or customer data. A
  production version needs a real backend with hashed passwords and
  server-side sessions, and real trade customer accounts.
- Prices without a listed value display as **"POA"** (Price On Application).

## Updating the data

If the source spreadsheets change, re-extract them into `js/data.js`
(the format is documented above) — send over updated
Products/Compatibility/Cross Reference spreadsheets and it can be
regenerated.
