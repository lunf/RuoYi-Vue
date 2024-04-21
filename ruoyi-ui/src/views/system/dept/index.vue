<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="Name of department" prop="deptName">
        <el-input
          v-model="queryParams.deptName"
          placeholder="Please enter the department name."
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="state of" prop="status">
        <el-select v-model="queryParams.status" placeholder="state of department." clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
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
          v-hasPermi="['system:dept:add']"
        >Added</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >opened/Completed</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="deptList"
      row-key="deptId"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="deptName" label="Name of department" width="260"></el-table-column>
      <el-table-column prop="orderNum" label="ordered" width="200"></el-table-column>
      <el-table-column prop="status" label="state of" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="Creating time." align="center" prop="createTime" width="200">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operations" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:dept:edit']"
          >Modified</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:dept:add']"
          >Added</el-button>
          <el-button
            v-if="scope.row.parentId != 0"
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:dept:remove']"
          >removed</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Add or modify the section dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="24" v-if="form.parentId !== 0">
            <el-form-item label="The upper department" prop="parentId">
              <treeselect v-model="form.parentId" :options="deptOptions" :normalizer="normalizer" placeholder="Choose the top department." />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Name of department" prop="deptName">
              <el-input v-model="form.deptName" placeholder="Please enter the department name." />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Showing order." prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="Responsible" prop="leader">
              <el-input v-model="form.leader" placeholder="Please enter the manager." maxlength="20" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Contact the phone." prop="phone">
              <el-input v-model="form.phone" placeholder="Please enter the contact phone." maxlength="11" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="The mailbox" prop="email">
              <el-input v-model="form.email" placeholder="Please enter the mailbox." maxlength="50" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="state of department.">
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
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">True is fixed</el-button>
        <el-button @click="cancel">take by</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDept, getDept, delDept, addDept, updateDept, listDeptExcludeChild } from "@/api/system/dept";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Dept",
  dicts: ['sys_normal_disable'],
  components: { Treeselect },
  data() {
    return {
      // covered layer.
      loading: true,
      // Showing the search conditions
      showSearch: true,
      // Table of Tree Data
      deptList: [],
      // Department Tree Options
      deptOptions: [],
      // The top title.
      title: "",
      // Showing the explosion.
      open: false,
      // Is it launched，Supposedly all started.
      isExpandAll: true,
      // Repeat the table status.
      refreshTable: true,
      // Question of parameters.
      queryParams: {
        deptName: undefined,
        status: undefined
      },
      // Number of Forms
      form: {},
      // Forms of Examination
      rules: {
        parentId: [
          { required: true, message: "The top department cannot be empty.", trigger: "blur" }
        ],
        deptName: [
          { required: true, message: "The office name cannot be empty.", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "Showing order cannot be empty.", trigger: "blur" }
        ],
        email: [
          {
            type: "email",
            message: "Please enter the correct mailbox address.",
            trigger: ["blur", "change"]
          }
        ],
        phone: [
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "Please enter the correct phone number.",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** List of query departments */
    getList() {
      this.loading = true;
      listDept(this.queryParams).then(response => {
        this.deptList = this.handleTree(response.data, "deptId");
        this.loading = false;
      });
    },
    /** Conversion Department Data Structure */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.deptId,
        label: node.deptName,
        children: node.children
      };
    },
    // Cancel the button.
    cancel() {
      this.open = false;
      this.reset();
    },
    // Repair of the form.
    reset() {
      this.form = {
        deptId: undefined,
        parentId: undefined,
        deptName: undefined,
        orderNum: undefined,
        leader: undefined,
        phone: undefined,
        email: undefined,
        status: "0"
      };
      this.resetForm("form");
    },
    /** Search button operation. */
    handleQuery() {
      this.getList();
    },
    /** Restore the button operation. */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** Add a button operation. */
    handleAdd(row) {
      this.reset();
      if (row != undefined) {
        this.form.parentId = row.deptId;
      }
      this.open = true;
      this.title = "Added department";
      listDept().then(response => {
        this.deptOptions = this.handleTree(response.data, "deptId");
      });
    },
    /** opened/Completed Operations */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** Change the button operation. */
    handleUpdate(row) {
      this.reset();
      getDept(row.deptId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "The Department of Modification";
        listDeptExcludeChild(row.deptId).then(response => {
          this.deptOptions = this.handleTree(response.data, "deptId");
          if (this.deptOptions.length == 0) {
            const noResultsOptions = { deptId: this.form.parentId, deptName: this.form.parentName, children: [] };
            this.deptOptions.push(noResultsOptions);
          }
        });
      });
    },
    /** Submit the button. */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.deptId != undefined) {
            updateDept(this.form).then(response => {
              this.$modal.msgSuccess("Changes are Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addDept(this.form).then(response => {
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
      this.$modal.confirm('confirming the removal of the name"' + row.deptName + '"of data.？').then(function() {
        return delDept(row.deptId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Remove Success");
      }).catch(() => {});
    }
  }
};
</script>
