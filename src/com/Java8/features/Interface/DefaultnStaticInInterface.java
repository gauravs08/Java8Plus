package com.Java8.features.Interface;


/*default and static methods in Interfaces
 * */


public class DefaultnStaticInInterface implements Interface1, Interface2 {

	@Override
	public void method2() {
	}

	@Override
	public void method1(String str) {
	}

	
	//MyClass won't compile without having it's own log() implementation 
	//Since both interface has log method as default compiler would not know which method to call
	//so need to overrride this method if only one interface is implemented then no need to override the log
	@Override
	public void log(String str) {
		System.out.println("MyClass logging::"+str);
		Interface1.super.log(str);
		Interface2.super.log(str);
		Interface1.print(str);
	}

	
	public static void main (String args[]){
	DefaultnStaticInInterface obj = new DefaultnStaticInInterface();
	obj.log("Hello World");
	
	
	Interface1 i1 = (s) -> System.out.println(s);
	i1.method1("helloworld");
	}
	
	
}