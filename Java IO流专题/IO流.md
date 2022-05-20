# 文件

## 文件流

文件在程序中是以流的形式来操作的

![image-20220508222116850](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205082221884.png)

- 从源文件到内存为输入流
- 从内存到文件为输出流

##  创建文件的三种方式

```java
public class FileCreate {
    public static void main(String[] args) {

    }
    @Test
    //方式一
    public void create01(){
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/1.txt";
        File file = new File(filePath);
        try {
            file.createNewFile();
            System.out.println("文件创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    //方式二 new File(File parent,String child) 根据父目录文件+子路径构建
    public void create02(){
        File parentFile = new File("/Users/austin/Documents/IdeaProject/io-stream");
        String fileName = "2.txt";
        //这个动作相当于只在内存里有一个对象，还没有和硬盘发生任何关系
        File file = new File(parentFile, fileName);
        try {
            //只有执行了这个方法才会创建
            file.createNewFile();
            System.out.println("创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    //方式三 new File(String parentPath,String child)
    public void create() {
        String parentPath = "/Users/austin/Documents/IdeaProject/io-stream/";
        String fileName = "3.txt";
        File file = new File(parentPath, fileName);
        try {
            file.createNewFile();
            System.out.println("创建成功");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

```

![image-20220509090322475](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205090903574.png)

## 获取文件的相关信息

常用的方法

```java
 @Test
    //获取文件的信息
    public void info(){
        File file = new File("/Users/austin/Documents/IdeaProject/io-stream/1.txt");
        //调用相应的方法
        System.out.println("文件名字"+file.getName());
        System.out.println("绝对路径"+file.getAbsolutePath());
        System.out.println("文件父级目录"+file.getParent());
        System.out.println("文件大小"+file.length());
        System.out.println("是否存在？"+file.exists());
        System.out.println("是不是目录？"+file.isDirectory());
        System.out.println("是不是一个文件？"+file.isFile());
    }
```

## 目录的操作

java中目录也被当作文件对待，只不过比较特殊而已

1. 判断文件是否存在存在就删除

```java
@Test
    //判断文件是否存在存在就删除
    public void m1(){
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/1.txt";
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
            System.out.println("删除成功");
        }else {
            System.out.println("该文件不存在 ");
        }
    }
```

2. 判断目录是否存在，存在就删除,java目录也被当作文件

```java
public void m2(){
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test";
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
            System.out.println("删除成功");
        }else {
            System.out.println("该目录不存在 ");
        }
    }
```

3. 判断目录是否存在，如果存在提示存在，如果不存在就把它创建起来

```java
 public void m3(){
        String directoryPath = "/Users/austin/Documents/IdeaProject/io-stream/test";
        File file = new File(directoryPath);
        if (file.exists()){
            System.out.println("该目录存在");
        }else {
            //mkdirs 创建多级目录
            //mkdir 只创建一级目录
            if (file.mkdir()) {
                System.out.println("创建成功");
            }else {
                System.out.println("创建失败");
            }
        }
    }
```

## IO流原理及流的分类

- IO 是input/output的缩写，是非常实用的技术，用于处理数据的传输，如读写文件，网络通讯
- java中数据的输入输出都是按照stream流的方式进行
- java io包下提供各种数据的输入输出流，用于获取不同的数据，并通过方法输入或输出数据

### **Java IO流原理**

- 输入input：读取外部数据到内存中
- 输出output：将内存中的数据输出到磁盘中

![image-20220509093438999](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205090934072.png)

### **流的分类**

- 按照操作数据单位不同分为：字节流(8bit) ，字符流（按字符）

  - 对于二进制文件用字节流操作我们可以保证文件无损。
  - 字符流操作文本文件 

- 按照流的方向不同分为：输入流、输出流

