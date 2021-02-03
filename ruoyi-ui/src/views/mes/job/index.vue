<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="120px">
      <el-form-item label="Job code" prop="jobCode">
        <el-input
          v-model="queryParams.jobCode"
          placeholder="please enter Job code"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Customer name" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="please enter Customer name"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Ended At">
        <el-date-picker
          v-model="daterangeEndAt"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['mes:job:add']"
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
          v-hasPermi="['mes:job:edit']"
        >Edit</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['mes:job:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mes:job:export']"
        >Export</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jobList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" align="center" />
      <el-table-column label="ID" align="center" prop="jobId" />
      <el-table-column label="Job code" align="center" prop="jobCode" />
      <el-table-column label="PO" align="center" prop="purchaseOrder" />
      <el-table-column label="Customer name" align="center" prop="customerName" width="140" />
      <el-table-column label="Customer address" align="center" prop="customerAddress" width="140" />
      <el-table-column label="Customer phone" align="center" prop="customerPhone" width="130" />
      <el-table-column label="Job status" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="Job priority" align="center" prop="priority" :formatter="priorityFormat" />
      <el-table-column label="Start at" align="center" prop="startAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="End at" align="center" prop="endAt" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endAt, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      </el-table-column>
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
              size="mini"
              type="text"
              icon="el-icon-collection-tag"
              @click="viewWorkOrder(scope.row)"
              v-hasPermi="['mes:job:edit']"
            >Work Order</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:job:edit']"
          >Edit</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:job:remove']"
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

    <!-- Add or modify the Job Order dialog -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-form-item label="Job code" prop="jobCode">
          <el-input v-model="form.jobCode" placeholder="Please enter Job code" />
        </el-form-item>
        <el-form-item label="Purchase order" prop="purchaseOrder">
          <el-input v-model="form.purchaseOrder" placeholder="Please enter Purchase order" />
        </el-form-item>
        <el-form-item label="Job status" prop="status">
          <el-select v-model="form.status" placeholder="Please choose Job status">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Job priority" prop="priority">
          <el-select v-model="form.priority" placeholder="Please choose Job priority">
            <el-option
              v-for="dict in priorityOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Job started at" prop="startAt">
          <el-date-picker clearable size="small"
            v-model="form.startAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="Choose Job started at">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="Job end at" prop="endAt">
          <el-date-picker clearable size="small"
            v-model="form.endAt"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="Choose Job should end at">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="Customer name" prop="customerName">
          <el-input v-model="form.customerName" placeholder="Please enter Customer name" />
        </el-form-item>
        <el-form-item label="Customer address">
          <editor v-model="form.customerAddress" :min-height="192"/>
        </el-form-item>
        <el-form-item label="Customer phone" prop="customerPhone">
          <el-input v-model="form.customerPhone" placeholder="Please enter Customer phone" />
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
import { listJob, getJob, delJob, addJob, updateJob, exportJob } from "@/api/mes/job";
import Editor from '@/components/Editor';

export default {
  name: "Job",
  components: {
    Editor,
  },
  data() {
    return {
      // Mask layer
      loading: true,
      // Selected array
      ids: [],
      // Individually disabled
      single: true,
      // Multiple disabled
      multiple: true,
      // Show search criteria
      showSearch: true,
      // Total number
      total: 0,
      // Job Order Tabular data
      jobList: [],
      // Pop-up layer title
      title: "",
      // Should show the pop-up layer
      open: false,
      // Job status - (0 - default, 1 - active, 2 - inactive, 3 - achieved)   dictionary
      statusOptions: [],
      // Job priority (1 - urgent, 2 - high, 3 - normal, 4 - low, 5 - trivial) dictionary
      priorityOptions: [],
      // Job started at time range
      daterangeStartAt: [],
      // Job should end at time range
      daterangeEndAt: [],
      // Query parameter
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobCode: null,
        customerName: null,
        endAt: null,
      },
      // Form parameter
      form: {},
      // Form validation
      rules: {
        jobCode: [
          { required: true, message: "Job code cannot be empty", trigger: "blur" }
        ],
        customerName: [
          { required: true, message: "Customer name cannot be empty", trigger: "blur" }
        ],
        endAt: [
          { required: true, message: "Job should end at cannot be empty", trigger: "blur" }
        ],
        createBy: [
          { required: true, message: "Creator cannot be empty", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("job_order_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("priority_list").then(response => {
      this.priorityOptions = response.data;
    });
  },
  methods: {
    /** Query the list of Job Order */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeStartAt && '' != this.daterangeStartAt) {
        this.queryParams.params["beginStartAt"] = this.daterangeStartAt[0];
        this.queryParams.params["endStartAt"] = this.daterangeStartAt[1];
      }
      if (null != this.daterangeEndAt && '' != this.daterangeEndAt) {
        this.queryParams.params["beginEndAt"] = this.daterangeEndAt[0];
        this.queryParams.params["endEndAt"] = this.daterangeEndAt[1];
      }
      listJob(this.queryParams).then(response => {
        this.jobList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // Job status - (0 - default, 1 - active, 2 - inactive, 3 - achieved)   dictionary translation
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // Job priority (1 - urgent, 2 - high, 3 - normal, 4 - low, 5 - trivial) dictionary translation
    priorityFormat(row, column) {
      return this.selectDictLabel(this.priorityOptions, row.priority);
    },
    // Cancel button
    cancel() {
      this.open = false;
      this.reset();
    },
    // Form reset
    reset() {
      this.form = {
        jobId: null,
        jobCode: null,
        purchaseOrder: null,
        customerName: null,
        customerAddress: null,
        customerPhone: null,
        status: null,
        priority: null,
        startAt: null,
        endAt: null,
        createTime: null,
        createBy: null,
        updateTime: null,
        updateBy: null
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
      this.daterangeStartAt = [];
      this.daterangeEndAt = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // Checkbox to select data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.jobId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** New button operation */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add Job Order";
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const jobId = row.jobId || this.ids
      getJob(jobId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify Job Order";
      });
    },
    /** View Work Order */
    viewWorkOrder(row) {
      this.reset();
      const jobId = row.jobId || this.ids
      this.$router.push("/work/job/" + jobId)
    },
    /** Submit button */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.jobId != null) {
            updateJob(this.form).then(response => {
              this.msgSuccess("Successfully modified");
              this.open = false;
              this.getList();
            });
          } else {
            addJob(this.form).then(response => {
              this.msgSuccess("Added successfully");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** Delete button operation */
    handleDelete(row) {
      const jobIds = row.jobId || this.ids;
      this.$confirm('Please confirm to delete the Job Order "' + jobIds + '"?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return delJob(jobIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("Successfully deleted");
        })
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('Are you sure to export all Job Order data items?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return exportJob(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
