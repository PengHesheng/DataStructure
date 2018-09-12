package tree;

import stack.LinkStack;

import java.util.Objects;

/**
 * 链表二叉树
 * @author 14512 on 2018/9/10.
 */
public class BinaryTree<E> {

    private Node<E> mRoot;
    private int mDepth = 0;

    public BinaryTree(E root) {
        mRoot = new Node<>(root, null, null);
    }

    public E getRoot() {
        return mRoot != null ? mRoot.elem : null;
    }

    /**
     * 目前还没有想出好方法
     * @return
     */
    public int getDepth() {
        return mDepth;
    }

    /**
     * 根据父母结点添加左孩子
     * @param parent
     * @param child
     * @return
     */
    public boolean addLeft(E parent, E child) {
        Node<E> nodeParent = inOrderTraverseNo2(mRoot, parent);
        if (nodeParent == null) {
            return false;
        }
        nodeParent.leftChild = new Node<>(child, null, null);
        return true;
    }

    /**
     * 根据父母结点添加右孩子
     * @param parent
     * @param child
     * @return
     */
    public boolean addRight(E parent, E child) {
        Node<E> nodeParent = inOrderTraverseNo2(mRoot, parent);
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
        return inOrderTraverseNo2(mRoot, elem) != null;
    }

    private Node<E> pre = null;

    private Node<E> inOrderThreading() {
        Node<E> rootThrBin = new Node<>(null, null, null);
        Node<E> rootBinary = mRoot;
        rootThrBin.lTag = Tag.LINK;
        rootThrBin.rTag = Tag.THREAD;
        //右指针回指
        rootThrBin.rightChild = rootThrBin;
        //若二叉树为空，则左指针回指
        if (rootBinary == null) {
            rootThrBin.leftChild = rootThrBin;
        } else {
            rootThrBin.leftChild = rootBinary;
            pre = rootThrBin;
            //中序遍历进行中序线索化
            inThread(rootBinary);
            pre.rightChild = rootThrBin;
            pre.rTag = Tag.THREAD;
            rootThrBin.rightChild = pre;
        }
        return rootThrBin;
    }

    private void inThread(Node<E> parent) {
        if (parent != null) {
            //左子树线索化

            inThread(parent.leftChild);
            if (parent.leftChild == null) {
                parent.lTag = Tag.THREAD;
                parent.leftChild = pre;
            }
            if (pre.rightChild == null) {
                pre.rTag = Tag.THREAD;
                pre.rightChild = parent;
            }
            pre = parent;
            inThread(parent.rightChild);
        }
    }

    public void inOrderTraverse() {
        Node<E> root = inOrderThreading();
        Node<E> p = root.leftChild;
        while (p != root) {
            while (p.lTag == Tag.LINK) {
                p = p.leftChild;
            }
            System.out.print(p.elem + "  ");
            while (p.rTag == Tag.THREAD && p.rightChild != root) {
                p = p.rightChild;
                System.out.print(p.elem + "  ");
            }
            p = p.rightChild;
        }
    }

    /**
     * 二叉树的遍历，代码中打印结点的位置即可操作该节点
     */
    public void traverse() {
        System.out.println("pre");
        preOrderTraverse(mRoot);
        System.out.println("\nin");
        inOrderTraverse(mRoot);
        System.out.println("\npost");
        postOrderTraverse(mRoot);
        System.out.println("\npreNo");
        preOrderTraverseNo1(mRoot);
        System.out.println("\ninNo1");
        inOrderTraverseNo1(mRoot);
//        System.out.println("\ninNo2");
//        inOrderTraverseNo2(mRoot);
        System.out.println("\npostNo");
        postOrderTraverseNo1(mRoot);
    }

    /**
     * 后序遍历的非递归实现
     * @param root
     */
    private void postOrderTraverseNo1(Node<E> root) {
        if (root == null) {
            return;
        }
        LinkStack<Node<E>> stack = new LinkStack<>();
        Node<E> parent = root;
        //用于标识已经上一个访问的结点
        Node<E> preNode = null;
        while (parent != null || !stack.empty()) {
            if (parent != null) {
                stack.push(parent);
                parent = parent.leftChild;
            } else {
                parent = stack.getTop();
                //当前结点没有右孩子或者当前结点是上一个访问的，则出栈
                if (parent.rightChild == null || preNode == parent.rightChild) {
                    preNode = stack.pop();
                    System.out.print(preNode.elem + "  ");
                    parent = null;
                } else {
                    parent = parent.rightChild;
                }

            }
        }
    }

