<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Operating Address" prop="operIp">
        <el-input
          v-model="queryParams.operIp"
          placeholder="Please enter the operating address."
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="The system module" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="Please enter the system module."
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Operating staff" prop="operName">
        <el-input
          v-model="queryParams.operName"
          placeholder="Please enter the operator."
          clearable
          style="width: 240px;"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Type of" prop="businessType">
        <el-select
          v-model="queryParams.businessType"
          placeholder="Types of operation"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.sys_oper_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="state of" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="Operating state"
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
      <el-form-item label="Operating time.">
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
          v-hasPermi="['monitor:operlog:remove']"
        >removed</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleClean"
          v-hasPermi="['monitor:operlog:remove']"
        >the empty</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['monitor:operlog:export']"
        >Exported</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table ref="tables" v-loading="loading" :data="list" @selection-change="handleSelectionChange" :default-sort="defaultSort" @sort-change="handleSortChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="Number of Diary" align="center" prop="operId" />
      <el-table-column label="The system module" align="center" prop="title" :show-overflow-tooltip="true" />
      <el-table-column label="Types of operation" align="center" prop="businessType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_oper_type" :value="scope.row.businessType"/>
        </template>
      </el-table-column>
      <el-table-column label="Operating staff" align="center" prop="operName" width="110" :show-overflow-tooltip="true" sortable="custom" :sort-orders="['descending', 'ascending']" />
      <el-table-column label="Operating Address" align="center" prop="operIp" width="130" :show-overflow-tooltip="true" />
      <el-table-column label="Location of operation." align="center" prop="operLocation" :show-overflow-tooltip="true" />
      <el-table-column label="Operating state" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_common_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="Date of operation" align="center" prop="operTime" width="160" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.operTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="spending time." align="center" prop="costTime" width="110" :show-overflow-tooltip="true" sortable="custom" :sort-orders="['descending', 'ascending']">
        <template slot-scope="scope">
          <span>{{ scope.row.costTime }}Mills of seconds.</span>
        </template>
      </el-table-column>
      <el-table-column label="Operations" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row,scope.index)"
            v-hasPermi="['monitor:operlog:query']"
          >Detailed</el-button>
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

    <!-- Operating log details. -->
    <el-dialog title="Operating log details." :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Operating modules：">{{ form.title }} / {{ typeFormat(form) }}</el-form-item>
            <el-form-item
              label="Registration Information："
            >{{ form.operName }} / {{ form.operIp }} / {{ form.operLocation }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="requested address：">{{ form.operUrl }}</el-form-item>
            <el-form-item label="Method of request：">{{ form.requestMethod }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Method of operation：">{{ form.method }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Demand of Parameters：">{{ form.operParam }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Return to Parameters：">{{ form.jsonResult }}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Operating state：">
              <div v-if="form.status === 0">Normal</div>
              <div v-else-if="form.status === 1">Failure</div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="spending time.：">{{ form.costTime }}Mills of seconds.</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="Operating time.：">{{ parseTime(form.operTime) }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Unusual information：" v-if="form.status === 1">{{ form.errorMsg }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">Closed closed</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { list, delOperlog, cleanOperlog } from "@/api/monitor/operlog";

export default {
  name: "Operlog",
  dicts: ['sys_oper_type', 'sys_common_status'],
  data() {
    return {
      // covered layer.
      loading: true,
      // Selected Groups
      ids: [],
      // Not many prohibited.
      multiple: true,
      // Showing the search conditions
      showSearch: true,
      // Total number
      total: 0,
      // The Table Data
      list: [],
      // Showing the explosion.
      open: false,
      // The date range
      dateRange: [],
      // The default order.
      defaultSort: {prop: 'operTime', order: 'descending'},
      // Number of Forms
      form: {},
      // Question of parameters.
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        operIp: undefined,
        title: undefined,
        operName: undefined,
        businessType: undefined,
        status: undefined
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** Ask for logging. */
    getList() {
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then( response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // Operating Log Type Dictionary Translation
    typeFormat(row, column) {
      return this.selectDictLabel(this.dict.type.sys_oper_type, row.businessType);
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
      this.ids = selection.map(item => item.operId)
      this.multiple = !selection.length
    },
    /** Urge the incident. */
    handleSortChange(column, prop, order) {
      this.queryParams.orderByColumn = column.prop;
      this.queryParams.isAsc = column.order;
      this.getList();
    },
    /** Detailed button operation. */
    handleView(row) {
      this.open = true;
      this.form = row;
    },
    /** Remove the button operation. */
    handleDelete(row) {
      const operIds = row.operId || this.ids;
      this.$modal.confirm('Confirm the log number to be deleted."' + operIds + '"of data.？').then(function() {
        return delOperlog(operIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Remove Success");
      }).catch(() => {});
    },
    /** Clean the button operation. */
    handleClean() {
      this.$modal.confirm('Confirm that all operating log data items are empty.？').then(function() {
        return cleanOperlog();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Clean Success");
      }).catch(() => {});
    },
    /** Export the button operation. */
    handleExport() {
      this.download('monitor/operlog/export', {
        ...this.queryParams
      }, `operlog_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

