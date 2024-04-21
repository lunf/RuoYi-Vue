import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: Router Configuration
 *
 * hidden: true                     // When set up true The router will no longer appear on the side bar. as401，loginWait for page.，Like some editing pages./edit/1
 * alwaysShow: true                 // When you have a route below. children The statement is greater than1At one time，It automatically becomes an embedded pattern.--Like the component page.
 *                                  // Only one time.，It will display that subroute as a root route on the side bar.--Like guiding page.
 *                                  // If you don’t care about the route below. children The numbers of the statements show your root routes.
 *                                  // You can set up. alwaysShow: true，So it will ignore the rules defined before.，It always shows rotating.
 * redirect: noRedirect             // When set up noRedirect When the route is not clicked in the bread shell navigation.
 * name:'router-name'               // Set the name of the routing.，must be filled or used.<keep-alive>There will be various problems.
 * query: '{"id": 1, "name": "ry"}' // Access to the router’s default transmission parameters
 * roles: ['admin', 'common']       // Access to the router's role permits
 * permissions: ['a:a:a', 'b:b:b']  // Access to the routing menu permits
 * meta : {
    noCache: true                   // If set fortrue，It will not be <keep-alive> cache(presumed false)
    title: 'title'                  // Set the name that the router shows in the side bar and bread shell.
    icon: 'svg-name'                // Set the routing icon.，corresponding path.src/assets/icons/svg
    breadcrumb: false               // If set forfalse，It will not bebreadcrumbShowing in the bread.
    activeMenu: '/system/user'      // When the router set this property.，It will be a high side bar corresponding.。
  }
 */

// The public route.
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: () => import('@/views/redirect')
      }
    ]
  },
  {
    path: '/login',
    component: () => import('@/views/login'),
    hidden: true
  },
  {
    path: '/register',
    component: () => import('@/views/register'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error/401'),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/index'),
        name: 'Index',
        meta: { title: 'The first page', icon: 'dashboard', affix: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    hidden: true,
    redirect: 'noredirect',
    children: [
      {
        path: 'profile',
        component: () => import('@/views/system/user/profile/index'),
        name: 'Profile',
        meta: { title: 'The Personal Center', icon: 'user' }
      }
    ]
  }
]

// Dynamic routing.，Based on user permits to load dynamically.
export const dynamicRoutes = [
  {
    path: '/system/user-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:user:edit'],
    children: [
      {
        path: 'role/:userId(\\d+)',
        component: () => import('@/views/system/user/authRole'),
        name: 'AuthRole',
        meta: { title: 'Distribution of roles', activeMenu: '/system/user' }
      }
    ]
  },
  {
    path: '/system/role-auth',
    component: Layout,
    hidden: true,
    permissions: ['system:role:edit'],
    children: [
      {
        path: 'user/:roleId(\\d+)',
        component: () => import('@/views/system/role/authUser'),
        name: 'AuthUser',
        meta: { title: 'Distribution of Users', activeMenu: '/system/role' }
      }
    ]
  },
  {
    path: '/system/dict-data',
    component: Layout,
    hidden: true,
    permissions: ['system:dict:list'],
    children: [
      {
        path: 'index/:dictId(\\d+)',
        component: () => import('@/views/system/dict/data'),
        name: 'Data',
        meta: { title: 'dictionary data', activeMenu: '/system/dict' }
      }
    ]
  },
  {
    path: '/monitor/job-log',
    component: Layout,
    hidden: true,
    permissions: ['monitor:job:list'],
    children: [
      {
        path: 'index/:jobId(\\d+)',
        component: () => import('@/views/monitor/job/log'),
        name: 'JobLog',
        meta: { title: 'Modified Diary', activeMenu: '/monitor/job' }
      }
    ]
  },
  {
    path: '/tool/gen-edit',
    component: Layout,
    hidden: true,
    permissions: ['tool:gen:edit'],
    children: [
      {
        path: 'index/:tableId(\\d+)',
        component: () => import('@/views/tool/gen/editTable'),
        name: 'GenEdit',
        meta: { title: 'Modification of configuration.', activeMenu: '/tool/gen' }
      }
    ]
  }
]

// Avoid repeated clicks by routing errors.
let routerPush = Router.prototype.push;
let routerReplace = Router.prototype.replace;
// push
Router.prototype.push = function push(location) {
  return routerPush.call(this, location).catch(err => err)
}
// replace
Router.prototype.replace = function push(location) {
  return routerReplace.call(this, location).catch(err => err)
}

export default new Router({
  mode: 'history', // go off.urlIn the#
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
