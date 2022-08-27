package com.laptrinhjava.demo;

import java.util.ArrayList;
import java.util.Arrays;

public class test2 {
	
	    public static Object[] findMostApperence(int []arr){
	        if (arr == null || arr.length == 0) {
	            return null;
	        }
	        int max = 0;
	        int[] a =new int[100000];
	        Arrays.fill(a,0);
	        ArrayList<Integer> b = new ArrayList<>();

	        for (int i=0;i< arr.length;i++){
	            int tmp = arr[i];
	            a[tmp]++;
	            if (a[tmp]>max){
	                max= a[tmp];
	                b= new ArrayList<>();
	            }

	            if (a[tmp]==max){
	            	System.out.println(b);
	                b.add(tmp);
	            }
	        }
	        return b.toArray();
	    }

	    public static void main (String[] args) {
	        int arr[]={1,2,3,4,5,1,2,2};
	        Object[] result = findMostApperence(arr);
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
	    }
	
}
