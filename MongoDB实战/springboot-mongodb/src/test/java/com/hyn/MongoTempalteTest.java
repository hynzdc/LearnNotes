package com.hyn;

import com.hyn.entity.User;
import com.mongodb.client.result.UpdateResult;
import org.ietf.jgss.MessageProp;
import org.ietf.jgss.Oid;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.UnknownServiceException;
import java.util.*;

/**
 * @Auther: hyn
 * @Date: 2022/5/31 - 05 - 09:29
 * @Description: com.hyn
 * @Version: 1.0
 */
@SpringBootTest(classes = SpringbootMongodbApplication.class)
@RunWith(SpringRunner.class)
public class MongoTempalteTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    //创建一个新的集合
    @Test
    public void createCollection() {
        boolean exists = mongoTemplate.collectionExists("products");
        if (!exists) {
            mongoTemplate.createCollection("products");
        }
    }

    //删除集合
    @Test
    public void testDropTest() {
        mongoTemplate.dropCollection("products");
    }

    //文档操作添加
    @Test
    public void testAdd() {
        User user1 = new User(7, "小花朵", 2000.0, new Date());
        User user2 = new User(9, "小乌龟", 3000.3, new Date());
        //mongoTemplate.save(user);//save方法是更新数据
        List<User> list = new ArrayList<>();
        list.add(user1);
        list.add(user2);
        mongoTemplate.insert(list, User.class);//批量的数据，类型
    }

    //文档的查询
    @Test
    public void find() {
        //查询所有
        List<User> all = mongoTemplate.findAll(User.class);
//        all.forEach((e) -> {
//            System.out.println(e);
//        });
//        List<User> users = mongoTemplate.findAll(User.class, "users");
//        users.forEach(e->{
//            System.out.println(e);
//        });
        //2.按照id查询
        System.out.println(mongoTemplate.findById(1, User.class));
        //3.条件查询
        List<User> list = mongoTemplate.find(new Query(), User.class);
        list.forEach(e->{
            System.out.println(e);
        });
       // 等值查询
        Query query = Query.query(Criteria.where("username").is("小猪头"));
        List<User> list1 = mongoTemplate.find(query, User.class);
        list1.forEach(System.out::println);
        System.out.println("---------------------------------");
        //>= <=
        List<User> list2 = mongoTemplate.find(Query.query(Criteria.where("salary").lte(2000)), User.class);
        list2.forEach(System.out::println);

        //and查询
        System.out.println("-=================================");
        List<User> list3 = mongoTemplate.find(Query.query(Criteria.where("username").is("小猪头").and("salary").lte(2000)), User.class);
        list3.forEach(System.out::println);
        //or查询
        System.out.println("----------------------------------");
        Criteria criteria = new Criteria();
        criteria.orOperator(
                Criteria.where("username").is("小猪头"),
                Criteria.where("username").is("小世界"),
                Criteria.where("salary").lte(2000)
        );
        List<User> list4 = mongoTemplate.find(Query.query(criteria), User.class);
        list4.forEach(System.out::println);
        //and  or
        System.out.println("--=-=-=-=-=-=-=-=-=-=-=-=-");
        Criteria criteria1  = new Criteria();
        criteria1.where("salary").lte(2000)
                .orOperator(
                        Criteria.where("username").is("小猪头")
                );
        List<User> list5 = mongoTemplate.find(Query.query(criteria1), User.class);
        list5.forEach(System.out::println);
        //basicQuery
        System.out.println(".................................");
        BasicQuery basicQuery = new BasicQuery("{$or:[{name:/小/},{salary:{$lte:3000}}]}","{name:1,salary:1}");
        List<User> list6 = mongoTemplate.find(basicQuery, User.class);
        list6.forEach(System.out::println);

    }
    //更新
    @Test
    public void update(){
        Update update = new Update();
        update.setOnInsert("_id",10);
        update.set("salary",5647);
       // mongoTemplate.updateFirst(Query.query(Criteria.where("username").is("小花朵")), update,User.class);
        //多条更新
       // mongoTemplate.updateMulti(Query.query(Criteria.where("salary").is(2000)),new Update().set("salary",4000),User.class);
        //插入更新
        UpdateResult result = mongoTemplate.upsert(Query.query(Criteria.where("salary").is(2888)), update, User.class);
        System.out.println(result.getModifiedCount());
        System.out.println(result.getMatchedCount());
        System.out.println(result.getUpsertedId());
    }

    //文档的删除
    @Test
    public void del(){
        mongoTemplate.remove(Query.query(Criteria.where("username").is("小乌龟")),User.class);

    }
}
