package com.Java8.OptionalClassDemo;

import java.util.Optional;

/*
 * Optional:
Optional is a final Class introduced as part of Java SE 8. It is defined in java.util package.
It is used to represent optional values that is either exist or not exist. It can contain either one value or zero value. If it contains a value, we can get it. Otherwise, we get nothing.
It is a bounded collection that is it contains at most one element only. It is an alternative to “null” value.
----Main Advantage of Optional is:----
--> It is used to avoid null checks.
--> It is used to avoid “NullPointerException”.
 */
public class OptionalClassDemo {

	public static void main(String[] args) {
		String[] words = new String[10];
		//String wrd = words[5].toLowerCase();  // remove the comment to check the  java.lang.NullPointerException
		Optional<String> checkNull = Optional.ofNullable(words[5]);
		
		if (checkNull.isPresent()) {
			String word = words[5].toLowerCase();
			System.out.print(word);
		} else
			System.out.println("word is null");
	
	//java 9 introduce ifPresentorElse()
		//checkNull.ifPresentOrElse(words[5].toLowerCase().toString()::println, System.out.print("Word Is null"););
	}

}
