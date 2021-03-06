-- Menu SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Material', '5', '1', 'material', 'med/material/index', 1, 0, 'C', '0', '0', 'med:material:list', '#', 'admin', sysdate(), '', null, 'Material menu');

-- Button parent menu ID
SELECT @parentId := LAST_INSERT_ID();

-- Button SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Material Query', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'med:material:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Material Create', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'med:material:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Material Modify', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'med:material:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Material Delete', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'med:material:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Material Export', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'med:material:export',       '#', 'admin', sysdate(), '', null, '');