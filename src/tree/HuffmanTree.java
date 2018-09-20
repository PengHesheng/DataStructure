package tree;

/**
 * 哈夫曼树（最优二叉树）
 * @author 14512 on 2018/9/18.
 */
public class HuffmanTree<E> {

    /**
     * 最大权重值
     */
    private static int MAX_WEIGHT = Integer.MAX_VALUE;
    private Node[] mNodes;
    private int mLength;

    /**
     * 构建哈夫曼树
     * @param elements  元素
     * @param weights  元素权重
     * @param n 元素个数
     */
    public HuffmanTree(E[] elements, int[] weights, int n) {
        if (n <= 1) {
            return;
        }
        mLength = n;
        int m = 2 * n - 1;
        mNodes = new Node[m];
        for (int i = 0; i < n; i++) {
            mNodes[i] = new Node<>(elements[i], weights[i], 0, 0, 0);
        }

        for (int i = n; i < m ; i++) {
            int[] sIndex = selectSmall(i - 1);
            mNodes[sIndex[0]].parent = i;
            mNodes[sIndex[1]].parent = i;
            //保证有元素的结点（非新增结点）在左子树
            if (mNodes[sIndex[0]].elem != null) {
                mNodes[i] = new Node<>(null, sIndex[2], 0, sIndex[0], sIndex[1]);
            } else {
                mNodes[i] = new Node<>(null, sIndex[2], 0, sIndex[1], sIndex[0]);
            }
        }
    }

    /**
     * 进行编码
     */
    public void huffmanCoding() {
        char[] code = new char[mLength];
        for (int i = 0; i < mLength; i++) {
            int start = 0;
            int c = i, p = mNodes[i].parent;
            //从叶子向根结点遍历
            while (p == 0) {
                if (mNodes[p].lChild == c) {
                    //编码逆序存储
                    code[start++] = '0';
                } else {
                    code[start++] = '1';
                }
                c = p;
                p = mNodes[p].parent;
            }
            mNodes[i].initCode(code, start);
        }

    }

    /**
     * 遍历输出编码
     */
    public void traverse() {
        for (int i = 0; i < mLength; i++) {
            char[] code = mNodes[i].code;
            System.out.print(mNodes[i].elem + "  ");
            for (int j = 0; j < code.length; j++) {
                System.out.print(code[j]);
            }
            System.out.println();
        }
    }

    /**
     * 选择权重最小两个结点，且parent==0（从未访问过的）
     * @param index
     * @return
     */
    private int[] selectSmall(int index) {
        int[] sIndex = new int[3];
        int s1 = MAX_WEIGHT;
        for (int j = 0; j <= index; j++) {
            if (mNodes[j].parent == 0 && mNodes[j].weight < s1) {
                s1 = mNodes[j].weight;
                sIndex[0] = j;
            }
        }
        int s2 = MAX_WEIGHT;
        for (int j = 0; j <= index; j++) {
            if (mNodes[j].parent == 0 && mNodes[j].weight != s1 && mNodes[j].weight < s2) {
                s2 = mNodes[j].weight;
                sIndex[1] = j;
            }
        }
        //第三个表示权重和
        sIndex[2] = s1 + s2;
        return sIndex;
    }

    private static class Node<E> {
        E elem;
        int weight;
        int parent, lChild, rChild;
        char[] code;

        Node(E elem, int weight, int parent, int lchild, int rchild) {
            this.elem = elem;
            this.weight = weight;
            this.parent = parent;
            this.lChild = lchild;
            this.rChild = rchild;
        }

        void initCode(char[] code, int n) {
            this.code = new char[n];
            for (int i = 0; i < n; i++) {
                this.code[i] = code[n - 1 - i];
            }
        }
    }
}
