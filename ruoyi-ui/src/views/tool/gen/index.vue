<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="Name of Table" prop="tableName">
        <el-input
          v-model="queryParams.tableName"
          placeholder="Please enter the name of the table."
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="Table Description" prop="tableComment">
        <el-input
          v-model="queryParams.tableComment"
          placeholder="Please enter the description."
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          icon="el-icon-download"
          size="mini"
          :disabled="multiple"
          @click="handleGenTable"
          v-hasPermi="['tool:gen:code']"
        >produced</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="openCreateTable"
          v-hasRole="['admin']"
        >Created</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="openImportTable"
          v-hasPermi="['tool:gen:import']"
        >Introduction</el-button>
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
          v-hasPermi="['tool:gen:remove']"
        >removed</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="tableList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" align="center" width="55"></el-table-column>
      <el-table-column label="The number" type="index" width="50" align="center">
        <template slot-scope="scope">
          <span>{{(queryParams.pageNum - 1) * queryParams.pageSize + scope.$index + 1}}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Name of Table"
        align="center"
        prop="tableName"
        :show-overflow-tooltip="true"
        width="120"
      />
      <el-table-column
        label="Table Description"
        align="center"
        prop="tableComment"
        :show-overflow-tooltip="true"
        width="120"
      />
      <el-table-column
        label="the entity"
        align="center"
        prop="className"
        :show-overflow-tooltip="true"
        width="120"
      />
      <el-table-column label="Creating time." align="center" prop="createTime" width="160" />
      <el-table-column label="Updated time" align="center" prop="updateTime" width="160" />
      <el-table-column label="Operations" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            type="text"
            size="small"
            icon="el-icon-view"
            @click="handlePreview(scope.row)"
            v-hasPermi="['tool:gen:preview']"
          >Preview</el-button>
          <el-button
            type="text"
            size="small"
            icon="el-icon-edit"
            @click="handleEditTable(scope.row)"
            v-hasPermi="['tool:gen:edit']"
          >The Editor</el-button>
          <el-button
            type="text"
            size="small"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['tool:gen:remove']"
          >removed</el-button>
          <el-button
            type="text"
            size="small"
            icon="el-icon-refresh"
            @click="handleSynchDb(scope.row)"
            v-hasPermi="['tool:gen:edit']"
          >synchronized</el-button>
          <el-button
            type="text"
            size="small"
            icon="el-icon-download"
            @click="handleGenTable(scope.row)"
            v-hasPermi="['tool:gen:code']"
          >Create the code.</el-button>
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
    <el-dialog :title="preview.title" :visible.sync="preview.open" width="80%" top="5vh" append-to-body class="scrollbar">
      <el-tabs v-model="preview.activeName">
        <el-tab-pane
          v-for="(value, key) in preview.data"
          :label="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
          :name="key.substring(key.lastIndexOf('/')+1,key.indexOf('.vm'))"
          :key="key"
        >
          <el-link :underline="false" icon="el-icon-document-copy" v-clipboard:copy="value" v-clipboard:success="clipboardSuccess" style="float:right">Reproduction</el-link>
          <pre><code class="hljs" v-html="highlightedCode(value, key)"></code></pre>
        </el-tab-pane>
      </el-tabs>
    </el-dialog>
    <import-table ref="import" @ok="handleQuery" />
    <create-table ref="create" @ok="handleQuery" />
  </div>
</template>

<script>
import { listTable, previewTable, delTable, genCode, synchDb } from "@/api/tool/gen";
import importTable from "./importTable";
import createTable from "./createTable";
import hljs from "highlight.js/lib/highlight";
import "highlight.js/styles/github-gist.css";
hljs.registerLanguage("java", require("highlight.js/lib/languages/java"));
hljs.registerLanguage("xml", require("highlight.js/lib/languages/xml"));
hljs.registerLanguage("html", require("highlight.js/lib/languages/xml"));
hljs.registerLanguage("vue", require("highlight.js/lib/languages/xml"));
hljs.registerLanguage("javascript", require("highlight.js/lib/languages/javascript"));
hljs.registerLanguage("sql", require("highlight.js/lib/languages/sql"));

export default {
  name: "Gen",
  components: { importTable, createTable },
  data() {
    return {
      // covered layer.
      loading: true,
      // The only identifier.
      uniqueId: "",
      // Selected Groups
      ids: [],
      // Select the number of tables.
      tableNames: [],
      // Not single prohibition.
      single: true,
      // Not many prohibited.
      multiple: true,
      // Showing the search conditions
      showSearch: true,
      // Total number
      total: 0,
      // Table data
      tableList: [],
      // The date range
      dateRange: "",
      // Question of parameters.
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: undefined,
        tableComment: undefined
      },
      // Preview of Parameters
      preview: {
        open: false,
        title: "Code Preview",
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
      this.queryParams.pageNum = Number(this.$route.query.pageNum);
      this.getList();
    }
  },
  methods: {
    /** Question table collection. */
    getList() {
      this.loading = true;
      listTable(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.tableList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** Search button operation. */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** Create code operations. */
    handleGenTable(row) {
      const tableNames = row.tableName || this.tableNames;
      if (tableNames == "") {
        this.$modal.msgError("Please select the data to be generated.");
        return;
      }
      if(row.genType === "1") {
        genCode(row.tableName).then(response => {
          this.$modal.msgSuccess("Successfully creating a custom route.：" + row.genPath);
        });
      } else {
        this.$download.zip("/tool/gen/batchGenCode?tables=" + tableNames, "ruoyi.zip");
      }
    },
    /** Synchronize database operations */
    handleSynchDb(row) {
      const tableName = row.tableName;
      this.$modal.confirm('Confirm for compulsory synchronization."' + tableName + '"The table structure?？').then(function() {
        return synchDb(tableName);
      }).then(() => {
        this.$modal.msgSuccess("Successful synchronization");
      }).catch(() => {});
    },
    /** Open the table window. */
    openImportTable() {
      this.$refs.import.show();
    },
    /** Open the table window. */
    openCreateTable() {
      this.$refs.create.show();
    },
    /** Restore the button operation. */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** Preview the button. */
    handlePreview(row) {
      previewTable(row.tableId).then(response => {
        this.preview.data = response.data;
        this.preview.open = true;
        this.preview.activeName = "domain.java";
      });
    },
    /** high display. */
    highlightedCode(code, key) {
      const vmName = key.substring(key.lastIndexOf("/") + 1, key.indexOf(".vm"));
      var language = vmName.substring(vmName.indexOf(".") + 1, vmName.length);
      const result = hljs.highlight(language, code || "", true);
      return result.value || '&nbsp;';
    },
    /** Successful copying code. */
    clipboardSuccess() {
      this.$modal.msgSuccess("Successful copying");
    },
    // Multiple selection of data.
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tableId);
      this.tableNames = selection.map(item => item.tableName);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** Change the button operation. */
    handleEditTable(row) {
      const tableId = row.tableId || this.ids[0];
      const tableName = row.tableName || this.tableNames[0];
      const params = { pageNum: this.queryParams.pageNum };
      this.$tab.openPage("Modified[" + tableName + "]Create configuration.", '/tool/gen-edit/index/' + tableId, params);
    },
    /** Remove the button operation. */
    handleDelete(row) {
      const tableIds = row.tableId || this.ids;
      this.$modal.confirm('Confirm the number of tables removed."' + tableIds + '"of data.？').then(function() {
        return delTable(tableIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Remove Success");
      }).catch(() => {});
    }
  }
};
</script>
