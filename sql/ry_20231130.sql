-- ----------------------------
-- 1、Department Table
-- ----------------------------
drop table if exists sys_dept;
create table sys_dept (
  dept_id           bigint(20)      not null auto_increment    comment 'Departmentid',
  parent_id         bigint(20)      default 0                  comment 'The Father Departmentid',
  ancestors         varchar(50)     default ''                 comment 'List of ancestors',
  dept_name         varchar(30)     default ''                 comment 'Name of department',
  order_num         int(4)          default 0                  comment 'Show the order.',
  leader            varchar(20)     default null               comment 'Responsible',
  phone             varchar(11)     default null               comment 'Contact the phone.',
  email             varchar(50)     default null               comment 'The mailbox',
  status            char(1)         default '0'                comment 'state of department.（0Normal 1stopped）',
  del_flag          char(1)         default '0'                comment 'Remove the label.（0representations exist. 2The representative removed.）',
  create_by         varchar(64)     default ''                 comment 'The Creator',
  create_time 	    datetime                                   comment 'Creating time.',
  update_by         varchar(64)     default ''                 comment 'Updated',
  update_time       datetime                                   comment 'Updated time',
  primary key (dept_id)
) engine=innodb auto_increment=200 comment = 'Department Table';

-- ----------------------------
-- Initiation-Department Table Data
-- ----------------------------
insert into sys_dept values(100,  0,   '0',          'If the technology',   0, 'If I', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(101,  100, '0,100',      'Shenzhen General Company', 1, 'If I', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(102,  100, '0,100',      'Long Sand Company', 2, 'If I', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(103,  101, '0,100,101',  'Department of R&D',   1, 'If I', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(104,  101, '0,100,101',  'The market department',   2, 'If I', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(105,  101, '0,100,101',  'Department of Testing',   3, 'If I', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(106,  101, '0,100,101',  'The Financial Department',   4, 'If I', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(107,  101, '0,100,101',  'Department of Operations',   5, 'If I', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(108,  102, '0,100,102',  'The market department',   1, 'If I', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);
insert into sys_dept values(109,  102, '0,100,102',  'The Financial Department',   2, 'If I', '15888888888', 'ry@qq.com', '0', '0', 'admin', sysdate(), '', null);


-- ----------------------------
-- 2、User Information Table
-- ----------------------------
drop table if exists sys_user;
create table sys_user (
  user_id           bigint(20)      not null auto_increment    comment 'UsersID',
  dept_id           bigint(20)      default null               comment 'DepartmentID',
  user_name         varchar(30)     not null                   comment 'User Account',
  nick_name         varchar(30)     not null                   comment 'Users say',
  user_type         varchar(2)      default '00'               comment 'Type of User（00System Users）',
  email             varchar(50)     default ''                 comment 'User mailbox',
  phonenumber       varchar(11)     default ''                 comment 'The phone number.',
  sex               char(1)         default '0'                comment 'User gender（0The Man 1The Woman 2Unknown）',
  avatar            varchar(100)    default ''                 comment 'The head address.',
  password          varchar(100)    default ''                 comment 'The code',
  status            char(1)         default '0'                comment 'Status of Account（0Normal 1stopped）',
  del_flag          char(1)         default '0'                comment 'Remove the label.（0representations exist. 2The representative removed.）',
  login_ip          varchar(128)    default ''                 comment 'Last logged in.IP',
  login_date        datetime                                   comment 'Last entry time.',
  create_by         varchar(64)     default ''                 comment 'The Creator',
  create_time       datetime                                   comment 'Creating time.',
  update_by         varchar(64)     default ''                 comment 'Updated',
  update_time       datetime                                   comment 'Updated time',
  remark            varchar(500)    default null               comment 'Note to',
  primary key (user_id)
) engine=innodb auto_increment=100 comment = 'User Information Table';

-- ----------------------------
-- Initiation-User Information Table Data
-- ----------------------------
insert into sys_user values(1,  103, 'admin', 'If I', '00', 'ry@163.com', '15888888888', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, 'Managers');
insert into sys_user values(2,  105, 'ry',    'If I', '00', 'ry@qq.com',  '15666666666', '1', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), 'admin', sysdate(), '', null, 'Testers');


-- ----------------------------
-- 3、Job Information Table
-- ----------------------------
drop table if exists sys_post;
create table sys_post
(
  post_id       bigint(20)      not null auto_increment    comment 'The jobID',
  post_code     varchar(64)     not null                   comment 'Job Code',
  post_name     varchar(50)     not null                   comment 'Employment Name',
  post_sort     int(4)          not null                   comment 'Show the order.',
  status        char(1)         not null                   comment 'state of（0Normal 1stopped）',
  create_by     varchar(64)     default ''                 comment 'The Creator',
  create_time   datetime                                   comment 'Creating time.',
  update_by     varchar(64)     default ''			       comment 'Updated',
  update_time   datetime                                   comment 'Updated time',
  remark        varchar(500)    default null               comment 'Note to',
  primary key (post_id)
) engine=innodb comment = 'Job Information Table';

-- ----------------------------
-- Initiation-Job Information Table Data
-- ----------------------------
insert into sys_post values(1, 'ceo',  'The chairman',    1, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(2, 'se',   'Project Manager',  2, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(3, 'hr',   'Human Resources',  3, '0', 'admin', sysdate(), '', null, '');
insert into sys_post values(4, 'user', 'Ordinary employees',  4, '0', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 4、Character Information Table
-- ----------------------------
drop table if exists sys_role;
create table sys_role (
  role_id              bigint(20)      not null auto_increment    comment 'The roleID',
  role_name            varchar(30)     not null                   comment 'The role name.',
  role_key             varchar(100)    not null                   comment 'Character authorization string',
  role_sort            int(4)          not null                   comment 'Show the order.',
  data_scope           char(1)         default '1'                comment 'scope of data（1：All data permits 2：Adjusted data authority 3：Data authority of this department 4：This department and the following data authorities）',
  menu_check_strictly  tinyint(1)      default 1                  comment 'Menu tree options are linked to display',
  dept_check_strictly  tinyint(1)      default 1                  comment 'Department Tree Options Related to Show',
  status               char(1)         not null                   comment 'Status of role.（0Normal 1stopped）',
  del_flag             char(1)         default '0'                comment 'Remove the label.（0representations exist. 2The representative removed.）',
  create_by            varchar(64)     default ''                 comment 'The Creator',
  create_time          datetime                                   comment 'Creating time.',
  update_by            varchar(64)     default ''                 comment 'Updated',
  update_time          datetime                                   comment 'Updated time',
  remark               varchar(500)    default null               comment 'Note to',
  primary key (role_id)
) engine=innodb auto_increment=100 comment = 'Character Information Table';

-- ----------------------------
-- Initiation-Character Information Table Data
-- ----------------------------
insert into sys_role values('1', 'Supermanagers',  'admin',  1, 1, 1, 1, '0', '0', 'admin', sysdate(), '', null, 'Supermanagers');
insert into sys_role values('2', 'The ordinary role.',    'common', 2, 2, 1, 1, '0', '0', 'admin', sysdate(), '', null, 'The ordinary role.');


-- ----------------------------
-- 5、The menu permits.
-- ----------------------------
drop table if exists sys_menu;
create table sys_menu (
  menu_id           bigint(20)      not null auto_increment    comment 'The menuID',
  menu_name         varchar(50)     not null                   comment 'Name of Menu',
  parent_id         bigint(20)      default 0                  comment 'The Dad's MenuID',
  order_num         int(4)          default 0                  comment 'Show the order.',
  path              varchar(200)    default ''                 comment 'The routing address.',
  component         varchar(255)    default null               comment 'The component route',
  query             varchar(255)    default null               comment 'The routing parameters.',
  is_frame          int(1)          default 1                  comment 'The external chain.（0is 1No）',
  is_cache          int(1)          default 0                  comment 'Is it cache（0cache 1not cache.）',
  menu_type         char(1)         default ''                 comment 'Type of Menu（MThe catalogue CThe menu FThe button）',
  visible           char(1)         default 0                  comment 'The menu state.（0Showing 1Hidden）',
  status            char(1)         default 0                  comment 'The menu state.（0Normal 1stopped）',
  perms             varchar(100)    default null               comment 'Identification of authority',
  icon              varchar(100)    default '#'                comment 'Menu Symbols',
  create_by         varchar(64)     default ''                 comment 'The Creator',
  create_time       datetime                                   comment 'Creating time.',
  update_by         varchar(64)     default ''                 comment 'Updated',
  update_time       datetime                                   comment 'Updated time',
  remark            varchar(500)    default ''                 comment 'Note to',
  primary key (menu_id)
) engine=innodb auto_increment=2000 comment = 'The menu permits.';

-- ----------------------------
-- Initiation-Menu Information Table Data
-- ----------------------------
-- The Level Menu
insert into sys_menu values('1', 'system management', '0', '1', 'system',           null, '', 1, 0, 'M', '0', '0', '', 'system',   'admin', sysdate(), '', null, 'System Management Catalogue');
insert into sys_menu values('2', 'System Monitoring', '0', '2', 'monitor',          null, '', 1, 0, 'M', '0', '0', '', 'monitor',  'admin', sysdate(), '', null, 'System monitoring directory');
insert into sys_menu values('3', 'System tools', '0', '3', 'tool',             null, '', 1, 0, 'M', '0', '0', '', 'tool',     'admin', sysdate(), '', null, 'System Tool Catalogue');
insert into sys_menu values('4', 'If the official', '0', '4', 'http://ruoyi.vip', null, '', 0, 0, 'M', '0', '0', '', 'guide',    'admin', sysdate(), '', null, 'By the official address.');
-- Secondary menu
insert into sys_menu values('100',  'User Management', '1',   '1', 'user',       'system/user/index',        '', 1, 0, 'C', '0', '0', 'system:user:list',        'user',          'admin', sysdate(), '', null, 'User Management Menu');
insert into sys_menu values('101',  'Role Management', '1',   '2', 'role',       'system/role/index',        '', 1, 0, 'C', '0', '0', 'system:role:list',        'peoples',       'admin', sysdate(), '', null, 'Character Management Menu');
insert into sys_menu values('102',  'Management of Menu', '1',   '3', 'menu',       'system/menu/index',        '', 1, 0, 'C', '0', '0', 'system:menu:list',        'tree-table',    'admin', sysdate(), '', null, 'Menu Management Menu');
insert into sys_menu values('103',  'Department Management', '1',   '4', 'dept',       'system/dept/index',        '', 1, 0, 'C', '0', '0', 'system:dept:list',        'tree',          'admin', sysdate(), '', null, 'Department Management Menu');
insert into sys_menu values('104',  'job management', '1',   '5', 'post',       'system/post/index',        '', 1, 0, 'C', '0', '0', 'system:post:list',        'post',          'admin', sysdate(), '', null, 'Job Management Menu');
insert into sys_menu values('105',  'Dictionary management', '1',   '6', 'dict',       'system/dict/index',        '', 1, 0, 'C', '0', '0', 'system:dict:list',        'dict',          'admin', sysdate(), '', null, 'Dictionary Management Menu');
insert into sys_menu values('106',  'The parameter set.', '1',   '7', 'config',     'system/config/index',      '', 1, 0, 'C', '0', '0', 'system:config:list',      'edit',          'admin', sysdate(), '', null, 'Set up the menu.');
insert into sys_menu values('107',  'Notification announcement', '1',   '8', 'notice',     'system/notice/index',      '', 1, 0, 'C', '0', '0', 'system:notice:list',      'message',       'admin', sysdate(), '', null, 'Notification of the announcement menu');
insert into sys_menu values('108',  'The Diary Management', '1',   '9', 'log',        '',                         '', 1, 0, 'M', '0', '0', '',                        'log',           'admin', sysdate(), '', null, 'Diary Management Menu');
insert into sys_menu values('109',  'Online users', '2',   '1', 'online',     'monitor/online/index',     '', 1, 0, 'C', '0', '0', 'monitor:online:list',     'online',        'admin', sysdate(), '', null, 'Online User Menu');
insert into sys_menu values('110',  'timely task.', '2',   '2', 'job',        'monitor/job/index',        '', 1, 0, 'C', '0', '0', 'monitor:job:list',        'job',           'admin', sysdate(), '', null, 'timely task menu.');
insert into sys_menu values('111',  'Data Monitoring', '2',   '3', 'druid',      'monitor/druid/index',      '', 1, 0, 'C', '0', '0', 'monitor:druid:list',      'druid',         'admin', sysdate(), '', null, 'Data Monitoring Menu');
insert into sys_menu values('112',  'Service Monitoring', '2',   '4', 'server',     'monitor/server/index',     '', 1, 0, 'C', '0', '0', 'monitor:server:list',     'server',        'admin', sysdate(), '', null, 'Service Monitoring Menu');
insert into sys_menu values('113',  'Cache monitoring', '2',   '5', 'cache',      'monitor/cache/index',      '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis',         'admin', sysdate(), '', null, 'Cache Monitoring Menu');
insert into sys_menu values('114',  'Cache List', '2',   '6', 'cacheList',  'monitor/cache/list',       '', 1, 0, 'C', '0', '0', 'monitor:cache:list',      'redis-list',    'admin', sysdate(), '', null, 'Cache List Menu');
insert into sys_menu values('115',  'Forms are built.', '3',   '1', 'build',      'tool/build/index',         '', 1, 0, 'C', '0', '0', 'tool:build:list',         'build',         'admin', sysdate(), '', null, 'Forms to build menu.');
insert into sys_menu values('116',  'Code is generated.', '3',   '2', 'gen',        'tool/gen/index',           '', 1, 0, 'C', '0', '0', 'tool:gen:list',           'code',          'admin', sysdate(), '', null, 'Create a menu code.');
insert into sys_menu values('117',  'system interface', '3',   '3', 'swagger',    'tool/swagger/index',       '', 1, 0, 'C', '0', '0', 'tool:swagger:list',       'swagger',       'admin', sysdate(), '', null, 'System Interface Menu');
-- The Third Level Menu
insert into sys_menu values('500',  'Operating Diaries', '108', '1', 'operlog',    'monitor/operlog/index',    '', 1, 0, 'C', '0', '0', 'monitor:operlog:list',    'form',          'admin', sysdate(), '', null, 'Operating Diary Menu');
insert into sys_menu values('501',  'Registered logs.', '108', '2', 'logininfor', 'monitor/logininfor/index', '', 1, 0, 'C', '0', '0', 'monitor:logininfor:list', 'logininfor',    'admin', sysdate(), '', null, 'Register the log menu.');
-- User Management Button
insert into sys_menu values('1000', 'User Question', '100', '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1001', 'Added Users', '100', '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1002', 'User Modification', '100', '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1003', 'Users removed.', '100', '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1004', 'Users Export', '100', '5',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:export',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1005', 'User Introduction', '100', '6',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:import',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1006', 'Repeat the password.', '100', '7',  '', '', '', 1, 0, 'F', '0', '0', 'system:user:resetPwd',       '#', 'admin', sysdate(), '', null, '');
-- The role management button.
insert into sys_menu values('1007', 'The role query.', '101', '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1008', 'The role added.', '101', '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1009', 'Character Modification', '101', '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1010', 'The role is removed.', '101', '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1011', 'The role exports.', '101', '5',  '', '', '', 1, 0, 'F', '0', '0', 'system:role:export',         '#', 'admin', sysdate(), '', null, '');
-- Menu Management Button
insert into sys_menu values('1012', 'The menu query', '102', '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:menu:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1013', 'The menu is added.', '102', '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:menu:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1014', 'Modified Menu', '102', '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:menu:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1015', 'Remove the menu.', '102', '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:menu:remove',         '#', 'admin', sysdate(), '', null, '');
-- The Department Management Button
insert into sys_menu values('1016', 'Department inquiries', '103', '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:dept:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1017', 'Additional Department', '103', '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:dept:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1018', 'Department Modification', '103', '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:dept:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1019', 'department removed.', '103', '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:dept:remove',         '#', 'admin', sysdate(), '', null, '');
-- The job management button.
insert into sys_menu values('1020', 'job query', '104', '1',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1021', 'Additional jobs', '104', '2',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1022', 'Change of position', '104', '3',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1023', 'The job is removed.', '104', '4',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1024', 'Export of jobs.', '104', '5',  '', '', '', 1, 0, 'F', '0', '0', 'system:post:export',         '#', 'admin', sysdate(), '', null, '');
-- The dictionary management button.
insert into sys_menu values('1025', 'Dictionary Questions', '105', '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1026', 'The dictionary added.', '105', '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1027', 'The dictionary is modified.', '105', '3', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1028', 'The dictionary is removed.', '105', '4', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1029', 'Dictionary is exported.', '105', '5', '#', '', '', 1, 0, 'F', '0', '0', 'system:dict:export',         '#', 'admin', sysdate(), '', null, '');
-- Set the parameter button.
insert into sys_menu values('1030', 'Question of parameters.', '106', '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1031', 'Additional parameters', '106', '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1032', 'Modification of parameters', '106', '3', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1033', 'Remove the parameter.', '106', '4', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:remove',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1034', 'The Parameters Export', '106', '5', '#', '', '', 1, 0, 'F', '0', '0', 'system:config:export',       '#', 'admin', sysdate(), '', null, '');
-- Notification button.
insert into sys_menu values('1035', 'Announcement of Question', '107', '1', '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:query',        '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1036', 'Announcement Added', '107', '2', '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:add',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1037', 'Advertisement Modified', '107', '3', '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:edit',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1038', 'announcement removed.', '107', '4', '#', '', '', 1, 0, 'F', '0', '0', 'system:notice:remove',       '#', 'admin', sysdate(), '', null, '');
-- Use the log button.
insert into sys_menu values('1039', 'Operating inquiries', '500', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:query',      '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1040', 'Operations removed.', '500', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:remove',     '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1041', 'The Diary Export', '500', '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:operlog:export',     '#', 'admin', sysdate(), '', null, '');
-- Enter the log button.
insert into sys_menu values('1042', 'Registration in Question.', '501', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:query',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1043', 'Log in to remove.', '501', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:remove',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1044', 'The Diary Export', '501', '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:export',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1045', 'Unlocking the account.', '501', '4', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:logininfor:unlock',  '#', 'admin', sysdate(), '', null, '');
-- Online user button.
insert into sys_menu values('1046', 'Online inquiries', '109', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:query',       '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1047', 'The mass resignation.', '109', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:batchLogout', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1048', 'A single retreat.', '109', '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:online:forceLogout', '#', 'admin', sysdate(), '', null, '');
-- Time task button.
insert into sys_menu values('1049', 'Question of task.', '110', '1', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:query',          '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1050', 'Additional tasks', '110', '2', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:add',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1051', 'Amendment of task', '110', '3', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:edit',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1052', 'Delete the task.', '110', '4', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:remove',         '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1053', 'Modification of state', '110', '5', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:changeStatus',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1054', 'Exporting the task.', '110', '6', '#', '', '', 1, 0, 'F', '0', '0', 'monitor:job:export',         '#', 'admin', sysdate(), '', null, '');
-- Create the code button.
insert into sys_menu values('1055', 'Create the query.', '116', '1', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:query',             '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1056', 'produced modifications.', '116', '2', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:edit',              '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1057', 'produced removal.', '116', '3', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:remove',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1058', 'Introduction of Code', '116', '4', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:import',            '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1059', 'Preview of code.', '116', '5', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:preview',           '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('1060', 'Create the code.', '116', '6', '#', '', '', 1, 0, 'F', '0', '0', 'tool:gen:code',              '#', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 6、User and Role Relationships  UsersN-1The role
-- ----------------------------
drop table if exists sys_user_role;
create table sys_user_role (
  user_id   bigint(20) not null comment 'UsersID',
  role_id   bigint(20) not null comment 'The roleID',
  primary key(user_id, role_id)
) engine=innodb comment = 'User and Role Relationships';

-- ----------------------------
-- Initiation-User and Character Related Table Data
-- ----------------------------
insert into sys_user_role values ('1', '1');
insert into sys_user_role values ('2', '2');


-- ----------------------------
-- 7、Character and Menu Relationships  The role1-NThe menu
-- ----------------------------
drop table if exists sys_role_menu;
create table sys_role_menu (
  role_id   bigint(20) not null comment 'The roleID',
  menu_id   bigint(20) not null comment 'The menuID',
  primary key(role_id, menu_id)
) engine=innodb comment = 'Character and Menu Relationships';

-- ----------------------------
-- Initiation-Character and Menu Related Table Data
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
insert into sys_role_menu values ('2', '117');
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
-- 8、Role and Department Relationships  The role1-NDepartment
-- ----------------------------
drop table if exists sys_role_dept;
create table sys_role_dept (
  role_id   bigint(20) not null comment 'The roleID',
  dept_id   bigint(20) not null comment 'DepartmentID',
  primary key(role_id, dept_id)
) engine=innodb comment = 'Role and Department Relationships';

-- ----------------------------
-- Initiation-Role and department related tables data
-- ----------------------------
insert into sys_role_dept values ('2', '100');
insert into sys_role_dept values ('2', '101');
insert into sys_role_dept values ('2', '105');


-- ----------------------------
-- 9、User and Job Relationships  Users1-NThe job
-- ----------------------------
drop table if exists sys_user_post;
create table sys_user_post
(
  user_id   bigint(20) not null comment 'UsersID',
  post_id   bigint(20) not null comment 'The jobID',
  primary key (user_id, post_id)
) engine=innodb comment = 'User and Job Relationships';

-- ----------------------------
-- Initiation-User and Job Relationship Data
-- ----------------------------
insert into sys_user_post values ('1', '1');
insert into sys_user_post values ('2', '2');


-- ----------------------------
-- 10、Operating log records.
-- ----------------------------
drop table if exists sys_oper_log;
create table sys_oper_log (
  oper_id           bigint(20)      not null auto_increment    comment 'The Diary Key.',
  title             varchar(50)     default ''                 comment 'Title of the module',
  business_type     int(2)          default 0                  comment 'Type of business（0Other 1Added 2Modified 3removed）',
  method            varchar(100)    default ''                 comment 'Method Name',
  request_method    varchar(10)     default ''                 comment 'Method of request',
  operator_type     int(1)          default 0                  comment 'Operating categories（0Other 1User of the Background 2Mobile User）',
  oper_name         varchar(50)     default ''                 comment 'Operating staff',
  dept_name         varchar(50)     default ''                 comment 'Name of department',
  oper_url          varchar(255)    default ''                 comment 'requestedURL',
  oper_ip           varchar(128)    default ''                 comment 'The host address.',
  oper_location     varchar(255)    default ''                 comment 'Location of operation.',
  oper_param        varchar(2000)   default ''                 comment 'Demand of Parameters',
  json_result       varchar(2000)   default ''                 comment 'Return to Parameters',
  status            int(1)          default 0                  comment 'Operating state（0Normal 1Unusual）',
  error_msg         varchar(2000)   default ''                 comment 'The wrong news.',
  oper_time         datetime                                   comment 'Operating time.',
  cost_time         bigint(20)      default 0                  comment 'spending time.',
  primary key (oper_id),
  key idx_sys_oper_log_bt (business_type),
  key idx_sys_oper_log_s  (status),
  key idx_sys_oper_log_ot (oper_time)
) engine=innodb auto_increment=100 comment = 'Operating log records.';


-- ----------------------------
-- 11、Type of dictionary
-- ----------------------------
drop table if exists sys_dict_type;
create table sys_dict_type
(
  dict_id          bigint(20)      not null auto_increment    comment 'The dictionary key.',
  dict_name        varchar(100)    default ''                 comment 'Name of Dictionary',
  dict_type        varchar(100)    default ''                 comment 'Type of dictionary',
  status           char(1)         default '0'                comment 'state of（0Normal 1stopped）',
  create_by        varchar(64)     default ''                 comment 'The Creator',
  create_time      datetime                                   comment 'Creating time.',
  update_by        varchar(64)     default ''                 comment 'Updated',
  update_time      datetime                                   comment 'Updated time',
  remark           varchar(500)    default null               comment 'Note to',
  primary key (dict_id),
  unique (dict_type)
) engine=innodb auto_increment=100 comment = 'Type of dictionary';

insert into sys_dict_type values(1,  'User gender', 'sys_user_sex',        '0', 'admin', sysdate(), '', null, 'User gender list');
insert into sys_dict_type values(2,  'The menu state.', 'sys_show_hide',       '0', 'admin', sysdate(), '', null, 'Menu Status List');
insert into sys_dict_type values(3,  'the system switch.', 'sys_normal_disable',  '0', 'admin', sysdate(), '', null, 'List of system connections');
insert into sys_dict_type values(4,  'Status of task.', 'sys_job_status',      '0', 'admin', sysdate(), '', null, 'List of task status');
insert into sys_dict_type values(5,  'The task group.', 'sys_job_group',       '0', 'admin', sysdate(), '', null, 'List of tasks');
insert into sys_dict_type values(6,  'The system is', 'sys_yes_no',          '0', 'admin', sysdate(), '', null, 'Is the system listed');
insert into sys_dict_type values(7,  'Type of Notification', 'sys_notice_type',     '0', 'admin', sysdate(), '', null, 'List of Type Notification');
insert into sys_dict_type values(8,  'state of notification.', 'sys_notice_status',   '0', 'admin', sysdate(), '', null, 'Notification Status List');
insert into sys_dict_type values(9,  'Types of operation', 'sys_oper_type',       '0', 'admin', sysdate(), '', null, 'Operating Types List');
insert into sys_dict_type values(10, 'The system state.', 'sys_common_status',   '0', 'admin', sysdate(), '', null, 'Registered status list');


-- ----------------------------
-- 12、dictionary data tables
-- ----------------------------
drop table if exists sys_dict_data;
create table sys_dict_data
(
  dict_code        bigint(20)      not null auto_increment    comment 'The dictionary code',
  dict_sort        int(4)          default 0                  comment 'dictionary order.',
  dict_label       varchar(100)    default ''                 comment 'dictionary labels',
  dict_value       varchar(100)    default ''                 comment 'Keyword Value',
  dict_type        varchar(100)    default ''                 comment 'Type of dictionary',
  css_class        varchar(100)    default null               comment 'Style characteristics（Other extension styles.）',
  list_class       varchar(100)    default null               comment 'Returns to style.',
  is_default       char(1)         default 'N'                comment 'Is it presumed（Yis NNo）',
  status           char(1)         default '0'                comment 'state of（0Normal 1stopped）',
  create_by        varchar(64)     default ''                 comment 'The Creator',
  create_time      datetime                                   comment 'Creating time.',
  update_by        varchar(64)     default ''                 comment 'Updated',
  update_time      datetime                                   comment 'Updated time',
  remark           varchar(500)    default null               comment 'Note to',
  primary key (dict_code)
) engine=innodb auto_increment=100 comment = 'dictionary data tables';

insert into sys_dict_data values(1,  1,  'The Man',       '0',       'sys_user_sex',        '',   '',        'Y', '0', 'admin', sysdate(), '', null, 'The sex man.');
insert into sys_dict_data values(2,  2,  'The Woman',       '1',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, 'gender female');
insert into sys_dict_data values(3,  3,  'Unknown',     '2',       'sys_user_sex',        '',   '',        'N', '0', 'admin', sysdate(), '', null, 'Gender Unknown');
insert into sys_dict_data values(4,  1,  'Showing',     '0',       'sys_show_hide',       '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'Showing the menu.');
insert into sys_dict_data values(5,  2,  'Hidden',     '1',       'sys_show_hide',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Hidden Menu');
insert into sys_dict_data values(6,  1,  'Normal',     '0',       'sys_normal_disable',  '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'in normal state.');
insert into sys_dict_data values(7,  2,  'stopped',     '1',       'sys_normal_disable',  '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Stopped state');
insert into sys_dict_data values(8,  1,  'Normal',     '0',       'sys_job_status',      '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'in normal state.');
insert into sys_dict_data values(9,  2,  'suspended',     '1',       'sys_job_status',      '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Stopped state');
insert into sys_dict_data values(10, 1,  'presumed',     'DEFAULT', 'sys_job_group',       '',   '',        'Y', '0', 'admin', sysdate(), '', null, 'The default group.');
insert into sys_dict_data values(11, 2,  'The system',     'SYSTEM',  'sys_job_group',       '',   '',        'N', '0', 'admin', sysdate(), '', null, 'The system group.');
insert into sys_dict_data values(12, 1,  'is',       'Y',       'sys_yes_no',          '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'The system is supposed to');
insert into sys_dict_data values(13, 2,  'No',       'N',       'sys_yes_no',          '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'The system does not.');
insert into sys_dict_data values(14, 1,  'Notification',     '1',       'sys_notice_type',     '',   'warning', 'Y', '0', 'admin', sysdate(), '', null, 'Notification');
insert into sys_dict_data values(15, 2,  'announcement',     '2',       'sys_notice_type',     '',   'success', 'N', '0', 'admin', sysdate(), '', null, 'announcement');
insert into sys_dict_data values(16, 1,  'Normal',     '0',       'sys_notice_status',   '',   'primary', 'Y', '0', 'admin', sysdate(), '', null, 'in normal state.');
insert into sys_dict_data values(17, 2,  'closed',     '1',       'sys_notice_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Closed state.');
insert into sys_dict_data values(18, 99, 'Other',     '0',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, 'Other Operations');
insert into sys_dict_data values(19, 1,  'Added',     '1',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, 'Additional Operations');
insert into sys_dict_data values(20, 2,  'Modified',     '2',       'sys_oper_type',       '',   'info',    'N', '0', 'admin', sysdate(), '', null, 'Modifying Operations');
insert into sys_dict_data values(21, 3,  'removed',     '3',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Remove the operation.');
insert into sys_dict_data values(22, 4,  'Authorized',     '4',       'sys_oper_type',       '',   'primary', 'N', '0', 'admin', sysdate(), '', null, 'Authorized operations');
insert into sys_dict_data values(23, 5,  'Exported',     '5',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, 'Export Operations');
insert into sys_dict_data values(24, 6,  'Introduction',     '6',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, 'Introduction Operations');
insert into sys_dict_data values(25, 7,  'withdrawal',     '7',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'forced operation.');
insert into sys_dict_data values(26, 8,  'Create the code.', '8',       'sys_oper_type',       '',   'warning', 'N', '0', 'admin', sysdate(), '', null, 'Create operations.');
insert into sys_dict_data values(27, 9,  'The empty data', '9',       'sys_oper_type',       '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Empty operations');
insert into sys_dict_data values(28, 1,  'Successful',     '0',       'sys_common_status',   '',   'primary', 'N', '0', 'admin', sysdate(), '', null, 'in normal state.');
insert into sys_dict_data values(29, 2,  'Failure',     '1',       'sys_common_status',   '',   'danger',  'N', '0', 'admin', sysdate(), '', null, 'Stopped state');


-- ----------------------------
-- 13、The parameter configuration table.
-- ----------------------------
drop table if exists sys_config;
create table sys_config (
  config_id         int(5)          not null auto_increment    comment 'The parameter key.',
  config_name       varchar(100)    default ''                 comment 'Name of Parameters',
  config_key        varchar(100)    default ''                 comment 'The parameter name.',
  config_value      varchar(500)    default ''                 comment 'The parameter value.',
  config_type       char(1)         default 'N'                comment 'The system integrated.（Yis NNo）',
  create_by         varchar(64)     default ''                 comment 'The Creator',
  create_time       datetime                                   comment 'Creating time.',
  update_by         varchar(64)     default ''                 comment 'Updated',
  update_time       datetime                                   comment 'Updated time',
  remark            varchar(500)    default null               comment 'Note to',
  primary key (config_id)
) engine=innodb auto_increment=100 comment = 'The parameter configuration table.';

insert into sys_config values(1, 'Main Framework Page-Default skin style name',     'sys.index.skinName',            'skin-blue',     'Y', 'admin', sysdate(), '', null, 'the blue skin-blue、the green skin-green、the purple skin-purple、Red is skin-red、Yellow skin-yellow' );
insert into sys_config values(2, 'User Management-The initial account code.',         'sys.user.initPassword',         '123456',        'Y', 'admin', sysdate(), '', null, 'Initial Password 123456' );
insert into sys_config values(3, 'Main Framework Page-The side bar theme.',           'sys.index.sideTheme',           'theme-dark',    'Y', 'admin', sysdate(), '', null, 'The dark topic.theme-dark，The light theme.theme-light' );
insert into sys_config values(4, 'Account for self-help-Confirmation of code.',           'sys.account.captchaEnabled',    'true',          'Y', 'admin', sysdate(), '', null, 'Open the verification code function.（trueopened，falseclosed）');
insert into sys_config values(5, 'Account for self-help-Open the user registration function.', 'sys.account.registerUser',      'false',         'Y', 'admin', sysdate(), '', null, 'Open the registration user function.（trueopened，falseclosed）');
insert into sys_config values(6, 'User Registration-The black list list',           'sys.login.blackIPList',         '',              'Y', 'admin', sysdate(), '', null, 'Set up logging.IPBlack list restrictions，A number of matchings;Separated，Support of matching（*Combined、The network）');


-- ----------------------------
-- 14、System Access Records
-- ----------------------------
drop table if exists sys_logininfor;
create table sys_logininfor (
  info_id        bigint(20)     not null auto_increment   comment 'Visit toID',
  user_name      varchar(50)    default ''                comment 'User Account',
  ipaddr         varchar(128)   default ''                comment 'RegisteredIPAddressed',
  login_location varchar(255)   default ''                comment 'place of registration.',
  browser        varchar(50)    default ''                comment 'Type of browser.',
  os             varchar(50)    default ''                comment 'Operating system',
  status         char(1)        default '0'               comment 'Registered state（0Successful 1Failure）',
  msg            varchar(255)   default ''                comment 'suggested news',
  login_time     datetime                                 comment 'Time of Visit',
  primary key (info_id),
  key idx_sys_logininfor_s  (status),
  key idx_sys_logininfor_lt (login_time)
) engine=innodb auto_increment=100 comment = 'System Access Records';


-- ----------------------------
-- 15、timely tasks.
-- ----------------------------
drop table if exists sys_job;
create table sys_job (
  job_id              bigint(20)    not null auto_increment    comment 'The taskID',
  job_name            varchar(64)   default ''                 comment 'Name of task',
  job_group           varchar(64)   default 'DEFAULT'          comment 'The task group name',
  invoke_target       varchar(500)  not null                   comment 'Call the target string.',
  cron_expression     varchar(255)  default ''                 comment 'cronImplementation of expression',
  misfire_policy      varchar(20)   default '3'                comment 'Planning an Error Strategy（1Immediately implemented 2performed once. 3abandon the execution.）',
  concurrent          char(1)       default '1'                comment 'Is it executed（0permitted 1Prohibited）',
  status              char(1)       default '0'                comment 'state of（0Normal 1suspended）',
  create_by           varchar(64)   default ''                 comment 'The Creator',
  create_time         datetime                                 comment 'Creating time.',
  update_by           varchar(64)   default ''                 comment 'Updated',
  update_time         datetime                                 comment 'Updated time',
  remark              varchar(500)  default ''                 comment 'Note Information',
  primary key (job_id, job_name, job_group)
) engine=innodb auto_increment=100 comment = 'timely tasks.';

insert into sys_job values(1, 'System is default.（No signs）', 'DEFAULT', 'ryTask.ryNoParams',        '0/10 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(2, 'System is default.（There is）', 'DEFAULT', 'ryTask.ryParams(\'ry\')',  '0/15 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');
insert into sys_job values(3, 'System is default.（and more）', 'DEFAULT', 'ryTask.ryMultipleParams(\'ry\', true, 2000L, 316.50D, 100)',  '0/20 * * * * ?', '3', '1', '1', 'admin', sysdate(), '', null, '');


-- ----------------------------
-- 16、Time-to-Time Task Diary
-- ----------------------------
drop table if exists sys_job_log;
create table sys_job_log (
  job_log_id          bigint(20)     not null auto_increment    comment 'The task log.ID',
  job_name            varchar(64)    not null                   comment 'Name of task',
  job_group           varchar(64)    not null                   comment 'The task group name',
  invoke_target       varchar(500)   not null                   comment 'Call the target string.',
  job_message         varchar(500)                              comment 'Diary information',
  status              char(1)        default '0'                comment 'The Execution State（0Normal 1Failure）',
  exception_info      varchar(2000)  default ''                 comment 'Unusual information',
  create_time         datetime                                  comment 'Creating time.',
  primary key (job_log_id)
) engine=innodb comment = 'Time-to-Time Task Diary';


-- ----------------------------
-- 17、Notification form
-- ----------------------------
drop table if exists sys_notice;
create table sys_notice (
  notice_id         int(4)          not null auto_increment    comment 'announcementID',
  notice_title      varchar(50)     not null                   comment 'Title of the announcement',
  notice_type       char(1)         not null                   comment 'Type of Advertising（1Notification 2announcement）',
  notice_content    longblob        default null               comment 'Content of announcement',
  status            char(1)         default '0'                comment 'state of announcement.（0Normal 1closed）',
  create_by         varchar(64)     default ''                 comment 'The Creator',
  create_time       datetime                                   comment 'Creating time.',
  update_by         varchar(64)     default ''                 comment 'Updated',
  update_time       datetime                                   comment 'Updated time',
  remark            varchar(255)    default null               comment 'Note to',
  primary key (notice_id)
) engine=innodb auto_increment=10 comment = 'Notification form';

-- ----------------------------
-- Initiation-Publication Information Table Data
-- ----------------------------
insert into sys_notice values('1', 'A warm reminder.：2018-07-01 If the new version is published.', '2', 'New version of content.', '0', 'admin', sysdate(), '', null, 'Managers');
insert into sys_notice values('2', 'Maintenance of notification：2018-07-01 Keeping the system early in the morning.', '1', 'Maintenance of content',   '0', 'admin', sysdate(), '', null, 'Managers');


-- ----------------------------
-- 18、Code to create a business table.
-- ----------------------------
drop table if exists gen_table;
create table gen_table (
  table_id          bigint(20)      not null auto_increment    comment 'Number of',
  table_name        varchar(200)    default ''                 comment 'Name of Table',
  table_comment     varchar(500)    default ''                 comment 'Table Description',
  sub_table_name    varchar(64)     default null               comment 'Name of associated subtitles.',
  sub_table_fk_name varchar(64)     default null               comment 'The external key name associated with the subtitle.',
  class_name        varchar(100)    default ''                 comment 'Name of entity',
  tpl_category      varchar(200)    default 'crud'             comment 'The template used.（crudSingle operations treeTree operations）',
  tpl_web_type      varchar(30)     default ''                 comment 'Type of frontal template（element-uiThe model element-plusThe model）',
  package_name      varchar(100)                               comment 'Create a package route.',
  module_name       varchar(30)                                comment 'Create a module name.',
  business_name     varchar(30)                                comment 'Create a business name.',
  function_name     varchar(50)                                comment 'Name of Success.',
  function_author   varchar(50)                                comment 'Successful writer.',
  gen_type          char(1)         default '0'                comment 'Create the method of code.（0zipCompressed package 1Personalized route）',
  gen_path          varchar(200)    default '/'                comment 'Create the path.（Not filling the default project route）',
  options           varchar(1000)                              comment 'Other generating options.',
  create_by         varchar(64)     default ''                 comment 'The Creator',
  create_time 	    datetime                                   comment 'Creating time.',
  update_by         varchar(64)     default ''                 comment 'Updated',
  update_time       datetime                                   comment 'Updated time',
  remark            varchar(500)    default null               comment 'Note to',
  primary key (table_id)
) engine=innodb auto_increment=1 comment = 'Code to create a business table.';


-- ----------------------------
-- 19、Code Generates Business Table Fields
-- ----------------------------
drop table if exists gen_table_column;
create table gen_table_column (
  column_id         bigint(20)      not null auto_increment    comment 'Number of',
  table_id          bigint(20)                                 comment 'Additional Table Number',
  column_name       varchar(200)                               comment 'The Name',
  column_comment    varchar(500)                               comment 'The description',
  column_type       varchar(100)                               comment 'Type of column',
  java_type         varchar(500)                               comment 'JAVAType of',
  java_field        varchar(200)                               comment 'JAVAName of field',
  is_pk             char(1)                                    comment 'The main key.（1is）',
  is_increment      char(1)                                    comment 'Is it increased（1is）',
  is_required       char(1)                                    comment 'must be filled.（1is）',
  is_insert         char(1)                                    comment 'to enter the field.（1is）',
  is_edit           char(1)                                    comment 'Edit the field.（1is）',
  is_list           char(1)                                    comment 'List of fields.（1is）',
  is_query          char(1)                                    comment 'Are you searching fields?（1is）',
  query_type        varchar(200)    default 'EQ'               comment 'Method of query（equal to、not equal.、greater than、less than、The range）',
  html_type         varchar(200)                               comment 'Showing Types（The text box、Text area、The lower box.、The selection box.、The Single Selection、Date of control）',
  dict_type         varchar(200)    default ''                 comment 'Type of dictionary',
  sort              int                                        comment 'ordered',
  create_by         varchar(64)     default ''                 comment 'The Creator',
  create_time 	    datetime                                   comment 'Creating time.',
  update_by         varchar(64)     default ''                 comment 'Updated',
  update_time       datetime                                   comment 'Updated time',
  primary key (column_id)
) engine=innodb auto_increment=1 comment = 'Code Generates Business Table Fields';