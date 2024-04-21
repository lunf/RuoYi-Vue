import Vue from 'vue'

import Cookies from 'js-cookie'

import Element from 'element-ui'
import locale_en from 'element-ui/lib/locale/lang/en';
import './assets/styles/element-variables.scss'

import '@/assets/styles/index.scss' // global css
import '@/assets/styles/ruoyi.scss' // ruoyi css
import App from './App'
import store from './store'
import router from './router'
import directive from './directive' // directive
import plugins from './plugins' // plugins
import { download } from '@/utils/request'

import './assets/icons' // icon
import './permission' // permission control
import { getDicts } from "@/api/system/dict/data";
import { getConfigKey } from "@/api/system/config";
import { parseTime, resetForm, addDateRange, selectDictLabel, selectDictLabels, handleTree } from "@/utils/ruoyi";
// Parts of page
import Pagination from "@/components/Pagination";
// Customized Table Tool Components
import RightToolbar from "@/components/RightToolbar"
// rich text components.
import Editor from "@/components/Editor"
// The file upload components.
import FileUpload from "@/components/FileUpload"
// Photo upload components.
import ImageUpload from "@/components/ImageUpload"
// Preview of components
import ImagePreview from "@/components/ImagePreview"
// Dictionary tag components
import DictTag from '@/components/DictTag'
// The head label component.
import VueMeta from 'vue-meta'
// Dictionary data components
import DictData from '@/components/DictData'

// The overall method of charging
Vue.prototype.getDicts = getDicts
Vue.prototype.getConfigKey = getConfigKey
Vue.prototype.parseTime = parseTime
Vue.prototype.resetForm = resetForm
Vue.prototype.addDateRange = addDateRange
Vue.prototype.selectDictLabel = selectDictLabel
Vue.prototype.selectDictLabels = selectDictLabels
Vue.prototype.download = download
Vue.prototype.handleTree = handleTree

// Components are charged.
Vue.component('DictTag', DictTag)
Vue.component('Pagination', Pagination)
Vue.component('RightToolbar', RightToolbar)
Vue.component('Editor', Editor)
Vue.component('FileUpload', FileUpload)
Vue.component('ImageUpload', ImageUpload)
Vue.component('ImagePreview', ImagePreview)

Vue.use(directive)
Vue.use(plugins)
Vue.use(VueMeta)
DictData.install()

/**
 * If you don't want to use mock-server
 * you want to use MockJs for mock api
 * you can execute: mockXHR()
 *
 * Currently MockJs will be used in the production environment,
 * please remove it before going online! ! !
 */

Vue.use(Element, {
  size: Cookies.get('size') || 'medium', // set element-ui default size
  locale: locale_en
})

Vue.config.productionTip = false

new Vue({
  el: '#app',
  router,
  store,
  render: h => h(App)
})
