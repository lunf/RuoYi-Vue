-- Root menu
insert into sys_menu values('4', 'MES', '0', '4', 'mes',             null,   1, 0, 'M', '0', '0', '', 'factory',     'admin', sysdate(), '', null, 'Manufacturing execution system');


-- Job Order SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Job Order', '4', '1', 'job', 'mes/job/index', 1, 0, 'C', '0', '0', 'mes:job:list', '#', 'admin', sysdate(), '', null, 'Job Order menu');

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