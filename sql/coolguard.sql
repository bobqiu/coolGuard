create table de_access
(
    id                bigint auto_increment comment '自增编号'
        primary key,
    name              varchar(32) charset utf8mb4 default ''                not null comment '服务名',
    code              varchar(32) charset utf8mb4 default ''                not null comment 'code',
    input_field_list  varchar(5000)                                         null,
    output_field_list varchar(5000)                                         null,
    test_params       varchar(1000)                                         null comment '测试参数',
    description       varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator           varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time       datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater           varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time       datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '接入表';

create table de_application
(
    id          bigint auto_increment comment '主键'
        primary key,
    name        varchar(32)                 default ''                not null comment '应用名',
    code        varchar(32)                 default ''                not null comment 'code',
    secret      varchar(64)                 default ''                not null comment '密钥',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '应用表';

create table de_chain
(
    id               bigint auto_increment comment '主键'
        primary key,
    application_name varchar(32)                 default 'coolguard'       not null comment '应用名',
    chain_name       varchar(64)                 default ''                not null comment 'chain名',
    el_data          varchar(3000)               default ''                not null comment 'el数据',
    enable           bit                         default b'1'              not null comment 'chain状态',
    description      varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator          varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time      datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater          varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time      datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (chain_name)
)
    comment 'chain表';

create table de_disposal
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(32)                 default ''                not null comment '处置编码',
    name        varchar(32)                 default ''                not null comment '处置名',
    grade       int                         default 1                 not null comment '风险等级',
    color       varchar(32)                                           null comment '颜色',
    standard    bit                         default b'0'              not null comment '标准',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '处置表';

create table de_field
(
    id            bigint auto_increment comment '主键'
        primary key,
    name          varchar(32) charset utf8mb4 default ''                not null comment '字段名',
    code          varchar(32) charset utf8mb4 default ''                not null comment 'code',
    group_code    varchar(32)                                           not null comment '字段分组code',
    standard      bit                         default b'0'              not null comment '是否标准字段',
    type          varchar(5)                                            not null comment '字段类型',
    info          varchar(1000)                                         null comment '字段信息',
    description   varchar(64) charset utf8mb4 default ''                null comment '描述',
    default_value varchar(64) charset utf8mb4                           null comment '默认值',
    dynamic       bit                         default b'0'              not null comment '是否动态字段(0否1是)',
    script        varchar(5000)                                         null comment '动态字段脚本',
    creator       varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '字段表';

create table de_field_group
(
    id          bigint auto_increment comment '自增编号'
        primary key,
    name        varchar(32) charset utf8mb4 default ''                not null comment '分组名',
    code        varchar(32) charset utf8mb4 default ''                not null comment 'code',
    standard    bit                         default b'0'              not null comment '标准',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '字段分组表';

create table de_indicator
(
    id             bigint auto_increment comment '自增编号'
        primary key,
    code           varchar(32)                                            not null comment 'code',
    name           varchar(32) charset utf8mb4  default ''                not null comment '指标名',
    publish        bit                          default b'0'              not null comment '发布',
    type           varchar(10)                                            null comment '类型',
    calc_field     varchar(32) charset utf8mb4  default ''                null comment '计算字段',
    return_type    varchar(1)                   default 'F'               null comment '返回类型',
    return_flag    varchar(10)                  default 'earliest'        null comment '返回取值方式',
    win_size       varchar(5) charset utf8mb4   default ''                not null comment '窗口大小',
    win_type       varchar(10) charset utf8mb4  default ''                not null comment '窗口类型',
    win_count      int                          default 0                 not null comment '窗口数量',
    time_slice     bigint                       default 0                 not null comment '时间片',
    master_field   varchar(32) charset utf8mb4  default ''                null comment '主字段',
    slave_fields   varchar(128) charset utf8mb4 default ''                null comment '从字段',
    compute_script varchar(64) charset utf8mb4  default ''                null comment '计算脚本',
    scenes         varchar(64)                                            not null comment '场景（,分割）',
    scene_type     varchar(32)                  default 'appName'         not null comment '场景类型',
    cond           varchar(2000)                                          null comment '条件',
    description    varchar(64) charset utf8mb4  default ''                null comment '描述',
    creator        varchar(32) charset utf8mb4  default ''                null comment '创建者',
    create_time    datetime                     default CURRENT_TIMESTAMP not null comment '创建时间',
    updater        varchar(32) charset utf8mb4  default ''                null comment '更新者',
    update_time    datetime                     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '指标表';

create table de_indicator_version
(
    id             bigint auto_increment comment '自增编号'
        primary key,
    code           varchar(32)                                            not null comment 'code',
    name           varchar(32) charset utf8mb4  default ''                not null comment '指标名',
    type           varchar(10)                                            null comment '类型',
    calc_field     varchar(32) charset utf8mb4  default ''                null comment '计算字段',
    return_type    varchar(1)                   default 'F'               null comment '返回类型',
    return_flag    varchar(10)                  default 'earliest'        null comment '返回取值方式',
    win_size       varchar(5) charset utf8mb4   default ''                not null comment '窗口大小',
    win_type       varchar(10) charset utf8mb4  default ''                not null comment '窗口类型',
    win_count      int                          default 0                 not null comment '窗口数量',
    time_slice     bigint                       default 0                 not null comment '时间片',
    master_field   varchar(32) charset utf8mb4  default ''                null comment '主字段',
    slave_fields   varchar(128) charset utf8mb4 default ''                null comment '从字段',
    compute_script varchar(64) charset utf8mb4  default ''                null comment '计算脚本',
    scenes         varchar(64)                                            not null comment '场景（,分割）',
    scene_type     varchar(32)                  default 'appName'         not null comment '场景类型',
    cond           varchar(2000)                                          null comment '条件',
    description    varchar(64) charset utf8mb4  default ''                null comment '描述',
    latest         bit                          default b'0'              not null comment '最新',
    version        int                          default 1                 not null comment '版本号',
    version_desc   varchar(64)                                            null comment '版本描述',
    creator        varchar(32) charset utf8mb4  default ''                null comment '创建者',
    create_time    datetime                     default CURRENT_TIMESTAMP not null comment '创建时间',
    updater        varchar(32) charset utf8mb4  default ''                null comment '更新者',
    update_time    datetime                     default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '指标表历史表';

create table de_list_data
(
    id            bigint auto_increment comment '主键'
        primary key,
    list_set_code varchar(32)                                           not null comment '名单集code',
    value         varchar(64)                                           not null comment '名单数据',
    source        varchar(10)                 default ''                not null comment '名单数据来源',
    status        bit                         default b'0'              not null comment '名单数据状态',
    description   varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator       varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '名单数据表';

create table de_list_set
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(32)                                           null comment '名单集code',
    name        varchar(32)                                           not null comment '名单集名',
    type        varchar(10)                                           not null comment '名单集类型',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '名单集表';

create table de_policy
(
    id              bigint auto_increment comment '主键'
        primary key,
    policy_set_code varchar(32)                                           not null comment '策略集编码',
    code            varchar(32)                 default ''                not null comment '策略编码',
    name            varchar(32)                 default ''                not null comment '策略名',
    mode            varchar(32)                 default 'order'           not null comment '策略模式',
    th_list         varchar(500)                                          null comment '阈值表',
    publish         bit                         default b'0'              not null comment '发布',
    description     varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator         varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time     datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater         varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time     datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '策略表';

create table de_policy_set
(
    id          bigint auto_increment comment '主键'
        primary key,
    app_name    varchar(32)                 default 'ALL'             not null comment '应用名',
    code        varchar(32)                 default ''                not null comment '策略集编码',
    name        varchar(32)                 default ''                not null comment '策略集名',
    publish     bit                         default b'0'              not null comment '发布',
    chain       varchar(5000)               default ''                not null comment '策略chain',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '策略集表';

create table de_policy_set_version
(
    id           bigint auto_increment comment '主键'
        primary key,
    app_name     varchar(32)                 default 'ALL'             not null comment '应用名',
    code         varchar(32)                 default ''                not null comment '策略集code',
    name         varchar(32)                 default ''                not null comment '策略集名',
    chain        varchar(5000)               default ''                not null comment '策略集链路',
    description  varchar(64) charset utf8mb4 default ''                null comment '描述',
    latest       bit                         default b'0'              not null comment '最新',
    version      int                         default 1                 not null comment '版本号',
    version_desc varchar(64)                                           null comment '版本描述',
    creator      varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time  datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater      varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time  datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '策略集表历史';

create table de_policy_version
(
    id              bigint auto_increment comment '主键'
        primary key,
    policy_set_code varchar(32)                                           not null comment '策略集编码',
    code            varchar(32)                 default ''                not null comment '策略编码',
    name            varchar(32)                 default ''                not null comment '策略名',
    mode            varchar(32)                 default 'order'           not null comment '策略模式',
    th_list         varchar(500)                                          null comment '阈值表',
    description     varchar(64) charset utf8mb4 default ''                null comment '描述',
    latest          bit                         default b'0'              not null comment '最新',
    version         int                         default 1                 not null comment '版本号',
    version_desc    varchar(64)                                           null comment '版本描述',
    creator         varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time     datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater         varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time     datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '策略版本表';

create table de_rule
(
    id            bigint auto_increment comment '主键'
        primary key,
    policy_code   varchar(32)                                           not null comment '策略编码',
    code          varchar(32)                 default ''                not null comment '规则编码',
    rule_id       varchar(6)                  default ''                not null comment '规则id',
    name          varchar(32)                 default ''                not null comment '规则名',
    disposal_code varchar(32)                                           not null comment '处置code',
    express       varchar(2000)                                         null comment '表达式',
    status        varchar(10)                 default 'off'             not null comment '状态',
    sort          int                         default 99                not null comment '排序',
    publish       bit                         default b'0'              not null comment '发布',
    cond          varchar(2000)                                         null comment '条件',
    rule_true     varchar(2000)                                         null comment 'true执行',
    rule_false    varchar(2000)                                         null comment 'false执行',
    description   varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator       varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '规则表';

create table de_rule_script
(
    id               bigint auto_increment comment '主键'
        primary key,
    application_name varchar(32)                 default ''                not null comment '脚本应用名',
    script_id        varchar(32)                 default ''                not null comment '脚本id',
    script_name      varchar(64)                 default ''                not null comment '脚本名',
    script_data      varchar(5000)                                         not null comment '脚本数据',
    script_type      varchar(16)                 default ''                not null comment '脚本类型',
    language         varchar(32)                 default ''                not null comment '脚本语言',
    enable           bit                         default b'0'              not null comment '脚本状态',
    description      varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator          varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time      datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater          varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time      datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '规则脚本表';

create table de_rule_version
(
    id            bigint auto_increment comment '主键'
        primary key,
    policy_code   varchar(32)                                           not null comment '策略编码',
    code          varchar(32)                 default ''                not null comment '规则编码',
    rule_id       varchar(6)                  default ''                not null comment '规则id',
    name          varchar(32)                 default ''                not null comment '规则名',
    disposal_code varchar(32)                                           not null comment '处置code',
    express       varchar(2000)                                         null comment '表达式',
    status        varchar(10)                 default 'off'             not null comment '状态',
    sort          int                         default 99                not null comment '排序',
    cond          varchar(2000)                                         null comment '条件',
    rule_true     varchar(2000)                                         null comment 'true执行',
    rule_false    varchar(2000)                                         null comment 'false执行',
    description   varchar(64) charset utf8mb4 default ''                null comment '描述',
    latest        bit                         default b'0'              not null comment '最新',
    version       int                         default 1                 not null comment '版本号',
    version_desc  varchar(64)                                           null comment '版本描述',
    creator       varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '规则版本表';

create table de_sms_flow
(
    id                bigint auto_increment comment '主键'
        primary key,
    event_seq_id      varchar(64)                 default ''                not null comment '事件流水号',
    sms_template_code varchar(64)                                           not null comment '消息模版编码',
    content           varchar(1000)               default ''                not null comment '消息内容',
    target            varchar(100)                                          null comment '消息目标',
    send_time         datetime                    default CURRENT_TIMESTAMP not null comment '发送时间',
    retry_times       int                                                   null comment '重试次数',
    status            varchar(30)                                           null comment '消息状态',
    error_message     varchar(200)                                          null comment '消息失败信息',
    creator           varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time       datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater           varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time       datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间'
)
    comment '消息流水表';

create table de_sms_template
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(32)                 default ''                not null comment '消息编码',
    name        varchar(32)                 default ''                not null comment '消息名',
    content     varchar(1000)               default ''                not null comment '消息内容',
    params      varchar(300)                                          null comment '消息参数',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '消息模版表';

create table de_tag
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(32)                 default ''                not null comment '标签编码',
    name        varchar(32)                 default ''                not null comment '标签名',
    color       varchar(32)                                           null comment '颜色',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(32) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(32) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint uk_code
        unique (code)
)
    comment '标签表';

