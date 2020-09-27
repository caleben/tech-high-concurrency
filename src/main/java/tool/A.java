package tool;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author wuenci
 * @date 2020/9/25
 * @since 2.0.0
 */
public class A {
    public static void main(String[] args) {
        Stream<String> i = Stream.of("I", "love", "you", "too");
        Map<String, Integer> collect = i.collect(Collectors.toMap(Function.identity(), String::length));
        System.out.println(collect);

    }
}
