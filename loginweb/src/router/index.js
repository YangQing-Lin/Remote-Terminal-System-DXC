import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'
import store from '../store';

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },



  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' },
      
    }]
  },

  {
    path: '/sys',
    component: Layout,
    redirect: '/sys/user',
    name: 'sysManager',
    meta: { title: '信息管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'changeuserinfo',
        name: 'changeuserinfo',
        component: () => import('@/views/sys/changeuserinfo'),
        meta: { title: '修改信息', icon: 'table' }
      },
      {
        path: 'changepassword',
        name: 'changepassword',
        component: () => import('@/views/sys/changepassword'),
        meta: { title: '修改密码', icon: 'tree' }
      }
    ]
  },
  {
    path: '/userlist',
    component: Layout,
    redirect: '/userlist',
    children: [{
      path: 'userlist',
      name: 'userlist',
      component: () => import('@/views/userlist/userlist'),
      meta: { title: '用户列表', icon: 'dashboard' },
      hidden: false,
    }]
  },
  {
    path: '/role',
    component: Layout,
    redirect: '/role',
    children: [{
      path: 'role',
      name: 'role',
      component: () => import('@/views/role/role'),
      meta: { title: '角色管理', icon: 'dashboard' },
      hidden: false,
    }]
  },
  {
    path: '/menu',
    component: Layout,
    redirect: '/menu',
    children: [{
      path: 'menu',
      name: 'menu',
      component: () => import('@/views/menu/menu'),
      meta: { title: '菜单管理', icon: 'dashboard' },
      hidden: false,
    }]
  },

  // 404 page must be placed at the end !!!
  // { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => {
  return new Router({
    // mode: 'history', // require service support
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
  })
}

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
