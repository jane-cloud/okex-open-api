如何使用？

第一步：下载代码
   
    1.1 安装依赖composer包
    
        composer update
    
第二步：配置自己的API key

    这里默认有key, 供社区用户测试调用，不过要想用自己的账户，需要重新配置

    2.1 先去OKEx官网申请API Key
    2.2 将各项参数在Config文件中进行替换

第三步：调用Rest，在项目根目录下，运行如下命令
    
    3.1 调用Rest公共接口
        
        php rapiDemo.php
        
    3.2 调用websocket
    
        php wsDemo.php start
     
附言：        
1、如果对API不太了解，可以先去参考 OKEx 的文档
    
    https://www.okex.com/docs/zh/#README
    
   
2、如果有改进意见，欢迎提issue，欢迎提交代码

    
    
    
    
