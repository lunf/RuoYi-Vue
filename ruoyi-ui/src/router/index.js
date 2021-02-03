import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import ParentView from '@/components/ParentView';

/**
 * Note: Routing configuration items
 *
 * hidden: true                   // When set to true, the route will not appear in the sidebar such as 401, login and other pages, or some edit pages such as /edit/1
 * alwaysShow: true               // When you have more than one route declared by children under a route, it will automatically become a nested mode - such as a component page
 *                                // When there is only one, the sub-route will be displayed as the root route in the sidebar-such as the guide page
 *                                // If you want to display your root route regardless of the number of children declared under the route
 *                                // You can set alwaysShow: true, so that it will ignore the previously defined rules and always show the root route
 * redirect: noRedirect           // When noRedirect is set, the route cannot be clicked in the breadcrumb navigation
 * name:'router-name'             // Set the name of the route, it must be filled in otherwise various problems will occur when using <keep-alive>
 * meta : {
    noCache: true                // If set to true, it will not be cached by <keep-alive> (default false)
    title: 'title'               // Set the name of the route displayed in the sidebar and breadcrumbs
    icon: 'svg-name'             // Set the icon of the route, corresponding to the path src/assets/icons/svg
    breadcrumb: false            // If set to false, it will not be displayed in breadcrumb
  }
 */

// Public routing
export const constantRoutes = [
  {
    path: '/redirect',
    component: Layout,
    hidden: true,
    children: [
      {
        path: '/redirect/:path(.*)',
        component: (resolve) => require(['@/views/redirect'], resolve)
      }
    ]
  },
  {
    path: '/login',
    component: (resolve) => require(['@/views/login'], resolve),
    hidden: true
  },
  {
    path: '/404',
    component: (resolve) => require(['@/views/error/404'], resolve),
    hidden: true
  },
  {
    path: '/401',
    component: (resolve) => require(['@/views/error/401'], resolve),
    hidden: true
  },
  {
    path: '',
    component: Layout,
    redirect: 'index',
    children: [
      {
        path: 'index',
        component: (resolve) => require(['@/views/index'], resolve),
        name: 'Dashboard',
        meta: { title: 'Dashboard', icon: 'dashboard', noCache: true, affix: true }
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
        component: (resolve) => require(['@/views/system/user/profile/index'], resolve),
        name: 'Profile',
        meta: { title: 'Profile', icon: 'user' }
      }
    ]
  },
  {
    path: '/dict',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'type/data/:dictId(\\d+)',
        component: (resolve) => require(['@/views/system/dict/data'], resolve),
        name: 'Data',
        meta: { title: 'Dictionary Data', icon: '' }
      }
    ]
  },
  {
    path: '/job',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'log',
        component: (resolve) => require(['@/views/monitor/job/log'], resolve),
        name: 'JobLog',
        meta: { title: 'Scheduling log' }
      }
    ]
  },
  {
    path: '/gen',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'edit/:tableId(\\d+)',
        component: (resolve) => require(['@/views/tool/gen/editTable'], resolve),
        name: 'GenEdit',
        meta: { title: 'Modify build configuration' }
      }
    ]
  },
  {
    path: '/work',
    component: Layout,
    hidden: true,
    children: [
      {
        path: 'job/:jobId(\\d+)',
        component: (resolve) => require(['@/views/mes/work/index'], resolve),
        name: 'Work Order',
        meta: { title: 'Work Order', icon: '' }
      }
    ]
  }
]

export default new Router({
  mode: 'history', // Remove the # in the url
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})
