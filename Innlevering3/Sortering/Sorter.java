// Prekode fra: https://github.uio.no/IN2010/sortering-ressursside/tree/master/java-src

import java.util.Arrays;

abstract class Sorter {
    // Keep copy of the original array
    int original[];
    // The array which is to be sorted
    int A[];
    // The number of elements of A
    int n = 0;
    // Counters for comparisons and swaps
    int comparisons = 0;
    int swaps = 0;

    // Set true when a sort exceeds SortRunner.TIME_LIMIT_MS
    // When true, we discard the call to run() (i.e. return immediately)
    boolean discard = false;

    void initializePart1(int[] A) {
        this.original = A;
        this.n = A.length;
        // We clone, so that A may be safely passed to multiple sorters
        this.A = Arrays.copyOfRange(original, 0, n);
    }

    void initializePart2(int[] A) {
        this.original = A;
        this.n = 0;
        // We clone, so that A may be safely passed to multiple sorters
        this.A = Arrays.copyOfRange(original, 0, n);
    }

    // For the students to implement in an appropriate subclass
    abstract void sort();

    // Necessary for output
    abstract String algorithmName();

    // A swapping method that counts
    void swap(int i, int j) {
        swaps++;
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    // Comparisons that count
    boolean lt(int a, int b) {
        comparisons++;
        return a < b;
    }

    boolean leq(int a, int b) {
        comparisons++;
        return a <= b;
    }

    boolean gt(int a, int b) {
        comparisons++;
        return a > b;
    }

    boolean geq(int a, int b) {
        comparisons++;
        return a >= b;
    }

    boolean eq(int a, int b) {
        comparisons++;
        return a == b;
    }

    // For SortRunner

    // Sort and return the time it consumed in microseconds
    long sortTimed() {
        long t = System.nanoTime();
        sort();
        return (System.nanoTime() - t) / 1000;
    }

    // Run a sorting and return a description of the execution as a CSV row
    String run() {
        String fmt = runStringFormat();
        if (discard) {
            String res = String.format(fmt, 0, 0, 0);
            return res.replace("0", " ");
        }
        long timeus = sortTimed();
        long timems = timeus / 1000;

        if (timems > SortRunner.TIME_LIMIT_MS) {
            discard = true;
            System.out.println("\nGiving up on " + algorithmName() + "\n");
        }

        String res = String.format(fmt, comparisons, swaps, timeus);
        return res;
    }

    // Reset counters and restore the original array (up to n)
    void reset() {
        comparisons = 0;
        swaps = 0;
        this.A = Arrays.copyOfRange(original, 0, n);
    }

    // Reset with a higher n
    void resetAndIncBy(int increment) {
        n += increment;
        reset();
    }

    // Run, reset and increment
    String runResetAndIncBy(int increment) {
        String res = run();
        resetAndIncBy(increment);
        return res;
    }

    // Generate the header for this sorting algorithm
    String headerString() {
        String name = this.algorithmName();
        String headerfmt = "%s_cmp, %s_swaps, %s_time";
        return String.format(headerfmt, name, name, name);
    }

    // Generate a format string for printing results of a run
    String runStringFormat() {
        String name = this.algorithmName();
        int cmplen = name.length() + "_cmp".length();
        int swaplen = name.length() + "_swaps".length();
        int timelen = name.length() + "_time".length();
        String fmt = "%%%dd, %%%dd, %%%dd";
        return String.format(fmt, cmplen, swaplen, timelen);
    }
}