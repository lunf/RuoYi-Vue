<template>
  <div class="upload-file">
    <el-upload
      :action="uploadFileUrl"
      :before-upload="handleBeforeUpload"
      :file-list="fileList"
      :limit="1"
      :on-error="handleUploadError"
      :on-exceed="handleExceed"
      :on-success="handleUploadSuccess"
      :show-file-list="false"
      :headers="headers"
      class="upload-file-uploader"
      ref="upload"
    >
      <!-- Upload button -->
      <el-button size="mini" type="primary">Select file</el-button>
      <!-- Upload tips -->
      <div class="el-upload__tip" slot="tip" v-if="showTip">
        Please upload
        <template v-if="fileSize"> Size not exceeding <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
        <template v-if="fileType"> The format is <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>
        document
      </div>
    </el-upload>

    <!-- document list -->
    <transition-group class="upload-file-list el-upload-list el-upload-list--text" name="el-fade-in-linear" tag="ul">
      <li :key="file.uid" class="el-upload-list__item ele-upload-list__item-content" v-for="(file, index) in list">
        <el-link :href="file.url" :underline="false" target="_blank">
          <span class="el-icon-document"> {{ getFileName(file.name) }} </span>
        </el-link>
        <div class="ele-upload-list__item-content-action">
          <el-link :underline="false" @click="handleDelete(index)" type="danger">Delete</el-link>
        </div>
      </li>
    </transition-group>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";

export default {
  props: {
    // Value
    value: [String, Object, Array],
    // Size limit (MB)
    fileSize: {
      type: Number,
      default: 10,
    },
    // File type, for example ['png','jpg','jpeg']
    fileType: {
      type: Array,
      default: () => ["xls", "txt", "zip"],
    },
    // Should to show prompt
    isShowTip: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/common/upload", // Uploaded server address
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      fileList: [],
    };
  },
  computed: {
    // Should to show prompt
    showTip() {
      return this.isShowTip && (this.fileType || this.fileSize);
    },
    // List
    list() {
      let temp = 1;
      if (this.value) {
        // First convert the value to an array
        const list = Array.isArray(this.value) ? this.value : [this.value];
        // Then turn the array into an object array
        return list.map((item) => {
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
  },
  methods: {
    // Check format and size before uploading
    handleBeforeUpload(file) {
      // Type of proof file
      if (this.fileType) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
        }
        const isTypeOk = this.fileType.some((type) => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
        if (!isTypeOk) {
          this.$message.error(`The file format is incorrect, please upload the file in ${this.fileType.join("/")} format!`);
          return false;
        }
      }
      // Check file size
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$message.error(`The upload file size cannot exceed ${this.fileSize} MB!`);
          return false;
        }
      }
      return true;
    },
    // The number of files exceeds
    handleExceed() {
      this.$message.error(`Only allow single file upload`);
    },
    // upload failed
    handleUploadError(err) {
      this.$message.error("Upload failed, please try again");
    },
    // Upload successful callback
    handleUploadSuccess(res, file) {
      this.$message.success("Upload successfully");
      this.$emit("input", res.url);
    },
    // Delete Files
    handleDelete(index) {
      this.fileList.splice(index, 1);
      this.$emit("input", '');
    },
    // Get file name
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        return name.slice(name.lastIndexOf("/") + 1).toLowerCase();
      } else {
        return "";
      }
    }
  },
  created() {
    this.fileList = this.list;
  },
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
