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

    public boolean addRoot(E root) {
        if (isRootExit()) {
            return false;
        }
        parents[0] = new ParentNode<>(-1, root, null);
        return true;
    }

    private boolean isRootExit() {
        return parents[0] != null;
    }

    /**
     * 根结点已经存在
     * @param elements
     * @return
     */
    public boolean addNodes(E[] elements) {
        if (isRootExit()) {
            return false;
        }
        for (E element : elements) {
            parents[mLength++] = new ParentNode<>(-1, element, null);
        }
        return true;
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

    private void addChildNode(int pIndex, int cIndex) {
        ChildNode first = parents[pIndex].firstChild;
        if (first == null) {
            first = new ChildNode(cIndex, null);
        } else {
            first = new ChildNode(cIndex, first);
        }
        parents[pIndex].firstChild = first;
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
