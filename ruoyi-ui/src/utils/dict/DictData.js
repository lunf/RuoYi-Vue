/**
 * @classdesc dictionary data
 * @property {String} label Tagged
 * @property {*} value Tagged
 * @property {Object} raw The original data
 */
export default class DictData {
  constructor(label, value, raw) {
    this.label = label
    this.value = value
    this.raw = raw
  }
}
