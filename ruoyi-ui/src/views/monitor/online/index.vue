<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="120px">
      <el-form-item label="Login Address" prop="ipaddr">
        <el-input
          v-model="queryParams.ipaddr"
          placeholder="Please enter login address"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="User Name" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="Please enter user name"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
      </el-form-item>

    </el-form>
    <el-table
      v-loading="loading"
      :data="list.slice((pageNum-1)*pageSize,pageNum*pageSize)"
      style="width: 100%;"
    >
      <el-table-column label="Serial" type="index" align="center">
        <template slot-scope="scope">
          <span>{{(pageNum - 1) * pageSize + scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Token" align="center" prop="tokenId" :show-overflow-tooltip="true" />
      <el-table-column label="User Name" align="center" prop="userName" :show-overflow-tooltip="true" />
      <el-table-column label="Department" align="center" prop="deptName" />
      <el-table-column label="Host" align="center" prop="ipaddr" :show-overflow-tooltip="true" />
      <el-table-column label="Login Location" align="center" prop="loginLocation" :show-overflow-tooltip="true" />
      <el-table-column label="Browser" align="center" prop="browser" />
      <el-table-column label="Operating System" align="center" prop="os" />
      <el-table-column label="Log In Time" align="center" prop="loginTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.loginTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Action" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleForceLogout(scope.row)"
            v-hasPermi="['monitor:online:forceLogout']"
          >Logout</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="pageNum" :limit.sync="pageSize" />
  </div>
</template>

<script>
import { list, forceLogout } from "@/api/monitor/online";

export default {
  name: "Online",
  data() {
    return {
      // Mask layer
      loading: true,
      // Total number
      total: 0,
      // Tabular data
      list: [],
      pageNum: 1,
      pageSize: 10,
      // Query parameter
      queryParams: {
        ipaddr: undefined,
        userName: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** Query login log list */
    getList() {
      this.loading = true;
      list(this.queryParams).then(response => {
        this.list = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** Search button operation */
    handleQuery() {
      this.pageNum = 1;
      this.getList();
    },
    /** Reset button operation */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** Force logout button operation */
    handleForceLogout(row) {
      this.$confirm('Confirm to force logout user "' + row.userName + '"?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return forceLogout(row.tokenId);
        }).then(() => {
          this.getList();
          this.msgSuccess("Forced logout successfully");
        })
    }
  }
};
</script>
