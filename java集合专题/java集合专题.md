# java集合专题
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205161021932.png)



## 集合的理解与好处
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205161022276.png)

## 集合的框架体系
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205161022861.png)



![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205161022492.png)

集合主要分为两组：单列集合（存放单个元素）和双列集合

Collection有两个重要的子接口List Set 他们的实现子类都是单列集合

Map接口实现的子类是双列集合，存放k-v

上面的两张图要记下来

![image-20220516102258753](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201637888.png)

* 任何接口的实现类都带有iterator方法

## ArrayList的底层结构和原码分析 
![image-20220516102351804](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201637517.png)

### ArraryList的扩容机制
**分析使用无参构造的ArrayList原码**

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201637210.png)

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201637446.png)

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201637720.png)

**分析使用有参构造的ArrayList原码**

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201638624.png)

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201638650.png)

## Vector的底层结构和原码剖析
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201638549.png)

###  vector与ArrayList的比较
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201638331.png)

**增加元素**

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201638312.png)

**确定是否需要扩容**

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201638541.png)

扩容代码

如果需要的大小不够了就扩容两倍

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201638028.png)

## LinkedList的底层结构和原理
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201638932.png)

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201638173.png)

### LinkedList和ArrayList的比较
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201638109.png)

## Set接口和常用方法
* 无序的（添加和取出的顺序不一样），没有索引
* 不允许重复元素，所以最多包含一个null
* jdk中set接口的实现类有

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201638165.png)

### HashSet
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201638035.png)

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201639476.png)

* hashSet 不保证存放元素的顺序和取出元素的顺序一致

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201639085.png)

#### 分析底层
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201639652.png)

```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0) //table 就是hashmap的一个数组，类型为node
        //if语句表示如果当前table是空的，或者长度为0，那么第一次扩容到16个空间
            n = (tab = resize()).length;
        //根据key得到hash去计算key应该存放到table的哪个索引位置
        //把这个位置的对象赋给p
        //判断p是否为空
        //1.如果为空，表示还没有存放元素，就创建一个node（key=“java”,value）
        //就放在该位置
        if ((p = tab[i = (n - 1) & hash]) == null)
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
            if (p.hash == hash && //首先hash值相同并且里面的内容也相同
                ((k = p.key) == key || (key != null && key.equals(k))))
                //如果当前索引位置的第一个元素和准备添加的key的hash值一样
                //并且满足以上两个条件之一
                //1.加入的key和指向p的结点是同一个对象
                //2.p指向的结点equals（）加入的key比较后相同
                e = p;
                //判断p是不是一个红黑树
            else if (p instanceof TreeNode)
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value = PRESENt);
            else {
                //依次和该该链表的每一个元素进行比较，都不相同则链到最后，注意加入到最后要立即判断是否达到8个结点（就调用红黑树，对当前链表进行树化），在转成红黑树时还进行一个判断 判断table.length是否大于64 小于64就要先扩容
                //如果有相同的情况则，就break
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash &&
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }

```
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201639438.png)

#### **扩容机制**
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201639199.png)

因子0.75是结合了空桶和存储的数据相关的 因子大了 则空桶太多 因子小了则存储大量数据时可能会卡主

还有就是根据底层的原码只要往这个table里面扔一个node进去，就算size++了，所以不是说要占用不同的链表，只要进去了就算，当size达到了12就会第一次扩容

* 注意这里的临界值是超过12，和8，即是第13个和第9个
   * 如果一直在一条链表上，且超过八个，它会尽快的扩容来转换成红黑树

#### 课堂练习
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201639470.png)

![image](images/gu6ALhaZwVdCQ-0eQ6U03fcmStSb3ZH3IFu5xReksiE.png)

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201639787.png)

### LinkedHashSet
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201639566.png)

![image](images/KREkdyb_9TuaF93cN8DJ3TF4mY23OE7demt9VT69NAw.png)

* LinkedHashSet 加入元素和取出元素的顺序是一致的
* LinkedHashSet底层维护的是LinkedHashMap（HashMap的子类）
* LInkedHashSet底层结构是 数组加双向链表
* 添加第一次时数组table扩容到16，存放的类型LinkedHashMa\$Entry
* 数组是HashMap$Node[] ,存放的数据元素是LinkedHashMap$Entry,可以把子类放到父类类型的数组中去的现象叫数组多态

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201639264.png)
## Map接口
### Map接口特点
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201639039.png)

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201639702.png)

### Map核心内容
* k-v 是存储在HashMap\$Node newNode(hash,key,value,null) 
* hashMapNode 继承了 hashmapEntry 静态内部类。多态的思想
* k-v 为了方便程序员的遍历，还会创建EntrySet集合，该集合存放元素的类型Entry，而一个entry对象包含了key value
* 对象就是EntrySet<Entry<K,V>>

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201640084.png)

* entrySet中定义的类型是Map.entry 但实际上存放的还是HashMap\$Node,这是因为：

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201640815.png)

