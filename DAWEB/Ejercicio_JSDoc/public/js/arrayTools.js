/**
 * Modulo arrayTools.js
 * Rafael Vega LIbrero-6 de oct de 2026
 * Devuelve un nuevo array con los 
 * elementos únicos, preservando el orden.
 */


/**
 * We generate the array and return it.
 * @param {Array} arr 
 * @returns {Array}
 * @example unique([1, 2, 2, 3, 4]) // [1, 2, 3, 4]
 */
export function unique(arr) {
const seen = new Set();
const out = [];
for (const x of arr) {
 if (!seen.has(x)) {
 seen.add(x);
 out.push(x);
 }
}
return out;
}
/**
 * groups elements by a derived key
 * @param {out} arr 
 * @param {keyFn} keyFn 
 * @returns map
 */
export function groupBy(arr, keyFn) {
const map = new Map();
for (const item of arr) {
 const k = keyFn(item);
 if (!map.has(k)) map.set(k, []);
 map.get(k).push(item);
}
return map;
}
/**
 * Simulates a remote load and returns an array after a delay.
 * @param {number} delayMs 
 * @returns {Promise<number[]>}
 */
export async function fetchNumbers(delayMs = 200) {
await new Promise(r => setTimeout(r, delayMs));
return [1, 2, 2, 3, 4];
}