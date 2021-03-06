-- Job Order Status --
insert into sys_dict_type
values ('Job Order Status', 'job_order_status', '0', 'admin', sysdate(), '', NULL, 'Job Order Status');
insert into sys_dict_data values(1,'Default','0','job_order_status',NULL,NULL,'N','0','admin',sysdate(),'',NULL,'Default');
insert into sys_dict_data values(2,'Active','1','job_order_status',NULL,NULL,'N','0','admin',sysdate(),'',NULL,'Active');
insert into sys_dict_data values(3,'Inactive','2','job_order_status',NULL,NULL,'N','0','admin',sysdate(),'',NULL,'No activity');
insert into sys_dict_data values(4,'Archived','3','job_order_status',NULL,NULL,'N','0','admin',sysdate(),'',NULL,'Archived');

-- Priority list --
insert into sys_dict_type
values ('Priority list', 'priority_list', '0', 'admin', sysdate(), '', NULL, 'Priority list');
insert into sys_dict_data values(1,'Urgent','1','priority_list',NULL,NULL,'N','0','admin',sysdate(),'',NULL,'Top priority');
insert into sys_dict_data values(2,'High','2','priority_list',NULL,NULL,'N','0','admin',sysdate(),'',NULL,'High priority');
insert into sys_dict_data values(3,'Normal','3','priority_list',NULL,NULL,'N','0','admin',sysdate(),'',NULL,'Standard priority');
insert into sys_dict_data values(4,'Low','4','priority_list',NULL,NULL,'N','0','admin',sysdate(),'',NULL,'Low priority');
insert into sys_dict_data values(5,'Trivial','5','priority_list',NULL,NULL,'N','0','admin',sysdate(),'',NULL,'Trivial priority');

-- Work Order status --
insert into sys_dict_type
values ('Work Order Status','work_order_status','0','admin',sysdate(),'',NULL,NULL);
insert into sys_dict_data values(0,'Default','0','work_order_status',NULL,NULL,'N','0','admin',sysdate(),'',NULL,NULL);
insert into sys_dict_data values(1,'Ready','1','work_order_status',NULL,NULL,'N','0','admin',sysdate(),'',NULL,NULL);
insert into sys_dict_data values(2,'On-Going','2','work_order_status',NULL,NULL,'N','0','admin',sysdate(),'',NULL,NULL);
insert into sys_dict_data values(3,'Completed','3','work_order_status',NULL,NULL,'N','0','admin',sysdate(),'',NULL,NULL);

-- Work Order Type --
insert into sys_dict_type
values ('Work Order Type','work_order_type','0','admin',sysdate(),'',NULL,'Work Order Type');
insert into sys_dict_data values(0,'Cabinet Vision','0','work_order_type',NULL,NULL,'N','0','admin',sysdate(),'',NULL,'Cabinet Vision');
insert into sys_dict_data values(1,'Thermwood (Excel)','1','work_order_type',NULL,NULL,'N','0','admin',sysdate(),'',NULL,'Thermwood(Excel)');
