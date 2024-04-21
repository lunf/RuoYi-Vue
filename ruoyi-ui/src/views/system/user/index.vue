<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--Department Data-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="deptName"
            placeholder="Please enter the department name."
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <div class="head-container">
          <el-tree
            :data="deptOptions"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            node-key="id"
            default-expand-all
            highlight-current
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>
      <!--User Data-->
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="User Name" prop="userName">
            <el-input
              v-model="queryParams.userName"
              placeholder="Please enter the user name."
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="The phone number." prop="phonenumber">
            <el-input
              v-model="queryParams.phonenumber"
              placeholder="Please enter the phone number."
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="state of" prop="status">
            <el-select
              v-model="queryParams.status"
              placeholder="User Status"
              clearable
              style="width: 240px"
            >
              <el-option
                v-for="dict in dict.type.sys_normal_disable"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="Creating time.">
            <el-date-picker
              v-model="dateRange"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="Date of Beginning"
              end-placeholder="Date of end."
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Searching</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Repaired</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['system:user:add']"
            >Added</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:user:edit']"
            >Modified</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['system:user:remove']"
            >removed</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="info"
              plain
              icon="el-icon-upload2"
              size="mini"
              @click="handleImport"
              v-hasPermi="['system:user:import']"
            >Introduction</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['system:user:export']"
            >Exported</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" :columns="columns"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="User Number" align="center" key="userId" prop="userId" v-if="columns[0].visible" />
          <el-table-column label="User Name" align="center" key="userName" prop="userName" v-if="columns[1].visible" :show-overflow-tooltip="true" />
          <el-table-column label="Users say" align="center" key="nickName" prop="nickName" v-if="columns[2].visible" :show-overflow-tooltip="true" />
          <el-table-column label="Department" align="center" key="deptName" prop="dept.deptName" v-if="columns[3].visible" :show-overflow-tooltip="true" />
          <el-table-column label="The phone number." align="center" key="phonenumber" prop="phonenumber" v-if="columns[4].visible" width="120" />
          <el-table-column label="state of" align="center" key="status" v-if="columns[5].visible">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="Creating time." align="center" prop="createTime" v-if="columns[6].visible" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="Operations"
            align="center"
            width="160"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope" v-if="scope.row.userId !== 1">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:user:edit']"
              >Modified</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['system:user:remove']"
              >removed</el-button>
              <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:user:resetPwd', 'system:user:edit']">
                <el-button size="mini" type="text" icon="el-icon-d-arrow-right">more</el-button>
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item command="handleResetPwd" icon="el-icon-key"
                    v-hasPermi="['system:user:resetPwd']">Repeat the password.</el-dropdown-item>
                  <el-dropdown-item command="handleAuthRole" icon="el-icon-circle-check"
                    v-hasPermi="['system:user:edit']">Distribution of roles</el-dropdown-item>
                </el-dropdown-menu>
              </el-dropdown>
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>
    </el-row>

    <!-- Add or modify user configuration dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Users say" prop="nickName">
              <el-input v-model="form.nickName" placeholder="Please enter the user name." maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="The affiliate department." prop="deptId">
              <treeselect v-model="form.deptId" :options="deptOptions" :show-count="true" placeholder="Please select the affiliate department." />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="The phone number." prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="Please enter the phone number." maxlength="11" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="The mailbox" prop="email">
              <el-input v-model="form.email" placeholder="Please enter the mailbox." maxlength="50" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="User Name" prop="userName">
              <el-input v-model="form.userName" placeholder="Please enter the user name." maxlength="30" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item v-if="form.userId == undefined" label="User Password" prop="password">
              <el-input v-model="form.password" placeholder="Please enter the user password." type="password" maxlength="20" show-password/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="User gender">
              <el-select v-model="form.sex" placeholder="Please choose gender.">
                <el-option
                  v-for="dict in dict.type.sys_user_sex"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="state of">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="The job">
              <el-select v-model="form.postIds" multiple placeholder="Please choose a job.">
                <el-option
                  v-for="item in postOptions"
                  :key="item.postId"
                  :label="item.postName"
                  :value="item.postId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="The role">
              <el-select v-model="form.roleIds" multiple placeholder="Please choose the role.">
                <el-option
                  v-for="item in roleOptions"
                  :key="item.roleId"
                  :label="item.roleName"
                  :value="item.roleId"
                  :disabled="item.status == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="Note to">
              <el-input v-model="form.remark" type="textarea" placeholder="Please enter the content."></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">True is fixed</el-button>
        <el-button @click="cancel">take by</el-button>
      </div>
    </el-dialog>

    <!-- Users enter the dialog box. -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">Take the documents here.，or<em>Click to upload.</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <div class="el-upload__tip" slot="tip">
            <el-checkbox v-model="upload.updateSupport" /> Updates of existing user data.
          </div>
          <span>Only imported.xls、xlsxFormat of documents。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;" @click="importTemplate">Download the template.</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">True is fixed</el-button>
        <el-button @click="upload.open = false">take by</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUser, getUser, delUser, addUser, updateUser, resetUserPwd, changeUserStatus, deptTreeSelect } from "@/api/system/user";
