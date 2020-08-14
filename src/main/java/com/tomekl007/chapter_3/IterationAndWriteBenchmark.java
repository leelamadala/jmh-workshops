/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomekl007.chapter_3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
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
public class IterationAndWriteBenchmark {

    //@Param({"1000", "10000", "200000"})
    @Param({"1000", "10000"})
    int length;
    int[] intArray;
    Integer[] integerArray;
    ArrayList<Integer> arrayList;
    LinkedList<Integer> linkedList;

    @Setup(Level.Trial)
    public void setup() {
        intArray = new int[length];
        integerArray = new Integer[length];
        arrayList = new ArrayList<>(length);
        linkedList = new LinkedList<>();

        final Random random = new Random();
        for (int i = 0; i < length; i++) {
            final int value = random.nextInt();
            intArray[i] = value;
            integerArray[i] = value;
            arrayList.add(i);
            linkedList.add(i);
        }
        Collections.shuffle(arrayList);
        Collections.shuffle(linkedList);
        integerArray = arrayList.toArray(integerArray);
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

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void arrayList(Blackhole blackhole) {
        arrayList.forEach((integerArray1) -> {
            blackhole.consume(integerArray1);
        });
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void linkedList(Blackhole blackhole) {
        linkedList.forEach((integerArray1) -> {
            blackhole.consume(integerArray1);
        });
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int intArray1() {
        long sum = 0;

        for (final int value : intArray) {
            sum += value;
        }
        return (int) sum / length;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int integerArray1() {
        long sum = 0;
        for (final int value : integerArray) {
            sum += value;
        }
        return (int) sum / length;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int arrayList1() {
        long sum = 0;
        for (final int value : arrayList) {
            sum += value;
        }
        return (int) sum / length;
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public int linkedList1() {
        long sum = 0;
        for (final int value : linkedList) {
            sum += value;
        }
        return (int) sum / length;
    }

    public static void main(String... args) throws RunnerException {
        Options opts;
        opts = new OptionsBuilder()
                .include("IterationAndWriteBenchmark")
                .warmupIterations(1)
                .measurementIterations(1)
                .jvmArgs("-Xms2g", "-Xmx2g")
                .shouldDoGC(true)
                .forks(1)
                .build();

        new Runner(opts).run();
    }
}
