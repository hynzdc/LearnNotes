# **SpringCloud版本选择**

## **1.[版本选择官方建议](https://spring.io/projects/spring-cloud)**

- Angel 										版本基于springboot1.2.x版本构建与1.3版本不兼容
- Brixton										版本基于springboot1.3.x版本构建与1.2版本不兼容
	`2017年Brixton and Angel release官方宣布报废
- Camden      							版本基于springboot1.4.x版本构建并在1.5版本通过测试
	`2018年Camden release官方宣布报废
- Dalston、Edgware 				 版本基于springboot1.5.x版本构建目前不能再springboot2.0.x版本中使用
	`Dalston(达尔斯顿)将于2018年12月官方宣布报废。Edgware将遵循Spring Boot 1.5.x的生命周期结束。
- Finchley 									版本基于springboot2.0.x版本进行构建,不能兼容1.x版本
- Greenwich									版本基于springboot2.1.x版本进行构建,不能兼容1.x版本
- Hoxton										版本基于springboot2.2.x版本进行构建

![image-20211121201703949](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211121201703949.png)

- springboot 2.2.x.RELEASE+（我这里推荐用2.2.5.RELEASE）
- springcloud Hoxton SR1~6 
- java8+
- maven 3.3.6+
- idea 2018.3.5+

## **环境搭建**

### **SpringCloud采用全局管理**

```xml
<!--定义springcloud使用版本号-->
<properties>
  <java.version>1.8</java.version>
  <spring-cloud.version>Hoxton.SR6</spring-cloud.version>
</properties>
<!--全局管理springcloud版本,并不会引入具体依赖-->
<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>org.springframework.cloud</groupId>
      <artifactId>spring-cloud-dependencies</artifactId>
      <version>${spring-cloud.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

