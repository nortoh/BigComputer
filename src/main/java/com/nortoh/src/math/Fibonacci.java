package com.nortoh.src.math;

public class Fibonacci {

    public static long get(int n) {
        long fib1 = 0;
        long fib2 = 1;
        long fibTotal = 0;

        for (int i = 0; i <= n; i++) {
            fibTotal = fib1 + fib2;
            fib1 = fib2;
            fib2 = fibTotal;
        }
        return fibTotal;
    }
}
