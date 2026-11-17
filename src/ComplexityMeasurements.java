import java.util.Arrays;
import java.util.Random;

public class ComplexityMeasurements {

    private static final int WARMUP_ITERATIONS = 10_000;
    private static final int MEASUREMENT_ITERATIONS = 5;
    private static final int INNER_ITERATIONS = 10_000;
    private static final Random random = new Random(42);

    public static void main(String[] args) {
        int[] nValues = {10, 100, 1_000, 2_000, 4_000, 8_000, 16_000, 32_000, 64_000, 128_000};

        System.out.println("N;Task1_O1;Task2_First;Task2_Random;Task2_Missing;Task3_First;Task3_Random;Task3_Missing;Task4_All;Task4_Random;Task4_None;Task5_Best;Task5_Avg;Task5_Worst");

        for (int n : nValues) {
            double t1 = measure(() -> task1(n));
            double t2First = measure(() -> task2First(n));
            double t2Random = measure(() -> task2Random(n));
            double t2Missing = measure(() -> task2Missing(n));
            double t3First = measure(() -> task3First(n));
            double t3Random = measure(() -> task3Random(n));
            double t3Missing = measure(() -> task3Missing(n));
            double t4All = measure(() -> task4All(n));
            double t4Random = measure(() -> task4Random(n));
            double t4None = measure(() -> task4None(n));
            double t5Best = measure(() -> task5Best(n));
            double t5Avg = measure(() -> task5Avg(n));
            double t5Worst = measure(() -> task5Worst(n));

            System.out.printf("%d;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f;%.2f%n",
                    n, t1, t2First, t2Random, t2Missing, t3First, t3Random, t3Missing,
                    t4All, t4Random, t4None, t5Best, t5Avg, t5Worst);
        }
    }

    // Задание 1
    private static void task1(int n) {
        int[] a = new int[n];
        int x = a[n / 2];
    }

    // Задание 2
    private static void task2First(int n) {
        int[] a = initSorted(n);
        binarySearch(a, a[0]);
    }

    private static void task2Random(int n) {
        int[] a = initSorted(n);
        binarySearch(a, a[n / 2]);
    }

    private static void task2Missing(int n) {
        int[] a = initSorted(n);
        binarySearch(a, -1);
    }

    // Задание 3
    private static void task3First(int n) {
        int[] a = initSorted(n);
        linearSearch(a, a[0]);
    }

    private static void task3Random(int n) {
        int[] a = initSorted(n);
        linearSearch(a, a[n / 2]);
    }

    private static void task3Missing(int n) {
        int[] a = initSorted(n);
        linearSearch(a, -1);
    }

    // Задание 4
    private static void task4All(int n) {
        int[] a = initSorted(n);
        countGreaterThan(a, -1);
    }

    private static void task4Random(int n) {
        int[] a = initSorted(n);
        countGreaterThan(a, n / 2);
    }

    private static void task4None(int n) {
        int[] a = initSorted(n);
        countGreaterThan(a, n + 1);
    }

    // Задание 5
    private static void task5Best(int n) {
        int[] a = initMixed(n);
        a[0] = -1;
        findFirstNegative(a);
    }

    private static void task5Avg(int n) {
        int[] a = initMixed(n);
        a[n / 2] = -1;
        findFirstNegative(a);
    }

    private static void task5Worst(int n) {
        int[] a = initMixed(n);
        Arrays.fill(a, 1);
        findFirstNegative(a);
    }

    private static int[] initSorted(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = i;
        return a;
    }

    private static int[] initMixed(int n) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = random.nextInt(100);
        return a;
    }

    private static int binarySearch(int[] arr, int target) {
        int left = 0, right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) return mid;
            if (arr[mid] < target) left = mid + 1;
            else right = mid - 1;
        }
        return -1;
    }

    private static int linearSearch(int[] arr, int target) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == target) return i;
        }
        return -1;
    }

    private static int countGreaterThan(int[] arr, int target) {
        int count = 0;
        for (int value : arr) {
            if (value > target) count++;
        }
        return count;
    }

    private static int findFirstNegative(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] < 0) return i;
        }
        return -1;
    }

    private static void warmup(Runnable action) {
        for (int i = 0; i < WARMUP_ITERATIONS; i++) action.run();
    }

    private static double measure(Runnable action) {
        warmup(action);
        long totalTime = 0;
        for (int i = 0; i < MEASUREMENT_ITERATIONS; i++) {
            long start = System.nanoTime();
            for (int j = 0; j < INNER_ITERATIONS; j++) action.run();
            long end = System.nanoTime();
            totalTime += (end - start);
        }
        return (double) totalTime / (MEASUREMENT_ITERATIONS * INNER_ITERATIONS);
    }
}