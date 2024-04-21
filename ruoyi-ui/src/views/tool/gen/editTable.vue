<template>
  <el-card>
    <el-tabs v-model="activeName">
      <el-tab-pane label="Basic information" name="basic">
        <basic-info-form ref="basicInfo" :info="info" />
      </el-tab-pane>
      <el-tab-pane label="Field Information" name="columnInfo">
        <el-table ref="dragTable" :data="columns" row-key="columnId" :max-height="tableHeight">
          <el-table-column label="The number" type="index" min-width="5%" class-name="allowDrag" />
          <el-table-column
            label="Name of fields"
            prop="columnName"
            min-width="10%"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="Field Description" min-width="10%">
            <template slot-scope="scope">
              <el-input v-model="scope.row.columnComment"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            label="Physical Types"
            prop="columnType"
            min-width="10%"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="JavaType of" min-width="11%">
            <template slot-scope="scope">
              <el-select v-model="scope.row.javaType">
                <el-option label="Long" value="Long" />
                <el-option label="String" value="String" />
                <el-option label="Integer" value="Integer" />
                <el-option label="Double" value="Double" />
                <el-option label="BigDecimal" value="BigDecimal" />
                <el-option label="Date" value="Date" />
                <el-option label="Boolean" value="Boolean" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="javaProperties" min-width="10%">
            <template slot-scope="scope">
              <el-input v-model="scope.row.javaField"></el-input>
            </template>
          </el-table-column>

          <el-table-column label="Inserted" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox true-label="1" false-label="0" v-model="scope.row.isInsert"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="The Editor" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox true-label="1" false-label="0" v-model="scope.row.isEdit"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="List of" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox true-label="1" false-label="0" v-model="scope.row.isList"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="The query" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox true-label="1" false-label="0" v-model="scope.row.isQuery"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="Method of query" min-width="10%">
            <template slot-scope="scope">
              <el-select v-model="scope.row.queryType">
                <el-option label="=" value="EQ" />
                <el-option label="!=" value="NE" />
                <el-option label=">" value="GT" />
                <el-option label=">=" value="GTE" />
                <el-option label="<" value="LT" />
                <el-option label="<=" value="LTE" />
                <el-option label="LIKE" value="LIKE" />
                <el-option label="BETWEEN" value="BETWEEN" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="must fill." min-width="5%">
            <template slot-scope="scope">
              <el-checkbox true-label="1" false-label="0" v-model="scope.row.isRequired"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="Showing Types" min-width="12%">
            <template slot-scope="scope">
              <el-select v-model="scope.row.htmlType">
                <el-option label="The text box" value="input" />
                <el-option label="Text area" value="textarea" />
                <el-option label="The lower box." value="select" />
                <el-option label="The Single Selection" value="radio" />
                <el-option label="The selection box." value="checkbox" />
                <el-option label="Date of control" value="datetime" />
                <el-option label="Images uploaded" value="imageUpload" />
                <el-option label="The document is uploaded" value="fileUpload" />
                <el-option label="The rich text control." value="editor" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="Type of dictionary" min-width="12%">
            <template slot-scope="scope">
              <el-select v-model="scope.row.dictType" clearable filterable placeholder="Please choose">
                <el-option
                  v-for="dict in dictOptions"
                  :key="dict.dictType"
                  :label="dict.dictName"
                  :value="dict.dictType">
                  <span style="float: left">{{ dict.dictName }}</span>
                  <span style="float: right; color: #8492a6; font-size: 13px">{{ dict.dictType }}</span>
              </el-option>
              </el-select>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="Generate information." name="genInfo">
        <gen-info-form ref="genInfo" :info="info" :tables="tables" :menus="menus"/>
      </el-tab-pane>
    </el-tabs>
    <el-form label-width="100px">
      <el-form-item style="text-align: center;margin-left:-100px;margin-top:10px;">
        <el-button type="primary" @click="submitForm()">submitted</el-button>
        <el-button @click="close()">Return to</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
import { getGenTable, updateGenTable } from "@/api/tool/gen";
import { optionselect as getDictOptionselect } from "@/api/system/dict/type";
import { listMenu as getMenuTreeselect } from "@/api/system/menu";
import basicInfoForm from "./basicInfoForm";
import genInfoForm from "./genInfoForm";
import Sortable from 'sortablejs'

export default {
  name: "GenEdit",
  components: {
    basicInfoForm,
    genInfoForm
  },
  data() {
    return {
      // Choose the option card. name
      activeName: "columnInfo",
      // The height of the table
      tableHeight: document.documentElement.scrollHeight - 245 + "px",
      // Table Information
      tables: [],
      // List of information
      columns: [],
      // Dictionary information
      dictOptions: [],
      // Menu Information
      menus: [],
      // Table detailed information
      info: {}
    };
  },
  created() {
    const tableId = this.$route.params && this.$route.params.tableId;
    if (tableId) {
      // Get details of the table.
      getGenTable(tableId).then(res => {
        this.columns = res.data.rows;
        this.info = res.data.info;
        this.tables = res.data.tables;
      });
      /** Download the dictionary list. */
      getDictOptionselect().then(response => {
        this.dictOptions = response.data;
      });
      /** Download the menu list. */
      getMenuTreeselect().then(response => {
        this.menus = this.handleTree(response.data, "menuId");
      });
    }
  },
  methods: {
    /** Submit the button. */
    submitForm() {
      const basicForm = this.$refs.basicInfo.$refs.basicInfoForm;
      const genForm = this.$refs.genInfo.$refs.genInfoForm;
      Promise.all([basicForm, genForm].map(this.getFormPromise)).then(res => {
        const validateResult = res.every(item => !!item);
        if (validateResult) {
          const genTable = Object.assign({}, basicForm.model, genForm.model);
          genTable.columns = this.columns;
          genTable.params = {
            treeCode: genTable.treeCode,
            treeName: genTable.treeName,
            treeParentCode: genTable.treeParentCode,
            parentMenuId: genTable.parentMenuId
          };
          updateGenTable(genTable).then(res => {
            this.$modal.msgSuccess(res.msg);
            if (res.code === 200) {
              this.close();
            }
          });
        } else {
          this.$modal.msgError("Forms not passed.，Please check the submission.");
        }
      });
    },
    getFormPromise(form) {
      return new Promise(resolve => {
        form.validate(res => {
          resolve(res);
        });
      });
    },
    /** Close the button. */
    close() {
      const obj = { path: "/tool/gen", query: { t: Date.now(), pageNum: this.$route.query.pageNum } };
      this.$tab.closeOpenPage(obj);
    }
  },
  mounted() {
    const el = this.$refs.dragTable.$el.querySelectorAll(".el-table__body-wrapper > table > tbody")[0];
    const sortable = Sortable.create(el, {
      handle: ".allowDrag",
      onEnd: evt => {
        const targetRow = this.columns.splice(evt.oldIndex, 1)[0];
        this.columns.splice(evt.newIndex, 0, targetRow);
        for (let index in this.columns) {
          this.columns[index].sort = parseInt(index) + 1;
        }
      }
    });
  }
};
</script>
