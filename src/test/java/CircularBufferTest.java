import exception.BufferIsEmptyException;
import exception.BufferIsFullException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class CircularBufferTest {

    @Test
    void purThrowsIllegalArgumentExceptionInCaseOfNullValue() {
        // given
        CircularBuffer<String> buffer = new CircularBuffer<>(1, String.class);

        // when then
        assertThrows(IllegalArgumentException.class, () -> buffer.put(null));
    }

    @Test
    void elementWasAddedToBuffer() {
        // given
        CircularBuffer<String> buffer = new CircularBuffer<>(1, String.class);
        String value = "value";

        // when
        buffer.put(value);

        // then
        assertEquals(value, buffer.get());
    }

    @Test
    void throwsExceptionIfBufferAddElementToFullBuffer() {
        // given
        CircularBuffer<Integer> buffer = new CircularBuffer<>(1, Integer.class);
        buffer.put(1);

        // when - then
        assertThrows(BufferIsFullException.class, () -> buffer.put(2));
    }

    @Test
    void elementsAreRetrievedInCorrectOrder() {
        // given
        CircularBuffer<String> buffer = new CircularBuffer<>(3, String.class);

        // when
        String firstElement = "first";
        buffer.put(firstElement);
        String secondElement = "second";
        buffer.put(secondElement);
        String thirdElement = "third";
        buffer.put(thirdElement);

        // then
        assertEquals(firstElement, buffer.get());
        assertEquals(secondElement, buffer.get());
        assertEquals(thirdElement, buffer.get());
    }

    @Test
    void throwsExceptionIfGetElementFromEmptyBuffer() {
        // given
        CircularBuffer<String> buffer = new CircularBuffer<>(1, String.class);

        // when - then
        assertThrows(BufferIsEmptyException.class, buffer::get);
    }

    @Test
    void isEmptyReturnsTrueForNewBuffer() {
        // given
        CircularBuffer<String> buffer = new CircularBuffer<>(1, String.class);

        // then
        assertTrue(buffer.isEmpty());
    }

    @Test
    void isEmptyReturnsTrueIfAllElementsWereGottenFromBuffer() {
        // given
        CircularBuffer<String> buffer = new CircularBuffer<>(2, String.class);
        buffer.put("");
        buffer.get();

        // then
        assertTrue(buffer.isEmpty());
    }

    @Test
    void isEmptyReturnsFalseIfElementsWereAddedToBuffer() {
        // given
        CircularBuffer<String> buffer = new CircularBuffer<>(2, String.class);
        buffer.put("");

        // then
        assertFalse(buffer.isEmpty());
    }

    @Test
    void gottenElementsAreRemovedFromBuffer() {
        // given
        CircularBuffer<String> buffer = new CircularBuffer<>(2, String.class);
        buffer.put("1");
        buffer.put("2");

        // when
        buffer.get();
        buffer.put("3");

        // then
        assertEquals("2", buffer.get());
        assertEquals("3", buffer.get());
    }

    @Test
    void toObjectArrayReturnsEmptyArrayIfBufferIsEmpty() {
        // given
        CircularBuffer<String> buffer = new CircularBuffer<>(1, String.class);
        buffer.put("");
        buffer.get();

        // when
        Object[] actual = buffer.toObjectArray();

        // then
        assertEquals(0, actual.length);
    }

    @Test
    void toObjectArrayReturnsSameArrayIfBufferIsFull() {
        // given
        String[] expectedValues = new String[3];
        expectedValues[0] = "0";
        expectedValues[1] = "1";
        expectedValues[2] = "2";
        CircularBuffer<String> buffer = new CircularBuffer<>(3, String.class);
        Stream.of(expectedValues).forEach(buffer::put);

        // when
        Object[] actual = buffer.toObjectArray();

        // then
        assertAll(() -> {
            assertEquals(3, actual.length);
            assertEquals(expectedValues[0], actual[0]);
            assertEquals(expectedValues[1], actual[1]);
            assertEquals(expectedValues[2], actual[2]);
        });
    }

    @Test
    void toObjectArrayReturnsArrayOfActualLengthWithoutNullValues() {
        // given
        String[] expectedValues = new String[4];
        expectedValues[0] = "0";
        expectedValues[1] = "1";
        expectedValues[2] = "2";
        expectedValues[3] = "3";
        CircularBuffer<String> buffer = new CircularBuffer<>(4, String.class);
        Stream.of(expectedValues).forEach(buffer::put);

        // when
        buffer.get();
        buffer.get();
        buffer.put("4");
        Object[] actual = buffer.toObjectArray();

        // then
        assertAll(() -> {
            assertEquals(3, actual.length);
            assertEquals("2", actual[0]);
            assertEquals("3", actual[1]);
            assertEquals("4", actual[2]);
        });
    }

    @Test
    void toArrayReturnsEmptyArrayIfBufferIsEmpty() {
        // given
        CircularBuffer<String> buffer = new CircularBuffer<>(1, String.class);
        buffer.put("");
        buffer.get();

        // when
        String[] actual = buffer.toArray();

        // then
        assertEquals(0, actual.length);
    }

    @Test
    void toArrayReturnsSameArrayIfBufferIsFull() {
        // given
        String[] expectedValues = new String[3];
        expectedValues[0] = "0";
        expectedValues[1] = "1";
        expectedValues[2] = "2";
        CircularBuffer<String> buffer = new CircularBuffer<>(3, String.class);
        Stream.of(expectedValues).forEach(buffer::put);

        // when
        String[] actual = buffer.toArray();

        // then
        assertAll(() -> {
            assertEquals(3, actual.length);
            assertEquals(expectedValues[0], actual[0]);
            assertEquals(expectedValues[1], actual[1]);
            assertEquals(expectedValues[2], actual[2]);
        });
    }

    @Test
    void toArrayReturnsArrayOfActualLengthWithoutNullValues() {
        // given
        String[] expectedValues = new String[4];
        expectedValues[0] = "0";
        expectedValues[1] = "1";
        expectedValues[2] = "2";
        expectedValues[3] = "3";
        CircularBuffer<String> buffer = new CircularBuffer<>(4, String.class);
        Stream.of(expectedValues).forEach(buffer::put);

        // when
        buffer.get();
        buffer.get();
        buffer.put("4");
        String[] actual = buffer.toArray();

        // then
        assertAll(() -> {
            assertEquals(3, actual.length);
            assertEquals("2", actual[0]);
            assertEquals("3", actual[1]);
            assertEquals("4", actual[2]);
        });
    }

    @Test
    void asListReturnsEmptyListIfBufferIsEmpty() {
        // given
        CircularBuffer<String> buffer = new CircularBuffer<>(1, String.class);
        buffer.put("");
        buffer.get();

        // when
        List<String> actual = buffer.asList();

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    void asListReturnsSameListIfBufferIsFull() {
        // given
        String[] expectedValues = new String[3];
        expectedValues[0] = "0";
        expectedValues[1] = "1";
        expectedValues[2] = "2";
        CircularBuffer<String> buffer = new CircularBuffer<>(3, String.class);
        Stream.of(expectedValues).forEach(buffer::put);

        // when
        List<String> actual = buffer.asList();

        // then
        assertAll(() -> {
            assertEquals(3, actual.size());
            assertEquals(expectedValues[0], actual.get(0));
            assertEquals(expectedValues[1], actual.get(1));
            assertEquals(expectedValues[2], actual.get(2));
        });
    }

    @Test
    void asListReturnsListOfActualLengthWithoutNullValues() {
        // given
        String[] expectedValues = new String[4];
        expectedValues[0] = "0";
        expectedValues[1] = "1";
        expectedValues[2] = "2";
        expectedValues[3] = "3";
        CircularBuffer<String> buffer = new CircularBuffer<>(4, String.class);
        Stream.of(expectedValues).forEach(buffer::put);

        // when
        buffer.get();
        buffer.get();
        buffer.put("4");
        List<String> actual = buffer.asList();

        // then
        assertAll(() -> {
            assertEquals(3, actual.size());
            assertEquals("2", actual.get(0));
            assertEquals("3", actual.get(1));
            assertEquals("4", actual.get(2));
        });
    }

}
