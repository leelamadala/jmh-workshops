/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomekl007.chapter_3;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 *
 * @author MyMac
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
public class PrimitiveArrays {

    @Param({"1000", "20000"})
    int length;
    int[] intArray;
    Integer[] integerArray;

    @Setup(Level.Trial)
    public void setup() {
        intArray = new int[length];
        integerArray = new Integer[length];

        final Random random = new Random();
        for (int i = 0; i < length; i++) {
            final int value = random.nextInt();
            intArray[i] = value;
            integerArray[i] = value;
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void intArray(Blackhole blackhole) {
        for (int i = 0; i < intArray.length; i++) {
            blackhole.consume(intArray[i]);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void integerArray(Blackhole blackhole) {
        for (Integer integerArray1 : integerArray) {
            blackhole.consume(integerArray1);
        }
    }

    public static void main(String... args) throws RunnerException {
        Options opts;
        opts = new OptionsBuilder()
                .include("PrimitiveArrays")
                .warmupIterations(1)
                .measurementIterations(1)
                .jvmArgs("-Xms2g", "-Xmx2g")
                .shouldDoGC(true)
                .forks(1)
                .build();

        new Runner(opts).run();
    }
}
