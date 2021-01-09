<template>
  <div class="app-container">
    <el-row>
      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>CPU</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
                <tr>
                  <th class="is-leaf"><div class="cell">Attributes</div></th>
                  <th class="is-leaf"><div class="cell">Value</div></th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="cell">Number of Cores</div></td>
                  <td><div class="cell" v-if="server.cpu">{{ server.cpu.cpuNum }}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">User Utilization</div></td>
                  <td><div class="cell" v-if="server.cpu">{{ server.cpu.used }}%</div></td>
                </tr>
                <tr>
                  <td><div class="cell">System Utilization</div></td>
                  <td><div class="cell" v-if="server.cpu">{{ server.cpu.sys }}%</div></td>
                </tr>
                <tr>
                  <td><div class="cell">Current Idle</div></td>
                  <td><div class="cell" v-if="server.cpu">{{ server.cpu.free }}%</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>

      <el-col :span="12" class="card-box">
        <el-card>
          <div slot="header"><span>RAM</span></div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
                <tr>
                  <th class="is-leaf"><div class="cell">Attributes</div></th>
                  <th class="is-leaf"><div class="cell">RAM</div></th>
                  <th class="is-leaf"><div class="cell">JVM</div></th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <td><div class="cell">Total Memory</div></td>
                  <td><div class="cell" v-if="server.mem">{{ server.mem.total }}G</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.total }}M</div></td>
                </tr>
                <tr>
                  <td><div class="cell">Used Memory</div></td>
                  <td><div class="cell" v-if="server.mem">{{ server.mem.used}}G</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.used}}M</div></td>
                </tr>
                <tr>
                  <td><div class="cell">Remaining Memory</div></td>
                  <td><div class="cell" v-if="server.mem">{{ server.mem.free }}G</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.free }}M</div></td>
                </tr>
                <tr>
                  <td><div class="cell">Usage Rate</div></td>
                  <td><div class="cell" v-if="server.mem" :class="{'text-danger': server.mem.usage > 80}">{{ server.mem.usage }}%</div></td>
                  <td><div class="cell" v-if="server.jvm" :class="{'text-danger': server.jvm.usage > 80}">{{ server.jvm.usage }}%</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>

      <el-col :span="24" class="card-box">
        <el-card>
          <div slot="header">
            <span>Server Information</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <tbody>
                <tr>
                  <td><div class="cell">Name</div></td>
                  <td><div class="cell" v-if="server.sys">{{ server.sys.computerName }}</div></td>
                  <td><div class="cell">Operating System</div></td>
                  <td><div class="cell" v-if="server.sys">{{ server.sys.osName }}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">Server IP</div></td>
                  <td><div class="cell" v-if="server.sys">{{ server.sys.computerIp }}</div></td>
                  <td><div class="cell">System Architecture</div></td>
                  <td><div class="cell" v-if="server.sys">{{ server.sys.osArch }}</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>

      <el-col :span="24" class="card-box">
        <el-card>
          <div slot="header">
            <span>Java VM Info</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <tbody>
                <tr>
                  <td><div class="cell">Name</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.name }}</div></td>
                  <td><div class="cell">Version</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.version }}</div></td>
                </tr>
                <tr>
                  <td><div class="cell">Start Time</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.startTime }}</div></td>
                  <td><div class="cell">Run Time</div></td>
                  <td><div class="cell" v-if="server.jvm">{{ server.jvm.runTime }}</div></td>
                </tr>
                <tr>
                  <td colspan="1"><div class="cell">JVM Home</div></td>
                  <td colspan="3"><div class="cell" v-if="server.jvm">{{ server.jvm.home }}</div></td>
                </tr>
                <tr>
                  <td colspan="1"><div class="cell">Project Path</div></td>
                  <td colspan="3"><div class="cell" v-if="server.sys">{{ server.sys.userDir }}</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>

      <el-col :span="24" class="card-box">
        <el-card>
          <div slot="header">
            <span>Disk Information</span>
          </div>
          <div class="el-table el-table--enable-row-hover el-table--medium">
            <table cellspacing="0" style="width: 100%;">
              <thead>
                <tr>
                  <th class="is-leaf"><div class="cell">Drive Letter</div></th>
                  <th class="is-leaf"><div class="cell">File System</div></th>
                  <th class="is-leaf"><div class="cell">Drive Type</div></th>
                  <th class="is-leaf"><div class="cell">Total Size</div></th>
                  <th class="is-leaf"><div class="cell">Available Size</div></th>
                  <th class="is-leaf"><div class="cell">Used Size</div></th>
                  <th class="is-leaf"><div class="cell">Usage Percentage</div></th>
                </tr>
              </thead>
              <tbody v-if="server.sysFiles">
                <tr v-for="sysFile in server.sysFiles">
                  <td><div class="cell">{{ sysFile.dirName }}</div></td>
                  <td><div class="cell">{{ sysFile.sysTypeName }}</div></td>
                  <td><div class="cell">{{ sysFile.typeName }}</div></td>
                  <td><div class="cell">{{ sysFile.total }}</div></td>
                  <td><div class="cell">{{ sysFile.free }}</div></td>
                  <td><div class="cell">{{ sysFile.used }}</div></td>
                  <td><div class="cell" :class="{'text-danger': sysFile.usage > 80}">{{ sysFile.usage }}%</div></td>
                </tr>
              </tbody>
            </table>
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getServer } from "@/api/monitor/server";

export default {
  name: "Server",
  data() {
    return {
      // Load layer information
      loading: [],
      // server information
      server: []
    };
  },
  created() {
    this.getList();
    this.openLoading();
  },
  methods: {
    /** Query server information */
    getList() {
      getServer().then(response => {
        this.server = response.data;
        this.loading.close();
      });
    },
    // Open loading layer
    openLoading() {
      this.loading = this.$loading({
        lock: true,
        text: "Loading...",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)"
      });
    }
  }
};
</script>
