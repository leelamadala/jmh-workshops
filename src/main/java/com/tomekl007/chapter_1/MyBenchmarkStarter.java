/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomekl007.chapter_1;

import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 *
 * @author MyMac
 */
public class MyBenchmarkStarter {

    /**
     * Benchmark testing
     */
    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    @OutputTimeUnit(TimeUnit.SECONDS)
    public void testMethod() {
        int a = 1;
        int b = 2;
        int sum = a + b;
        // System.out.println("sum is ::  " + sum);
    }
    
    public static void main(String... args) throws RunnerException{
        Options opts;
        opts = new OptionsBuilder()
                .include("MyBenchmarkStarter")
                .warmupIterations(1)
                .measurementIterations(1)
                .jvmArgs("-Xms2g", "-Xmx2g")
                .shouldDoGC(true)
                .forks(1)
                .build();
        
        new Runner(opts).run();
    }
}
