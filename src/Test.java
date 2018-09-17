import tree.BinaryTree;
import tree.Tree;

/**
 * @author 14512 on 2018/9/8.
 */
public class Test {

    public static void main(String[] argc) {
        Tree<String> tree = new Tree<>("R");
        tree.addNodes(new String[]{"A", "B", "C", "D", "E", "F", "G", "H", "K"});
        tree.initNode("R", "A");
        tree.initNode("R", "B");
        tree.initNode("R", "C");
        tree.initNode("A", "D");
        tree.initNode("A", "E");
        tree.initNode("C", "F");
        tree.initNode("F", "G");
        tree.initNode("F", "H");
        tree.initNode("F", "K");
        tree.traverse();
        BinaryTree binaryTree = tree.convertToBinaryTree();
        if (binaryTree != null) {
            binaryTree.traverse();
        }
    }
}
