DROP TABLE IF EXISTS QRTZ_FIRED_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_PAUSED_TRIGGER_GRPS;
DROP TABLE IF EXISTS QRTZ_SCHEDULER_STATE;
DROP TABLE IF EXISTS QRTZ_LOCKS;
DROP TABLE IF EXISTS QRTZ_SIMPLE_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_SIMPROP_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_CRON_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_BLOB_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_TRIGGERS;
DROP TABLE IF EXISTS QRTZ_JOB_DETAILS;
DROP TABLE IF EXISTS QRTZ_CALENDARS;

-- ----------------------------
-- 1、Storage each set. jobDetail The detailed information
-- ----------------------------
create table QRTZ_JOB_DETAILS (
    sched_name           varchar(120)    not null            comment 'Modified Name',
    job_name             varchar(200)    not null            comment 'Name of task',
    job_group            varchar(200)    not null            comment 'The task group name',
    description          varchar(250)    null                comment 'Related Introduction',
    job_class_name       varchar(250)    not null            comment 'Implementation of task name.',
    is_durable           varchar(1)      not null            comment 'is sustainable.',
    is_nonconcurrent     varchar(1)      not null            comment 'is combined.',
    is_update_data       varchar(1)      not null            comment 'Updated data.',
    requests_recovery    varchar(1)      not null            comment 'Is recovery accepted?',
    job_data             blob            null                comment 'Sustainable storage.jobObjects',
    primary key (sched_name, job_name, job_group)
) engine=innodb comment = 'Detailed task information.';

-- ----------------------------
-- 2、 Storage is configured. Trigger The information
-- ----------------------------
create table QRTZ_TRIGGERS (
    sched_name           varchar(120)    not null            comment 'Modified Name',
    trigger_name         varchar(200)    not null            comment 'Name of the trigger.',
    trigger_group        varchar(200)    not null            comment 'Name of the group in which the trigger belongs',
    job_name             varchar(200)    not null            comment 'qrtz_job_detailsTable ofjob_nameThe external key.',
    job_group            varchar(200)    not null            comment 'qrtz_job_detailsTable ofjob_groupThe external key.',
    description          varchar(250)    null                comment 'Related Introduction',
    next_fire_time       bigint(13)      null                comment 'The last time triggered.（Mills of seconds.）',
    prev_fire_time       bigint(13)      null                comment 'The next time triggered.（I think-1Not to provoke.）',
    priority             integer         null                comment 'Priority',
    trigger_state        varchar(16)     not null            comment 'The provokator state.',
    trigger_type         varchar(8)      not null            comment 'Type of trigger',
    start_time           bigint(13)      not null            comment 'The Time Begins',
    end_time             bigint(13)      null                comment 'The time ends.',
    calendar_name        varchar(200)    null                comment 'Name of Calendar',
    misfire_instr        smallint(2)     null                comment 'Compensation Strategy',
    job_data             blob            null                comment 'Sustainable storage.jobObjects',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, job_name, job_group) references QRTZ_JOB_DETAILS(sched_name, job_name, job_group)
) engine=innodb comment = 'Scanner Detailed Information Table';

