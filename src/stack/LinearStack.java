package stack;

/**
 * 栈，数组实现
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

    /**
     * 返回元素个数
     * @return
     */
    public int length() {
        return mLength;
    }

    /**
     * 返回stack的大小
     * @return
     */
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

    /**
     * 空间不足时扩展
     * @param size
     */
    private void addStackSize(int size) {
        //扩展空间的具体大小此处示例
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
