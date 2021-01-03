<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="Task Name" prop="jobName">
        <el-input
          v-model="queryParams.jobName"
          placeholder="Please enter the task name"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Group Name" prop="jobGroup">
        <el-select v-model="queryParams.jobGroup" placeholder="Please select a task group name" clearable size="small">
          <el-option
            v-for="dict in jobGroupOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <el-select v-model="queryParams.status" placeholder="Please select task status" clearable size="small">
          <el-option
            v-for="dict in statusOptions"
            :key="dict.dictValue"
            :label="dict.dictLabel"
            :value="dict.dictValue"
          />
        </el-select>
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
          v-hasPermi="['monitor:job:add']"
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
          v-hasPermi="['monitor:job:edit']"
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
          v-hasPermi="['monitor:job:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['monitor:job:export']"
        >Export</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-s-operation"
          size="mini"
          @click="handleJobLog"
          v-hasPermi="['monitor:job:query']"
        >Log</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jobList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="jobId" width="50" />
      <el-table-column label="Name" align="center" prop="jobName" :show-overflow-tooltip="true" />
      <el-table-column label="Group" align="center" prop="jobGroup" :formatter="jobGroupFormat" />
      <el-table-column label="Call Target" align="center" prop="invokeTarget" :show-overflow-tooltip="true" />
      <el-table-column label="Cron Expression" align="center" prop="cronExpression" :show-overflow-tooltip="true" />
      <el-table-column label="Status" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="Action" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-caret-right"
            @click="handleRun(scope.row)"
            v-hasPermi="['monitor:job:changeStatus']"
          >Execute once</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['monitor:job:query']"
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

    <!-- Add or modify the scheduled task dialog -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Name" prop="jobName">
              <el-input v-model="form.jobName" placeholder="Please enter the task name" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Group" prop="jobGroup">
              <el-select v-model="form.jobGroup" placeholder="Please Choose">
                <el-option
                  v-for="dict in jobGroupOptions"
                  :key="dict.dictValue"
                  :label="dict.dictLabel"
                  :value="dict.dictValue"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="invokeTarget">
              <span slot="label">
                Call Method
                <el-tooltip placement="top">
                  <div slot="content">
                    Bean call example：ryTask.ryParams('ry')
                    <br />Class call example：com.ruoyi.quartz.task.RyTask.ryParams('ry')
                    <br />Parameter description: support string, boolean, long, floating, integer
                  </div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
              </span>
              <el-input v-model="form.invokeTarget" placeholder="Please enter the calling target in String" />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Cron Expression" prop="cronExpression">
              <el-input v-model="form.cronExpression" placeholder="Please enter cron execution expression" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Allow Concurrent" prop="concurrent">
              <el-radio-group v-model="form.concurrent" size="small">
                <el-radio-button label="0">Allow</el-radio-button>
                <el-radio-button label="1">Prohibit</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Misfired Strategy" prop="misfirePolicy">
              <el-radio-group v-model="form.misfirePolicy" size="small">
                <el-radio-button label="1">Execute immediately</el-radio-button>
                <el-radio-button label="2">Execute once</el-radio-button>
                <el-radio-button label="3">Give up</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Status">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">Confirm</el-button>
        <el-button @click="cancel">Cancel</el-button>
      </div>
    </el-dialog>

    <!-- Task log details -->
    <el-dialog title="Task details" :visible.sync="openView" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="180px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Serial：">{{ form.jobId }}</el-form-item>
            <el-form-item label="Group：">{{ jobGroupFormat(form) }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Name：">{{ form.jobName }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Cron Expression：">{{ form.cronExpression }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Next Execution Time：">{{ parseTime(form.nextValidTime) }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Call target：">{{ form.invokeTarget }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Task Status：">
              <div v-if="form.status == 0">Normal</div>
              <div v-else-if="form.status == 1">Failure</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Allow concurrent：">
              <div v-if="form.concurrent == 0">Allow</div>
              <div v-else-if="form.concurrent == 1">Prohibit</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Execution strategy：">
              <div v-if="form.misfirePolicy == 0">Default</div>
              <div v-else-if="form.misfirePolicy == 1">Execute immediately</div>
              <div v-else-if="form.misfirePolicy == 2">Execute once</div>
              <div v-else-if="form.misfirePolicy == 3">Give up</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Created At：">{{ form.createTime }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openView = false">Close</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJob, getJob, delJob, addJob, updateJob, exportJob, runJob, changeJobStatus } from "@/api/monitor/job";

export default {
  name: "Job",
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
      // Scheduled task table data
      jobList: [],
      // Pop-up layer title
      title: "",
      // Should show the pop-up layer
      open: false,
      // Should show detailed pop-up layer
      openView: false,
      // Task group name dictionary
      jobGroupOptions: [],
      // Status dictionary
      statusOptions: [],
      // Query parameter
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: undefined,
        jobGroup: undefined,
        status: undefined
      },
      // Form parameter
      form: {},
      // Form validation
      rules: {
        jobName: [
          { required: true, message: "The task name cannot be empty", trigger: "blur" }
        ],
        invokeTarget: [
          { required: true, message: "The call target cannot be empty", trigger: "blur" }
        ],
        cronExpression: [
          { required: true, message: "Cron expression cannot be empty", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_job_group").then(response => {
      this.jobGroupOptions = response.data;
    });
    this.getDicts("sys_job_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** Query the list of scheduled tasks */
    getList() {
      this.loading = true;
      listJob(this.queryParams).then(response => {
        this.jobList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // Task group name dictionary translation
    jobGroupFormat(row, column) {
      return this.selectDictLabel(this.jobGroupOptions, row.jobGroup);
    },
    // Status dictionary translation
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
        jobId: undefined,
        jobName: undefined,
        jobGroup: undefined,
        invokeTarget: undefined,
        cronExpression: undefined,
        misfirePolicy: 1,
        concurrent: 1,
        status: "0"
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // Checkbox to select data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.jobId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // Task status modification
    handleStatusChange(row) {
      let text = row.status === "0" ? "Enable" : "Disable";
      this.$confirm('Confirm to "' + text + '" "' + row.jobName + '"?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return changeJobStatus(row.jobId, row.status);
        }).then(() => {
          this.msgSuccess(text + " successful");
        }).catch(function() {
          row.status = row.status === "0" ? "1" : "0";
        });
    },
    /* Execute once */
    handleRun(row) {
      this.$confirm('Confirm to execute immediately this "' + row.jobName + '"?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return runJob(row.jobId, row.jobGroup);
        }).then(() => {
          this.msgSuccess("Execution succeed");
        })
    },
    /** Task details */
    handleView(row) {
      getJob(row.jobId).then(response => {
        this.form = response.data;
        this.openView = true;
      });
    },
    /** Task log list query */
    handleJobLog() {
      this.$router.push("/job/log");
    },
    /** New button operation */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Create a task";
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const jobId = row.jobId || this.ids;
      getJob(jobId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Update a task";
      });
    },
    /** Submit button */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.jobId != undefined) {
            updateJob(this.form).then(response => {
              this.msgSuccess("Updated successful");
              this.open = false;
              this.getList();
            });
          } else {
            addJob(this.form).then(response => {
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
      const jobIds = row.jobId || this.ids;
      this.$confirm('Confirm to delete the scheduled task number "' + jobIds + '"?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return delJob(jobIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("Deleted successful");
        })
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm("Are you sure to export all timed task data items?", "Warning", {
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
