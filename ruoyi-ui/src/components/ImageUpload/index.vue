<template>
  <div class="component-upload-image">
    <el-upload
      multiple
      :action="uploadImgUrl"
      list-type="picture-card"
      :on-success="handleUploadSuccess"
      :before-upload="handleBeforeUpload"
      :limit="limit"
      :on-error="handleUploadError"
      :on-exceed="handleExceed"
      ref="imageUpload"
      :on-remove="handleDelete"
      :show-file-list="true"
      :headers="headers"
      :file-list="fileList"
      :on-preview="handlePictureCardPreview"
      :class="{hide: this.fileList.length >= this.limit}"
    >
      <i class="el-icon-plus"></i>
    </el-upload>

    <!-- upload the advice. -->
    <div class="el-upload__tip" slot="tip" v-if="showTip">
      Please upload
      <template v-if="fileSize"> The size does not exceed <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
      <template v-if="fileType"> Form for <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>
      The documents.
    </div>

    <el-dialog
      :visible.sync="dialogVisible"
      title="Preview"
      width="800"
      append-to-body
    >
      <img
        :src="dialogImageUrl"
        style="display: block; max-width: 100%; margin: 0 auto"
      />
    </el-dialog>
  </div>
</template>

<script>
import { getToken } from "@/utils/auth";

export default {
  props: {
    value: [String, Object, Array],
    // Number of images limited
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
      default: () => ["png", "jpg", "jpeg"],
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
      dialogImageUrl: "",
      dialogVisible: false,
      hideUpload: false,
      baseUrl: process.env.VUE_APP_BASE_API,
      uploadImgUrl: process.env.VUE_APP_BASE_API + "/common/upload", // The image server address uploaded
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      fileList: []
    };
  },
  watch: {
    value: {
      handler(val) {
        if (val) {
          // First, convert the value into groups.
          const list = Array.isArray(val) ? val : this.value.split(',');
          // Then convert the group into the object group.
          this.fileList = list.map(item => {
            if (typeof item === "string") {
              if (item.indexOf(this.baseUrl) === -1) {
                  item = { name: this.baseUrl + item, url: this.baseUrl + item };
              } else {
                  item = { name: item, url: item };
              }
            }
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
    // Before the uploadloadingloaded
    handleBeforeUpload(file) {
      let isImg = false;
      if (this.fileType.length) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
        }
        isImg = this.fileType.some(type => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
      } else {
        isImg = file.type.indexOf("image") > -1;
      }

      if (!isImg) {
        this.$modal.msgError(`File format is incorrect., Please upload${this.fileType.join("/")}Photo format file!`);
        return false;
      }
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$modal.msgError(`The size of the image does not exceed the size. ${this.fileSize} MB!`);
          return false;
        }
      }
      this.$modal.loading("The image is uploaded.，Please wait a little....");
      this.number++;
    },
    // The number of documents exceeds
    handleExceed() {
      this.$modal.msgError(`The number of files can not exceed. ${this.limit} one!`);
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
        this.$refs.imageUpload.handleRemove(file);
        this.uploadedSuccessfully();
      }
    },
    // Remove the picture.
    handleDelete(file) {
      const findex = this.fileList.map(f => f.name).indexOf(file.name);
      if (findex > -1) {
        this.fileList.splice(findex, 1);
        this.$emit("input", this.listToString(this.fileList));
      }
    },
    // upload failure.
    handleUploadError() {
      this.$modal.msgError("Photo upload failed.，Please try again.");
      this.$modal.closeLoading();
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
    // Preview
    handlePictureCardPreview(file) {
      this.dialogImageUrl = file.url;
      this.dialogVisible = true;
    },
    // Convert the object to a specified string separation
    listToString(list, separator) {
      let strs = "";
      separator = separator || ",";
      for (let i in list) {
        if (list[i].url) {
          strs += list[i].url.replace(this.baseUrl, "") + separator;
        }
      }
      return strs != '' ? strs.substr(0, strs.length - 1) : '';
    }
  }
};
</script>
<style scoped lang="scss">
// .el-upload--picture-card Control of the number.
::v-deep.hide .el-upload--picture-card {
    display: none;
}
// Remove the animation effect.
::v-deep .el-list-enter-active,
::v-deep .el-list-leave-active {
    transition: all 0s;
}

::v-deep .el-list-enter, .el-list-leave-active {
  opacity: 0;
  transform: translateY(0);
}
</style>

