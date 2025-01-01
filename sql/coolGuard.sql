create schema coolGuard collate utf8mb4_general_ci;

create table coolGuard.de_access
(
    id            bigint auto_increment comment '自增编号'
        primary key,
    display_name  varchar(36) charset utf8mb4 default ''                not null comment '服务名',
    name          varchar(36) charset utf8mb4 default ''                not null comment 'name',
    input_config  varchar(10000) charset utf8mb4                        null comment '输入配置',
    output_config varchar(5000) charset utf8mb4                         null comment '输出配置',
    description   varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator       varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_name
        unique (name)
)
    comment '接入表';

create table coolGuard.de_application
(
    id           bigint auto_increment comment '主键'
        primary key,
    display_name varchar(36)                 default ''                not null comment '显示名',
    name         varchar(36)                 default ''                not null comment 'name',
    secret       varchar(64)                 default ''                not null comment '密钥',
    description  varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator      varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time  datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater      varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time  datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_name
        unique (name)
)
    comment '应用表';

create table coolGuard.de_chain
(
    id               bigint auto_increment comment '主键'
        primary key,
    application_name varchar(32)                 default 'coolGuard'       not null comment '应用名',
    chain_name       varchar(64)                 default ''                not null comment 'chain名',
    el_data          text                                                  not null comment 'el数据',
    enable           bit                         default b'1'              not null comment 'chain状态',
    description      varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator          varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time      datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater          varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time      datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (chain_name)
)
    comment 'chain表';

create table coolGuard.de_disposal
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(36)                 default ''                not null comment '处置编码',
    name        varchar(36)                 default ''                not null comment '处置名',
    grade       int                         default 1                 not null comment '风险等级',
    color       varchar(36)                                           null comment '颜色',
    standard    bit                         default b'0'              not null comment '标准',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '处置表';

create table coolGuard.de_field
(
    id            bigint auto_increment comment '主键'
        primary key,
    display_name  varchar(36) charset utf8mb4 default ''                not null comment '字段名',
    name          varchar(36) charset utf8mb4 default ''                not null comment 'name',
    group_name    varchar(36)                                           not null comment '字段分组name',
    standard      bit                         default b'0'              not null comment '是否标准字段',
    type          varchar(5)                                            not null comment '字段类型',
    info          varchar(1000)                                         null comment '字段信息',
    description   varchar(64) charset utf8mb4 default ''                null comment '描述',
    default_value varchar(64) charset utf8mb4                           null comment '默认值',
    dynamic       bit                         default b'0'              not null comment '是否动态字段(0否1是)',
    script        varchar(5000)                                         null comment '动态字段脚本',
    creator       varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_name
        unique (name)
)
    comment '字段表';

create table coolGuard.de_field_group
(
    id           bigint auto_increment comment '自增编号'
        primary key,
    display_name varchar(36) charset utf8mb4 default ''                not null comment '分组名',
    name         varchar(36) charset utf8mb4 default ''                not null comment '分组name',
    standard     bit                         default b'0'              not null comment '标准',
    description  varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator      varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time  datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater      varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time  datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_name
        unique (name)
)
    comment '字段分组表';

