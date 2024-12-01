create schema coolGuard collate utf8mb4_general_ci;

create table coolGuard.de_access
(
    id            bigint auto_increment comment '自增编号'
        primary key,
    display_name  varchar(36) charset utf8mb4 default ''                not null comment '显示服务名',
    name          varchar(36) charset utf8mb4 default ''                not null comment '服务标识',
    input_config  varchar(10000) charset utf8mb4                        null comment '输入配置',
    output_config varchar(5000) charset utf8mb4                         null comment '输出配置',
    description   varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator       varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted       bit                         default b'0'              not null comment '是否删除',
    constraint uk_name
        unique (name)
)
    comment '接入表';

create table coolGuard.de_application
(
    id           bigint auto_increment comment '主键'
        primary key,
    display_name varchar(36)                 default ''                not null comment '显示名',
    name         varchar(36)                 default ''                not null comment '应用名',
    secret       varchar(64)                 default ''                not null comment '密钥',
    description  varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator      varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time  datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater      varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time  datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      bit                         default b'0'              not null comment '是否删除',
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
    deleted          bit                         default b'0'              not null comment '是否删除',
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
    deleted     bit                         default b'0'              not null comment '是否删除',
    constraint uk_code
        unique (code)
)
    comment '处置表';

create table coolGuard.de_field
(
    id            bigint auto_increment comment '主键'
        primary key,
    display_name  varchar(36) charset utf8mb4 default ''                not null comment '显示名',
    name          varchar(36) charset utf8mb4 default ''                not null comment '字段名',
    group_name    varchar(36)                                           not null comment '字段分组名',
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
    deleted       bit                         default b'0'              not null comment '是否删除',
    constraint uk_name
        unique (name)
)
    comment '字段表';

create table coolGuard.de_field_group
(
    id           bigint auto_increment comment '自增编号'
        primary key,
    display_name varchar(36) charset utf8mb4 default ''                not null comment '显示分组名',
    name         varchar(36) charset utf8mb4 default ''                not null comment '分组标识',
    standard     bit                         default b'0'              not null comment '标准',
    description  varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator      varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time  datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater      varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time  datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      bit                         default b'0'              not null comment '是否删除',
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
    status         bit                         default b'0'              not null comment '状态',
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
    deleted        bit                         default b'0'              not null comment '是否删除',
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
    status         bit                         default b'0'              not null comment '状态',
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
    description    varchar(64) charset utf8mb4 default ''                null comment '描述',
    version        int                         default 0                 not null comment '版本号',
    version_desc   varchar(64)                                           null comment '版本描述',
    cond           varchar(1024) charset utf8mb4                         null comment '条件',
    creator        varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time    datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater        varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time    datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted        bit                         default b'0'              not null comment '是否删除'
)
    comment '指标表历史表';

create table coolGuard.de_list_data
(
    id          bigint auto_increment comment '主键'
        primary key,
    list_set_id bigint                                                not null comment '名单集id',
    value       varchar(64)                                           not null comment '名单数据',
    source      varchar(10)                 default ''                not null comment '名单数据来源',
    status      bit                         default b'0'              not null comment '名单数据状态',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit                         default b'0'              not null comment '是否删除'
)
    comment '名单数据表';

create table coolGuard.de_list_set
(
    id          bigint auto_increment comment '主键'
        primary key,
    name        varchar(36)                                           not null comment '名单集名',
    type        varchar(10)                                           not null comment '名单集类型',
    status      bit                         default b'0'              not null comment '名单集状态',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit                         default b'0'              not null comment '是否删除'
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
    status          bit                         default b'0'              not null comment '策略状态',
    description     varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator         varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time     datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater         varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time     datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted         bit                         default b'0'              not null comment '是否删除',
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
    status      bit                         default b'0'              not null comment '策略集状态',
    chain       varchar(5000)               default ''                not null comment '策略chain',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit                         default b'0'              not null comment '是否删除',
    constraint uk_code
        unique (code)
)
    comment '策略集表';

create table coolGuard.de_policy_set_version
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(36)                 default ''                not null comment '策略集code',
    status      bit                         default b'0'              not null comment '策略集状态',
    chain       varchar(5000)               default ''                not null comment '策略集链路',
    version     int                         default 0                 not null comment '版本号',
    creator     varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit                         default b'0'              not null comment '是否删除'
)
    comment '策略集表历史';

create table coolGuard.de_rule
(
    id            bigint auto_increment comment '主键'
        primary key,
    policy_code   varchar(36)                                           not null comment '策略编码',
    code          varchar(36)                 default ''                not null comment '规则编码',
    name          varchar(36)                 default ''                not null comment '规则名',
    disposal_code varchar(36)                                           not null comment '处置code',
    score         int                         default 0                 not null comment '得分',
    status        varchar(10)                 default 'off'             not null comment '状态',
    sort          int                         default 99                not null comment '排序',
    cond          varchar(2000)                                         null comment '条件',
    description   varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator       varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted       bit                         default b'0'              not null comment '是否删除',
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
    update_time      datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted          bit                         default b'0'              not null comment '是否删除'
)
    comment '规则脚本表';

