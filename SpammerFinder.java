public class SpammerFinder {
    public static void main(String[] args) {
        // You can test the findCelebrity method here.
    }
///Array
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

    Hubspot coding


public class SpammerFinder {

    public static List<Integer> findSpammers(int n, List<int[]> messages) {

        boolean[] received = new boolean[n + 1];

        // adjacency list (index -> set of unique recipients)
        List<Set<Integer>> sentTo = new ArrayList<>(n + 1);

        for (int i = 0; i <= n; i++) {
            sentTo.add(new HashSet<>());
        }

        for (int[] msg : messages) {
            int from = msg[0];
            int to   = msg[1];

            if (from == to) continue;

            received[to] = true;
            sentTo.get(from).add(to);
        }

        List<Integer> result = new ArrayList<>();

        for (int i = 1; i <= n; i++) {
            if (!received[i] && sentTo.get(i).size() == n - 1) {
                result.add(i);
            }
        }

        return result;
    }

    public static void main(String[] args) {
        int n = 4;

        List<int[]> messages = List.of(
            new int[]{2,1}, new int[]{2,3}, new int[]{2,4},
            new int[]{2,3}
        );

        System.out.println(findSpammers(n, messages)); // [2]
    }
}

public class SpammerFinder {
           public int findSpammer(int n) {
        if (n <= 0) return -1;
        if (n == 1) return 0; // by this definition, vacuously messages "everyone else" and receives none

            int candidate = 0;
        for (int i = 1; i < n; i++) {
            // spammer must message everyone; if candidate didn't message i, candidate cannot be spammer
            if (!knows(candidate, i)) {
                candidate = i;
            }
        }

        // 2) Verification: candidate must message everyone else AND receive no messages
        for (int i = 0; i < n; i++) {
            if (i == candidate) continue;

            // must message everyone
            if (!knows(candidate, i)) return -1;

            // must receive no messages
            if (knows(i, candidate)) return -1;
        }

        return candidate;
    }
}



public int findCelebrity(int n) {
    int candidate = 0;

    for (int i = 1; i < n; i++) {
        if (knows(candidate, i)) {
            candidate = i;
        }
    }

    for (int i = 0; i < n; i++) {
        if (i == candidate) continue;

        if (knows(candidate, i) || !knows(i, candidate)) {
            return -1;
        }
    }

    return candidate;
}

}
