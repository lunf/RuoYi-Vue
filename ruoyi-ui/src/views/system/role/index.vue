<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="The role name." prop="roleName">
        <el-input
          v-model="queryParams.roleName"
          placeholder="Please enter the role name."
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Authorization of characters" prop="roleKey">
        <el-input
          v-model="queryParams.roleKey"
          placeholder="Please enter the permission character."
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="state of" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="Status of role."
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
          v-hasPermi="['system:role:add']"
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
          v-hasPermi="['system:role:edit']"
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
          v-hasPermi="['system:role:remove']"
        >removed</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:role:export']"
        >Exported</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="roleList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Number of Character" prop="roleId" width="120" />
      <el-table-column label="The role name." prop="roleName" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="Authorization of characters" prop="roleKey" :show-overflow-tooltip="true" width="150" />
      <el-table-column label="Show the order." prop="roleSort" width="100" />
      <el-table-column label="state of" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="Creating time." align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operations" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" v-if="scope.row.roleId !== 1">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:role:edit']"
          >Modified</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:role:remove']"
          >removed</el-button>
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:role:edit']">
            <el-button size="mini" type="text" icon="el-icon-d-arrow-right">more</el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleDataScope" icon="el-icon-circle-check"
                v-hasPermi="['system:role:edit']">Data authority</el-dropdown-item>
              <el-dropdown-item command="handleAuthUser" icon="el-icon-user"
                v-hasPermi="['system:role:edit']">Distribution of Users</el-dropdown-item>
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

    <!-- Add or modify the role configuration dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="The role name." prop="roleName">
          <el-input v-model="form.roleName" placeholder="Please enter the role name." />
        </el-form-item>
        <el-form-item prop="roleKey">
          <span slot="label">
            <el-tooltip content="Authority characters defined in the controller，as：@PreAuthorize(`@ss.hasRole('admin')`)" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
            Authorization of characters
          </span>
          <el-input v-model="form.roleKey" placeholder="Please enter the permission character." />
        </el-form-item>
        <el-form-item label="The role order." prop="roleSort">
          <el-input-number v-model="form.roleSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="state of">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="permission of menu">
          <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand($event, 'menu')">opened/Completed</el-checkbox>
          <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll($event, 'menu')">The full choice/Not all selected</el-checkbox>
          <el-checkbox v-model="form.menuCheckStrictly" @change="handleCheckedTreeConnect($event, 'menu')">Father Connected</el-checkbox>
          <el-tree
            class="tree-border"
            :data="menuOptions"
            show-checkbox
            ref="menu"
            node-key="id"
            :check-strictly="!form.menuCheckStrictly"
            empty-text="in charge.，Please wait a little."
            :props="defaultProps"
          ></el-tree>
        </el-form-item>
        <el-form-item label="Note to">
          <el-input v-model="form.remark" type="textarea" placeholder="Please enter the content."></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">True is fixed</el-button>
        <el-button @click="cancel">take by</el-button>
      </div>
    </el-dialog>

    <!-- Distributing role data permissions dialog box -->
    <el-dialog :title="title" :visible.sync="openDataScope" width="500px" append-to-body>
      <el-form :model="form" label-width="80px">
        <el-form-item label="The role name.">
          <el-input v-model="form.roleName" :disabled="true" />
        </el-form-item>
        <el-form-item label="Authorization of characters">
          <el-input v-model="form.roleKey" :disabled="true" />
        </el-form-item>
        <el-form-item label="scope of authority">
          <el-select v-model="form.dataScope" @change="dataScopeSelectChange">
            <el-option
              v-for="item in dataScopeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Data authority" v-show="form.dataScope == 2">
          <el-checkbox v-model="deptExpand" @change="handleCheckedTreeExpand($event, 'dept')">opened/Completed</el-checkbox>
          <el-checkbox v-model="deptNodeAll" @change="handleCheckedTreeNodeAll($event, 'dept')">The full choice/Not all selected</el-checkbox>
          <el-checkbox v-model="form.deptCheckStrictly" @change="handleCheckedTreeConnect($event, 'dept')">Father Connected</el-checkbox>
          <el-tree
            class="tree-border"
            :data="deptOptions"
            show-checkbox
            default-expand-all
            ref="dept"
            node-key="id"
            :check-strictly="!form.deptCheckStrictly"
            empty-text="in charge.，Please wait a little."
            :props="defaultProps"
          ></el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDataScope">True is fixed</el-button>
        <el-button @click="cancelDataScope">take by</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listRole, getRole, delRole, addRole, updateRole, dataScope, changeRoleStatus, deptTreeSelect } from "@/api/system/role";
