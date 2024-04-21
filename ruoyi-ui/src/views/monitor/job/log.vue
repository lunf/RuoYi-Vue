<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Name of task" prop="jobName">
        <el-input
          v-model="queryParams.jobName"
          placeholder="Please enter the task name."
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="The task group name" prop="jobGroup">
        <el-select
          v-model="queryParams.jobGroup"
          placeholder="Please select the task group name."
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.sys_job_group"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="The Execution State" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="Please select the execution status."
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
      <el-form-item label="Execution time.">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="Date of Beginning"
          end-placeholder="Date of end."
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
          v-hasPermi="['monitor:job:remove']"
        >removed</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          @click="handleClean"
          v-hasPermi="['monitor:job:remove']"
        >the empty</el-button>
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
          type="warning"
          plain
          icon="el-icon-close"
          size="mini"
          @click="handleClose"
        >closed</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="jobLogList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Number of Diary" width="80" align="center" prop="jobLogId" />
      <el-table-column label="Name of task" align="center" prop="jobName" :show-overflow-tooltip="true" />
      <el-table-column label="The task group name" align="center" prop="jobGroup" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_job_group" :value="scope.row.jobGroup"/>
        </template>
      </el-table-column>
      <el-table-column label="Call the target string." align="center" prop="invokeTarget" :show-overflow-tooltip="true" />
      <el-table-column label="Diary information" align="center" prop="jobMessage" :show-overflow-tooltip="true" />
      <el-table-column label="The Execution State" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_common_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="Execution time." align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operations" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-hasPermi="['monitor:job:query']"
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

    <!-- The Diary Details. -->
    <el-dialog title="The Diary Details." :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px" size="mini">
        <el-row>
          <el-col :span="12">
            <el-form-item label="Number of Diary：">{{ form.jobLogId }}</el-form-item>
            <el-form-item label="Name of task：">{{ form.jobName }}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="The task group.：">{{ form.jobGroup }}</el-form-item>
            <el-form-item label="Execution time.：">{{ form.createTime }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Method of calling：">{{ form.invokeTarget }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Diary information：">{{ form.jobMessage }}</el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="The Execution State：">
              <div v-if="form.status == 0">Normal</div>
              <div v-else-if="form.status == 1">Failure</div>
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Unusual information：" v-if="form.status == 1">{{ form.exceptionInfo }}</el-form-item>
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
import { getJob} from "@/api/monitor/job";
import { listJobLog, delJobLog, cleanJobLog } from "@/api/monitor/jobLog";

export default {
  name: "JobLog",
  dicts: ['sys_common_status', 'sys_job_group'],
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
      // Modification of log form data
      jobLogList: [],
      // Showing the explosion.
      open: false,
      // The date range
      dateRange: [],
      // Number of Forms
      form: {},
      // Question of parameters.
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobName: undefined,
        jobGroup: undefined,
        status: undefined
      }
    };
  },
  created() {
    const jobId = this.$route.params && this.$route.params.jobId;
    if (jobId !== undefined && jobId != 0) {
      getJob(jobId).then(response => {
        this.queryParams.jobName = response.data.jobName;
        this.queryParams.jobGroup = response.data.jobGroup;
        this.getList();
      });
    } else {
      this.getList();
    }
  },
  methods: {
    /** Ask for a log list. */
    getList() {
      this.loading = true;
      listJobLog(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.jobLogList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // Return to the button.
    handleClose() {
      const obj = { path: "/monitor/job" };
      this.$tab.closeOpenPage(obj);
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
      this.handleQuery();
    },
    // Multiple selection of data.
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.jobLogId);
      this.multiple = !selection.length;
    },
    /** Detailed button operation. */
    handleView(row) {
      this.open = true;
      this.form = row;
    },
    /** Remove the button operation. */
    handleDelete(row) {
      const jobLogIds = this.ids;
      this.$modal.confirm('Confirming the removal of the log number as"' + jobLogIds + '"of data.？').then(function() {
        return delJobLog(jobLogIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Remove Success");
      }).catch(() => {});
    },
    /** Clean the button operation. */
    handleClean() {
      this.$modal.confirm('Confirming the empty of all data items in the log？').then(function() {
        return cleanJobLog();
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Clean Success");
      }).catch(() => {});
    },
    /** Export the button operation. */
    handleExport() {
      this.download('/monitor/jobLog/export', {
        ...this.queryParams
      }, `log_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
