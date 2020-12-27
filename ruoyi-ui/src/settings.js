module.exports = {
  title: 'Admin Dashboard System',

  /**
   * Sidebar theme dark theme (theme-dark), light theme (theme-light)
   */
  sideTheme: 'theme-dark',

  /**
   * Show the system layout configuration
   */
  showSettings: false,

  /**
   * Show tagsView
   */
  tagsView: true,

  /**
   * Should fix the head
   */
  fixedHeader: false,

  /**
   * Should display the logo
   */
  sidebarLogo: true,

  /**
   * @type {string | array} 'production' | ['production', 'development']
   * @description Need show err logs component.
   * The default is only used in the production env
   * If you want to also use it in dev, you can pass ['production', 'development']
   */
  errorLog: 'production'
}
