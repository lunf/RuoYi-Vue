<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Registration Address" prop="ipaddr">
        <el-input
          v-model="queryParams.ipaddr"
          placeholder="Please enter the login address."
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="User Name" prop="userName">
        <el-input
          v-model="queryParams.userName"
          placeholder="Please enter the user name."
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="state of" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="Registered state"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.sys_common_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="time of registration.">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd HH:mm:ss"
          type="daterange"
          range-separator="-"
          start-placeholder="Date of Beginning"
          end-placeholder="Date of end."
          :default-time="['00:00:00', '23:59:59']"
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
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['monitor:logininfor:remove']"
        >removed</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleClean"
          v-hasPermi="['monitor:logininfor:remove']"
        >the empty</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-unlock"
          size="mini"
          :disabled="single"
          @click="handleUnlock"
          v-hasPermi="['monitor:logininfor:unlock']"
        >Unlocked</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['monitor:logininfor:export']"
        >Exported</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="tables" v-loading="loading" :data="list" @selection-change="handleSelectionChange" :default-sort="defaultSort" @sort-change="handleSortChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Access to Number" align="center" prop="infoId" />
      <el-table-column label="User Name" align="center" prop="userName" :show-overflow-tooltip="true" sortable="custom" :sort-orders="['descending', 'ascending']" />
      <el-table-column label="Registration Address" align="center" prop="ipaddr" width="130" :show-overflow-tooltip="true" />
      <el-table-column label="place of registration." align="center" prop="loginLocation" :show-overflow-tooltip="true" />
      <el-table-column label="browsers" align="center" prop="browser" :show-overflow-tooltip="true" />
      <el-table-column label="Operating system" align="center" prop="os" />
      <el-table-column label="Registered state" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_common_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="Operating information" align="center" prop="msg" :show-overflow-tooltip="true" />
      <el-table-column label="Date of Enrollment" align="center" prop="loginTime" sortable="custom" :sort-orders="['descending', 'ascending']" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.loginTime) }}</span>
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
  </div>
</template>

<script>
import { list, delLogininfor, cleanLogininfor, unlockLogininfor } from "@/api/monitor/logininfor";

export default {
  name: "Logininfor",
  dicts: ['sys_common_status'],
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
      // Choose the user name
      selectName: "",
      // Showing the search conditions
      showSearch: true,
      // Total number
      total: 0,
      // The Table Data
      list: [],
      // The date range
      dateRange: [],
      // The default order.
      defaultSort: {prop: 'loginTime', order: 'descending'},
      // Question of parameters.
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ipaddr: undefined,
        userName: undefined,
        status: undefined
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
      list(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
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
      this.queryParams.pageNum = 1;
      this.$refs.tables.sort(this.defaultSort.prop, this.defaultSort.order)
    },
    /** Multiple selection of data. */
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.infoId)
      this.single = selection.length!=1
      this.multiple = !selection.length
      this.selectName = selection.map(item => item.userName);
    },
    /** Urge the incident. */
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    /** Remove the button operation. */
    handleDelete(row) {
      const infoIds = row.infoId || this.ids;
      this.$modal.confirm('Confirming the deletion of access numbers"' + infoIds + '"of data.？').then(function() {
        return delLogininfor(infoIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Remove Success");
      }).catch(() => {});
    },
    /** Clean the button operation. */
    handleClean() {
      this.$modal.confirm('Confirm that all log data items are empty.？').then(function() {
        return cleanLogininfor();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Clean Success");
      }).catch(() => {});
    },
    /** Open the button operation. */
    handleUnlock() {
      const username = this.selectName;
      this.$modal.confirm('Confirm to unlock the user."' + username + '"The data?').then(function() {
        return unlockLogininfor(username);
      }).then(() => {
        this.$modal.msgSuccess("Users" + username + "Unlock Success");
      }).catch(() => {});
    },
    /** Export the button operation. */
    handleExport() {
      this.download('monitor/logininfor/export', {
        ...this.queryParams
      }, `logininfor_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

