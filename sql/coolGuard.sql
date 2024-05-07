create schema coolGuard collate utf8mb4_general_ci;

create table de_application
(
    id           bigint auto_increment comment '主键'
        primary key,
    display_name varchar(64)                 default ''                not null comment '显示名',
    name         varchar(64)                 default ''                not null comment '应用名',
    secret       varchar(64)                 default ''                not null comment '密钥',
    description  varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator      varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time  datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater      varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time  datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      bit                         default b'0'              not null comment '是否删除',
    constraint uk_name
        unique (name)
)
    comment '应用表';

create table de_chain
(
    id               bigint auto_increment comment '主键'
        primary key,
    application_name varchar(32)                 default ''                not null comment '应用名',
    chain_name       varchar(64)                 default ''                not null comment 'chain名',
    el_data          text                                                  not null comment 'el数据',
    enable           bit                         default b'0'              not null comment 'chain状态',
    description      varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator          varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time      datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater          varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time      datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted          bit                         default b'0'              not null comment '是否删除',
    constraint uk_code
        unique (chain_name)
)
    comment 'chain表';

create table de_condition
(
    id           bigint auto_increment comment '主键'
        primary key,
    field_name   varchar(32)                 default ''                not null comment '字段名',
    operate_type varchar(32)                 default 'null'            not null comment '操作类型',
    expect_value varchar(32)                 default ''                not null comment '期望值',
    description  varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator      varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time  datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater      varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time  datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      bit                         default b'0'              not null comment '是否删除'
)
    comment '规则条件表';

create table de_disposal
(
    id          bigint auto_increment comment '主键'
        primary key,
    code        varchar(64)                 default ''                not null comment '处置编码',
    name        varchar(64)                 default ''                not null comment '处置名',
    grade       int                         default 1                 not null comment '风险等级',
    color       varchar(64)                                           null comment '颜色',
    standard    bit                         default b'0'              not null comment '标准',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit                         default b'0'              not null comment '是否删除',
    constraint uk_code
        unique (code)
)
    comment '处置表';

create table de_field
(
    id            bigint auto_increment comment '主键'
        primary key,
    display_name  varchar(64) charset utf8mb4 default ''                not null comment '显示名',
    name          varchar(64) charset utf8mb4 default ''                not null comment '字段名',
    group_id      bigint                                                not null comment '字段分组',
    standard      bit                         default b'0'              not null comment '是否标准字段',
    type          varchar(5)                                            not null comment '字段类型',
    description   varchar(64) charset utf8mb4 default ''                null comment '描述',
    default_value varchar(64) charset utf8mb4                           null comment '默认值',
    dynamic       bit                         default b'0'              not null comment '是否动态字段(0否1是)',
    script        varchar(100)                                          null comment '动态字段脚本',
    creator       varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted       bit                         default b'0'              not null comment '是否删除',
    constraint uk_name
        unique (name)
)
    comment '字段表';

create table de_field_group
(
    id           bigint auto_increment comment '自增编号'
        primary key,
    display_name varchar(64) charset utf8mb4 default ''                not null comment '显示分组名',
    name         varchar(64) charset utf8mb4 default ''                not null comment '分组标识',
    standard     bit                         default b'0'              not null comment '标准',
    description  varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator      varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time  datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater      varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time  datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted      bit                         default b'0'              not null comment '是否删除',
    constraint uk_name
        unique (name)
)
    comment '字段分组表';

create table de_indicator
(
    id             bigint auto_increment comment '自增编号'
        primary key,
    name           varchar(64) charset utf8mb4 default ''                not null comment '指标名',
    status         bit                         default b'0'              not null comment '状态',
    chain_name     varchar(64)                 default ''                null comment 'chain名',
    type           varchar(10)                                           null comment '类型',
    calc_field     varchar(64) charset utf8mb4 default ''                null comment '计算字段',
    win_size       varchar(5) charset utf8mb4  default ''                not null comment '窗口大小',
    win_type       varchar(10) charset utf8mb4 default ''                not null comment '窗口类型',
    win_count      int                         default 0                 not null comment '窗口数量',
    time_slice     bigint                      default 0                 not null comment '时间片',
    master_field   varchar(64) charset utf8mb4 default ''                null comment '主字段',
    slave_fields   varchar(64) charset utf8mb4 default ''                null comment '从字段',
    compute_script varchar(64) charset utf8mb4 default ''                null comment '计算脚本',
    version        int                         default 0                 not null comment '版本号',
    scene          varchar(64)                                           not null comment '场景（,分割）',
    scene_type     varchar(32)                 default 'App'             not null comment '场景类型',
    description    varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator        varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time    datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater        varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time    datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted        bit                         default b'0'              not null comment '是否删除'
)
    comment '指标表';

