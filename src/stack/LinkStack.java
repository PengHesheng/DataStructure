package stack;

/**
 * 链栈
 * @author 14512 on 2018/9/9.
 */
public class LinkStack<E> {

    private Node<E> mHead;
    private int mLength;

    public LinkStack() {
        mLength = 0;
    }

    public int length() {
        return mLength;
    }

    public E getTop() {
        if (mHead == null) {
            return null;
        }
        return mHead.elem;
    }

    public boolean push(E elem) {
        if (mHead == null) {
            mHead = new Node<>(elem, null);
        } else {
            mHead = new Node<>(elem, mHead);
        }
        mLength++;
        return true;
    }

    public E pop() {
        if (mHead == null) {
            return null;
        }
        Node<E> node = mHead;
        mHead = node.next;
        mLength--;
        return node.elem;
    }


    private static class Node<E> {
        E elem;
        Node<E> next;

        Node(E elem, Node<E> next) {
            this.elem = elem;
            this.next = next;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node<E> node = mHead;
        while (node != null) {
            stringBuilder.append(node.elem).append("  ");
            node = node.next;
        }
        return stringBuilder.toString();
    }
}
