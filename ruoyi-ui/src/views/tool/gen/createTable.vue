<template>
  <!-- Create a Table -->
  <el-dialog title="Create a Table" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <span>Create the word.(Support multiple words.)：</span>
    <el-input type="textarea" :rows="10" placeholder="Please enter the text." v-model="content"></el-input>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleCreateTable">True is fixed</el-button>
      <el-button @click="visible = false">take by</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { createTable } from "@/api/tool/gen";
export default {
  data() {
    return {
      // covered layer.
      visible: false,
      // The text content
      content: ""
    };
  },
  methods: {
    // Showing the Bullet.
    show() {
      this.visible = true;
    },
    /** Create the button operation. */
    handleCreateTable() {
      if (this.content === "") {
        this.$modal.msgError("Please enter the word.");
        return;
      }
      createTable({ sql: this.content }).then(res => {
        this.$modal.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.$emit("ok");
        }
      });
    }
  }
};
</script>
