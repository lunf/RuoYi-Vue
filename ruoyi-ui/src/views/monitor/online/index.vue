<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
      <el-form-item label="Registration Address" prop="ipaddr">
        <el-input
          v-model="queryParams.ipaddr"
          placeholder="Please enter the login address."
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="User Name" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="Please enter the user name."
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Searching</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Repaired</el-button>
      </el-form-item>

    </el-form>
    <el-table
      v-loading="loading"
      :data="list.slice((pageNum-1)*pageSize,pageNum*pageSize)"
      style="width: 100%;"
    >
      <el-table-column label="The number" type="index" align="center">
        <template slot-scope="scope">
          <span>{{(pageNum - 1) * pageSize + scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column label="Number of meeting." align="center" prop="tokenId" :show-overflow-tooltip="true" />
      <el-table-column label="Registration Name" align="center" prop="userName" :show-overflow-tooltip="true" />
      <el-table-column label="Name of department" align="center" prop="deptName" />
      <el-table-column label="The host" align="center" prop="ipaddr" :show-overflow-tooltip="true" />
      <el-table-column label="place of registration." align="center" prop="loginLocation" :show-overflow-tooltip="true" />
      <el-table-column label="browsers" align="center" prop="browser" />
      <el-table-column label="Operating system" align="center" prop="os" />
      <el-table-column label="time of registration." align="center" prop="loginTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.loginTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operations" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleForceLogout(scope.row)"
            v-hasPermi="['monitor:online:forceLogout']"
          >withdrawal</el-button>
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
      // covered layer.
      loading: true,
      // Total number
      total: 0,
      // The Table Data
      list: [],
      pageNum: 1,
      pageSize: 10,
      // Question of parameters.
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
    /** Request the log list. */
    getList() {
      this.loading = true;
      list(this.queryParams).then(response => {
        this.list = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** Search button operation. */
    handleQuery() {
      this.pageNum = 1;
      this.getList();
    },
    /** Restore the button operation. */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** Returning button operation. */
    handleForceLogout(row) {
      this.$modal.confirm('confirmation of the resignation."' + row.userName + '"The User？').then(function() {
        return forceLogout(row.tokenId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Return to Success");
      }).catch(() => {});
    }
  }
};
</script>

