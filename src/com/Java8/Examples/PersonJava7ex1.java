package com.Java8.Examples;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PersonJava7ex1 {

	public static void main(String[] args) {
		List<Person> people = Arrays.asList(
			new Person("Llory","Mane",31),
			new Person("Doni","More",32),
			new Person("Paul","Choey",34),
			new Person("John","Taylor",30),
			new Person("Godin","Cia",30),
			new Person("Charle","Chapplin",30)
		);
		
	// Step 1 : 	Sort List by First name.
		Collections.sort(people, new Comparator<Person>(){

			@Override
			public int compare(Person o1, Person o2) {
				return o1.getFirstName().compareTo(o2.getFirstName());
			}
			
		});
	// Step 2 : 	Create a method that print all elements in the list.
		printall(people);
	
	// Step 3 : 	Create a method that print all elements in list having last element as 'C'.
		System.out.println("\nLast name starting with 'C'");
		printPersonWithLastNameasC(people);

		
	//Step 4 : Condition as behaviour
		System.out.println("\n First and last name start with 'C'");
		printConditionally(people,new Condition() {
			
			@Override
			public boolean test(Person p) {

				return p.getFirstName().startsWith("C") && p.getLastName().startsWith("C");
			}
		});
	}

	
	private static void printConditionally(List<Person> people , Condition condition){
		for(Person p: people){
			if(condition.test(p)) {
				System.out.println(p);
			}
		}
	}
	private static void printPersonWithLastNameasC(List<Person> people) {
		for(Person p: people){
			if(p.getLastName().startsWith("C")) {
				System.out.println(p);
			}
		}
		
	}

	private static void printall(List<Person> people) {
		for(Person p: people){
			System.out.println(p);
		}
	}

}
 interface Condition{
	 boolean test(Person p);
 }