import { getToken } from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "User",
  dicts: ['sys_normal_disable', 'sys_user_sex'],
  components: { Treeselect },
  data() {
    return {
      // covered layer.
      loading: true,
      // Selected Groups
      ids: [],
      // Not single prohibition.
      single: true,
      // Not many prohibited.
      multiple: true,
      // Showing the search conditions
      showSearch: true,
      // Total number
      total: 0,
      // User Table Data
      userList: null,
      // The top title.
      title: "",
      // Department Tree Options
      deptOptions: undefined,
      // Showing the explosion.
      open: false,
      // Name of department
      deptName: undefined,
      // default code.
      initPassword: undefined,
      // The date range
      dateRange: [],
      // Position Options
      postOptions: [],
      // Role Options
      roleOptions: [],
      // Number of Forms
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // User imports parameters
      upload: {
        // Showing the explosion.（User Introduction）
        open: false,
        // The top title.（User Introduction）
        title: "",
        // Will it be banned?
        isUploading: false,
        // Updates of existing user data.
        updateSupport: 0,
        // Set up the request head.
        headers: { Authorization: "Bearer " + getToken() },
        // The address uploaded.
        url: process.env.VUE_APP_BASE_API + "/system/user/importData"
      },
      // Question of parameters.
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: undefined,
        phonenumber: undefined,
        status: undefined,
        deptId: undefined
      },
      // List of information
      columns: [
        { key: 0, label: `User Number`, visible: true },
        { key: 1, label: `User Name`, visible: true },
        { key: 2, label: `Users say`, visible: true },
        { key: 3, label: `Department`, visible: true },
        { key: 4, label: `The phone number.`, visible: true },
        { key: 5, label: `state of`, visible: true },
        { key: 6, label: `Creating time.`, visible: true }
      ],
      // Forms of Examination
      rules: {
        userName: [
          { required: true, message: "Username cannot be empty.", trigger: "blur" },
          { min: 2, max: 20, message: 'The user name length must be 2 and 20 Between', trigger: 'blur' }
        ],
        nickName: [
          { required: true, message: "Users say they cannot be empty.", trigger: "blur" }
        ],
        password: [
          { required: true, message: "The user password cannot be empty.", trigger: "blur" },
          { min: 5, max: 20, message: 'The user’s password length must be 5 and 20 Between', trigger: 'blur' },
          { pattern: /^[^<>"'|\\]+$/, message: "It cannot contain illegal characters.：< > \" ' \\\ |", trigger: "blur" }
        ],
        email: [
          {
            type: "email",
            message: "Please enter the correct mailbox address.",
            trigger: ["blur", "change"]
          }
        ],
        phonenumber: [
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
    // Selection of the tree by name.
    deptName(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getList();
    this.getDeptTree();
    this.getConfigKey("sys.user.initPassword").then(response => {
      this.initPassword = response.msg;
    });
  },
  methods: {
    /** Ask for user list */
    getList() {
      this.loading = true;
      listUser(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** Department of Investigation The Tree Structure */
    getDeptTree() {
      deptTreeSelect().then(response => {
        this.deptOptions = response.data;
      });
    },
    // Selection of nodes.
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // Notes of incident.
    handleNodeClick(data) {
      this.queryParams.deptId = data.id;
      this.handleQuery();
    },
    // Modification of User Status
    handleStatusChange(row) {
      let text = row.status === "0" ? "activated" : "stopped";
      this.$modal.confirm('Confirmed to"' + text + '""' + row.userName + '"the user?？').then(function() {
        return changeUserStatus(row.userId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "Successful");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    // Cancel the button.
    cancel() {
      this.open = false;
      this.reset();
    },
    // Repair of the form.
    reset() {
      this.form = {
        userId: undefined,
        deptId: undefined,
        userName: undefined,
        nickName: undefined,
        password: undefined,
        phonenumber: undefined,
        email: undefined,
        sex: undefined,
        status: "0",
        remark: undefined,
        postIds: [],
        roleIds: []
      };
      this.resetForm("form");
    },
    /** Search button operation. */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** Restore the button operation. */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.queryParams.deptId = undefined;
      this.$refs.tree.setCurrentKey(null);
      this.handleQuery();
    },
    // Multiple selection of data.
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.userId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // More action triggers.
    handleCommand(command, row) {
      switch (command) {
        case "handleResetPwd":
          this.handleResetPwd(row);
          break;
        case "handleAuthRole":
          this.handleAuthRole(row);
          break;
        default:
          break;
      }
    },
    /** Add a button operation. */
    handleAdd() {
      this.reset();
      getUser().then(response => {
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.open = true;
        this.title = "Add Users";
        this.form.password = this.initPassword;
      });
    },
    /** Change the button operation. */
    handleUpdate(row) {
      this.reset();
      const userId = row.userId || this.ids;
      getUser(userId).then(response => {
        this.form = response.data;
        this.postOptions = response.posts;
        this.roleOptions = response.roles;
        this.$set(this.form, "postIds", response.postIds);
        this.$set(this.form, "roleIds", response.roleIds);
        this.open = true;
        this.title = "Modifying Users";
        this.form.password = "";
      });
    },
    /** Restore the password button operation. */
    handleResetPwd(row) {
      this.$prompt('Please enter."' + row.userName + '"The new password.', "The Tip", {
        confirmButtonText: "Certainly",
        cancelButtonText: "cancelled",
        closeOnClickModal: false,
        inputPattern: /^.{5,20}$/,
        inputErrorMessage: "The user’s password length must be 5 and 20 Between",
        inputValidator: (value) => {
          if (/<|>|"|'|\||\\/.test(value)) {
            return "It cannot contain illegal characters.：< > \" ' \\\ |"
          }
        },
      }).then(({ value }) => {
          resetUserPwd(row.userId, value).then(response => {
            this.$modal.msgSuccess("Changes are Successful，The new password is：" + value);
          });
        }).catch(() => {});
    },
    /** Distribution of role operations */
    handleAuthRole: function(row) {
      const userId = row.userId;
      this.$router.push("/system/user-auth/role/" + userId);
    },
    /** Submit the button. */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.userId != undefined) {
            updateUser(this.form).then(response => {
              this.$modal.msgSuccess("Changes are Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addUser(this.form).then(response => {
              this.$modal.msgSuccess("Increased Success");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** Remove the button operation. */
    handleDelete(row) {
      const userIds = row.userId || this.ids;
      this.$modal.confirm('Confirming the user number to be deleted"' + userIds + '"of data.？').then(function() {
        return delUser(userIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Remove Success");
      }).catch(() => {});
    },
    /** Export the button operation. */
    handleExport() {
      this.download('system/user/export', {
        ...this.queryParams
      }, `user_${new Date().getTime()}.xlsx`)
    },
    /** Enter the button operation. */
    handleImport() {
      this.upload.title = "User Introduction";
      this.upload.open = true;
    },
    /** Download the template operation */
    importTemplate() {
      this.download('system/user/importTemplate', {
      }, `user_template_${new Date().getTime()}.xlsx`)
    },
    // Processing the file.
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // Successful file processing.
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "Import Results", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // Submit the file.
    submitFileForm() {
      this.$refs.upload.submit();
    }
  }
};
</script>