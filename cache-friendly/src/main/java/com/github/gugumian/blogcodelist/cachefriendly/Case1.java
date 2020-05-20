package com.github.gugumian.blogcodelist.cachefriendly;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.SingleShotTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Case1 {

    @Param({"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19",
            "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "64"})
    private int step;

    private int[] arr;

    @Setup
    public void init() {
        this.arr = new int[1 << 28];
        Arrays.fill(this.arr, 1);
    }

    @Benchmark
    public void traverseArr(Blackhole blackhole) {
        int sum = 0;
        for(int i = 0; i < arr.length; i += step) {
            sum += arr[i];
        }
        blackhole.consume(sum);
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(Case1.class.getSimpleName())
                .forks(1)
                .threads(1)
                .warmupIterations(0)
                .measurementIterations(1)
                .output("case1.log")
                .jvmArgsAppend("-Xmx4g", "-Xms4g")
                .build();
        new Runner(opt).run();
    }
}