-- ----------------------------
-- 3、 Simply stored. Trigger，The number of repeats.，The interval，The number that has been triggered.
-- ----------------------------
create table QRTZ_SIMPLE_TRIGGERS (
    sched_name           varchar(120)    not null            comment 'Modified Name',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggersTable oftrigger_nameThe external key.',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggersTable oftrigger_groupThe external key.',
    repeat_count         bigint(7)       not null            comment 'Repeated statistics',
    repeat_interval      bigint(12)      not null            comment 'Repeated interval time.',
    times_triggered      bigint(10)      not null            comment 'The number that has been triggered.',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = 'Simple scanner information.';

-- ----------------------------
-- 4、 stored Cron Trigger，Included Cron Expression and Time Zone Information
-- ---------------------------- 
create table QRTZ_CRON_TRIGGERS (
    sched_name           varchar(120)    not null            comment 'Modified Name',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggersTable oftrigger_nameThe external key.',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggersTable oftrigger_groupThe external key.',
    cron_expression      varchar(200)    not null            comment 'cronThe expression',
    time_zone_id         varchar(80)                         comment 'time area',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = 'CronType of trigger';

-- ----------------------------
-- 5、 Trigger as Blob Type of storage(used Quartz Users use JDBC Created by themselves. Trigger Type of，JobStore I don't know how to store the instances.)
-- ---------------------------- 
create table QRTZ_BLOB_TRIGGERS (
    sched_name           varchar(120)    not null            comment 'Modified Name',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggersTable oftrigger_nameThe external key.',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggersTable oftrigger_groupThe external key.',
    blob_data            blob            null                comment 'Sustainable storage.TriggerObjects',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = 'BlobType of trigger';

-- ----------------------------
-- 6、 by Blob Type of storage calendar information， quartzYou can configure a calendar to specify a time range.
-- ---------------------------- 
create table QRTZ_CALENDARS (
    sched_name           varchar(120)    not null            comment 'Modified Name',
    calendar_name        varchar(200)    not null            comment 'Name of Calendar',
    calendar             blob            not null            comment 'Sustainable storage.calendarObjects',
    primary key (sched_name, calendar_name)
) engine=innodb comment = 'Calendar Information Table';

-- ----------------------------
-- 7、 Storage is suspended. Trigger Information of the group
-- ---------------------------- 
create table QRTZ_PAUSED_TRIGGER_GRPS (
    sched_name           varchar(120)    not null            comment 'Modified Name',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggersTable oftrigger_groupThe external key.',
    primary key (sched_name, trigger_group)
) engine=innodb comment = 'The suspended trigger.';

-- ----------------------------
-- 8、 Stored and triggered. Trigger Related State Information，and connected. Job Implementation Information
-- ---------------------------- 
create table QRTZ_FIRED_TRIGGERS (
    sched_name           varchar(120)    not null            comment 'Modified Name',
    entry_id             varchar(95)     not null            comment 'Moderators Examplesid',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggersTable oftrigger_nameThe external key.',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggersTable oftrigger_groupThe external key.',
    instance_name        varchar(200)    not null            comment 'Examples of Moderators',
    fired_time           bigint(13)      not null            comment 'The time triggered.',
    sched_time           bigint(13)      not null            comment 'Time set up.',
    priority             integer         not null            comment 'Priority',
    state                varchar(16)     not null            comment 'state of',
    job_name             varchar(200)    null                comment 'Name of task',
    job_group            varchar(200)    null                comment 'The task group name',
    is_nonconcurrent     varchar(1)      null                comment 'is combined.',
    requests_recovery    varchar(1)      null                comment 'Is recovery accepted?',
    primary key (sched_name, entry_id)
) engine=innodb comment = 'The trigger tables are triggered.';

-- ----------------------------
-- 9、 Small amount of storage. Scheduler state information.，If used in the group.，You can see the others. Scheduler Examples
-- ---------------------------- 
create table QRTZ_SCHEDULER_STATE (
    sched_name           varchar(120)    not null            comment 'Modified Name',
    instance_name        varchar(200)    not null            comment 'Name of instance',
    last_checkin_time    bigint(13)      not null            comment 'Last check time.',
    checkin_interval     bigint(13)      not null            comment 'Check the interval time.',
    primary key (sched_name, instance_name)
) engine=innodb comment = 'The Modular Status Table.';

-- ----------------------------
-- 10、 Pessimistic Lock Information of the Storage Program(Using a pessimistic lock.)
-- ---------------------------- 
create table QRTZ_LOCKS (
    sched_name           varchar(120)    not null            comment 'Modified Name',
    lock_name            varchar(40)     not null            comment 'The Pessimistic Name',
    primary key (sched_name, lock_name)
) engine=innodb comment = 'Pessimistic Lock Information Table';

-- ----------------------------
-- 11、 QuartzGroups to realize the synchronization mechanism
-- ---------------------------- 
create table QRTZ_SIMPROP_TRIGGERS (
    sched_name           varchar(120)    not null            comment 'Modified Name',
    trigger_name         varchar(200)    not null            comment 'qrtz_triggersTable oftrigger_nameThe external key.',
    trigger_group        varchar(200)    not null            comment 'qrtz_triggersTable oftrigger_groupThe external key.',
    str_prop_1           varchar(512)    null                comment 'StringType oftriggerThe first parameter.',
    str_prop_2           varchar(512)    null                comment 'StringType oftriggerThe second parameter.',
    str_prop_3           varchar(512)    null                comment 'StringType oftriggerThe third parameter.',
    int_prop_1           int             null                comment 'intType oftriggerThe first parameter.',
    int_prop_2           int             null                comment 'intType oftriggerThe second parameter.',
    long_prop_1          bigint          null                comment 'longType oftriggerThe first parameter.',
    long_prop_2          bigint          null                comment 'longType oftriggerThe second parameter.',
    dec_prop_1           numeric(13,4)   null                comment 'decimalType oftriggerThe first parameter.',
    dec_prop_2           numeric(13,4)   null                comment 'decimalType oftriggerThe second parameter.',
    bool_prop_1          varchar(1)      null                comment 'BooleanType oftriggerThe first parameter.',
    bool_prop_2          varchar(1)      null                comment 'BooleanType oftriggerThe second parameter.',
    primary key (sched_name, trigger_name, trigger_group),
    foreign key (sched_name, trigger_name, trigger_group) references QRTZ_TRIGGERS(sched_name, trigger_name, trigger_group)
) engine=innodb comment = 'Lines of Sync Mechanism';

commit;