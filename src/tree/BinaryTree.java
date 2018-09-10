package tree;

/**
 * @author 14512 on 2018/9/10.
 */
public class BinaryTree<E> {

    private Node<E> mRoot;

    public BinaryTree(E root) {
        mRoot = new Node<>(root, null, null);
    }

    public boolean addLeft(E parent, E child) {
        Node<E> nodeParent = find(mRoot, parent);
        if (nodeParent == null) {
            return false;
        }
        nodeParent.leftChild = new Node<>(child, null, null);
        return true;
    }

    public boolean addRight(E parent, E child) {
        Node<E> nodeParent = find(mRoot, parent);
        if (nodeParent == null) {
            return false;
        }
        nodeParent.rightChild = new Node<>(child, null, null);
        return true;
    }

    /**
     * 查找某个元素是否在树内
     * @param elem 元素
     * @return
     */
    public boolean find(E elem) {
        return find(mRoot, elem) != null;
    }

    public void traverse() {

    }

    public boolean update(E oldElem, E newElem) {
        Node<E> node = find(mRoot, oldElem);
        if (node == null) {
            return false;
        }
        node.elem = newElem;
        return true;
    }

    /**
     * 递归查找elem结点
     * @param parent 父节点
     * @param elem 元素
     * @return
     */
    private Node<E> find(Node<E> parent, E elem) {
        if (parent.elem == null) {
            return null;
        }
        if (parent.elem == elem) {
            return parent;
        }
        find(parent.leftChild, elem);
        find(parent.rightChild, elem);
        return null;
    }

    private static class Node<E> {
        E elem;
        Node<E> leftChild;
        Node<E> rightChild;
        Node(E elem, Node<E> leftChild, Node<E> rightChild) {
            this.elem = elem;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }
}
