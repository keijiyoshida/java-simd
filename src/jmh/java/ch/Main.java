package ch;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1, jvmArgsAppend = {
        "-XX:+UseSuperWord",
        "-XX:+UnlockDiagnosticVMOptions",
        "-XX:CompileCommand=print,*Main.increment"})
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public class Main {
    public static final int SIZE = 1024;

    @State(Scope.Thread)
    public static class Context {
        public final char[] values = new char[SIZE];
        public final char[] results = new char[SIZE];

        @Setup
        public void setup() {
            Random random = new Random();
            for (int i = 0; i < SIZE; i++) {
                values[i] = (char)random.nextInt(Character.MAX_VALUE - 1);
            }
        }
    }

    @Benchmark
    public char[] increment(Context context) {
        for (int i = 0; i < SIZE; i++) {
            context.results[i] = (char)(context.values[i] ^ 0x0022);
        }
        return context.results;
    }
}
