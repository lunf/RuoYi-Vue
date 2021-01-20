<template>
  <el-card>
    <el-tabs v-model="activeName">
      <el-tab-pane label="Basic Information" name="basic">
        <basic-info-form ref="basicInfo" :info="info" />
      </el-tab-pane>
      <el-tab-pane label="Field information" name="cloum">
        <el-table ref="dragTable" :data="cloumns" row-key="columnId" :max-height="tableHeight">
          <el-table-column label="Serial number" type="index" min-width="5%" class-name="allowDrag" />
          <el-table-column
            label="Column name"
            prop="columnName"
            min-width="10%"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="Field description" min-width="10%">
            <template slot-scope="scope">
              <el-input v-model="scope.row.columnComment"></el-input>
            </template>
          </el-table-column>
          <el-table-column
            label="Physical type"
            prop="columnType"
            min-width="10%"
            :show-overflow-tooltip="true"
          />
          <el-table-column label="Java type" min-width="11%">
            <template slot-scope="scope">
              <el-select v-model="scope.row.javaType">
                <el-option label="Long" value="Long" />
                <el-option label="String" value="String" />
                <el-option label="Integer" value="Integer" />
                <el-option label="Double" value="Double" />
                <el-option label="BigDecimal" value="BigDecimal" />
                <el-option label="Date" value="Date" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="java attributes" min-width="10%">
            <template slot-scope="scope">
              <el-input v-model="scope.row.javaField"></el-input>
            </template>
          </el-table-column>

          <el-table-column label="Insert" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox true-label="1" v-model="scope.row.isInsert"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="Edit" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox true-label="1" v-model="scope.row.isEdit"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="List" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox true-label="1" v-model="scope.row.isList"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="Query" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox true-label="1" v-model="scope.row.isQuery"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="Query mode" min-width="10%">
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
          <el-table-column label="Required" min-width="5%">
            <template slot-scope="scope">
              <el-checkbox true-label="1" v-model="scope.row.isRequired"></el-checkbox>
            </template>
          </el-table-column>
          <el-table-column label="Display type" min-width="12%">
            <template slot-scope="scope">
              <el-select v-model="scope.row.htmlType">
                <el-option label="Text box" value="input" />
                <el-option label="Text field" value="textarea" />
                <el-option label="Drop down box" value="select" />
                <el-option label="Single box" value="radio" />
                <el-option label="Checkbox" value="checkbox" />
                <el-option label="Date control" value="datetime" />
                <el-option label="Upload image" value="imageUpload" />
                <el-option label="File Upload" value="fileUpload" />
                <el-option label="Rich text control" value="editor" />
              </el-select>
            </template>
          </el-table-column>
          <el-table-column label="Dictionary type" min-width="12%">
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
      <el-tab-pane label="Generate information" name="genInfo">
        <gen-info-form ref="genInfo" :info="info" :tables="tables" :menus="menus"/>
      </el-tab-pane>
    </el-tabs>
    <el-form label-width="100px">
      <el-form-item style="text-align: center;margin-left:-100px;margin-top:10px;">
        <el-button type="primary" @click="submitForm()">Confirm</el-button>
        <el-button @click="close()">Cancel</el-button>
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
      // The name of the selected tab
      activeName: "cloum",
      // Table height
      tableHeight: document.documentElement.scrollHeight - 245 + "px",
      // Table information
      tables: [],
      // Table information
      cloumns: [],
      // Dictionary information
      dictOptions: [],
      // 菜单信息
      menus: [],
      // Table details
      info: {}
    };
  },
  created() {
    const tableId = this.$route.params && this.$route.params.tableId;
    if (tableId) {
      // Get table details
      getGenTable(tableId).then(res => {
        this.cloumns = res.data.rows;
        this.info = res.data.info;
        this.tables = res.data.tables;
      });
      /** Query dictionary drop-down list */
      getDictOptionselect().then(response => {
        this.dictOptions = response.data;
      });
      /** Query menu drop-down list */
      getMenuTreeselect().then(response => {
        this.menus = this.handleTree(response.data, "menuId");
      });
    }
  },
  methods: {
    /** Submit button */
    submitForm() {
      const basicForm = this.$refs.basicInfo.$refs.basicInfoForm;
      const genForm = this.$refs.genInfo.$refs.genInfoForm;
      Promise.all([basicForm, genForm].map(this.getFormPromise)).then(res => {
        const validateResult = res.every(item => !!item);
        if (validateResult) {
          const genTable = Object.assign({}, basicForm.model, genForm.model);
          genTable.columns = this.cloumns;
          genTable.params = {
            treeCode: genTable.treeCode,
            treeName: genTable.treeName,
            treeParentCode: genTable.treeParentCode,
            parentMenuId: genTable.parentMenuId
          };
          updateGenTable(genTable).then(res => {
            this.msgSuccess(res.msg);
            if (res.code === 200) {
              this.close();
            }
          });
        } else {
          this.msgError("The form verification failed, please recheck the submitted content");
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
    /** Close button */
    close() {
      this.$store.dispatch("tagsView/delView", this.$route);
      this.$router.push({ path: "/tool/gen", query: { t: Date.now()}})
    }
  },
  mounted() {
    const el = this.$refs.dragTable.$el.querySelectorAll(".el-table__body-wrapper > table > tbody")[0];
    const sortable = Sortable.create(el, {
      handle: ".allowDrag",
      onEnd: evt => {
        const targetRow = this.cloumns.splice(evt.oldIndex, 1)[0];
        this.cloumns.splice(evt.newIndex, 0, targetRow);
        for (let index in this.cloumns) {
          this.cloumns[index].sort = parseInt(index) + 1;
        }
      }
    });
  }
};
</script>