- 按照流的角色不同分为：节点流、处理流/包装流

  | 抽象基类 | 字节流      | 字符流 |
  | -------- | ----------- | ------ |
  | 输入流   | InputStream | Reader |
  | 输出流   | OutputStrem | Writer |

  这四个类都是抽象类，java io流共设计40多个子类都是上面4个派生出来的

  ![image-20220509094840083](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205090948124.png)

​		由这四个类派生出来的子类名都是以父类名作为子类的后缀

![image-20220509095110132](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205090951161.png)

## IO流常用的类

### InputStream 字节输入流

**InputStram输入流常用的子类**

1. FileInputStream 文件输入流
2. BufferedInputStream 缓冲字节输入流
3. ObjectInputStream 对象字节输入流

---

#### **FileInputStream**

![image-20220509100055015](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205091000088.png)

- read() 读取单个字节

```java
 //单个字节读取效率比较低
    @Test
    public void readFile01() {
        FileInputStream fileInputStream = null;
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/hello.txt";
        int readData = 0;
        try {
            //用于读取文件
            fileInputStream = new FileInputStream(filePath);
            //从该输入流读取一个字节的数据，若没有输入可用，此方法将被阻止
            //如果返回-1，读取完毕
            while ((readData = fileInputStream.read()) != -1) {
                System.out.print((char) readData);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
```

- read(byte []) 读取byte.length 字节的内容

```java
//使用read(byte [])读取文件提高效率
    @Test
    public void readFile02() {
        FileInputStream fileInputStream = null;
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/hello.txt";
        //字节数组 一次读8个字节
        byte[] buf = new byte[8];
        int readLen = 0;
        try {
            //用于读取文件
            fileInputStream = new FileInputStream(filePath);
            //从该输入流最多读取byte.length 字节的数据到字节数组
            //如果返回-1，读取完毕
            //如果读取正常返回实际读取的字节数
            while ((readLen = fileInputStream.read(buf)) != -1) {
                System.out.print(new String(buf, 0, readLen));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
```

---

#### FileOutputStream

![image-20220509104556857](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205091045935.png)

- 在一个文件中写入hynzdc

```java
@Test
    //将数据写入文件中，如果文件不存在则创建文件
    public void writeFile(){
        FileOutputStream fileOutputStream = null;
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/hello.txt";

        try {
            //1.new FileOutputStream(filePath)写入内容会覆盖原来的内容
            //2.new FileOutputStream(filePath,true) 写入内容会追加在末尾
            //fileOutputStream = new FileOutputStream(filePath);
            fileOutputStream = new FileOutputStream(filePath,true);
            //写入一个字节
            //''代码的是char  "" 代表的是string
            fileOutputStream.write('a');//char会自动转换成int
            //写一个字符串
            String str = "hello hyn";
            byte[] bytes = str.getBytes();
            fileOutputStream.write(bytes);

            fileOutputStream.write(bytes,0,3);//从第0开始写3个进去
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
```

- 文件的拷贝

  在完成代码时，是读取部分就写入指定的位置

  ```java
  @Test
      //完成文件拷贝
      //1.创建文件的输入流，将文件读入到程序
      //2.创建文件输出流，将文件写入到指定的文件
      public void copyFile() {
          FileInputStream fileInputStream = null;
          FileOutputStream fileOutputStream = null;
          String inputPath = "/Users/austin/Documents/IdeaProject/io-stream/hello.txt";
          String outputPath = "/Users/austin/Documents/IdeaProject/io-stream/test/hello.txt";
          byte[] buf = new byte[1024];
          int readLen = 0;
          try {
              fileInputStream = new FileInputStream(inputPath);
              fileOutputStream = new FileOutputStream(outputPath);
              while ((readLen = fileInputStream.read(buf)) != -1) {
                  //读了就写，一边读一边写
                  fileOutputStream.write(buf, 0, readLen);//一定要使用这个方法
              }
              System.out.println("拷贝成功");
          } catch (IOException e) {
              e.printStackTrace();
          } finally {
              try {
                  if (fileOutputStream != null) {
                      fileOutputStream.close();
                  }
                  if (fileInputStream != null) {
                      fileInputStream.close();
                  }
  
              } catch (IOException e) {
                  e.printStackTrace();
              }
          }
      }
  ```

