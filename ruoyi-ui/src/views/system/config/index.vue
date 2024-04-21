<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Name of Parameters" prop="configName">
        <el-input
          v-model="queryParams.configName"
          placeholder="Please enter the parameter name."
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="The parameter name." prop="configKey">
        <el-input
          v-model="queryParams.configKey"
          placeholder="Please enter the parameter name."
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="The system integrated." prop="configType">
        <el-select v-model="queryParams.configType" placeholder="The system integrated." clearable>
          <el-option
            v-for="dict in dict.type.sys_yes_no"
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
          v-hasPermi="['system:config:add']"
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
          v-hasPermi="['system:config:edit']"
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
          v-hasPermi="['system:config:remove']"
        >removed</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['system:config:export']"
        >Exported</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-refresh"
          size="mini"
          @click="handleRefreshCache"
          v-hasPermi="['system:config:remove']"
        >Upgrade the cache.</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="configList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="The parameter key." align="center" prop="configId" />
      <el-table-column label="Name of Parameters" align="center" prop="configName" :show-overflow-tooltip="true" />
      <el-table-column label="The parameter name." align="center" prop="configKey" :show-overflow-tooltip="true" />
      <el-table-column label="The parameter value." align="center" prop="configValue" :show-overflow-tooltip="true" />
      <el-table-column label="The system integrated." align="center" prop="configType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.configType"/>
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
            v-hasPermi="['system:config:edit']"
          >Modified</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:config:remove']"
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
        <el-form-item label="Name of Parameters" prop="configName">
          <el-input v-model="form.configName" placeholder="Please enter the parameter name." />
        </el-form-item>
        <el-form-item label="The parameter name." prop="configKey">
          <el-input v-model="form.configKey" placeholder="Please enter the parameter name." />
        </el-form-item>
        <el-form-item label="The parameter value." prop="configValue">
          <el-input v-model="form.configValue" placeholder="Please enter the parameter key." />
        </el-form-item>
        <el-form-item label="The system integrated." prop="configType">
          <el-radio-group v-model="form.configType">
            <el-radio
              v-for="dict in dict.type.sys_yes_no"
              :key="dict.value"
              :label="dict.value"
            >{{dict.label}}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Note to" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="Please enter the content." />
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
import { listConfig, getConfig, delConfig, addConfig, updateConfig, refreshCache } from "@/api/system/config";

export default {
  name: "Config",
  dicts: ['sys_yes_no'],
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
      // The Parameters Table Data
      configList: [],
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
        configName: undefined,
        configKey: undefined,
        configType: undefined
      },
      // Number of Forms
      form: {},
      // Forms of Examination
      rules: {
        configName: [
          { required: true, message: "The parameter name cannot be empty.", trigger: "blur" }
        ],
        configKey: [
          { required: true, message: "The parameter key name cannot be empty.", trigger: "blur" }
        ],
        configValue: [
          { required: true, message: "The parameter key value cannot be empty.", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** List of requests. */
    getList() {
      this.loading = true;
      listConfig(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.configList = response.rows;
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
        configId: undefined,
        configName: undefined,
        configKey: undefined,
        configValue: undefined,
        configType: "Y",
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
      this.title = "Adding Parameters";
    },
    // Multiple selection of data.
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.configId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** Change the button operation. */
    handleUpdate(row) {
      this.reset();
      const configId = row.configId || this.ids
      getConfig(configId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Modified parameters";
      });
    },
    /** Submit the button. */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.configId != undefined) {
            updateConfig(this.form).then(response => {
              this.$modal.msgSuccess("Changes are Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addConfig(this.form).then(response => {
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
      const configIds = row.configId || this.ids;
      this.$modal.confirm('Confirm the removal of the parameter number."' + configIds + '"of data.？').then(function() {
          return delConfig(configIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("Remove Success");
        }).catch(() => {});
    },
    /** Export the button operation. */
    handleExport() {
      this.download('system/config/export', {
        ...this.queryParams
      }, `config_${new Date().getTime()}.xlsx`)
    },
    /** Update the cache button operation. */
    handleRefreshCache() {
      refreshCache().then(() => {
        this.$modal.msgSuccess("Updated success.");
      });
    }
  }
};
</script>