create table coolGuard.de_rule_version
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(36)                 default ''                not null comment '规则编码',
    rule        text                                                  not null comment '规则',
    status      bit                         default b'0'              not null comment '状态',
    version     int                         default 0                 not null comment '版本号',
    creator     varchar(36) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(36) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit                         default b'0'              not null comment '是否删除'
)
    comment '策略版本扩展表';

INSERT INTO coolGuard.de_access (display_name, name, input_config, output_config, description, creator, create_time,
                                 updater, update_time, deleted)
VALUES ('公共接口', 'publicInterface',
        '[{"paramName":"appName","required":false,"fieldName":"N_S_appName"},{"paramName":"policySetCode","required":false,"fieldName":"N_S_policySetCode"},{"paramName":"policyCode","required":false,"fieldName":"N_S_policyCode"},{"paramName":"transTime","required":false,"fieldName":"N_D_transTime"},{"paramName":"transAmount","required":false,"fieldName":"N_F_transAmount"},{"paramName":"transSerialNo","required":false,"fieldName":"N_S_transSerialNo"},{"paramName":"payeeAccount","required":false,"fieldName":"N_S_payeeAccount"},{"paramName":"payerAccount","required":false,"fieldName":"N_S_payerAccount"},{"paramName":"payeeName","required":false,"fieldName":"N_S_payeeName"},{"paramName":"payerName","required":false,"fieldName":"N_S_payerName"},{"paramName":"payeeType","required":false,"fieldName":"N_S_payeeType"},{"paramName":"payerType","required":false,"fieldName":"N_S_payerType"},{"paramName":"payeeRiskRating","required":false,"fieldName":"N_S_payeeRiskRating"},{"paramName":"payerRiskRating","required":false,"fieldName":"N_S_payerRiskRating"},{"paramName":"payeeBankName","required":false,"fieldName":"N_S_payeeBankName"},{"paramName":"payerBankName","required":false,"fieldName":"N_S_payerBankName"},{"paramName":"payeeAddress","required":false,"fieldName":"N_S_payeeAddress"},{"paramName":"payerAddress","required":false,"fieldName":"N_S_payerAddress"},{"paramName":"payeePhoneNumber","required":false,"fieldName":"N_S_payeePhoneNumber"},{"paramName":"payerPhoneNumber","required":false,"fieldName":"N_S_payerPhoneNumber"},{"paramName":"payeeIDNumber","required":false,"fieldName":"N_S_payeeIDNumber"},{"paramName":"payerIDNumber","required":false,"fieldName":"N_S_payerIDNumber"},{"paramName":"payeeIDCountryRegion","required":false,"fieldName":"N_S_payeeIDCountryRegion"},{"paramName":"payerIDCountryRegion","required":false,"fieldName":"N_S_payerIDCountryRegion"},{"paramName":"ip","required":false,"fieldName":"N_S_ip"},{"paramName":"lonAndLat","required":false,"fieldName":"N_S_lonAndLat"}]',
        '', '公共服务配置', NULL, '2024-04-07 18:32:10', NULL, '2024-11-28 18:22:08', 0),
       ('', 'test',
        '[{"paramName":"erger","required":true,"fieldName":"regtrh"},{"paramName":"regr","required":false,"fieldName":"erhryhj"}]',
        NULL, '', NULL, '2024-11-28 18:37:50', NULL, '2024-11-28 18:37:50', 0);
INSERT INTO coolGuard.de_application (display_name, name, secret, description, creator, create_time, updater,
                                      update_time, deleted)
VALUES ('手机', 'phone', 'thtrshtrshtaqq32t4y4hg', 'ewgwregw', NULL, '2024-11-17 13:41:18', NULL, '2024-11-17 13:41:18',
        0);
INSERT INTO coolGuard.de_chain (application_name, chain_name, el_data, enable, description, creator, create_time,
                                updater, update_time, deleted)
