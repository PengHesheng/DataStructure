package arithmetic;

/**
 * @author 14512 on 2018/9/10.
 */
public class Find {

    /**
     * 基于排序的拆半查找
     * @param a
     * @param key
     * @return
     */
    int binarySearch(int[] a, int key) {
        int low = 0, high = a.length;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (a[mid] == key) {
                return mid;
            } else if (a[mid] < key) {
                low = mid + 1;
            } else if (a[mid] > key) {
                high = mid - 1;
            }
        }
        return -1;
    }


}
