import tree.BinaryTree;

/**
 * @author 14512 on 2018/9/8.
 */
public class Test {

    public static void main(String[] argc) {
        BinaryTree<String> tree = new BinaryTree<>("A");
        tree.addLeft("A", "B");
        tree.addLeft("B", "C");
        tree.addRight("B", "D");
        tree.addLeft("D", "E");
        tree.addRight("D", "F");
        tree.addRight("A", "G");
        tree.addLeft("G", "H");
        tree.addLeft("H", "J");
        tree.addRight("G", "I");
        tree.addRight("I", "M");
        System.out.println(tree.getDepth());
//        System.out.println(tree.find("A"));
//        System.out.println(tree.find("D"));
//        System.out.println(tree.find("K"));
        tree.traverse();
    }
}
