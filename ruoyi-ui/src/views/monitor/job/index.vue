<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Name of task" prop="jobName">
        <el-input
          v-model="queryParams.jobName"
          placeholder="Please enter the task name."
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="The task group name" prop="jobGroup">
        <el-select v-model="queryParams.jobGroup" placeholder="Please select the task group name." clearable>
          <el-option
            v-for="dict in dict.type.sys_job_group"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="Status of task." prop="status">
        <el-select v-model="queryParams.status" placeholder="Please select the task status." clearable>
          <el-option
            v-for="dict in dict.type.sys_job_status"
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
          v-hasPermi="['monitor:job:add']"
        >Added</el-button>
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
        >Modified</el-button>
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
        >removed</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['monitor:job:export']"
        >Exported</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-s-operation"
          size="mini"
          @click="handleJobLog"
          v-hasPermi="['monitor:job:query']"
        >The Diary</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jobList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Number of task." width="100" align="center" prop="jobId" />
      <el-table-column label="Name of task" align="center" prop="jobName" :show-overflow-tooltip="true" />
      <el-table-column label="The task group name" align="center" prop="jobGroup">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_job_group" :value="scope.row.jobGroup"/>
        </template>
      </el-table-column>
      <el-table-column label="Call the target string." align="center" prop="invokeTarget" :show-overflow-tooltip="true" />
      <el-table-column label="cronImplementation of expression" align="center" prop="cronExpression" :show-overflow-tooltip="true" />
      <el-table-column label="state of" align="center">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="Operations" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['monitor:job:edit']"
          >Modified</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['monitor:job:remove']"
          >removed</el-button>
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['monitor:job:changeStatus', 'monitor:job:query']">
            <el-button size="mini" type="text" icon="el-icon-d-arrow-right">more</el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleRun" icon="el-icon-caret-right"
                v-hasPermi="['monitor:job:changeStatus']">performed once.</el-dropdown-item>
              <el-dropdown-item command="handleView" icon="el-icon-view"
                v-hasPermi="['monitor:job:query']">The task detailed.</el-dropdown-item>
              <el-dropdown-item command="handleJobLog" icon="el-icon-s-operation"
                v-hasPermi="['monitor:job:query']">Modified Diary</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
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

    <!-- Add or modify the timely task dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Name of task" prop="jobName">
              <el-input v-model="form.jobName" placeholder="Please enter the task name." />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="The task group." prop="jobGroup">
              <el-select v-model="form.jobGroup" placeholder="Please select the task group.">
                <el-option
                  v-for="dict in dict.type.sys_job_group"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item prop="invokeTarget">
              <span slot="label">
                Method of calling
                <el-tooltip placement="top">
                  <div slot="content">
                    BeanCalling for example.：ryTask.ryParams('ry')
                    <br />ClassExamples of calling：com.ruoyi.quartz.task.RyTask.ryParams('ry')
                    <br />The parameter explains：Supporting the string.，Type of Bull，The whole type.，Floating Type，The whole type
                  </div>
                  <i class="el-icon-question"></i>
                </el-tooltip>
              </span>
              <el-input v-model="form.invokeTarget" placeholder="Please enter the call target string." />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="cronThe expression" prop="cronExpression">
              <el-input v-model="form.cronExpression" placeholder="Please enter.cronImplementation of expression">
                <template slot="append">
                  <el-button type="primary" @click="handleShowCron">
                    Create an expression.
                    <i class="el-icon-time el-icon--right"></i>
                  </el-button>
                </template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="form.jobId !== undefined">
            <el-form-item label="state of">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_job_status"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Implementing Strategy" prop="misfirePolicy">
              <el-radio-group v-model="form.misfirePolicy" size="small">
                <el-radio-button label="1">Immediately implemented</el-radio-button>
                <el-radio-button label="2">performed once.</el-radio-button>
                <el-radio-button label="3">abandon the execution.</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="is combined." prop="concurrent">
              <el-radio-group v-model="form.concurrent" size="small">
                <el-radio-button label="0">permitted</el-radio-button>
                <el-radio-button label="1">Prohibited</el-radio-button>
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

    <el-dialog title="CronExpression Generator" :visible.sync="openCron" append-to-body destroy-on-close class="scrollbar">
      <crontab @hide="openCron=false" @fill="crontabFill" :expression="expression"></crontab>
    </el-dialog>

    <!-- Detailed task logs -->
    <el-dialog title="The task detailed." :visible.sync="openView" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="120px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Number of task.：">{{ form.jobId }}</el-form-item>
            <el-form-item label="Name of task：">{{ form.jobName }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="The task group.：">{{ jobGroupFormat(form) }}</el-form-item>
            <el-form-item label="Creating time.：">{{ form.createTime }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="cronThe expression：">{{ form.cronExpression }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="The next time to perform.：">{{ parseTime(form.nextValidTime) }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Method of Calling Target：">{{ form.invokeTarget }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Status of task.：">
              <div v-if="form.status == 0">Normal</div>
              <div v-else-if="form.status == 1">suspended</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="is combined.：">
              <div v-if="form.concurrent == 0">permitted</div>
              <div v-else-if="form.concurrent == 1">Prohibited</div>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Implementing Strategy：">
              <div v-if="form.misfirePolicy == 0">default strategy.</div>
              <div v-else-if="form.misfirePolicy == 1">Immediately implemented</div>
              <div v-else-if="form.misfirePolicy == 2">performed once.</div>
              <div v-else-if="form.misfirePolicy == 3">abandon the execution.</div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="openView = false">Closed closed</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listJob, getJob, delJob, addJob, updateJob, runJob, changeJobStatus } from "@/api/monitor/job";
import Crontab from '@/components/Crontab'

export default {
  components: { Crontab },
  name: "Job",
  dicts: ['sys_job_group', 'sys_job_status'],
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
      // Showing the search conditions
      showSearch: true,
      // Total number
      total: 0,
      // Time Task Table Data
      jobList: [],
      // The top title.
      title: "",
      // Showing the explosion.
      open: false,
      // Showing detailed explosions.
      openView: false,
      // Should showCronExpression of explosion.
      openCron: false,
      // The entering expression.
      expression: "",
      // Question of parameters.
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: undefined,
        jobGroup: undefined,
        status: undefined
      },
      // Number of Forms
      form: {},
      // Forms of Examination
      rules: {
        jobName: [
          { required: true, message: "The name of the task cannot be empty.", trigger: "blur" }
        ],
        invokeTarget: [
          { required: true, message: "Calling the target string cannot be empty.", trigger: "blur" }
        ],
        cronExpression: [
          { required: true, message: "cronExpression cannot be empty.", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** Find a timely task list */
    getList() {
      this.loading = true;
      listJob(this.queryParams).then(response => {
        this.jobList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // Translation of the task group name
    jobGroupFormat(row, column) {
      return this.selectDictLabel(this.dict.type.sys_job_group, row.jobGroup);
    },
    // Cancel the button.
    cancel() {
      this.open = false;
      this.reset();
    },
    // Repair of the form.
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
      this.ids = selection.map(item => item.jobId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // More action triggers.
    handleCommand(command, row) {
      switch (command) {
        case "handleRun":
          this.handleRun(row);
          break;
        case "handleView":
          this.handleView(row);
          break;
        case "handleJobLog":
          this.handleJobLog(row);
          break;
        default:
          break;
      }
    },
    // Modification of task status
    handleStatusChange(row) {
      let text = row.status === "0" ? "activated" : "stopped";
      this.$modal.confirm('Confirmed to"' + text + '""' + row.jobName + '"The task?？').then(function() {
        return changeJobStatus(row.jobId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "Successful");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /* Immediately performed once. */
    handleRun(row) {
      this.$modal.confirm('Confirm to be executed immediately."' + row.jobName + '"The task?？').then(function() {
        return runJob(row.jobId, row.jobGroup);
      }).then(() => {
        this.$modal.msgSuccess("Successful execution.");
      }).catch(() => {});
    },
    /** Details of the task */
    handleView(row) {
      getJob(row.jobId).then(response => {
        this.form = response.data;
        this.openView = true;
      });
    },
    /** cronExpression button operation. */
    handleShowCron() {
      this.expression = this.form.cronExpression;
      this.openCron = true;
    },
    /** determining the return value. */
    crontabFill(value) {
      this.form.cronExpression = value;
    },
    /** Question of task log list */
    handleJobLog(row) {
      const jobId = row.jobId || 0;
      this.$router.push('/monitor/job-log/index/' + jobId)
    },
    /** Add a button operation. */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Adding tasks";
    },
    /** Change the button operation. */
    handleUpdate(row) {
      this.reset();
      const jobId = row.jobId || this.ids;
      getJob(jobId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Modifying tasks";
      });
    },
    /** Submit the button. */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.jobId != undefined) {
            updateJob(this.form).then(response => {
              this.$modal.msgSuccess("Changes are Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addJob(this.form).then(response => {
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
      const jobIds = row.jobId || this.ids;
      this.$modal.confirm('Confirm to remove the timely task number."' + jobIds + '"of data.？').then(function() {
        return delJob(jobIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Remove Success");
      }).catch(() => {});
    },
    /** Export the button operation. */
    handleExport() {
      this.download('monitor/job/export', {
        ...this.queryParams
      }, `job_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
