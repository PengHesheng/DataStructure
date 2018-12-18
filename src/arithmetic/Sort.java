package arithmetic;

import java.util.Arrays;

/**
 * @author 14512 on 2018/9/10.
 */
public class Sort {

    public static void main(String[] argv) {
        int[] a = {4, 5, 3, 0, 1, 7, 2, 6};
        //quickSort(a);
        //heapSort(a);
        //bubbleSort(a);
        //chooseSort(a);
        //insertSort(a);
        //mergeSort(a);
        shellSort(a);
    }

    public static void bubbleSort(int[] a) {
        int temp;
        for (int i = 0; i < a.length; i++) {
            for (int j = i+1; j < a.length; j++) {
                if (a[j] <= a[i]) {
                    temp = a[i];
                    a[i] = a[j];
                    a[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(a));
    }

    /**
     * 插入排序
     * @param a
     */
    public static void insertSort(int[] a) {
        int temp;
        for (int i = 1, j = 0; i < a.length; i++) {
            temp = a[i];
            for (j = i - 1; j >= 0 && temp < a[j]; j--) {
                a[j+1] = a[j];
            }
            a[j+1] = temp;
        }
        System.out.println(Arrays.toString(a));
    }

    public static void chooseSort(int[] a) {
        int k;
        int temp;
        for (int i = 0; i < a.length; i++) {
            k = i;
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] <= a[k]) {
                    k = j;
                }
            }
            temp = a[i];
            a[i] = a[k];
            a[k] = temp;
        }
        System.out.println(Arrays.toString(a));
    }

    public static void heapSort(int[] a) {
        int temp;
        //将数组看为一棵完全二叉树，依次就对应着数组
        //构建最大heap，从数组中点也就是堆的中间开始调整，这样可以使该结点的孩子的下标实在最后
        for (int i = a.length/2 - 1; i >= 0; i--) {
            heapAdjust(a, i, a.length - 1);
        }
        //经过调整后，最大值始终是根结点，也就是第一个位置
        for (int i = a.length - 1; i > 0; i--) {
            temp = a[0];
            a[0] = a[i];
            a[i] = temp;
            heapAdjust(a, 0, i-1);
        }
        System.out.println(Arrays.toString(a));
    }

    private static void heapAdjust(int[] a, int s, int m) {
        int parent = a[s];
        //左孩子的下标是2*p+1，右孩子下标2*p+2
        for (int lChild = 2*s +1; lChild <= m; lChild = 2*lChild + 1) {
            //如果左孩子小于右孩子
            if (lChild < m && a[lChild] < a[lChild+1]) {
                //变成右孩子下标
                lChild++;
            }
            //如果父结点本身大于孩子结点，就不用调整 此时的lChild可能是左孩子可能是右孩子
            if (parent > a[lChild]) {
                break;
            }
            //父结点跟最大的孩子结点交换
            a[s] = a[lChild];
            //转为之前最大孩子结点，遍历它的子树进行调整
            s = lChild;
        }
        a[s] = parent;
    }

    public static void mergeSort(int[] a) {
        mergeSort(a, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    private static void mergeSort(int[] a, int start, int end) {
        if (start < end) {
            int mid = start + (end - start) / 2;
            mergeSort(a, start, mid);
            mergeSort(a, mid + 1, end);
            //在合并的时候进行排序
            merge(a, start, mid, end);
        }
    }

    private static void merge(int[] a, int start, int mid, int end) {
        int[] temp1 = new int[a.length / 2 + 1];
        int[] temp2 = new int[a.length / 2 + 1];
        int n1, n2;
        n1 = mid - start + 1;
        n2 = end - mid;
        //拷贝前半部分数组
        for (int i = 0; i < n1; i++) {
            temp1[i] = a[start + i];
        }
        for (int i = 0; i < n2; i++) {
            temp2[i] = a[mid + i +1];
        }

        temp1[n1] = temp2[n2] = Integer.MAX_VALUE;
        for (int k = start, i=0, j=0; k <= end ; k++) {
            if (temp1[i] <= temp2[j]) {
                a[k] = temp1[i];
                i++;
            } else {
                a[k] = temp2[j];
                j++;
            }
        }
    }

    public static void shellSort(int[] a) {
        int temp, i = 0, j = 0, k = 0;
        //取长度的一半作为希尔步数，最终都会是1步即插入排序
        for (i = a.length / 2; i > 0; i--) {
            for (j = i; j < a.length; j++) {
                temp = a[j];
                for (k = j - i; k >= 0 && temp < a[k]; k -= i) {
                    a[k + i] = a[k];
                }
                a[k + i] = temp;
            }
        }
        System.out.println(Arrays.toString(a));
    }

    public static void quickSort(int[] a) {
        int randIndex = random(0, a.length);
        quickSort(a, randIndex, 0, a.length - 1);
        System.out.println(Arrays.toString(a));
    }

    private static void quickSort(int[] a, int randIndex, int low, int high) {
        if (low < high) {
            int i = low;
            int j = high;
            //随机选定一个数
            int temp = a[randIndex];
            a[randIndex] = a[i];
            while (i < j) {
                while (i < j && a[j] > temp) {
                    j--;
                }
                if (i < j) {
                    a[i] = a[j];
                }
                while (i < j && a[i] < temp) {
                    i++;
                }
                if (i < j) {
                    a[j] = a[i];
                }
            }
            a[i] = temp;
            quickSort(a, random(low, i-1), low, i-1);
            quickSort(a, random(i + 1, high), i+1, high);
        }
    }

    /**
     * 生成指定区间的随机数
     * @param low 低区间
     * @param high 高区间
     * @return
     */
    private static int random(int low, int high) {
        return low + (int) (Math.random()*(high - low + 1));
    }

}
