<template>
  <el-form ref="genInfoForm" :model="info" :rules="rules" label-width="150px">
    <el-row>
      <el-col :span="12">
        <el-form-item prop="tplCategory">
          <span slot="label">Create a template.</span>
          <el-select v-model="info.tplCategory" @change="tplSelectChange">
            <el-option label="The Single Table（Additional removal check）" value="crud" />
            <el-option label="The Tree（Additional removal check）" value="tree" />
            <el-option label="The master table.（Additional removal check）" value="sub" />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item prop="tplWebType">
          <span slot="label">Type of front</span>
          <el-select v-model="info.tplWebType">
            <el-option label="Vue2 Element UI The model" value="element-ui" />
            <el-option label="Vue3 Element Plus The model" value="element-plus" />
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item prop="packageName">
          <span slot="label">
            Create a package route.
            <el-tooltip content="Which is generated.javaUnder the package.，for example com.ruoyi.system" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.packageName" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item prop="moduleName">
          <span slot="label">
            Create a module name.
            <el-tooltip content="Understood as a subsystem.，for example system" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.moduleName" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item prop="businessName">
          <span slot="label">
            Create a business name.
            <el-tooltip content="Understood as functional English name.，for example user" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.businessName" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item prop="functionName">
          <span slot="label">
            Name of Success.
            <el-tooltip content="Used as a description.，for example Users" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.functionName" />
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item prop="genType">
          <span slot="label">
            Create the method of code.
            <el-tooltip content="I thinkzipCompressed package.，You can also customize the route." placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-radio v-model="info.genType" label="0">zipCompressed package</el-radio>
          <el-radio v-model="info.genType" label="1">Personalized route</el-radio>
        </el-form-item>
      </el-col>

      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            The top menu.
            <el-tooltip content="Distributed to the specified menu.，for example system management" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <treeselect
            :append-to-body="true"
            v-model="info.parentMenuId"
            :options="menus"
            :normalizer="normalizer"
            :show-count="true"
            placeholder="Please select the system menu."
          />
        </el-form-item>
      </el-col>

      <el-col :span="24" v-if="info.genType == '1'">
        <el-form-item prop="genPath">
          <span slot="label">
            Personalized route
            <el-tooltip content="Complete the disks with absolute route.，If not filled.，Created to the present.Webunder the project." placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-input v-model="info.genPath">
            <el-dropdown slot="append">
              <el-button type="primary">
                Fast choice of new routes
                <i class="el-icon-arrow-down el-icon--right"></i>
              </el-button>
              <el-dropdown-menu slot="dropdown">
                <el-dropdown-item @click.native="info.genPath = '/'">Restore the default production basis route</el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>
          </el-input>
        </el-form-item>
      </el-col>
    </el-row>

    <el-row v-show="info.tplCategory == 'tree'">
      <h4 class="form-header">Other information</h4>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            The tree code field.
            <el-tooltip content="The tree shows the code field name.， as：dept_id" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.treeCode" placeholder="Please choose">
            <el-option
              v-for="(column, index) in info.columns"
              :key="index"
              :label="column.columnName + '：' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            The tree code field.
            <el-tooltip content="The tree shows the father code field name， as：parent_Id" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.treeParentCode" placeholder="Please choose">
            <el-option
              v-for="(column, index) in info.columns"
              :key="index"
              :label="column.columnName + '：' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            Name of the tree field
            <el-tooltip content="Showing the tree nod name field name， as：dept_name" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.treeName" placeholder="Please choose">
            <el-option
              v-for="(column, index) in info.columns"
              :key="index"
              :label="column.columnName + '：' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
    <el-row v-show="info.tplCategory == 'sub'">
      <h4 class="form-header">Related Information</h4>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            Name of associated subtitles.
            <el-tooltip content="Name of associated subtitles.， as：sys_user" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.subTableName" placeholder="Please choose" @change="subSelectChange">
            <el-option
              v-for="(table, index) in tables"
              :key="index"
              :label="table.tableName + '：' + table.tableComment"
              :value="table.tableName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
      <el-col :span="12">
        <el-form-item>
          <span slot="label">
            The external key name associated with the subtitle.
            <el-tooltip content="The external key name associated with the subtitle.， as：user_id" placement="top">
              <i class="el-icon-question"></i>
            </el-tooltip>
          </span>
          <el-select v-model="info.subTableFkName" placeholder="Please choose">
            <el-option
              v-for="(column, index) in subColumns"
              :key="index"
              :label="column.columnName + '：' + column.columnComment"
              :value="column.columnName"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-col>
    </el-row>
  </el-form>
</template>

<script>
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  components: { Treeselect },
  props: {
    info: {
      type: Object,
      default: null
    },
    tables: {
      type: Array,
      default: null
    },
    menus: {
      type: Array,
      default: []
    },
  },
  data() {
    return {
      subColumns: [],
      rules: {
        tplCategory: [
          { required: true, message: "Please create a template.", trigger: "blur" }
        ],
        packageName: [
          { required: true, message: "Please enter the package route.", trigger: "blur" }
        ],
        moduleName: [
          { required: true, message: "Please enter the module name.", trigger: "blur" }
        ],
        businessName: [
          { required: true, message: "Please enter a business name.", trigger: "blur" }
        ],
        functionName: [
          { required: true, message: "Please enter the success name.", trigger: "blur" }
        ],
      }
    };
  },
  watch: {
    'info.subTableName': function(val) {
      this.setSubTableColumns(val);
    },
    'info.tplWebType': function(val) {
      if (val === '') {
        this.info.tplWebType = "element-ui";
      }
    }
  },
  methods: {
    /** Convert menu data structure */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.menuId,
        label: node.menuName,
        children: node.children
      };
    },
    /** Choose the subname trigger */
    subSelectChange(value) {
      this.info.subTableFkName = '';
    },
    /** Choose to create a template. */
    tplSelectChange(value) {
      if(value !== 'sub') {
        this.info.subTableName = '';
        this.info.subTableFkName = '';
      }
    },
    /** Set the external key. */
    setSubTableColumns(value) {
      for (var item in this.tables) {
        const name = this.tables[item].tableName;
        if (value === name) {
          this.subColumns = this.tables[item].columns;
          break;
        }
      }
    }
  }
};
</script>
