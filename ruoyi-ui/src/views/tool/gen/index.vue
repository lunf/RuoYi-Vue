<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Table name" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="Please enter the table name"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Table description" prop="tableComment">
        <el-input
          v-model="queryParams.tableComment"
          placeholder="Please enter a table description"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Created At">
        <el-date-picker
          v-model="dateRange"
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
          icon="el-icon-download"
          size="mini"
          @click="handleGenTable"
          v-hasPermi="['tool:gen:code']"
        >Generate</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="openImportTable"
          v-hasPermi="['tool:gen:import']"
        >Import</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleEditTable"
          v-hasPermi="['tool:gen:edit']"
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
          v-hasPermi="['tool:gen:remove']"
        >Delete</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tableList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center" width="55"></el-table-column>
      <el-table-column label="ID" type="index" width="50" align="center">
        <template slot-scope="scope">
          <span>{{(queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Table name"
        align="center"
        prop="tableName"
        :show-overflow-tooltip="true"
        width="120"
      />
      <el-table-column
        label="Table description"
        align="center"
        prop="tableComment"
        :show-overflow-tooltip="true"
        width="120"
      />
      <el-table-column
        label="Entity"
        align="center"
        prop="className"
        :show-overflow-tooltip="true"
        width="120"
      />
      <el-table-column label="Created At" align="center" prop="createTime" width="160" />
      <el-table-column label="Updated At" align="center" prop="updateTime" width="160" />
      <el-table-column label="Actions" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="small"
            icon="el-icon-view"
            @click="handlePreview(scope.row)"
            v-hasPermi="['tool:gen:preview']"
          >View</el-button>
          <el-button
            type="text"
            size="small"
            icon="el-icon-edit"
            @click="handleEditTable(scope.row)"
            v-hasPermi="['tool:gen:edit']"
          >Edit</el-button>
          <el-button
            type="text"
            size="small"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tool:gen:remove']"
          >Delete</el-button>
          <el-button
            type="text"
            size="small"
            icon="el-icon-refresh"
            @click="handleSynchDb(scope.row)"
            v-hasPermi="['tool:gen:edit']"
          >Synchronize</el-button>
          <el-button
            type="text"
            size="small"
            icon="el-icon-download"
            @click="handleGenTable(scope.row)"
            v-hasPermi="['tool:gen:code']"
          >Generate</el-button>
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
    <!-- Preview interface -->
    <el-dialog :title="preview.title" :visible.sync="preview.open" width="80%" top="5vh" append-to-body>
      <el-tabs v-model="preview.activeName">
        <el-tab-pane
          v-for="(value, key) in preview.data"
          :label="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
          :name="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
          :key="key"
        >
        <pre><code class="hljs" v-html="highlightedCode(value, key)"></code></pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
    <import-table ref="import" @ok="handleQuery" />
  </div>
</template>

<script>
import { listTable, previewTable, delTable, genCode, synchDb } from "@/api/tool/gen";
import importTable from "./importTable";
import { downLoadZip } from "@/utils/zipdownload";
import hljs from "highlight.js/lib/highlight";
import "highlight.js/styles/github-gist.css";
// hljs.registerLanguage("java", require("highlight.js/lib/languages/java"));
//hljs.registerLanguage("xml", require("highlight.js/lib/languages/xml"));
//hljs.registerLanguage("html", require("highlight.js/lib/languages/xml"));
//hljs.registerLanguage("vue", require("highlight.js/lib/languages/xml"));
//hljs.registerLanguage("javascript", require("highlight.js/lib/languages/javascript"));
//hljs.registerLanguage("sql", require("highlight.js/lib/languages/sql"));

export default {
  name: "Gen",
  components: { importTable },
  data() {
    return {
      // Mask layer
      loading: true,
      // Unique identifier
      uniqueId: "",
      // Selected array
      ids: [],
      // Selected table array
      tableNames: [],
      // Not individually disabled
      single: true,
      // Not multiple disabled
      multiple: true,
      // Show search criteria
      showSearch: true,
      // Total number
      total: 0,
      // Table data
      tableList: [],
      // Date range
      dateRange: "",
      // Query parameter
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: undefined,
        tableComment: undefined
      },
      // Preview parameters
      preview: {
        open: false,
        title: "Code preview",
        data: {},
        activeName: "domain.java"
      }
    };
  },
  created() {
    this.getList();
  },
  activated() {
    const time = this.$route.query.t;
    if (time != null && time != this.uniqueId) {
      this.uniqueId = time;
      this.resetQuery();
    }
  },
  methods: {
    /** Query table collection */
    getList() {
      this.loading = true;
      listTable(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.tableList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** Search button operation */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** Generate code operation */
    handleGenTable(row) {
      const tableNames = row.tableName || this.tableNames;
      if (tableNames == "") {
        this.msgError("Please select the data to be generated");
        return;
      }
      if(row.genType === "1") {
        genCode(row.tableName).then(response => {
          this.msgSuccess("Successfully generated to custom path：" + row.genPath);
        });
      } else {
        downLoadZip("/tool/gen/batchGenCode?tables=" + tableNames, "ruoyi");
      }
    },
    /** Synchronize database operations */
    handleSynchDb(row) {
      const tableName = row.tableName;
      this.$confirm('Confirm to force sync "' + tableName + '" schema？', "Warning", {
        confirmButtonText: "Confirm",
        cancelButtonText: "Cancel",
        type: "warning"
      }).then(function() {
          return synchDb(tableName);
      }).then(() => {
          this.msgSuccess("Sync successfully");
      })
    },
    /** Open the import table popup */
    openImportTable() {
      this.$refs.import.show();
    },
    /** Reset button operation */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** Preview button */
    handlePreview(row) {
      previewTable(row.tableId).then(response => {
        this.preview.data = response.data;
        this.preview.open = true;
      });
    },
    /** Highlight */
    highlightedCode(code, key) {
      const vmName = key.substring(key.lastIndexOf("/") + 1, key.indexOf(".vm"));
      var language = vmName.substring(vmName.indexOf(".") + 1, vmName.length);
      const result = hljs.highlight(language, code || "", true);
      return result.value || '&nbsp;';
    },
    // Checkbox to select data
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tableId);
      this.tableNames = selection.map(item => item.tableName);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** Modify button operation */
    handleEditTable(row) {
      const tableId = row.tableId || this.ids[0];
      this.$router.push("/gen/edit/" + tableId);
    },
    /** Delete button operation */
    handleDelete(row) {
      const tableIds = row.tableId || this.ids;
      this.$confirm('Confirm to delete table ID: "' + tableIds + '"?', "Warning", {
        confirmButtonText: "Confirm",
        cancelButtonText: "Cancel",
        type: "warning"
      }).then(function() {
          return delTable(tableIds);
      }).then(() => {
          this.getList();
          this.msgSuccess("Deleted successful");
      })
    }
  }
};
</script>
