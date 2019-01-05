package com.techdialogue.benchmark.jmh;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@State(Scope.Benchmark)
public class ThreadSample {

    private Map<Long,Boolean> map1 =  new HashMap<>();

    @Param(value={"0","1000"})
    private long tokens;

    public void doSomething(long id){
        synchronized(map1){
            if(!map1.containsKey(id))
                map1.put(id,Boolean.TRUE);
        }
    }

    @Benchmark
    public void record1(){
        Blackhole.consumeCPU(tokens);
        long id  = Thread.currentThread().getId();
        doSomething(id);
    }

    private ConcurrentHashMap<Long,Boolean> map2 = new ConcurrentHashMap<>();

    public void doSomething2(long id){
        map2.putIfAbsent(id, Boolean.TRUE);
    }


    @Benchmark
    public void record2(){
        Blackhole.consumeCPU(tokens);
        long id  = Thread.currentThread().getId();
        doSomething2(id);
    }

}