import { treeselect as menuTreeselect, roleMenuTreeselect } from "@/api/system/menu";

export default {
  name: "Role",
  dicts: ['sys_normal_disable'],
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
      // Character Table Data
      roleList: [],
      // The top title.
      title: "",
      // Showing the explosion.
      open: false,
      // Showing the explosion.（Data authority）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      // The date range
      dateRange: [],
      // Data range options
      dataScopeOptions: [
        {
          value: "1",
          label: "All data permits"
        },
        {
          value: "2",
          label: "Adjusted data authority"
        },
        {
          value: "3",
          label: "Data authority of this department"
        },
        {
          value: "4",
          label: "This department and the following data authorities"
        },
        {
          value: "5",
          label: "Only personal data authority."
        }
      ],
      // The menu list
      menuOptions: [],
      // List of departments
      deptOptions: [],
      // Question of parameters.
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleName: undefined,
        roleKey: undefined,
        status: undefined
      },
      // Number of Forms
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // Forms of Examination
      rules: {
        roleName: [
          { required: true, message: "The name cannot be empty.", trigger: "blur" }
        ],
        roleKey: [
          { required: true, message: "The permission characters cannot be empty.", trigger: "blur" }
        ],
        roleSort: [
          { required: true, message: "The role order cannot be empty.", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** List of Characters */
    getList() {
      this.loading = true;
      listRole(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.roleList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** Ask for the tree structure. */
    getMenuTreeselect() {
      menuTreeselect().then(response => {
        this.menuOptions = response.data;
      });
    },
    // All menu nod data
    getMenuAllCheckedKeys() {
      // Currently selected menu nodes
      let checkedKeys = this.$refs.menu.getCheckedKeys();
      // Half-selected menu nodes
      let halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    // All department nod data
    getDeptAllCheckedKeys() {
      // Currently selected department nodes
      let checkedKeys = this.$refs.dept.getCheckedKeys();
      // Half-elected department nodes
      let halfCheckedKeys = this.$refs.dept.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    /** According to the roleIDAsk for the tree structure. */
    getRoleMenuTreeselect(roleId) {
      return roleMenuTreeselect(roleId).then(response => {
        this.menuOptions = response.menus;
        return response;
      });
    },
    /** According to the roleIDDepartment of the tree structure. */
    getDeptTree(roleId) {
      return deptTreeSelect(roleId).then(response => {
        this.deptOptions = response.depts;
        return response;
      });
    },
    // Modification of role status
    handleStatusChange(row) {
      let text = row.status === "0" ? "activated" : "stopped";
      this.$modal.confirm('Confirmed to"' + text + '""' + row.roleName + '"The role?？').then(function() {
        return changeRoleStatus(row.roleId, row.status);
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
    // Cancel the button.（Data authority）
    cancelDataScope() {
      this.openDataScope = false;
      this.reset();
    },
    // Repair of the form.
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      this.menuExpand = false,
      this.menuNodeAll = false,
      this.deptExpand = true,
      this.deptNodeAll = false,
      this.form = {
        roleId: undefined,
        roleName: undefined,
        roleKey: undefined,
        roleSort: 0,
        status: "0",
        menuIds: [],
        deptIds: [],
        menuCheckStrictly: true,
        deptCheckStrictly: true,
        remark: undefined
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
      this.handleQuery();
    },
    // Multiple selection of data.
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.roleId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    // More action triggers.
    handleCommand(command, row) {
      switch (command) {
        case "handleDataScope":
          this.handleDataScope(row);
          break;
        case "handleAuthUser":
          this.handleAuthUser(row);
          break;
        default:
          break;
      }
    },
    // Authority of the Tree（opened/Completed）
    handleCheckedTreeExpand(value, type) {
      if (type == 'menu') {
        let treeList = this.menuOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.menu.store.nodesMap[treeList[i].id].expanded = value;
        }
      } else if (type == 'dept') {
        let treeList = this.deptOptions;
        for (let i = 0; i < treeList.length; i++) {
          this.$refs.dept.store.nodesMap[treeList[i].id].expanded = value;
        }
      }
    },
    // Authority of the Tree（The full choice/Not all selected）
    handleCheckedTreeNodeAll(value, type) {
      if (type == 'menu') {
        this.$refs.menu.setCheckedNodes(value ? this.menuOptions: []);
      } else if (type == 'dept') {
        this.$refs.dept.setCheckedNodes(value ? this.deptOptions: []);
      }
    },
    // Authority of the Tree（Father Connected）
    handleCheckedTreeConnect(value, type) {
      if (type == 'menu') {
        this.form.menuCheckStrictly = value ? true: false;
      } else if (type == 'dept') {
        this.form.deptCheckStrictly = value ? true: false;
      }
    },
    /** Add a button operation. */
    handleAdd() {
      this.reset();
      this.getMenuTreeselect();
      this.open = true;
      this.title = "Add a role.";
    },
    /** Change the button operation. */
    handleUpdate(row) {
      this.reset();
      const roleId = row.roleId || this.ids
      const roleMenu = this.getRoleMenuTreeselect(roleId);
      getRole(roleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.$nextTick(() => {
          roleMenu.then(res => {
            let checkedKeys = res.checkedKeys
            checkedKeys.forEach((v) => {
                this.$nextTick(()=>{
                    this.$refs.menu.setChecked(v, true ,false);
                })
            })
          });
        });
        this.title = "Change the role.";
      });
    },
    /** Choose the role authority range */
    dataScopeSelectChange(value) {
      if(value !== '2') {
        this.$refs.dept.setCheckedKeys([]);
      }
    },
    /** Distribution of Data Authority Operations */
    handleDataScope(row) {
      this.reset();
      const deptTreeSelect = this.getDeptTree(row.roleId);
      getRole(row.roleId).then(response => {
        this.form = response.data;
        this.openDataScope = true;
        this.$nextTick(() => {
          deptTreeSelect.then(res => {
            this.$refs.dept.setCheckedKeys(res.checkedKeys);
          });
        });
        this.title = "Allocation of data authority";
      });
    },
    /** Distribution of User Operations */
    handleAuthUser: function(row) {
      const roleId = row.roleId;
      this.$router.push("/system/role-auth/user/" + roleId);
    },
    /** Submit the button. */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.roleId != undefined) {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            updateRole(this.form).then(response => {
              this.$modal.msgSuccess("Changes are Successful");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            addRole(this.form).then(response => {
              this.$modal.msgSuccess("Increased Success");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** Submit the button.（Data authority） */
    submitDataScope: function() {
      if (this.form.roleId != undefined) {
        this.form.deptIds = this.getDeptAllCheckedKeys();
        dataScope(this.form).then(response => {
          this.$modal.msgSuccess("Changes are Successful");
          this.openDataScope = false;
          this.getList();
        });
      }
    },
    /** Remove the button operation. */
    handleDelete(row) {
      const roleIds = row.roleId || this.ids;
      this.$modal.confirm('Confirm to remove the role number."' + roleIds + '"of data.？').then(function() {
        return delRole(roleIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Remove Success");
      }).catch(() => {});
    },
    /** Export the button operation. */
    handleExport() {
      this.download('system/role/export', {
        ...this.queryParams
      }, `role_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>