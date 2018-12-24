# greenhouse
基于NB-IoT的温室环境远程监测系统应用平台

该项目是上位机系统，主要使用netty构建UDP服务端用于接收底层通信模块发送的UDP数据包（BC95通信模块，基于NB-IoT通信），构建websocket实现数据在web端的实时展示（echarts），数据存储在mysql

使用shiro对用户权限做了相应额控制。

与之对应的下位机系统：STM32+Quectel BC95-B5+STH20：https://github.com/hiajianchan/humiture
