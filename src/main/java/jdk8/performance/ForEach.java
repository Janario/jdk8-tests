package jdk8.performance;

import com.google.caliper.Benchmark;
import com.google.caliper.runner.CaliperMain;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class ForEach {

    private static final Function<Integer, List<Integer>> SEEDS = i -> {
        if (i == 0) {
            return new ArrayList<>();
        }
        final List<Integer> s = SEEDS.apply(i - 1);
        s.add(i);
        return s;
    };

    public static class ForEachBenchmark extends Benchmark {

        private List<Integer> list;

        @Override
        protected void setUp() throws Exception {
            list = SEEDS.apply(1000);
        }

        public void timeForEachClassic(int reps) {
            for (int i = 0; i < reps; i++) {
                AtomicInteger atomicInteger = new AtomicInteger(0);
                for (Integer integer : list) {
                    atomicInteger.accumulateAndGet(integer, Integer::sum);
                }
            }
        }

        public void timeForEachStream(int reps) {
            for (int i = 0; i < reps; i++) {

                AtomicInteger atomicInteger = new AtomicInteger(0);
                list.stream().forEach((integer) -> {
                    atomicInteger.accumulateAndGet(integer, Integer::sum);
                });
            }
        }

        public void timeForEachArrayList(int reps) {
            for (int i = 0; i < reps; i++) {
                AtomicInteger atomicInteger = new AtomicInteger(0);
                list.forEach((integer) -> {
                    atomicInteger.accumulateAndGet(integer, Integer::sum);
                });
            }
        }

        public void timeForEachParallelStream(int reps) {
            for (int i = 0; i < reps; i++) {
                AtomicInteger atomicInteger = new AtomicInteger(0);
                list.parallelStream().forEach((integer) -> {
                    atomicInteger.accumulateAndGet(integer, Integer::sum);
                });
            }
        }

    }

    public static void main(String[] args) {
//        ObjectArrays.concat(args, new String[]{"-Cinstrument.micro.options.warmup=10s", "--time-limit", "40s"}, String.class)
        CaliperMain.main(ForEachBenchmark.class, args);
    }
}
