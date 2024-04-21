import { mergeRecursive } from "@/utils/ruoyi";
import dictConverter from './DictConverter'

export const options = {
  metas: {
    '*': {
      /**
       * Dictionary request，The method is signedfunction(dictMeta: DictMeta): Promise
       */
      request: (dictMeta) => {
        console.log(`load dict ${dictMeta.type}`)
        return Promise.resolve([])
      },
      /**
       * Dictionary React Data Converter，The method is signedfunction(response: Object, dictMeta: DictMeta): DictData
       */
      responseConverter,
      labelField: 'label',
      valueField: 'value',
    },
  },
  /**
   * default labelling fields
   */
  DEFAULT_LABEL_FIELDS: ['label', 'name', 'title'],
  /**
   * default value field.
   */
  DEFAULT_VALUE_FIELDS: ['value', 'id', 'uid', 'key'],
}

/**
 * The dictionary.
 * @param {Object} response dictionary data
 * @param {DictMeta} dictMeta Dictionary data
 * @returns {DictData}
 */
function responseConverter(response, dictMeta) {
  const dicts = response.content instanceof Array ? response.content : response
  if (dicts === undefined) {
    console.warn(`no dict data of "${dictMeta.type}" found in the response`)
    return []
  }
  return dicts.map(d => dictConverter(d, dictMeta))
}

export function mergeOptions(src) {
  mergeRecursive(options, src)
}

export default options