VALUES ('coolGuard', 'R_C#f1df5b70574a46a09b95f3662cffaed9',
        'IF(OR(c_cn.data(''{"type":"normal","value":"N_S_payerAccount","logicType":"not_null","expectType":"","expectValue":""}''), c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}'')),r_tcn,r_fcn);',
        1, '', '', '2024-04-08 15:34:32', '', '2024-11-16 12:16:51', 0),
       ('coolGuard', 'R_C#0b30510bf425492381a78aca098be029',
        'IF(AND(c_cn.data(''{"type":"normal","value":"N_S_appName","logicType":"eq","expectType":"input","expectValue":"phone"}''),c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"lt","expectType":"input","expectValue":"100"}'')),r_tcn,r_fcn);',
        1, '', '', '2024-04-08 15:34:32', '', '2024-11-16 12:16:51', 0),
       ('coolGuard', 'R_C#6ae5cb587c784326b45c944e80319d50',
        'IF(c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"gte","expectType":"input","expectValue":"20.0"}''),r_tcn,r_fcn);',
        1, '', '', '2024-04-09 10:57:57', '', '2024-11-16 12:16:51', 0),
       ('coolGuard', 'A_C#publicInterface', 'THEN(a_icn,nf_cn,df_cn,I_F,ps_cn,a_ocn);', 1, '', '',
        '2024-04-09 15:34:47', '', '2024-11-16 11:32:51', 0),
       ('coolGuard', 'I_C#b75c84a6b5ec45ed8026ca7c873e789c',
        'IF(c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"lt","expectType":"input","expectValue":"1000"}''),i_tcn,i_fcn);',
        1, '', '', '2024-05-07 11:23:16', '', '2024-11-16 12:16:51', 0),
       ('coolGuard', 'PS_C#phone_login', 'WHEN(e_cn,p_cn.tag("phone_login_worst"),p_cn.tag("phone_login_order"));', 1,
        '', '', '2024-07-20 11:06:01', '', '2024-11-30 20:55:31', 0),
       ('coolGuard', 'I_C#72df2843a1094b2c8b8f6c4aebeb63dd',
        'IF(c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"15"}''),i_tcn,i_fcn);',
        1, '', '', '2024-07-20 11:23:46', '', '2024-11-16 12:16:51', 0),
       ('coolGuard', 'I_C#d43b398568a24d9dbc82aae162f41ed6',
        'IF(c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"1000"}''),i_tcn,i_fcn);',
        1, '', NULL, '2024-08-03 20:01:18', NULL, '2024-11-16 12:16:51', 0),
       ('coolGuard', 'I_F', 'FOR(i_fn).parallel(true).DO(i_cn);', 1, '', '', '2024-08-20 14:20:43', '',
        '2024-08-20 14:24:51', 0),
       ('coolGuard', 'P_FP', 'FOR(p_fn).parallel(true).DO(r_cn);', 1, '', '', '2024-08-20 17:31:38', '',
        '2024-08-20 19:06:39', 0);
INSERT INTO coolGuard.de_chain (application_name, chain_name, el_data, enable, description, creator, create_time,
                                updater, update_time, deleted)
VALUES ('coolGuard', 'P_F', 'FOR(p_fn).DO(r_cn).BREAK(p_bn);', 1, '', '', '2024-08-20 17:31:38', '',
        '2024-08-22 08:33:39', 0),
       ('coolGuard', 'I_C#cd420db94e1948c59ea95c535b682975',
        'IF(c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"lt","expectType":"input","expectValue":"100000"}''),i_tcn,i_fcn);',
        1, '', NULL, '2024-08-21 17:30:23', NULL, '2024-11-16 12:16:51', 0),
       ('coolGuard', 'I_C#fc0547d4e57e431082314c1c1b28b121',
        'IF(c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}''),i_tcn,i_fcn);',
        1, '', NULL, '2024-08-22 10:33:18', NULL, '2024-11-16 12:16:51', 0),
       ('coolGuard', 'I_C#c689576cf38d45888729c537697897af',
        'IF(c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}''),i_tcn,i_fcn);',
        1, '', NULL, '2024-08-22 10:39:29', NULL, '2024-11-16 12:16:51', 0),
       ('coolGuard', 'I_C#b02b2000723e422fbcafbf2cd671cd0b',
        'IF(c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}''),i_tcn,i_fcn);',
        1, '', NULL, '2024-08-22 10:39:33', NULL, '2024-11-16 12:16:51', 0),
       ('coolGuard', 'I_C#9d07af28e7624571b89c9eacd65bac2d',
        'IF(c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}''),i_tcn,i_fcn);',
        1, '', NULL, '2024-08-22 10:39:39', NULL, '2024-11-16 12:16:51', 0),
       ('coolGuard', 'I_C#a7f924197fb0497ea8718c386d1c7db7',
        'IF(c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}''),i_tcn,i_fcn);',
        1, '', NULL, '2024-08-22 10:39:59', NULL, '2024-11-16 12:16:51', 0),
       ('coolGuard', 'I_C#c161c3943d4449408f75843fff68af4e',
        'IF(c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}''),i_tcn,i_fcn);',
        1, '', NULL, '2024-08-22 10:40:33', NULL, '2024-11-16 12:16:51', 0),
       ('coolGuard', 'I_C#4025845e0cdc498284f0da08c3649242',
        'IF(c_cn.data(''{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"1"}''),i_tcn,i_fcn);',
        1, '', NULL, '2024-11-14 18:41:22', NULL, '2024-11-16 12:16:51', 0);
INSERT INTO coolGuard.de_disposal (code, name, grade, color, standard, description, creator, create_time, updater,
                                   update_time, deleted)
VALUES ('pass', '通过', 1, NULL, 0, '', '', '2024-04-08 17:03:25', '', '2024-11-16 09:42:40', 0);
INSERT INTO coolGuard.de_field (display_name, name, group_name, standard, `type`, info, description, default_value,
                                `dynamic`, script, creator, create_time, updater, update_time, deleted)
