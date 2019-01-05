package com.techdialogue.benchmark.jmh;

import org.openjdk.jmh.annotations.*;

import java.util.regex.Pattern;

@State(Scope.Benchmark)
public class BasicSample {

    @Setup
    public void setupMyBenchmark(){

    }

    private String input =  "some==string==to==split";
    private Pattern pattern = Pattern.compile("==");

    @Benchmark
    public String[] benchmark1(){
        return input.split("==");
    }

    @Benchmark
    public String[] benchmark2(){
        return pattern.split(input,2);
    }

    @TearDown
    public void tearDownMyBenchmark(){

    }

}