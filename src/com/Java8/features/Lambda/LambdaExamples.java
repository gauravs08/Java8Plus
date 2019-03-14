package com.Java8.features.Lambda;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class LambdaExamples {
	//Traditional approach
	private static boolean isPrimeOldway(int number) {		
		if(number < 2) return false;
		for(int i=2; i<number; i++){
			if(number % i == 0) return false;
		}
		return true;
	}
	
	//Declarative approach
	private static boolean isPrime1way(int number) {		
		
		return number > 1
				&& IntStream.range(2, number).noneMatch(
						index -> number % index == 0);
	}
	
	private static boolean isPrime2way(int number) {
		IntPredicate isDivisible = index -> number % index == 0;
		
		return number > 1
				&& IntStream.range(2, number).noneMatch(
						isDivisible);
	}
	
	
	
	public static void main(String args[]){
		
		LambdaExamples c = new LambdaExamples();
		
		long startTime = System.nanoTime();
			System.out.println(c.isPrimeOldway(677));
		System.out.println("Execution time :"+ (System.nanoTime() - startTime));
		
		startTime = System.nanoTime();
			System.out.println(c.isPrime1way(677));
			System.out.println("Execution time :"+ (System.nanoTime() - startTime));
		
		startTime = System.nanoTime();
			System.out.println(c.isPrime2way(677));
			System.out.println("Execution time :"+ (System.nanoTime() - startTime));
		
		
		
	}
}


