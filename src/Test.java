import list.LinearList;
import list.LinkList;
import queue.LinearQueue;
import queue.LinkQueue;
import stack.LinearStack;

/**
 * @author 14512 on 2018/9/8.
 */
public class Test {

    public static void main(String[] argc) {
        LinkQueue<String> queue = new LinkQueue<>(LinkQueue.FLAG_CYCLE_QUEUE);
        queue.enqueue("1");
        queue.enqueue("2");
        queue.enqueue("3");
        queue.enqueue("4");
        queue.enqueue("5");
        System.out.println(queue.enqueue("6"));
        System.out.println("length" + queue.length());
        System.out.println(queue.dequeue());
        System.out.println("length" + queue.length());
        System.out.println(queue.enqueue("61"));
        System.out.println("length" + queue.length());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());
        System.out.println(queue.dequeue());

    }
}
