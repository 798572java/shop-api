package com.fh.shop.admin.demo;


public class FBNQ {
    public static void main(String[] args) {
        //aaa();
       // int a=jiecheng(3);
        //.out.println(a);

       int a= factorial(4);
        System.out.println(a);
    }

    public static int factorial(int n){
        if (n==0){
            return 1;
        }else {
            return  factorial(n-1)*n;//1*2*3...
        }
    }





    public static  void aaa() {
        long jieguo = ccc(20);
        System.out.println(jieguo);
    }

    public static   int ccc(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else if (n == 2) {
            return 1;
        }else {
            return ccc(n - 1) + ccc(n - 2);
        }
    }


    public static int jiecheng(int num) {
        if (num == 1) {
            return 1;
        }
        return num * jiecheng(num - 1);
    }





}
