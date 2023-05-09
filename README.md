# distributed-file-system

protoc-3.1.0-win32文件夹使用方法：

NameNodeRpcModel.proto

NameNodeRpcServer.proto

两个文件放在bin目录下

win命令行：protoc.exe --java_out=./ *.proto

protoc-gen-grpc-java-0.13.2-windows-x86_64.exe放到bin目录下

win命令行：

protoc.exe --plugin=protoc-gen-grpc-java=protoc-gen-grpc-java-0.13.2-windows-x86_64.exe --grpc-java_out=./ *.proto

怎么解决namenode怎么知道哪些datanode是可以正常运行的?

>  心跳机制：dadanode启动时，首先携带自己的ip和hostname给namenode，如果注册成功，namenode将datanode的信息存入concurrenthashmap中，然后返回成功的信息给datanode。然后每一个datanode每次隔30s携带一次自己的ip和hostname信息给namenode，namenode会记录下收到每一个datanode上次心跳的最后时间到concurrenthashmap中，并且namenode会开启一个线程，每隔30s遍历concurrenthashmap中的datanodeinfo对象，如果有一个datanode已经90s没有发送心跳给namenode，namenode就将它加入到toRemoveDatanodes中，使用ArrayList就行，因为没有并发安全问题存在