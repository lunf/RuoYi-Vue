<template>
  <div>
    <div class="user-info-head" @click="editCropper()"><img v-bind:src="options.img" title="Click to upload avatar" class="img-circle img-lg" /></div>
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body @opened="modalOpened">
      <el-row>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <vue-cropper
            ref="cropper"
            :img="options.img"
            :info="true"
            :autoCrop="options.autoCrop"
            :autoCropWidth="options.autoCropWidth"
            :autoCropHeight="options.autoCropHeight"
            :fixedBox="options.fixedBox"
            @realTime="realTime"
            v-if="visible"
          />
        </el-col>
        <el-col :xs="24" :md="12" :style="{height: '350px'}">
          <div class="avatar-upload-preview">
            <img :src="previews.url" :style="previews.img" />
          </div>
        </el-col>
      </el-row>
      <br />
      <el-row>
        <el-col :lg="2" :md="2">
          <el-upload action="#" :http-request="requestUpload" :show-file-list="false" :before-upload="beforeUpload">
            <el-button size="small">
              Select
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{span: 1, offset: 2}" :md="2">
          <el-button icon="el-icon-plus" size="small" @click="changeScale(1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft()"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :md="2">
          <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight()"></el-button>
        </el-col>
        <el-col :lg="{span: 2, offset: 6}" :md="2">
          <el-button type="primary" size="small" @click="uploadImg()">Submit</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import store from "@/store";
import { VueCropper } from "vue-cropper";
import { uploadAvatar } from "@/api/system/user";

export default {
  components: { VueCropper },
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      // Whether to show the pop-up layer
      open: false,
      // Whether to show cropper
      visible: false,
      // Pop-up layer title
      title: "Modify avatar",
      options: {
        img: store.getters.avatar, // The address of the cropped picture
        autoCrop: true, // Whether to generate a screenshot frame by default
        autoCropWidth: 200, // The default screenshot frame width
        autoCropHeight: 200, // The default height of the screenshot frame
        fixedBox: true // The size of the screenshot frame is fixed and cannot be changed
      },
      previews: {}
    };
  },
  methods: {
    // Edit avatar
    editCropper() {
      this.open = true;
    },
    // Callback at the end of opening the popup layer
    modalOpened() {
      this.visible = true;
    },
    // Override the default upload behavior
    requestUpload() {
    },
    // Rotate to the left
    rotateLeft() {
      this.$refs.cropper.rotateLeft();
    },
    // Rotate to the right
    rotateRight() {
      this.$refs.cropper.rotateRight();
    },
    // Picture zoom
    changeScale(num) {
      num = num || 1;
      this.$refs.cropper.changeScale(num);
    },
    // Upload pre-processing
    beforeUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.msgError("The file format is not supported, please upload the image type, such as: JPG, PNG suffix files.");
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.options.img = reader.result;
        };
      }
    },
    // Upload image
    uploadImg() {
      this.$refs.cropper.getCropBlob(data => {
        let formData = new FormData();
        formData.append("avatarfile", data);
        uploadAvatar(formData).then(response => {
          this.open = false;
          this.options.img = process.env.VUE_APP_BASE_API + response.imgUrl;
          store.commit('SET_AVATAR', this.options.img);
          this.msgSuccess("修改成功");
          this.visible = false;
        });
      });
    },
    // Real-time preview
    realTime(data) {
      this.previews = data;
    }
  }
};
</script>
<style scoped lang="scss">
.user-info-head {
  position: relative;
  display: inline-block;
  height: 120px;
}

.user-info-head:hover:after {
  content: '+';
  position: absolute;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  color: #eee;
  background: rgba(0, 0, 0, 0.5);
  font-size: 24px;
  font-style: normal;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  cursor: pointer;
  line-height: 110px;
  border-radius: 50%;
}
</style>