VALUES ('应用名', 'N_S_appName', 'CUST', 1, 'S', NULL, '应用名', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32', 0),
       ('交易时间', 'N_D_transTime', 'CUST', 1, 'D', NULL, '交易时间', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32', 0),
       ('交易金额', 'N_F_transAmount', 'CUST', 1, 'F', NULL, '交易金额', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32', 0),
       ('交易流水号', 'N_S_transSerialNo', 'CUST', 1, 'S', NULL, '交易流水号', NULL, 0, NULL, '', '2024-04-06 19:45:03',
        '', '2024-11-16 10:34:32', 0),
       ('收款方账户', 'N_S_payeeAccount', 'CUST', 1, 'S', NULL, '收款方账户', NULL, 0, NULL, '', '2024-04-06 19:45:03',
        '', '2024-11-16 10:34:32', 0),
       ('付款方账户', 'N_S_payerAccount', 'CUST', 1, 'S', NULL, '付款方账户', NULL, 0, NULL, '', '2024-04-06 19:45:03',
        '', '2024-11-16 10:34:32', 0),
       ('收款方名称', 'N_S_payeeName', 'CUST', 1, 'S', NULL, '收款方名称', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32', 0),
       ('付款方名称', 'N_S_payerName', 'CUST', 1, 'S', NULL, '付款方名称', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32', 0),
       ('收款方类型', 'N_S_payeeType', 'CUST', 1, 'S', NULL, '收款方类型', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32', 0),
       ('付款方类型', 'N_S_payerType', 'CUST', 1, 'S', NULL, '付款方类型', NULL, 0, NULL, '', '2024-04-06 19:45:03', '',
        '2024-11-16 10:34:32', 0);
INSERT INTO coolGuard.de_field (display_name, name, group_name, standard, `type`, info, description, default_value,
                                `dynamic`, script, creator, create_time, updater, update_time, deleted)
VALUES ('收款方风险评级', 'N_S_payeeRiskRating', 'CUST', 1, 'S', NULL, '收款方风险评级', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32', 0),
       ('付款方风险评级', 'N_S_payerRiskRating', 'CUST', 1, 'S', NULL, '付款方风险评级', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32', 0),
       ('收款方银行名', 'N_S_payeeBankName', 'CUST', 1, 'S', NULL, '收款方银行名', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32', 0),
       ('付款方银行名', 'N_S_payerBankName', 'CUST', 1, 'S', NULL, '付款方银行名', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32', 0),
       ('收款方地址', 'N_S_payeeAddress', 'CUST', 1, 'S', NULL, '收款方地址', NULL, 0, NULL, '', '2024-04-06 19:45:03',
        '', '2024-11-16 10:34:32', 0),
       ('付款方地址', 'N_S_payerAddress', 'CUST', 1, 'S', NULL, '付款方地址', NULL, 0, NULL, '', '2024-04-06 19:45:03',
        '', '2024-11-16 10:34:32', 0),
       ('收款方手机号', 'N_S_payeePhoneNumber', 'CUST', 1, 'S', NULL, '收款方手机号', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32', 0),
       ('付款方手机号', 'N_S_payerPhoneNumber', 'CUST', 1, 'S', NULL, '付款方手机号', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32', 0),
       ('收款方证件号码', 'N_S_payeeIDNumber', 'CUST', 1, 'S', NULL, '收款方证件号码', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32', 0),
       ('付款方证件号码', 'N_S_payerIDNumber', 'CUST', 1, 'S', NULL, '付款方证件号码', NULL, 0, NULL, '',
        '2024-04-06 19:45:03', '', '2024-11-16 10:34:32', 0);
INSERT INTO coolGuard.de_field (display_name, name, group_name, standard, `type`, info, description, default_value,
                                `dynamic`, script, creator, create_time, updater, update_time, deleted)
VALUES ('收款方证件所属国家/地区', 'N_S_payeeIDCountryRegion', 'CUST', 1, 'S', NULL, '收款方所属国家/地区', NULL, 0,
        NULL, '', '2024-04-06 19:45:03', '', '2024-11-16 10:34:32', 0),
       ('付款方证件所属国家/地区', 'N_S_payerIDCountryRegion', 'CUST', 1, 'S', NULL, '付款方所属国家/地区', NULL, 0,
        NULL, '', '2024-04-06 19:45:03', '', '2024-11-16 10:34:32', 0),
       ('策略集code', 'N_S_policySetCode', 'CUST', 1, 'S', NULL, '策略集code', NULL, 0, NULL, '', '2024-04-07 08:53:19',
        '', '2024-11-16 10:34:32', 0),
       ('策略code', 'N_S_policyCode', 'CUST', 1, 'S', NULL, '策略code', NULL, 0, NULL, '', '2024-04-07 08:53:19', '',
        '2024-11-16 10:34:32', 0),
       ('证件号所属省名称', 'N_S_idCardProvince', 'CUST', 1, 'S', NULL, '证件号所属省名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32', 0),
       ('证件号所属市名称', 'N_S_idCardCity', 'CUST', 1, 'S', NULL, '证件号所属市名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:31', 0),
       ('证件号所属区/县名称', 'N_S_idCardDistrict', 'CUST', 1, 'S', NULL, '证件号所属区/县名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32', 0),
       ('手机号号所属省名称', 'N_S_phoneNumberProvince', 'CUST', 1, 'S', NULL, '手机号所属省名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32', 0),
       ('手机号号所属市名称', 'N_S_phoneNumberCity', 'CUST', 1, 'S', NULL, '手机号所属市名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32', 0),
       ('手机号号ISP', 'N_S_phoneNumberIsp', 'CUST', 1, 'S', NULL, '手机号所属运营商', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32', 0);
INSERT INTO coolGuard.de_field (display_name, name, group_name, standard, `type`, info, description, default_value,
                                `dynamic`, script, creator, create_time, updater, update_time, deleted)
VALUES ('IP所属国家名称', 'N_S_ipCountry', 'CUST', 1, 'S', NULL, 'IP归属国家名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32', 0),
       ('IP所属省名称', 'N_S_ipProvince', 'CUST', 1, 'S', NULL, 'IP归属省名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32', 0),
       ('IP所属市名称', 'N_S_ipCity', 'CUST', 1, 'S', NULL, 'IP归属市名称', NULL, 0, NULL, '', '2024-04-30 16:08:38',
        '', '2024-11-16 10:34:32', 0),
       ('IP所属isp', 'N_S_ipIsp', 'CUST', 1, 'S', NULL, 'IP归属运营商', NULL, 0, NULL, '', '2024-04-30 16:08:38', '',
        '2024-11-16 10:34:32', 0),
       ('经纬度', 'N_S_lonAndLat', 'CUST', 1, 'S', NULL, '经纬度字符串，格式必须是 "经度,纬度"', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32', 0),
       ('经纬度所在省名称', 'N_S_geoProvince', 'CUST', 1, 'S', NULL, '经纬度所在省名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32', 0),
       ('经纬度所在市名称', 'N_S_geoCity', 'CUST', 1, 'S', NULL, '经纬度所在市名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32', 0),
       ('经纬度所在区/县名称', 'N_S_geoDistrict', 'CUST', 1, 'S', NULL, '经纬度所在区/县名称', NULL, 0, NULL, '',
        '2024-04-30 16:08:38', '', '2024-11-16 10:34:32', 0),
       ('ip', 'N_S_ip', 'CUST', 1, 'S', NULL, 'ip', NULL, 0, NULL, '', '2024-04-30 16:38:40', '', '2024-11-16 10:34:32',
        0);
INSERT INTO coolGuard.de_field_group (display_name, name, standard, description, creator, create_time, updater,
                                      update_time, deleted)
VALUES ('客户信息', 'CUST', 1, '', '', '2024-04-18 09:23:25', '', '2024-11-16 09:45:25', 0),
       ('账户信息', 'ACC', 1, '', '', '2024-04-18 09:23:25', '', '2024-11-16 09:45:25', 0),
       ('交易信息', 'TR', 1, '', '', '2024-04-18 09:23:25', '', '2024-11-16 09:45:25', 0),
       ('商户信息', 'MER', 1, '', '', '2024-04-18 09:23:25', '', '2024-11-16 09:45:25', 0),
       ('设备信息', 'DEV', 1, '', '', '2024-04-18 09:23:25', '', '2024-11-16 09:45:25', 0);
INSERT INTO coolGuard.de_indicator (code, name, status, `type`, calc_field, return_type, return_flag, win_size,
                                    win_type, win_count, time_slice, master_field, slave_fields, compute_script, scenes,
                                    scene_type, cond, description, creator, create_time, updater, update_time, deleted)
VALUES ('b75c84a6b5ec45ed8026ca7c873e789c', '24小时交易金额之和', 1, 'sum', 'N_F_transAmount', 'F', 'earliest', 'H',
        'last', 24, 86400, 'N_S_payerAccount', '', '', 'phone', 'appName',
        '{"type":"normal","value":"N_F_transAmount","logicType":"lt","expectType":"input","expectValue":"1000"}',
        '金额求和', '', '2024-04-15 10:48:31', NULL, '2024-11-26 18:52:26', 0),
       ('72df2843a1094b2c8b8f6c4aebeb63dd', '24小时交易金额最大', 1, 'max', 'N_F_transAmount', 'F', 'earliest', 'H',
        'last', 24, 86400, 'N_S_payerAccount', '', '', 'phone:login', 'policySet',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"15"}', '', '',
        '2024-07-20 11:10:31', NULL, '2024-11-22 15:34:06', 0),
       ('d43b398568a24d9dbc82aae162f41ed6', '选必于', 1, 'sum', 'N_F_transAmount', 'F', 'earliest', 'D', 'last', 3,
        7776000, 'N_S_payerAccount', '', '', 'phone', 'appName',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"1000"}',
        '管军重中车更儿专比十接一时条。', NULL, '2024-08-03 20:01:18', NULL, '2024-11-22 15:34:06', 0),
       ('cd420db94e1948c59ea95c535b682975', '24小时交易金额大于10万求和', 1, 'sum', 'N_F_transAmount', 'F', 'earliest',
        'D', 'last', 3, 7776000, 'N_S_payerAccount', '', '', 'phone', 'appName',
        '{"type":"normal","value":"N_F_transAmount","logicType":"lt","expectType":"input","expectValue":"100000"}',
        '金额大于10万求和', NULL, '2024-08-21 17:30:23', NULL, '2024-11-22 15:34:06', 0),
       ('fc0547d4e57e431082314c1c1b28b121', '规支才公照还', 1, 'ass', 'N_S_payeeAccount', 'F', 'earliest', 'H', 'last',
        24, 3600, 'N_S_payerAccount', '', '', 'phone', 'appName',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}',
        '计行民平来铁及长么气社组较在先比例。', NULL, '2024-08-22 10:33:18', NULL, '2024-11-22 15:34:06', 0),
       ('c689576cf38d45888729c537697897af', '且者矿', 1, 'max', 'N_F_transAmount', 'F', 'earliest', 'H', 'last', 24,
        3600, 'N_S_payerAccount', '', '', 'phone', 'appName',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}',
        '世非克九界重被程局用开必米深专知体格。', NULL, '2024-08-22 10:39:29', NULL, '2024-11-22 15:34:21', 0),
       ('b02b2000723e422fbcafbf2cd671cd0b', '北在文地', 1, 'min', 'N_F_transAmount', 'F', 'earliest', 'H', 'last', 24,
        3600, 'N_S_payerAccount', '', '', 'phone', 'appName',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}',
        '般我五清其断界圆阶现性史派业反山他入。', NULL, '2024-08-22 10:39:33', NULL, '2024-11-22 15:34:21', 0),
       ('9d07af28e7624571b89c9eacd65bac2d', '看活历地许', 1, 'avg', 'N_F_transAmount', 'F', 'earliest', 'H', 'last', 24,
        3600, 'N_S_payerAccount', '', '', 'phone', 'appName',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}',
        '意素国府使造门安织应方行电。', NULL, '2024-08-22 10:39:39', NULL, '2024-11-22 15:34:21', 0),
       ('a7f924197fb0497ea8718c386d1c7db7', '情性特问写养八', 1, 'sum', 'N_F_transAmount', 'F', 'earliest', 'H', 'last',
        24, 3600, 'N_S_payerAccount', '', '', 'phone', 'appName',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}',
        '能率行主务直流明圆眼历火传周当日值种。', NULL, '2024-08-22 10:39:59', NULL, '2024-11-22 15:34:21', 0),
       ('c161c3943d4449408f75843fff68af4e', '其断子把酸', 1, 'count', '', 'F', 'earliest', 'H', 'last', 24, 3600,
        'N_S_payerAccount', '', '', 'phone', 'appName',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}',
        '拉县级它入题张长程权圆老所。', NULL, '2024-08-22 10:40:33', NULL, '2024-11-22 15:34:21', 0);
INSERT INTO coolGuard.de_indicator (code, name, status, `type`, calc_field, return_type, return_flag, win_size,
                                    win_type, win_count, time_slice, master_field, slave_fields, compute_script, scenes,
                                    scene_type, cond, description, creator, create_time, updater, update_time, deleted)
VALUES ('4025845e0cdc498284f0da08c3649242', '大行米', 1, 'his', 'N_F_transAmount', 'F', 'earliest', 'H', 'last', 24,
        3600, 'N_S_payerAccount', '', '', 'phone', 'appName',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"1"}',
        '切断又划根文相其明场龙义须文之需。', NULL, '2024-11-14 18:41:22', NULL, '2024-11-22 15:34:21', 0);
INSERT INTO coolGuard.de_indicator_version (code, name, status, `type`, calc_field, return_type, return_flag, win_size,
                                            win_type, win_count, time_slice, master_field, slave_fields, compute_script,
                                            scenes, scene_type, description, version, version_desc, cond, creator,
                                            create_time, updater, update_time, deleted)
VALUES ('b75c84a6b5ec45ed8026ca7c873e789c', '24小时交易金额之和', 0, 'sum', 'N_F_transAmount', 'F', 'earliest', 'H',
        'last', 24, 86400, 'N_S_payerAccount', '', '', 'phone', 'appName', '金额求和', 1, 'ex tempor officia elit',
        '{"type":"normal","value":"N_F_transAmount","logicType":"lt","expectType":"input","expectValue":"1000"}', NULL,
        '2024-11-22 15:30:52', NULL, '2024-11-26 18:52:26', 0),
       ('72df2843a1094b2c8b8f6c4aebeb63dd', '24小时交易金额最大', 1, 'max', 'N_F_transAmount', 'F', 'earliest', 'H',
        'last', 24, 86400, 'N_S_payerAccount', '', '', 'phone:login', 'policySet', '', 1, 'fugiat in Lorem',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"15"}', NULL,
        '2024-11-22 15:34:06', NULL, '2024-11-22 15:34:06', 0),
       ('d43b398568a24d9dbc82aae162f41ed6', '选必于', 1, 'sum', 'N_F_transAmount', 'F', 'earliest', 'D', 'last', 3,
        7776000, 'N_S_payerAccount', '', '', 'phone', 'appName', '管军重中车更儿专比十接一时条。', 1, 'fugiat in Lorem',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"1000"}', NULL,
        '2024-11-22 15:34:06', NULL, '2024-11-22 15:34:06', 0),
       ('cd420db94e1948c59ea95c535b682975', '24小时交易金额大于10万求和', 1, 'sum', 'N_F_transAmount', 'F', 'earliest',
        'D', 'last', 3, 7776000, 'N_S_payerAccount', '', '', 'phone', 'appName', '金额大于10万求和', 1,
        'fugiat in Lorem',
        '{"type":"normal","value":"N_F_transAmount","logicType":"lt","expectType":"input","expectValue":"100000"}',
        NULL, '2024-11-22 15:34:06', NULL, '2024-11-22 15:34:06', 0),
       ('fc0547d4e57e431082314c1c1b28b121', '规支才公照还', 1, 'ass', 'N_S_payeeAccount', 'F', 'earliest', 'H', 'last',
        24, 3600, 'N_S_payerAccount', '', '', 'phone', 'appName', '计行民平来铁及长么气社组较在先比例。', 1,
        'fugiat in Lorem',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}', NULL,
        '2024-11-22 15:34:06', NULL, '2024-11-22 15:34:06', 0),
       ('c689576cf38d45888729c537697897af', '且者矿', 1, 'max', 'N_F_transAmount', 'F', 'earliest', 'H', 'last', 24,
        3600, 'N_S_payerAccount', '', '', 'phone', 'appName', '世非克九界重被程局用开必米深专知体格。', 1,
        'fugiat in Lorem',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}', NULL,
        '2024-11-22 15:34:21', NULL, '2024-11-22 15:34:21', 0),
       ('b02b2000723e422fbcafbf2cd671cd0b', '北在文地', 1, 'min', 'N_F_transAmount', 'F', 'earliest', 'H', 'last', 24,
        3600, 'N_S_payerAccount', '', '', 'phone', 'appName', '般我五清其断界圆阶现性史派业反山他入。', 1,
        'fugiat in Lorem',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}', NULL,
        '2024-11-22 15:34:21', NULL, '2024-11-22 15:34:21', 0),
       ('9d07af28e7624571b89c9eacd65bac2d', '看活历地许', 1, 'avg', 'N_F_transAmount', 'F', 'earliest', 'H', 'last', 24,
        3600, 'N_S_payerAccount', '', '', 'phone', 'appName', '意素国府使造门安织应方行电。', 1, 'fugiat in Lorem',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}', NULL,
        '2024-11-22 15:34:21', NULL, '2024-11-22 15:34:21', 0),
       ('a7f924197fb0497ea8718c386d1c7db7', '情性特问写养八', 1, 'sum', 'N_F_transAmount', 'F', 'earliest', 'H', 'last',
        24, 3600, 'N_S_payerAccount', '', '', 'phone', 'appName', '能率行主务直流明圆眼历火传周当日值种。', 1,
        'fugiat in Lorem',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}', NULL,
        '2024-11-22 15:34:21', NULL, '2024-11-22 15:34:21', 0),
       ('c161c3943d4449408f75843fff68af4e', '其断子把酸', 1, 'count', '', 'F', 'earliest', 'H', 'last', 24, 3600,
        'N_S_payerAccount', '', '', 'phone', 'appName', '拉县级它入题张长程权圆老所。', 1, 'fugiat in Lorem',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}', NULL,
        '2024-11-22 15:34:21', NULL, '2024-11-22 15:34:21', 0);
