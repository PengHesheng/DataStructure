package queue;

/**
 * 链队列
 * @author 14512 on 2018/9/10.
 */
public class LinkQueue<E> {

    public static final int FLAG_LINK_QUEUE = 1;
    /**
     * 标识循环队列
     */
    public static final int FLAG_CYCLE_QUEUE = 2;

    /**
     * 标志位，元素个数
     */
    private int mLength;
    /**
     * 队列的大小
     */
    private int mSize;
    private Node<E> mHead = null;
    private Node<E> mLast = null;
    private int mFlag;

    public LinkQueue(int flag, int size) {
        if (flag == FLAG_CYCLE_QUEUE) {
            mSize = size;
        }
        mFlag = flag;
        mLength = 0;
    }

    public LinkQueue(int flag) {
        if (flag == FLAG_CYCLE_QUEUE) {
            mSize = 5;
        }
        mFlag = flag;
        mLength = 0;
    }

    /**
     * 采用尾插法，入队
     * @param elem
     * @return
     */
    public boolean enqueue(E elem) {
        if (mFlag == FLAG_CYCLE_QUEUE && mLength == mSize) {
            //队满
            return false;
        }
        if (mHead == null) {
            return insertHead(elem);
        }
        Node<E> node = new Node<>(elem, null);
        mLast.next = node;
        mLast = node;
        mLength++;
        return true;
    }

    public E dequeue() {
        if (mHead == null) {
            //队空
            return null;
        }
        Node<E> node = mHead;
        mHead = mHead.next;
        mLength--;
        return node.elem;
    }

    public E getHead() {
        if (mHead == null) {
            return null;
        }
        return mHead.elem;
    }

    public int length() {
        return mLength;
    }

    private boolean insertHead(E elem) {
        mHead = new Node<>(elem, null);
        mLast = mHead;
        mLength++;
        return true;
    }

    private static class Node<E> {
        E elem;
        Node<E> next;
        Node(E elem, Node<E> next) {
            this.elem = elem;
            this.next = next;
        }
    }
}
