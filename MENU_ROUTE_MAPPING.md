# 菜单路由源码映射

本文档用于对齐 `backend/sql/init.sql` 的 `sys_menu` 初始化菜单、后端 `/getRouters` 返回路径、当前前端路由别名和 `.vue` 源码文件。

说明：

- 当前后台侧栏使用 `sys_menu.parent.path + sys_menu.path` 生成访问路径，例如 `config + robot` 对应 `/config/robot`。
- 旧部署包里的 `component` 字段仍保留为页面来源线索，例如 `configManagment/robot/index`。
- 当前 `frontend/src/router/index.js` 同时保留 legacy component 风格路由和菜单路径别名，避免侧栏点击 404。

## 系统管理

| 菜单 | 后端菜单路径 | legacy component | 当前源码 |
| --- | --- | --- | --- |
| 用户管理 | `/system/user` | `system/user/index` | `frontend/src/views/system/user/index.vue` |
| 角色管理 | `/system/role` | `system/role/index` | `frontend/src/views/system/role/index.vue` |
| 菜单管理 | `/system/menu` | `system/menu/index` | `frontend/src/views/system/menu/index.vue` |
| 部门管理 | `/system/dept` | `system/dept/index` | `frontend/src/views/system/dept/index.vue` |
| 岗位管理 | `/system/post` | `system/post/index` | `frontend/src/views/system/post/index.vue` |
| 字典类型 | `/system/dict` | `system/dict/index` | `frontend/src/views/system/dict/index.vue` |
| 字典数据 | `/system/dict-data/index/:dictId` | `system/dict/data` | `frontend/src/views/system/dict/data.vue` |
| 参数配置 | `/system/config` | `system/config/index` | `frontend/src/views/system/config/index.vue` |
| 通知公告 | `/system/notice` | `system/notice/index` | `frontend/src/views/system/notice/index.vue` |
| 个人中心 | `/profile` | `system/user/profile/index` | `frontend/src/views/system/user/profile/index.vue` |
| 头像上传 | `/profile/userAvatar` | `system/user/profile/userAvatar` | `frontend/src/views/system/user/profile/userAvatar.vue` |
| 角色授权 | `/system/role-auth/user/:roleId` | `system/role/authUser` | `frontend/src/views/system/role/authUser.vue` |
| 分配角色 | `/system/user-auth/role/:userId` | `system/user/authRole` | `frontend/src/views/system/user/authRole.vue` |

## 系统监控

| 菜单 | 后端菜单路径 | legacy component | 当前源码 |
| --- | --- | --- | --- |
| 在线用户 | `/monitor/online` | `monitor/online/index` | `frontend/src/views/monitor/online/index.vue` |
| 操作日志 | `/monitor/operlog` | `monitor/operlog/index` | `frontend/src/views/monitor/operlog/index.vue` |
| 操作日志兼容 | `/monitor/oper-log` | `monitor/operlog/index` | `frontend/src/views/monitor/operlog/index.vue` |
| 登录日志 | `/monitor/logininfor` | `monitor/logininfor/index` | `frontend/src/views/monitor/logininfor/index.vue` |
| 登录日志兼容 | `/monitor/login-log` | `monitor/logininfor/index` | `frontend/src/views/monitor/logininfor/index.vue` |
| 缓存监控 | `/monitor/cache` | `monitor/cache/index` | `frontend/src/views/monitor/cache/index.vue` |
| 定时任务 | `/monitor/job` | `monitor/job/index` | `frontend/src/views/monitor/job/index.vue` |
| 调度日志 | `/monitor/job-log` | `monitor/job/log` | `frontend/src/views/monitor/job/log.vue` |
| 调度日志详情 | `/monitor/job/log/:jobId?` | `monitor/job/log` | `frontend/src/views/monitor/job/log.vue` |
| 服务监控 | `/monitor/server` | `monitor/server/index` | `frontend/src/views/monitor/server/index.vue` |
| 数据库监控 | `/monitor/druid` | `monitor/druid/index` | `frontend/src/views/monitor/druid/index.vue` |

## 机器人配置

