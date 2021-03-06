package simple;

import org.openjdk.jmh.annotations.*;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@BenchmarkMode(Mode.AverageTime)
@Fork(value = 1, jvmArgsAppend = {
        "-XX:-UseSuperWord",
        "-XX:+UnlockDiagnosticVMOptions",
        "-XX:CompileCommand=print,*Main.increment"})
@Warmup(iterations = 5)
@Measurement(iterations = 10)
public class Main {
    public static final int SIZE = 1024;

    @State(Scope.Thread)
    public static class Context {
        public final byte[] values = new byte[SIZE];
        public final byte[] results = new byte[SIZE];

        @Setup
        public void setup() {
            Random random = new Random();
            for (int i = 0; i < SIZE; i++) {
                values[i] = (byte)random.nextInt(Byte.MAX_VALUE - 1);
            }
        }
    }

    @Benchmark
    public byte[] increment(Context context) {
        for (int i = 0; i < SIZE; i++) {
            context.results[i] = (byte)(context.values[i] + 1);
        }
        return context.results;
    }
}
