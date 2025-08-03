package util;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ListUtil {

    // ✅ Filter using Predicate
    public static <T> List<T> filter(List<T> items, Predicate<T> condition) {
        return items.stream()
                .filter(condition)
                .collect(Collectors.toList());
    }

    // ✅ Sort using Comparator
    public static <T> List<T> sort(List<T> items, Comparator<T> comparator) {
        return items.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    // ✅ Filter + then Sort
    public static <T> List<T> filterAndSort(List<T> items, Predicate<T> filter, Comparator<T> comparator) {
        return items.stream()
                .filter(filter)
                .sorted(comparator)
                .collect(Collectors.toList());
    }
}
