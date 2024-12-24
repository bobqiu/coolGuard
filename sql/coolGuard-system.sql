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
    id                  bigint auto_increment comment '菜单id'
        primary key,
    name                varchar(30)                            null comment '菜单名称',
    permission          varchar(30)                            null comment '权限标识',
    type                tinyint                                not null comment '菜单类型',
    order_no            int          default 0                 not null comment '显示顺序',
    parent_id           bigint       default 0                 not null comment '父菜单ID',
    path                varchar(50)  default ''                null comment '路由地址',
    icon                varchar(100) default '#'               null comment '菜单图标',
    component           varchar(100)                           null comment '组件路径',
    hide_breadcrumb     bit          default b'0'              null comment '隐藏面包屑',
    current_active_menu varchar(50)                            null comment '当前激活菜单',
    keepalive           bit          default b'0'              null comment '缓存',
    title               varchar(50)                            null comment '组件名',
    redirect            varchar(50)                            null comment '可点击',
    status              bit          default b'0'              not null comment '菜单状态',
    is_show             bit          default b'0'              not null comment '是否可见',
    creator             varchar(30)  default ''                null comment '创建者',
    create_time         datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updater             varchar(30)  default ''                null comment '更新者',
    update_time         datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    is_ext              bit          default b'0'              null comment '是否外链'
)
    comment '菜单权限表' charset = utf8mb4;