* 当把HashMap\$Node 存放到EntrySet就方便了我们的遍历，因为Map.Entry提供了两个重要的方法

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201640753.png)



![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201640290.png)

* 注意

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201640730.png)

map里面的table表存放的数据，放在entrySet集合中，然后entrySet中引用指向了table中的数据 ，

### Map真正的结构
map里面有一个table表，是以数组链表加红黑树（HashMapNode）进行管理的,但是为了方便管理它在底层做了一个控制，把node封装成一个entry，再把多个entry放到entrySet集合中去,如下

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201640045.png)

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201640933.png)

### Map接口的常用方法
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201640354.png)

### Map接口遍历方法
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201640669.png)

```java
//1.第一种先取出所有的key
        Set set1 = map.keySet();
        for (Object o : set1) {
            System.out.println(map.get(o));
        }


        //2.迭代器
        Iterator iterator = set1.iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
            System.out.println(map.get(key));
        }
        //第二种把所有的value取出
        Collection values = map.values();
        Iterator iterator1 = values.iterator();
        while (iterator1.hasNext()) {
            Object next = iterator1.next();
            System.out.println(next);
        }
        for (Object value : values) {
            System.out.println(value);
        }

        //第三种，通过entrySet获取
        Set entrySet = map.entrySet();
        for (Object entry : entrySet) {//取出来就是entry
            //将entry转成map.entry
            java.util.Map.Entry m = (java.util.Map.Entry) entry;
            System.out.println(m.getKey() + "-" + m.getValue());
        }
        Iterator iterator2 = entrySet.iterator();
        while (iterator2.hasNext()) {
            Object next = iterator2.next();
            System.out.println(next.getClass());//hashMapNode 实现了 HashMapEntry
            //向下转型
            java.util.Map.Entry entry = (java.util.Map.Entry) next;
        }
    }
```
### HashMap小结
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201640784.png)

### HashMap底层机制即原码剖析
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201640026.png)

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201640575.png)

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201641072.png)

1. 执行构造器 newhashMap 初始化加载因子 loadfafactor = 0.75 , HashMapNode\[\] table = null
2. 执行put调用hash方法



```java
final V putVal(int hash, K key, V value, boolean onlyIfAbsent,
                   boolean evict) {
        Node<K,V>[] tab; Node<K,V> p; int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length; //对我们的table表进行扩容
        if ((p = tab[i = (n - 1) & hash]) == null) //得到索引位置，如果该索引位置为空，把加入的kv创建成一个Node加入进去就可以了
            tab[i] = newNode(hash, key, value, null);
        else {
            Node<K,V> e; K k;
            if (p.hash == hash && //如果table索引位置的hash值和新的key的hash值相同，并且满足存在呢（Table现有的这个结点的key和准备添加的key是同一个对象 或者 equals返回真
                ((k = p.key) == key || (key != null && key.equals(k))))
                e = p;
            else if (p instanceof TreeNode)//走树的路线
                e = ((TreeNode<K,V>)p).putTreeVal(this, tab, hash, key, value);
            else {
                for (int binCount = 0; ; ++binCount) {。//如果后面是一个链表，那么一个个比较如果有相同的直接break；
                    if ((e = p.next) == null) {//如果整个链表没有和他相同的就加在它的最后面
                        p.next = newNode(hash, key, value, null);
                        //加入后判断判断当前链表的个数，是否已经达到8个
                        //就进入红黑树的转化工作
                        if (binCount >= TREEIFY_THRESHOLD - 1) // -1 for 1st
                            treeifyBin(tab, hash);
                        break;
                    }
                    if (e.hash == hash && //如果循环过程中发现有相同的就break
                        ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) { // existing mapping for key
                V oldValue = e.value;//把key相同的内容进行一个替换
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                afterNodeAccess(e);
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold)
            resize();
        afterNodeInsertion(evict);
        return null;
    }
 
```
## HashTable的基本介绍【：
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201641158.png)

### HashTable的底层
1. 底层有数组HashTable\$Entry\[\] 初始化大小为11
2. 临界值threshold = 11\*0.75 = 8
3. 扩容 :按照自己的扩容机制
4. 执行方法addEntry(hash,key,value,index)
5. 扩容机制两倍加1

![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201641555.png)

## Map接口实现类Properties
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201641339.png)

## 总结
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201641757.png)

## TreeSet
```java
//1.当我们使用无参构造的时候，创建TreeSet仍然是无序的
        //2.希望按照字符串大小来排序，可以用他的构造器传入一个比较器(匿名内部类)

        TreeSet treeSet = new TreeSet(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                //下面调用string的compareTo进行字符串大小的比较
                return ((String) o1).compareTo((String) o2);
            }
        });
        treeSet.add("jack");
        treeSet.add("tom");
        treeSet.add("mike");
        treeSet.add("xnn");
        System.out.println(treeSet);
    }
```
## TreeMap 
![image](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201641595.png)

