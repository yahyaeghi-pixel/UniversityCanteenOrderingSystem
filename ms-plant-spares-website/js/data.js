/*
 * data.js
 * ------------------------------------------------------------------
 * PLACEHOLDER / SAMPLE DATA ONLY.
 *
 * Everything in this file (trade accounts, machines, parts) is made up
 * so the site can be demonstrated end-to-end. Replace it with real
 * M&S Plant Spares data:
 *   - TRADE_ACCOUNTS: real trade customer login credentials
 *   - MACHINES: real Make / Model / Version 1 / Version 2 combinations
 *   - PARTS: real part numbers, descriptions, prices and which
 *     machine(s) each part is compatible with (cross-reference data)
 * ------------------------------------------------------------------
 */

// Sample trade customer accounts (client-side demo only, NOT secure).
const TRADE_ACCOUNTS = [
  { username: "trade1", password: "trade123", company: "ABC Plant Hire Ltd" },
  { username: "demo", password: "demo123", company: "Demo Trade Account" }
];

// Sample machines: each row is one Make / Model / Version 1 / Version 2 combination.
const MACHINES = [
  { id: "m1", make: "CAT",     model: "320",   version1: "D",        version2: "L" },
  { id: "m2", make: "CAT",     model: "320",   version1: "D",        version2: "LN" },
  { id: "m3", make: "CAT",     model: "330",   version1: "C",        version2: "L" },
  { id: "m4", make: "JCB",     model: "3CX",   version1: "Sitemaster", version2: "Turbo" },
  { id: "m5", make: "JCB",     model: "JS220", version1: "Tier 3",   version2: "LC" },
  { id: "m6", make: "Komatsu", model: "PC200", version1: "-8",       version2: "MO" }
];

// Sample parts, linked to machines via compatibleMachineIds (the cross-reference data).
const PARTS = [
  {
    partNumber: "MS-1001",
    name: "Track Chain Assembly",
    description: "Heavy duty track chain assembly. (Placeholder sample part.)",
    price: 450.0,
    compatibleMachineIds: ["m1", "m2", "m3"]
  },
  {
    partNumber: "MS-1002",
    name: "Front Idler Wheel",
    description: "Front idler wheel. (Placeholder sample part.)",
    price: 210.0,
    compatibleMachineIds: ["m1", "m2"]
  },
  {
    partNumber: "MS-1003",
    name: "Bucket Tooth Set (x5)",
    description: "Set of 5 bucket teeth. (Placeholder sample part.)",
    price: 85.0,
    compatibleMachineIds: ["m4", "m5", "m6"]
  },
  {
    partNumber: "MS-1004",
    name: "Hydraulic Filter",
    description: "Main hydraulic system filter. (Placeholder sample part.)",
    price: 32.5,
    compatibleMachineIds: ["m5", "m6"]
  },
  {
    partNumber: "MS-1005",
    name: "Cab Air Filter",
    description: "Cabin air filter, fits all listed sample machines. (Placeholder sample part.)",
    price: 18.0,
    compatibleMachineIds: ["m1", "m2", "m3", "m4", "m5", "m6"]
  }
];
