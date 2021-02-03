<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="Description" prop="description">
        <el-input
          v-model="queryParams.description"
          placeholder="Please enter description"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Status" prop="status">
        <el-select v-model="queryParams.status" placeholder="please choose Status" clearable size="small">
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
          v-hasPermi="['mes:work:add']"
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
          v-hasPermi="['mes:work:edit']"
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
          v-hasPermi="['mes:work:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['mes:work:export']"
        >Export</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Status" align="center" prop="workOrderId" />
      <el-table-column label="Description" align="center" prop="description" />
      <el-table-column label="Status" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="Work type" align="center" prop="type" :formatter="typeFormat" />
      <el-table-column label="Work order sequence" align="center" prop="sequence" />
      <el-table-column label="Cabinet qty" align="center" prop="cabinetQty" />
      <el-table-column label="Part qty" align="center" prop="partQty" />
      <el-table-column label="File name" align="center" prop="uploadFileName" />
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['mes:work:edit']"
          >Edit</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['mes:work:remove']"
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

    <!-- Add or modify the Work Order dialog -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="Description" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="Please enter content" />
        </el-form-item>
        <el-form-item label="Work type" prop="type">
          <el-select v-model="form.type" placeholder="Please choose Work type">
            <el-option
              v-for="dict in typeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Uploaded file">
          <fileUpload v-model="form.uploadFilePath"/>
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
import { getWorkByJob, listWork, getWork, delWork, addWork, updateWork, exportWork } from "@/api/mes/work";
import FileUpload from '@/components/FileUpload';

export default {
  name: "Work",
  components: {
    FileUpload,
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
      // Work Order Tabular data
      workList: [],
      // Pop-up layer title
      title: "",
      // Should show the pop-up layer
      open: false,
      // Default = 0, Ready = 1, On-Going = 2, Completed = 3 dictionary
      statusOptions: [],
      // Work type dictionary
      typeOptions: [],
      // Default Job Id
      defaultJobId: "",
      // Query parameter
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        description: null,
        status: null,
        jobId: null,
      },
      // Form parameter
      form: {},
      // Form validation
      rules: {
        description: [
          { required: true, message: "Description cannot be empty", trigger: "blur" }
        ],
        uploadFilePath: [
          { required: true, message: "Link to store the uploaded file	 cannot be empty", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    const jobId = this.$route.params && this.$route.params.jobId;
    this.queryParams.jobId = jobId;
    this.defaultJobId = jobId;
    this.getListByJobId(jobId);
    this.getDicts("work_order_status").then(response => {
      this.statusOptions = response.data;
    });
    this.getDicts("work_order_type").then(response => {
      this.typeOptions = response.data;
    });
  },
  methods: {
    /** Query by Job Id */
    getListByJobId(jobId) {
      this.loading = true;
      getWorkByJob(jobId).then(response => {
        this.workList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** Query the list of Work Order */
    getList() {
      this.loading = true;
      listWork(this.queryParams).then(response => {
        this.workList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // Default = 0, Ready = 1, On-Going = 2, Completed = 3 dictionary translation
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // Work type dictionary translation
    typeFormat(row, column) {
      return this.selectDictLabel(this.typeOptions, row.type);
    },
    // Cancel button
    cancel() {
      this.open = false;
      this.reset();
    },
    // Form reset
    reset() {
      this.form = {
        workOrderId: null,
        jobId: null,
        description: null,
        status: null,
        type: null,
        sequence: null,
        cabinetQty: null,
        partQty: null,
        uploadFileName: null,
        uploadFilePath: null,
        processFilePath: null,
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
      this.resetForm("queryForm");
      this.queryParams.jobId = this.defaultJobId;
      this.handleQuery();
    },
    // Checkbox to select data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.workOrderId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** New button operation */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add Work Order";
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const workOrderId = row.workOrderId || this.ids
      getWork(workOrderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify Work Order";
      });
    },
    /** Submit button */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.jobId = this.defaultJobId;
          if (this.form.workOrderId != null) {
            updateWork(this.form).then(response => {
              this.msgSuccess("Successfully modified");
              this.open = false;
              this.getList();
            });
          } else {
            addWork(this.form).then(response => {
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
      const workOrderIds = row.workOrderId || this.ids;
      this.$confirm('Please confirm to delete the Work Order "' + workOrderIds + '"?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return delWork(workOrderIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("Successfully deleted");
        })
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('Are you sure to export all Work Order data items?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return exportWork(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
