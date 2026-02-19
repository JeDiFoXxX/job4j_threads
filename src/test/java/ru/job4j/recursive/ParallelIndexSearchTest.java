package ru.job4j.recursive;

import org.junit.jupiter.api.*;
import ru.job4j.email.User;
import ru.job4j.linked.Node;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParallelIndexSearchTest {
    @Test
    void shouldThrowExceptionWhenTypesAreDifferent() {
        User[] array = createArray(1);
        Node<Integer> object = new Node<>(null, 1);
        String expected = "Different type array and search object";
        Exception result = assertThrows(
                IllegalArgumentException.class, () ->
                        new ParallelIndexSearch<>(array, object, 0, array.length)
        );
        assertThat(expected).isEqualTo(result.getMessage());
    }

    @Test
    void shouldReturnMinusOneWhenObjectNotFound() {
        User[] array = createArray(1);
        User object = new User("10", "10@mail.ru");
        Integer result = ParallelIndexSearch.search(array, object);
        assertThat(-1).isEqualTo(result);
    }

    @Test
    void shouldFindObjectInSmallArrayUsingLinearSearch() {
        User[] array = createArray(1);
        User object = new User("0", "0@mail.ru");
        Integer result = ParallelIndexSearch.search(array, object);
        assertThat(0).isEqualTo(result);
    }

    @Test
    void shouldFindObjectInLargeArrayUsingRecursiveSearch() {
        User[] array = createArray(40);
        User object = new User("33", "33@mail.ru");
        Integer result = ParallelIndexSearch.search(array, object);
        assertThat(33).isEqualTo(result);
    }

    private static User[] createArray(int length) {
        User[] array = new User[length];
        for (int i = 0; i < length; i++) {
            array[i] = new User(String.valueOf(i), i + "@mail.ru");
        }
        return array;
    }
}