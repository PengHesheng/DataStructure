package list;

/**
 * @author 14512 on 2018/9/8.
 */
public class LinearList<E> {

    private Object[] elements;
    /**
     * 空间长度
     */
    private int mSize = 10;
    /**
     * 数据个数
     */
    private int mLength = 0;

    public LinearList() {
        elements = new Object[mSize];
    }

    public LinearList(int initSize) {
        mSize = initSize;
        elements = new Object[mSize];
    }

    public void destroy() {
        elements = null;
    }

    public void clear() {
        for (int i = 0; i < mLength; i++) {
            elements[i] = null;
        }
    }

    public boolean isEmpty() {
        return mLength == 0;
    }

    public int  size() {
        return mLength;
    }

    public int length() {
        return mSize;
    }

    public E get(int index) {
        return (E) elements[index];
    }

    public int locate(E currentElem) {
        for (int i = 0; i < mLength; i++) {
            Object element = elements[i];
            if (currentElem == element) {
                return i;
            }
        }
        return -1;
    }

    public E prior(E currentElem) {
        int index = locate(currentElem);
        if (index == -1 || index - 1 == -1) {
            return null;
        }
        return (E) elements[index - 1];
    }

    public E next(E currentElem) {
        int index = locate(currentElem);
        if (index == -1 || index + 1 > mSize) {
            return null;
        }
        return (E) elements[index + 1];
    }

    public boolean insert(E elem) {
        return insertIndex(elem, mLength);
    }

    public boolean insert(E elem, int position) {
        return insertIndex(elem, position - 1);

    }

    /**
     *
     * @param elem
     * @param position start with 1
     * @return
     */
    public boolean replace(E elem, int position) {
        return insertIndex(elem, position - 1);
    }

    /**
     *
     * @param elem
     * @param index  start with 0
     * @return
     */
    private boolean insertIndex(E elem, int index) {
        if (index < 0 || index > mLength) {
            return false;
        }
        if (mSize <= mLength + 1) {
            newElements(mSize);
        }
        if (index == mLength) {
            elements[index] = elem;
        } else {
            for (int i = mLength; i > index; i--) {
                elements[i] = elements[i - 1];
            }
            elements[index] = elem;
        }
        mLength++;
        return true;
    }

    public boolean insertList(LinearList<String> list) {
        if (list == null) {
            return false;
        }
        //空间不足时，应该以list分配多少空间来扩展，而不是list使用了多少来扩展
        if (list.mSize > mSize - mLength) {
            newElements(list.mSize);
        }
        //进行数据复制的时候，则以list使用了多少空间来复制
        for (int i = 0; i < list.mLength; i++) {
            elements[mLength] = list.get(i);
            mLength++;
        }
        mSize += list.mLength;
        return true;
    }

    private boolean newElements(int length) {
        int newLength = mSize + length;
        Object[] newElements = new Object[newLength];
        for (int i = 0; i < elements.length; i++) {
            newElements[i] = elements[i];
        }
        elements = null;
        elements = newElements;
        mSize = newLength;
        return true;
    }

    /**
     *
     * @param position start with 1
     * @return
     */
    public boolean delete(int position) {
        return deleteIndex(position - 1);
    }

    /**
     *
     * @param index start with 0
     * @return
     */
    private boolean deleteIndex(int index) {
        if (index < 0 || index >= mLength) {
            return false;
        }
        for (int i = index; i < mLength; i++) {
            elements[i] = elements[i + 1];
        }
        elements[mLength + 1] = null;
        mLength--;
        return true;
    }

    public boolean delete(E elem) {
        return deleteIndex(locate(elem));
    }

    public void reverse() {
        int i = 0, j = mLength - 1;
        while (i != j + 1) {
            if (i == j) {
                break;
            }
            E first = (E) elements[i];
            elements[i] = elements[j];
            elements[j] = first;
            i++;
            j--;
        }
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
