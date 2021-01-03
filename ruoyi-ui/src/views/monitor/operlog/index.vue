<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
      <el-form-item label="Module" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="Please enter the system module"
          clearable
          style="width: 240px;"
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Operator" prop="operName">
        <el-input
          v-model="queryParams.operName"
          placeholder="Please enter the operator"
          clearable
          style="width: 240px;"
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Operation Type" prop="businessType">
        <el-select
          v-model="queryParams.businessType"
          placeholder="Operation type"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in typeOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="Result" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="Operating Result"
          clearable
          size="small"
          style="width: 240px"
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="Operating Time">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="Start date"
          end-placeholder="End date"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Search</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Reset</el-button>
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
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleClean"
          v-hasPermi="['monitor:operlog:remove']"
        >Empty</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['monitor:operlog:export']"
        >Export</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="operId" />
      <el-table-column label="Module" align="center" prop="title" />
      <el-table-column label="Operation" align="center" prop="businessType" :formatter="typeFormat" />
      <el-table-column label="Request Method" align="center" prop="requestMethod" />
      <el-table-column label="Operator" align="center" prop="operName" />
      <el-table-column label="Host" align="center" prop="operIp" width="130" :show-overflow-tooltip="true" />
      <el-table-column label="Location" align="center" prop="operLocation" :show-overflow-tooltip="true" />
      <el-table-column label="Result" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="Operating Time" align="center" prop="operTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.operTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Action" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row,scope.index)"
            v-hasPermi="['monitor:operlog:query']"
          >View</el-button>
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

    <!-- Operation log details -->
    <el-dialog title="Operation log details" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="120px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Module：">{{ form.title }} / {{ typeFormat(form) }}</el-form-item>
            <el-form-item
              label="Login Info："
            >{{ form.operName }} / {{ form.operIp }} / {{ form.operLocation }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Address：">{{ form.operUrl }}</el-form-item>
            <el-form-item label="Method：">{{ form.requestMethod }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Class Method：">{{ form.method }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Request：">{{ form.operParam }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Response：">{{ form.jsonResult }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Result：">
              <div v-if="form.status === 0">Normal</div>
              <div v-else-if="form.status === 1">Failure</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Requested At：">{{ parseTime(form.operTime) }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Exception Info：" v-if="form.status === 1">{{ form.errorMsg }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="open = false">Close</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { list, delOperlog, cleanOperlog, exportOperlog } from "@/api/monitor/operlog";

export default {
  name: "Operlog",
  data() {
    return {
      // Mask layer
      loading: true,
      // Selected array
      ids: [],
      // Not multiple disabled
      multiple: true,
      // Show search criteria
      showSearch: true,
      // Total number
      total: 0,
      // Tabular data
      list: [],
      // Should show the pop-up layer
      open: false,
      // Type data dictionary
      typeOptions: [],
      // Status data dictionary
      statusOptions: [],
      // Date range
      dateRange: [],
      // Form parameter
      form: {},
      // Query parameter
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: undefined,
        operName: undefined,
        businessType: undefined,
        status: undefined
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_oper_type").then(response => {
      this.typeOptions = response.data;
    });
    this.getDicts("sys_common_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** Query login log */
    getList() {
      this.loading = true;
      list(this.addDateRange(this.queryParams, this.dateRange)).then( response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // Operation log status dictionary translation
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // Operation log type dictionary translation
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.businessType);
    },
    /** Search button operation */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** Reset button operation */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // Checkbox to select data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.operId)
      this.multiple = !selection.length
    },
    /** Detailed button operation */
    handleView(row) {
      this.open = true;
      this.form = row;
    },
    /** Delete button operation */
    handleDelete(row) {
      const operIds = row.operId || this.ids;
      this.$confirm('Confirm to delete operation log "' + operIds + '"?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return delOperlog(operIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("Deleted successful");
        })
    },
    /** Clear button operation */
    handleClean() {
        this.$confirm('Are you sure to clear all operation log data items?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return cleanOperlog();
        }).then(() => {
          this.getList();
          this.msgSuccess("Empty successfully");
        })
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('Are you sure to export all operation log data items?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return exportOperlog(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
