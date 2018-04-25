# ip-spider
计划使用webmagic+ 实现抓取各个代理网站的ip，然后用redis缓存。动态展示到页面上。

用这种方式模拟热点数据更新。
## finished
* 抓取IP，持久化到mysql 数据库
* 使用[讯代理][1]实现混淆代理爬虫
* 使用redis缓存爬取的内容
* 读取redis中的数据，并动态展示在页面上
## TODO
* 完善列表查询以及展示
* 自动化部署

  [1]: http://www.xdaili.cn
