'use strict'
const path = require('path')

function resolve(dir) {
  return path.join(__dirname, dir)
}

const CompressionPlugin = require('compression-webpack-plugin')

const name = process.env.VUE_APP_TITLE || 'If the management system' // Title of the website

const port = process.env.port || process.env.npm_config_port || 80 // The port.

// vue.config.js Configuration Explanation
//Officialvue.config.js Reference Documents https://cli.vuejs.org/zh/config/#css-loaderoptions
// Here is only a part.，Specific configuration of reference documents
module.exports = {
  // Installation in the production and development environmentURL。
  // In the default circumstances.，Vue CLI It will assume that your application is deployed on a domain name root route.
  // for example https://www.ruoyi.vip/。If the application is deployed on a subway，You need to specify this subway with this option.。for example，If your application is deployed https://www.ruoyi.vip/admin/，and set up baseUrl for /admin/。
  publicPath: process.env.NODE_ENV === "production" ? "/" : "/",
  // innpm run build or yarn build The time ，Name of the directory of the file generated（to andbaseUrlThe production environmental path consistent.）（presumeddist）
  outputDir: 'dist',
  // Statistical resources generated. (js、css、img、fonts) of；（After the packaging.，Statistical resources will be placed under this folder.）
  assetsDir: 'static',
  // is opened.eslintpreserved testing.，Effective Value：ture | false | 'error'
  lintOnSave: process.env.NODE_ENV === 'development',
  // If you don’t need a production environment. source map，It can be set to false Accelerating the production environment.。
  productionSourceMap: false,
  // webpack-dev-server Related configuration
  devServer: {
    host: '0.0.0.0',
    port: port,
    open: true,
    proxy: {
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      [process.env.VUE_APP_BASE_API]: {
        target: `http://localhost:8080`,
        changeOrigin: true,
        pathRewrite: {
          ['^' + process.env.VUE_APP_BASE_API]: ''
        }
      }
    },
    disableHostCheck: true
  },
  css: {
    loaderOptions: {
      sass: {
        sassOptions: { outputStyle: "expanded" }
      }
    }
  },
  configureWebpack: {
    name: name,
    resolve: {
      alias: {
        '@': resolve('src')
      }
    },
    plugins: [
      // http://doc.ruoyi.vip/ruoyi-vue/other/faq.html#Use ofgzipCompressed static files.
      new CompressionPlugin({
        cache: false,                                  // Do not activate file cache
        test: /\.(js|css|html|jpe?g|png|gif|svg)?$/i,  // Compressed file format.
        filename: '[path][base].gz[query]',            // Document Name After Compression
        algorithm: 'gzip',                             // Use ofgzipCompressed
        minRatio: 0.8,                                 // The compression proportion.，less than 80% Documents will not be compressed.
        deleteOriginalAssets: false                    // Remove the original file after compression.
      })
    ],
  },
  chainWebpack(config) {
    config.plugins.delete('preload') // TODO: need test
    config.plugins.delete('prefetch') // TODO: need test

    // set svg-sprite-loader
    config.module
      .rule('svg')
      .exclude.add(resolve('src/assets/icons'))
      .end()
    config.module
      .rule('icons')
      .test(/\.svg$/)
      .include.add(resolve('src/assets/icons'))
      .end()
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
      .end()

    config.when(process.env.NODE_ENV !== 'development', config => {
          config
            .plugin('ScriptExtHtmlWebpackPlugin')
            .after('html')
            .use('script-ext-html-webpack-plugin', [{
            // `runtime` must same as runtimeChunk name. default is `runtime`
              inline: /runtime\..*\.js$/
            }])
            .end()

          config.optimization.splitChunks({
            chunks: 'all',
            cacheGroups: {
              libs: {
                name: 'chunk-libs',
                test: /[\\/]node_modules[\\/]/,
                priority: 10,
                chunks: 'initial' // only package third parties that are initially dependent
              },
              elementUI: {
                name: 'chunk-elementUI', // split elementUI into a single package
                test: /[\\/]node_modules[\\/]_?element-ui(.*)/, // in order to adapt to cnpm
                priority: 20 // the weight needs to be larger than libs and app or it will be packaged into libs or app
              },
              commons: {
                name: 'chunk-commons',
                test: resolve('src/components'), // can customize your rules
                minChunks: 3, //  minimum common number
                priority: 5,
                reuseExistingChunk: true
              }
            }
          })
          config.optimization.runtimeChunk('single')
    })
  }
}
