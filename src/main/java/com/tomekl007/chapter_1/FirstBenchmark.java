/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomekl007.chapter_1;

import org.openjdk.jmh.annotations.Benchmark;

/**
 *
 * @author MyMac
 * run with java -jar target/benchmarks.jar FirstBenchmark -f 1 -wi 3 -i 10
 * f - forks
 * wi - warmup iterations
 * i - iteration
 */
public class FirstBenchmark {
    
    @Benchmark
    public void testMethod(){
        int a = 1;
        int b = 2;
        int sum = a + b;
    }
    
}
