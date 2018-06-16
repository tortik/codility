package com.project.euler;

import java.util.*;
import java.util.AbstractMap.SimpleEntry;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StackSolution {

    Stack<Integer> stack = new Stack<>();

    public int solution(String S) {
        Map<Predicate<String>, Consumer<String>> commandHandlers = getCommandHandlers();
        String[] commands = S.split(" ");
        Arrays.stream(commands).forEach(partial(this::applyCommand, commandHandlers));
        return stack.peek();
    }

    private static <F, S> Consumer<S> partial(BiConsumer<F, S> biConsumer, F firstArg) {
        return second -> biConsumer.accept(firstArg, second);
    }

    private void applyCommand(Map<Predicate<String>, Consumer<String>> commandHandlers, String command) {
        commandHandlers.entrySet().stream().filter(it -> it.getKey().test(command)).map(Map.Entry::getValue).
                forEach(cons -> cons.accept(command));
    }

    private Map<Predicate<String>, Consumer<String>> getCommandHandlers() {
        Predicate<String> numberPredicate = Pattern.compile("^-?\\d+$").asPredicate();
        Predicate<String> popPredicate = it -> Objects.equals("POP", it);
        Predicate<String> dupPredicate = it -> Objects.equals("DUP", it);
        Predicate<String> plusPredicate = it -> Objects.equals("+", it);
        Predicate<String> subtractPredicate = it -> Objects.equals("-", it);

        Consumer<String> numberConsumer = num -> stack.push(Integer.parseInt(num));
        Consumer<String> popConsumer = num -> stack.pop();
        Consumer<String> dupConsumer = num -> stack.push(stack.peek());
        Consumer<String> plusConsumer = num -> stack.push(stack.pop() + stack.pop());
        Consumer<String> subtractConsumer = num -> stack.push(stack.pop() - stack.pop());


        return Collections.unmodifiableMap(Stream.of(
                new SimpleEntry<>(numberPredicate, numberConsumer),
                new SimpleEntry<>(popPredicate, popConsumer),
                new SimpleEntry<>(dupPredicate, dupConsumer),
                new SimpleEntry<>(plusPredicate, plusConsumer),
                new SimpleEntry<>(subtractPredicate, subtractConsumer))
                .collect(Collectors.toMap(SimpleEntry::getKey, SimpleEntry::getValue)));
    }

    public static void main(String[] args) {
        StackSolution stackSolution = new StackSolution();
        System.out.println(stackSolution.solution("13 DUP 4 POP 5 DUP + DUP + -"));
    }

}
