<template>
  <!-- Introduction Table -->
  <el-dialog title="Introduction Table" :visible.sync="visible" width="800px" top="5vh" append-to-body>
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">Searching</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">Repaired</el-button>
      </el-form-item>
    </el-form>
    <el-row>
      <el-table @row-click="clickRow" ref="table" :data="dbTableList" @selection-change="handleSelectionChange" height="260px">
        <el-table-column type="selection" width="55"></el-table-column>
        <el-table-column prop="tableName" label="Name of Table" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="tableComment" label="Table Description" :show-overflow-tooltip="true"></el-table-column>
        <el-table-column prop="createTime" label="Creating time."></el-table-column>
        <el-table-column prop="updateTime" label="Updated time"></el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </el-row>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleImportTable">True is fixed</el-button>
      <el-button @click="visible = false">take by</el-button>
    </div>
  </el-dialog>
</template>

<script>
import { listDbTable, importTable } from "@/api/tool/gen";
export default {
  data() {
    return {
      // covered layer.
      visible: false,
      // Select the number of values.
      tables: [],
      // Total number
      total: 0,
      // Table data
      dbTableList: [],
      // Question of parameters.
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableName: undefined,
        tableComment: undefined
      }
    };
  },
  methods: {
    // Showing the Bullet.
    show() {
      this.getList();
      this.visible = true;
    },
    clickRow(row) {
      this.$refs.table.toggleRowSelection(row);
    },
    // Multiple selection of data.
    handleSelectionChange(selection) {
      this.tables = selection.map(item => item.tableName);
    },
    // Question table data
    getList() {
      listDbTable(this.queryParams).then(res => {
        if (res.code === 200) {
          this.dbTableList = res.rows;
          this.total = res.total;
        }
      });
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
    /** Enter the button operation. */
    handleImportTable() {
      const tableNames = this.tables.join(",");
      if (tableNames == "") {
        this.$modal.msgError("Please select the table to import.");
        return;
      }
      importTable({ tables: tableNames }).then(res => {
        this.$modal.msgSuccess(res.msg);
        if (res.code === 200) {
          this.visible = false;
          this.$emit("ok");
        }
      });
    }
  }
};
</script>
