<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="Order No" prop="dictName">
        <el-input
          v-model="queryParams.dictName"
          placeholder="Please enter the Order number"
          clearable
          size="small"
          style="width: 180px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Customer Name" prop="dictType" label-width="130px">
        <el-input
          v-model="queryParams.dictType"
          placeholder="Please enter the customer name"
          clearable
          size="small"
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="Order status"
          clearable
          size="small"
          style="width: 160px"
        >
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="Created At">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="start date"
          end-placeholder="end date"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:dict:add']"
        >Add</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:dict:edit']"
        >Modify</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:dict:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:dict:export']"
        >Export</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleClearCache"
          v-hasPermi="['system:dict:remove']"
        >Refresh</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Job No" align="center" prop="dictId" />
      <el-table-column label="Customer Name" align="center" prop="dictName" :show-overflow-tooltip="true" />
      <el-table-column label="PO" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <router-link :to="'/dict/type/data/' + scope.row.dictId" class="link-type">
            <span>{{ scope.row.dictType }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="Priority" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="Status" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="Remark" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column label="Started At" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Ended At" align="center" prop="createTime" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.createTime, '{d}-{m}-{y}') }}</span>
              </template>
            </el-table-column>
      <el-table-column label="Action" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:dict:edit']"
          >Edit</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:dict:remove']"
          >Delete</el-button>
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

    <!-- Add or modify parameter configuration dialog -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="Name" prop="dictName">
          <el-input v-model="form.dictName" placeholder="Please enter the dictionary name" />
        </el-form-item>
        <el-form-item label="Type" prop="dictType">
          <el-input v-model="form.dictType" placeholder="Please enter the dictionary type" />
        </el-form-item>
        <el-form-item label="Status" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictValue"
            >{{dict.dictLabel}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="Please enter remark"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">Confirm</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listType, getType, delType, addType, updateType, exportType, clearCache } from "@/api/mes/order";

export default {
  name: "Dict",
  data() {
    return {
      // Mask layer
      loading: true,
      // Selected array
      ids: [],
      // Not individually disabled
      single: true,
      // Not multiple disabled
      multiple: true,
      // Show search criteria
      showSearch: true,
      // Total number
      total: 0,
      // Dictionary table data
      typeList: [],
      // Pop-up layer title
      title: "",
      // Should show the pop-up layer
      open: false,
      // Status data dictionary
      statusOptions: [],
      // Date range
      dateRange: [],
      // Query parameter
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dictName: undefined,
        dictType: undefined,
        status: undefined
      },
      // Form parameter
      form: {},
      // Form validation
      rules: {
        dictName: [
          { required: true, message: "Dictionary name cannot be empty", trigger: "blur" }
        ],
        dictType: [
          { required: true, message: "Dictionary type cannot be empty", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** Query a list of dictionary types */
    getList() {
      this.loading = true;
      listType(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.typeList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // Dictionary status dictionary translation
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // Cancel button
    cancel() {
      this.open = false;
      this.reset();
    },
    // Form reset
    reset() {
      this.form = {
        dictId: undefined,
        dictName: undefined,
        dictType: undefined,
        status: "0",
        remark: undefined
      };
      this.resetForm("form");
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
    /** New button operation */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Create Dictionary Type";
    },
    // Checkbox to select data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.dictId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const dictId = row.dictId || this.ids
      getType(dictId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Update Dictionary Type";
      });
    },
    /** Submit button */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.dictId != undefined) {
            updateType(this.form).then(response => {
              this.msgSuccess("Updated successful");
              this.open = false;
              this.getList();
            });
          } else {
            addType(this.form).then(response => {
              this.msgSuccess("Created successful");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** Delete button operation */
    handleDelete(row) {
      const dictIds = row.dictId || this.ids;
      this.$confirm('Confirm to delete the dictionary number "' + dictIds + '"?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return delType(dictIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("Deleted successful");
        })
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('Are you sure to export all types of data items?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return exportType(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    },
    /** Clear cache button operation */
    handleClearCache() {
      clearCache().then(response => {
        this.msgSuccess("Cache cleared");
      });
    }
  }
};
</script>
