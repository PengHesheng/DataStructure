package queue;

/**
 * @author 14512 on 2018/9/10.
 */
public class LinearQueue<E> {

    private Object[] elements;
    private int mLength;
    private int mSize;

    public LinearQueue() {
        mSize = 10;
        init();
    }

    public LinearQueue(int size) {
        mSize = size;
        init();
    }

    private void init() {
        mLength = 0;
        elements = new Object[mSize];
    }

    public boolean enqueue(E elem) {
        if (mLength >= mSize) {
            addQueueSize(mSize);
        }
        elements[mLength] = elem;
        mLength++;
        return true;
    }

    public E getHead() {
        if (mLength == 0) {
            return null;
        }
        return (E) elements[0];
    }

    public E dequeue() {
        if (mLength == 0) {
            return null;
        }
        E elem = (E) elements[0];
        for (int i = 1; i < mLength; i++) {
            elements[i - 1] = elements[i];
        }
        mLength--;
        return elem;
    }

    public int length() {
        return mLength;
    }

    private void addQueueSize(int size) {
        int newSize = mSize + size;
        Object[] newElements = new Object[newSize];
        System.arraycopy(elements, 0, newElements, 0, mLength);
        elements = newElements;
        mSize = newSize;
    }

}
