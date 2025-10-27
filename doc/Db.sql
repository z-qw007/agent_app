# 智能体库表
drop table if exists agent;
create table agent
(
    agent_id    int auto_increment comment '智能体id',
    agent_name  varchar(64)                     comment '智能体名',
    user_id     int                             comment '用户id',
    description varchar(255)                    comment '智能体描述',
    prompt      LONGTEXT                        comment '提示词',
    config_json     varchar(512)        comment '模型配置JSON (e.g., temperature, top_p)',
    create_time datetime                        comment '创建时间',
    update_time datetime                        comment '更新时间',
    status      tinyint     default 1           not null comment '状态(0-删除，1-正常)',
    constraint agent_pk
        primary key (agent_id)
)
    comment '智能体';

ALTER TABLE agent ADD COLUMN usage_count INT DEFAULT 0 COMMENT '模型使用/点击次数';

INSERT INTO agent
(user_id, agent_name, description, prompt, config_json, create_time, update_time)
VALUES
-- 示例 1: 营销文案专家 Agent (需要创意，高 temperature)
(
    1,                                                                      -- user_id
    '营销文案专家',                                                             -- agent_name
    '专注于生成高质量、吸引人的社交媒体营销文案和广告语。',                           -- description
    '你是一位顶尖的数字营销专家，精通各种社交媒体平台的文案风格。你的任务是根据用户的产品描述和目标受众，创作出具有高度转化率的营销内容。请保持专业、简洁和创新。', -- prompt
    '{"temperature": 0.8, "top_p": 0.9}',                                 -- config_json：高创意度配置
    NOW(),                                                                    -- create_time
    NOW()                                                                     -- update_time
),
-- 示例 2: 技术文档问答 Agent (需要严谨，低 temperature)
(
    1,                                                                      -- user_id
    '技术文档问答助手',                                                           -- agent_name
    '用于检索和回答特定技术手册和 API 文档中的问题，需要 RAG 工具支持。',              -- description
    '你是一位严谨且精确的技术支持工程师，你的回答必须基于知识库的检索结果，并且只回答与技术文档相关的问题。如果知识库中没有相关信息，请明确告知用户。', -- prompt
    '{"temperature": 0.3, "top_p": 0.5}',                                 -- config_json：高准确度配置
    NOW(),                                                                    -- create_time
    NOW()                                                                     -- update_time
);

# 用户表
drop table if exists user;
create table user
(
    user_id      int auto_increment comment '用户id',
    user_name    varchar(255)      not null comment '用户名',
    email        VARCHAR(64)       null comment '邮箱',
    password     varchar(255)      not null,
    created_time datetime          not null comment '账号创建时间',
    update_time  datetime          null comment '修改时间',
    status       tinyint default 1 not null comment '状态',
    constraint user_pk
        primary key (user_id)
)
    comment '用户';

INSERT INTO user
(user_id, user_name, email, password, created_time, update_time)
VALUES
(1, '张三', 'zhangsan@example.com', '123456', NOW(), NOW()),
(2, '李四', 'lisi@example.com', '123456', NOW(), NOW());


-- chat_session 表：存储用户与特定智能体的会话记录
drop table if exists chat_session;
create table chat_session
(
    session_id      int auto_increment comment '会话id',
    agent_id        int         not null comment '关联的智能体id',
    user_id         int         not null comment '会话归属用户id',
    title           varchar(128)        comment '会话标题',

    -- 存储历史消息的压缩摘要
    context_summary LONGTEXT            comment '当前会话历史的AI生成摘要',

    create_time     datetime            comment '会话创建时间',
    update_time     datetime            comment '会话最后更新时间',
    PRIMARY KEY (session_id)
    ) comment '智能体对话会话';


-- message_log 表：消息记录
drop table if exists message_log;
create table message_log
(
    message_id  int auto_increment comment '消息id',
    session_id  int                 not null comment '关联的会话id',
    role        varchar(16)         not null comment '消息角色 (USER/AI/SYSTEM)',
    content     LONGTEXT            comment '消息内容',
    create_time datetime            comment '消息发送时间',
    PRIMARY KEY (message_id),
    index idx_session_id (session_id)
) comment '对话消息记录';