---

### 字符流

#### FileReader

- read()读取单个字符，返回该字符，读到末尾返回-1
- read(char[]) 批量读取多个字符到数组，返回读取到的字符数，文件末尾返回-1
- new string(char[]) 将char[] 转换为string
- new string(char[],off,len)将指定的部分转为string

**读取单个字符**

```java
@Test
    //一个一个读取
    public void reader(){
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/love.txt";
        FileReader fileReader = null;
        int data = 0;
        try {
            fileReader = new FileReader(filePath);
            //循环读
            while ((data = fileReader.read())!=-1){
                System.out.print((char)data);
            }
            System.out.println("读取完毕");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
```

**读取多个字符，覆盖到char[]中去**

```java
@Test
    //使用字符数组读取文件
    public void reader2(){
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/love.txt";
        int readLen = 0;
        FileReader fileReader = null;
        char[] bufs = new char[10];
        try {
            fileReader = new FileReader(filePath);
            while ((readLen = fileReader.read(bufs))!=-1){
                System.out.print(new String(bufs,0,readLen));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
```

- 注意char 和 int可以互相转换

#### FileWriter

- new  FileWriter(File/String) 
- new File Writer(File,true) 追加模式
- Writer(int) 写入单个字符
- writer(char[]) 写入指定数组
- Writer(char[],off,len) 写入指定数组的指定部分
- Writer(string) 写入整个字符串
- Writer(string,off,len)。写入字符串的指定部分
- tocharArray  将string转换为char数组 

```java
 @Test
    public void write01(){
        String writePath = "/Users/austin/Documents/IdeaProject/io-stream/test/love.txt";
        //创建fileWriter对象
        int num = '2';
        char[] chars = {'l','o','v','y'};
        System.out.println(num-'0');
        FileWriter fileWriter = null;
        try {
            fileWriter  = new FileWriter(writePath,true);
            //1.写入单个字符
            fileWriter.write('I');
            //2.写入char数组
            fileWriter.write(chars);
            //3.写入指定的数组
            fileWriter.write("郝亚宁真的帅".toCharArray(),0,3);
            //4.写入字符串
            fileWriter.write("你好北京");
            //5.写入字符串的指定部分
            fileWriter.write("你好上海",0,1);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                fileWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

```

- 注意一定要关闭文件流或者刷新文件流否则内容将写不进去

## 节点流和处理流

### 节点流

节点流可以从一个特定的数据源读取数据

![image-20220509161723017](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205091617148.png)

这种灵活性不够好，效率不够强大，所以提出了处理流

### 处理流（修饰器模式）

处理流也叫包装流，是连接已经存在的流（节点流或处理流）为程序提供更强大的读写功能，比如bufferedReader，bufferedWriter

BufferedReader 里面封装了Reader 故只要是Reader的子类，BufferedReader都可以进行操作

![image-20220509162411833](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205091624888.png)

用理buffereWriter里面也有Writer

![image-20220509162607936](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205091626991.png)

所以只要是Writer的子类都可以被BufferedWriter接管

![](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205091631810.png)

### 节点流和处理流的区别和联系

1. 节点流底层流/低级流、直接跟数据源相接
2. 处理流包装了节点流，既可以消除不同节点流的实现差异，也可以提供更方便的方法来完成输入输出流
3. 处理流对节点流进行了包装，使用了修饰器模式，不会直接与数据源相连。

### 处理流的功能主要体现在以下两个方面

1. 性能的提高：主要以增加缓存的方式来提高输入输出的效率
2. 操作的便捷：处理流提供了一系列便捷的方法，来一次输入输出大批量的数据，使用更加灵活方便

