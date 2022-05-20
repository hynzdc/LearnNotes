

# **SpringCloud微服务组件**

## **consul**

consul是一个可以提供服务发现，健康检查，多数据中心，Key/Value存储等功能的分布式服务框架，用于实现分布式系统的服务发现与配置。与其他分布式服务注册与发现的方案，使用起来也较为简单。Consul用Golang实现，因此具有天然可移植性(支持Linux、Windows和Mac OS X)；安装包仅包含一个可执行文件，方便部署。

### **安装**

[下载](https://www.consul.io/downloads)

![image-20220101211443277](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20220101211443277.png)

启动

```bash
./consul agent -dev
```

访问

```
http://localhost:8500
```

![image-20211115135812296](https://i.loli.net/2021/11/15/bntNQW1eMpRyGAr.png)

### **开发consul客户微服务**

**创建项目并引入consul客户端依赖**

```xml
 <!--引入consul依赖-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-consul-discovery</artifactId>
</dependency>
```

**yml配置**

```properties
server.port=8889
spring.application.name=consulclient8889
spring.cloud.consul.host=localhost														#注册consul服务的主机
spring.cloud.consul.port=8500																	#注册consul服务的端口号
spring.cloud.consul.discovery.register-health-check=false	    #关闭consu了服务的健康检查[不推荐]
spring.cloud.consul.discovery.service-name=${spring.application.name} #指定注册的服务名称 默认就是应用名
```

**consul 开启健康监控检查**

默认情况加consul监控健康是开启的,但是必须依赖健康监控依赖才能正确监控健康状态所以直接启动会显示错误,引入健康监控依赖之后服务正常

```xml
<!-- 这个包是用做健康度监控的-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

## **不同注册中心的区别**

- **CAP定理**：CAP定理又称CAP原则，指的是在一个分布式系统中，一致性（Consistency）、可用性（Availability）、分区容错性（Partition tolerance）。CAP 原则指的是，这三个要素最多只能同时实现两点，不可能三者兼顾。
  	`一致性（C）：在分布式系统中的所有数据备份，在同一时刻是否同样的值。（等同于所有节点访问同一份最新的数据副本）
  	`可用性（A）：在集群中一部分节点故障后，集群整体是否还能响应客户端的读写请求。（对数据更新具备高可用性）分区容忍性（P），就是高可用性，一个节点崩了，并不影响其它的节点（100个节点，挂了几个，不影响服务，越多机器越好）

- **Eureka特点 **

  Eureka中没有使用任何的数据强一致性算法保证不同集群间的Server的数据一致，仅通过数据拷贝的方式争取注册中心数据的最终一致性，虽然放弃数据强一致性但是换来了Server的可用性，降低了注册的代价，提高了集群运行的健壮性。

- **Consul特点**

  基于Raft算法，Consul提供强一致性的注册中心服务，但是由于Leader节点承担了所有的处理工作，势必加大了注册和发现的代价，降低了服务的可用性。通过Gossip协议，Consul可以很好地监控Consul集群的运行，同时可以方便通知各类事件，如Leader选择发生、Server地址变更等。

- **zookeeper特点**

  基于Zab协议，Zookeeper可以用于构建具备数据强一致性的服务注册与发现中心，而与此相对地牺牲了服务的可用性和提高了注册需要的时间。

![image-20211115144808882](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211115144808882.png)

## **服务间通信方式**

接下来在整个微服务架构中,我们比较关心的就是服务间的服务改如何调用,有哪些调用方式?

![image-20211115144833389](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211115144833389.png)

### **基于RestTemplate的服务调用**

spring框架提供的RestTemplate类可用于在应用中调用rest服务，它简化了与http服务的通信方式，统一了RESTful的标准，封装了http链接， 我们只需要传入url及返回值类型即可。相较于之前常用的HttpClient，RestTemplate是一种更优雅的调用RESTful服务的方式。

-Dserver.port=9997

**在商品服务中提供服务方法**

```java
@RestController
@Slf4j
public class ProductController {
    @Value("${server.port}")
    private int port;
    @GetMapping("/product/findAll")
    public Map<String,Object> findAll(){
        log.info("商品服务查询所有调用成功,当前服务端口:[{}]",port);
        Map<String, Object> map = new HashMap<String,Object>();
        map.put("msg","服务调用成功,服务提供端口为: "+port);
        map.put("status",true);
        return map;
    }
}
```

**在用户服务中使用restTemplate进行调用**

```java
@RestController
@Slf4j
public class UserController {
    @GetMapping("/user/findAll")
    public String findAll(){
        log.info("调用用户服务...");
        //1.使用restTemplate调用商品服务
        RestTemplate restTemplate = new RestTemplate();
        String forObject = restTemplate.getForObject("http://localhost:9998/product/findAll", 
                                                     String.class);
        return forObject;
    }
}
```

![image-20211115164847014](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211115164847014.png)

### **总结**

- rest Template是直接基于服务地址调用没有在服务注册中心获取服务,也没有办法完成服务的负载均衡如果需要实现服务的负载均衡需要自己书写服务负载均衡策略。
- restTemplate直接调用存在问题：
  - 直接使用restTemplate方式调用没有经过服务注册中心获取服务地址,代码写死不利于维护,当服务宕机时不能高效剔除
  - 调用服务时没有负载均衡需要自己实现负载均衡策略

## **基于Ribbon服务调用**

Spring Cloud Ribbon是一个基于HTTP和TCP的客户端负载均衡工具，它基于Netflix Ribbon实现。通过Spring Cloud的封装，可以让我们轻松地将面向服务的REST模版请求自动转换成客户端负载均衡的服务调用。

## **Ribbon**

```xml
 <!--引入ribbon依赖-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-ribbon</artifactId>
</dependency>
```

![image-20211115171443888](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211115171443888.png)

在引入consul时默认已经引入了ribbon了，不需要再引入依赖了

## **使用restTemplate + ribbon进行服务调用**

- 使用discovery client  进行客户端调用
- 使用loadBalanceClient 进行客户端调用
- 使用@loadBalanced     进行客户端调用

### **使用discovery Client形式调用**

```java
@Autowired
private DiscoveryClient discoveryClient;

//获取服务列表
List<ServiceInstance> products = discoveryClient.getInstances("服务ID");
for (ServiceInstance product : products) {
  log.info("服务主机:[{}]",product.getHost());
  log.info("服务端口:[{}]",product.getPort());
  log.info("服务地址:[{}]",product.getUri());
  log.info("====================================");
}
```

### **使用loadBalance Client形式调用**

```java
@Autowired
private LoadBalancerClient loadBalancerClient;
//根据负载均衡策略选取某一个服务调用
ServiceInstance product = loadBalancerClient.choose("服务ID");//地址  轮询策略
log.info("服务主机:[{}]",product.getHost());
log.info("服务端口:[{}]",product.getPort());
log.info("服务地址:[{}]",product.getUri());
```

### **使用@loadBalanced（使用最多的一种方式）**

```java
//1.整合restTemplate + ribbon
@Bean
@LoadBalanced
public RestTemplate getRestTemplate(){
  return new RestTemplate();
}
//2.调用服务位置注入RestTemplate
@Autowired
private RestTemplate restTemplate;
//3.调用
String forObject = restTemplate.getForObject("http://服务ID/hello/hello?name=" + name, String.class);
```

## **Ribbon负载均衡策略**

### **ribbon负载均衡算法**

- RoundRobinRule         		轮训策略	按顺序循环选择 Server 

- RandomRule             		随机策略	随机选择 Server  

- AvailabilityFilteringRule 可用过滤策略

  会先过滤由于多次访问故障而处于断路器跳闸状态的服务，还有并发的连接数量超过阈值的服务，然后对剩余的服务列表按照轮询策略进行访问

- WeightedResponseTimeRule  响应时间加权策略

  根据平均响应的时间计算所有服务的权重，响应时间越快服务权重越大被选中的概率越高，刚启动时如果统计信息不足，则使用		
  RoundRobinRule策略，等统计信息足够会切换到

- RetryRule                 重试策略

  先按照RoundRobinRule的策略获取服务，如果获取失败则在制定时间内进行重试，获取可用的服务

- BestAviableRule           最低并发策略    

  会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务

![image-20211116150817781](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211116150817781.png)

### **修改服务默认负载均衡策略**

**修改服务默认随机策略**

-  服务id.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule	

```properties
products.ribbon.NFLoadBalancerRuleClassName=com.netflix.loadbalancer.RandomRule	
```

![image-20211116151031015](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211116151031015.png)

## **OpenFeign组件**

**思考: 使用RestTemplate+ribbon已经可以完成服务间的调用，为什么还要使用feign？**

**存在问题**

- 每次调用服务都需要写这些代码,存在大量的代码冗余
- 服务地址如果修改,维护成本增高
- 使用时不够灵活

Feign是一个声明式的伪Http客户端，它使得写Http客户端变得更简单。使用Feign，只需要创建一个接口并注解。它具有可插拔的注解特性(可以使用springmvc的注解)，可使用Feign 注解和JAX-RS注解。Feign支持可插拔的编码器和解码器。Feign默认集成了Ribbon，默认实现了负载均衡的效果并且springcloud为feign添加了springmvc注解的支持。

### **OpenFeign服务调用**

**1、服务调用方法引入依赖OpenFeign依赖**

```xml
<!--Open Feign依赖-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

**2、入口类加入注解开启OpenFeign支持**

```java
@SpringBootApplication
@EnableFeignClients   //开启openfeign支持
public class Users9999Application {
    public static void main(String[] args) {
        SpringApplication.run(Users9999Application.class, args);
    }
}
```

**3、创建一个客户端调用接口**

```java
//商品服务的opennfeign组件
@FeignClient(value = "product") //表明当前接口是一个feign的组件 value书写调用服务的ServiceId
public interface ProductClient {
    @GetMapping("product/showMsg")
    String showMsg();
    @GetMapping("product/findAll")
    String findAllShops();
}
```

**4、使用feignClient客户端对象调用服务**

```java
@RestController
@Slf4j
public class FeignController {
    @Autowired
    private ProductClient productClient;
    @GetMapping("feign/showMsg")
    public String showMsg(){
        return productClient.showMsg();
    }
    @GetMapping("feign/findAll")
    public String findAll(){
        return productClient.findAllShops();
    }
}
```

**5、访问测试**

![image-20211116160005232](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211116160005232.png)

### **调用服务并传参**

服务和服务之间通信,不仅仅是调用,往往在调用过程中还伴随着参数传递,接下来重点来看看OpenFeign在调用服务时如何传递参数

#### **GET方式调用服务并传递参数**

- 在商品服务中加入需要传递参数的服务方法来进行测试

- 在用户服务中进行调用商品服务中需要传递参数的服务方法进行测试

**1.商品服务中添加如下方法**

```java
// 1.商品服务中添加如下方法
 @GetMapping("/product/findOne")
public Map<String,Object> findOne(String productId){
  log.info("商品服务查询商品信息调用成功,当前服务端口:[{}]",port);
  log.info("当前接收商品信息的id:[{}]",productId);
  Map<String, Object> map = new HashMap<String,Object>();
  map.put("msg","商品服务查询商品信息调用成功,当前服务端口: "+port);
  map.put("status",true);
  map.put("productId",productId);
  return map;
}
```

**2.用户服务中在product客户端中声明方法**

```java
//2.用户服务中在product客户端中声明方法
@FeignClient("PRODUCTS")
public interface ProductClient { 
	@GetMapping("/product/findOne")
 	String findOne(@RequestParam("productId") String productId);
}
```

**3.用户服务中调用并传递参数**

```java
//3.用户服务中调用并传递参数
//注入客户端对象
@Autowired
private ProductClient productClient;

@GetMapping("/feign/test1")
public Map<String,Object> test1(String id){
  log.info("用来测试Openfiegn的GET方式参数传递");
  Map<String, Object> msg = productClient.findOne(id);
  log.info("调用返回信息:[{}]",msg);
  return msg;
}
```

**测试访问**

![image-20211117194537500](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211117194537500.png)

#### **POST方式调用方法传递参数**

- 在商品服务中加入需要传递参数的服务方法来进行测试

- 在用户服务中进行调用商品服务中需要传递参数的服务方法进行测试

**1.商品服务加入post方式请求并接受name**

```java
@PostMapping("/product/save")
public Map<String,Object> save(String name){
  log.info("商品服务保存商品调用成功,当前服务端口:[{}]",port);
  log.info("当前接收商品名称:[{}]",name);
  Map<String, Object> map = new HashMap<String,Object>();
  map.put("msg","商品服务查询商品信息调用成功,当前服务端口: "+port);
  map.put("status",true);
  map.put("name",name);
  return map;
}
```

**2.用户服务中在product客户端中声明方法**

```java
@FeignClient("PRODUCTS")
public interface ProductClient {
    @PostMapping("/product/save")
    String save(@RequestParam("name") String name);
}
```

**3.用户服务中调用并传递参数**

```java
@Autowired
private ProductClient productClient;

@GetMapping("/user/save")
public String save(String productName){
  log.info("接收到的商品信息名称:[{}]",productName);
  String save = productClient.save(productName);
  log.info("调用成功返回结果: "+save);
  return save;
}
```

**4.访问测试**

![image-20211117200353885](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211117200353885.png)

#### **POST传递对象**

**1.商品服务定义对象**

```java
@Data
public class Product {
    private String id;
    private String name;
    private Double price;
    private Date update;
}
```

**2.商品服务定义接收对象的方法**

```java
//2.商品服务定义接收对象的方法
@PostMapping("/product/saveProduct")
public Map<String,Object> saveProduct(@RequestBody Product product){
  log.info("商品服务保存商品信息调用成功,当前服务端口:[{}]",port);
  log.info("当前接收商品名称:[{}]",product);
  Map<String, Object> map = new HashMap<String,Object>();
  map.put("msg","商品服务查询商品信息调用成功,当前服务端口: "+port);
  map.put("status",true);
  map.put("product",product);
  return map;
}
```

**3.将商品对象复制到用户服务中**

```java
@FeignClient("PRODUCTS")
public interface ProductClient {
  @PostMapping("/product/saveProduct")
  String saveProduct(@RequestBody Product product);
}
//注意:服务提供方和调用方一定要加入@RequestBody注解
```

**4.在用户服务中调用保存商品信息服务**

```java
@Autowired
private ProductClient productClient;

@GetMapping("/user/saveProduct")
public String saveProduct(Product product){
  log.info("接收到的商品信息:[{}]",product);
  String save = productClient.saveProduct(product);
  log.info("调用成功返回结果: "+save);
  return save;
}
```

**5.测试**

![image-20211117204653468](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211117204653468.png)

### **OpenFeign超时设置**

默认情况下,openFiegn在进行服务调用时,要求服务提供方处理业务逻辑时间必须在1S内返回,如果超过1S没有返回则OpenFeign会直接报错,不会等待服务执行,但是往往在处理复杂业务逻辑是可能会超过1S,因此需要修改OpenFeign的默认服务调用超时时间。

**1.模拟超时**

![image-20211119174033801](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211119174033801.png)

**2.进行客户端调用**

![image-20211119174148606](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211119174148606.png)

**3.修改OpenFeign默认超时时间**

```properties
feign.client.config.PRODUCTS.connectTimeout=5000  #配置指定服务连接超时
feign.client.config.PRODUCTS.readTimeout=5000		  #配置指定服务等待超时
#feign.client.config.default.connectTimeout=5000  #配置所有服务连接超时
#feign.client.config.default.readTimeout=5000			#配置所有服务等待超时
```

### **OpenFeign调用详细日志展示**

往往在服务调用时我们需要详细展示feign的日志,默认feign在调用是并不是最详细日志输出,因此在调试程序时应该开启feign的详细日志展示。feign对日志的处理非常灵活可为每个feign客户端指定日志记录策略，每个客户端都会创建一个logger默认情况下logger的名称是feign的全限定名需要注意的是，feign日志的打印只会DEBUG级别做出响应。

我们可以为feign客户端配置各自的logger.level对象，告诉feign记录那些日志logger.lever有以下的几种值

- NONE  不记录任何日志
- BASIC 仅仅记录请求方法，url，响应状态代码及执行时间
- HEADERS 记录Basic级别的基础上，记录请求和响应的header
- FULL 记录请求和响应的header，body和元数据

**1.开启日志展示**

```properties
feign.client.config.PRODUCTS.loggerLevel=full  #开启指定服务日志展示
#feign.client.config.default.loggerLevel=full  #全局开启服务日志展示
logging.level.com.baizhi.feignclients=debug    #指定feign调用客户端对象所在包,必须是debug级别
```

**2.测试服务调用查看日志**

![image-20211121112713007](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211121112713007.png)

## **Hystri组件使用**

### **Hystriy组件**

![image-20211121113523014](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211121113523014.png)

In a distributed environment, inevitably some of the many service dependencies will fail. Hystrix is a library that helps you control the interactions between these distributed services by adding latency tolerance and fault tolerance logic. Hystrix does this by isolating points of access between the services, stopping cascading failures across them, and providing fallback options, all of which improve your system’s overall resiliency.														--[摘自官方]

在分布式环境中，许多服务依赖项不可避免地会失败。Hystrix是一个库，它通过添加延迟容忍和容错逻辑来帮助您控制这些分布式服务之间的交互。Hystrix通过隔离服务之间的访问点、停止它们之间的级联故障以及提供后备选项来实现这一点，所有这些都可以提高系统的整体弹性。

通俗定义: Hystrix是一个用于处理分布式系统的延迟和容错的开源库，在分布式系统中，许多依赖不可避免的会调用失败，超时、异常等，Hystrix能够保证在一个依赖出问题的情况下，不会导致整体服务失败，避免级联故障(服务雪崩现象)，提高分布式系统的弹性。

### **服务雪崩**

**1.服务雪崩**

在微服务之间进行服务调用是由于某一个服务故障，导致级联服务故障的现象，称为雪崩效应。雪崩效应描述的是提供方不可用，导致消费方不可用并将不可用逐渐放大的过程。

**2.图解雪崩效应**

![image-20211121151913749](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211121151913749.png)

而此时，Service A的流量波动很大，流量经常会突然性增加！那么在这种情况下，就算Service A能扛得住请求，Service B和Service C未必能扛得住这突发的请求。此时，如果Service C因为抗不住请求，变得不可用。那么Service B的请求也会阻塞，慢慢耗尽Service B的线程资源，Service B就会变得不可用。紧接着，Service A也会不可用，这一过程如下图所示

![image-20211121151953495](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211121151953495.png)

这里推荐一个并发压力测试工具[Jmeter](https://jmeter.apache.org/download_jmeter.cgi)

### **服务熔断**

“熔断器”本身是一种开关装置，当某个服务单元发生故障之后，通过断路器的故障监控，某个异常条件被触发，直接熔断整个服务。向调用方法返回一个符合预期的、可处理的备选响应(FallBack),而不是长时间的等待或者抛出调用方法无法处理的异常，就保证了服务调用方的线程不会被长时间占用，避免故障在分布式系统中蔓延，乃至雪崩。如果目标服务情况好转则恢复调用。服务熔断是解决服务雪崩的重要手段。

**服务熔断图示**

![image-20211121155824040](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211121155824040.png)

### **服务降级**

**服务降级说明**

服务压力剧增的时候根据当前的业务情况及流量对一些服务和页面有策略的降级，以此环节服务器的压力，以保证核心任务的进行。同时保证部分甚至大部分任务客户能得到正确的相应。也就是当前的请求处理不了了或者出错了，给一个默认的返回。

通俗: 关闭系统中边缘服务 保证系统核心服务的正常运行  称之为服务降级（比如双11，淘宝 删除地址  确认收货  删除订单   取消支付   节省cpu  内存）

**服务降级图示**

![image-20211121160555589](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211121160555589.png)

### **降级和熔断的总结**

**1.共同点**

- 目的很一致，都是从可用性可靠性着想，为防止系统的整体缓慢甚至崩溃，采用的技术手段；
- 最终表现类似，对于两者来说，最终让用户体验到的是某些功能暂时不可达或不可用；
- 粒度一般都是服务级别，当然，业界也有不少更细粒度的做法，比如做到数据持久层（允许查询，不允许增删改）；
- 自治性要求很高，熔断模式一般都是服务基于策略的自动触发，降级虽说可人工干预，但在微服务架构下，完全靠人显然不可能，开关预置、配置中心都是必要手段； 

**2.异同点**

- 触发原因不太一样，服务熔断一般是某个服务（下游服务）故障引起，而服务降级一般是从整体负荷考虑；
- 管理目标的层次不太一样，熔断其实是一个框架级的处理，每个微服务都需要（无层级之分），而降级一般需要对业务有层级之分（比如降级一般是从最外围服务开始）

**3.总结**

- 熔断必会触发降级,所以熔断也是降级一种,区别在于熔断是对调用链路的保护,而降级是对系统过载的一种保护处理

###  **服务熔断的实现**

**1.项目中引入hystrix依赖**

```xml
<!--引入hystrix-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

**2.开启断路器**

```java
@SpringBootApplication
@EnableCircuitBreaker  //用来开启断路器
public class Products9998Application {
    public static void main(String[] args) {
        SpringApplication.run(Products9998Application.class, args);
    }
}
```

**3.使用HystrixCommand注解实现断路**

```java
//服务熔断
@GetMapping("/product/break")
@HystrixCommand(fallbackMethod = "testBreakFall" )
public String testBreak(int id){
  log.info("接收的商品id为: "+ id);
  if(id<=0){
    throw new RuntimeException("数据不合法!!!");
  }
  return "当前接收商品id: "+id;
}

public String testBreakFall(int id){
  return "当前数据不合法: "+id;
}
```

![image-20211121164518326](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211121164518326.png)

**4.访问测试**

![image-20211121164614527](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211121164614527.png)

![image-20211121164632107](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211121164632107.png)

**5.总结**

从上面演示过程中会发现如果触发一定条件断路器会自动打开,过了一点时间正常之后又会关闭。那么断路器打开条件是什么呢？

**6.断路器打开条件**

- [官网解释](https://cloud.spring.io/spring-cloud-netflix/2.2.x/reference/html/#circuit-breaker-spring-cloud-circuit-breaker-with-hystrix)

A service failure in the lower level of services can cause cascading failure all the way up to the user. When calls to a particular service exceed `circuitBreaker.requestVolumeThreshold` (default: 20 requests) and the failure percentage is greater than `circuitBreaker.errorThresholdPercentage` (default: >50%) in a rolling window defined by `metrics.rollingStats.timeInMilliseconds` (default: 10 seconds), the circuit opens and the call is not made. In cases of error and an open circuit, a fallback can be provided by the developer.																		--摘自官方

**原文翻译之后,总结打开关闭的条件:**

1、 当满足一定的阀值的时候（默认10秒内超过20个请求次数）

2、 当失败率达到一定的时候（默认10秒内超过50%的请求失败）

3、 到达以上阀值，断路器将会开启

4、 当开启的时候，所有请求都不会进行转发

5、 一段时间之后（默认是5秒），这个时候断路器是半开状态，会让其中一个请求进行转发。如果成功，断路器会关闭，若失败，继续开启。重复4和5。

![image-20211121164912302](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211121164912302.png)

#### **默认的服务FallBack处理方法**

如果为每一个服务方法开发一个降级,对于我们来说,可能会出现大量的代码的冗余,不利于维护,这个时候就需要加入默认服务降级处理方法

```java
@GetMapping("product/break")
    @HystrixCommand(defaultFallback = "defaaultFallback")
    public String testBreak(Integer id){
        if (id<0){
            throw new RuntimeException("非法参数，id不能小于0");
        }else {
            return "访问成功，当前查询的id为："+id;
        }
    }
  //默认熔断处理方法
    public String defaaultFallback(){
        return "熔断触发，请求失败";
    }
```

### **服务降级实现**

**1.客户端openfeign + hystrix实现服务降级实现**

- 引入hystrix依赖
- 配置文件开启feign支持hystrix
- 在feign客户端调用加入fallback指定降级处理
- 开发降级处理方法

```xml
<!--引入hystrix-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

**2.开启openfeign支持服务降级**

```properties
feign.hystrix.enabled=true #开启openfeign支持降级
```

**3.在openfeign客户端中加如Hystrix**

```java
@FeignClient(value = "PRODUCTS",fallback = ProductFallBack.class)
public interface ProductClient {
    @GetMapping("/product/hystrix")
    String testHystrix(@RequestParam("name") String name);
}
```

**4.开发fallback处理类**

```java
@Component
public class ProductFallBack implements ProductClient {
    @Override
    public String testHystrix(String name) {
        return "我是客户端的Hystrix服务实现!!!";
    }
}
```

![image-20211121173957229](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211121173957229.png)

**注意:如果服务端降级和客户端降级同时开启,要求服务端降级方法的返回值必须与客户端方法降级的返回值一致!!**

**客户端降级更多是处理一些down掉的服务，而服务端降级更多是处理一些异常错误之类的防止服务雪崩**

### **Hystrix Dashboard**

别用了，很不好用，早就被抛弃了

## **GateWay组件使用**

### **什么是服务网关**

网关统一服务入口，可方便实现对平台众多服务接口进行管控，对访问服务的身份认证、防报文重放与防数据篡改、功能调用的业务鉴权、响应数据的脱敏、流量与并发控制，甚至基于API调用的计量或者计费等等。

网关 =  路由转发 + 过滤器 ，路由转发：接收一切外界请求，转发到后端的微服务上去；在服务网关中可以完成一系列的横切功能，例如权限校验、限流以及监控等，这些都可以通过过滤器完成

**2.为什么需要网关**

1.网关可以实现服务的统一管理

2.网关可以解决微服务中通用代码的冗余问题(如权限控制,流量监控,限流等)

**3.网关组件在微服务中架构**

![image-20211122162400882](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211122162400882.png)

### **服务网关组件**

#### **zuul**

[zuul](https://github.com/Netflix/zuul/wiki)是从设备和网站到Netflix流媒体应用程序后端的所有请求的前门。作为一个边缘服务应用程序，zul被构建为支持动态路由、监视、弹性和安全性。

#### [**gateway**](https://spring.io/projects/spring-cloud-gateway)

This project provides a library for building an API Gateway on top of Spring MVC. Spring Cloud Gateway aims to provide a simple, yet effective way to route to APIs and provide cross cutting concerns to them such as: security, monitoring/metrics, and resiliency.

这个项目提供了一个在springmvc之上构建API网关的库。springcloudgateway旨在提供一种简单而有效的方法来路由到api，并为api提供横切关注点，比如：安全性、监控/度量和弹性。

**1.创建项目引入网关依赖**

```xml
<!--引入gateway网关依赖-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-gateway</artifactId>
</dependency>
```

**2.编写网关配置**

```yaml
spring:
  application:
    name: gateway
  cloud:
    consul:
      host: localhost
      port: 8500
    gateway:
      routes:
        - id: user_route							# 指定路由唯一标识
          uri: http://localhost:9999/ # 指定路由服务的地址
          predicates:
            - Path=/user/**					  # 指定路由规则

        - id: product_route
          uri: http://localhost:9998/
          predicates:
            - Path=/product/**
server:
  port: 8989
```

**3.启动gateway网关项目**

![image-20211122162851317](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211122162851317.png)

- 在启动日志中发现,gateway为了效率使用webflux进行异步非阻塞模型的实现,因此和原来的web包冲突,去掉原来的web即可

![image-20211122162917199](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211122162917199.png)

**4.测试网关路由转发**

- 测试通过网关访问用户服务: http://localhost:8989/user/findOne?productId=21

- 测试通过网关访问商品服务: http://localhost:8989/product/findOne?productId=1

#### **java方式配置路由**

```java
@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("order_route", r -> r.path("/order/**")
                        .uri("http://localhost:9997/"))
                .build();
    }
}
```

#### **查看路由网关规则表**

gateway提供路由访问规则列表的web界面,但是默认是关闭的,如果想要查看服务路由规则可以在配置文件中开启

```yaml
management:
  endpoints:
    web:
      exposure:
        include: "*"   #开启所有web端点暴露
```

访问路由管理列表地址
- http://localhost:8989/actuator/gateway/routes

![image-20211122165544656](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211122165544656.png)

#### **配置路由负载均衡**

现有路由配置方式,都是基于服务地址写死的路由转发,能不能根据服务名称进行路由转发同时实现负载均衡的呢?

**动态路由以及负载均衡转发配置**

```yaml
spring
  application:
    name: gateway
  cloud:
    consul:
      host: localhost
      port: 8500
    gateway:
      routes:
        - id: user_route
          #uri: http://localhost:9999/
          uri: lb://users							# lb代表转发后台服务使用负载均衡,users代表服务注册中心上的服务名
          predicates:
            - Path=/user/**

        - id: product_route
          #uri: http://localhost:9998/
          uri: lb://products          # lb(loadbalance)代表负载均衡转发路由
          predicates:
            - Path=/product/**
      discovery:
        locator:
          enabled: true 							#开启根据服务名动态获取路由
```

![image-20211122171714995](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211122171714995.png)

#### **常见路由predicate（断言验证）**

**1.Gateway支持多种方式的predicate**

![image-20211123151454726](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211123151454726.png)

- After=2020-07-21T11:33:33.993+08:00[Asia/Shanghai]  			指定日期之后的请求进行路由
- Before=2020-07-21T11:33:33.993+08:00[Asia/Shanghai]       指定日期之前的请求进行路由
- Between=2017-01-20T17:42:47.789-07:00[America/Denver], 2017-01-21T17:42:47.789-07:00[America/Denver]
- Cookie=username,chenyn																		基于指定cookie的请求进行路由
- Cookie=username,[A-Za-z0-9]+															`基于指定cookie的请求进行路由	
	`curl http://localhost:8989/user/findAll --cookie "username=zhangsna"
- Header=X-Request-Id, \d+																 基于请求头中的指定属性的正则匹配路由(这里全是整数)
	curl http://localhost:8989/user/findAll -H "X-Request-Id:11"
- Method=GET,POST																						 基于指定的请求方式请求进行路由

[官方更多](https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.3.RELEASE/reference/html/#the-cookie-route-predicate-factory)

**2.使用predicate**

```yaml
spring:
  application:
    name: gateway
  cloud:
    consul:
      host: localhost
      port: 8500
    gateway:
      routes:
        - id: user_route
          #uri: http://localhost:9999/
          uri: lb://users
          predicates:
            - Path=/user/**
            - After=2020-07-21T11:39:33.993+08:00[Asia/Shanghai]
            - Cookie=username,[A-Za-z0-9]+
            - Header=X-Request-Id, \d+
```

#### **常用的Filter以及自定义filter**

路由过滤器允许以某种方式修改传入的HTTP请求或传出的HTTP响应。路由筛选器的作用域是特定路由。springcloudgateway包括许多内置的GatewayFilter工厂。

[官网](https://cloud.spring.io/spring-cloud-static/spring-cloud-gateway/2.2.3.RELEASE/reference/html/#gatewayfilter-factories)

**1.作用**

当我们有很多个服务时，比如下图中的user-service、order-service、product-service等服务，客户端请求各个服务的Api时，每个服务都需要做相同的事情，比如鉴权、限流、日志输出等。

![image-20211124170221778](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211124170221778.png)

![image-20211124170236432](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211124170236432.png)

**2.使用内置过滤器**

![image-20211124170331317](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211124170331317.png)

使用方式如下

```yaml
spring:
  application:
    name: gateway
  cloud:
    gateway:
      routes:
        - id: product_route
          #uri: http://localhost:9998/
          uri: lb://products     # lb: 使用负载均衡策略   products代表注册中心的具体服务名
          predicates:
            - Path=/product/**
            #- After=2020-07-30T09:45:49.078+08:00[Asia/Shanghai]
          filters:
            - AddRequestParameter=id,34
            - AddResponseHeader=username,chenyn
```

**3.使用自定义filter**

全局的拦截

```java
@Configuration
@Slf4j
public class CustomGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("进入自定义的filter");
        if(exchange.getRequest().getQueryParams().get("username")!=null){
            log.info("用户身份信息合法,放行请求继续执行!!!");
            return chain.filter(exchange);
        }
        log.info("非法用户,拒绝访问!!!");
       return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {  //filter 数字越小filter越先执行
        return -1;           //-1  最先执行
    }
}
```

## **Config组件**

### **什么是config**

config(配置)又称为 统一配置中心顾名思义,就是将配置统一管理,配置统一管理的好处是在日后大规模集群部署服务应用时相同的服务配置一致,日后再修改配置只需要统一修改全部同步,不需要一个一个服务手动维护。[官网说明](https://cloud.spring.io/spring-cloud-static/spring-cloud-config/2.2.3.RELEASE/reference/html/#_spring_cloud_config_server)

**1.统一配置中心组件流程图**

![image-20211125093936670](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211125093936670.png)

### **Config Server 开发**

**1.引入依赖**

```xml
<!--引入统一配置中心-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-config-server</artifactId>
</dependency>
```

**2.开启统一配置中心服务**

```java
@SpringBootApplication
@EnableConfigServer
public class Configserver7878Application {
	public static void main(String[] args) {
		SpringApplication.run(Configserver7878Application.class, args);
	}
}
```

**3.修改配置文件**

```properties
server.port=7878
spring.application.name=configserver
spring.cloud.consul.host=localhost
spring.cloud.consul.port=8500
```

**4.直接启动服务报错**

![image-20211125094137036](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211125094137036.png)

**5.创建远程仓库**

gitee一个远程仓库

![image-20211125094229351](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211125094229351.png)

**6.复制仓库地址**

![image-20211125094255758](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211125094255758.png)

**7.在统一配置中心服务中修改配置文件指向远程仓库地址**

```properties
spring.cloud.config.server.git.uri=https://github.com/chenyn-java/configservers.git
#spring.cloud.config.server.git.username=       私有仓库访问用户名
#spring.cloud.config.server.git.password=				私有仓库访问密码
```

**8.再次启动统一配置中心**

![image-20211125094353177](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211125094353177.png)

**9.拉取远端配置 [三种方式]**

1. http://localhost:7878/test-xxxx.properties
2. http://localhost:7878/test-xxxx.json
3. http://localhost:7878/test-xxxx.yml

![image-20211125094513284](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211125094513284.png)

**10.拉取远端配置规则**

- label/name-profiles.yml

label   代表去那个分支获取 默认使用master分支

name    代表读取那个具体的配置文件文件名称

profile 代表读取配置文件环境

![image-20211125094639135](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211125094639135.png)

**11.查看拉取配置详细信息**

![image-20211125094710271](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211125094710271.png)

**12.指定分支和本地仓库位置**

```properties
spring.cloud.config.server.git.basedir=/localresp 		#一定要是一个空目录,在首次会将该目录清空
spring.cloud.config.server.git.default-label=master   #指定使用远程仓库中那个分支中内容
```

![image-20211125094804453](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211125094804453.png)

### **ConfigClient开发**

**1.项目中引入config client依赖**

```xml
<!--引入config client-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

**2.编写配置文件**

```properties
spring.cloud.config.discovery.enabled=true                #开启统一配置中心服务
spring.cloud.config.discovery.service-id=configserver     #指定统一配置服务中心的服务唯一标识
spring.cloud.config.label=master													#指定从仓库的那个分支拉取配置	
spring.cloud.config.name=client														#指定拉取配置文件的名称
spring.cloud.config.profile=dev														#指定拉取配置文件的环境
```

**3.远程仓库创建配置文件**

```properties
 client.properties										[用来存放公共配置][]
	spring.application.name=configclient
	spring.cloud.consul.host=localhost
	spring.cloud.consul.port=8500

 client-dev.properties  							[用来存放研发相关配置][注意:这里端口为例,以后不同配置分别存放]
	server.port=9099

 client-prod.properties							[用来存放生产相关配置][]
	server.port=9098
```

![image-20211201205218939](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211201205218939.png)

**4.启动客户端服务进行远程配置拉取测试**

- 直接启动过程中发现无法启动直接报错

![image-20211201205252907](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211201205252907.png)

**报错原因**

项目中目前使用的是application.properties启动项目,使用这个配置文件在springboot项目启动过程中不会等待远程配置拉取,直接根据配置文件中内容启动,因此当需要注册中心,服务端口等信息时,远程配置还没有拉取到,所以直接报错

![image-20211201205328622](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211201205328622.png)

**解决方案**

应该在项目启动时先等待拉取远程配置,拉取远程配置成功之后再根据远程配置信息启动即可,为了完成上述要求springboot官方提供了一种解决方案,就是在使用统一配置中心时应该将微服务的配置文件名修改为bootstrap.(properties|yml),bootstrap.properties作为配置启动项目时,会优先拉取远程配置,远程配置拉取成功之后根据远程配置启动当前应用。

![image-20211201205357697](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211201205357697.png)

**再次启动服务**

![image-20211201205420939](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211201205420939.png)

- **注意spring.application.name写到项目的配置里就好了，不要写到远程git上，不然会报错，而且这个consul-id不会经常改变的**

### **手动配置刷新**

在生产环境中,微服务可能非常多,每次修改完远端配置之后,不可能对所有服务进行重新启动,这个时候需要让修改配置的服务能够刷新远端修改之后的配置,从而不要每次重启服务才能生效,进一步提高微服务系统的维护效率。在springcloud中也为我们提供了手动刷新配置和自动刷新配置两种策略,这里我们先试用手动配置文件刷新。

**1.在config client端加入刷新暴露端点**

```properties
management.endpoints.web.exposure.include="*"          #开启所有web端点暴露  [推荐使用这种]
```

![image-20211204203932095](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211204203932095.png)

**2.在需要刷新代码的类中加入刷新配置的注解**

```java
@RestController
@RefreshScope
@Slf4j
public class TestController {
    @Value("${name}")
    private String name;
    @GetMapping("/test/test")
    public String test(){
      log.info("当前加载配置文件信息为:[{}]",name);
      return name;
    }
}
```

**3.在远程配置中加入name并启动测试**

![image-20211204204152182](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211204204152182.png)

**4.启动之后直接访问**

![image-20211204204222675](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211204204222675.png)

**5.修改远程配置**

**6.修改之后在访问**

发现并没有自动刷新配置?

必须调用刷新配置接口才能刷新配置

![image-20211204204350270](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211204204350270.png)

**7.在次访问发现配置已经成功刷新**

![image-20211204204429861](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211204204429861.png)

## **Bus组件的使用**

### **什么是Bus？**

Spring Cloud Bus links nodes of a distributed system with a lightweight message broker. This can then be used to broadcast state changes (e.g. configuration changes) or other management instructions. AMQP and Kafka broker implementations are included with the project. Alternatively, any [Spring Cloud Stream](https://spring.io/projects/spring-cloud-stream) binder found on the classpath will work out of the box as a transport.   --[摘自官网](https://spring.io/projects/spring-cloud-bus)

springcloudbus使用轻量级消息代理将分布式系统的节点连接起来。然后，可以使用它来广播状态更改（例如配置更改）或其他管理指令。AMQP和Kafka broker实现包含在项目中。或者，在类路径上找到的任何springcloudstream绑定器都可以作为传输使用。

通俗定义: bus称之为springcloud中消息总线,主要用来在微服务系统中实现远端配置更新时通过广播形式通知所有客户端刷新配置信息,避免手动重启服务的工作。

### **实现刷新配置原理**

![image-20211211104509258](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20211211104509258.png)

### **搭建RabbitMQ服务**

[详情见笔记](http://www.ningyyds.cn/blog/240)

### **实现自动刷新配置**

**1.在所有项目中引入bus依赖**

```xml
<!--引入bus依赖-->
<dependency>
  <groupId>org.springframework.cloud</groupId>
  <artifactId>spring-cloud-starter-bus-amqp</artifactId>
</dependency>
```

**2.配置统一配置中心连接到mq**

```properties
spring.rabbitmq.host=localhost											#连接主机
spring.rabbitmq.port=5672														#连接mq端口
spring.rabbitmq.username=user												#连接mq用户名
spring.rabbitmq.password=password										#连接mq密码
```

**3.远端配置中加入连接mq配置**

![image-20220110182425113](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20220110182425113.png)

**4.启动统一配置中心服务**

![image-20220110182457952](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20220110182457952.png)

**5.启动客户端服务**

- 加入bus组件之后客户端启动报错
- 原因springcloud中默认链接不到远程服务器不会报错,但是在使用bus消息总线时必须开启连接远程服务失败报错

![image-20220110182609503](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20220110182609503.png)

```properties
spring.cloud.config.fail-fast=true
```

![image-20220110182836651](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20220110182836651.png)

**6.修改远程配置后在配置中心服务通过执行post接口刷新配置**

![image-20220110182905457](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20220110182905457.png)

**7.通过上述配置就实现了配置统一刷新**

### **指定服务刷新配置**

**1.说明**

- 默认情况下使用POST http://localhost:7878/actuator/bus-refresh这种方式刷新配置是全部广播形式,也就是所有的微服务都能接收到刷新配置通知,但有时我们修改的仅仅是某个服务的配置,这个时候对于其他服务的通知是多余的,因此就需要指定服务进行通知

**2.指定服务刷新配置实现**

- 指定端口刷新某个具体服务: curl -X POST http://localhost:7878/actuator/bus-refresh/configclient:9090
- 指定服务id刷新服务集群节点: curl -X POST http://localhost:7878/actuator/bus-refresh/configclient 

### **集成Webhook实现自动刷新**

**1.配置webhook**

![image-20220111123900239](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20220111123900239.png)

![image-20220111124019342](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20220111124019342.png)

**2.解决400错误问题**

- 在配置中心服务端加入过滤器进行解决(springcloud中一个坑)

```java
@Component
public class UrlFilter  implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
 
    }
 
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest)request;
        HttpServletResponse httpServletResponse = (HttpServletResponse)response;
 
        String url = new String(httpServletRequest.getRequestURI());
 
        //只过滤/actuator/bus-refresh请求
        if (!url.endsWith("/bus-refresh")) {
            chain.doFilter(request, response);
            return;
        }
 
        //获取原始的body
        String body = readAsChars(httpServletRequest);
 
        System.out.println("original body:   "+ body);
 
        //使用HttpServletRequest包装原始请求达到修改post请求中body内容的目的
        CustometRequestWrapper requestWrapper = new CustometRequestWrapper(httpServletRequest);
 
        chain.doFilter(requestWrapper, response);
 
    }
 
    @Override
    public void destroy() {
 
    }
 
    private class CustometRequestWrapper extends HttpServletRequestWrapper {
        public CustometRequestWrapper(HttpServletRequest request) {
            super(request);
        }
 
        @Override
        public ServletInputStream getInputStream() throws IOException {
            byte[] bytes = new byte[0];
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
 
            return new ServletInputStream() {
                @Override
                public boolean isFinished() {
                    return byteArrayInputStream.read() == -1 ? true:false;
                }
 
                @Override
                public boolean isReady() {
                    return false;
                }
 
                @Override
                public void setReadListener(ReadListener readListener) {
 
                }
 
                @Override
                public int read() throws IOException {
                    return byteArrayInputStream.read();
                }
            };
        }
    }
 
    public static String readAsChars(HttpServletRequest request)
    {
 
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder("");
        try
        {
            br = request.getReader();
            String str;
            while ((str = br.readLine()) != null)
            {
                sb.append(str);
            }
            br.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != br)
            {
                try
                {
                    br.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
```

![image-20220111124129105](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20220111124129105.png)

## **内网穿透natapp**

**1.[下载客户端](https://natapp.cn/#download)**

**2.用Item2打开**

**3.运行以下命令**

- 进入到目录后输入下面命令行 chmod 777 natapp 是为所有用户开启执行命令 (如果不加这一步 也可以直接用sudo+第二步运行软件)
- 然后输入 ./natapp -authtoken=你的authtoken值  

**注：natapp可以免费使用一个隧道**

![image-20220111124657114](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20220111124657114.png)

![image-20220111124714974](https://pic-es.oss-cn-shanghai.aliyuncs.com/img/image-20220111124714974.png)
