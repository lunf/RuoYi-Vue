-- Root menu
insert into sys_menu values
('5', 'Medical', '0', '5', 'medical', NULL, '1', '0', 'M', '0', '0', '', 'logininfor', 'admin', sysdate(), 'admin', null, 'Medical management');

-- Menu SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Patient', '5', '1', 'patient', 'med/patient/index', 1, 0, 'C', '0', '0', 'med:patient:list', 'people', 'admin', sysdate(), '', null, 'Patient menu');

-- Button parent menu ID
SELECT @parentId := LAST_INSERT_ID();

-- Button SQL
insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Patient Query', @parentId, '1',  '#', '', 1, 0, 'F', '0', '0', 'med:patient:query',        '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Patient Create', @parentId, '2',  '#', '', 1, 0, 'F', '0', '0', 'med:patient:add',          '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Patient Modify', @parentId, '3',  '#', '', 1, 0, 'F', '0', '0', 'med:patient:edit',         '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Patient Delete', @parentId, '4',  '#', '', 1, 0, 'F', '0', '0', 'med:patient:remove',       '#', 'admin', sysdate(), '', null, '');

insert into sys_menu (menu_name, parent_id, order_num, path, component, is_frame, is_cache, menu_type, visible, status, perms, icon, create_by, create_time, update_by, update_time, remark)
values('Patient Export', @parentId, '5',  '#', '', 1, 0, 'F', '0', '0', 'med:patient:export',       '#', 'admin', sysdate(), '', null, '');
