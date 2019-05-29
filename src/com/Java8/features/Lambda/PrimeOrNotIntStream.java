package com.Java8.features.Lambda;

import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class PrimeOrNotIntStream {
	//Traditional approach
	private static boolean isPrimeOldway(int number) {		
		if(number < 2) return true;
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
		
		PrimeOrNotIntStream c = new PrimeOrNotIntStream();
		int n = 67676777;
		long startTime = System.nanoTime();
			System.out.println(c.isPrimeOldway(n));
		System.out.println("Execution time oldwayS :"+ (System.nanoTime() - startTime));
		
		startTime = System.nanoTime();
			System.out.println(c.isPrime1way(n));
			System.out.println("Execution time using IntStream :"+ (System.nanoTime() - startTime));
		
		startTime = System.nanoTime();
			System.out.println(c.isPrime2way(n));
			System.out.println("Execution time using IntPredicate :"+ (System.nanoTime() - startTime));
		
		
		
	}
}


