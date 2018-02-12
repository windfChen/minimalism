# minimalism
代码生成，极简框架

1、摘取现有功能
备份现在代码
去掉多余的业务和插架(包括dev)，保持最简
dev复制，保留已经做好的entity生成
2、dao生成
表，字段，查询字段，排序字段等参数生成sql
生成dao方法，和SQL文件对应
页面上输入，默认全部
mybatis了解
3、service
方法签名生成
字符流程图定义
根据定义语句生成相应的代码，以及私有方法签名(代替设想中的modal)
组织impl和接口的生成
4、controller
URL对应方法签名
参数验证和接受组织
调用service
异常处理
baseaction完善
