import exception.BufferIsEmptyException;
import exception.BufferIsFullException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class CircularBuffer<T> {

    private T[] values;
    private int headIndex = 0;
    private int tailIndex = 0;
    private Class<T> type;

    public CircularBuffer(int size, Class<T> type) {
        this.type = type;
        values = createEmptyArray(size);
    }

    public void put(T t) {
        if (t == null) {
            throw new IllegalArgumentException();
        }
        if (isFull()) {
            throw new BufferIsFullException();
        }
        values[headIndex] = t;
        headIndex = defineNextIndexPosition(headIndex);
    }

    public T get() {
        if (isEmpty()) {
            throw new BufferIsEmptyException();
        }
        T tailValue = values[tailIndex];
        values[tailIndex] = null;
        tailIndex = defineNextIndexPosition(tailIndex);
        return tailValue;
    }

    public Object[] toObjectArray() {
        return toArray();
    }

    public T[] toArray() {
        if (isEmpty()) {
            return createEmptyArray(0);
        }
        if (isFull()) {
            T[] output = createEmptyArray(values.length);
            System.arraycopy(values, 0, output, 0, values.length);
            return output;
        }
        int actualSize = defineActualSize();
        T[] output = createEmptyArray(actualSize);
        for (int i = tailIndex, j = 0; i != headIndex; j++) {
            output[j] = values[i];
            i = defineNextIndexPosition(i);
        }
        return output;
    }

    public List<T> asList() {
        return Arrays.asList(toArray());
    }

    public boolean isEmpty() {
        return headIndex == tailIndex && values[headIndex] == null;
    }

    private T[] createEmptyArray(int size) {
        return (T[]) Array.newInstance(type, size);
    }

    private int defineActualSize() {
        if (isFull()) {
            return values.length;
        }
        if (isEmpty()) {
            return 0;
        }
        return headIndex > tailIndex ? headIndex - tailIndex : values.length - (tailIndex - headIndex);
    }

    private boolean isFull() {
        return headIndex == tailIndex && values[headIndex] != null;
    }

    private int defineNextIndexPosition(int currentValue) {
        return currentValue + 1 == values.length ? 0 : currentValue + 1;
    }

}
