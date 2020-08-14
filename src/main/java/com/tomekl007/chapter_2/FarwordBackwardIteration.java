/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tomekl007.chapter_2;

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
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 *
 * @author MyMac
 */
@State(Scope.Benchmark)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class FarwordBackwardIteration {

    @Param({"16348", "5536", "524288"})
    private int length;

    private int[] values;
    private int[] factors;
    private int[] results;

    @Setup(Level.Trial)
    public void setup() {
        values = new int[length];
        factors = new int[length];
        results = new int[length];

        final Random random = new Random();
        for (int i = 0; i < values.length; i++) {
            values[i] = random.nextInt();
            factors[i] = random.nextInt();
            results[i] = values[i];
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void baseline() {
        final int len = values.length - 1;
        for (int i = 0; i < values.length; i++) {
            results[len - i] = (values[i] * factors[i]);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void forwardIteration() {
        for (int i = 0; i < values.length; i++) {
            int value = i < 10_000 ? values[i] : 2 * values[i];
            int factor = factors[i];
            results[i] = (value * factor);
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    public void backWardIteration() {
        final int len = values.length - 1;
        for (int i = len; i >= 0; i--) {
            int value = i < 10_000 ? values[i] : 2 * values[i];
            int factor = factors[i];
            results[i] = (value * factor);
        }
    }

    public static void main(String... args) throws RunnerException {
        Options opts;
        opts = new OptionsBuilder()
                .include("FarwordBackwardIteration")
                .warmupIterations(1)
                .measurementIterations(1)
                .jvmArgs("-Xms2g", "-Xmx2g")
                .shouldDoGC(true)
                .forks(1)
                .build();

        new Runner(opts).run();
    }
}
