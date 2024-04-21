<template>
  <div class="upload-file">
    <el-upload
      multiple
      :action="uploadFileUrl"
      :before-upload="handleBeforeUpload"
      :file-list="fileList"
      :limit="limit"
      :on-error="handleUploadError"
      :on-exceed="handleExceed"
      :on-success="handleUploadSuccess"
      :show-file-list="false"
      :headers="headers"
      class="upload-file-uploader"
      ref="fileUpload"
    >
      <!-- upload the button. -->
      <el-button size="mini" type="primary">Selection of documents.</el-button>
      <!-- upload the advice. -->
      <div class="el-upload__tip" slot="tip" v-if="showTip">
        Please upload
        <template v-if="fileSize"> The size does not exceed <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
        <template v-if="fileType"> Form for <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>
        The documents.
      </div>
    </el-upload>

    <!-- List of documents -->
    <transition-group class="upload-file-list el-upload-list el-upload-list--text" name="el-fade-in-linear" tag="ul">
      <li :key="file.url" class="el-upload-list__item ele-upload-list__item-content" v-for="(file, index) in fileList">
        <el-link :href="`${baseUrl}${file.url}`" :underline="false" target="_blank">
          <span class="el-icon-document"> {{ getFileName(file.name) }} </span>
        </el-link>
        <div class="ele-upload-list__item-content-action">
          <el-link :underline="false" @click="handleDelete(index)" type="danger">removed</el-link>
        </div>
      </li>
    </transition-group>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";

export default {
  name: "FileUpload",
  props: {
    // Value
    value: [String, Object, Array],
    // The number limitation
    limit: {
      type: Number,
      default: 5,
    },
    // The size limit.(MB)
    fileSize: {
      type: Number,
      default: 5,
    },
    // Type of document, for example['png', 'jpg', 'jpeg']
    fileType: {
      type: Array,
      default: () => ["doc", "xls", "ppt", "txt", "pdf"],
    },
    // Showing a tip.
    isShowTip: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      number: 0,
      uploadList: [],
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // The file server address.
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      fileList: [],
    };
  },
  watch: {
    value: {
      handler(val) {
        if (val) {
          let temp = 1;
          // First, convert the value into groups.
          const list = Array.isArray(val) ? val : this.value.split(',');
          // Then convert the group into the object group.
          this.fileList = list.map(item => {
            if (typeof item === "string") {
              item = { name: item, url: item };
            }
            item.uid = item.uid || new Date().getTime() + temp++;
            return item;
          });
        } else {
          this.fileList = [];
          return [];
        }
      },
      deep: true,
      immediate: true
    }
  },
  computed: {
    // Showing a tip.
    showTip() {
      return this.isShowTip && (this.fileType || this.fileSize);
    },
  },
  methods: {
    // Pre-school examination format and size.
    handleBeforeUpload(file) {
      // Type of school inspection document
      if (this.fileType) {
        const fileName = file.name.split('.');
        const fileExt = fileName[fileName.length - 1];
        const isTypeOk = this.fileType.indexOf(fileExt) >= 0;
        if (!isTypeOk) {
          this.$modal.msgError(`File format is incorrect., Please upload${this.fileType.join("/")}Format of documents!`);
          return false;
        }
      }
      // School examination file size.
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$modal.msgError(`The file size cannot exceed ${this.fileSize} MB!`);
          return false;
        }
      }
      this.$modal.loading("The file is uploaded.，Please wait a little....");
      this.number++;
      return true;
    },
    // The number of documents exceeds
    handleExceed() {
      this.$modal.msgError(`The number of files can not exceed. ${this.limit} one!`);
    },
    // upload failure.
    handleUploadError(err) {
      this.$modal.msgError("Filed failure.，Please try again.");
      this.$modal.closeLoading();
    },
    // Successfully uploaded
    handleUploadSuccess(res, file) {
      if (res.code === 200) {
        this.uploadList.push({ name: res.fileName, url: res.fileName });
        this.uploadedSuccessfully();
      } else {
        this.number--;
        this.$modal.closeLoading();
        this.$modal.msgError(res.msg);
        this.$refs.fileUpload.handleRemove(file);
        this.uploadedSuccessfully();
      }
    },
    // Delete the document.
    handleDelete(index) {
      this.fileList.splice(index, 1);
      this.$emit("input", this.listToString(this.fileList));
    },
    // End up processing.
    uploadedSuccessfully() {
      if (this.number > 0 && this.uploadList.length === this.number) {
        this.fileList = this.fileList.concat(this.uploadList);
        this.uploadList = [];
        this.number = 0;
        this.$emit("input", this.listToString(this.fileList));
        this.$modal.closeLoading();
      }
    },
    // Get the document name.
    getFileName(name) {
      // If it isurlTake the last name. If not directly back.
      if (name.lastIndexOf("/") > -1) {
        return name.slice(name.lastIndexOf("/") + 1);
      } else {
        return name;
      }
    },
    // Convert the object to a specified string separation
    listToString(list, separator) {
      let strs = "";
      separator = separator || ",";
      for (let i in list) {
        strs += list[i].url + separator;
      }
      return strs != '' ? strs.substr(0, strs.length - 1) : '';
    }
  }
};
</script>

<style scoped lang="scss">
.upload-file-uploader {
  margin-bottom: 5px;
}
.upload-file-list .el-upload-list__item {
  border: 1px solid #e4e7ed;
  line-height: 2;
  margin-bottom: 10px;
  position: relative;
}
.upload-file-list .ele-upload-list__item-content {
  display: flex;
  justify-content: space-between;
  align-items: center;
  color: inherit;
}
.ele-upload-list__item-content-action .el-link {
  margin-right: 10px;
}
</style>
