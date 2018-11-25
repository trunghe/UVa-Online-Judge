/*
https://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=280
Description: How many Roman digits of each type (I, V, X, L, C) is used to write all numbers
        from 1 to n (1 <= n <= 100) in the Roman numbering system?
Category: Dynamic Programming
Solution:
Complexity: TODO Analyze complexity
Author: Vu Thanh Trung
 */

#include <bits/stdc++.h>

using namespace std;

#define FOR(i, a, b) \
    for (int i = (a); i < (b); i++)
#define FORE(i, a, b) \
    for (int i = (a); i <= (b); i++)
//#define FORD(i, a, b) \
//    for (int i = (a); i >= (b); i--)

//#define INF 1e9+7
//#define INFLL 1e18+7
//#define esp 1e-9
//#define PI 3.14159265
//
//ll nMod = 1e9 + 7;
#define MAX 3502
//const char SYMBOLS[] = "IVXLCDM";
const char SYMBOLS[] = "ivxlcdm";
int n;
int symbols[MAX][8];
int indexSuffix[2] = {0, 1};
int suffix[2] = {1, 5};
int prefix[2] = {4, 9};
int indexPrefix[2][2] = {{0, 1},
                         {0, 2}};
int countSymbols[MAX][8];

void incrementSymbols(int i, int mod) {
    FOR(j, 0, 8) {
        symbols[i][j] += symbols[mod][j];
    }
}

//void test() {
//    FOR(h, 0, 8) {
//        FOR(g, 0, symbols[i + suffix[j] * k][h]) {
//            cerr << SYMBOLS[h];
//        }
//    }
//    cerr << endl << "i + suffix[j] * k = " << i + suffix[j] * k << endl;
//    FOR(h, 0, 8) {
//        FOR(g, 0, symbols[i + suffix[j] * (5 + k)][h]) {
//            cerr << SYMBOLS[h];
//        }
//    }
//    cerr << endl << "i + suffix[j] * (5 + k) = " << i + suffix[j] * (5 + k) << endl;
//    cerr << "k = " << k << endl;
//}

bool caseSuffix(int i) {
    FOR(j, 0, 2) {
        if (i == suffix[j]) {
            if (j == 0) {
                FOR(k, 0, 3) {
                    symbols[i + suffix[j] * k][indexSuffix[j]] += (k + 1);
                    if (i < 1000) {
                        symbols[i + suffix[j] * (5 + k)][indexSuffix[j]] += (k + 1);
                    }
//                    test();
                }
            } else {
                FOR(k, 0, 4) {
                    symbols[i + (suffix[0] / 10) * k][indexSuffix[j]]++;
                }
            }
            indexSuffix[j] += 2;
            suffix[j] *= 10;
            return true;
        }
    }
    return false;
}

bool casePrefix(int i) {
    FOR(j, 0, 2) {
        if (i == prefix[j]) {
            FOR(k, 0, 2) {
                symbols[i][indexPrefix[j][k]]++;
                indexPrefix[j][k] += 2;
            }
            prefix[j] *= 10;
            return true;
        }
    }
    return false;
}

void caseDp(int i) {
    int num = i;
    int md = 10;
    while (num > 0) {
        int mod = num % md;
        if (mod == i) {
            return;
        }
        incrementSymbols(i, mod);
        md *= 10;
        num -= mod;
    }
}

void solve() {
    FORE(i, 1, n) {
        if (!caseSuffix(i) & !casePrefix(i)) {
            caseDp(i);
        }
        FOR(j, 0, 8) {
//            FOR(g, 0, symbols[i][j]) {
//                cerr << SYMBOLS[j];
//            }
            countSymbols[i][j] += (countSymbols[i - 1][j] + symbols[i][j]);
        }
//        cerr << endl;
    }
}

int main() {
//    ios_base::sync_with_stdio(false);
//    cin.tie();
//    freopen("preface.in", "r", stdin);
//    freopen("preface.out", "w", stdout);
//    freopen("error.txt", "w", stderr);
//    cin >> n;
    n = 100;
    solve();
    int pages;
    cin >> pages;
    while (pages > 0) {
        cout << pages << ":";
        FOR(i, 0, 5) {
            cout << " " << countSymbols[pages][i] << " " << SYMBOLS[i];
            if (i < 4) {
                cout << ",";
            }
        }
        cout << endl;
        cin >> pages;
    }
    return 0;
}
