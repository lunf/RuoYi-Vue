<template>
  <el-form ref="form" :model="user" :rules="rules" class="user-form">
    <el-form-item label="Old Password" prop="oldPassword">
      <el-input v-model="user.oldPassword" placeholder="Please enter the old password" type="password" />
    </el-form-item>
    <el-form-item label="New Password" prop="newPassword">
      <el-input v-model="user.newPassword" placeholder="Please enter a new password" type="password" />
    </el-form-item>
    <el-form-item label="Confirm Password" prop="confirmPassword">
      <el-input v-model="user.confirmPassword" placeholder="Please confirm your password" type="password" />
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">Save</el-button>
      <el-button type="danger" size="mini" @click="close">Cancel</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserPwd } from "@/api/system/user";

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("The two passwords entered do not matched"));
      } else {
        callback();
      }
    };
    return {
      test: "1test",
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      // Form validation
      rules: {
        oldPassword: [
          { required: true, message: "Old password cannot be empty", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "New password cannot be empty", trigger: "blur" },
          { min: 6, max: 20, message: "6 to 20 characters in length", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "Confirm password can not be blank", trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwd(this.user.oldPassword, this.user.newPassword).then(
            response => {
              this.msgSuccess("Successfully updated");
            }
          );
        }
      });
    },
    close() {
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/index" });
    }
  }
};
</script>