create table coolGuard.sys_operate_log
(
    id               bigint auto_increment comment '操作日志id'
        primary key,
    user_id          bigint                                  not null comment '用户编号',
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
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
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
        unique (username, update_time)
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
INSERT INTO coolGuard.sys_menu (name, permission, `type`, order_no, parent_id, `path`, icon, component, hide_breadcrumb,
                                current_active_menu, keepalive, title, redirect, status, is_show, creator, create_time,
                                updater, update_time, is_ext)
VALUES ('System', NULL, 0, 0, 0, '/system', 'ant-design:audit-outlined', NULL, 0, NULL, 0, '系统管理', '/system/user',
        1, 0, '', '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       ('User', 'system:user:list', 1, 1, 1, 'user', 'ant-design:user-outlined', 'system/user/index', 0, NULL, 0,
        '用户管理', NULL, 1, 0, '', '2024-03-25 10:12:09', 'admin', '2024-08-04 09:04:52', 0),
       ('Role', 'system:role:list', 1, 2, 1, 'role', 'ant-design:crown-outlined', 'system/role/index', 0, NULL, 0,
        '角色管理', NULL, 1, 0, '', '2024-03-25 10:12:09', 'admin', '2024-08-04 09:04:52', 0),
       ('Menu', 'system:menu:list', 1, 3, 1, 'menu', 'ant-design:menu-fold-outlined', 'system/menu/index', 0, NULL, 0,
        '菜单管理', NULL, 1, 0, '', '2024-03-25 10:12:09', 'admin', '2024-08-04 09:04:52', 0),
       ('Password', NULL, 1, 97, 1, 'password', 'ant-design:unlock-outlined', 'system/password/index', 0, NULL, 0,
        '修改密码', NULL, 1, 0, '', '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       ('Log', NULL, 0, 99, 1, 'log', 'ant-design:ordered-list-outlined', NULL, 0, NULL, 0, '日志管理',
        '/system/log/loginlog', 1, 0, '', '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       ('Dict', NULL, 0, 4, 1, 'dict', 'ant-design:medicine-box-outlined', NULL, 0, NULL, 0, '字典管理',
        '/system/dict/dicttype', 1, 0, '', '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       ('UserDetail', NULL, 1, 0, 1, 'userDetail/:id', 'ant-design:carry-out-twotone', 'system/user/UserDetail', 0,
        '/system/user', 0, '用户详情', NULL, 1, 1, '', '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       (NULL, 'system:user:create', 2, 0, 100, '', '', '', 0, NULL, 0, '用户新增', NULL, 1, 0, '',
        '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       (NULL, 'system:user:update', 2, 0, 100, '', '', '', 0, NULL, 0, '用户更新', NULL, 1, 0, '',
        '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0);
INSERT INTO coolGuard.sys_menu (name, permission, `type`, order_no, parent_id, `path`, icon, component, hide_breadcrumb,
                                current_active_menu, keepalive, title, redirect, status, is_show, creator, create_time,
                                updater, update_time, is_ext)
VALUES (NULL, 'system:user:delete', 2, 0, 100, '', '', '', 0, NULL, 0, '用户删除', NULL, 1, 0, '',
        '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       (NULL, 'system:user:query', 2, 0, 100, '', '', '', 0, NULL, 0, '用户查询', NULL, 1, 0, '', '2024-03-25 10:12:09',
        '', '2024-08-04 09:04:52', 0),
       (NULL, 'system:role:create', 2, 0, 101, '', '', '', 0, NULL, 0, '角色新增', NULL, 1, 0, '',
        '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       (NULL, 'system:role:update', 2, 0, 101, '', '', '', 0, NULL, 0, '角色更新', NULL, 1, 0, '',
        '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       (NULL, 'system:role:delete', 2, 0, 101, '', '', '', 0, NULL, 0, '角色删除', NULL, 1, 0, '',
        '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       (NULL, 'system:role:query', 2, 0, 101, '', '', '', 0, NULL, 0, '角色查询', NULL, 1, 0, '', '2024-03-25 10:12:09',
        '', '2024-08-04 09:04:52', 0),
       (NULL, 'system:menu:create', 2, 0, 102, '', '', '', 0, NULL, 0, '菜单新增', NULL, 1, 0, '',
        '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       (NULL, 'system:menu:update', 2, 0, 102, '', '', '', 0, NULL, 0, '菜单更新', NULL, 1, 0, '',
        '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       (NULL, 'system:menu:delete', 2, 0, 102, '', '', '', 0, NULL, 0, '菜单删除', NULL, 1, 0, '',
        '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       (NULL, 'system:menu:query', 2, 0, 102, '', '', '', 0, NULL, 0, '菜单查询', NULL, 1, 0, '', '2024-03-25 10:12:09',
        '', '2024-08-04 09:04:52', 0);
INSERT INTO coolGuard.sys_menu (name, permission, `type`, order_no, parent_id, `path`, icon, component, hide_breadcrumb,
                                current_active_menu, keepalive, title, redirect, status, is_show, creator, create_time,
                                updater, update_time, is_ext)
VALUES ('Loginlog', NULL, 1, 0, 104, 'loginlog', 'ant-design:login-outlined', 'system/loginLog/index', 0, NULL, 0,
        '登录日志', NULL, 1, 0, '', '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       ('Operatelog', NULL, 1, 0, 104, 'operatelog', 'ant-design:logout-outlined', 'system/operateLog/index', 0, NULL,
        0, '操作日志', NULL, 1, 0, '', '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       ('DictType', NULL, 1, 0, 105, 'dicttype', 'ant-design:book-outlined', 'system/dictType/index', 0, NULL, 0,
        '字典类型', NULL, 1, 0, '', '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       ('DictData', NULL, 1, 0, 105, 'dictdata', 'ant-design:behance-outlined', 'system/dictData/index', 0, NULL, 0,
        '字典数据', NULL, 1, 0, '', '2024-03-25 10:12:09', '', '2024-08-04 09:04:52', 0),
       (NULL, 'system:user:import', 2, 0, 100, '', '', '', 0, NULL, 0, '用户导入', NULL, 1, 0, 'admin',
        '2024-06-30 15:35:33', 'admin', '2024-08-04 09:04:52', 0),
       (NULL, 'system:user:export', 2, 0, 100, '', '', '', 0, NULL, 0, '用户导出', NULL, 1, 0, 'admin',
        '2024-06-30 15:36:12', 'admin', '2024-08-04 09:04:52', 0),
       (NULL, 'system:role:import', 2, 0, 101, '', '', '', 0, NULL, 0, '角色导入', NULL, 1, 0, 'admin',
        '2024-06-30 15:37:01', 'admin', '2024-08-04 09:04:52', 0),
       (NULL, 'system:role:export', 2, 0, 101, '', '', '', 0, NULL, 0, '角色导出', NULL, 1, 0, 'admin',
        '2024-06-30 15:37:33', 'admin', '2024-08-04 09:04:52', 0),
       (NULL, 'system:menu:export', 2, 0, 102, '', '', '', 0, NULL, 0, '菜单导出', NULL, 1, 0, 'admin',
        '2024-06-30 15:38:11', 'admin', '2024-08-04 09:04:52', 0);
INSERT INTO coolGuard.sys_role (name, value, sort, status, remark, creator, create_time, updater, update_time)
VALUES ('超级管理员', 'administrator', 0, 1, NULL, '', '2024-03-25 08:59:56', '', '2024-08-04 09:00:35'),
       ('操作员', 'operator', 99, 1, NULL, 'admin', '2024-03-26 20:45:35', 'admin', '2024-08-04 09:00:35'),
       ('管理员', 'admin', 99, 1, NULL, 'admin', '2024-06-30 15:27:44', 'admin', '2024-08-04 09:00:35');
INSERT INTO coolGuard.sys_role_menu (role_id, menu_id, creator, create_time, updater, update_time)
VALUES (2, 104, 'admin', '2024-03-26 20:45:36', 'admin', '2024-03-26 21:35:57'),
       (2, 1041, 'admin', '2024-03-26 20:45:36', 'admin', '2024-03-26 21:35:57'),
       (2, 1042, 'admin', '2024-03-26 20:45:36', 'admin', '2024-03-26 21:35:57'),
       (2, 1041, 'admin', '2024-03-26 21:35:58', 'admin', '2024-06-30 16:25:59'),
       (2, 1042, 'admin', '2024-03-26 21:35:58', 'admin', '2024-06-30 16:25:59'),
       (2, 104, 'admin', '2024-03-26 21:35:58', 'admin', '2024-06-30 16:25:59'),
       (2, 105, 'admin', '2024-03-26 21:35:58', 'admin', '2024-06-30 16:25:59'),
       (2, 1051, 'admin', '2024-03-26 21:35:58', 'admin', '2024-06-30 16:25:59'),
       (2, 1052, 'admin', '2024-03-26 21:35:58', 'admin', '2024-06-30 16:25:59'),
       (3, 1024, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18');
INSERT INTO coolGuard.sys_role_menu (role_id, menu_id, creator, create_time, updater, update_time)
VALUES (3, 1, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 100, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 101, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 102, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 104, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 105, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1001, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 106, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1002, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1003, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18');
INSERT INTO coolGuard.sys_role_menu (role_id, menu_id, creator, create_time, updater, update_time)
VALUES (3, 1004, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1041, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1042, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1011, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1012, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1013, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1014, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1051, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1052, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1021, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18');
INSERT INTO coolGuard.sys_role_menu (role_id, menu_id, creator, create_time, updater, update_time)
VALUES (3, 1022, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (3, 1023, 'admin', '2024-06-30 15:27:44', 'admin', '2024-06-30 16:28:18'),
       (2, 1041, 'admin', '2024-06-30 16:25:59', 'admin', '2024-06-30 16:25:59'),
       (2, 1051, 'admin', '2024-06-30 16:26:00', 'admin', '2024-06-30 16:26:00'),
       (3, 1024, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 104, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 105, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1001, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 106, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1002, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19');
INSERT INTO coolGuard.sys_role_menu (role_id, menu_id, creator, create_time, updater, update_time)
VALUES (3, 1003, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1004, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1041, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1042, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1011, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1012, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1013, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1014, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1051, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1052, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19');
INSERT INTO coolGuard.sys_role_menu (role_id, menu_id, creator, create_time, updater, update_time)
VALUES (3, 1021, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1022, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19'),
       (3, 1023, 'admin', '2024-06-30 16:28:19', 'admin', '2024-06-30 16:28:19');
INSERT INTO coolGuard.sys_user (username, password, nickname, `type`, remark, email, mobile, sex, avatar, status,
                                login_ip, login_date, creator, create_time, updater, update_time)
VALUES ('admin', '$2a$10$YxtQzqA1SX6M2rov0DLDveyLa1a7K737k46MxMGUlOqY46nW4Vz02', 'admin', 0, NULL, 'wnhyang@qq.com',
        '17317430552', 0, '', 1, '0:0:0:0:0:0:0:1', '2024-12-22 21:48:30', '', '2024-03-25 08:59:56', NULL,
        '2024-12-22 21:48:30'),
       ('zhangsan', '$2a$10$TtOM8M7KW0MXapaN6a3awOxko9AdPoFAMk7EM6.esMtv46D/8/IDG', '张三', 0, NULL, '3423@qq.com',
        '13423454567', 2, '', 1, '127.0.0.1', '2024-03-26 21:57:05', 'admin', '2024-03-26 13:40:16', NULL,
        '2024-08-04 09:18:12'),
       ('lisi', '$2a$10$q8HB8XHmmDvHaFC4L4LMze3bWVH.JFkpaSf123Vz.iHWc9Ag.z9de', '李四', 0, NULL, '325423@qq.com',
        '17485984758', 0, '', 1, '127.0.0.1', '2024-03-28 16:23:44', 'admin', '2024-03-26 20:53:32', NULL,
        '2024-08-04 09:09:59'),
       ('wnhyang', '$2a$10$uqU5jmFmn6BoFa2vraUXyuiwXfgB23LRK7OrSWhHCOROK4qxyEifi', '无奈何杨', 0, NULL,
        'wnhyang@163.com', '17317430553', 1, '', 1, '127.0.0.1', '2024-06-30 15:22:04', 'admin', '2024-06-30 15:11:38',
        'admin', '2024-08-03 22:31:41');
INSERT INTO coolGuard.sys_user_role (user_id, role_id, creator, create_time, updater, update_time)
VALUES (1, 1, '', '2024-03-25 08:59:56', '', '2024-03-25 08:59:56'),
       (2, 1, 'admin', '2024-03-26 13:40:16', 'admin', '2024-03-26 20:50:28'),
       (2, 2, 'admin', '2024-03-26 20:50:29', 'admin', '2024-08-04 09:18:11'),
       (3, 2, 'admin', '2024-03-26 20:53:32', 'admin', '2024-08-04 09:09:59'),
       (4, 1, 'admin', '2024-06-30 15:11:38', 'admin', '2024-06-30 15:22:37'),
       (4, 2, 'admin', '2024-06-30 15:22:38', 'admin', '2024-06-30 15:27:55'),
       (4, 3, 'admin', '2024-06-30 15:27:55', 'admin', '2024-06-30 15:27:55'),
       (3, 2, NULL, '2024-08-04 09:09:59', NULL, '2024-08-04 09:09:59'),
       (2, 2, NULL, '2024-08-04 09:18:12', NULL, '2024-08-04 09:18:12');
