package tree;

/**
 * 树
 * 数组第一个存放跟结点
 * @author 14512 on 2018/9/17.
 */
public class Tree<E> {

    private ParentNode[] parents;
    private int mSize;
    private int mLength;
    private static int FIND_NOT = -2;

    public Tree() {
        initDefaultTree();
    }

    public Tree(int size) {
        mSize = size;
        initTree();
    }

    public Tree(E root) {
        initDefaultTree();
        parents[0] = new ParentNode<>(-1, root, null);
        mLength++;
    }

    private void initDefaultTree() {
        mSize = 10;
        initTree();
    }

    private void initTree() {
        parents = new ParentNode[mSize];
        mLength = 0;
    }

    /**
     * 添加根结点
     * @param root
     * @return
     */
    public boolean addRoot(E root) {
        if (isRootExit()) {
            return false;
        }
        parents[0] = new ParentNode<>(-1, root, null);
        return true;
    }

    private boolean isRootExit() {
        return parents[0] == null;
    }

    /**
     * 根结点已经存在，添加结点
     * @param elements
     * @return
     */
    public boolean addNodes(E[] elements) {
        if (isRootExit()) {
            return false;
        }
        if (mSize < elements.length) {
            addNewSize(elements.length);
        }
        for (E element : elements) {
            parents[mLength++] = new ParentNode<>(-1, element, null);
        }
        return true;
    }

    private void addNewSize(int size) {
        int newSize = mSize + size;
        ParentNode[] newParents = new ParentNode[newSize];
        System.arraycopy(parents, 0, newParents, 0, mLength);
        parents = newParents;
        mSize = newSize;
    }

    public boolean addNewNode(E elem) {
        if (isRootExit()) {
            return false;
        }
        parents[mLength++] = new ParentNode<>(-1, elem, null);
        return true;
    }

    /**
     * 初始化结点间的关系
     * @param parent isExited
     * @param child
     * @return
     */
    public boolean initNode(E parent, E child) {
        int pIndex = find(parent);
        if (pIndex == FIND_NOT) {
            return false;
        }
        int cIndex = find(child);
        if (cIndex == FIND_NOT) {
            parents[mLength] = new ParentNode<>(pIndex, child, null);
            addChildNode(pIndex, mLength);
            mLength++;
            return true;
        }
        parents[cIndex].pIndex = pIndex;
        addChildNode(pIndex, cIndex);
        return true;
    }

    /**
     * 将树转换为二叉树
     * @return
     */
    public BinaryTree<E> convertToBinaryTree() {
        if (isRootExit()) {
            return null;
        }
        E root = (E) parents[0].elem;
        BinaryTree<E> binaryTree = new BinaryTree<>(root);
        traverseAdd(binaryTree, parents[0]);
        return binaryTree;
    }

    /**
     * 根据树的递归遍历，将树转换为二叉树
     * @param binaryTree 二叉树
     * @param parent 父结点
     */
    private void traverseAdd(BinaryTree<E> binaryTree, ParentNode parent) {
        boolean isFirstChild = true;
        ChildNode firstChild = parent.firstChild;
        ParentNode oldChild = null;
        while (firstChild != null) {
            ParentNode child = parents[firstChild.cIndex];
            if (isFirstChild) {
                binaryTree.addLeft((E) parent.elem, (E) child.elem);
                isFirstChild = false;
            } else {
                binaryTree.addRight((E) oldChild.elem, (E) child.elem);
            }
            oldChild = child;
            traverseAdd(binaryTree, child);
            firstChild = firstChild.next;
        }
    }

    public void preTraverse() {
        preTraverse(parents[0]);
        System.out.println();
        postTraverse(parents[0]);
    }

    /**
     * 后根序遍历
     * @param parent
     */
    private void postTraverse(ParentNode parent) {
        ChildNode firstChild = parent.firstChild;
        while (firstChild != null) {
            postTraverse(parents[firstChild.cIndex]);
            firstChild = firstChild.next;
        }
        System.out.print(parent.elem + "  ");
    }

    /**
     * 使用递归遍历，先序遍历父结点，接着遍历孩子结点
     * @param parent
     */
    private void preTraverse(ParentNode parent) {
        System.out.print(parent.elem + "  ");
        ChildNode firstChild = parent.firstChild;
        while (firstChild != null) {
            preTraverse(parents[firstChild.cIndex]);
            firstChild = firstChild.next;
        }
    }

    private void addChildNode(int pIndex, int cIndex) {
        ChildNode first = parents[pIndex].firstChild;
        if (first == null) {
            first = new ChildNode(cIndex, null);
            parents[pIndex].firstChild = first;
        } else {
            while (first.next != null) {
                first = first.next;
            }
            first.next = new ChildNode(cIndex, null);
        }
    }

    private int find(E elem) {
        for (int i = 0; i < mLength; i++) {
            if (parents[i].elem == elem) {
                return i;
            }
        }
        return FIND_NOT;
    }

    private static class ParentNode<E> {
        /**
         * 父结点的下标
         */
        int pIndex;
        E elem;
        ChildNode firstChild;
        ParentNode(int pIndex, E elem, ChildNode firstChild) {
            this.pIndex = pIndex;
            this.elem = elem;
            this.firstChild = firstChild;
        }
    }

    private static class ChildNode {
        /**
         * 孩子结点的下标
         */
        int cIndex;
        ChildNode next;
        ChildNode(int cIndex, ChildNode next) {
            this.cIndex = cIndex;
            this.next = next;
        }
    }
}
