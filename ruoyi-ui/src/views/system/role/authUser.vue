<template>
  <div class="app-container">
     <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
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
          @click="openSelectUser"
          v-hasPermi="['system:role:add']"
        >Add Users</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-circle-close"
          size="mini"
          :disabled="multiple"
          @click="cancelAuthUserAll"
          v-hasPermi="['system:role:remove']"
        >Cancellation of authorisation</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-close"
          size="mini"
          @click="handleClose"
        >closed</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="userList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="User Name" prop="userName" :show-overflow-tooltip="true" />
      <el-table-column label="Users say" prop="nickName" :show-overflow-tooltip="true" />
      <el-table-column label="The mailbox" prop="email" :show-overflow-tooltip="true" />
      <el-table-column label="The Phone" prop="phonenumber" :show-overflow-tooltip="true" />
      <el-table-column label="state of" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="Creating time." align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operations" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-circle-close"
            @click="cancelAuthUser(scope.row)"
            v-hasPermi="['system:role:remove']"
          >cancellation of authorisation.</el-button>
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
    <select-user ref="select" :roleId="queryParams.roleId" @ok="handleQuery" />
  </div>
</template>

<script>
import { allocatedUserList, authUserCancel, authUserCancelAll } from "@/api/system/role";
import selectUser from "./selectUser";

export default {
  name: "AuthUser",
  dicts: ['sys_normal_disable'],
  components: { selectUser },
  data() {
    return {
      // covered layer.
      loading: true,
      // Select the user group.
      userIds: [],
      // Not many prohibited.
      multiple: true,
      // Showing the search conditions
      showSearch: true,
      // Total number
      total: 0,
      // User Table Data
      userList: [],
      // Question of parameters.
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleId: undefined,
        userName: undefined,
        phonenumber: undefined
      }
    };
  },
  created() {
    const roleId = this.$route.params && this.$route.params.roleId;
    if (roleId) {
      this.queryParams.roleId = roleId;
      this.getList();
    }
  },
  methods: {
    /** List of authorized users. */
    getList() {
      this.loading = true;
      allocatedUserList(this.queryParams).then(response => {
          this.userList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // Return to the button.
    handleClose() {
      const obj = { path: "/system/role" };
      this.$tab.closeOpenPage(obj);
    },
    /** Search button operation. */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** Restore the button operation. */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // Multiple selection of data.
    handleSelectionChange(selection) {
      this.userIds = selection.map(item => item.userId)
      this.multiple = !selection.length
    },
    /** Open the authorized user window. */
    openSelectUser() {
      this.$refs.select.show();
    },
    /** Cancel the authorized button operation. */
    cancelAuthUser(row) {
      const roleId = this.queryParams.roleId;
      this.$modal.confirm('Confirm to cancel the user."' + row.userName + '"The role?？').then(function() {
        return authUserCancel({ userId: row.userId, roleId: roleId });
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Cancel permission success");
      }).catch(() => {});
    },
    /** Cancellation of the authorized button operation */
    cancelAuthUserAll(row) {
      const roleId = this.queryParams.roleId;
      const userIds = this.userIds.join(",");
      this.$modal.confirm('Cancel selected user authorized data items？').then(function() {
        return authUserCancelAll({ roleId: roleId, userIds: userIds });
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Cancel permission success");
      }).catch(() => {});
    }
  }
};
</script>