INSERT INTO coolGuard.de_indicator_version (code, name, status, `type`, calc_field, return_type, return_flag, win_size,
                                            win_type, win_count, time_slice, master_field, slave_fields, compute_script,
                                            scenes, scene_type, description, version, version_desc, cond, creator,
                                            create_time, updater, update_time, deleted)
VALUES ('4025845e0cdc498284f0da08c3649242', '大行米', 1, 'his', 'N_F_transAmount', 'F', 'earliest', 'H', 'last', 24,
        3600, 'N_S_payerAccount', '', '', 'phone', 'appName', '切断又划根文相其明场龙义须文之需。', 1, 'fugiat in Lorem',
        '{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"1"}', NULL,
        '2024-11-22 15:34:21', NULL, '2024-11-22 15:34:21', 0),
       ('b75c84a6b5ec45ed8026ca7c873e789c', '24小时交易金额之和', 1, 'sum', 'N_F_transAmount', 'F', 'earliest', 'H',
        'last', 24, 86400, 'N_S_payerAccount', '', '', 'phone', 'appName', '金额求和', 2, 'ex tempor officia elit',
        '{"type":"normal","value":"N_F_transAmount","logicType":"lt","expectType":"input","expectValue":"1000"}', NULL,
        '2024-11-26 18:52:26', NULL, '2024-11-26 18:52:26', 0);