| 菜单 | 后端菜单路径 | legacy component | 当前源码 |
| --- | --- | --- | --- |
| 机器人配置 | `/config/robot` | `configManagment/robot/index` | `frontend/src/views/configManagment/robot/index.vue` |
| 区域配置 | `/config/region` | `configManagment/vipRoomRegion/index` | `frontend/src/views/configManagment/vipRoomRegion/index.vue` |
| 图片素材 | `/config/photo` | `configManagment/photo/index` | `frontend/src/views/configManagment/photo/index.vue` |
| 桌台配置 | `/config/table` | `foodManagment/foodTable/index` | `frontend/src/views/foodManagment/foodTable/index.vue` |
| 语音配置 | `/config/audio` | `configManagment/robotAudio/index` | `frontend/src/views/configManagment/robotAudio/index.vue` |
| 任务配置 | `/config/task` | `taskManagment/taskList/index` | `frontend/src/views/taskManagment/taskList/index.vue` |
| 贵宾室配置 | `/config/vipRoom` | `configManagment/vipRoom/index` | `frontend/src/views/configManagment/vipRoom/index.vue` |
| 区域管理 | `/config/areaManagment` | `configManagment/areaManagment/index` | `frontend/src/views/configManagment/areaManagment/index.vue` |
| 监控设备 | `/config/monitorDevice` | `configManagment/monitorDevice/index` | `frontend/src/views/configManagment/monitorDevice/index.vue` |
| 投诉记录 | `/config/complaintRecord` | `configManagment/complaintRecord/index` | `frontend/src/views/configManagment/complaintRecord/index.vue` |
| 视频资源 | `/config/vedio` | `configManagment/vedio/index` | `frontend/src/views/configManagment/vedio/index.vue` |

## 航班旅客

| 菜单 | 后端菜单路径 | legacy component | 当前源码 |
| --- | --- | --- | --- |
| 旅客信息 | `/flight/passenger` | `statAnalysis/inLoungeList/index` | `frontend/src/views/statAnalysis/inLoungeList/index.vue` |
| 航班信息 | `/flight/flightInfo` | `statAnalysis/moveStat/index` | `frontend/src/views/statAnalysis/moveStat/index.vue` |
| 数字孪生 | `/flight/digitalTwin` | `digitalTwin/index` | `frontend/src/views/digitalTwin/index.vue` |
| 出厅统计 | `/flight/outGoing` | `viewManagment/outGoing/index` | `frontend/src/views/viewManagment/outGoing/index.vue` |
| 通行统计 | `/flight/goingStat` | `statAnalysis/goingStat/index` | `frontend/src/views/statAnalysis/goingStat/index.vue` |
| 问题统计 | `/flight/questionStat` | `statAnalysis/questionStat/index` | `frontend/src/views/statAnalysis/questionStat/index.vue` |
| 预警记录 | `/flight/passengerWarning` | `statAnalysis/passengerWarningLog/index` | `frontend/src/views/statAnalysis/passengerWarningLog/index.vue` |

## 休息室点餐

| 菜单 | 后端菜单路径 | legacy component | 当前源码 |
| --- | --- | --- | --- |
| 菜品管理 | `/food/foodConfig` | `foodManagment/food/index` | `frontend/src/views/foodManagment/food/index.vue` |
| 今日菜单 | `/food/dailyMenu` | `foodManagment/menuPlan/index` | `frontend/src/views/foodManagment/menuPlan/index.vue` |
| 点餐订单 | `/food/foodOrder` | `foodManagment/foodMenu/index` | `frontend/src/views/foodManagment/foodMenu/index.vue` |
| 桌台视图 | `/food/foodTable` | `foodManagment/foodTable/index` | `frontend/src/views/foodManagment/foodTable/index.vue` |
| 菜单计划 | `/food/foodPlan` | `foodManagment/foodPlan/index` | `frontend/src/views/foodManagment/foodPlan/index.vue` |

## AI 知识库

| 菜单 | 后端菜单路径 | legacy component | 当前源码 |
| --- | --- | --- | --- |
| 知识库管理 | `/ai/knowledge` | `knowledgeManagment/ai/knowledge/index` | `frontend/src/views/knowledgeManagment/ai/knowledge/index.vue` |
| 引导日志 | `/ai/log` | `knowledgeManagment/ai/log/index` | `frontend/src/views/knowledgeManagment/ai/log/index.vue` |

## 系统工具

| 菜单 | 后端菜单路径 | legacy component | 当前源码 |
| --- | --- | --- | --- |
| 代码生成 | `/tool/gen` | `tool/gen/index` | `frontend/src/views/tool/gen/index.vue` |
| 表单构建 | `/tool/build` | `tool/build/index` | `frontend/src/views/tool/build/index.vue` |
| Swagger 文档 | `/tool/swagger` | `tool/swagger/index` | `frontend/src/views/tool/swagger/index.vue` |

## 其他业务入口

| 菜单 | 后端菜单路径 | legacy component | 当前源码 |
| --- | --- | --- | --- |
| 桌台模型 | `/numberModel` | `numberModel/index` | `frontend/src/views/numberModel/index.vue` |
| legacy 数字孪生入口 | `/digitalTwin` | `digitalTwin/index` | `frontend/src/views/digitalTwin/index.vue` |
