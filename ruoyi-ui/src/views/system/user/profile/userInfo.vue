<template>
  <el-form ref="form" :model="user" :rules="rules" class="user-form">
    <el-form-item label="Display Name" prop="nickName">
      <el-input v-model="user.nickName" />
    </el-form-item>
    <el-form-item label="Phone Number" prop="phonenumber">
      <el-input v-model="user.phonenumber" maxlength="11" />
    </el-form-item>
    <el-form-item label="Email" prop="email">
      <el-input v-model="user.email" maxlength="50" />
    </el-form-item>
    <el-form-item label="Gender">
      <el-radio-group v-model="user.sex">
        <el-radio label="0">Male</el-radio>
        <el-radio label="1">Female</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">Save</el-button>
      <el-button type="danger" size="mini" @click="close">Cancel</el-button>
    </el-form-item>
  </el-form>
</template>

<script>
import { updateUserProfile } from "@/api/system/user";

export default {
  props: {
    user: {
      type: Object
    }
  },
  data() {
    return {
      // Form validation
      rules: {
        nickName: [
          { required: true, message: "User's display name cannot be empty", trigger: "blur" }
        ],
        email: [
          { required: true, message: "Email address cannot be empty", trigger: "blur" },
          {
            type: "email",
            message: "'Please input the correct email address",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          { required: true, message: "Phone number can not be blank", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "Please enter the correct phone number",
            trigger: "blur"
          }
        ]
      }
    };
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserProfile(this.user).then(response => {
            this.msgSuccess("Successfully updated");
          });
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
