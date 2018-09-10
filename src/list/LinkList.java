package list;

import java.util.Objects;

/**
 * 链表，头结点有值
 * @author 14512 on 2018/9/9.
 */
public class LinkList<E> {

    public static final int FLAG_ONE_LIST = 1;
    public static final int FLAG_DOUBLE_LIST = 2;

    /**
     * 数据个数
     */
    private int mLength;
    private Node<E> mHead;
    private Node<E> mLast;
    private int mFlag;

    public LinkList(int flag) {
        mFlag = flag;
        mLength = 0;
    }

    public int length() {
        return mLength;
    }

    /**
     * @param position start with 1
     * @return
     */
    public E get(int position) {
        if (checkBorder(position)) {
            return null;
        } else {
            return getNode(position).elem;
        }
    }

    public E getHead() {
        return mHead == null ? null : mHead.elem;
    }

    public E getLast() {
        return mLast == null ? null : mLast.elem;
    }

    public E next(E elem) {
        int position = locate(elem);
        if (position == -1) {
            return null;
        }
        Node<E> node = getNode(position);
        return node.elem;
    }


    /**
     * 获取position位置的Node，position已经经过安全检查
     * @param position
     * @return
     */
    private Node<E> getNode(int position) {
        Node<E> node = mHead;
        int count = 1;
        while (count < position) {
            node = node.next;
            count++;
        }
        return node;
    }

    /**
     * 寻找elem的位置
     * @param elem
     * @return
     */
    public int locate(E elem) {
        Node<E> node = mHead;
        int position = 1;
        while (node != null && position <= mLength) {
            if (Objects.equals(elem, node.elem)) {
                return position;
            }
            node = node.next;
            position++;
        }
        return -1;
    }

    /**
     * 添加到指定位置
     * @param elem
     * @param position
     * @return
     */
    public boolean insert(E elem, int position) {
        if (checkBorder(position)) {
            return false;
        }
        if (mLength == 0) {
            return insertHeader(elem);
        }

        if (mFlag == FLAG_ONE_LIST) {
            return insertOneList(elem, position);
        } else if (mFlag == FLAG_DOUBLE_LIST) {
            return insertDoubleList(elem, position);
        }
        return false;
    }

    private boolean insertDoubleList(E elem, int position) {
        Node<E> node = getNode(position);
        //双向链表
        node.pre.next = new Node<>(node.pre, elem, node);
        mLength++;
        return true;
    }

    private boolean insertOneList(E elem, int position) {
        Node<E> node;
        if (position == 1) {
            //头插法
            return insertHead(elem);
        }
        //单向链表获取前一个Node需要考虑头结点
        node = getNode(position - 1);
        //单向链表
        node.next = new Node<>(node, elem, node.next);
        mLength++;
        return true;
    }

    private boolean insertHeader(E elem) {
        mHead = new Node<>(null, elem, null);
        mLast = mHead;
        mLength++;
        return true;
    }

    /**
     * 尾插法
     * @param elem
     * @return
     */
    public boolean insertLast(E elem) {
        if (mLength == 0) {
            return insertHeader(elem);
        }
        Node<E> node = getNode(mLength);
        Node<E> newNode = new Node<>(node, elem, null);
        node.next = newNode;
        mLast = newNode;
        mLength++;
        return true;
    }

    /**
     * 头插法
     * @param elem
     * @return
     */
    public boolean insertHead(E elem) {
        Node<E> node = mHead;
        mHead = new Node<>(null, elem, node);
        mLength++;
        return true;
    }

    /**
     * 插入elem在before之前
     * @param elem
     * @param before
     * @return
     */
    public boolean insertBefore(E elem, E before) {
        return insert(elem, locate(before));
    }

    /**
     * 插入elem在after之后
     * @param elem
     * @param after
     * @return
     */
    public boolean insertAfter(E elem, E  after) {
        return insert(elem, locate(after) + 1);
    }

    private boolean checkBorder(int position) {
        return position < 1 && position > mLength;
    }

    /**
     * 删除指定位置的元素
     * @param position
     * @return
     */
    public boolean delete(int position) {
        if (checkBorder(position)) {
            return false;
        }
        if (mFlag == FLAG_ONE_LIST) {
            deleteOneList(position);
        } else if (mFlag == FLAG_DOUBLE_LIST) {
            deleteDoubleList(position);
        }
        mLength--;
        return true;
    }

    private void deleteDoubleList(int position) {
        if (position == 1) {
            mHead = mHead.next;
        } else {
            Node<E> node = getNode(position);
            //双向链表，要找到当前这个
            node.pre.next = node.next;
        }
    }

    private void deleteOneList(int position) {
        //单向链表要考虑头结点
        if (position == 1) {
            mHead = mHead.next;
        } else {
            Node<E> node = getNode(position - 1);
            //单向链表，找到前一个
            node.next = node.next.next;
        }
    }

    public boolean delete(E elem) {
        return delete(locate(elem));
    }

    public boolean update(E oldElem, E newElem) {
        int position = locate(oldElem);
        if (position == -1) {
            return false;
        }
        Node<E> node = getNode(position);
        node.elem = newElem;
        return true;
    }

    /**
     * 翻转
     */
    public void reverse() {
        reverseSingleTrack();
    }

    /**
     * 单向链表翻转
     */
    private void reverseSingleTrack() {
//        reverseNotRecursion();
        mHead = reverseRecursion(mHead);
    }

    /**
     * 递归翻转
     */
    private Node<E> reverseRecursion(Node<E> current) {
        if (current == null || current.next == null) {
            return current;
        }
        Node<E> nextNode = current.next;
        current.next = null;
        Node<E> reverseNode = reverseRecursion(nextNode);
        nextNode.next = current;
        return reverseNode;
    }

    /**
     * 非递归翻转
     */
    private void reverseNotRecursion() {
        Node<E> preNode = mHead;
        Node<E> nextNode = mHead.next;
        preNode.next = null;
        while (nextNode != null) {
            mHead = nextNode;
            nextNode = nextNode.next;
            mHead.next = preNode;
            preNode = mHead;
        }
    }


    private static class Node<E> {
        E elem;
        Node<E> pre;
        Node<E> next;

        Node(Node<E> pre, E elem, Node<E> next) {
            this.elem = elem;
            this.pre = pre;
            this.next = next;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Node<E> node = mHead;
        while (node != null) {
            stringBuilder.append(node.elem.toString()).append("  ");
            node = node.next;
        }
        return stringBuilder.toString();
    }
}
