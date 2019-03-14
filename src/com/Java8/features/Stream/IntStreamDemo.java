package com.Java8.features.Stream;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class IntStreamDemo {

	//Traditional approach
	private static boolean isPrimeTraditional(int number) {		
		if(number < 2) return false;
		for(int i=2; i<number; i++){
			if(number % i == 0) return false;
		}
		return true;
	}
	//Declarative approach
	private static boolean isPrime(int number) {	
		IntPredicate isDivisible = index -> number % index == 0;
		
		return number > 1
				&& IntStream.range(2, number).noneMatch(isDivisible);
	}
	public static void main(String[] args) {

		IntStreamDemo obj = new IntStreamDemo();
		
		System.out.println("OLD:: "+obj.isPrimeTraditional(37));
		System.out.println("NEW JAVA8::"+obj.isPrime(8));

	}

}
