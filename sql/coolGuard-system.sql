create table coolGuard.sys_dict
(
    id          bigint auto_increment comment '字典id'
        primary key,
    label       varchar(30) default ''                not null comment '字典标签',
    value       varchar(30) default ''                not null comment '字典值',
    data        varchar(500)                          not null comment '字典数据',
    standard    bit         default b'0'              not null comment '标准',
    description varchar(100)                          null comment '描述',
    creator     varchar(30) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_value
        unique (value)
)
    comment '字典表' charset = utf8mb4;

create table coolGuard.sys_dict_data
(
    id          bigint auto_increment comment '字典数据id'
        primary key,
    sort        int         default 99                not null comment '字典排序',
    label       varchar(30) default ''                not null comment '字典标签',
    value       varchar(30) default ''                not null comment '字典键值',
    dict_type   varchar(30)                           not null comment '字典类型',
    color       varchar(20) default 'default'         not null comment '颜色',
    status      bit         default b'0'              not null comment '状态',
    remark      varchar(100)                          null comment '备注',
    creator     varchar(30) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '字典数据表' charset = utf8mb4;

create table coolGuard.sys_dict_type
(
    id          bigint auto_increment comment '字典类型id'
        primary key,
    name        varchar(30) default ''                not null comment '字典名称',
    type        varchar(30) default ''                not null comment '字典类型',
    status      bit         default b'0'              not null comment '状态',
    standard    bit         default b'0'              null comment '是否内置',
    remark      varchar(100)                          null comment '备注',
    creator     varchar(30) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint dict_type
        unique (type)
)
    comment '字典类型表' charset = utf8mb4;

create table coolGuard.sys_login_log
(
    id          bigint auto_increment comment '登录日志id'
        primary key,
    login_type  bigint                                not null comment '登录类型',
    user_id     bigint      default 0                 not null comment '用户编号',
    user_type   tinyint     default 0                 not null comment '用户类型',
    account     varchar(30) default ''                not null comment '用户账号',
    result      tinyint                               not null comment '登录结果',
    user_ip     varchar(50)                           not null comment '用户 IP',
    user_agent  varchar(200)                          not null comment '浏览器 UA',
    creator     varchar(30) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '系统访问记录' charset = utf8mb4;

create table coolGuard.sys_menu
(
    id          bigint auto_increment comment '菜单id'
        primary key,
    parent_id   bigint      default 0                 not null comment '父菜单ID',
    type        tinyint                               not null comment '菜单类型',
    name        varchar(30)                           null comment '菜单名称',
    path        varchar(50) default ''                null comment '路由地址',
    component   varchar(100)                          null comment '组件路径',
    redirect    varchar(50)                           null comment '可点击',
    permission  varchar(50)                           null comment '权限码',
    order_no    int                                   null comment '用于路由->菜单排序',
    query_param text                                  null comment '菜单所携带的参数',
    title       varchar(50)                           null comment '标题名称',
    meta        varchar(1000)                         null comment '路由配置',
    creator     varchar(30) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_name
        unique (name)
)
    comment '菜单权限表' charset = utf8mb4;

create table coolGuard.sys_operate_log
(
    id               bigint auto_increment comment '操作日志id'
        primary key,
    user_id          bigint                                  not null comment '用户编号',
    user_nickname    varchar(30)                             null comment '用户昵称',
    module           varchar(50)                             not null comment '模块标题',
    name             varchar(50)                             not null comment '操作名',
    type             int           default 0                 not null comment '操作分类',
    content          varchar(2000) default ''                not null comment '操作内容',
    exts             varchar(512)  default ''                not null comment '拓展字段',
    request_method   varchar(16)   default ''                null comment '请求方法名',
    request_url      varchar(255)  default ''                null comment '请求地址',
    user_ip          varchar(50)                             null comment '用户 IP',
    user_agent       varchar(200)                            null comment '浏览器 UA',
    java_method      varchar(512)  default ''                not null comment 'Java 方法名',
    java_method_args varchar(8000) default ''                null comment 'Java 方法的参数',
    start_time       datetime                                not null comment '操作时间',
    end_time         datetime                                null comment '结束时间',
    duration         int                                     not null comment '执行时长',
    result_code      int           default 0                 not null comment '结果码',
    result_msg       varchar(512)  default ''                null comment '结果提示',
    result_data      varchar(4000) default ''                null comment '结果数据',
    creator          varchar(30)   default ''                null comment '创建者',
    create_time      datetime      default CURRENT_TIMESTAMP not null comment '创建时间',
    updater          varchar(30)   default ''                null comment '更新者',
    update_time      datetime      default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '操作日志记录' charset = utf8mb4;

create table coolGuard.sys_param
(
    id          bigint auto_increment comment '参数id'
        primary key,
    label       varchar(30) default ''                not null comment '参数标签',
    value       varchar(30) default ''                not null comment '参数值',
    type        varchar(10) default 'json'            not null comment '参数类型',
    data        varchar(500)                          not null comment '参数数据',
    standard    bit         default b'0'              not null comment '标准',
    description varchar(100)                          null comment '描述',
    creator     varchar(30) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_value
        unique (value)
)
    comment '参数表' charset = utf8mb4;

create table coolGuard.sys_role
(
    id          bigint auto_increment comment '角色id'
        primary key,
    name        varchar(30)                           not null comment '角色名称',
    value       varchar(30)                           not null comment '角色权限字符串',
    sort        int         default 99                not null comment '显示顺序',
    status      bit         default b'0'              not null comment '角色状态',
    remark      varchar(200)                          null comment '备注',
    creator     varchar(30) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_value
        unique (value)
)
    comment '角色信息表' charset = utf8mb4;

create table coolGuard.sys_role_menu
(
    id          bigint auto_increment comment '角色菜单id'
        primary key,
    role_id     bigint                                not null comment '角色ID',
    menu_id     bigint                                not null comment '菜单ID',
    creator     varchar(30) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '角色和菜单关联表' charset = utf8mb4;

create table coolGuard.sys_rsa
(
    id          bigint                                not null comment '密钥id'
        primary key,
    private_key varchar(255)                          null comment '私钥',
    public_key  varchar(255)                          null comment '公钥',
    remark      varchar(200)                          null comment '描述',
    creator     varchar(30) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment 'Rsa密钥表' charset = utf8mb4;

create table coolGuard.sys_user
(
    id          bigint auto_increment comment '用户id'
        primary key,
    username    varchar(30)                            not null comment '用户账号',
    password    varchar(100) default ''                not null comment '密码',
    nickname    varchar(30)                            not null comment '用户昵称',
    real_name   varchar(30)                            null comment '真实姓名',
    type        tinyint      default 0                 not null comment '用户类型',
    remark      varchar(200)                           null comment '描述',
    email       varchar(50)  default ''                null comment '用户邮箱',
    mobile      varchar(11)  default ''                null comment '手机号码',
    sex         tinyint      default 0                 null comment '用户性别',
    avatar      varchar(200) default ''                null comment '头像地址',
    status      bit          default b'0'              not null comment '帐号状态',
    login_ip    varchar(50)  default ''                null comment '最后登录IP',
    login_date  datetime                               null comment '最后登录时间',
    creator     varchar(30)  default ''                null comment '创建者',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30)  default ''                null comment '更新者',
    update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_email
        unique (email),
    constraint uk_mobile
        unique (mobile),
    constraint uk_username
        unique (username)
)
    comment '用户信息表' charset = utf8mb4;

create table coolGuard.sys_user_role
(
    id          bigint auto_increment comment '自增编号'
        primary key,
    user_id     bigint                                not null comment '用户ID',
    role_id     bigint                                not null comment '角色ID',
    creator     varchar(30) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP null comment '创建时间',
    updater     varchar(30) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '用户和角色关联表' charset = utf8mb4;



INSERT INTO coolGuard.sys_dict_data (sort, label, value, dict_type, color, status, remark, creator, create_time,
                                     updater, update_time)
VALUES (99, '开启', 'true', 'common_status', 'green', 1, '通用状态-开启', '', '2024-03-25 08:59:56', NULL,
        '2024-08-04 08:59:08'),
       (99, '关闭', 'false', 'common_status', 'red', 1, '通用状态-关闭', '', '2024-03-25 08:59:56', NULL,
        '2024-08-04 08:59:02'),
       (99, '目录', '0', 'system_menu_type', 'blue', 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:36'),
       (99, '菜单', '1', 'system_menu_type', 'green', 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:36'),
       (99, '按钮', '2', 'system_menu_type', 'red', 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:36'),
       (99, '男', '1', 'common_sex', 'blue', 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:36'),
       (99, '女', '2', 'common_sex', 'red', 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:36'),
       (99, '未知', '0', 'common_sex', 'default', 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:36'),
       (99, '账号密码登录', '100', 'system_login_type', 'pink', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36'),
       (99, '手机号密码登录', '101', 'system_login_type', 'red', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36');
INSERT INTO coolGuard.sys_dict_data (sort, label, value, dict_type, color, status, remark, creator, create_time,
                                     updater, update_time)
VALUES (99, '邮箱密码登录', '102', 'system_login_type', 'orange', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36'),
       (99, '手机号验证码登录', '103', 'system_login_type', 'green', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36'),
       (99, '邮箱验证码登录', '104', 'system_login_type', 'cyan', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36'),
       (99, '自己退出', '120', 'system_login_type', 'purple', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36'),
       (99, '登录成功', '0', 'system_login_result', 'green', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36'),
       (99, '账号或密码不正确', '10', 'system_login_result', 'pink', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36'),
       (99, '手机验证码不正确', '11', 'system_login_result', 'red', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36'),
       (99, '邮箱验证码不正确', '12', 'system_login_result', 'orange', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36'),
       (99, '未知异常', '100', 'system_login_result', 'default', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36'),
       (99, '用户被禁用', '20', 'system_login_result', 'blue', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36');
INSERT INTO coolGuard.sys_dict_data (sort, label, value, dict_type, color, status, remark, creator, create_time,
                                     updater, update_time)
VALUES (99, '新增', '2', 'system_operate_type', 'cyan', 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:36'),
       (99, '修改', '3', 'system_operate_type', 'blue', 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:36'),
       (99, '查询', '1', 'system_operate_type', 'green', 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:36'),
       (99, '删除', '4', 'system_operate_type', 'red', 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:36'),
       (99, '导出', '5', 'system_operate_type', 'pink', 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:36'),
       (99, '导入', '6', 'system_operate_type', 'orange', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36'),
       (99, '其他', '0', 'system_operate_type', 'default', 1, NULL, '', '2024-03-25 08:59:56', '',
        '2024-08-04 08:58:36'),
       (99, '是', 'true', 'common_standard', 'red', 1, NULL, 'admin', '2024-06-30 14:59:53', 'admin',
        '2024-08-04 08:58:36'),
       (99, '否', 'false', 'common_standard', 'green', 1, NULL, 'admin', '2024-06-30 15:00:11', 'admin',
        '2024-08-04 08:58:36');
INSERT INTO coolGuard.sys_dict_type (name, `type`, status, standard, remark, creator, create_time, updater, update_time)
VALUES ('通用状态', 'common_status', 1, 1, '通用状态', '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:11'),
       ('菜单类型', 'system_menu_type', 1, 1, '菜单类型', '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:11'),
       ('通用性别', 'common_sex', 1, 1, '通用性别', '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:11'),
       ('登录类型', 'system_login_type', 1, 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:11'),
       ('登录结果', 'system_login_result', 1, 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:11'),
       ('操作类型', 'system_operate_type', 1, 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 08:58:11'),
       ('系统标准', 'common_standard', 1, 1, NULL, 'admin', '2024-06-30 14:57:16', 'admin', '2024-08-04 08:58:11');
INSERT INTO coolGuard.sys_menu (parent_id, `type`, name, `path`, component, redirect, permission, order_no, query_param,
                                title, meta, creator, create_time, updater, update_time)
VALUES (0, 0, 'System', '/system', 'BasicLayout', '', NULL, NULL, NULL, '系统管理', '{"title":"page.system.title"}', '',
        '2024-03-25 10:12:09', NULL, '2025-01-05 22:01:09'),
       (1, 1, 'User', '/system/userDO', '/system/userDO/index', NULL, NULL, NULL, NULL, '用户管理',
        '{"title":"page.system.userDO"}', '', '2024-03-25 10:12:09', NULL, '2025-01-05 22:02:40'),
       (1, 1, 'Role', '/system/roleDO', '/system/roleDO/index', NULL, NULL, NULL, NULL, '角色管理',
        '{"title":"page.system.roleDO"}', '', '2024-03-25 10:12:09', NULL, '2025-01-05 22:03:30'),
       (1, 1, 'Menu', '/system/menuDO', '/system/menuDO/index', NULL, NULL, NULL, NULL, '菜单管理',
        '{"title":"page.system.menuDO"}', '', '2024-03-25 10:12:09', NULL, '2025-01-05 22:03:45'),
       (1, 0, 'Log', '/system/log', NULL, '/system/loginLogDO', NULL, NULL, NULL, '日志管理',
        '{"title":"page.system.log"}', '', '2024-03-25 10:12:09', NULL, '2025-01-05 22:47:21'),
       (104, 1, 'LoginLog', '/system/loginLogDO', '/system/loginLogDO/index', NULL, NULL, NULL, NULL, '登录日志',
        '{"title":"page.system.loginLogDO"}', '', '2024-03-25 10:12:09', NULL, '2025-01-05 22:05:48'),
       (104, 1, 'OperateLog', '/system/operateLogDO', '/system/operateLogDO/index', NULL, NULL, NULL, NULL, '操作日志',
        '{"iframeSrc":"","title":"page.system.operateLogDO"}', '', '2024-03-25 10:12:09', NULL, '2025-01-07 18:39:54'),
       (0, 0, 'Access', '/access', 'BasicLayout', NULL, NULL, NULL, NULL, '接入中心', '{"title":"page.access.title"}',
        NULL, '2025-01-05 22:15:25', NULL, '2025-01-05 22:47:44'),
       (1058, 1, 'Filed', '/access/field', '/access/field/index', NULL, NULL, NULL, NULL, '字段管理',
        '{"title":"page.access.field"}', NULL, '2025-01-05 22:20:32', NULL, '2025-01-05 22:24:33'),
       (0, 0, 'Dashboard', '/', 'BasicLayout', NULL, NULL, NULL, NULL, '概览', '{"title":"page.dashboard.title"}', NULL,
        '2025-01-07 18:21:42', NULL, '2025-01-07 18:22:23');
INSERT INTO coolGuard.sys_menu (parent_id, `type`, name, `path`, component, redirect, permission, order_no, query_param,
                                title, meta, creator, create_time, updater, update_time)
VALUES (1060, 1, 'Analytics', '/analytics', '/dashboard/analytics/index', NULL, NULL, NULL, NULL, '分析页',
        '{"title":"page.dashboard.analytics"}', NULL, '2025-01-07 18:23:36', NULL, '2025-01-07 18:23:36'),
       (1060, 1, 'Workspace', '/workspace', '/dashboard/workspace/index', NULL, NULL, NULL, NULL, '工作台',
        '{"title":"page.dashboard.workspace"}', NULL, '2025-01-07 18:24:18', NULL, '2025-01-07 18:24:18'),
       (1, 1, 'Dict', '/system/dictDO', '/system/dictDO/index', NULL, NULL, NULL, NULL, '字典管理',
        '{"title":"page.system.dictDO"}', NULL, '2025-01-07 18:26:23', NULL, '2025-01-07 18:26:23'),
       (1, 1, 'Param', '/paramDO', '/system/paramDO/index', NULL, NULL, NULL, NULL, '参数管理',
        '{"title":"page.system.paramDO"}', NULL, '2025-01-07 18:27:13', NULL, '2025-01-07 18:27:13');
INSERT INTO coolGuard.sys_role (name, value, sort, status, remark, creator, create_time, updater, update_time)
VALUES ('超级管理员', 'administrator', 0, 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 09:00:35'),
       ('管理员', 'admin', 99, 1, NULL, 'admin', '2024-06-30 15:27:44', NULL, '2025-01-04 22:41:49');
INSERT INTO coolGuard.sys_role_menu (role_id, menu_id, creator, create_time, updater, update_time)
VALUES (3, 1001, NULL, '2025-01-04 22:41:49', NULL, '2025-01-04 22:41:49');
INSERT INTO coolGuard.sys_user (username, password, nickname, real_name, `type`, remark, email, mobile, sex, avatar,
                                status, login_ip, login_date, creator, create_time, updater, update_time)
VALUES ('admin', '$2a$10$YxtQzqA1SX6M2rov0DLDveyLa1a7K737k46MxMGUlOqY46nW4Vz02', 'admin', NULL, 0, NULL,
        'wnhyang@qq.com', '17317430552', 0, '', 1, '0:0:0:0:0:0:0:1', '2025-01-07 18:41:15', '', '2024-03-25 08:59:56',
        NULL, '2025-01-07 18:41:15'),
       ('zhangsan', '$2a$10$TtOM8M7KW0MXapaN6a3awOxko9AdPoFAMk7EM6.esMtv46D/8/IDG', '张三', NULL, 0, NULL,
        '3423@qq.com', '13423454567', 2, '', 1, '127.0.0.1', '2024-03-26 21:57:05', 'admin', '2024-03-26 13:40:16',
        NULL, '2025-01-04 22:40:15'),
       ('wnhyang', '$2a$10$uqU5jmFmn6BoFa2vraUXyuiwXfgB23LRK7OrSWhHCOROK4qxyEifi', '无奈何杨', NULL, 0, NULL,
        'wnhyang@163.com', '17317430553', 1, '', 1, '127.0.0.1', '2024-06-30 15:22:04', 'admin', '2024-06-30 15:11:38',
        NULL, '2025-01-04 22:37:05'),
       ('rerhd', '$2a$10$XDwbSYsundS3bCCOAQ/ppuFPJhkiS8WSKX0LFtQ1CDcrPNKnRwsyO', 'hre', NULL, 0, 'dbd', 'ehr@sg.egf',
        'htrhettrjr', 0, 'njtr', 0, '', NULL, NULL, '2025-01-04 16:34:37', NULL, '2025-01-04 16:34:37');
INSERT INTO coolGuard.sys_user_role (user_id, role_id, creator, create_time, updater, update_time)
VALUES (1, 1, '', '2024-03-25 08:59:56', '', '2024-03-25 08:59:56'),
       (7, 1, NULL, '2025-01-04 16:34:37', NULL, '2025-01-04 16:34:37'),
       (7, 3, NULL, '2025-01-04 16:34:37', NULL, '2025-01-04 16:34:37'),
       (4, 1, NULL, '2025-01-04 22:37:05', NULL, '2025-01-04 22:37:05'),
       (4, 3, NULL, '2025-01-04 22:37:05', NULL, '2025-01-04 22:37:05'),
       (2, 1, NULL, '2025-01-04 22:40:15', NULL, '2025-01-04 22:40:15');