### BufferedReader

**使用buffereReader读取文本文件，并显示在控制台**

```java
public class BufferedReader_ {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/love.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
        //读取
        //按行读取  当返回为空的话读取完毕
        String line = null;
        while (( line = bufferedReader.readLine())!=null){
            System.out.println(line);
        }

        //关闭流只需要关闭bufferedReader就可以了，底层会关闭节点流
        bufferedReader.close();
    }
}
```

### BufferedWriter

```java
public class BufferedWriter_ {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test/fuck.txt";
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath,true));
        bufferedWriter.write("我爱你");
        bufferedWriter.newLine();
        bufferedWriter.write("你看起来真的很厉害");
        bufferedWriter.close();
    }
}
```

**综合BufferedReader和BufferedWrite写一个copy**

```java
public class BuferedCopy {
    public static void main(String[] args) throws Exception {
        //BufferedReader 和BufferedWriter 是操作字符的
        //不要操作二进制文件，不如视频，音频
        String  srcFile = "/Users/austin/Documents/IdeaProject/io-stream/2.txt";
        String  tarFile = "/Users/austin/Documents/IdeaProject/io-stream/test/2.txt";
        BufferedReader bufferedReader = new BufferedReader(new FileReader(srcFile));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(tarFile));
        String line;
        while ((line = bufferedReader.readLine())!=null){
            //没读取一行就写入
            bufferedWriter.write(line);
            //插入一个换行
            bufferedWriter.newLine();
        }
        if (bufferedReader!=null){
            bufferedReader.close();
        }
        if (bufferedWriter!=null){
            bufferedWriter.close();
        }
    }
}
```

### BufferedInputStream、BufferedOutputstream

**复制视频文件**

```java
public class BufferedCopy {
    public static void main(String[] args) throws Exception {
        String srcPath = "/Users/austin/Documents/IdeaProject/io-stream/lalal.mp4";
        String tarPath = "/Users/austin/Documents/IdeaProject/io-stream/test/dudu.mp4";
        BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(srcPath));
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(tarPath));
        byte[] bytes = new byte[1024];
        int readLen = 0;
        while ((readLen = bufferedInputStream.read(bytes)) != -1) {
            bufferedOutputStream.write(bytes, 0, readLen);
        }
        System.out.println("复制完成");
        if (bufferedInputStream != null) {
            bufferedInputStream.close();
        }
        if (bufferedOutputStream != null) {
            bufferedOutputStream.close();
        }
    }
}
```

## 对象流

### 需求

- 将int num = 100 这个int 数据保存到文件中，注意不是数字100，而是int 100 ，并且能够从文件中直接恢复int 100
- 将Dog dog = new Dog{"小黄",3} 保存到文件中，并且能够恢复
- 上面的要求，就是要将基本的数据类型或者对象进行序列化或者反序列化

### 序列化和反序列化

1. 序列化就是在保存数据时，保存数据的值和数据的类型
2. 反序列化就是在恢复数据时，恢复数据的值和数据的类型
3. 需要让某个对象支持序列化机制，则必须让其类是可序列化的，为了让类可序列化，该类必须实现如下两个接口之一
   - Serializable. 这是一个标记接口，没有方法
   - Extrnalizable  需要实现方法

![image-20220510090515659](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205100905822.png)

### ObjectInputStream

**处理流**

![image-20220510091140826](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205100911896.png)

### ObjectOutputStream

![image-20220510091311207](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205100913283.png)

### 应用案例

1. 使用ObjectOutputStream序列化基本数据类型一个Dog对象(name,age)并保存到data.dat文件中

