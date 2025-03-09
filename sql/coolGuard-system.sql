create table coolGuard.sys_dict_data
(
    id          bigint auto_increment comment '字典数据id'
        primary key,
    sort        int          default 99                not null comment '字典排序',
    name        varchar(100) default ''                not null comment '字典标签',
    code        varchar(100) default ''                not null comment '字典键值',
    type_code   varchar(100)                           not null comment '字典类型',
    color       varchar(20)  default 'default'         not null comment '颜色',
    status      bit          default b'0'              not null comment '状态',
    remark      varchar(100)                           null comment '备注',
    creator     varchar(30)  default ''                null comment '创建者',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30)  default ''                null comment '更新者',
    update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '字典数据表' charset = utf8mb4;

create table coolGuard.sys_dict_type
(
    id          bigint auto_increment comment '字典类型id'
        primary key,
    name        varchar(100) default ''                not null comment '字典名',
    code        varchar(100) default ''                not null comment '字典值',
    standard    bit          default b'0'              null comment '是否内置',
    remark      varchar(100)                           null comment '备注',
    creator     varchar(30)  default ''                null comment '创建者',
    create_time datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30)  default ''                null comment '更新者',
    update_time datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint dict_type
        unique (code)
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
    query_param varchar(3000)                         null comment '菜单所携带的参数',
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
    id             bigint auto_increment comment '操作日志id'
        primary key,
    trace_id       varchar(32)                            null comment '链路id',
    user_id        bigint                                 not null comment '用户编号',
    user_nickname  varchar(30)                            null comment '用户昵称',
    module         varchar(50)                            not null comment '模块标题',
    name           varchar(50)                            not null comment '操作名',
    type           int          default 0                 not null comment '操作分类',
    request_method varchar(16)  default ''                null comment '请求方法名',
    request_url    varchar(255) default ''                null comment '请求地址',
    request_params varchar(3000)                          null comment '请求参数',
    user_ip        varchar(50)                            null comment '用户 IP',
    user_agent     varchar(200)                           null comment '浏览器 UA',
    start_time     datetime                               not null comment '操作时间',
    end_time       datetime                               null comment '结束时间',
    duration       int                                    not null comment '执行时长',
    result_code    int          default 0                 not null comment '结果码',
    result_msg     varchar(512) default ''                null comment '结果提示',
    result_data    varchar(3000)                          null,
    creator        varchar(30)  default ''                null comment '创建者',
    create_time    datetime     default CURRENT_TIMESTAMP not null comment '创建时间',
    updater        varchar(30)  default ''                null comment '更新者',
    update_time    datetime     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '操作日志记录' charset = utf8mb4;

create table coolGuard.sys_param
(
    id          bigint auto_increment comment '参数id'
        primary key,
    name        varchar(30) default ''                not null comment '参数名',
    code        varchar(30) default ''                not null comment '参数code',
    type        varchar(10) default 'json'            not null comment '参数类型',
    data        varchar(500)                          not null comment '参数数据',
    standard    bit         default b'0'              not null comment '标准',
    description varchar(100)                          null comment '描述',
    creator     varchar(30) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '参数表' charset = utf8mb4;

create table coolGuard.sys_role
(
    id          bigint auto_increment comment '角色id'
        primary key,
    name        varchar(30)                           not null comment '角色名称',
    code        varchar(30)                           not null comment '角色权限字符串',
    sort        int         default 99                not null comment '显示顺序',
    status      bit         default b'0'              not null comment '角色状态',
    remark      varchar(200)                          null comment '备注',
    creator     varchar(30) default ''                null comment '创建者',
    create_time datetime    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(30) default ''                null comment '更新者',
    update_time datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
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

