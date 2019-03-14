package com.Java8.features.Interface;
@FunctionalInterface
public interface Interface1 {

	void method1(String str);
	
	//Since it is @FunctionalInterface I1 cannot have more then one abstract method2, 
	//FunctionalInterface means only one abstract non-Object method
	// eg:  java.lang.Runnable only has run() method 
	//void method2(String str);
	
	default void log(String str){
		System.out.println("I1 logging::"+str);
	}
	
	static void print(String str){
		System.out.println("I1 Static Printing "+str);
	}
	
	//trying to override Object method gives compile time error as
	//"A default method cannot override a method from java.lang.Object"
	
//	default String toString(){
//		return "i1";
//	}
	static void print2(String str){
		System.out.println("I1 Static Printing "+str);
	}

	//void method2(); //remove this comment and check @FunctionalInterface warning on Interface1 
					  //it should have only one abstract method fro calling it a functional interface
}
