<template>
  <div class="app-container">
    <el-row :gutter="10">
      <el-col :span="8">
        <el-card style="height: calc(100vh - 125px)">
          <div slot="header">
            <span><i class="el-icon-collection"></i> Cache List</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              icon="el-icon-refresh-right"
              @click="refreshCacheNames()"
            ></el-button>
          </div>
          <el-table
            v-loading="loading"
            :data="cacheNames"
            :height="tableHeight"
            highlight-current-row
            @row-click="getCacheKeys"
            style="width: 100%"
          >
            <el-table-column
              label="The number"
              width="60"
              type="index"
            ></el-table-column>

            <el-table-column
              label="Cache Name"
              align="center"
              prop="cacheName"
              :show-overflow-tooltip="true"
              :formatter="nameFormatter"
            ></el-table-column>

            <el-table-column
              label="Note to"
              align="center"
              prop="remark"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="Operations"
              width="60"
              align="center"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleClearCacheName(scope.row)"
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card style="height: calc(100vh - 125px)">
          <div slot="header">
            <span><i class="el-icon-key"></i> List of key names</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              icon="el-icon-refresh-right"
              @click="refreshCacheKeys()"
            ></el-button>
          </div>
          <el-table
            v-loading="subLoading"
            :data="cacheKeys"
            :height="tableHeight"
            highlight-current-row
            @row-click="handleCacheValue"
            style="width: 100%"
          >
            <el-table-column
              label="The number"
              width="60"
              type="index"
            ></el-table-column>
            <el-table-column
              label="The key name."
              align="center"
              :show-overflow-tooltip="true"
              :formatter="keyFormatter"
            >
            </el-table-column>
            <el-table-column
              label="Operations"
              width="60"
              align="center"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  @click="handleClearCacheKey(scope.row)"
                ></el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>

      <el-col :span="8">
        <el-card :bordered="false" style="height: calc(100vh - 125px)">
          <div slot="header">
            <span><i class="el-icon-document"></i> cache content</span>
            <el-button
              style="float: right; padding: 3px 0"
              type="text"
              icon="el-icon-refresh-right"
              @click="handleClearCacheAll()"
              >Cleaning all.</el-button
            >
          </div>
          <el-form :model="cacheForm">
            <el-row :gutter="32">
              <el-col :offset="1" :span="22">
                <el-form-item label="Cache Name:" prop="cacheName">
                  <el-input v-model="cacheForm.cacheName" :readOnly="true" />
                </el-form-item>
              </el-col>
              <el-col :offset="1" :span="22">
                <el-form-item label="The key name.:" prop="cacheKey">
                  <el-input v-model="cacheForm.cacheKey" :readOnly="true" />
                </el-form-item>
              </el-col>
              <el-col :offset="1" :span="22">
                <el-form-item label="cache content:" prop="cacheValue">
                  <el-input
                    v-model="cacheForm.cacheValue"
                    type="textarea"
                    :rows="8"
                    :readOnly="true"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { listCacheName, listCacheKey, getCacheValue, clearCacheName, clearCacheKey, clearCacheAll } from "@/api/monitor/cache";

export default {
  name: "CacheList",
  data() {
    return {
      cacheNames: [],
      cacheKeys: [],
      cacheForm: {},
      loading: true,
      subLoading: false,
      nowCacheName: "",
      tableHeight: window.innerHeight - 200
    };
  },
  created() {
    this.getCacheNames();
  },
  methods: {
    /** Ask for Cache Name List */
    getCacheNames() {
      this.loading = true;
      listCacheName().then(response => {
        this.cacheNames = response.data;
        this.loading = false;
      });
    },
    /** Updated Cache Name List */
    refreshCacheNames() {
      this.getCacheNames();
      this.$modal.msgSuccess("Updated Cache List Successfully");
    },
    /** Clean the specified name cache */
    handleClearCacheName(row) {
      clearCacheName(row.cacheName).then(response => {
        this.$modal.msgSuccess("Clean the name.[" + row.cacheName + "]Successful");
        this.getCacheKeys();
      });
    },
    /** Ask for Cache Key Name List */
    getCacheKeys(row) {
      const cacheName = row !== undefined ? row.cacheName : this.nowCacheName;
      if (cacheName === "") {
        return;
      }
      this.subLoading = true;
      listCacheKey(cacheName).then(response => {
        this.cacheKeys = response.data;
        this.subLoading = false;
        this.nowCacheName = cacheName;
      });
    },
    /** Updated Cache Key Name List */
    refreshCacheKeys() {
      this.getCacheKeys();
      this.$modal.msgSuccess("Updated key list successfully");
    },
    /** Clean the key name cache. */
    handleClearCacheKey(cacheKey) {
      clearCacheKey(cacheKey).then(response => {
        this.$modal.msgSuccess("Clean the key name.[" + cacheKey + "]Successful");
        this.getCacheKeys();
      });
    },
    /** Remove the list. */
    nameFormatter(row) {
      return row.cacheName.replace(":", "");
    },
    /** Remove the key. */
    keyFormatter(cacheKey) {
      return cacheKey.replace(this.nowCacheName, "");
    },
    /** Ask for detailed cache content. */
    handleCacheValue(cacheKey) {
      getCacheValue(this.nowCacheName, cacheKey).then(response => {
        this.cacheForm = response.data;
      });
    },
    /** Clean the entire cache. */
    handleClearCacheAll() {
      clearCacheAll().then(response => {
        this.$modal.msgSuccess("Clean up all cache successfully.");
      });
    }
  },
};
</script>
