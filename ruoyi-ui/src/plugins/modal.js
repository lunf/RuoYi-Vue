import { Message, MessageBox, Notification, Loading } from 'element-ui'

let loadingInstance;

export default {
  // News Tips
  msg(content) {
    Message.info(content)
  },
  // The wrong news.
  msgError(content) {
    Message.error(content)
  },
  // Success news
  msgSuccess(content) {
    Message.success(content)
  },
  // warning news.
  msgWarning(content) {
    Message.warning(content)
  },
  // The tip.
  alert(content) {
    MessageBox.alert(content, "system advice")
  },
  // The wrong advice.
  alertError(content) {
    MessageBox.alert(content, "system advice", { type: 'error' })
  },
  // Success Tips
  alertSuccess(content) {
    MessageBox.alert(content, "system advice", { type: 'success' })
  },
  // warning advice.
  alertWarning(content) {
    MessageBox.alert(content, "system advice", { type: 'warning' })
  },
  // Notification of advice
  notify(content) {
    Notification.info(content)
  },
  // The wrong notification.
  notifyError(content) {
    Notification.error(content);
  },
  // Success notification
  notifySuccess(content) {
    Notification.success(content)
  },
  // warning notification.
  notifyWarning(content) {
    Notification.warning(content)
  },
  // Confirm the window.
  confirm(content) {
    return MessageBox.confirm(content, "system advice", {
      confirmButtonText: 'Certainly',
      cancelButtonText: 'cancelled',
      type: "warning",
    })
  },
  // submitted content
  prompt(content) {
    return MessageBox.prompt(content, "system advice", {
      confirmButtonText: 'Certainly',
      cancelButtonText: 'cancelled',
      type: "warning",
    })
  },
  // Open the cover.
  loading(content) {
    loadingInstance = Loading.service({
      lock: true,
      text: content,
      spinner: "el-icon-loading",
      background: "rgba(0, 0, 0, 0.7)",
    })
  },
  // Close the cover.
  closeLoading() {
    loadingInstance.close();
  }
}
