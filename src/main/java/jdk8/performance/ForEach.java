package jdk8.performance;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class ForEach {

//    private static final Function<Long, List<BigDecimal>> seeds = i -> {
//        if (i == 0) {
//            return new ArrayList<>();
//        }
//
//        final List<BigDecimal> s = seeds.apply(i - 1);
//        s.add(BigDecimal.valueOf(i));
//        return s;
//    };
    public static void main(String[] args) {
        List<BigDecimal> list = getList();

        for (int i = 0; i < 2; i++) {
            Duration classic = printClassicForEach(list);
            Duration streamForEach = printStreamForEach(list);
            Duration iterableForEach = printIterableForEach(list);

            System.out.printf("Classic:  %s ms\n", classic.toMillis());
            System.out.printf("Stream:   %s ms\n", streamForEach.toMillis());
            System.out.printf("Iterable: %s ms\n", iterableForEach.toMillis());
        }
        //Example output:
        //ClassicForEach: 3
        //StreamForEach: 58
        //IterableForEach: 6
    }

    private static List<BigDecimal> getList() {
        List<BigDecimal> list = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            list.add(BigDecimal.valueOf(i));
        }
        return list;
    }

    private static Duration printClassicForEach(List<BigDecimal> list) {
        final StringJoiner sj = new StringJoiner(", ", "[", "]");
        final Instant inicio = Instant.now();
        for (BigDecimal bigDecimal : list) {
            sj.add(bigDecimal.add(BigDecimal.ONE).toString());
        }
        final Duration duration = Duration.between(inicio, Instant.now());
        System.out.println(sj.toString());
        return duration;
    }

    private static Duration printStreamForEach(List<BigDecimal> list) {
        final StringJoiner sj = new StringJoiner(", ", "[", "]");
        final Instant inicio = Instant.now();
        list.stream().forEach((bigDecimal) -> {
            sj.add(bigDecimal.add(BigDecimal.ONE).toString());
        });
        final Duration duration = Duration.between(inicio, Instant.now());
        System.out.println(sj.toString());
        return duration;
    }

    private static Duration printIterableForEach(List<BigDecimal> list) {
        final StringJoiner sj = new StringJoiner(", ", "[", "]");
        final Instant inicio = Instant.now();
        list.forEach((bigDecimal) -> {
            sj.add(bigDecimal.add(BigDecimal.ONE).toString());
        });
        final Duration duration = Duration.between(inicio, Instant.now());
        System.out.println(sj.toString());
        return duration;
    }
}
