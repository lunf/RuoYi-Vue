import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken } from '@/utils/auth'
import { isRelogin } from '@/utils/request'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login', '/register']

router.beforeEach((to, from, next) => {
  NProgress.start()
  if (getToken()) {
    to.meta.title && store.dispatch('settings/setTitle', to.meta.title)
    /* has token*/
    if (to.path === '/login') {
      next({ path: '/' })
      NProgress.done()
    } else if (whiteList.indexOf(to.path) !== -1) {
      next()
    } else {
      if (store.getters.roles.length === 0) {
        isRelogin.show = true
        // To determine whether the current user has been removed.user_infoInformation
        store.dispatch('GetInfo').then(() => {
          isRelogin.show = false
          store.dispatch('GenerateRoutes').then(accessRoutes => {
            // based onrolesPermit to create accessible router tables
            router.addRoutes(accessRoutes) // Dynamic Add Accessible Router Table
            next({ ...to, replace: true }) // hackMethod to ensureaddRouteshas completed
          })
        }).catch(err => {
            store.dispatch('LogOut').then(() => {
              Message.error(err)
              next({ path: '/' })
            })
          })
      } else {
        next()
      }
    }
  } else {
    // Notoken
    if (whiteList.indexOf(to.path) !== -1) {
      // Not on the white list.，Directly entering
      next()
    } else {
      next(`/login?redirect=${encodeURIComponent(to.fullPath)}`) // Otherwise all redirect to the login page.
      NProgress.done()
    }
  }
})

router.afterEach(() => {
  NProgress.done()
})
