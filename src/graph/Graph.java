package graph;


import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @author 14512 on 2018/9/20.
 */
public class Graph<E> {

    private int[][] mEdges;
    private Node[] mNodes;
    /**
     * 没有访问的节点（队列）
     * */
    private Queue<Node<E>> unVisited;

    public Graph(E[] elements, int[][] edges, Type type) {
        mNodes = new Node[elements.length];
        mEdges = edges;
        checkGraph(type);
        unVisited = new PriorityQueue<>();
        for (int i = 0; i < elements.length; i++) {
            Node<E> node = new Node<>(elements[i], null);
            unVisited.add(node);
            mNodes[i] = node;
        }

    }

    /**
     * 搜索各顶点最短路径
     * */
    private void searchMinPath() {
        while (!unVisited.isEmpty()) {
            Node<E> node = unVisited.element();
            //顶点已计算出路径，设置为‘已访问’
            node.isMarked = true;
            //获取所有未访问的邻接点
            Node[] neighbors = getNeighbors(node, false);
            //更新邻居的最短路径
            updatesDistance(node, neighbors);
            //从未访问的节点集合中删除已找到最短路径的节点
            unVisited.poll();
        }
        System.out.println("search over");
    }

    /**
     * 打印最短路径
     * @param firstNode 首节点
     **/
    public void printMinPath(E firstNode) {
        searchMinPath();
        for (Node node : mNodes) {
            E precursor = (E) node.precursor;
            StringBuilder path = new StringBuilder(node.elem + ">-");
            if (precursor == null || Objects.equals(precursor, firstNode)) {
                path.append(firstNode);
            } else  {
                for (int i = 0; i < mNodes.length; i++) {
                    Node node1 = mNodes[i];
                    if (node1.elem.equals(precursor)) {
                        path = path.append(precursor).append(">-");
                        precursor = (E) node1.precursor;
                        i = 0;
                    }
                    if (precursor == null || precursor.equals(firstNode)) {
                        break;
                    }
                }
                path.append(firstNode);
            }
            path.reverse();
            System.out.println(path + "\t距离:" + node.minPath);
        }
    }

    /**
     * 更新所有邻接的最短路径
     * @param node 源节点
     * @param neighbors 所有邻接点
     * */
    private void updatesDistance(Node<E> node, Node[] neighbors) {
        for (Node neighbor : neighbors) {
            updateDistance(node, neighbor);
        }
    }

    /**
     * 更新顶点到邻接的节点的最短路径
     * @param node 源节点
     * @param neighbor 邻接点
     * */
    private boolean updateDistance(Node<E> node, Node neighbor) {
        int distance = getDistance(node, neighbor);
        if (distance == -1) {
            return false;
        }
        distance += node.minPath;
        if (distance < neighbor.minPath) {
            neighbor.minPath = distance;
            neighbor.precursor = node.elem;
        }
        return true;
    }

    /**
     * 获取源节点到目标节点的距离
     * @param source 源节点
     * @param destination 目标节点
     * @return int 权重
     * */
    private int getDistance(Node<E> source, Node destination) {
        int sourceIndex = find(source);
        int destIndex = find(destination);
        if (sourceIndex < 0 || destIndex < 0) {
            return -1;
        }
        return mEdges[sourceIndex][destIndex];
    }

    private int find(Node node) {
        for (int i = 0; i < mNodes.length; i++) {
            if (node == mNodes[i]) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 获取顶点所有邻居
     * @param node 源节点
     * @param marked 是否需要获取所有的邻接点
     * @return List<Node>
     * */
    private Node[] getNeighbors(Node<E> node, boolean marked) {
        Node[] neighbors = new Node[mNodes.length];
        int position = find(node);
        Node neighbor;
        int distance;
        int j = 0;
        for (int i = 0; i < mNodes.length; i++) {
            if (i == position) {
                //顶点本身，跳过
                continue;
            }
            //到所有顶点的距离
            distance = mEdges[position][i];
            if (distance < Integer.MAX_VALUE) {
                //是邻居(有路径可达)
                neighbor = mNodes[i];
                //根据传入的参数来判别是否获取所有的邻接点
                if (marked) {
                    neighbors[j] = neighbor;
                    j++;
                } else {
                    if (!neighbor.isMarked) {
                        //如果邻居没有访问过，则加入list;
                        neighbors[j] = neighbor;
                        j++;
                    }
                }

            }
        }
        return neighbors;
    }

    private void checkGraph(Type type) {
        if (mNodes.length != mEdges.length) {
            error();
        }
        switch (type) {
            case DG:
                break;
            case DN:
                break;
            case UDG:
                break;
            case UDN:
                break;
            default:
                error();
                break;
        }
    }

    private void error() {
        throw new Error("type is illegal");
    }

    public void traverseDFS() {
        boolean[] visited = new boolean[mNodes.length];
        for (int i = 0; i < mNodes.length; i++) {
            visited[i] = false;
        }
        for (int i = 0; i < mNodes.length; i++) {
            if (!visited[i]) {
                DFS(visited, i);
            }
        }
    }

    private void DFS(boolean[] visited, int i) {
        visited[i] = true;
        System.out.print(mNodes[i] + "  ");

    }


    private static class Edge {
        /**
         * 指向的结点
         */
        int nextNode;
        /**
         * 权重
         */
        int weight;
        /**
         * 另一条边
         */
        Edge next;

    }

    private static class Node<E> {
        E elem;
        /**
         * 邻接表使用
         */
        Edge first;
        E precursor;
        int minPath;
        boolean isMarked;
        Node(E elem, Edge first) {
            this.elem = elem;
            this.first = first;
            precursor = null;
            minPath = 0;
            isMarked = false;
        }
    }

    /**
     * 图的类型
     */
    enum Type {
        /**
         * 有向图
         */
        DG,
        /**
         * 有向网
         */
        DN,
        /**
         * 无向图
         */
        UDG,
        /**
         * 无向网
         */
        UDN
    }
}
