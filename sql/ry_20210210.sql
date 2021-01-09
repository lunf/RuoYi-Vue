-- ----------------------------
-- 1, Department table
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint(20)      not null auto_increment    comment 'Department id',
  parent_id         bigint(20)      default 0                  comment 'Parent department id',
  ancestors         varchar(50)     default ''                 comment 'Ancestor list',
  dept_name         varchar(256)     default ''                 comment 'Department name',
  order_num         int(4)          default 0                  comment 'Display order',
  leader            varchar(20)     default null               comment 'Leader',
  phone             varchar(11)     default null               comment 'Contact number',
  email             varchar(50)     default null               comment 'Email address',
  status            char(1)         default '0'                comment 'Department status (0 normal, 1 disabled)',
  del_flag          char(1)         default '0'                comment 'Delete flag (0 means existence 2 means deletion)',
  create_by         varchar(64)     default ''                 comment 'Creator',
  create_time 	    datetime                                   comment 'Created at',
  update_by         varchar(64)     default ''                 comment 'Updater',
  update_time       datetime                                   comment 'Updated at',
  primary key (dept_id)
) engine=innodb auto_increment=200 comment = 'Department table';

-- ----------------------------
-- Initialization department table data
-- ----------------------------
insert into sys_dept values(100,  0,   '0',          'Ruoyi Technology',   0, 'John Doe', '15888888888', 'john.doe@domain.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(101,  100, '0,100',      'Shenzhen Headquarter', 1, 'John Doe', '15888888888', 'john.doe@domain.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(102,  100, '0,100',      'Changsha Branch', 2, 'John Doe', '15888888888', 'john.doe@domain.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(103,  101, '0,100,101',  'R&D department',   1, 'John Doe', '15888888888', 'john.doe@domain.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(104,  101, '0,100,101',  'Marketing department',   2, 'John Doe', '15888888888', 'john.doe@domain.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(105,  101, '0,100,101',  'Testing department',   3, 'John Doe', '15888888888', 'john.doe@domain.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(106,  101, '0,100,101',  'Financial department',   4, 'John Doe', '15888888888', 'john.doe@domain.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(107,  101, '0,100,101',  'Operation and maintenance department',   5, 'John Doe', '15888888888', 'john.doe@domain.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(108,  102, '0,100,102',  'Accounting department',   1, 'John Doe', '15888888888', 'john.doe@domain.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(109,  102, '0,100,102',  'Procurement department',   2, 'John Doe', '15888888888', 'john.doe@domain.com', '0', '0', 'admin', sysdate(), '', null);


-- ----------------------------
-- 2, User table
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment 'User ID',
  dept_id           bigint(20)      default null               comment 'Department ID',
  user_name         varchar(30)     not null                   comment 'User account',
  nick_name         varchar(30)     not null                   comment 'User`s Nickname',
  user_type         varchar(2)      default '00'               comment 'User type (00 system user)',
  email             varchar(50)     default ''                 comment 'Email address',
  phonenumber       varchar(11)     default ''                 comment 'Phone number',
  sex               char(1)         default '0'                comment 'User gender (0 male, 1 female, 2 unknown)',
  avatar            varchar(100)    default ''                 comment 'Avatar address',
  password          varchar(100)    default ''                 comment 'Password',
  status            char(1)         default '0'                comment 'Account status (0 normal, 1 disabled)',
  del_flag          char(1)         default '0'                comment 'Delete flag (0 means existence, 2 means deletion)',
  login_ip          varchar(128)    default ''                comment 'Last login IP',
  login_date        datetime                                   comment 'Last login time',
  create_by         varchar(64)     default ''                 comment 'Creator',
  create_time       datetime                                   comment 'Created at',
  update_by         varchar(64)     default ''                 comment 'Updater',
  update_time       datetime                                   comment 'Updated at',
  remark            varchar(500)    default null               comment 'Remark',
  primary key (user_id)
) engine=innodb auto_increment=100 comment = 'User table';

-- ----------------------------
-- Initialization user table
-- ----------------------------
insert into sys_user values(1,  103, 'admin', 'Admin', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, 'administrator');
insert into sys_user values(2,  105, 'ry',    'John Doe', '00', 'john.doe@domain.com',  '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, 'tester');


-- ----------------------------
-- 3, Job post table
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigint(20)      not null auto_increment    comment 'Post ID',
  post_code     varchar(64)     not null                   comment 'Job post code',
  post_name     varchar(50)     not null                   comment 'Position name',
  post_sort     int(4)          not null                   comment 'Display order',
  status        char(1)         not null                   comment 'Status (0 normal, 1 disabled)',
  create_by     varchar(64)     default ''                 comment 'Creator',
  create_time   datetime                                   comment 'Created at',
  update_by     varchar(64)     default ''			       comment 'Updater',
  update_time   datetime                                   comment 'Updated at',
  remark        varchar(500)    default null               comment 'Remark',
  primary key (post_id)
) engine=innodb comment = 'Job post table';

-- ----------------------------
-- Initialization-job post table
-- ----------------------------
insert into sys_post values(1, 'ceo',  'Chairman',    1, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(2, 'se',   'Project manager',  2, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(3, 'hr',   'Human resources',  3, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(4, 'user', 'General staff',  4, '0', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 4,Role table
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigint(20)      not null auto_increment    comment 'Role ID',
  role_name            varchar(30)     not null                   comment 'Role name',
  role_key             varchar(100)    not null                   comment 'Role permission string',
  role_sort            int(4)          not null                   comment 'Display order',
  data_scope           char(1)         default '1'                comment 'Data scope (1: All data permissions, 2: Customized data permissions, 3: Data permissions for this department, 4: Data permissions for this department and below)',
  menu_check_strictly  tinyint(1)      default 1                  comment 'Whether the menu tree selection items are displayed in association',
  dept_check_strictly  tinyint(1)      default 1                  comment 'Whether the department tree selection items are displayed in association',
  status               char(1)         not null                   comment 'Role status (0 normal 1 disabled)',
  del_flag             char(1)         default '0'                comment 'Delete flag (0 means existence, 2 means deletion)',
  create_by            varchar(64)     default ''                 comment 'Creator',
  create_time          datetime                                   comment 'Created at',
  update_by            varchar(64)     default ''                 comment 'Updater',
  update_time          datetime                                   comment 'Updated at',
  remark               varchar(500)    default null               comment 'Remark',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = 'Role table';

-- ----------------------------
-- Initialization-role table
-- ----------------------------
insert into sys_role values('1', 'Super administrator',  'admin',  1, 1, 1, 1, '0', '0', 'admin', sysdate(), '', null, 'Super administrator');
insert into sys_role values('2', 'Normal role',    'common', 2, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, 'Normal role');


-- ----------------------------
-- 5, Menu permission table
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment 'Menu ID',
  menu_name         varchar(50)     not null                   comment 'Menu name',
  parent_id         bigint(20)      default 0                  comment 'Parent menu ID',
  order_num         int(4)          default 0                  comment 'Display order',
  path              varchar(200)    default ''                 comment 'Routing address',
  component         varchar(255)    default null               comment 'Component path',
  is_frame          int(1)          default 1                  comment 'Whether it is an external link (0 yes, 1 no)',
  is_cache          int(1)          default 0                  comment 'Whether to cache (0 cache, 1 no cache)',
  menu_type         char(1)         default ''                 comment 'Menu type (M directory, C menu, F button)',
  visible           char(1)         default 0                  comment 'Menu visibility (0 display, 1 hide)',
  status            char(1)         default 0                  comment 'Menu status (0 normal, 1 disabled)',
  perms             varchar(100)    default null               comment 'Authority ID',
  icon              varchar(100)    default '#'                comment 'Menu icon',
  create_by         varchar(64)     default ''                 comment 'Creator',
  create_time       datetime                                   comment 'Created at',
  update_by         varchar(64)     default ''                 comment 'Updater',
  update_time       datetime                                   comment 'Updated at',
  remark            varchar(500)    default ''                 comment 'Remark',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = 'Menu permission table';

-- ----------------------------
-- Initialization-menu table
-- ----------------------------
-- Root menu
insert into sys_menu values('1', 'System', '0', '1', 'system',           null,   1, 0, 'M', '0', '0', '', 'system',   'admin', sysdate(), '', null, 'System management directory');
insert into sys_menu values('2', 'Monitoring', '0', '2', 'monitor',          null,   1, 0, 'M', '0', '0', '', 'monitor',  'admin', sysdate(), '', null, 'System monitoring directory');
insert into sys_menu values('3', 'Tools', '0', '3', 'tool',             null,   1, 0, 'M', '0', '0', '', 'tool',     'admin', sysdate(), '', null, 'System tool catalog');
insert into sys_menu values('4', 'Official website', '0', '4', 'http://ruoyi.vip', null ,  0, 0, 'M', '0', '0', '', 'guide',    'admin', sysdate(), '', null, 'Official website address');
-- Secondary menu
insert into sys_menu values('100',  'User Management', '1',   '1', 'user',       'system/user/index',        1, 0, 'C', '0', '0', 'system:user:list',        'user',          'admin', sysdate(), '', null, 'User management menu');
insert into sys_menu values('101',  'Role Management', '1',   '2', 'role',       'system/role/index',        1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'admin', sysdate(), '', null, 'Role management menu');
insert into sys_menu values('102',  'Menu Management', '1',   '3', 'menu',       'system/menu/index',        1, 0, 'C', '0', '0', 'system:menu:list',        'tree-table',    'admin', sysdate(), '', null, 'Menu management menu');
insert into sys_menu values('103',  'Department Management', '1',   '4', 'dept',       'system/dept/index',        1, 0, 'C', '0', '0', 'system:dept:list',        'tree',          'admin', sysdate(), '', null, 'Department management menu');
insert into sys_menu values('104',  'Position Management', '1',   '5', 'post',       'system/post/index',        1, 0, 'C', '0', '0', 'system:post:list',        'post',          'admin', sysdate(), '', null, 'Position management menu');
insert into sys_menu values('105',  'Dictionary Management', '1',   '6', 'dict',       'system/dict/index',        1, 0, 'C', '0', '0', 'system:dict:list',        'dict',          'admin', sysdate(), '', null, 'Dictionary management menu');
insert into sys_menu values('106',  'Parameter Settings', '1',   '7', 'config',     'system/config/index',      1, 0, 'C', '0', '0', 'system:config:list',      'edit',          'admin', sysdate(), '', null, 'Parameter setting menu');
insert into sys_menu values('107',  'Announcement', '1',   '8', 'notice',     'system/notice/index',      1, 0, 'C', '0', '0', 'system:notice:list',      'message',       'admin', sysdate(), '', null, 'Notification announcement menu');
insert into sys_menu values('108',  'Log Management', '1',   '9', 'log',        '',                         1, 0, 'M', '0', '0', '',                        'log',           'admin', sysdate(), '', null, 'Log management menu');
insert into sys_menu values('109',  'Online User', '2',   '1', 'online',     'monitor/online/index',     1, 0, 'C', '0', '0', 'monitor:online:list',     'online',        'admin', sysdate(), '', null, 'Online user menu');
insert into sys_menu values('110',  'Timed Task', '2',   '2', 'job',        'monitor/job/index',        1, 0, 'C', '0', '0', 'monitor:job:list',        'job',           'admin', sysdate(), '', null, 'Timed task menu');
insert into sys_menu values('111',  'Data Monitoring', '2',   '3', 'druid',      'monitor/druid/index',      1, 0, 'C', '0', '0', 'monitor:druid:list',      'druid',         'admin', sysdate(), '', null, 'Data monitoring menu');
insert into sys_menu values('112',  'Service Monitoring', '2',   '4', 'server',     'monitor/server/index',     1, 0, 'C', '0', '0', 'monitor:server:list',     'server',        'admin', sysdate(), '', null, 'Service monitoring menu');
insert into sys_menu values('113',  'Cache Monitoring', '2',   '5', 'cache',      'monitor/cache/index',      1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis',         'admin', sysdate(), '', null, 'Cache monitoring menu');
insert into sys_menu values('114',  'Form Construction', '3',   '1', 'build',      'tool/build/index',         1, 0, 'C', '0', '0', 'tool:build:list',         'build',         'admin', sysdate(), '', null, 'Form build menu');
insert into sys_menu values('115',  'Code Generation', '3',   '2', 'gen',        'tool/gen/index',           1, 0, 'C', '0', '0', 'tool:gen:list',           'code',          'admin', sysdate(), '', null, 'Code generation menu');
insert into sys_menu values('116',  'System Interface', '3',   '3', 'swagger',    'tool/swagger/index',       1, 0, 'C', '0', '0', 'tool:swagger:list',       'swagger',       'admin', sysdate(), '', null, 'System interface menu');
-- Third level menu
insert into sys_menu values('500',  'Operation log', '108', '1', 'operlog',    'monitor/operlog/index',    1, 0, 'C', '0', '0', 'monitor:operlog:list',    'form',          'admin', sysdate(), '', null, 'Operation log menu');
insert into sys_menu values('501',  'Login log', '108', '2', 'logininfor', 'monitor/logininfor/index', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor',    'admin', sysdate(), '', null, 'Login log menu');
-- User management button
insert into sys_menu values('1001', 'User query', '100', '1',  '', '', 1, 0, 'F', '0', '0', 'system:user:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1002', 'User added', '100', '2',  '', '', 1, 0, 'F', '0', '0', 'system:user:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1003', 'User modification', '100', '3',  '', '', 1, 0, 'F', '0', '0', 'system:user:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1004', 'User delete', '100', '4',  '', '', 1, 0, 'F', '0', '0', 'system:user:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1005', 'User export', '100', '5',  '', '', 1, 0, 'F', '0', '0', 'system:user:export',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1006', 'User import', '100', '6',  '', '', 1, 0, 'F', '0', '0', 'system:user:import',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1007', 'Reset password', '100', '7',  '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd',       '#', 'admin', sysdate(), '', null, '');
-- Role management button
insert into sys_menu values('1008', 'Role query', '101', '1',  '', '', 1, 0, 'F', '0', '0', 'system:role:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1009', 'New role', '101', '2',  '', '', 1, 0, 'F', '0', '0', 'system:role:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1010', 'Role modification', '101', '3',  '', '', 1, 0, 'F', '0', '0', 'system:role:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1011', 'Role deletion', '101', '4',  '', '', 1, 0, 'F', '0', '0', 'system:role:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1012', 'Role export', '101', '5',  '', '', 1, 0, 'F', '0', '0', 'system:role:export',         '#', 'admin', sysdate(), '', null, '');
-- Menu management button
insert into sys_menu values('1013', 'Menu query', '102', '1',  '', '', 1, 0, 'F', '0', '0', 'system:menu:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1014', 'New menu', '102', '2',  '', '', 1, 0, 'F', '0', '0', 'system:menu:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1015', 'Menu modification', '102', '3',  '', '', 1, 0, 'F', '0', '0', 'system:menu:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1016', 'Menu delete', '102', '4',  '', '', 1, 0, 'F', '0', '0', 'system:menu:remove',         '#', 'admin', sysdate(), '', null, '');
-- Department Management Button
insert into sys_menu values('1017', 'Department query', '103', '1',  '', '', 1, 0, 'F', '0', '0', 'system:dept:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1018', 'New department', '103', '2',  '', '', 1, 0, 'F', '0', '0', 'system:dept:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1019', 'Department modification', '103', '3',  '', '', 1, 0, 'F', '0', '0', 'system:dept:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1020', 'Department delete', '103', '4',  '', '', 1, 0, 'F', '0', '0', 'system:dept:remove',         '#', 'admin', sysdate(), '', null, '');
-- Position management button
insert into sys_menu values('1021', 'Job inquiry', '104', '1',  '', '', 1, 0, 'F', '0', '0', 'system:post:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1022', 'New job', '104', '2',  '', '', 1, 0, 'F', '0', '0', 'system:post:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1023', 'Post modification', '104', '3',  '', '', 1, 0, 'F', '0', '0', 'system:post:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1024', 'Post deletion', '104', '4',  '', '', 1, 0, 'F', '0', '0', 'system:post:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1025', 'Post export', '104', '5',  '', '', 1, 0, 'F', '0', '0', 'system:post:export',         '#', 'admin', sysdate(), '', null, '');
-- Dictionary management button
insert into sys_menu values('1026', 'Dictionary lookup', '105', '1', '#', '', 1, 0, 'F', '0', '0', 'system:dict:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1027', 'New dictionary', '105', '2', '#', '', 1, 0, 'F', '0', '0', 'system:dict:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1028', 'Dictionary modification', '105', '3', '#', '', 1, 0, 'F', '0', '0', 'system:dict:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1029', 'Dictionary delete', '105', '4', '#', '', 1, 0, 'F', '0', '0', 'system:dict:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1030', 'Dictionary export', '105', '5', '#', '', 1, 0, 'F', '0', '0', 'system:dict:export',         '#', 'admin', sysdate(), '', null, '');
-- Parameter setting button
insert into sys_menu values('1031', 'Parameter query', '106', '1', '#', '', 1, 0, 'F', '0', '0', 'system:config:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1032', 'Add parameter', '106', '2', '#', '', 1, 0, 'F', '0', '0', 'system:config:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1033', 'Parameter modification', '106', '3', '#', '', 1, 0, 'F', '0', '0', 'system:config:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1034', 'Parameter deletion', '106', '4', '#', '', 1, 0, 'F', '0', '0', 'system:config:remove',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1035', 'Parameter export', '106', '5', '#', '', 1, 0, 'F', '0', '0', 'system:config:export',       '#', 'admin', sysdate(), '', null, '');
-- Notification announcement button
insert into sys_menu values('1036', 'Announcement query', '107', '1', '#', '', 1, 0, 'F', '0', '0', 'system:notice:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1037', 'New announcement', '107', '2', '#', '', 1, 0, 'F', '0', '0', 'system:notice:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1038', 'Announcement modification', '107', '3', '#', '', 1, 0, 'F', '0', '0', 'system:notice:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1039', 'Announcement delete', '107', '4', '#', '', 1, 0, 'F', '0', '0', 'system:notice:remove',       '#', 'admin', sysdate(), '', null, '');
-- Operation log button
insert into sys_menu values('1040', 'Operation query', '500', '1', '#', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1041', 'Operation delete', '500', '2', '#', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1042', 'Log export', '500', '4', '#', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export',     '#', 'admin', sysdate(), '', null, '');
-- Login log button
insert into sys_menu values('1043', 'Login query', '501', '1', '#', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1044', 'Login delete', '501', '2', '#', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1045', 'Log export', '501', '3', '#', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export',  '#', 'admin', sysdate(), '', null, '');
-- Online user button
insert into sys_menu values('1046', 'Online search', '109', '1', '#', '', 1, 0, 'F', '0', '0', 'monitor:online:query',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1047', 'Batch logout', '109', '2', '#', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1048', 'Force logout', '109', '3', '#', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', sysdate(), '', null, '');
-- Time task button
insert into sys_menu values('1049', 'Task query', '110', '1', '#', '', 1, 0, 'F', '0', '0', 'monitor:job:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1050', 'New task', '110', '2', '#', '', 1, 0, 'F', '0', '0', 'monitor:job:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1051', 'Task modification', '110', '3', '#', '', 1, 0, 'F', '0', '0', 'monitor:job:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1052', 'Task delete', '110', '4', '#', '', 1, 0, 'F', '0', '0', 'monitor:job:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1053', 'State modification', '110', '5', '#', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1054', 'Task export', '110', '7', '#', '', 1, 0, 'F', '0', '0', 'monitor:job:export',         '#', 'admin', sysdate(), '', null, '');
-- Code generation button
insert into sys_menu values('1055', 'Generate query', '115', '1', '#', '', 1, 0, 'F', '0', '0', 'tool:gen:query',             '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1056', 'Generate modification', '115', '2', '#', '', 1, 0, 'F', '0', '0', 'tool:gen:edit',              '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1057', 'Generate delete', '115', '3', '#', '', 1, 0, 'F', '0', '0', 'tool:gen:remove',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1058', 'Import code', '115', '2', '#', '', 1, 0, 'F', '0', '0', 'tool:gen:import',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1059', 'Preview code', '115', '4', '#', '', 1, 0, 'F', '0', '0', 'tool:gen:preview',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1060', 'Generate code', '115', '5', '#', '', 1, 0, 'F', '0', '0', 'tool:gen:code',              '#', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 6, User and role association table - Role 1-N User
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment 'User ID',
  role_id   bigint(20) not null comment 'Role ID',
  primary key(user_id, role_id)
) engine=innodb comment = 'User and role association table';

-- ----------------------------
-- Initialization-user and role association table
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');


-- ----------------------------
-- 7, Role and menu association table - Role 1-N Menu
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment 'Role ID',
  menu_id   bigint(20) not null comment 'Menu ID',
  primary key(role_id, menu_id)
) engine=innodb comment = 'Role and menu association table';

-- ----------------------------
-- Initialization-role and menu association table
-- ----------------------------
insert into sys_role_menu values ('2', '1');
insert into sys_role_menu values ('2', '2');
insert into sys_role_menu values ('2', '3');
insert into sys_role_menu values ('2', '4');
insert into sys_role_menu values ('2', '100');
insert into sys_role_menu values ('2', '101');
insert into sys_role_menu values ('2', '102');
insert into sys_role_menu values ('2', '103');
insert into sys_role_menu values ('2', '104');
insert into sys_role_menu values ('2', '105');
insert into sys_role_menu values ('2', '106');
insert into sys_role_menu values ('2', '107');
insert into sys_role_menu values ('2', '108');
insert into sys_role_menu values ('2', '109');
insert into sys_role_menu values ('2', '110');
insert into sys_role_menu values ('2', '111');
insert into sys_role_menu values ('2', '112');
insert into sys_role_menu values ('2', '113');
insert into sys_role_menu values ('2', '114');
insert into sys_role_menu values ('2', '115');
insert into sys_role_menu values ('2', '116');
insert into sys_role_menu values ('2', '500');
insert into sys_role_menu values ('2', '501');
insert into sys_role_menu values ('2', '1000');
insert into sys_role_menu values ('2', '1001');
insert into sys_role_menu values ('2', '1002');
insert into sys_role_menu values ('2', '1003');
insert into sys_role_menu values ('2', '1004');
insert into sys_role_menu values ('2', '1005');
insert into sys_role_menu values ('2', '1006');
insert into sys_role_menu values ('2', '1007');
insert into sys_role_menu values ('2', '1008');
insert into sys_role_menu values ('2', '1009');
insert into sys_role_menu values ('2', '1010');
insert into sys_role_menu values ('2', '1011');
insert into sys_role_menu values ('2', '1012');
insert into sys_role_menu values ('2', '1013');
insert into sys_role_menu values ('2', '1014');
insert into sys_role_menu values ('2', '1015');
insert into sys_role_menu values ('2', '1016');
insert into sys_role_menu values ('2', '1017');
insert into sys_role_menu values ('2', '1018');
insert into sys_role_menu values ('2', '1019');
insert into sys_role_menu values ('2', '1020');
insert into sys_role_menu values ('2', '1021');
insert into sys_role_menu values ('2', '1022');
insert into sys_role_menu values ('2', '1023');
insert into sys_role_menu values ('2', '1024');
insert into sys_role_menu values ('2', '1025');
insert into sys_role_menu values ('2', '1026');
insert into sys_role_menu values ('2', '1027');
insert into sys_role_menu values ('2', '1028');
insert into sys_role_menu values ('2', '1029');
insert into sys_role_menu values ('2', '1030');
insert into sys_role_menu values ('2', '1031');
insert into sys_role_menu values ('2', '1032');
insert into sys_role_menu values ('2', '1033');
insert into sys_role_menu values ('2', '1034');
insert into sys_role_menu values ('2', '1035');
insert into sys_role_menu values ('2', '1036');
insert into sys_role_menu values ('2', '1037');
insert into sys_role_menu values ('2', '1038');
insert into sys_role_menu values ('2', '1039');
insert into sys_role_menu values ('2', '1040');
insert into sys_role_menu values ('2', '1041');
insert into sys_role_menu values ('2', '1042');
insert into sys_role_menu values ('2', '1043');
insert into sys_role_menu values ('2', '1044');
insert into sys_role_menu values ('2', '1045');
insert into sys_role_menu values ('2', '1046');
insert into sys_role_menu values ('2', '1047');
insert into sys_role_menu values ('2', '1048');
insert into sys_role_menu values ('2', '1049');
insert into sys_role_menu values ('2', '1050');
insert into sys_role_menu values ('2', '1051');
insert into sys_role_menu values ('2', '1052');
insert into sys_role_menu values ('2', '1053');
insert into sys_role_menu values ('2', '1054');
insert into sys_role_menu values ('2', '1055');
insert into sys_role_menu values ('2', '1056');
insert into sys_role_menu values ('2', '1057');
insert into sys_role_menu values ('2', '1058');
insert into sys_role_menu values ('2', '1059');
insert into sys_role_menu values ('2', '1060');

-- ----------------------------
-- 8、Role and department association table Role 1-N Department
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint(20) not null comment 'Role ID',
  dept_id   bigint(20) not null comment 'Department ID',
  primary key(role_id, dept_id)
) engine=innodb comment = 'Role and department association table';

-- ----------------------------
-- Initialization-role and department association table
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '105');


-- ----------------------------
-- 9,User and job post association table User 1-N position
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint(20) not null comment 'User ID',
  post_id   bigint(20) not null comment 'Post ID',
  primary key (user_id, post_id)
) engine=innodb comment = 'User and post association table';

-- ----------------------------
-- Initialization-user and job post association table
-- ----------------------------
insert into sys_user_post values ('1', '1');
insert into sys_user_post values ('2', '2');


-- ----------------------------
-- 10, Operation log table
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint(20)      not null auto_increment    comment 'Log ID',
  title             varchar(50)     default ''                 comment 'Module title',
  business_type     int(2)          default 0                  comment 'Business type (0 other, 1 added, 2 modified, 3 deleted)',
  method            varchar(100)    default ''                 comment 'Method name',
  request_method    varchar(10)     default ''                 comment 'Request method',
  operator_type     int(1)          default 0                  comment 'Operation category (0 other, 1 background user, 2 mobile phone user)',
  oper_name         varchar(50)     default ''                 comment 'Operator',
  dept_name         varchar(50)     default ''                 comment 'Department name',
  oper_url          varchar(255)    default ''                 comment 'Request URL',
  oper_ip           varchar(128)    default ''                 comment 'Host address',
  oper_location     varchar(255)    default ''                 comment 'Operating location',
  oper_param        varchar(2000)   default ''                 comment 'Request parameter',
  json_result       varchar(2000)   default ''                 comment 'Return parameter',
  status            int(1)          default 0                  comment 'Operation status (0 normal, 1 abnormal)',
  error_msg         varchar(2000)   default ''                 comment 'Wrong information',
  oper_time         datetime                                   comment 'Operating time',
  primary key (oper_id)
) engine=innodb auto_increment=100 comment = 'Operation log table';


-- ----------------------------
-- 11, Dictionary type table
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint(20)      not null auto_increment    comment 'Dictionary ID',
  dict_name        varchar(100)    default ''                 comment 'Dictionary name',
  dict_type        varchar(100)    default ''                 comment 'Dictionary type',
  status           char(1)         default '0'                comment 'Status (0 normal, 1 disabled)',
  create_by        varchar(64)     default ''                 comment 'Creator',
  create_time      datetime                                   comment 'Created at',
  update_by        varchar(64)     default ''                 comment 'Updater',
  update_time      datetime                                   comment 'Updated at',
  remark           varchar(500)    default null               comment 'Remark',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = 'Dictionary type table';

insert into sys_dict_type values(1,  'User gender', 'sys_user_sex',        '0', 'admin', sysdate(), '', null, 'User gender list');
insert into sys_dict_type values(2,  'Menu status', 'sys_show_hide',       '0', 'admin', sysdate(), '', null, 'Menu status list');
insert into sys_dict_type values(3,  'System switch', 'sys_normal_disable',  '0', 'admin', sysdate(), '', null, 'System switch list');
insert into sys_dict_type values(4,  'Task status', 'sys_job_status',      '0', 'admin', sysdate(), '', null, 'Task status list');
insert into sys_dict_type values(5,  'Task grouping', 'sys_job_group',       '0', 'admin', sysdate(), '', null, 'Task grouping list');
insert into sys_dict_type values(6,  'The system condition', 'sys_yes_no',          '0', 'admin', sysdate(), '', null, 'Is the system listed');
insert into sys_dict_type values(7,  'Notification type', 'sys_notice_type',     '0', 'admin', sysdate(), '', null, 'List of notification types');
insert into sys_dict_type values(8,  'Notification status', 'sys_notice_status',   '0', 'admin', sysdate(), '', null, 'Notification status list');
insert into sys_dict_type values(9,  'Operation type', 'sys_oper_type',       '0', 'admin', sysdate(), '', null, 'List of operation types');
insert into sys_dict_type values(10, 'System status', 'sys_common_status',   '0', 'admin', sysdate(), '', null, 'Login status list');


-- ----------------------------
-- 12,Dictionary data table
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint(20)      not null auto_increment    comment 'Dictionary code',
  dict_sort        int(4)          default 0                  comment 'Dictionary sort',
  dict_label       varchar(100)    default ''                 comment 'Dictionary label',
  dict_value       varchar(100)    default ''                 comment 'Dictionary key',
  dict_type        varchar(100)    default ''                 comment 'Dictionary type',
  css_class        varchar(100)    default null               comment 'Style attributes (other style extensions)',
  list_class       varchar(100)    default null               comment 'Table style',
  is_default       char(1)         default 'N'                comment 'Whether the default (Y is, N no)',
  status           char(1)         default '0'                comment 'Status (0 normal ,1 disabled)',
  create_by        varchar(64)     default ''                 comment 'Creator',
  create_time      datetime                                   comment 'Create at',
  update_by        varchar(64)     default ''                 comment 'Updater',
  update_time      datetime                                   comment 'Updated at',
  remark           varchar(500)    default null               comment 'Remark',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = 'Dictionary data table';

insert into sys_dict_data values(1,  1,  'Male',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', sysdate(), '', null, 'Gender: Male');
insert into sys_dict_data values(2,  2,  'Female',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, 'Gender: Female');
insert into sys_dict_data values(3,  3,  'Unknown',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, 'Gender: Unknown');
insert into sys_dict_data values(4,  1,  'Display',     '0',       'sys_show_hide',       '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'Show menu');
insert into sys_dict_data values(5,  2,  'Hide',     '1',       'sys_show_hide',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Hide menu');
insert into sys_dict_data values(6,  1,  'Normal',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'Normal state');
insert into sys_dict_data values(7,  2,  'Deactivate',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Disabled state');
insert into sys_dict_data values(8,  1,  'Normal',     '0',       'sys_job_status',      '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'Normal state');
insert into sys_dict_data values(9,  2,  'Time out',     '1',       'sys_job_status',      '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Disabled state');
insert into sys_dict_data values(10, 1,  'Default',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '0', 'admin', sysdate(), '', null, 'Default group');
insert into sys_dict_data values(11, 2,  'System',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '0', 'admin', sysdate(), '', null, 'System group');
insert into sys_dict_data values(12, 1,  'Yes',       'Y',       'sys_yes_no',          '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'System default YES');
insert into sys_dict_data values(13, 2,  'No',       'N',       'sys_yes_no',          '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'System default NO');
insert into sys_dict_data values(14, 1,  'Notice',     '1',       'sys_notice_type',     '',   'warning', 'Y', '0', 'admin', sysdate(), '', null, 'Notice');
insert into sys_dict_data values(15, 2,  'Announcement',     '2',       'sys_notice_type',     '',   'success', 'N', '0', 'admin', sysdate(), '', null, 'Announcement');
insert into sys_dict_data values(16, 1,  'Normal',     '0',       'sys_notice_status',   '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'Normal status');
insert into sys_dict_data values(17, 2,  'Shut down',     '1',       'sys_notice_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Disabled');
insert into sys_dict_data values(18, 1,  'Add',     '1',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, 'New operation');
insert into sys_dict_data values(19, 2,  'Modify',     '2',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, 'Modify operation');
insert into sys_dict_data values(20, 3,  'Delete',     '3',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Delete operation');
insert into sys_dict_data values(21, 4,  'Authorization',     '4',       'sys_oper_type',       '',   'primary', 'N', '0', 'admin', sysdate(), '', null, 'Authorized operation');
insert into sys_dict_data values(22, 5,  'Export',     '5',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, 'Export operation');
insert into sys_dict_data values(23, 6,  'Import',     '6',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, 'Import operation');
insert into sys_dict_data values(24, 7,  'Retreat',     '7',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Force back operation');
insert into sys_dict_data values(25, 8,  'Generate code', '8',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, 'Generate operation');
insert into sys_dict_data values(26, 9,  'Clear data', '9',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Empty operation');
insert into sys_dict_data values(27, 1,  'Success',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'admin', sysdate(), '', null, 'Normal status');
insert into sys_dict_data values(28, 2,  'Failure',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Disabled state');


-- ----------------------------
-- 13, Parameter configuration table
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         int(5)          not null auto_increment    comment 'Parameter ID',
  config_name       varchar(100)    default ''                 comment 'Parameter name',
  config_key        varchar(100)    default ''                 comment 'Parameter key',
  config_value      varchar(500)    default ''                 comment 'Parameter value',
  config_type       char(1)         default 'N'                comment 'Built-in system (Y Yes N No)',
  create_by         varchar(64)     default ''                 comment 'Creator',
  create_time       datetime                                   comment 'Create at',
  update_by         varchar(64)     default ''                 comment 'Updater',
  update_time       datetime                                   comment 'Updated at',
  remark            varchar(500)    default null               comment 'Remark',
  primary key (config_id)
) engine=innodb auto_increment=100 comment = 'Parameter configuration table';

insert into sys_config values(1, 'Main frame page-default skin style name', 'sys.index.skinName',     'skin-blue',     'Y', 'admin', sysdate(), '', null, 'Blue skin-blue, Green skin-green, Purple skin-purple, Red skin-red, Yellow skin-yellow' );
insert into sys_config values(2, 'User management-account initial password',     'sys.user.initPassword',  '123456',        'Y', 'admin', sysdate(), '', null, 'Initialization password 123456' );
insert into sys_config values(3, 'Main frame page-sidebar theme',       'sys.index.sideTheme',    'theme-dark',    'Y', 'admin', sysdate(), '', null, 'Dark theme theme-dark, Light theme theme-light' );


-- ----------------------------
-- 14,System access table
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint(20)     not null auto_increment   comment 'Access ID',
  user_name      varchar(50)    default ''                comment 'User account',
  ipaddr         varchar(128)   default ''                comment 'Login IP address',
  login_location varchar(255)   default ''                comment 'Login location',
  browser        varchar(50)    default ''                comment 'Browser type',
  os             varchar(50)    default ''                comment 'Operating system',
  status         char(1)        default '0'               comment 'Login status (0 success 1 failure)',
  msg            varchar(255)   default ''                comment 'Prompt message',
  login_time     datetime                                 comment 'Access time',
  primary key (info_id)
) engine=innodb auto_increment=100 comment = 'System access table';


-- ----------------------------
-- 15, Timed task schedule table
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigint(20)    not null auto_increment    comment 'Task ID',
  job_name            varchar(64)   default ''                 comment 'Mission name',
  job_group           varchar(64)   default 'DEFAULT'          comment 'Task group name',
  invoke_target       varchar(500)  not null                   comment 'Call target string',
  cron_expression     varchar(255)  default ''                 comment 'Cron execution expression',
  misfire_policy      varchar(20)   default '3'                comment 'Plan execution error strategy (1 execute immediately, 2 execute once, 3 give up execution)',
  concurrent          char(1)       default '1'                comment 'Allow to execute concurrently (0 allowed, 1 prohibited)',
  status              char(1)       default '0'                comment 'Status (0 normal, 1 pause)',
  create_by           varchar(64)   default ''                 comment 'Creator',
  create_time         datetime                                 comment 'Created at',
  update_by           varchar(64)   default ''                 comment 'Updater',
  update_time         datetime                                 comment 'Updated at',
  remark              varchar(500)  default ''                 comment 'Remark',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=100 comment = 'Timed task schedule table';

insert into sys_job values(1, 'System default (no parameters)', 'DEFAULT', 'ryTask.ryNoParams',        '0/10 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(2, 'System default (with parameters)', 'DEFAULT', 'ryTask.ryParams(\'ry\')',  '0/15 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(3, 'System default (multiple parameters)', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)',  '0/20 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 16, Scheduled task scheduling log table
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigint(20)     not null auto_increment    comment 'Task log ID',
  job_name            varchar(64)    not null                   comment 'Mission name',
  job_group           varchar(64)    not null                   comment 'Task group name',
  invoke_target       varchar(500)   not null                   comment 'Call target string',
  job_message         varchar(500)                              comment 'Log information',
  status              char(1)        default '0'                comment 'Execution status (0 normal and 1 failed)',
  exception_info      varchar(2000)  default ''                 comment 'Exception information',
  create_time         datetime                                  comment 'Created at',
  primary key (job_log_id)
) engine=innodb comment = 'Scheduled task scheduling log table';


-- ----------------------------
-- 17, Notification inform table
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         int(4)          not null auto_increment    comment 'Announcement ID',
  notice_title      varchar(256)     not null                   comment 'Announcement title',
  notice_type       char(1)         not null                   comment 'Announcement type (1 notification, 2 announcement)',
  notice_content    longblob        default null               comment 'Announcement content',
  status            char(1)         default '0'                comment 'Announcement status (0 normal 1 closed)',
  create_by         varchar(64)     default ''                 comment 'Creator',
  create_time       datetime                                   comment 'Created at',
  update_by         varchar(64)     default ''                 comment 'Updater',
  update_time       datetime                                   comment 'Update at',
  remark            varchar(255)    default null               comment 'Remark',
  primary key (notice_id)
) engine=innodb auto_increment=10 comment = 'Notification inform table';

-- ----------------------------
-- Initialization-notification information table
-- ----------------------------
insert into sys_notice values('1', 'Reminder: 26-12-2020 If the new version is released', '2', 'Release note', '0', 'admin', sysdate(), '', null, 'Administrator');
insert into sys_notice values('2', 'Maintenance notice: 25-12-2020 If the system is maintained in the early morning', '1', 'Maintenance content',   '0', 'admin', sysdate(), '', null, 'Administrator');


-- ----------------------------
-- 18, Code generation business table
-- ----------------------------
drop table if exists gen_table;
create table gen_table (
  table_id          bigint(20)      not null auto_increment    comment 'Numbering',
  table_name        varchar(200)    default ''                 comment 'Table name',
  table_comment     varchar(500)    default ''                 comment 'Table description',
  sub_table_name    varchar(64)     default null               comment 'Table name of the associated child table',
  sub_table_fk_name varchar(64)     default null               comment 'The name of the foreign key associated with the child table',
  class_name        varchar(100)    default ''                 comment 'Entity class name',
  tpl_category      varchar(200)    default 'crud'             comment 'The template used (crud - single table operation, tree - tree table operation)',
  package_name      varchar(100)                               comment 'Generate package path',
  module_name       varchar(30)                                comment 'Generate module name',
  business_name     varchar(30)                                comment 'Generate business name',
  function_name     varchar(50)                                comment 'Generate function name',
  function_author   varchar(50)                                comment 'Generate function author',
  gen_type          char(1)         default '0'                comment 'Code generation method (0 zip compressed package, 1 custom path)',
  gen_path          varchar(200)    default '/'                comment 'Build path (do not fill in the default project path)',
  options           varchar(1000)                              comment 'Other build options',
  create_by         varchar(64)     default ''                 comment 'Creator',
  create_time 	    datetime                                   comment 'Created at',
  update_by         varchar(64)     default ''                 comment 'Updater',
  update_time       datetime                                   comment 'Updated at',
  remark            varchar(500)    default null               comment 'Remark',
  primary key (table_id)
) engine=innodb auto_increment=1 comment = 'Code generation business table';


-- ----------------------------
-- 19, Code generation business fields table
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigint(20)      not null auto_increment    comment 'Numbering',
  table_id          varchar(64)                                comment 'Attribute table number',
  column_name       varchar(200)                               comment 'Column name',
  column_comment    varchar(500)                               comment 'Column description',
  column_type       varchar(100)                               comment 'Column type',
  java_type         varchar(500)                               comment 'JAVA type',
  java_field        varchar(200)                               comment 'JAVA field name',
  is_pk             char(1)                                    comment 'Whether the primary key (1 yes)',
  is_increment      char(1)                                    comment 'Whether to auto-increase (1 yes)',
  is_required       char(1)                                    comment 'Is it required (1 yes)',
  is_insert         char(1)                                    comment 'Is it an insert field (1 yes)',
  is_edit           char(1)                                    comment 'Whether to edit the field (1 yes)',
  is_list           char(1)                                    comment 'Whether the list field (1 is)',
  is_query          char(1)                                    comment 'Whether to query the field (1 is)',
  query_type        varchar(200)    default 'EQ'               comment 'Query method (equal to, not equal to, greater than, less than, range)',
  html_type         varchar(200)                               comment 'Display type (text box, text field, drop-down box, check box, radio button, date control)',
  dict_type         varchar(200)    default ''                 comment 'Dictionary type',
  sort              int                                        comment 'Sort position',
  create_by         varchar(64)     default ''                 comment 'Creator',
  create_time 	    datetime                                   comment 'Created at',
  update_by         varchar(64)     default ''                 comment 'Updater',
  update_time       datetime                                   comment 'Updated at',
  primary key (column_id)
) engine=innodb auto_increment=1 comment = 'Code generation business fields table';