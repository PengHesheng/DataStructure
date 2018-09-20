import tree.HuffmanTree;

/**
 * @author 14512 on 2018/9/8.
 */
public class Test {

    public static void main(String[] argc) {
        HuffmanTree<String> huffmanTree = new HuffmanTree<>(new String[]{"A", "B", "C", "D"}, new int[]{9, 8, 3, 4}, 4);
        huffmanTree.huffmanCoding();
        huffmanTree.traverse();
    }
}
