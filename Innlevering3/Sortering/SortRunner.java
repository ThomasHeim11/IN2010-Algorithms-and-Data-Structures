import java.io.BufferedWriter;
import java.io.File;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.stream.Collectors;
// Prekode fra: https://github.uio.no/IN2010/sortering-ressursside/tree/master/java-src
class SortRunner {

    // The student can adjust these parameters to conduct their experiments

    // Put the sorting algorithms under test for part 1 here
    static final Sorter[] ALGS1 = { new InsertionSort(), new MergeSort(), new Bucketsort(), new Heapsort() };
    // Put the sorting algorithms under test for part 2 here
    static final Sorter[] ALGS2 = { new InsertionSort(), new MergeSort(), new Bucketsort(), new Heapsort() };
    // Time limit for a single sorting in milliseconds
    static final long TIME_LIMIT_MS = 100;
    // How much n grows each iteration for part 2
    static final int INCREMENT = 1;

    // Run all sorting algorithms and create .out files
    static void runAlgsPart1(int[] A, String infilename) throws Exception {
        for (Sorter alg : ALGS1) {
            // Make an outfile for the algorithm
            String outfilename = infilename + "_" + alg.algorithmName() + ".out";
            File outfile = new File(outfilename);
            BufferedWriter writer = new BufferedWriter(new PrintWriter(outfile));

            // Initialize and sort
            alg.initializePart1(A);
            alg.sort();

            // Write results to file
            for (int i = 0; i < alg.n; i++) {
                writer.write(alg.A[i] + "\n");
            }
            writer.close();
        }
    }

    // Run all sorting algorithms from 0 to n, and write CSV file
    static void runAlgsPart2(int[] A, String infilename) throws Exception {
        String outfilename = infilename + "_results.csv";
        File outfile = new File(outfilename);
        BufferedWriter writer = new BufferedWriter(new PrintWriter(outfile));

        // Provide the sorter with the array that is to be sorted
        for (Sorter alg : ALGS2) {
            alg.initializePart2(A);
        }

        // Construct the header row of the CSV
        int digits = numberOfDigits(A.length);
        String header = makeHeader(ALGS2, digits);

        // Write it to file and print it
        writer.write(header + "\n");
        System.out.println(header);

        // For right-aligning numbers in the n-column
        String rowfmt = "%" + digits + "d";

        // Keep track of when we last printed, so as to only print every second
        long printtime = System.nanoTime();

        // Run the experiments for increasing values of n
        for (int i = 0; i <= A.length; i += INCREMENT) {
            // Construct the row, by running all sorting algorithms in ALGS2
            String row = String.format(rowfmt, i);
            for (Sorter alg : ALGS2) {
                row += ", " + alg.runResetAndIncBy(INCREMENT);
            }

            // Quit if all sorting algorithms are discarded
            if (row.matches("\\s*\\d+,(\\s*,)*\\s*")) {
                break;
            }

            // Write the row to file
            writer.write(row + "\n");

            // Print the line every second
            long now = System.nanoTime();
            if (now - printtime > 1000000000) {
                System.out.println(row);
                printtime = now;
                writer.flush();
            }
        }
        // Close the file for good measure
        writer.close();
    }

    static int numberOfDigits(int n) {
        return (int) (Math.log10(n) + 1);
    }

    // Make the header string for the CSV
    static String makeHeader(Sorter[] algs, int digits) {
        String headers = Arrays.stream(algs)
            .map(alg -> alg.headerString())
            .collect(Collectors.joining(", "));
        String fmt = "%" + digits + "s, %s";
        return String.format(fmt, "n", headers);
    }
}