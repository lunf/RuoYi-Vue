import store from '@/store'
import router from '@/router';

export default {
  // Updated CurrenttabPage signs
  refreshPage(obj) {
    const { path, query, matched } = router.currentRoute;
    if (obj === undefined) {
      matched.forEach((m) => {
        if (m.components && m.components.default && m.components.default.name) {
          if (!['Layout', 'ParentView'].includes(m.components.default.name)) {
            obj = { name: m.components.default.name, path: path, query: query };
          }
        }
      });
    }
    return store.dispatch('tagsView/delCachedView', obj).then(() => {
      const { path, query } = obj
      router.replace({
        path: '/redirect' + path,
        query: query
      })
    })
  },
  // Closed at present.tabPage signs，Open a new page.
  closeOpenPage(obj) {
    store.dispatch("tagsView/delView", router.currentRoute);
    if (obj !== undefined) {
      return router.push(obj);
    }
  },
  // Closing the designation.tabPage signs
  closePage(obj) {
    if (obj === undefined) {
      return store.dispatch('tagsView/delView', router.currentRoute).then(({ visitedViews }) => {
        const latestView = visitedViews.slice(-1)[0]
        if (latestView) {
          return router.push(latestView.fullPath)
        }
        return router.push('/');
      });
    }
    return store.dispatch('tagsView/delView', obj);
  },
  // Close all.tabPage signs
  closeAllPage() {
    return store.dispatch('tagsView/delAllViews');
  },
  // Closed on the left.tabPage signs
  closeLeftPage(obj) {
    return store.dispatch('tagsView/delLeftTags', obj || router.currentRoute);
  },
  // Close the right side.tabPage signs
  closeRightPage(obj) {
    return store.dispatch('tagsView/delRightTags', obj || router.currentRoute);
  },
  // Closing the others.tabPage signs
  closeOtherPage(obj) {
    return store.dispatch('tagsView/delOthersViews', obj || router.currentRoute);
  },
  // AddedtabPage signs
  openPage(title, url, params) {
    const obj = { path: url, meta: { title: title } }
    store.dispatch('tagsView/addView', obj);
    return router.push({ path: url, query: params });
  },
  // ModifiedtabPage signs
  updatePage(obj) {
    return store.dispatch('tagsView/updateVisitedView', obj);
  }
}
