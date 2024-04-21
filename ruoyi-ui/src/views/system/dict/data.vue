<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Name of Dictionary" prop="dictType">
        <el-select v-model="queryParams.dictType">
          <el-option
            v-for="item in typeOptions"
            :key="item.dictId"
            :label="item.dictName"
            :value="item.dictType"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="dictionary labels" prop="dictLabel">
        <el-input
          v-model="queryParams.dictLabel"
          placeholder="Please enter the dictionary label."
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="state of" prop="status">
        <el-select v-model="queryParams.status" placeholder="state of data" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
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
          type="warning"
          plain
          icon="el-icon-close"
          size="mini"
          @click="handleClose"
        >closed</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="The dictionary code" align="center" prop="dictCode" />
      <el-table-column label="dictionary labels" align="center" prop="dictLabel">
        <template slot-scope="scope">
          <span v-if="(scope.row.listClass == '' || scope.row.listClass == 'default') && (scope.row.cssClass == '' || scope.row.cssClass == null)">{{ scope.row.dictLabel }}</span>
          <el-tag v-else :type="scope.row.listClass == 'primary' ? '' : scope.row.listClass" :class="scope.row.cssClass">{{ scope.row.dictLabel }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="Keyword Value" align="center" prop="dictValue" />
      <el-table-column label="dictionary order." align="center" prop="dictSort" />
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
        <el-form-item label="Type of dictionary">
          <el-input v-model="form.dictType" :disabled="true" />
        </el-form-item>
        <el-form-item label="Data labels" prop="dictLabel">
          <el-input v-model="form.dictLabel" placeholder="Please enter the data label." />
        </el-form-item>
        <el-form-item label="Data Key Value" prop="dictValue">
          <el-input v-model="form.dictValue" placeholder="Please enter the data key value." />
        </el-form-item>
        <el-form-item label="Style characteristics" prop="cssClass">
          <el-input v-model="form.cssClass" placeholder="Please enter the style character." />
        </el-form-item>
        <el-form-item label="Showing order." prop="dictSort">
          <el-input-number v-model="form.dictSort" controls-position="right" :min="0" />
        </el-form-item>
        <el-form-item label="Returning style." prop="listClass">
          <el-select v-model="form.listClass">
            <el-option
              v-for="item in listClassOptions"
              :key="item.value"
              :label="item.label + '(' + item.value + ')'"
              :value="item.value"
            ></el-option>
          </el-select>
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
import { listData, getData, delData, addData, updateData } from "@/api/system/dict/data";
import { optionselect as getDictOptionselect, getType } from "@/api/system/dict/type";

export default {
  name: "Data",
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
      dataList: [],
      // Type of dictionary.
      defaultDictType: "",
      // The top title.
      title: "",
      // Showing the explosion.
      open: false,
      // Data tag returns.
      listClassOptions: [
        {
          value: "default",
          label: "presumed"
        },
        {
          value: "primary",
          label: "Mainly"
        },
        {
          value: "success",
          label: "Successful"
        },
        {
          value: "info",
          label: "Information"
        },
        {
          value: "warning",
          label: "warning"
        },
        {
          value: "danger",
          label: "Dangerous"
        }
      ],
      // Type of Data Dictionary
      typeOptions: [],
      // Question of parameters.
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dictType: undefined,
        dictLabel: undefined,
        status: undefined
      },
      // Number of Forms
      form: {},
      // Forms of Examination
      rules: {
        dictLabel: [
          { required: true, message: "Data labels cannot be empty.", trigger: "blur" }
        ],
        dictValue: [
          { required: true, message: "The data key is not empty.", trigger: "blur" }
        ],
        dictSort: [
          { required: true, message: "The data order cannot be empty.", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    const dictId = this.$route.params && this.$route.params.dictId;
    this.getType(dictId);
    this.getTypeList();
  },
  methods: {
    /** Find the dictionary type in detail */
    getType(dictId) {
      getType(dictId).then(response => {
        this.queryParams.dictType = response.data.dictType;
        this.defaultDictType = response.data.dictType;
        this.getList();
      });
    },
    /** List of Dictionary Types */
    getTypeList() {
      getDictOptionselect().then(response => {
        this.typeOptions = response.data;
      });
    },
    /** Ask for dictionary data list */
    getList() {
      this.loading = true;
      listData(this.queryParams).then(response => {
        this.dataList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // Cancel the button.
    cancel() {
      this.open = false;
      this.reset();
    },
    // Repair of the form.
    reset() {
      this.form = {
        dictCode: undefined,
        dictLabel: undefined,
        dictValue: undefined,
        cssClass: undefined,
        listClass: 'default',
        dictSort: 0,
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
    /** Back to the button operation. */
    handleClose() {
      const obj = { path: "/system/dict" };
      this.$tab.closeOpenPage(obj);
    },
    /** Restore the button operation. */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.dictType = this.defaultDictType;
      this.handleQuery();
    },
    /** Add a button operation. */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "Add dictionary data";
      this.form.dictType = this.queryParams.dictType;
    },
    // Multiple selection of data.
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.dictCode)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** Change the button operation. */
    handleUpdate(row) {
      this.reset();
      const dictCode = row.dictCode || this.ids
      getData(dictCode).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Modifying dictionary data";
      });
    },
    /** Submit the button. */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.dictCode != undefined) {
            updateData(this.form).then(response => {
              this.$store.dispatch('dict/removeDict', this.queryParams.dictType);
              this.$modal.msgSuccess("Changes are Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addData(this.form).then(response => {
              this.$store.dispatch('dict/removeDict', this.queryParams.dictType);
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
      const dictCodes = row.dictCode || this.ids;
      this.$modal.confirm('Confirming the removal of the dictionary code for"' + dictCodes + '"of data.？').then(function() {
        return delData(dictCodes);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Remove Success");
        this.$store.dispatch('dict/removeDict', this.queryParams.dictType);
      }).catch(() => {});
    },
    /** Export the button operation. */
    handleExport() {
      this.download('system/dict/data/export', {
        ...this.queryParams
      }, `data_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>