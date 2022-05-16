# **JWT**

## **什么是jwt？**

Json web token (JWT), 是为了在网络应用环境间传递声明而执行的一种基于JSON的开放标准（[(RFC 7519](https://link.jianshu.com?t=https://tools.ietf.org/html/rfc7519)).该token被设计为紧凑且安全的，特别适用于分布式站点的单点登录（SSO）场景。JWT的声明一般被用来在身份提供者和服务提供者间传递被认证的用户身份信息，以便于从资源服务器获取资源，也可以增加一些额外的其它业务逻辑所必须的声明信息，该token也可直接被用于认证，也可被加密。

## **为什么是jwt**

首先说一下基于传统的Session认证

1. 认证方式

   http协议本身就是一种无状态协议，而这就意味着如果用户向我们的应用提供了用户名和密码来进行用户认证，那么下一次请求时，用户还需要进行一次用户认证，因为根据http协议我们并不知道是哪个用户发出的请求，所以为了让我们让我们应用能识别是哪个用户发送的请求，我们只能在服务器存储一份用户登录的信息，这份登录信息会在响应时传递给浏览器，告诉其保存为cookie，以便下次请求时发送给我们应用，这样我们的应用就能识别出是哪个用户了，这就是基于传统的session认证

2. 认证流程

   ![image-20211021103625869](https://i.loli.net/2021/10/21/CgdKz6lQ4RtiFHu.png)

3. 暴露问题

   ![image-20211021103655613](https://i.loli.net/2021/10/21/EyBx2aACL53sKb4.png)

## **基于jwt认证**

![image-20211021104224720](https://i.loli.net/2021/10/21/GDYXkZIVcUzdFsh.png)

- session是存在服务器内存上的，而jwt是存储在本地浏览器上的

![image-20211021104916399](https://i.loli.net/2021/10/21/EqfjBgwcRG8KXvy.png)

## **jwt结构**

![image-20211021105222061](https://i.loli.net/2021/10/21/P8lMCNXAYVhi7cz.png)

![image-20211021105417642](https://i.loli.net/2021/10/21/ZvRV6nfytw1pgsE.png)

![image-20211021105644942](https://i.loli.net/2021/10/21/5efOJ1mHSzIpR4d.png)

![image-20211021110507954](https://i.loli.net/2021/10/21/JjIp8mSMnwds3CR.png)

![image-20211021110728385](https://i.loli.net/2021/10/21/r8xwdzXenFAEJq7.png)

 

## **使用JWT**

```xml
              <dependency>
                    <groupId>com.auth0</groupId>
                    <artifactId>java-jwt</artifactId>
                    <version>3.18.1</version>
                </dependency>
```

生成token

```java
//生成token
    @Test
    void testToken(){
        HashMap<String, Object> map = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND,100000000);
        String token =  JWT.create()
                .withHeader(map)
                .withClaim("id",22)
                .withClaim("username","zs")
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256("!&*^hdad"));
        System.out.println(token);
    }
```

验证token

```java
@Test
void testExam(){
    //创建验证对象
    JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!&*^hdad")).build();
    //通过这个对象可以拿出负载信息
    DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MjIsImV4cCI6MTczNDgwMTE4NSwidXNlcm5hbWUiOiJ6cyJ9.md55tFKkqiH878dNQTEeAWugspQyNPai_7qHwcWuDxY");
    System.out.println(verify.getClaim("id").asInt());
    System.out.println(verify.getClaim("username").asString());
    System.out.println(verify.getHeader());
}
```



## **封装工具类**

```java
public class jwtUtil {
    private static final String signture = "!&*^hdad&*T*GUGyyugu&*T^T*";
    /**
     * 生成token header.payloder.signture
     */
    public static String getToken(Map<String,String> map){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE ,7);//默认7天过期
        //创建jwtbuilder
        JWTCreator.Builder builder = JWT.create();
        //payload
        map.forEach((k,v)->{
            builder.withClaim(k,v);
        });
        String token = builder.withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(signture));
        return token;
    }
    /**
     * 验证token合法性
     */
    public static DecodedJWT verify(String token){
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(signture)).build().verify(token);
        return verify;
    }

    /**
     * 获取token信息的方法
     */
//    public static DecodedJWT getTokenInfo(String token){
//        DecodedJWT verify = JWT.require(Algorithm.HMAC256(signture)).build().verify(token);
//        return verify;
//    }
}
```

## **随便写一个登录验证**

Service

```java
@Override
public Users login(Users user) {
    QueryWrapper wrapper = new QueryWrapper();
    wrapper.eq("username",user.getUsername());
    wrapper.eq("password",user.getPassword());
    Users users = mapper.selectOne(wrapper);
    if (users!=null){
        return users;
    }else {
        throw new RuntimeException("登录失败");
    }
}
```

controller

```java
@Autowired
private UsersService service;
@PostMapping("/login")
public Map<String,Object> login(Users users){
    Map<String,Object> map = new HashMap<>();
    try {
        Users userDB  = service.login(users);
        Map<String,String> payload = new HashMap<>();
        //生成jwt令牌
        payload.put("id",userDB.getId().toString());
        payload.put("username",userDB.getUsername());
        String token = jwtUtil.getToken(payload);
        map.put("state",200);
        map.put("msg","登录成功");
        map.put("token",token);//响应token
    }catch (Exception e){
        map.put("state",408);
        map.put("msg",e.getMessage());
    }
    return map;
}
```

![image-20211021171222525](https://i.loli.net/2021/10/21/DzkEBJua4yf26Kn.png)

## ![image-20211021171253753](https://i.loli.net/2021/10/21/rSTmRIKds6lkF2x.png)

## **配置拦截器**

```java
public class jwtInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头中的令牌
        String token = request.getHeader("token");
        Map<String,Object> map = new HashMap<>();
        try {
            jwtUtil.verify(token);//验证令牌
            return true;//放行请求
        }catch (SignatureVerificationException e){
            e.printStackTrace();
            map.put("msg","无效签名");
        }catch (TokenExpiredException e){
            e.printStackTrace();
            map.put("msg","token过期");
        }catch (AlgorithmMismatchException e){
            e.printStackTrace();
            map.put("msg","算法不一致");
        }catch (Exception e){
            e.printStackTrace();
            map.put("msg","其他异常");
        }
        map.put("state",false);
        //将map专为jsos
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
```

## **config配置interceptor**

```java
@Configuration
public class interceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
         registry.addInterceptor(new jwtInterceptor())
                 .addPathPatterns("/user/test")
                 .excludePathPatterns("/user/login");
    }
}
```

必须header里有token信息才可以访问到

![image-20211021194613486](https://i.loli.net/2021/10/21/zuQ3B7v4ybPKVX1.png)

![image-20211021194651076](https://i.loli.net/2021/10/21/Z7P4yMHFzdKXxTB.png)

