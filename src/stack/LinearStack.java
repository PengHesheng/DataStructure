package stack;

/**
 * @author 14512 on 2018/9/9.
 */
public class LinearStack<E> {

    private Object[] elements;

    /**
     * 入栈个数
     */
    private int mLength;
    /**
     * 栈的大小
     */
    private int mSize;
    private int mBase = 0;
    private int mTop;

    public LinearStack() {
        mLength = 0;
        mTop = 0;
        mSize = 10;
        elements = new Object[mSize];
    }

    public LinearStack(int size) {
        mLength = 0;
        mTop = 0;
        mSize = size;
        elements = new Object[mSize];
    }

    public int length() {
        return mLength;
    }

    public int size() {
        return mSize;
    }

    public boolean push(E elem) {
        if (mTop - mBase >= mSize) {
            addStackSize(mSize);
        }
        elements[mTop++] = elem;
        mLength++;
        return true;
    }

    private void addStackSize(int size) {
        int newSize = mSize + size;
        Object[] newElements = new Object[newSize];
        System.arraycopy(elements, 0, newElements, 0, mLength);
        elements = newElements;
        mSize = newSize;
    }

    public E pop() {
        if (mBase == mTop) {
            return null;
        }
        E elem = (E) elements[--mTop];
        elements[mTop] = null;
        mLength--;
        return elem;
    }

    public E getTop() {
        if (mBase == mTop) {
            return null;
        }
        return (E) elements[mTop - 1];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < mLength; i++) {
            stringBuilder.append(elements[i]).append("  ");
        }
        return stringBuilder.toString();
    }
}
