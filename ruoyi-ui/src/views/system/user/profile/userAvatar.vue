<template>
  <div>
    <div class="user-info-head" @click="editCropper()"><img v-bind:src="options.img" title="Click to upload the image." class="img-circle img-lg" /></div>
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body @opened="modalOpened"  @close="closeDialog">
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
            :outputType="options.outputType"
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
        <el-col :lg="2" :sm="3" :xs="3">
          <el-upload action="#" :http-request="requestUpload" :show-file-list="false" :before-upload="beforeUpload">
            <el-button size="small">
              The choice
              <i class="el-icon-upload el-icon--right"></i>
            </el-button>
          </el-upload>
        </el-col>
        <el-col :lg="{span: 1, offset: 2}" :sm="2" :xs="2">
          <el-button icon="el-icon-plus" size="small" @click="changeScale(1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :sm="2" :xs="2">
          <el-button icon="el-icon-minus" size="small" @click="changeScale(-1)"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :sm="2" :xs="2">
          <el-button icon="el-icon-refresh-left" size="small" @click="rotateLeft()"></el-button>
        </el-col>
        <el-col :lg="{span: 1, offset: 1}" :sm="2" :xs="2">
          <el-button icon="el-icon-refresh-right" size="small" @click="rotateRight()"></el-button>
        </el-col>
        <el-col :lg="{span: 2, offset: 6}" :sm="2" :xs="2">
          <el-button type="primary" size="small" @click="uploadImg()">by Ti by</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import store from "@/store";
import { VueCropper } from "vue-cropper";
import { uploadAvatar } from "@/api/system/user";
import { debounce } from '@/utils'

export default {
  components: { VueCropper },
  data() {
    return {
      // Showing the explosion.
      open: false,
      // Should showcropper
      visible: false,
      // The top title.
      title: "Change the image.",
      options: {
        img: store.getters.avatar,  //Cut the image address.
        autoCrop: true,             // Do you generate a screenshot box?
        autoCropWidth: 200,         // Default generate screenshots width
        autoCropHeight: 200,        // Default Generate Screenshots Height
        fixedBox: true,             // Fixed Screenshot Size Do not allow change.
        outputType:"png",           // Create a picture forPNGformats
        filename: 'avatar'          // Name of document
      },
      previews: {},
      resizeHandler: null
    };
  },
  methods: {
    // Editing the image.
    editCropper() {
      this.open = true;
    },
    // Turn off at the end of the explosion.
    modalOpened() {
      this.visible = true;
      if (!this.resizeHandler) {
        this.resizeHandler = debounce(() => {
          this.refresh()
        }, 100)
      }
      window.addEventListener("resize", this.resizeHandler)
    },
    // Updated components
    refresh() {
      this.$refs.cropper.refresh();
    },
    // Cover default upload behavior.
    requestUpload() {
    },
    // Turn to the left.
    rotateLeft() {
      this.$refs.cropper.rotateLeft();
    },
    // Turn to the right.
    rotateRight() {
      this.$refs.cropper.rotateRight();
    },
    // Picture is shortened.
    changeScale(num) {
      num = num || 1;
      this.$refs.cropper.changeScale(num);
    },
    // The Pre-Treatment
    beforeUpload(file) {
      if (file.type.indexOf("image/") == -1) {
        this.$modal.msgError("File Format Error，Please upload the image type.,as：JPG，PNGThe following documentation.。");
      } else {
        const reader = new FileReader();
        reader.readAsDataURL(file);
        reader.onload = () => {
          this.options.img = reader.result;
          this.options.filename = file.name;
        };
      }
    },
    // uploaded images
    uploadImg() {
      this.$refs.cropper.getCropBlob(data => {
        let formData = new FormData();
        formData.append("avatarfile", data, this.options.filename);
        uploadAvatar(formData).then(response => {
          this.open = false;
          this.options.img = process.env.VUE_APP_BASE_API + response.imgUrl;
          store.commit('SET_AVATAR', this.options.img);
          this.$modal.msgSuccess("Changes are Successful");
          this.visible = false;
        });
      });
    },
    // Real time forecast
    realTime(data) {
      this.previews = data;
    },
    // Close the window.
    closeDialog() {
      this.options.img = store.getters.avatar
      this.visible = false;
      window.removeEventListener("resize", this.resizeHandler)
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
