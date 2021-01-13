-- Root menu
insert into sys_menu values('4', 'MES', '0', '4', 'mes',             null,   1, 0, 'M', '0', '0', '', 'school',     'admin', sysdate(), '', null, 'Manufacturing execution system');

-- Secondary menu
insert into sys_menu values('117',  'Order Management', '4',   '1', 'order',    'mes/order/index',       1, 0, 'C', '0', '0', 'mes:order:list',       'order',       'admin', sysdate(), '', null, 'Order Management Menu');