<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="Name of Menu" prop="menuName">
        <el-input
          v-model="queryParams.menuName"
          placeholder="Please enter the menu name."
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="state of" prop="status">
        <el-select v-model="queryParams.status" placeholder="The menu state." clearable>
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
          v-hasPermi="['system:menu:add']"
        >Added</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-sort"
          size="mini"
          @click="toggleExpandAll"
        >opened/Completed</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="menuList"
      row-key="menuId"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="menuName" label="Name of Menu" :show-overflow-tooltip="true" width="160"></el-table-column>
      <el-table-column prop="icon" label="The Symbol" align="center" width="100">
        <template slot-scope="scope">
          <svg-icon :icon-class="scope.row.icon" />
        </template>
      </el-table-column>
      <el-table-column prop="orderNum" label="ordered" width="60"></el-table-column>
      <el-table-column prop="perms" label="Identification of authority" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="component" label="The component route" :show-overflow-tooltip="true"></el-table-column>
      <el-table-column prop="status" label="state of" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="Creating time." align="center" prop="createTime">
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
            v-hasPermi="['system:menu:edit']"
          >Modified</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:menu:add']"
          >Added</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:menu:remove']"
          >removed</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- Add or modify the menu dialog box -->
    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="24">
            <el-form-item label="The top menu." prop="parentId">
              <treeselect
                v-model="form.parentId"
                :options="menuOptions"
                :normalizer="normalizer"
                :show-count="true"
                placeholder="Choose the top menu."
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="Type of Menu" prop="menuType">
              <el-radio-group v-model="form.menuType">
                <el-radio label="M">The catalogue</el-radio>
                <el-radio label="C">The menu</el-radio>
                <el-radio label="F">The button</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="24" v-if="form.menuType != 'F'">
            <el-form-item label="Menu Symbols" prop="icon">
              <el-popover
                placement="bottom-start"
                width="460"
                trigger="click"
                @show="$refs['iconSelect'].reset()"
              >
                <IconSelect ref="iconSelect" @selected="selected" :active-icon="form.icon" />
                <el-input slot="reference" v-model="form.icon" placeholder="Click to select the icon." readonly>
                  <svg-icon
                    v-if="form.icon"
                    slot="prefix"
                    :icon-class="form.icon"
                    style="width: 25px;"
                  />
                  <i v-else slot="prefix" class="el-icon-search el-input__icon" />
                </el-input>
              </el-popover>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Name of Menu" prop="menuName">
              <el-input v-model="form.menuName" placeholder="Please enter the menu name." />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="Showing order." prop="orderNum">
              <el-input-number v-model="form.orderNum" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item prop="isFrame">
              <span slot="label">
                <el-tooltip content="The option is the external link and the address is required to`http(s)://`Beginning" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                The external chain.
              </span>
              <el-radio-group v-model="form.isFrame">
                <el-radio label="0">is</el-radio>
                <el-radio label="1">No</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item prop="path">
              <span slot="label">
                <el-tooltip content="Access to the router address.，as：`user`，If the external network address requires internal link access,`http(s)://`Beginning" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                The routing address.
              </span>
              <el-input v-model="form.path" placeholder="Please enter the router address." />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item prop="component">
              <span slot="label">
                <el-tooltip content="Access to the component route，as：`system/user/index`，presumed in`views`under the catalogue." placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                The component route
              </span>
              <el-input v-model="form.component" placeholder="Please enter the component route." />
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'M'">
            <el-form-item prop="perms">
              <el-input v-model="form.perms" placeholder="Please enter the permission label." maxlength="100" />
              <span slot="label">
                <el-tooltip content="Authority characters defined in the controller，as：@PreAuthorize(`@ss.hasPermi('system:user:list')`)" placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                Authorization of characters
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item prop="query">
              <el-input v-model="form.query" placeholder="Please enter the routing parameters." maxlength="255" />
              <span slot="label">
                <el-tooltip content='Access to the router’s default transmission parameters，as：`{"id": 1, "name": "ry"}`' placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                The routing parameters.
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType == 'C'">
            <el-form-item prop="isCache">
              <span slot="label">
                <el-tooltip content="The choice will be made.`keep-alive`cache，Need to match the components.`name`Keep the address consistent." placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                Is it cache
              </span>
              <el-radio-group v-model="form.isCache">
                <el-radio label="0">cache</el-radio>
                <el-radio label="1">not cache.</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.menuType != 'F'">
            <el-form-item prop="visible">
              <span slot="label">
                <el-tooltip content="Select Hidden and the router will not appear on the side bar.，It is still accessible." placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                Showing the state.
              </span>
              <el-radio-group v-model="form.visible">
                <el-radio
                  v-for="dict in dict.type.sys_show_hide"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item prop="status">
              <span slot="label">
                <el-tooltip content="If you select Stop, the router will not appear on the side bar.，cannot be visited." placement="top">
                <i class="el-icon-question"></i>
                </el-tooltip>
                The menu state.
              </span>
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.value"
                >{{dict.label}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">True is fixed</el-button>
        <el-button @click="cancel">take by</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listMenu, getMenu, delMenu, addMenu, updateMenu } from "@/api/system/menu";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import IconSelect from "@/components/IconSelect";

