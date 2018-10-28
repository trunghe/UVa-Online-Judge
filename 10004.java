/**
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=945
 * 10004 Bicoloring
 * Description: Given a simple undirected graph, check whether it is Bipartite or not
 * Catergory: Graph
 * Solution: dfs
 * Author: Vu Thanh Trung
 */

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static PrintWriter pw;
    static StringTokenizer st;
    static int n, m;
    static Boolean[] color;
    static final Boolean RED = true, BLUE = false;
    static ArrayList<Integer>[] adjLists;

//    static String inputLineNonEmpty() throws IOException {
//        String line;
//        while ((line = br.readLine()).isEmpty()) {
//        }
//        return line;
//    }

    public static void main(String[] args) throws Exception {

//        System.setIn(new FileInputStream("input.txt"));
//        System.setOut(new PrintStream("output.txt"));

        br = new BufferedReader(new InputStreamReader(System.in));
        pw = new PrintWriter(new BufferedOutputStream(System.out));

        String line;

        while ((line = br.readLine()).charAt(0) != '0') {
            input(line);
            solve();
        }
        pw.close();

    }

    private static void solve() {
        color = new Boolean[n];
        pw.println((dfs(0, RED) ? "" : "NOT ") + "BICOLORABLE.");
    }

    private static boolean dfs(int u, final boolean COLOUR) {
        color[u] = COLOUR;
        for (int v : adjLists[u]) {
            if (color[v] != null) {
                if (color[v] == color[u]) {
                    return false;
                }
            } else {
                if (!dfs(v, !COLOUR)) {
                    return false;
                }
            }
        }
        return true;
    }

    private static void input(String input) throws IOException {
        n = Integer.parseInt(input);
        adjLists = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            adjLists[i] = new ArrayList();
        }
        m = Integer.parseInt(br.readLine());
        for (int i = 0; i < m; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken());
            int v = Integer.parseInt(st.nextToken());
            adjLists[u].add(v);
            adjLists[v].add(u);
        }
    }
}
