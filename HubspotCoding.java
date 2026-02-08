public class SpammerFinder {
    public static void main(String[] args) {
        // You can test the findCelebrity method here.
    }

    public static int findCelebrity(int[][] M) {
        int n = M.length;
        int candidate = 0;

        // Find the candidate celebrity.
        for (int i = 1; i < n; i++) {
            if (M[candidate][i] == 1) {
                candidate = i;
            }
        }

        // Verify if the candidate is a celebrity.
        for (int i = 0; i < n; i++) {
            if (i != candidate && (M[candidate][i] == 1 || M[i][candidate] == 0)) {
                return -1;
            }
        }

        return candidate;
    }
}