import tab from './tab'
import auth from './auth'
import cache from './cache'
import modal from './modal'
import download from './download'

export default {
  install(Vue) {
    // Page operations
    Vue.prototype.$tab = tab
    // Certified Objects
    Vue.prototype.$auth = auth
    // cache objects
    Vue.prototype.$cache = cache
    // Modular frame objects
    Vue.prototype.$modal = modal
    // Download the document.
    Vue.prototype.$download = download
  }
}
