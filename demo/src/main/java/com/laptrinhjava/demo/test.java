package com.laptrinhjava.demo;

import java.util.ArrayList;

public class test {

	public static Object[] findMostAppearence(int []a) {
		int maxA=0;
		int[] b;
		for(int i=0;i<a.length;i++) {
			if(a[i]>maxA)
				maxA=a[i];
		}
		
		b=new int[maxA+1];
		 ArrayList<Integer> b2 = new ArrayList<>();
		for(int i=0;i<a.length;i++) {
			b[a[i]]=0;
		}
		int max=0;
		int number=0;
		for(int i=0;i<a.length;i++) {
			b[a[i]]++;
			if(b[a[i]]>max) {
				max=b[a[i]];
				number=a[i];
				b2=new ArrayList<>();
			}
			if(b[a[i]]==max) {
				b2.add(a[i]);
			}
		}
		
		return b2.toArray();
	}
	public static void main(String[] args) {
		int[] a= {9,1,2,9,6,1,8,7,5,8,8,9};
		Object[] result = findMostAppearence(a);
		if (result != null) {
            System.out.print("[");
            for (int i=0; i<result.length; i++){
                if (i == result.length - 1){
                    System.out.print( result[i] );
                }else{
                    System.out.print( result[i] + ",");
                }
            }
            System.out.println("]");
        } else {
            System.out.println("Invalid array");
        }
		int number=20;
		long factorial=Factorial(20);
		System.out.println("giai thua cua "+number+" la: "+factorial);
	}
	public static long Factorial(int number) {
		long giai_thua = 1;
	    for (int i = 1; i <= number; i++)
	        giai_thua *= i;
	    return giai_thua;
	}
}
