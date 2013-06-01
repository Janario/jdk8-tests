package jdk8.performance;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ForEach {

    public static void main(String[] args) {
        List<BigDecimal> list = getList();//list from 1 to 100

        Duration classic = printClassicForEach(list);
        Duration streamForEach = printStreamForEach(list);
        Duration iterableForEach = printIterableForEach(list);

        System.out.println("ClassicForEach: " + classic.toMillis());
        System.out.println("StreamForEach: " + streamForEach.toMillis());
        System.out.println("IterableForEach: " + iterableForEach.toMillis());
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
        Instant inicio = Instant.now();
        for (BigDecimal bigDecimal : list) {
            System.out.println(bigDecimal.add(BigDecimal.ONE));
        }
        return Duration.between(inicio, Instant.now());
    }

    private static Duration printStreamForEach(List<BigDecimal> list) {
        Instant inicio = Instant.now();
        list.stream().forEach((bigDecimal) -> {
            System.out.println(bigDecimal.add(BigDecimal.ONE));
        });
        return Duration.between(inicio, Instant.now());
    }

    private static Duration printIterableForEach(List<BigDecimal> list) {
        Instant inicio = Instant.now();
        list.forEach((bigDecimal) -> {
            System.out.println(bigDecimal.add(BigDecimal.ONE));
        });
        return Duration.between(inicio, Instant.now());
    }
}