INSERT INTO coolGuard.de_policy (policy_set_code, code, name, mode, status, description, creator, create_time, updater,
                                 update_time, deleted)
VALUES ('phone_login', 'phone_login_worst', '手机登录最坏', 'worst', 1, '', '', '2024-04-07 19:19:28', '',
        '2024-11-16 11:01:04', 0),
       ('phone_login', 'phone_login_order', '手机登录顺序', 'order', 1, '', '', '2024-04-07 19:55:02', '',
        '2024-11-16 11:01:04', 0);
INSERT INTO coolGuard.de_policy_set (app_name, code, name, status, `chain`, description, creator, create_time, updater,
                                     update_time, deleted)
VALUES ('phone', 'phone_login', '手机登录策略', 1,
        'WHEN(e_cn,p_cn.tag("phone_login_worst"),p_cn.tag("phone_login_order"));', '', '', '2024-04-07 19:18:09', '',
        '2024-11-30 21:11:57', 0);
INSERT INTO coolGuard.de_policy_set_version (code, status, `chain`, version, creator, create_time, updater, update_time,
                                             deleted)
VALUES ('phone_login', 1, 'WHEN(e_cn,p_cn.tag("phone_login_worst"),p_cn.tag("phone_login_order"));', 0, NULL,
        '2024-12-01 13:32:57', NULL, '2024-12-01 13:32:57', 0);
