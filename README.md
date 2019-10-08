# Patent
河北工程-专利项目

在线地址: 39.108.119.99:9090

项目背景:
   对专利进行机器学习分类时,需要提前对专利压缩包信息进行提取分词,处理等提前处理.该系统的目的就是简化机器学习的专利信息提取过程.
   
项目技术:
   
   Spring,Spring MVC,Mybatis ,Mysql ,Lucene,Linux,Ansj,Jsoup,easyexcel.
 Ansj 介绍:
  这是一个基于n-Gram+CRF+HMM的中文分词的java实现.分词速度达到每秒钟大约200万字左右（mac air下测试），准确率能达到96%以上
  项目开源地址: https://github.com/NLPchina/ansj_seg
 Jsoup 介绍:
  html文件解析Jar
  Jsoup官网: https://www.open-open.com/jsoup/
 Lucene介绍:
  Java的索引和搜索技术
  Lucene官网: http://lucene.apache.org/
 easyExcel介绍:
  Java解析、生成Excel比较有名的框架有Apache poi、jxl。但他们都存在一个严重的问题就是非常的耗内存，poi有一套SAX模式的API可以一定程度的解决一些内存溢出的问题，但POI还是有一些缺陷，比如07版Excel解压缩以及解压后存储都是在内存中完成的，内存消耗依然很大。easyexcel重写了poi对07版Excel的解析，能够原本一个3M的excel用POI sax依然需要100M左右内存降低到几M，并且再大的excel不会出现内存溢出，03版依赖POI的sax模式。在上层做了模型转换的封装，让使用者更加简单方便
  开源项目地址: https://github.com/alibaba/easyexcel
   
   