```java
public class ObjectOutputStream_ {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test/data.dat";
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
        //序列化数据
        oos.write(100); //int 会自动装箱 成Integer 而Integer实现了Serializable
        oos.writeBoolean(true); //装箱成Boolean类 也实现了Serializable
        oos.writeChar('a'); //char -> Character 实现了Serializable
        oos.writeDouble(20.34); //double -> Double 实现了Serializable
        oos.writeUTF("郝亚宁"); //String 也实现了序列化接口
        oos.writeObject(new Dog("旺财",22));
        oos.close();
        System.out.println("数据保存完毕");
    }
}
//实现序列化
class Dog implements Serializable {
    private String name;
    private int age;
  
    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }
}

```

![image-20220510093159736](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205100931791.png)

2. 使用ObjectInputStream读取data.dat文件，恢复数据

  ```java
  /**
   * 反序列化
   */
  public class ObjectInputStream_ {
      public static void main(String[] args) throws Exception  {
          String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test/data.dat";
          ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
          //读取的顺序一定要和存放（序列化）的顺序的一致
          System.out.println(ois.readInt());
          System.out.println(ois.readBoolean());
          System.out.println(ois.readChar());
          System.out.println(ois.readDouble());
          System.out.println(ois.readUTF());
          Object dog = ois.readObject();
          System.out.println("dog类型"+dog.getClass());
          //特别重要的细节
          //如果我们需要用到Dog的方法，需要向下转型
          //需要我们要将Dog类定义，
          Dog dog1 = (Dog)dog;
          System.out.println(dog1.getName());
          System.out.println(dog);//底层object ->dog
          ois.close();
      }
  }
  ```

### 注意事项和细节说明

- 读写要求一致

- 要求实现序列化和反序列化对象实现Serializable

- 序列化建议添加SerialVersionUid,为了提高版本的兼容性

  ```java
  //serialVersionUID序列化版本，提高兼容性
      private static final long serialVersionUID = 1L;
  ```

- 序列化对象时，默认将里面所有的属性进行序列化，但除了static和transient修饰的成员

  - 在持久化对象时，对于⼀些特殊的数据成员（如⽤户的密码，银⾏卡号等），我们不想⽤序列化机制来保存它。为了在⼀个特定对象的⼀个成员变量上关闭序列化，可以在这个成员变量前加上关键字transient。

- 序列化对象时，要求里面的属性类型也要实现序列化接口

- 序列化具备可继承性，也就是如果某类已经实现了序列化，则他的所有子类默认实现了序列化

---

## 转换流

下面说一个例子，在我们用BufferedReader读取文本文件时如果文本文件是utf-8那么可以读取正常，但是如果以其他格式编码的文件就会出现如下情况

![image-20220510105828963](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205101058091.png)

![image-20220510105836840](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205101058914.png)

所以我们可以用字节流读取文件，再指定编码格式，再转换成字符流

![image-20220510110020680](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205101100751.png)

### InputStreamReader

![image-20220510110421913](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205101104983.png)

### OutputStreamWriter

![image-20220510111220695](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205101112747.png)

- InputStreamReader：Reader 的子类，可以将InputStream（字节流）包装成 Reader字符流
- OutputStreamWriter: Writer 的子类 ，可以讲outputStream 包装成 Writer 字符流

- 处理文本数据，如果使用字符流效率更高，并且可以有效的解决中文问题，所以建议将字节流转换成字符流
- 可以在使用时指定编码格式

### FileInputStream -> InputStreamReader

将字节流转换成字符流读取到内存里

```java
/**
 * 将字节流转换成字符流
 * FileInputStream -> InputStreamReader
 */
public class InputStreamReader_ {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test/love.txt";
        //1.把FileInputStream转为inputStreamReader
        //2.指定编码gbk
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filePath),"UTF-16");
        //3.把inputStreamReader传入BufferedReader
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        //4.读取
        String s = bufferedReader.readLine();
        System.out.println(s);
        //关闭外层流
        inputStreamReader.close();
    }
}
```

### FileOutputStream-> OutputStramWriter

将字节流转换成字符流输出到文件中去