    /**
     * 先序遍历，非递归实现
     * @param root
     */
    private void preOrderTraverseNo1(Node<E> root) {
        if (root == null) {
            return;
        }
        LinkStack<Node<E>> stack = new LinkStack<>();
        Node<E> parent = root;
        //当右子树为null和栈为空栈同时成立，代表遍历完成
        while (parent != null || !stack.empty()) {
            if (parent != null) {
                System.out.print(parent.elem + "  ");
                stack.push(parent);
                parent = parent.leftChild;
            } else {
                parent = stack.pop();
                parent = parent.rightChild;
            }
        }
    }

    /**
     * 中序遍历的非递归算法2
     * 此处用来寻找二叉树中得某一个结点
     * @param root
     */
    private Node<E> inOrderTraverseNo2(Node<E> root, E elem) {
        if (root == null || elem == null) {
            return null;
        }
        Node<E> parent = root;
        LinkStack<Node<E>> stack = new LinkStack<>();
        while (parent != null || !stack.empty()) {
            if (parent != null) {
                stack.push(parent);
                parent = parent.leftChild;
            } else {
                parent = stack.pop();
                if (parent.elem == elem) {
                    return parent;
                }
//                System.out.print(parent.elem + "  ");
                parent = parent.rightChild;
            }
        }
        return null;
    }

    /**
     * 中序遍历的非递归实现，
     * 1和2的区别仅在于遍历结束的条件是不同的判定方法
     * 先序和后序都可以采用类似的方法实现非递归遍历
     * @param root
     */
    private void inOrderTraverseNo1(Node<E> root) {
        if (root == null) {
            return;
        }
        LinkStack<Node<E>> stack = new LinkStack<>();
        stack.push(root);
        while (!stack.empty()) {
            Node<E> node = stack.getTop();
            while (node != null) {
                stack.push(node.leftChild);
                node = node.leftChild;
            }
            //空指针退栈
            stack.pop();
            if (!stack.empty()) {
                node = stack.pop();
                System.out.print(node.elem + "  ");
                stack.push(node.rightChild);
            }
        }
    }

    /**
     * 后序遍历，递归实现
     * @param parent
     */
    private boolean postOrderTraverse(Node<E> parent) {
        if (parent != null) {
            if (postOrderTraverse(parent.leftChild)) {
                if (postOrderTraverse(parent.rightChild)) {
                    System.out.print(parent.elem + "  ");
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 中序遍历，递归实现
     * @param parent
     */
    private boolean inOrderTraverse(Node<E> parent) {
        if (parent != null) {
            if (inOrderTraverse(parent.leftChild)) {
                System.out.print(parent.elem + "  ");
                if (inOrderTraverse(parent.rightChild)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 先序遍历，递归实现
     * @param parent
     */
    private boolean preOrderTraverse(Node<E> parent) {
        if (parent != null) {
            System.out.print(parent.elem + "  ");
            if (preOrderTraverse(parent.leftChild)) {
                if (preOrderTraverse(parent.rightChild)) {
                    return true;
                }
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 更新值
     * @param oldElem
     * @param newElem
     * @return
     */
    public boolean update(E oldElem, E newElem) {
        Node<E> node = inOrderTraverseNo2(mRoot, oldElem);
        if (node == null) {
            return false;
        }
        node.elem = newElem;
        return true;
    }

    /**
     * 递归查找elem结点
     * @param parent 父结点
     * @param elem 元素
     * @return
     */
    private Node<E> find(Node<E> parent, E elem) {
        Node<E> node;
        if (parent == null) {
            return null;
        }
        if (Objects.equals(parent.elem, elem)) {
            return parent;
        }
        node = find(parent.leftChild, elem);
        if (node != null) {
            return node;
        }
        node = find(parent.rightChild, elem);
        if (node != null) {
            return node;
        }
        return null;
    }

    private static class Node<E> {
        E elem;
        Node<E> leftChild;
        Node<E> rightChild;
        Tag lTag = Tag.LINK;
        Tag rTag = Tag.LINK;
        Node(E elem, Node<E> leftChild, Node<E> rightChild) {
            this.elem = elem;
            this.leftChild = leftChild;
            this.rightChild = rightChild;
        }
    }

    private enum Tag {
        /**
         * 表示结点，指向孩子结点
         */
        LINK,
        /**
         * 表示线索，指向前驱
         */
        THREAD
    }
}
