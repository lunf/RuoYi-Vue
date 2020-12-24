<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" :lg="12" style="padding-left: 20px">
        <h2>Backend Management System</h2>
        <p>
          <b>Current version:</b> <span>v{{ version }}</span>
        </p>
        <p>
          <el-button
            type="primary"
            size="mini"
            icon="el-icon-message"
            plain
            @click="goTarget('mailto:${support@domain.com}')"
            >Request features</el-button
          >
        </p>
      </el-col>
    </el-row>
    <el-divider />
    <el-row :gutter="20">
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>Contact Information</span>
          </div>
          <div class="body">
            <p>
              <i class="el-icon-s-promotion"></i> Website：<el-link
                href="http://www.ruoyi.vip"
                target="_blank"
                >http://www.ruoyi.vip</el-link
              >
            </p>
            <p>
              <i class="el-icon-user-solid"></i> Skype：
                <a href="https://jq.qq.com/?_wv=1027&k=4WWwqNxN" target="_blank"> skye_account</a
              >
            </p>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>Next Release</span>
          </div>
          <div class="body">
            <i class="el-icon-time"></i><span style="display: inline-block; height: 30px; line-height: 30px"
              >2020-01-20</span>
            <el-collapse accordion>
              <el-collapse-item title="v3.4.0 Release note">
                <ol>
                  <li>Added cache monitoring function</li>
                  <li>Support theme style configuration</li>
                  <li>Fix the problem that switching between multi-level menus cannot be cached</li>
                </ol>
              </el-collapse-item>
            </el-collapse>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="8">
        <el-card class="update-log">
          <div slot="header" class="clearfix">
            <span>Previous Release</span>
          </div>
          <el-collapse accordion>
            <el-collapse-item title="v3.4.0 - 2021-02-22">
              <ol>
                <li>User mobile email & menu component modification allows empty string</li>
                <li>Upgrade SpringBoot to the latest version 2.2.13 to improve startup speed</li>
                <li>Upgrade druid to the latest version v1.2.4</li>
                <li>Upgrade fastjson to the latest version 1.2.75</li>
                <li>Upgrade element-ui to the latest version 2.15.0</li>
                <li>Fixed IE11 browser error report</li>
                <li>Optimize the problem that switching between multi-level menus cannot be cached</li>
                <li>Push and add catch after login to prevent checking errors</li>
                <li>Add redis database index properties to the configuration file</li>
                <li>Excel annotation supports Image image export</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v3.3.0 - 2020-12-14">
              <ol>
                <li>Added cache monitoring function</li>
                <li>Support theme style configuration</li>
                <li>Fix the problem that switching between multi-level menus cannot be cached</li>
                <li>Multi-level menu automatic configuration components</li>
                <li>Code generation preview supports highlighting</li>
                <li>Support Get request to map Params parameters</li>
                <li>Delete user and role unbind association</li>
                <li>Remove the required verification of the user's mobile phone mailbox department</li>
                <li>Excel supports annotation align alignment</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v3.2.1 - 2020-11-18">
              <ol>
                <li>Prevent arbitrary file download vulnerabilities</li>
                <li>Code generation supports upload control</li>
                <li>Added image upload component</li>
              </ol>
            </el-collapse-item>
            <el-collapse-item title="v3.2.0 - 2020-10-10">
              <ol>
                <li>Upgrade springboot version to 2.1.17 to improve security</li>
                <li>Upgrade oshi to the latest version v5.2.5</li>
                <li>Upgrade druid to the latest version v1.2.1</li>
                <li>Upgrade jjwt to version 0.9.1</li>
                <li>Upgrade fastjson to the latest version 1.2.74</li>
              </ol>
            </el-collapse-item>

            <el-collapse-item title="v3.1.0 - 2020-08-13">
              <ol>
                <li>Add refresh & explicit query component on the right side of the table toolbar</li>
                <li>The backend supports CORS cross-domain requests</li>
              </ol>
            </el-collapse-item>
          </el-collapse>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "index",
  data() {
    return {
      // Version
      version: "3.4.0",
    };
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }
  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }
  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>
