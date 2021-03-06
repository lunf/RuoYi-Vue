<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Name" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="please enter Name"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Barcode" prop="barcode">
        <el-input
          v-model="queryParams.barcode"
          placeholder="please enter Barcode"
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
          v-hasPermi="['med:material:add']"
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
          v-hasPermi="['med:material:edit']"
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
          v-hasPermi="['med:material:remove']"
        >Delete</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['med:material:export']"
        >Export</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="materialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="Primary key" align="center" prop="materialId" />
      <el-table-column label="Name" align="center" prop="name" />
      <el-table-column label="Description" align="center" prop="description" />
      <el-table-column label="Material supplier" align="center" prop="supplier" />
      <el-table-column label="Stock status" align="center" prop="status" :formatter="statusFormat" />
      <el-table-column label="Qty" align="center" prop="qty" />
      <el-table-column label="Unit" align="center" prop="unit" />
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['med:material:edit']"
          >Edit</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['med:material:remove']"
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

    <!-- Add or modify the Material dialog -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="Name" prop="name">
          <el-input v-model="form.name" placeholder="Please enter Name" />
        </el-form-item>
        <el-form-item label="Description" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="Please enter content" />
        </el-form-item>
        <el-form-item label="Barcode" prop="barcode">
          <el-input v-model="form.barcode" disabled="disabled" placeholder="Auto generated Barcode" />
        </el-form-item>
        <el-form-item label="Supplier" prop="supplier">
          <el-input v-model="form.supplier" type="textarea" placeholder="Please enter material supplier" />
        </el-form-item>
        <el-form-item label="Stock status" prop="status">
          <el-select v-model="form.status" placeholder="Please choose Stock status">
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="parseInt(dict.dictValue)"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="Qty" prop="qty">
          <el-input v-model="form.qty" placeholder="Please enter Qty" />
        </el-form-item>
        <el-form-item label="Unit" prop="unit">
          <el-input v-model="form.unit" placeholder="Please enter Unit" />
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
import { listMaterial, getMaterial, delMaterial, addMaterial, updateMaterial, exportMaterial } from "@/api/med/material";

export default {
  name: "Material",
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
      // Material Tabular data
      materialList: [],
      // Pop-up layer title
      title: "",
      // Should show the pop-up layer
      open: false,
      // Stock status dictionary
      statusOptions: [],
      // Query parameter
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        barcode: null,
      },
      // Form parameter
      form: {},
      // Form validation
      rules: {
        name: [
          { required: true, message: "Name cannot be empty", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("material_stock_status").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** Query the list of Material */
    getList() {
      this.loading = true;
      listMaterial(this.queryParams).then(response => {
        this.materialList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // Stock status dictionary translation
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
        materialId: null,
        name: null,
        description: null,
        barcode: null,
        supplier: null,
        status: null,
        qty: null,
        unit: null
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
      this.ids = selection.map(item => item.materialId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** New button operation */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add Material";
    },
    /** Modify button operation */
    handleUpdate(row) {
      this.reset();
      const materialId = row.materialId || this.ids
      getMaterial(materialId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify Material";
      });
    },
    /** Submit button */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.materialId != null) {
            updateMaterial(this.form).then(response => {
              this.msgSuccess("Successfully modified");
              this.open = false;
              this.getList();
            });
          } else {
            addMaterial(this.form).then(response => {
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
      const materialIds = row.materialId || this.ids;
      this.$confirm('Please confirm to delete the Material "' + materialIds + '"?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return delMaterial(materialIds);
        }).then(() => {
          this.getList();
          this.msgSuccess("Successfully deleted");
        })
    },
    /** Export button operation */
    handleExport() {
      const queryParams = this.queryParams;
      this.$confirm('Are you sure to export all Material data items?', "Warning", {
          confirmButtonText: "Confirm",
          cancelButtonText: "Cancel",
          type: "warning"
        }).then(function() {
          return exportMaterial(queryParams);
        }).then(response => {
          this.download(response.msg);
        })
    }
  }
};
</script>