create table de_rule
(
    id          bigint auto_increment comment '主键'
        primary key,
    strategy_id bigint                                                not null comment '策略id',
    chain_name  varchar(64)                 default ''                null comment 'chain名',
    code        varchar(64)                 default ''                not null comment '规则编码',
    name        varchar(64)                 default ''                not null comment '规则名',
    disposal_id bigint                      default 1                 not null comment '处置id',
    score       int                         default 0                 not null comment '得分',
    status      varchar(64)                                           not null comment '状态',
    sort        int                         default 99                not null comment '排序',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit                         default b'0'              not null comment '是否删除',
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
    script_data      text                                                  not null comment '脚本数据',
    script_type      varchar(16)                 default ''                not null comment '脚本类型',
    language         varchar(32)                 default ''                not null comment '脚本语言',
    enable           bit                         default b'0'              not null comment '脚本状态',
    description      varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator          varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time      datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater          varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time      datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted          bit                         default b'0'              not null comment '是否删除'
)
    comment '规则脚本表';

create table de_service_config
(
    id            bigint auto_increment comment '自增编号'
        primary key,
    display_name  varchar(64) charset utf8mb4 default ''                not null comment '显示服务名',
    name          varchar(64) charset utf8mb4 default ''                not null comment '服务标识',
    input_config  varchar(64) charset utf8mb4 default ''                null comment '输入配置',
    output_config varchar(64) charset utf8mb4 default ''                null comment '输出配置',
    description   varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator       varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time   datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater       varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time   datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted       bit                         default b'0'              not null comment '是否删除',
    constraint uk_name
        unique (name)
)
    comment '服务配置表';

create table de_service_config_field
(
    id                bigint auto_increment comment '主键'
        primary key,
    service_config_id bigint                                                not null comment '服务id',
    param_name        varchar(64)                                           not null,
    required          bit                         default b'0'              not null,
    field_name        varchar(64)                                           not null comment '字段id',
    creator           varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time       datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater           varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time       datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted           bit                         default b'0'              not null comment '是否删除'
)
    comment '服务配置字段表';

create table de_strategy
(
    id              bigint auto_increment comment '主键'
        primary key,
    chain_name      varchar(64)                                           not null,
    strategy_set_id bigint                      default 0                 not null comment '策略集id',
    code            varchar(64)                 default ''                not null comment '策略编码',
    name            varchar(64)                 default ''                not null comment '策略名',
    mode            varchar(32)                 default 'order'           not null comment '策略模式',
    status          int                         default 0                 not null comment '策略状态',
    description     varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator         varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time     datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater         varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time     datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted         bit                         default b'0'              not null comment '是否删除',
    constraint uk_code
        unique (code)
)
    comment '策略表';

create table de_strategy_set
(
    id          bigint auto_increment comment '主键'
        primary key,
    app_name    varchar(32)                 default 'ALL'             not null comment '应用名',
    code        varchar(64)                 default ''                not null comment '策略集编码',
    name        varchar(64)                 default ''                not null comment '策略集名',
    status      bit                         default b'0'              not null comment '策略集状态',
    description varchar(64) charset utf8mb4 default ''                null comment '描述',
    creator     varchar(64) charset utf8mb4 default ''                null comment '创建者',
    create_time datetime                    default CURRENT_TIMESTAMP not null comment '创建时间',
    updater     varchar(64) charset utf8mb4 default ''                null comment '更新者',
    update_time datetime                    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
    deleted     bit                         default b'0'              not null comment '是否删除',
    constraint uk_code
        unique (code)
)
    comment '策略集表';


# TODO 数据，名单集、名单数据、标签、数据源、数据集

# TODO 客户免疫、客户免疫历史

# TODO 规则控制，规则免疫、规则免疫历史、时间控制（每天什么时间段开启），权重、首次、最坏

# strategy disposal


INSERT INTO coolGuard.de_disposal (code, name, description)
VALUES ('pass', '通过', '');