export default {
  name: "Menu",
  dicts: ['sys_show_hide', 'sys_normal_disable'],
  components: { Treeselect, IconSelect },
  data() {
    return {
      // covered layer.
      loading: true,
      // Showing the search conditions
      showSearch: true,
      // Menu Table Tree Data
      menuList: [],
      // Menu Tree Options
      menuOptions: [],
      // The top title.
      title: "",
      // Showing the explosion.
      open: false,
      // Is it launched，Supposedly all overlap.
      isExpandAll: false,
      // Repeat the table status.
      refreshTable: true,
      // Question of parameters.
      queryParams: {
        menuName: undefined,
        visible: undefined
      },
      // Number of Forms
      form: {},
      // Forms of Examination
      rules: {
        menuName: [
          { required: true, message: "The menu name cannot be empty.", trigger: "blur" }
        ],
        orderNum: [
          { required: true, message: "The menu order cannot be empty.", trigger: "blur" }
        ],
        path: [
          { required: true, message: "The router address cannot be empty.", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // Choose the symbol.
    selected(name) {
      this.form.icon = name;
    },
    /** Request menu list */
    getList() {
      this.loading = true;
      listMenu(this.queryParams).then(response => {
        this.menuList = this.handleTree(response.data, "menuId");
        this.loading = false;
      });
    },
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
    /** Ask for the tree structure. */
    getTreeselect() {
      listMenu().then(response => {
        this.menuOptions = [];
        const menu = { menuId: 0, menuName: 'The main eye.', children: [] };
        menu.children = this.handleTree(response.data, "menuId");
        this.menuOptions.push(menu);
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
        menuId: undefined,
        parentId: 0,
        menuName: undefined,
        icon: undefined,
        menuType: "M",
        orderNum: undefined,
        isFrame: "1",
        isCache: "0",
        visible: "0",
        status: "0"
      };
      this.resetForm("form");
    },
    /** Search button operation. */
    handleQuery() {
      this.getList();
    },
    /** Restore the button operation. */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** Add a button operation. */
    handleAdd(row) {
      this.reset();
      this.getTreeselect();
      if (row != null && row.menuId) {
        this.form.parentId = row.menuId;
      } else {
        this.form.parentId = 0;
      }
      this.open = true;
      this.title = "Add the menu.";
    },
    /** opened/Completed Operations */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** Change the button operation. */
    handleUpdate(row) {
      this.reset();
      this.getTreeselect();
      getMenu(row.menuId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "Modifying the menu";
      });
    },
    /** Submit the button. */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.menuId != undefined) {
            updateMenu(this.form).then(response => {
              this.$modal.msgSuccess("Changes are Successful");
              this.open = false;
              this.getList();
            });
          } else {
            addMenu(this.form).then(response => {
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
      this.$modal.confirm('confirming the removal of the name"' + row.menuName + '"of data.？').then(function() {
        return delMenu(row.menuId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("Remove Success");
      }).catch(() => {});
    }
  }
};
</script>