```java
/**
 * FileOutputStream -> OutputStreamWriter
 */
public class OutputStreamWriter_ {
    public static void main(String[] args) throws Exception {
        String filePath = "/Users/austin/Documents/IdeaProject/io-stream/test/hyn.txt";
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),"utf-8"));
        bufferedWriter.write("郝亚宁真的帅");
        bufferedWriter.close();
    }
}
```

---

## 打印流

**打印流： PrintStream 和PrintWriter**

只有输出流没有输入流

### PrintStream

![image-20220510154744024](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205101547158.png)

```java
//演示字节打印流
public class PrintStream_ {
    public static void main(String[] args) throws IOException {
        PrintStream out = System.out;
        //默认情况下PrintStream打印的位置是显示器
        out.print("我爱你");
        //因为print底层是write所以我们可以直接用write答应
        out.write("我真帅".getBytes());
        out.close();
        //我们可以修改打印流的输出位置
        //修改到
        System.setOut(new PrintStream("/Users/austin/Documents/IdeaProject/io-stream/test/hyn.txt"));
        System.out.println("我是靓仔");
    }
}
```



### PrintWriter

![image-20220510154920489](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205101549585.png)

```java
public class PrintWriter_ {
    public static void main(String[] args) throws IOException {
        //PrintWriter printWriter = new PrintWriter(System.out);
        PrintWriter printWriter = new PrintWriter(new FileWriter("/Users/austin/Documents/IdeaProject/io-stream/test/hyn.txt"));
        printWriter.println("北京你好");
        printWriter.close();
    }
}
```

## Properties类

Properties父类是

Properties是一个专门读取配置文件的集合类

配置文件格式

键=值

![image-20220520160440019](https://pic-es.oss-cn-shanghai.aliyuncs.com/study-pic/202205201604060.png)

### Properties常用的方法

- load() 加载配置文件的键值对到Properties对象
- list() 将数据显示到指定设备
- getProperty(key) 根据键获取值
- setProperty(key,value) 设置键值到Properties对象
- store: 将Properties中的键值对存储到配置文件去，如果有中文会uicode编码

### 看一个需求

![image-20220510161256348](https://pic-es.oss-cn-shanghai.aliyuncs.com/harehouse-master/img/202205101612418.png)

**用传统的方法解决**

```java
public class Properties_ {
    public static void main(String[] args) throws Exception {
        //用传统的方法读取mysql.properties
        BufferedReader bufferedReader = new BufferedReader(new FileReader("/Users/austin/Documents/IdeaProject/io-stream/src/mysql.properties"));
        String line = "";
        while ((line = bufferedReader.readLine())!=null){
            String[] split = line.split("=");
            System.out.println(split[0]+"值是"+split[1]);
        }
        //这种方式也可以但是如果我要只获取到ip就比较麻烦了
        bufferedReader.close();
    }
}
```

**用Properties类来解决**

```java
public class Properties02_ {
    public static void main(String[] args) throws Exception {
        //使用Properties这个类来读取mysql.properties
        //1,创建对象
        Properties properties = new Properties();
        //2,加载指定配置文件
        properties.load(new FileReader("/Users/austin/Documents/IdeaProject/io-stream/src/mysql.properties"));
        //3,把k-v显示控制台
        properties.list(System.out);
        //4,根据key获取对应的值
        System.out.println(properties.get("user"));
        System.out.println(properties.get("pwd"));
    }
}
```

### 应用案例

1. 使用Properties完成对配置文件的读取
2. 使用Properties完成键值对的添加
3. 使用Properties修改键值对，并保存到配置文件中

```java
public class Properties03_ {
    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();

        properties.setProperty("name","hyn");
        properties.setProperty("age","23");
        properties.setProperty("hha","郝亚宁");
        properties.store(new FileWriter("/Users/austin/Documents/IdeaProject/io-stream/src/mysql2.properties"),"你是真的帅");
        System.out.println("保存成功");
    }
}
```

