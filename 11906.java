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
 * Solution: dfs
 * Issues:
 * Author: Vu Thanh Trung
 */

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static PrintWriter pw;
    static StringTokenizer st;
    static int noRows, noCols, m, n, noEvens, noOdds;
    static boolean[][] blocked;
    static boolean[][] visited;
    static HashSet<Pair> moves;

    public static void main(String[] args) throws Exception {

//        System.setIn(new FileInputStream("input.txt"));
//        System.setOut(new PrintStream("output.txt"));

        br = new BufferedReader(new InputStreamReader(System.in));
        pw = new PrintWriter(new BufferedOutputStream(System.out));

        int noTcs = Integer.parseInt(br.readLine());
        for (int i = 1; i <= noTcs; i++) {
            input();
            noEvens = 0;
            noOdds = 0;
            visited = new boolean[noRows][noCols];
            dfs(new Pair(0, 0));
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

    static void dfs(Pair p) {
        visited[p.r][p.c] = true;
        int noAdjCells = 0;
        ArrayList<Pair> adj = new ArrayList<>();
        for (Pair move : moves) {
            Pair pos;
            if ((pos = getPos(p, move)) != null) {
                adj.add(pos);
                noAdjCells++;
                if (!visited[pos.r][pos.c]) {
                    dfs(pos);
                }
            }
        }
        if (noAdjCells % 2 != 1) {
            noEvens++;
        } else {
            noOdds++;
        }
    }


    private static Pair getPos(Pair p, Pair move) {
        int r = p.r + move.r;
        int c = p.c + move.c;
        if (r < 0 || r >= noRows || c < 0 || c >= noCols || blocked[r][c]) {
            return null;
        }
        return new Pair(r, c);
    }

    private static void input() throws IOException {
        st = new StringTokenizer(br.readLine());
        noRows = Integer.parseInt(st.nextToken());
        noCols = Integer.parseInt(st.nextToken());
        blocked = new boolean[noRows][noCols];
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        int noBlockedCells = Integer.parseInt(br.readLine());
        for (int i = 0; i < noBlockedCells; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            blocked[r][c] = true;
        }
        moves = new HashSet();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (i != 0 && j != 0) {
                    moves.add(new Pair(i * m, j * n));
                    moves.add(new Pair(i * n, j * m));
                }
            }
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

/* Test input:
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
