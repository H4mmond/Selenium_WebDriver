package academit.lessonfive;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

public class ParameterizedTests {
    @ParameterizedTest(name = "{index} - Say hello to the person with name")
    @ValueSource(strings = {"Ivan", "Petr", "Maria"})
    void sayHello(String name) {
        System.out.println("Hello, " + name);
    }

    @ParameterizedTest(name = "{index} - Multiple by 1000")
    @ValueSource(ints = {1, 20, 300})
    void multipleBy1000(int number) {
        System.out.println(number * 1000);
    }

    @ParameterizedTest
    @CsvSource({
            "first, 1, 1, 2",
            "second, 2, 2, 4",
            "third, 3, 3, 6"
    })
    void summarizeNumbers(String testName, int firstNumber, int secondNumber, int sum) {
        Assertions.assertEquals(sum,(firstNumber + secondNumber));
        System.out.println("Sum for numbers, " + testName + " test: ");
        System.out.println(firstNumber + " + " + secondNumber + " = " + sum);
    }

    @ParameterizedTest
    @MethodSource("provideTestData")
    void summarizeNumbersWithMethodSourse(int firstNumber, int secondNumber, int sum) {
        Assertions.assertEquals(sum, (firstNumber + secondNumber));
    }

    private static Stream<Arguments> provideTestData() {
        return Stream.of(
                Arguments.of(1, 1, 2),
                Arguments.of(2, 2, 4),
                Arguments.of(3, 3, 6)
        );
    }
}
