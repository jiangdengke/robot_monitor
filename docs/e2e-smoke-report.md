# 浏览器逐页端到端烟测报告

- 生成时间：2026-07-21T04:27:10.638Z
- 前端地址：http://127.0.0.1:4174
- 后端地址：http://127.0.0.1:8080
- 总页面：42
- 通过：42
- 失败：0

| 状态 | 类型 | 检查项 | 路径 | 耗时 | 说明 |
| --- | --- | --- | --- | ---: | --- |
| [x] | 保留 API | 当前用户认证 | `/auth/me` | 27ms | 通过 |
| [x] | 保留 API | 用户列表 | `/users` | 26ms | 通过 |
| [x] | 保留 API | 个人资料 | `/me` | 7ms | 通过 |
| [x] | 保留 API | 场地列表 | `/config/sites` | 13ms | 通过 |
| [x] | 保留 API | 区域列表 | `/config/areas` | 22ms | 通过 |
| [x] | 保留 API | 点位列表 | `/config/points` | 12ms | 通过 |
| [x] | 保留 API | 设备列表 | `/config/devices` | 11ms | 通过 |
| [x] | 保留 API | 机器人列表 | `/config/robots` | 10ms | 通过 |
| [x] | 保留 API | 任务列表 | `/config/tasks` | 10ms | 通过 |
| [x] | 保留 API | 任务执行日志 | `/config/task-logs` | 18ms | 通过 |
| [x] | 保留 API | 登录日志 | `/monitor/login-logs` | 15ms | 通过 |
| [x] | 保留 API | 操作日志 | `/monitor/operation-logs` | 13ms | 通过 |
| [x] | 保留 API | Swagger 文档 | `/v3/api-docs` | 342ms | 通过 |
| [x] | 已删除 API | 旧贵宾室接口 | `/config/lounges` | 6ms | 通过 |
| [x] | 已删除 API | 旧空间点位接口 | `/config/regions` | 4ms | 通过 |
| [x] | 已删除 API | 旧设备区域绑定接口 | `/config/device-region-bindings` | 4ms | 通过 |
| [x] | 已删除 API | 旧媒体接口 | `/config/images` | 3ms | 通过 |
| [x] | 已删除 API | 旧统计接口 | `/statistics/in-lounge` | 4ms | 通过 |
| [x] | 已删除 API | 旧数字孪生接口 | `/DigitalTwin/all` | 5ms | 通过 |
| [x] | 已删除 API | 旧投诉接口 | `/config/complaints` | 3ms | 通过 |
| [x] | 已删除 API | 旧知识库接口 | `/knowledge` | 3ms | 通过 |
| [x] | 保留路由 | 用户管理 | `/system/user` | 899ms | 通过 |
| [x] | 保留路由 | 场地管理 | `/config/site` | 951ms | 通过 |
| [x] | 保留路由 | 区域管理 | `/config/area` | 814ms | 通过 |
| [x] | 保留路由 | 点位管理 | `/config/point` | 813ms | 通过 |
| [x] | 保留路由 | 设备管理 | `/config/device` | 820ms | 通过 |
| [x] | 保留路由 | 机器人管理 | `/config/robot` | 810ms | 通过 |
| [x] | 保留路由 | 任务管理 | `/config/task` | 820ms | 通过 |
| [x] | 保留路由 | 登录日志 | `/monitor/logininfor` | 807ms | 通过 |
| [x] | 保留路由 | 操作日志 | `/monitor/operlog` | 851ms | 通过 |
| [x] | 保留路由 | 首页 | `/` | 951ms | 通过 |
| [x] | 保留路由 | 个人中心 | `/profile` | 817ms | 通过 |
| [x] | 保留路由 | 个人资料 | `/profile/userInfo` | 828ms | 通过 |
| [x] | 保留路由 | 修改密码 | `/profile/resetPwd` | 813ms | 通过 |
| [x] | 保留路由 | 头像上传 | `/profile/userAvatar` | 850ms | 通过 |
| [x] | 已删除路由 | 前端 API 文档页 | `/tool/swagger` | 754ms | 通过 |
| [x] | 已删除路由 | 旧贵宾室管理 | `/configManagment/vipRoom` | 760ms | 通过 |
| [x] | 已删除路由 | 旧空间点位管理 | `/configManagment/vipRoomRegion` | 750ms | 通过 |
| [x] | 已删除路由 | 旧统计页面 | `/statAnalysis/inLoungeList` | 758ms | 通过 |
| [x] | 已删除路由 | 旧数字孪生页面 | `/digitalTwin` | 752ms | 通过 |
| [x] | 已删除路由 | 旧媒体页面 | `/configManagment/photo` | 876ms | 通过 |
| [x] | 已删除路由 | 旧投诉页面 | `/configManagment/complaintRecord` | 736ms | 通过 |

## 失败明细

全部通过。
