package com.techdialogue.benchmark.stream;

import org.openjdk.jmh.annotations.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;
import java.util.stream.Stream;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
//@Fork(value = 2, jvmArgs = {"-Xms4G", "-Xmx4G"})
@Fork(jvmArgs = {"-Xms4G", "-Xmx4G"})
@State(Scope.Benchmark)
public class ParallelStreamBenchmark {

    private static final long N= 10_000_000L;

    @Benchmark
    public long sequentialiterativeSum(){
        return Stream.iterate(1L, i->i+1)
                .limit(N)
                .reduce(0L, Long::sum);
    }

    //slow because of boxing overhead and sequential nature of iterate
    @Benchmark
    public long parallelIterativeSum(){
        return Stream.iterate(1L, i->i+1)
                .limit(N)
                .parallel()
                .reduce(0L, Long::sum);
    }

    //fast because works on primitives
    @Benchmark
    public long forLoopSum(){
        long result =0;
        for(long i=1L; i<=N; i++){
            result +=1;
        }
        return result;
    }

    ////////////////////////////////////////////////////////////////////////
    //fast because of primitives
    @Benchmark
    public long rangedSum(){
        return LongStream.rangeClosed(1,N)
                .reduce(0L, Long::sum);
    }

    //fastest
    @Benchmark
    public long parallelRangedSum(){
        return LongStream.rangeClosed(1,N)
                .parallel()
                .reduce(0L, Long::sum);
    }

    @TearDown(Level.Invocation)
    public void tearDown(){
        System.gc();
    }

}