create table coolGuard.de_indicator
(
    id             bigint auto_increment comment '自增编号'
        primary key,
    code           varchar(36)                                           not null comment 'code',
    name           varchar(36) charset utf8mb4 default ''                not null comment '指标名',
    publish        bit                         default b'0'              not null comment '发布',
    type           varchar(10)                                           null comment '类型',
    calc_field     varchar(36) charset utf8mb4 default ''                null comment '计算字段',
    return_type    varchar(1)                  default 'F'               null comment '返回类型',
    return_flag    varchar(10)                 default 'earliest'        null comment '返回取值方式',
    win_size       varchar(5) charset utf8mb4  default ''                not null comment '窗口大小',
    win_type       varchar(10) charset utf8mb4 default ''                not null comment '窗口类型',
    win_count      int                         default 0                 not null comment '窗口数量',
    time_slice     bigint                      default 0                 not null comment '时间片',
    master_field   varchar(36) charset utf8mb4 default ''                null comment '主字段',
    slave_fields   varchar(64) charset utf8mb4 default ''                null comment '从字段',
    compute_script varchar(64) charset utf8mb4 default ''                null comment '计算脚本',
    scenes         varchar(64)                                           not null comment '场景（,分割）',
    scene_type     varchar(32)                 default 'appName'         not null comment '场景类型',
    cond           varchar(2000)                                         null comment '条件',
    description    varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator        varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time    datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater        varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time    datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '指标表';

create table coolGuard.de_indicator_version
(
    id             bigint auto_increment comment '自增编号'
        primary key,
    code           varchar(36)                                           not null comment 'code',
    name           varchar(36) charset utf8mb4 default ''                not null comment '指标名',
    type           varchar(10)                                           null comment '类型',
    calc_field     varchar(36) charset utf8mb4 default ''                null comment '计算字段',
    return_type    varchar(1)                  default 'F'               null comment '返回类型',
    return_flag    varchar(10)                 default 'earliest'        null comment '返回取值方式',
    win_size       varchar(5) charset utf8mb4  default ''                not null comment '窗口大小',
    win_type       varchar(10) charset utf8mb4 default ''                not null comment '窗口类型',
    win_count      int                         default 0                 not null comment '窗口数量',
    time_slice     bigint                      default 0                 not null comment '时间片',
    master_field   varchar(36) charset utf8mb4 default ''                null comment '主字段',
    slave_fields   varchar(64) charset utf8mb4 default ''                null comment '从字段',
    compute_script varchar(64) charset utf8mb4 default ''                null comment '计算脚本',
    scenes         varchar(64)                                           not null comment '场景（,分割）',
    scene_type     varchar(32)                 default 'appName'         not null comment '场景类型',
    cond           varchar(2000)                                         null comment '条件',
    description    varchar(64) charset utf8mb4 default ''                null comment '描述',
    latest         bit                         default b'0'              not null comment '最新',
    version        int                         default 0                 not null comment '版本号',
    version_desc   varchar(64)                                           null comment '版本描述',
    creator        varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time    datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater        varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time    datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '指标表历史表';

create table coolGuard.de_list_data
(
    id            bigint auto_increment comment '主键'
        primary key,
    list_set_code varchar(30)                                           not null comment '名单集code',
    value         varchar(64)                                           not null comment '名单数据',
    source        varchar(10)                 default ''                not null comment '名单数据来源',
    status        bit                         default b'0'              not null comment '名单数据状态',
    description   varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator       varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '名单数据表';

create table coolGuard.de_list_set
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(30)                                           null comment '名单集code',
    name        varchar(36)                                           not null comment '名单集名',
    type        varchar(10)                                           not null comment '名单集类型',
    status      bit                         default b'0'              not null comment '名单集状态',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '名单集表';

create table coolGuard.de_policy
(
    id              bigint auto_increment comment '主键'
        primary key,
    policy_set_code varchar(36)                                           not null comment '策略集编码',
    code            varchar(36)                 default ''                not null comment '策略编码',
    name            varchar(36)                 default ''                not null comment '策略名',
    mode            varchar(32)                 default 'order'           not null comment '策略模式',
    th_list         varchar(500)                                          null comment '阈值表',
    description     varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator         varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time     datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater         varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time     datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '策略表';

create table coolGuard.de_policy_set
(
    id          bigint auto_increment comment '主键'
        primary key,
    app_name    varchar(32)                 default 'ALL'             not null comment '应用名',
    code        varchar(36)                 default ''                not null comment '策略集编码',
    name        varchar(36)                 default ''                not null comment '策略集名',
    publish     bit                         default b'0'              not null comment '发布',
    chain       varchar(5000)               default ''                not null comment '策略chain',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '策略集表';

create table coolGuard.de_policy_set_version
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(36)                 default ''                not null comment '策略集code',
    latest      bit                         default b'0'              not null comment '最新',
    chain       varchar(5000)               default ''                not null comment '策略集链路',
    version     int                         default 0                 not null comment '版本号',
    creator     varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '策略集表历史';

create table coolGuard.de_rule
(
    id            bigint auto_increment comment '主键'
        primary key,
    policy_code   varchar(36)                                           not null comment '策略编码',
    code          varchar(36)                 default ''                not null comment '规则编码',
    rule_id       varchar(6)                  default ''                not null comment '规则id',
    name          varchar(36)                 default ''                not null comment '规则名',
    disposal_code varchar(36)                                           not null comment '处置code',
    express       varchar(2000)                                         null comment '表达式',
    status        varchar(10)                 default 'off'             not null comment '状态',
    sort          int                         default 99                not null comment '排序',
    publish       bit                         default b'0'              not null comment '发布',
    cond          varchar(2000)                                         null comment '条件',
    rule_true     varchar(2000)                                         null comment 'true执行',
    rule_false    varchar(2000)                                         null comment 'false执行',
    description   varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator       varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '规则表';

create table coolGuard.de_rule_script
(
    id               bigint auto_increment comment '主键'
        primary key,
    application_name varchar(32)                 default ''                not null comment '脚本应用名',
    script_id        varchar(32)                 default ''                not null comment '脚本id',
    script_name      varchar(64)                 default ''                not null comment '脚本名',
    script_data      text                                                  not null comment '脚本数据',
    script_type      varchar(16)                 default ''                not null comment '脚本类型',
    language         varchar(32)                 default ''                not null comment '脚本语言',
    enable           bit                         default b'0'              not null comment '脚本状态',
    description      varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator          varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time      datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater          varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time      datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '规则脚本表';

create table coolGuard.de_rule_version
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(36)                 default ''                not null comment '规则编码',
    rule        text                                                  not null comment '规则',
    latest      bit                         default b'0'              not null comment '最新',
    version     int                         default 0                 not null comment '版本号',
    creator     varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '策略版本扩展表';

create table coolGuard.de_sms_template
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(36)                 default ''                not null comment '消息编码',
    name        varchar(36)                 default ''                not null comment '消息名',
    content     varchar(1000)               default ''                not null comment '消息内容',
    params      varchar(300)                                          null comment '消息参数',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '消息模版表';

create table coolGuard.de_tag
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(36)                 default ''                not null comment '标签编码',
    name        varchar(36)                 default ''                not null comment '标签名',
    color       varchar(36)                                           null comment '颜色',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '标签表';
INSERT INTO coolGuard.de_access (display_name, name, input_config, output_config, description, creator, create_time,
                                 updater, update_time)
VALUES ('公共接口', 'publicInterface',
        '[{"paramName":"appName","required":false,"fieldName":"N_S_appName"},{"paramName":"policySetCode","required":false,"fieldName":"N_S_policySetCode"},{"paramName":"policyCode","required":false,"fieldName":"N_S_policyCode"},{"paramName":"transTime","required":false,"fieldName":"N_D_transTime"},{"paramName":"transAmount","required":false,"fieldName":"N_F_transAmount"},{"paramName":"transSerialNo","required":false,"fieldName":"N_S_transSerialNo"},{"paramName":"payeeAccount","required":false,"fieldName":"N_S_payeeAccount"},{"paramName":"payerAccount","required":false,"fieldName":"N_S_payerAccount"},{"paramName":"payeeName","required":false,"fieldName":"N_S_payeeName"},{"paramName":"payerName","required":false,"fieldName":"N_S_payerName"},{"paramName":"payeeType","required":false,"fieldName":"N_S_payeeType"},{"paramName":"payerType","required":false,"fieldName":"N_S_payerType"},{"paramName":"payeeRiskRating","required":false,"fieldName":"N_S_payeeRiskRating"},{"paramName":"payerRiskRating","required":false,"fieldName":"N_S_payerRiskRating"},{"paramName":"payeeBankName","required":false,"fieldName":"N_S_payeeBankName"},{"paramName":"payerBankName","required":false,"fieldName":"N_S_payerBankName"},{"paramName":"payeeAddress","required":false,"fieldName":"N_S_payeeAddress"},{"paramName":"payerAddress","required":false,"fieldName":"N_S_payerAddress"},{"paramName":"payeePhoneNumber","required":false,"fieldName":"N_S_payeePhoneNumber"},{"paramName":"payerPhoneNumber","required":false,"fieldName":"N_S_payerPhoneNumber"},{"paramName":"payeeIDNumber","required":false,"fieldName":"N_S_payeeIDNumber"},{"paramName":"payerIDNumber","required":false,"fieldName":"N_S_payerIDNumber"},{"paramName":"payeeIDCountryRegion","required":false,"fieldName":"N_S_payeeIDCountryRegion"},{"paramName":"payerIDCountryRegion","required":false,"fieldName":"N_S_payerIDCountryRegion"},{"paramName":"ip","required":false,"fieldName":"N_S_ip"},{"paramName":"lonAndLat","required":false,"fieldName":"N_S_lonAndLat"},{"paramName":"transTimeHour","required":false,"fieldName":"D_N_transTimeHour"},{"paramName":"eventTime","required":true,"fieldName":"N_D_eventTime"}]',
        '', '公共服务配置', NULL, '2024-04-07 18:32:10', NULL, '2024-12-22 21:43:14'),
       ('test', 'test',
        '[{"paramName":"appName","required":false,"fieldName":"N_S_appName"},{"paramName":"policySetCode","required":false,"fieldName":"N_S_policySetCode"},{"paramName":"policyCode","required":false,"fieldName":"N_S_policyCode"},{"paramName":"transTime","required":false,"fieldName":"N_D_transTime"},{"paramName":"transAmount","required":false,"fieldName":"N_F_transAmount"},{"paramName":"transSerialNo","required":false,"fieldName":"N_S_transSerialNo"},{"paramName":"payeeAccount","required":false,"fieldName":"N_S_payeeAccount"},{"paramName":"payerAccount","required":false,"fieldName":"N_S_payerAccount"},{"paramName":"payeeName","required":false,"fieldName":"N_S_payeeName"},{"paramName":"payerName","required":false,"fieldName":"N_S_payerName"},{"paramName":"payeeType","required":false,"fieldName":"N_S_payeeType"},{"paramName":"payerType","required":false,"fieldName":"N_S_payerType"},{"paramName":"payeeRiskRating","required":false,"fieldName":"N_S_payeeRiskRating"},{"paramName":"payerRiskRating","required":false,"fieldName":"N_S_payerRiskRating"},{"paramName":"payeeBankName","required":false,"fieldName":"N_S_payeeBankName"},{"paramName":"payerBankName","required":false,"fieldName":"N_S_payerBankName"},{"paramName":"payeeAddress","required":false,"fieldName":"N_S_payeeAddress"},{"paramName":"payerAddress","required":false,"fieldName":"N_S_payerAddress"},{"paramName":"payeePhoneNumber","required":false,"fieldName":"N_S_payeePhoneNumber"},{"paramName":"payerPhoneNumber","required":false,"fieldName":"N_S_payerPhoneNumber"},{"paramName":"payeeIDNumber","required":false,"fieldName":"N_S_payeeIDNumber"},{"paramName":"payerIDNumber","required":false,"fieldName":"N_S_payerIDNumber"},{"paramName":"payeeIDCountryRegion","required":false,"fieldName":"N_S_payeeIDCountryRegion"},{"paramName":"payerIDCountryRegion","required":false,"fieldName":"N_S_payerIDCountryRegion"},{"paramName":"ip","required":false,"fieldName":"N_S_ip"},{"paramName":"lonAndLat","required":false,"fieldName":"N_S_lonAndLat"},{"paramName":"transTimeHour","required":false,"fieldName":"D_N_transTimeHour"},{"paramName":"eventTime","required":true,"fieldName":"N_D_eventTime"}]',
        NULL, '', NULL, '2024-11-28 18:37:50', NULL, '2024-12-22 21:43:14');
INSERT INTO coolGuard.de_application (display_name, name, secret, description, creator, create_time, updater,
                                      update_time)
VALUES ('手机', 'phone', 'thtrshtrshtaqq32t4y4hg', 'ewgwregw', NULL, '2024-11-17 13:41:18', NULL,
        '2024-11-17 13:41:18');
INSERT INTO coolGuard.de_disposal (code, name, grade, color, standard, description, creator, create_time, updater,
                                   update_time)
VALUES ('pass', '通过', 1, NULL, 0, '', '', '2024-04-08 17:03:25', '', '2024-11-16 09:42:40'),
       ('review', '人工审核', 15, 'fuchsia', 0, '', NULL, '2024-12-18 16:09:11', NULL, '2024-12-18 16:09:11'),
       ('face', '人脸', 45, 'fuchsia', 0, '', NULL, '2024-12-18 16:09:48', NULL, '2024-12-18 16:09:48'),
       ('video', '视频', 65, 'fuchsia', 0, '', NULL, '2024-12-18 16:10:02', NULL, '2024-12-18 16:10:02'),
       ('reject', '拒绝', 80, 'fuchsia', 0, '', NULL, '2024-12-18 16:10:17', NULL, '2024-12-18 16:10:17'),
       ('lock', '锁定', 120, 'fuchsia', 0, '', NULL, '2024-12-18 16:10:29', NULL, '2024-12-18 16:10:29');
INSERT INTO coolGuard.de_field (display_name, name, group_name, standard, `type`, info, description, default_value,
                                `dynamic`, script, creator, create_time, updater, update_time)
VALUES ('应用名', 'N_S_appName', 'CUST', 1, 'S', NULL, '应用名', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32'),
       ('交易时间', 'N_D_transTime', 'CUST', 1, 'D', NULL, '交易时间', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32'),
       ('交易金额', 'N_F_transAmount', 'CUST', 1, 'F', NULL, '交易金额', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32'),
       ('交易流水号', 'N_S_transSerialNo', 'CUST', 1, 'S', NULL, '交易流水号', NULL, 0, NULL, '', '2024-04-06 19:45:03',
        '', '2024-11-16 10:34:32'),
       ('收款方账户', 'N_S_payeeAccount', 'CUST', 1, 'S', NULL, '收款方账户', NULL, 0, NULL, '', '2024-04-06 19:45:03',
        '', '2024-11-16 10:34:32'),
       ('付款方账户', 'N_S_payerAccount', 'CUST', 1, 'S', NULL, '付款方账户', NULL, 0, NULL, '', '2024-04-06 19:45:03',
        '', '2024-11-16 10:34:32'),
       ('收款方名称', 'N_S_payeeName', 'CUST', 1, 'S', NULL, '收款方名称', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32'),
       ('付款方名称', 'N_S_payerName', 'CUST', 1, 'S', NULL, '付款方名称', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32'),
       ('收款方类型', 'N_S_payeeType', 'CUST', 1, 'S', NULL, '收款方类型', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32'),
       ('付款方类型', 'N_S_payerType', 'CUST', 1, 'S', NULL, '付款方类型', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32');
INSERT INTO coolGuard.de_field (display_name, name, group_name, standard, `type`, info, description, default_value,
                                `dynamic`, script, creator, create_time, updater, update_time)
VALUES ('收款方风险评级', 'N_S_payeeRiskRating', 'CUST', 1, 'S', NULL, '收款方风险评级', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32'),
       ('付款方风险评级', 'N_S_payerRiskRating', 'CUST', 1, 'S', NULL, '付款方风险评级', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32'),
       ('收款方银行名', 'N_S_payeeBankName', 'CUST', 1, 'S', NULL, '收款方银行名', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32'),
       ('付款方银行名', 'N_S_payerBankName', 'CUST', 1, 'S', NULL, '付款方银行名', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32'),
       ('收款方地址', 'N_S_payeeAddress', 'CUST', 1, 'S', NULL, '收款方地址', NULL, 0, NULL, '', '2024-04-06 19:45:03',
        '', '2024-11-16 10:34:32'),
       ('付款方地址', 'N_S_payerAddress', 'CUST', 1, 'S', NULL, '付款方地址', NULL, 0, NULL, '', '2024-04-06 19:45:03',
        '', '2024-11-16 10:34:32'),
       ('收款方手机号', 'N_S_payeePhoneNumber', 'CUST', 1, 'S', NULL, '收款方手机号', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32'),
       ('付款方手机号', 'N_S_payerPhoneNumber', 'CUST', 1, 'S', NULL, '付款方手机号', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32'),
       ('收款方证件号码', 'N_S_payeeIDNumber', 'CUST', 1, 'S', NULL, '收款方证件号码', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32'),
       ('付款方证件号码', 'N_S_payerIDNumber', 'CUST', 1, 'S', NULL, '付款方证件号码', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32');
INSERT INTO coolGuard.de_field (display_name, name, group_name, standard, `type`, info, description, default_value,
                                `dynamic`, script, creator, create_time, updater, update_time)
VALUES ('收款方证件所属国家/地区', 'N_S_payeeIDCountryRegion', 'CUST', 1, 'S', NULL, '收款方所属国家/地区', NULL, 0,
        NULL, '', '2024-04-06 19:45:03', '', '2024-11-16 10:34:32'),
       ('付款方证件所属国家/地区', 'N_S_payerIDCountryRegion', 'CUST', 1, 'S', NULL, '付款方所属国家/地区', NULL, 0,
        NULL, '', '2024-04-06 19:45:03', '', '2024-11-16 10:34:32'),
       ('策略集code', 'N_S_policySetCode', 'CUST', 1, 'S', NULL, '策略集code', NULL, 0, NULL, '', '2024-04-07 08:53:19',
        '', '2024-11-16 10:34:32'),
       ('策略code', 'N_S_policyCode', 'CUST', 1, 'S', NULL, '策略code', NULL, 0, NULL, '', '2024-04-07 08:53:19', '',
        '2024-11-16 10:34:32'),
       ('证件号所属省名称', 'N_S_idCardProvince', 'CUST', 1, 'S', NULL, '证件号所属省名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32'),
       ('证件号所属市名称', 'N_S_idCardCity', 'CUST', 1, 'S', NULL, '证件号所属市名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:31'),
       ('证件号所属区/县名称', 'N_S_idCardDistrict', 'CUST', 1, 'S', NULL, '证件号所属区/县名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32'),
       ('手机号号所属省名称', 'N_S_phoneNumberProvince', 'CUST', 1, 'S', NULL, '手机号所属省名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32'),
       ('手机号号所属市名称', 'N_S_phoneNumberCity', 'CUST', 1, 'S', NULL, '手机号所属市名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32'),
       ('手机号号ISP', 'N_S_phoneNumberIsp', 'CUST', 1, 'S', NULL, '手机号所属运营商', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32');
INSERT INTO coolGuard.de_field (display_name, name, group_name, standard, `type`, info, description, default_value,
                                `dynamic`, script, creator, create_time, updater, update_time)
VALUES ('IP所属国家名称', 'N_S_ipCountry', 'CUST', 1, 'S', NULL, 'IP归属国家名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32'),
       ('IP所属省名称', 'N_S_ipProvince', 'CUST', 1, 'S', NULL, 'IP归属省名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32'),
       ('IP所属市名称', 'N_S_ipCity', 'CUST', 1, 'S', NULL, 'IP归属市名称', NULL, 0, NULL, '', '2024-04-30 16:08:38',
        '', '2024-11-16 10:34:32'),
       ('IP所属isp', 'N_S_ipIsp', 'CUST', 1, 'S', NULL, 'IP归属运营商', NULL, 0, NULL, '', '2024-04-30 16:08:38', '',
        '2024-11-16 10:34:32'),
       ('经纬度', 'N_S_lonAndLat', 'CUST', 1, 'S', NULL, '经纬度字符串，格式必须是 "经度,纬度"', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32'),
       ('经纬度所在省名称', 'N_S_geoProvince', 'CUST', 1, 'S', NULL, '经纬度所在省名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32'),
       ('经纬度所在市名称', 'N_S_geoCity', 'CUST', 1, 'S', NULL, '经纬度所在市名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32'),
       ('经纬度所在区/县名称', 'N_S_geoDistrict', 'CUST', 1, 'S', NULL, '经纬度所在区/县名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32'),
       ('ip', 'N_S_ip', 'CUST', 1, 'S', NULL, 'ip', NULL, 0, NULL, '', '2024-04-30 16:38:40', '',
        '2024-11-16 10:34:32'),
       ('交易小时', 'D_N_transTimeHour', 'TR', 1, 'N', NULL, 'rgergwr', '0', 1, 'N_D_transTime.getHour()', NULL,
        '2024-12-18 16:35:47', NULL, '2024-12-19 14:23:54');
INSERT INTO coolGuard.de_field (display_name, name, group_name, standard, `type`, info, description, default_value,
                                `dynamic`, script, creator, create_time, updater, update_time)
VALUES ('事件时间', 'N_D_eventTime', 'TR', 1, 'D', NULL, '', 'now', 0, NULL, NULL, '2024-12-19 14:13:50', NULL,
        '2024-12-19 14:23:46');
INSERT INTO coolGuard.de_field_group (display_name, name, standard, description, creator, create_time, updater,
                                      update_time)
VALUES ('客户信息', 'CUST', 1, '', '', '2024-04-18 09:23:25', '', '2024-11-16 09:45:25'),
       ('账户信息', 'ACC', 1, '', '', '2024-04-18 09:23:25', '', '2024-11-16 09:45:25'),
       ('交易信息', 'TR', 1, '', '', '2024-04-18 09:23:25', '', '2024-11-16 09:45:25'),
       ('商户信息', 'MER', 1, '', '', '2024-04-18 09:23:25', '', '2024-11-16 09:45:25'),
       ('设备信息', 'DEV', 1, '', '', '2024-04-18 09:23:25', '', '2024-11-16 09:45:25');
