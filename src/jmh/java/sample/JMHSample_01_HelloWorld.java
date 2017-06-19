package sample;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@Measurement(iterations = 4)
@Warmup(iterations = 4)
@Fork(1)
public class JMHSample_01_HelloWorld {
    @State(Scope.Benchmark)
    public static class BenchmarkState {
        volatile double x = Math.PI;
    }

    @State(Scope.Thread)
    public static class ThreadState {
        volatile double x = Math.PI;
    }

    @Benchmark
    public void measureUnshared(ThreadState state) {
        state.x++;
    }

    @Benchmark
    public void measureShared(BenchmarkState state) {
        state.x++;
    }
}
