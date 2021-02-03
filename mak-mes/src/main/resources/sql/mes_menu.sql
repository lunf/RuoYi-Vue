-- Root menu
insert into sys_menu values('4', 'MES', '0', '4', 'mes',             null,   1, 0, 'M', '0', '0', '', 'factory',     'admin', sysdate(), '', null, 'Manufacturing execution system');


-- Job Order SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Job Order', '4', '1', 'order', 'mes/job/index', 1, 0, 'C', '0', '0', 'mes:job:list', '#', 'admin', sysdate(), '', null, 'Job Order menu');

-- Job Order button parent menu ID
SELECT @parentId := LAST_INSERT_ID();

-- Job Order button SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Job Order Query', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'mes:job:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Job Order Create', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'mes:job:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Job Order Modify', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'mes:job:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Job Order Delete', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'mes:job:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Job Order Export', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'mes:job:export',       '#', 'admin', sysdate(), '', null, '');

----------- Work Order SQL ------
-- Menu SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Work Order', '2000', '1', 'work', 'mes/work/index', 1, 0, 'C', '0', '0', 'mes:work:list', '#', 'admin', sysdate(), '', null, 'Work Order menu');

-- Button parent menu ID
SELECT @parentId := LAST_INSERT_ID();

-- Button SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Work Order Query', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'mes:work:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Work Order Create', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'mes:work:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Work Order Modify', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'mes:work:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Work Order Delete', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'mes:work:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Work Order Export', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'mes:work:export',       '#', 'admin', sysdate(), '', null, '');