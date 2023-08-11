package java8.features.Interface;

@FunctionalInterface
public interface Interface2 {

    void method2();

    default void log(String str) {
        System.out.println("I2 logging::" + str);
    }
    //Java8 Interface cannot override java.lang.Objects methods
    //clone(),equals(Object),finalize(),hashCode(),toString()
	
	/*default void hashCode(){
	}
	
	default void equals(Object o){
	}
	
	default void  toString(){
	}*/

    default void newEquals(Object o) {

    }
}