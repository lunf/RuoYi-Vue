<template>
  <el-form ref="form" :model="user" :rules="rules" label-width="80px">
    <el-form-item label="The old code." prop="oldPassword">
      <el-input v-model="user.oldPassword" placeholder="Please enter the old password." type="password" show-password/>
    </el-form-item>
    <el-form-item label="The new code." prop="newPassword">
      <el-input v-model="user.newPassword" placeholder="Please enter a new password." type="password" show-password/>
    </el-form-item>
    <el-form-item label="Confirm the password." prop="confirmPassword">
      <el-input v-model="user.confirmPassword" placeholder="Please confirm the new password." type="password" show-password/>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">preserved</el-button>
      <el-button type="danger" size="mini" @click="close">closed</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserPwd } from "@/api/system/user";

export default {
  data() {
    const equalToPassword = (rule, value, callback) => {
      if (this.user.newPassword !== value) {
        callback(new Error("Two passwords are incompatible."));
      } else {
        callback();
      }
    };
    return {
      user: {
        oldPassword: undefined,
        newPassword: undefined,
        confirmPassword: undefined
      },
      // Forms of Examination
      rules: {
        oldPassword: [
          { required: true, message: "The old password cannot be empty.", trigger: "blur" }
        ],
        newPassword: [
          { required: true, message: "The new password cannot be empty.", trigger: "blur" },
          { min: 6, max: 20, message: "The length is 6 to 20 A character.", trigger: "blur" },
          { pattern: /^[^<>"'|\\]+$/, message: "It cannot contain illegal characters.：< > \" ' \\\ |", trigger: "blur" }
        ],
        confirmPassword: [
          { required: true, message: "Confirm that the password cannot be empty.", trigger: "blur" },
          { required: true, validator: equalToPassword, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserPwd(this.user.oldPassword, this.user.newPassword).then(response => {
            this.$modal.msgSuccess("Changes are Successful");
          });
        }
      });
    },
    close() {
      this.$tab.closePage();
    }
  }
};
</script>
