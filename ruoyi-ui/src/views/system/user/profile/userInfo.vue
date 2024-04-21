<template>
  <el-form ref="form" :model="form" :rules="rules" label-width="80px">
    <el-form-item label="Users say" prop="nickName">
      <el-input v-model="form.nickName" maxlength="30" />
    </el-form-item> 
    <el-form-item label="The phone number." prop="phonenumber">
      <el-input v-model="form.phonenumber" maxlength="11" />
    </el-form-item>
    <el-form-item label="The mailbox" prop="email">
      <el-input v-model="form.email" maxlength="50" />
    </el-form-item>
    <el-form-item label="The gender">
      <el-radio-group v-model="form.sex">
        <el-radio label="0">The Man</el-radio>
        <el-radio label="1">The Woman</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item>
      <el-button type="primary" size="mini" @click="submit">preserved</el-button>
      <el-button type="danger" size="mini" @click="close">closed</el-button>
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
      form: {},
      // Forms of Examination
      rules: {
        nickName: [
          { required: true, message: "Users say they cannot be empty.", trigger: "blur" }
        ],
        email: [
          { required: true, message: "The mailbox address cannot be empty.", trigger: "blur" },
          {
            type: "email",
            message: "Please enter the correct mailbox address.",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
          { required: true, message: "The phone number cannot be empty.", trigger: "blur" },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "Please enter the correct phone number.",
            trigger: "blur"
          }
        ]
      }
    };
  },
  watch: {
    user: {
      handler(user) {
        if (user) {
          this.form = { nickName: user.nickName, phonenumber: user.phonenumber, email: user.email, sex: user.sex };
        }
      },
      immediate: true
    }
  },
  methods: {
    submit() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateUserProfile(this.form).then(response => {
            this.$modal.msgSuccess("Changes are Successful");
            this.user.phonenumber = this.form.phonenumber;
            this.user.email = this.form.email;
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
