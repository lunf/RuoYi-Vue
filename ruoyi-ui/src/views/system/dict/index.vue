<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Name of Dictionary" prop="dictName">
        <el-input
          v-model="queryParams.dictName"
          placeholder="Please enter the dictionary name."
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Type of dictionary" prop="dictType">
        <el-input
          v-model="queryParams.dictType"
          placeholder="Please enter the dictionary type."
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="state of" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="The dictionary state."
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="Creating time.">
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:dict:add']"
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
          v-hasPermi="['system:dict:edit']"
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
          v-hasPermi="['system:dict:remove']"
        >removed</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:dict:export']"
        >Exported</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleRefreshCache"
          v-hasPermi="['system:dict:remove']"
        >Upgrade the cache.</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="typeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="The dictionary number" align="center" prop="dictId" />
      <el-table-column label="Name of Dictionary" align="center" prop="dictName" :show-overflow-tooltip="true" />
      <el-table-column label="Type of dictionary" align="center" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <router-link :to="'/system/dict-data/index/' + scope.row.dictId" class="link-type">
            <span>{{ scope.row.dictType }}</span>
          </router-link>
        </template>
      </el-table-column>
      <el-table-column label="state of" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="Note to" align="center" prop="remark" :show-overflow-tooltip="true" />
      <el-table-column label="Creating time." align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Operations" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:dict:edit']"
          >Modified</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:dict:remove']"
          >removed</el-button>
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

    <!-- Add or modify the parameter configuration dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="Name of Dictionary" prop="dictName">
          <el-input v-model="form.dictName" placeholder="Please enter the dictionary name." />
        </el-form-item>
        <el-form-item label="Type of dictionary" prop="dictType">
          <el-input v-model="form.dictType" placeholder="Please enter the dictionary type." />
        </el-form-item>
        <el-form-item label="state of" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Note to" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="Please enter the content."></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">True is fixed</el-button>
        <el-button @click="cancel">take by</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listType, getType, delType, addType, updateType, refreshCache } from "@/api/system/dict/type";

export default {
  name: "Dict",
  dicts: ['sys_normal_disable'],
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
      // dictionary data
      typeList: [],
      // The top title.
      title: "",
      // Showing the explosion.
      open: false,
      // The date range
      dateRange: [],
      // Question of parameters.
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dictName: undefined,
        dictType: undefined,
        status: undefined
      },
      // Number of Forms
      form: {},
      // Forms of Examination
      rules: {
        dictName: [
          { required: true, message: "The dictionary name cannot be empty.", trigger: "blur" }
        ],
        dictType: [
          { required: true, message: "Dictionary cannot be empty.", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** List of Dictionary Types */
    getList() {
      this.loading = true;
      listType(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.typeList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // Cancel the button.
    cancel() {
      this.open = false;
      this.reset();
    },
    // Repair of the form.
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
    /** Add a button operation. */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add a dictionary type.";
    },
    // Multiple selection of data.
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.dictId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** Change the button operation. */
    handleUpdate(row) {
      this.reset();
      const dictId = row.dictId || this.ids
      getType(dictId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Modify the dictionary type.";
      });
    },
    /** Submit the button. */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.dictId != undefined) {
            updateType(this.form).then(response => {
              this.$modal.msgSuccess("Changes are Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addType(this.form).then(response => {
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
      const dictIds = row.dictId || this.ids;
      this.$modal.confirm('Confirm to remove the dictionary number."' + dictIds + '"of data.？').then(function() {
        return delType(dictIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Remove Success");
      }).catch(() => {});
    },
    /** Export the button operation. */
    handleExport() {
      this.download('system/dict/type/export', {
        ...this.queryParams
      }, `type_${new Date().getTime()}.xlsx`)
    },
    /** Update the cache button operation. */
    handleRefreshCache() {
      refreshCache().then(() => {
        this.$modal.msgSuccess("Updated success.");
        this.$store.dispatch('dict/cleanDict');
      });
    }
  }
};
</script>