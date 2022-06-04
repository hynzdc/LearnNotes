import java.util.*;
import java.lang.reflect.*;
public class ClassLoading{
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("请输入key");
		String key = sc.next();
		switch(key){
			case: "1"
				Dog dog  = new Dog();
			case: "2"
				Class cls = Class.forName("com.hyn.Dog");	
				Object o = cls.newInstance();
				Method m = o.getMethod("hi");
				m.invoke(o);
		}
	}
}