INSERT INTO coolGuard.de_rule (policy_code, code, name, disposal_code, score, status, sort, cond, description, creator,
                               create_time, updater, update_time, deleted)
VALUES ('phone_login_order', 'f1df5b70574a46a09b95f3662cffaed9', '付款方账号不为空或者金额大于等于100', 'pass', 0, 'on',
        0,
        '{"logicOp":"OR","children":[{"logicOp":null,"children":null,"type":"normal","value":"N_S_payerAccount","logicType":"not_null","expectType":"","expectValue":""},{"logicOp":null,"children":null,"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}],"type":null,"value":null,"logicType":null,"expectType":null,"expectValue":null}',
        'hethethethe', '', '2024-04-07 22:13:58', NULL, '2024-11-16 13:56:19', 0),
       ('phone_login_order', '0b30510bf425492381a78aca098be029', '测试规则02', 'pass', 0, 'on', 2,
        '{"logicOp":"AND","children":[{"logicOp":null,"children":null,"type":"normal","value":"N_S_appName","logicType":"eq","expectType":"input","expectValue":"phone"},{"logicOp":null,"children":null,"type":"normal","value":"N_F_transAmount","logicType":"lt","expectType":"input","expectValue":"100"}],"type":null,"value":null,"logicType":null,"expectType":null,"expectValue":null}',
        '', '', '2024-04-07 22:14:32', '', '2024-11-16 13:56:19', 0),
       ('phone_login_worst', '6ae5cb587c784326b45c944e80319d50', '测试规则03', 'pass', 0, 'on', 99,
        '{"logicOp":null,"children":null,"type":"normal","value":"N_F_transAmount","logicType":"gte","expectType":"input","expectValue":"20.0"}',
        '', '', '2024-04-09 10:56:48', '', '2024-11-16 13:56:19', 0);