INSERT INTO coolGuard.de_field (display_name, name, group_id, standard, type, description, default_value, dynamic)
VALUES ('应用名', 'N_S_appName', 1, true, 'S', '应用名', null, false),
       ('策略集code', 'N_S_strategySetCode', 1, true, 'S', '策略集code', null, false),
       ('策略code', 'N_S_strategyCode', 1, true, 'S', '策略code', null, false),
       ('交易时间', 'N_D_transTime', 1, true, 'D', '交易时间', null, false),
       ('交易金额', 'N_F_transAmount', 1, true, 'F', '交易金额', null, false),
       ('交易流水号', 'N_S_transSerialNo', 1, true, 'S', '交易流水号', null, false),
       ('收款方账户', 'N_S_payeeAccount', 1, true, 'S', '收款方账户', null, false),
       ('付款方账户', 'N_S_payerAccount', 1, true, 'S', '付款方账户', null, false),
       ('收款方名称', 'N_S_payeeName', 1, true, 'S', '收款方名称', null, false),
       ('付款方名称', 'N_S_payerName', 1, true, 'S', '付款方名称', null, false),
       ('收款方类型', 'N_S_payeeType', 1, true, 'S', '收款方类型', null, false),
       ('付款方类型', 'N_S_payerType', 1, true, 'S', '付款方类型', null, false),
       ('收款方风险评级', 'N_S_payeeRiskRating', 1, true, 'S', '收款方风险评级', null, false),
       ('付款方风险评级', 'N_S_payerRiskRating', 1, true, 'S', '付款方风险评级', null, false),
       ('收款方银行名', 'N_S_payeeBankName', 1, true, 'S', '收款方银行名', null, false),
       ('付款方银行名', 'N_S_payerBankName', 1, true, 'S', '付款方银行名', null, false),
       ('收款方地址', 'N_S_payeeAddress', 1, true, 'S', '收款方地址', null, false),
       ('付款方地址', 'N_S_payerAddress', 1, true, 'S', '付款方地址', null, false),
       ('收款方手机号', 'N_S_payeePhoneNumber', 1, true, 'S', '收款方手机号', null, false),
       ('付款方手机号', 'N_S_payerPhoneNumber', 1, true, 'S', '付款方手机号', null, false),
       ('收款方证件号码', 'N_S_payeeIDNumber', 1, true, 'S', '收款方证件号码', null, false),
       ('付款方证件号码', 'N_S_payerIDNumber', 1, true, 'S', '付款方证件号码', null, false),
       ('收款方证件所属国家/地区', 'N_S_payeeIDCountryRegion', 1, true, 'S', '收款方所属国家/地区', null, false),
       ('付款方证件所属国家/地区', 'N_S_payerIDCountryRegion', 1, true, 'S', '付款方所属国家/地区', null, false);


INSERT INTO coolGuard.de_field (display_name, name, group_id, standard, type, description, default_value, dynamic)
VALUES ('证件号所属省名称', 'N_S_idCardProvince', 1, true, 'S', '证件号所属省名称', null, false),
       ('证件号所属市名称', 'N_S_idCardCity', 1, true, 'S', '证件号所属市名称', null, false),
       ('证件号所属区/县名称', 'N_S_idCardDistrict', 1, true, 'S', '证件号所属区/县名称', null, false),
       ('手机号号所属省名称', 'N_S_phoneNumberProvince', 1, true, 'S', '手机号所属省名称', null, false),
       ('手机号号所属市名称', 'N_S_phoneNumberCity', 1, true, 'S', '手机号所属市名称', null, false),
       ('手机号号ISP', 'N_S_phoneNumberIsp', 1, true, 'S', '手机号所属运营商', null, false),
       ('ip', 'N_S_ip', 1, true, 'S', 'ip', null, false),
       ('IP所属国家名称', 'N_S_ipCountry', 1, true, 'S', 'IP归属国家名称', null, false),
       ('IP所属省名称', 'N_S_ipProvince', 1, true, 'S', 'IP归属省名称', null, false),
       ('IP所属市名称', 'N_S_ipCity', 1, true, 'S', 'IP归属市名称', null, false),
       ('IP所属isp', 'N_S_ipIsp', 1, true, 'S', 'IP归属运营商', null, false),
       ('经纬度', 'N_S_lonAndLat', 1, true, 'S', '经纬度字符串，格式必须是 "经度,纬度"', null, false),
       ('经纬度所在省名称', 'N_S_geoProvince', 1, true, 'S', '经纬度所在省名称', null, false),
       ('经纬度所在市名称', 'N_S_geoCity', 1, true, 'S', '经纬度所在市名称', null, false),
       ('经纬度所在区/县名称', 'N_S_geoDistrict', 1, true, 'S', '经纬度所在区/县名称', null, false);

