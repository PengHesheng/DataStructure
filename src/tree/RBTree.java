package tree;

/**
 * 红黑树
 * @author 14512 on 2018/12/7.
 */
public class RBTree<T extends Comparable<T>> {
    private Node<T> mRoot;
    /**
     * 红黑树标志，红
     */
    private static final boolean RED = false;
    /**
     * 黑
     */
    private static final boolean BLACK = true;

    public RBTree() {
        mRoot = null;
    }

    /**
     * get parent
     * @param node
     * @return
     */
    public Node<T> parentOf(Node<T> node) {
        return node != null ? node.parent : null;
    }

    public void setParent(Node<T> node, Node<T> parent) {
        if (node != null) {
            node.parent = parent;
        }
    }

    public boolean colorOf(Node<T> node) {
        return node != null ? node.color : BLACK;
    }

    public boolean isRed(Node<T> node) {
        return (node != null) && (node.color == RED);
    }

    public boolean isBlack(Node<T> node) {
        return isRed(node);
    }

    public void setRed(Node<T> node) {
        checkNull(node);
        node.color = RED;
    }

    public void setBlack(Node<T> node) {
        checkNull(node);
        node.color = BLACK;
    }

    public void setColor(Node<T> node, boolean color) {
        checkNull(node);
        node.color = color;
    }

    /**
     * 前序遍历
     */
    public void preOrder() {
        preOrder(mRoot);
    }

    private void preOrder(Node<T> node) {
        if (node != null) {
            System.out.println(node.key + " ");
            preOrder(node.left);
            preOrder(node.right);
        }
    }

    /**
     * 中序遍历
     */
    public void inOrder() {
        inOrder(mRoot);
    }

