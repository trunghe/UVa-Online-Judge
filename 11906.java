/**
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=3057
 * 11906 Knight in a War Grid
 * Description: Given a R-by-C grid(1 < R,C <= 100), starting from cell (0, 0),
 *              count the number of cells the knight can move to divided into
 *              2 groups: ones with even degrees and ones with odd degrees. The
 *              knight can only move in a L-like-shape with one side M cells long
 *              and the other N cells long (like a knight in chess but with
 *              0 <= M,N <= 50, M + N > 0). There are W cells(0 ≤ W < R∗C) that
 *              are blocked.
 * Catergory: Graph
 * Solution: (not working within time limit 1 second, see test case after the code)
 *          bfs
 * Issues: Time Limit Exceeded!
 * Author: Vu Thanh Trung
 */

import java.io.*;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Objects;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static PrintWriter pw;
    static StringTokenizer st;
    static int noRows, noCols, m, n, noEvens, noOdds;
//    static boolean[][] blocked;
    static HashSet<Pair> blocked;

    public static void main(String[] args) throws Exception {

//        System.setIn(new FileInputStream("input.txt"));
//        System.setOut(new PrintStream("output.txt"));

        br = new BufferedReader(new InputStreamReader(System.in));
        pw = new PrintWriter(new BufferedOutputStream(System.out));

        int noTcs = Integer.parseInt(br.readLine());
        for (int i = 1; i <= noTcs; i++) {
            input();
            solve();
            output(i);
        }
        pw.close();
    }

    private static void output(int i) {
        pw.print("Case ");
        pw.print(i);
        pw.print(": ");
        pw.print(noEvens);
        pw.print(' ');
        pw.print(noOdds);
        pw.println();
    }

    private static void solve() {
        noEvens = noOdds = 0;
        HashSet<Pair> adjVertices;
        LinkedList<Pair> queue = new LinkedList<>();
        queue.addLast(new Pair(0, 0));
//        HashSet<Pair> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            Pair p = queue.pollFirst();
//            visited.add(p);
            blocked.add(p);
//            System.out.println("(" + p.r + " " + p.c + ")");
            adjVertices = new HashSet<>();
            for (int i = -1; i < 2; i++) {
                for (int j = -1; j < 2; j++) {
                    if (i != 0 && j != 0) {
                        Pair pos;
                        if ((pos = getPos (p, m, n, i, j)) != null) {
                            if (adjVertices.add(pos)
//                                    && !visited.contains(pos)) {
                                    && !blocked.contains(pos)) {
                                queue.addLast(pos);
                            }
                        }
                        if ((pos = getPos(p, n, m, j, i)) != null) {
                            if (adjVertices.add(pos)
//                                    && !visited.contains(pos)) {
                                    && !blocked.contains(pos)) {
                                queue.addLast(pos);
                            }
                        }
                    }
                }
            }
            if (adjVertices.size() % 2 != 1) {
                noEvens++;
            } else {
                noOdds++;
            }
        }
    }

    private static Pair getPos(Pair p, int moveR, int moveC, int i, int j) {
        int r = p.r + i * moveR;
        int c = p.c + j * moveC;
        if (r < 0 || r >= noRows || c < 0 || c >= noCols
//                || blocked[r][c]) {
                || blocked.contains(new Pair(r, c))) {
//            System.out.println("(" + r + " " + c + ") is not valid");
            return null;
        }
        return new Pair(r, c);
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        noRows = Integer.parseInt(st.nextToken());
        noCols = Integer.parseInt(st.nextToken());
//        blocked = new boolean[noRows][noCols];
        blocked = new HashSet<>();
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        int noBlockedCells = Integer.parseInt(br.readLine());
        for (int i = 0; i < noBlockedCells; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
//            blocked[r][c] = true;
            blocked.add(new Pair(r, c));
        }
    }
}

class Pair {
    int r, c;

    public Pair(int r, int c) {
        this.r = r;
        this.c = c;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return r == pair.r &&
                c == pair.c;
    }

    @Override
    public int hashCode() {
        return Objects.hash(r, c);
    }
}

/* Test (time limit):
1
86 98 23 19
17
65 54
76 26
51 66
0 74
4 85
19 31
78 23
16 15
63 30
3 59
18 97
74 87
52 10
19 57
71 12
28 63
63 29
 */
/* Correct output:
Case 1: 3873 334
 */