INSERT INTO coolGuard.de_rule_version (code, rule, status, version, creator, create_time, updater, update_time, deleted)
VALUES ('f1df5b70574a46a09b95f3662cffaed9',
        '{"createTime":"2024-04-07 22:13:58","updateTime":"2024-11-16 13:56:19","creator":"","deleted":false,"id":"1","policyCode":"phone_login_order","code":"f1df5b70574a46a09b95f3662cffaed9","name":"付款方账号不为空或者金额大于等于100","disposalCode":"pass","score":0,"status":"on","sort":0,"cond":{"logicOp":"OR","children":[{"type":"normal","value":"N_S_payerAccount","logicType":"not_null","expectType":"","expectValue":""},{"type":"normal","value":"N_F_transAmount","logicType":"gt","expectType":"input","expectValue":"100"}]},"description":"hethethethe"}',
        1, 0, NULL, '2024-12-01 14:06:07', NULL, '2024-12-01 14:06:07', 0),
       ('0b30510bf425492381a78aca098be029',
        '{"createTime":"2024-04-07 22:14:32","updateTime":"2024-11-16 13:56:19","creator":"","updater":"","deleted":false,"id":"2","policyCode":"phone_login_order","code":"0b30510bf425492381a78aca098be029","name":"测试规则02","disposalCode":"pass","score":0,"status":"on","sort":2,"cond":{"logicOp":"AND","children":[{"type":"normal","value":"N_S_appName","logicType":"eq","expectType":"input","expectValue":"phone"},{"type":"normal","value":"N_F_transAmount","logicType":"lt","expectType":"input","expectValue":"100"}]},"description":""}',
        1, 0, NULL, '2024-12-01 14:06:07', NULL, '2024-12-01 14:06:07', 0),
       ('6ae5cb587c784326b45c944e80319d50',
        '{"createTime":"2024-04-09 10:56:48","updateTime":"2024-11-16 13:56:19","creator":"","updater":"","deleted":false,"id":"3","policyCode":"phone_login_worst","code":"6ae5cb587c784326b45c944e80319d50","name":"测试规则03","disposalCode":"pass","score":0,"status":"on","sort":99,"cond":{"type":"normal","value":"N_F_transAmount","logicType":"gte","expectType":"input","expectValue":"20.0"},"description":""}',
        1, 0, NULL, '2024-12-01 14:06:07', NULL, '2024-12-01 14:06:07', 0);
