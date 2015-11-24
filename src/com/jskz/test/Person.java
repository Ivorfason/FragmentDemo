package com.jskz.test;

class Person {  
	
    public static void prt(String s) {  
       System.out.println(s);  
    }  
    
    Person() {  
       prt("A Person.");  
    } //构造方法(1)  
   
    Person(String name) {  
       prt("A person name is:" + name);  
    } //构造方法(2)  
}  
   
class Chinese extends Person {  
	Chinese() {  
	    super(); 
	    prt("A chinese.");
    }  
   
    Chinese(String name) {  
	    super(name); 
	    prt("his name is:" + name);  
    }  
   
    Chinese(String name, int age) {  
	    this(name); 
	    prt("his age is:" + age);  
    }  
   
    public static void main(String[] args) {  
       Chinese cn = new Chinese();  
       cn = new Chinese("kevin");  
       cn = new Chinese("kevin", 22);  
    }  
}  
