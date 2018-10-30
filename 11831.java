/**
 * https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&category=24&page=show_problem&problem=2931
 * 11831 Sticker Collector Robot
 * Description: Traverse a graph per instructions
 * Catergory: Graph
 * Solution: (not working) Define the four directions that the robot can face
 *              and turn or move forward accordingly.
 * Issues: For test 1 (see below the code), the output is correct. However, for
 *          for test 2, which contains test 1 as the last test case, the output
 *          for the exact same test case is wrong!?
 * Author: Vu Thanh Trung
 */

import java.io.*;
import java.util.HashMap;
import java.util.StringTokenizer;

public class Main {

    static BufferedReader br;
    static PrintWriter pw;
    static StringTokenizer st;
    static HashMap<Character, Direction> initialDirections;
    static Direction direction;

    public static void main(String[] args) throws Exception {

//        System.setIn(new FileInputStream("input.txt"));
//        System.setOut(new PrintStream("output.txt"));

        br = new BufferedReader(new InputStreamReader(System.in));
        pw = new PrintWriter(new BufferedOutputStream(System.out));
        init();
        String line;
        while ((line = br.readLine()).charAt(0) != '0') {
            char[][] graph = inputGraph(line);
            char[] instructions = br.readLine().toCharArray();
            solve(graph, instructions);
        }
        pw.close();
    }

    private static char[][] inputGraph(String line) throws IOException {
        st = new StringTokenizer(line);
        int noRows = Integer.parseInt(st.nextToken());
        char[][] graph = new char[noRows][];
        for (int i = 0; i < noRows; i++) {
            graph[i] = br.readLine().toCharArray();
        }
        return graph;
    }

    private static void init() {
        initialDirections = new HashMap<>();
        initialDirections.put('N', new Direction(-1, 0));
        initialDirections.put('S', new Direction(1, 0));
        initialDirections.put('L', new Direction(0, 1));
        initialDirections.put('O', new Direction(0, -1));
    }

    static int[] getDirection(char[][] graph) {
        direction = null;
        for (int i = 0; i < graph.length; i++) {
            for (int j = 0; j < graph[i].length; j++) {
                if ((direction = initialDirections.get(graph[i][j])) != null) {
                    int[] location = new int[2];
                    location[0] = i;
                    location[1] = j;
                    return location;
                }
            }
        }
        return null;
    }


    private static void solve(char[][] graph, char[] instructions) {
        int count = 0;
        int[] location = getDirection(graph);

//        pw.println(direction.rowDir + " " + direction.colDir + " : " + row + " " + col);
        for (char ch : instructions) {
//            pw.println(ch);
            if (!direction.changeDirection(ch)) {
                graph[location[0]][location[1]] = '.';
                direction.changeLocation(location, graph);
                if (graph[location[0]][location[1]] == '*') {
//                    pw.println(row + " " + col);
                    count++;
                }
            }
//            pw.println(direction.rowDir + " " + direction.colDir + " : " + row + " " + col);
        }
        pw.println(count);
    }

}

class Direction {
    int rowDir, colDir;

    public Direction(int rowDir, int colDir) {
        this.rowDir = rowDir;
        this.colDir = colDir;
    }

    boolean changeDirection(char side) {
        if (side == 'D') {
            if (rowDir == 0) {
                rowDir = colDir;
                colDir = 0;
            } else {
                colDir = -rowDir;
                rowDir = 0;
            }
        } else if (side == 'E') {
            if (rowDir == 0) {
                rowDir = -colDir;
                colDir = 0;
            } else {
                colDir = rowDir;
                rowDir = 0;
            }
        } else {
            return false;
        }
        return true;
    }

    static boolean withinRange(int a, int m) {
        return a > -1 && a < m;
    }

    boolean validSquare(int r, int c, int noRows, int noCols, char[][] graph) {
        return withinRange(r, noRows) && withinRange(c, noCols) && graph[r][c] != '#';
    }

    public void changeLocation(int[] location, char[][] graph) {
        int row = location[0] + rowDir;
        int col = location[1] + colDir;
        int noRows = graph.length;
        int noCols = graph[0].length;
        if (validSquare(row, col, noRows, noCols, graph)) {
            location[0] += rowDir;
            location[1] += colDir;
        }
    }
}
/* Test 1:
11 25 23
.....*...#.....*........#
........#....#...*..*#...
...*#..*..........**....*
..**........*........#...
....*...#*.#*...#.......N
#.....#..#**...#....*.#..
........#.*..............
*#....#..*.....*..##...*.
........*.*#.#......*.#*.
..*...#....*.....#..**..*
.*...#...*.....*..*..**..
FEEFEEFEEFFFFFEFFFDDFEE
0 0 0
 */
/* Correct output for test 1:
1
 */

/* Test 2: (notice that test 1 is contained at the bottom)
27 7 47
*..*...
.*..N#*
***..*#
*#**.#.
*...**#
..***.#
**#.*#.
##..*#*
.*#...#
**##*.*
*..****
##.***#
**...**
.#**#*.
*#..**.
..*..*#
......#
*.**.#*
..*##.#
**..#*.
...*.*#
#..*#*.
*#*##.*
*...*#.
*#*#.*.
*......
*..****
DFFFDFFEDDEFDFDEDEDDFDFEFDFFDFDEFFDFEFDEDFDDEDE
24 21 48
.....................
.....................
.....................
..*..................
.#...................
.........#...........
.....................
.............#.......
.....................
...............*.....
.....................
...*.................
.....................
.....................
.....................
........O.*..........
.....................
.....................
...*.................
.....................
...*.....#...........
...................*.
.....................
.....................
FDDFFDEEFFEFDFEFEFDFDFDDDFDFFEFEFFEEFFFFEDFEFFFD
30 30 3
...*...#...#......*..*...#..#.
**#.*.#.**.*..*#..*....#.....#
..*..*.*.*...#*......*#....*..
..................*.*.....*.#.
**#*..*.........#.*....*..#...
*....#..*..*..*....#..#..**.*.
.*.#.*...#.#.*...*...........*
...*.#..........#..*#...*.....
..*....*.......#..*......*.#*.
..*.****..#.**......#..*.*....
..#.*.#....##..****#...#......
..*..*..*....#*.*........**..*
...*..*#....***#...#.**.....**
#.........##.*....#......*.*..
.**....*..*......#.....*......
.....*....*...*..**..*##.#.*#.
.#...#.*.......#....*.*.......
*..*.**.*...#..#*#..#.*#.#*...
*...#..#.#...**...#*.#..*...*.
#.*.#........*............*#..
.*...........*S..*#...*..*#...
..#......#*.........*......*..
....*.....**......*...*.*.....
.*.****...**.**....*....*.**.*
..*.*....*.*.#.....*#......#.*
.#.*.....#.......*.......#...*
.....#....#..#..*..#....*##**.
**...#*.....***.*#*...........
..*#.....*.*.*.*.*...#..*.*...
..*#..##...*..*..##.......*..#
FDD
11 25 23
.....*...#.....*........#
........#....#...*..*#...
...*#..*..........**....*
..**........*........#...
....*...#*.#*...#.......N
#.....#..#**...#....*.#..
........#.*..............
*#....#..*.....*..##...*.
........*.*#.#......*.#*.
..*...#....*.....#..**..*
.*...#...*.....*..*..**..
FEEFEEFEEFFFFFEFFFDDFEE
0 0 0
 */
/* Correct output for test 2:
4
0
0
1
 */
/* My output:
4
0
0
0
 */
