package com.Java8.features.Stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/*
 * https://www.journaldev.com/2774/java-8-stream
 */
public class CreateVariousStream {

	public static void main(String[] args) {
		//--1 | Creating various Streams
		Stream<Integer> stream = Stream.of(1,2,3,4);
		Stream<Integer> stre = Stream.of(new Integer[] {1,2,3,1,4});
		
		IntStream intStream = IntStream.of(1,2,3,4,5);
		IntStream is2 = "abc".chars();
		is2.forEach(System.out::print);
		LongStream is = Arrays.stream(new long[]{1,2,3,4});
		
		//Stream<Integer> stream1 = Stream.of(new int[]{1,2,3,4});  //// it doesn’t support autoboxing Compile time error, Type mismatch: cannot convert from Stream<int[]> to Stream<Integer>
		
		//--2 | Creating Stream through generate() and iterate
		Stream<String> stream1 = Stream.generate(() -> {return "abc";});
		Stream<String> stream2 = Stream.iterate("abc", (i) -> i);

		
		//--3 | Sequential & Parallel Stream creation
		List<Integer> myList = new ArrayList<>();
		for(int i=0; i<100; i++) myList.add(i);
				
		//sequential stream
		Stream<Integer> sequentialStream = myList.stream();
				
		//parallel stream
		Stream<Integer> parallelStream = myList.parallelStream();
	
	
		//--4 | Converting Java Stream to Collection or Array
		System.out.println("--Converting Java Stream to Collection or Array--");
		Stream<Integer> intStream1 = Stream.of(1,2,3,4);
		List<Integer> intList = intStream1.collect(Collectors.toList());  // converting Stream to List

		System.out.println(intList); //prints [1, 2, 3, 4]

		intStream1 = Stream.of(1,2,3,4); //stream is closed, so we need to create it again
		Map<Integer,Integer> intMap = intStream1.collect(Collectors.toMap(i -> i, i -> new Integer(i+""+i)));  //Converting Stream to Map
		System.out.println(intMap); //prints {1=11, 2=22, 3=33, 4=44}
		
		intStream1 = Stream.of(1,2,3,4);
		ConcurrentMap<Object, Object> cMap = intStream1.collect(Collectors.toConcurrentMap(i ->i, i ->i+i)); //Converting into ConcurrentHashMap
		
		cMap.forEach((k,v)->System.out.println(k +"="+v));
		
		
		
		//--5 | Convert Stream to Array using .toArray
		System.out.println("--Convert Stream to Array using .toArray----");
		intStream1 = Stream.of(1,2,3,4);
		Integer[] intArray = intStream1.toArray(Integer[]::new);
		System.out.println(Arrays.toString(intArray)); //prints [1, 2, 3, 4]
		
		
		
	}

}
