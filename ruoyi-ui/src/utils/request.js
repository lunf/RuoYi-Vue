import axios from 'axios'
import { Notification, MessageBox, Message, Loading } from 'element-ui'
import store from '@/store'
import { getToken } from '@/utils/auth'
import errorCode from '@/utils/errorCode'
import { tansParams, blobValidate } from "@/utils/ruoyi";
import cache from '@/plugins/cache'
import { saveAs } from 'file-saver'

let downloadLoadingInstance;
// Showing re-logging
export let isRelogin = { show: false };

axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// CreatedaxiosExamples
const service = axios.create({
  // axiosApplications are available.baseURLOptions，expressed request.URLThe public part
  baseURL: process.env.VUE_APP_BASE_API,
  // Super time
  timeout: 10000
})

// requestthe blocker.
service.interceptors.request.use(config => {
  // Is it necessary to set up token
  const isToken = (config.headers || {}).isToken === false
  // Is it necessary to prevent data from being submitted again?
  const isRepeatSubmit = (config.headers || {}).repeatSubmit === false
  if (getToken() && !isToken) {
    config.headers['Authorization'] = 'Bearer ' + getToken() // All requests are customized.token Please change according to the actual situation.
  }
  // getRequest for shooting.paramsParameters
  if (config.method === 'get' && config.params) {
    let url = config.url + '?' + tansParams(config.params);
    url = url.slice(0, -1);
    config.params = {};
    config.url = url;
  }
  if (!isRepeatSubmit && (config.method === 'post' || config.method === 'put')) {
    const requestObj = {
      url: config.url,
      data: typeof config.data === 'object' ? JSON.stringify(config.data) : config.data,
      time: new Date().getTime()
    }
    const requestSize = Object.keys(JSON.stringify(requestObj)).length; // request data size.
    const limitSize = 5 * 1024 * 1024; // Restriction of data storage5M
    if (requestSize >= limitSize) {
      console.warn(`[${config.url}]: ` + 'The request data is greater than permitted.5MThe limitation，Unable to verify repeated submissions.。')
      return config;
    }
    const sessionObj = cache.session.getJSON('sessionObj')
    if (sessionObj === undefined || sessionObj === null || sessionObj === '') {
      cache.session.setJSON('sessionObj', requestObj)
    } else {
      const s_url = sessionObj.url;                  // requested address
      const s_data = sessionObj.data;                // requested data
      const s_time = sessionObj.time;                // Request time.
      const interval = 1000;                         // interval time.(ms)，Less than this time is considered to be repeated.
      if (s_data === requestObj.data && requestObj.time - s_time < interval && s_url === requestObj.url) {
        const message = 'Data is being processed.，Do not repeat submission.';
        console.warn(`[${s_url}]: ` + message)
        return Promise.reject(new Error(message))
      } else {
        cache.session.setJSON('sessionObj', requestObj)
      }
    }
  }
  return config
}, error => {
    console.log(error)
    Promise.reject(error)
})

// Response to blockers.
service.interceptors.response.use(res => {
    // Uninstalled status code is default success status.
    const code = res.data.code || 200;
    // Obtaining Wrong Information
    const msg = errorCode[code] || res.data.msg || errorCode['default']
    // The data is returned directly.
    if (res.request.responseType ===  'blob' || res.request.responseType ===  'arraybuffer') {
      return res.data
    }
    if (code === 401) {
      if (!isRelogin.show) {
        isRelogin.show = true;
        MessageBox.confirm('Registration has expired.，You can stay on this page.，or re-enroll.', 'system advice', { confirmButtonText: 'Registered again.', cancelButtonText: 'cancelled', type: 'warning' }).then(() => {
          isRelogin.show = false;
          store.dispatch('LogOut').then(() => {
            location.href = '/index';
          })
      }).catch(() => {
        isRelogin.show = false;
      });
    }
      return Promise.reject('Ineffective Meetings，Or the meeting is over.，Please re-enroll。')
    } else if (code === 500) {
      Message({ message: msg, type: 'error' })
      return Promise.reject(new Error(msg))
    } else if (code === 601) {
      Message({ message: msg, type: 'warning' })
      return Promise.reject('error')
    } else if (code !== 200) {
      Notification.error({ title: msg })
      return Promise.reject('error')
    } else {
      return res.data
    }
  },
  error => {
    console.log('err' + error)
    let { message } = error;
    if (message == "Network Error") {
      message = "Unusual connectivity.";
    } else if (message.includes("timeout")) {
      message = "System Interface Request Overtime";
    } else if (message.includes("Request failed with status code")) {
      message = "system interface" + message.substr(message.length - 3) + "Unusual";
    }
    Message({ message: message, type: 'error', duration: 5 * 1000 })
    return Promise.reject(error)
  }
)

// General Downloads Method
export function download(url, params, filename, config) {
  downloadLoadingInstance = Loading.service({ text: "The data is downloading.，Please wait a little.", spinner: "el-icon-loading", background: "rgba(0, 0, 0, 0.7)", })
  return service.post(url, params, {
    transformRequest: [(params) => { return tansParams(params) }],
    headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
    responseType: 'blob',
    ...config
  }).then(async (data) => {
    const isBlob = blobValidate(data);
    if (isBlob) {
      const blob = new Blob([data])
      saveAs(blob, filename)
    } else {
      const resText = await data.text();
      const rspObj = JSON.parse(resText);
      const errMsg = errorCode[rspObj.code] || rspObj.msg || errorCode['default']
      Message.error(errMsg);
    }
    downloadLoadingInstance.close();
  }).catch((r) => {
    console.error(r)
    Message.error('There is an error in the file download.，Please contact the manager.！')
    downloadLoadingInstance.close();
  })
}

export default service
