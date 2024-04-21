module.exports = {
  /**
   * The side bar theme. The dark topic.theme-dark，The light theme.theme-light
   */
  sideTheme: 'theme-dark',

  /**
   * Is the system set up?
   */
  showSettings: false,

  /**
   * Showing the top navigation.
   */
  topNav: false,

  /**
   * Should show tagsView
   */
  tagsView: true,

  /**
   * Is the head fixed?
   */
  fixedHeader: false,

  /**
   * Should showlogo
   */
  sidebarLogo: true,

  /**
   * Showing a dynamic title
   */
  dynamicTitle: false,

  /**
   * @type {string | array} 'production' | ['production', 'development']
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: 'production'
}