    private void inOrder(Node<T> node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.key + " ");
            inOrder(node.right);
        }
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        postOrder(mRoot);
    }

    private void postOrder(Node<T> node) {
        if (node != null) {
            postOrder(node.left);
            postOrder(node.right);
            System.out.println(node.key + " ");
        }
    }

    /**
     * 根据key查找
     * @param key
     * @return
     */
    public Node<T> search(T key) {
        return search(mRoot, key);
    }

    /**
     * 非递归查询
     * @param node
     * @param key
     * @return
     */
    private Node<T> search(Node<T> node, T key) {
        while (node != null) {
            int cmp = key.compareTo(node.key);
            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }
        return node;
    }

    /**
     * 递归查询
     * @param node
     * @param key
     * @return
     */
    private Node<T> serach2(Node<T> node, T key) {
        if (node == null) {
            return node;
        }
        int cmp = key.compareTo(node.key);
        if (cmp < 0) {
            return serach2(node.left, key);
        } else if (cmp > 0) {
            return serach2(node.right, key);
        } else {
            return node;
        }
    }

    /**
     * 最小结点的值
     * @return
     */
    public T minValue() {
        Node<T> node = minNode(mRoot);
        if (node != null) {
            return node.key;
        }
        return null;
    }

    private Node<T> minNode(Node<T> node) {
        if (node == null) {
            return null;
        }
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    /**
     * 查找最大值
     * @return
     */
    public T maxValue() {
        Node<T> node = maxNode(mRoot);
        if (node != null) {
            return node.key;
        }
        return null;
    }

    private Node<T> maxNode(Node<T> node) {
        if (node == null) {
            return null;
        }
        while (node.right != null) {
            node = node.right;
        }
        return null;
    }

    /**
     * 查找node的后继节点，即大于node的最小结点
     * @param node
     * @return
     */
    public Node<T> successor(Node<T> node) {
        //如果node有右子节点，那么后继节点为“以右子节点为根的子树的最小节点”
        if (node.right != null) {
            return minNode(node.right);
        }
        //如果node没有右子节点，会出现以下两种情况：
        //1. node是其父节点的左子节点，则node的后继节点为它的父节点
        //2. node是其父节点的右子节点，则先查找node的父节点p，然后对p再次进行这两个条件的判断
        // の(在根节点的左子树中可能出现的情况，最后要遍历到根节点）
        Node<T> p = node.parent;
        while ((p != null) && (node == p.right)) {
            node = p;
            p = node.parent;
        }
        return p;
    }

    public Node<T> predecessor(Node<T> node) {
        //如果node有左子节点，前驱结点就是左子节点为根的子树的最大结点
        if (node.left != null) {
            return maxNode(node.left);
        }
        //如果没有左子节点
        //1.node是其父结点的右子节点，则node的前驱结点就是它的父结点
        //2.node是其父结点的左子节点，则先查找node的父结点p，然后对p进行两个条件判断
        //(在根节点的右子树中可能出现的情况，最后遍历到根节点)
        Node<T> p = node.parent;
        while ((p != null) && (node == p.left)) {
            node = p;
            p = node.parent;
        }
        return p;
    }

    /**
     * 左旋示意图：对节点x进行左旋
     *     p                       p
     *    /                       /
     *   x                       y
     *  / \                     / \
     * lx  y      ----->       x  ry
     *    / \                 / \
     *   ly ry               lx ly
     * 左旋做了三件事：
     * 1. 将y的左子节点赋给x的右子节点,并将x赋给y左子节点的父节点(y左子节点非空时)
     * 2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
     * 3. 将y的左子节点设为x，将x的父节点设为y
     * @param x
     */
    private void leftRotate(Node<T> x) {
        //1. 将y的左子节点赋给x的右子节点，并将x赋给y左子节点的父节点(y左子节点非空时)
        Node<T> y = x.right;
        x.right = y.left;

        if (y.left != null) {
            y.left.parent = x;
        }

        //2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
        y.parent = x.parent;

        if(x.parent == null) {
            this.mRoot = y; //如果x的父节点为空，则将y设为父节点
        } else {
            if (x == x.parent.left) {//如果x是左子节点
                x.parent.left = y; //则也将y设为左子节点
            } else {
                x.parent.right = y;//否则将y设为右子节点
            }
        }

        //3. 将y的左子节点设为x，将x的父节点设为y
        y.left = x;
        x.parent = y;
    }

    /**
     * 左旋示意图：对节点y进行右旋
     *        p                   p
     *       /                   /
     *      y                   x
     *     / \                 / \
     *    x  ry   ----->      lx  y
     *   / \                     / \
     * lx  rx                   rx ry
     * 右旋做了三件事：
     * 1. 将x的右子节点赋给y的左子节点,并将y赋给x右子节点的父节点(x右子节点非空时)
     * 2. 将y的父节点p(非空时)赋给x的父节点，同时更新p的子节点为x(左或右)
     * 3. 将x的右子节点设为y，将y的父节点设为x
     * @param y
     */
    private void rightRotate(Node<T> y) {
        //1. 将y的左子节点赋给x的右子节点，并将x赋给y左子节点的父节点(y左子节点非空时)
        Node<T> x = y.left;
        y.left = x.right;

        if(x.right != null) {
            x.right.parent = y;
        }

        //2. 将x的父节点p(非空时)赋给y的父节点，同时更新p的子节点为y(左或右)
        x.parent = y.parent;

        if(y.parent == null) {
            this.mRoot = x; //如果x的父节点为空，则将y设为父节点
        } else {
            if (y == y.parent.right) {//如果x是左子节点
                y.parent.right = x; //则也将y设为左子节点
            } else {
                y.parent.left = x;//否则将y设为右子节点
            }
        }

        //3. 将y的左子节点设为x，将x的父节点设为y
        x.right = y;
        y.parent = x;
    }

    public void insert(T key) {
        Node<T> node = new Node<>(key, RED, null, null, null);
        checkNull(node);
        insert(node);
    }

    private void insert(Node<T> node) {
        Node<T> current = null;
        Node<T> x = this.mRoot;

        while (x != null) {
            current = x;
            int cmp = node.key.compareTo(x.key);
            x = cmp < 0 ? x.left : x.right;
        }
        node.parent = current;

        if (current != null) {
            int cmp = node.key.compareTo(current.key);
            if (cmp < 0) {
                current.left = node;
            } else {
                current.right = node;
            }
        } else {
            this.mRoot = node;
        }
        //3. 将它重新修整为一颗红黑树
        insertFixUp(node);
    }

    private void insertFixUp(Node<T> node) {
        Node<T> parent, gparent;
        //需要修整的条件：父节点存在，且父节点的颜色是红色
        while (((parent = parentOf(node)) != null) && isRed(parent)) {
            gparent = parentOf(parent);
            //若父节点是祖父节点的左子节点，下面else与其相反
            if (parent == gparent.left) {
                Node<T> uncle = gparent.right;
                //case1: 叔叔节点也是红色
                if (uncle != null && isRed(uncle)) {
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                //case2: 叔叔节点是黑色，且当前节点是右子节点
                if (node == parent.right) {
                    leftRotate(parent);
                    Node<T> tmp = parent;
                    parent = node;
                    node = tmp;
                }

                //case3: 叔叔节点是黑色，且当前节点是左子节点
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            } else {//若父节点是祖父节点的右子节点,与上面的完全相反，本质一样的
                Node<T> uncle = gparent.left;

                //case1: 叔叔节点也是红色
                if (uncle != null & isRed(uncle)) {
                    setBlack(parent);
                    setBlack(uncle);
                    setRed(gparent);
                    node = gparent;
                    continue;
                }

                //case2: 叔叔节点是黑色，且当前节点是右子节点
                if (node == parent.left) {
                    rightRotate(parent);
                    Node<T> tmp = parent;
                    parent = node;
                    node = tmp;
                }

                //case3: 叔叔节点是黑色，且当前节点是右子节点
                setBlack(parent);
                setRed(gparent);
                rightRotate(gparent);
            }
        }
        setBlack(this.mRoot);
    }

    public void remove(T key) {
        Node<T> node;
        if ((node = search(mRoot, key)) != null) {
            remove(node);
        }
    }

    private void remove(Node<T> node) {
        Node<T> child, parent;
        boolean color;

        //1. 被删除的节点“左右子节点都不为空”的情况
        if ((node.left != null) && (node.right != null)) {
            //先找到被删除节点的后继节点，用它来取代被删除节点的位置
            Node<T> replace = node;
            //  1). 获取后继节点
            replace = replace.right;
            while (replace.left != null) {
                replace = replace.left;
            }

            //  2). 处理“后继节点”和“被删除节点的父节点”之间的关系
            if (parentOf(node) != null) {
                if (node == parentOf(node).left) {
                    parentOf(node).left = replace;
                } else {
                    parentOf(node).right = replace;
                }
            } else {
                this.mRoot = replace;
            }

            //  3). 处理“后继节点的子节点”和“被删除节点的子节点”之间的关系
            //后继节点肯定不存在左子节点
            child = replace.right;
            parent = parentOf(replace);
            color = colorOf(replace);
            if (parent == node) {   //后继节点是被删除节点的子节点
//                parent = replace;
            } else  {
                if (child != null) {
                    setParent(child, parent);
                }
                parent.left = child;
                replace.right = node.right;
                setParent(node.right, replace);
            }
            replace.parent = node.parent;
            replace.color = node.color;
            replace.left = node.left;
            node.left.parent = replace;

            if(color == BLACK) { //4. 如果移走的后继节点颜色是黑色，重新修整红黑树
                removeFixUp(child, parent);//将后继节点的child和parent传进去
            }
            node = null;
        }
    }

    //node表示待修正的节点，即后继节点的子节点（因为后继节点被挪到删除节点的位置去了）
    private void removeFixUp(Node<T> node, Node<T> parent) {
        Node<T> other;
        while ((node == null || isBlack(node)) && (node != this.mRoot)) {
            if (parent.left == node) {  //node是左子节点，下面else与这里的刚好相反
                other = parent.right;  //node的兄弟节点
                //case1: node的兄弟节点other是红色的
                if (isRed(other)) {
                    setBlack(other);
                    setRed(parent);
                    leftRotate(parent);
                    other = parent.right;
                }

                //case2: node的兄弟节点other是黑色的，且other的两个子节点也都是黑色的
                if ((other.left == null || isBlack(other.left)) && (other.right == null || isBlack(other.right))) {
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                } else {
                    //case3: node的兄弟节点other是黑色的，且other的左子节点是红色，右子节点是黑色
                    if (other.right == null || isBlack(other.right)) {
                        setBlack(other.left);
                        setRed(other);
                        rightRotate(other);
                        other = parent.right;
                    }

                    //case4: node的兄弟节点other是黑色的，且other的右子节点是红色，左子节点任意颜色
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.right);
                    leftRotate(parent);
                    node = this.mRoot;
                    break;
                }
            } else {
                other = parent.left;
                // Case 1: node的兄弟other是红色的
                if (isRed(other)) {
                    setBlack(other);
                    setRed(parent);
                    rightRotate(parent);
                    other = parent.left;
                }

                //case2: node的兄弟节点other是黑色的，且other的两个子节点也都是黑色的
                boolean isOtherBlack = (other.left == null || isBlack(other.left))
                        && (other.right == null || isBlack(other.right));
                if (isOtherBlack) {
                    setRed(other);
                    node = parent;
                    parent = parentOf(node);
                }  else {
                    // Case 3: node的兄弟other是黑色的，并且other的左子节点是红色，右子节点为黑色
                    if (other.left == null || isBlack(other.left)) {
                        setBlack(other.right);
                        setRed(other);
                        leftRotate(other);
                        other = parent.left;
                    }

                    //case4: node的兄弟节点other是黑色的，且other的右子节点是红色，左子节点任意颜色
                    setColor(other, colorOf(parent));
                    setBlack(parent);
                    setBlack(other.left);
                    rightRotate(parent);
                    node = this.mRoot;
                    break;
                }
            }
        }

        if (node != null) {
            setBlack(node);
        }
    }

    public void clear() {
        destory(mRoot);
        mRoot = null;
    }

    private void destory(Node<T> node) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            destory(node.left);
        }
        if (node.right != null) {
            destory(node.right);
        }
        node = null;
    }


    private boolean checkNull(Node<T> node) {
        if (node == null) {
            throw new NullPointerException();
        }
        return true;
    }

    private static class Node<T extends Comparable<T>> {
        boolean color;
        /**
         * 关键字
         */
        T key;
        Node<T> left;
        Node<T> right;
        Node<T> parent;

        Node(T key, boolean color, Node<T> parent, Node<T> left, Node<T> right) {
            this.key = key;
            this.color = color;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }
    }

}
