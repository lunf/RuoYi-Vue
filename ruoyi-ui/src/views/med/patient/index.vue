<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item label="Barcode" prop="barcode">
        <el-input
          v-model="queryParams.barcode"
          placeholder="please enter barcode"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Mobile" prop="mobile">
        <el-input
          v-model="queryParams.mobile"
          placeholder="please enter Mobile number"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="CMND" prop="nationalNumber">
        <el-input
          v-model="queryParams.nationalNumber"
          placeholder="please enter National security number"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['med:patient:add']"
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
          v-hasPermi="['med:patient:edit']"
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
          v-hasPermi="['med:patient:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['med:patient:export']"
        >Export</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="patientList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Primary key" align="center" prop="patientId" />
      <el-table-column label="Full name" align="center" prop="fullName" />
      <el-table-column label="Mobile number" align="center" prop="mobile" />
      <el-table-column label="Gender" align="center" prop="sex" :formatter="sexFormat" />
      <el-table-column label="Date of birth" align="center" prop="dateOfBirth" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dateOfBirth, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Home address" align="center" prop="homeAddress" />
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['med:patient:edit']"
          >Edit</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['med:patient:remove']"
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

    <!-- Add or modify the Patient dialog -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="Full name" prop="fullName">
          <el-input v-model="form.fullName" placeholder="Nguyen Van A" />
        </el-form-item>
        <el-form-item label="Barcode" prop="barcode">
          <el-input v-model="form.barcode" disabled="disabled" placeholder="Auto generated barcode" />
        </el-form-item>
        <el-form-item label="Mobile number" prop="mobile">
          <el-input v-model="form.mobile" placeholder="Please enter Mobile number" />
        </el-form-item>
        <el-form-item label="Gender" prop="sex">
          <el-select v-model="form.sex" placeholder="Please choose Gender">
            <el-option
              v-for="dict in sexOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Date of birth" prop="dateOfBirth">
          <el-date-picker clearable size="small"
            v-model="form.dateOfBirth"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="Choose Date of birth">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="CMND" prop="nationalNumber">
          <el-input v-model="form.nationalNumber" placeholder="Please enter National security number" />
        </el-form-item>
        <el-form-item label="Home address" prop="homeAddress">
          <el-input v-model="form.homeAddress" type="textarea" placeholder="Please enter content" />
        </el-form-item>
        <el-form-item label="Remark" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="Please enter content" />
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
import { listPatient, getPatient, delPatient, addPatient, updatePatient, exportPatient } from "@/api/med/patient";

export default {
  name: "Patient",
  components: {
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
      // Patient Tabular data
      patientList: [],
      // Pop-up layer title
      title: "",
      // Should show the pop-up layer
      open: false,
      // Gender dictionary
      sexOptions: [],
      // Date of birth time range
      daterangeDateOfBirth: [],
      // Query parameter
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        fullName: null,
        barcode: null,
        mobile: null,
        nationalNumber: null,
      },
      // Form parameter
      form: {},
      // Form validation
      rules: {
        fullName: [
          { required: true, message: "Full name cannot be empty", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
  },
  methods: {
    /** Query the list of Patient */
    getList() {
      this.loading = true;
      this.queryParams.params = {};
      if (null != this.daterangeDateOfBirth && '' != this.daterangeDateOfBirth) {
        this.queryParams.params["beginDateOfBirth"] = this.daterangeDateOfBirth[0];
        this.queryParams.params["endDateOfBirth"] = this.daterangeDateOfBirth[1];
      }
      listPatient(this.queryParams).then(response => {
        this.patientList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // Gender dictionary translation
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex);
    },
    // Cancel button
    cancel() {
      this.open = false;
      this.reset();
    },
    // Form reset
    reset() {
      this.form = {
        patientId: null,
        fullName: null,
        barcode: null,
        mobile: null,
        sex: null,
        dateOfBirth: null,
        nationalNumber: null,
        homeAddress: null,
        remark: null
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
      this.daterangeDateOfBirth = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // Checkbox to select data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.patientId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** New button operation */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add Patient";
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const patientId = row.patientId || this.ids
      getPatient(patientId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify Patient";
      });
    },
    /** Submit button */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.patientId != null) {
            updatePatient(this.form).then(response => {
              this.msgSuccess("Successfully modified");
              this.open = false;
              this.getList();
            });
          } else {
            addPatient(this.form).then(response => {
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
      const patientIds = row.patientId || this.ids;
      this.$confirm('Please confirm to delete the Patient "' + patientIds + '"?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return delPatient(patientIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("Successfully deleted");
        })
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('Are you sure to export all Patient data items?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return exportPatient(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
