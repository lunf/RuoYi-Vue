const state = {
  dict: new Array()
}
const mutations = {
  SET_DICT: (state, { key, value }) => {
    if (key !== null && key !== "") {
      state.dict.push({
        key: key,
        value: value
      })
    }
  },
  REMOVE_DICT: (state, key) => {
    try {
      for (let i = 0; i < state.dict.length; i++) {
        if (state.dict[i].key == key) {
          state.dict.splice(i, 1)
          return true
        }
      }
    } catch (e) {
    }
  },
  CLEAN_DICT: (state) => {
    state.dict = new Array()
  }
}

const actions = {
  // Set up the dictionary.
  setDict({ commit }, data) {
    commit('SET_DICT', data)
  },
  // Remove the dictionary.
  removeDict({ commit }, key) {
    commit('REMOVE_DICT', key)
  },
  // The empty dictionary.
  cleanDict({ commit }) {
    commit('CLEAN_DICT')
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

