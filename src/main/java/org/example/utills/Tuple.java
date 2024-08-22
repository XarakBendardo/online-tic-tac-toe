package org.example.utills;

public record Tuple<T1, T2>(T1 first, T2 second) {
    public static <T1, T2> Tuple<T1, T2> of(T1 first, T2 second) {
        return new Tuple<>(first, second);
    }
}
