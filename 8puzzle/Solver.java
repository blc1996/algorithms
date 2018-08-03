import java.util.ArrayList;
import java.util.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.util.Comparator;
import java.lang.IllegalArgumentException;

public class Solver {
    private MinPQ<Node> pq;
    private MinPQ<Node> pqTwin;
    private boolean solvable = true;
    private boolean transfer = false;
    private Stack<Board> solution;
    private ArrayList<Board> out;
    private int move = -1;


    private class Node {
        Board board;
        int steps;
        Node parent;
        int point;

        Node (Board b, int s, Node n) {
            board = b;
            steps = s;
            parent = n;
            point = b.manhattan();
        }
    }

    private class Priority implements Comparator<Node> {
        public int compare(Node a, Node b) {
            int m1 = a.point;
            int m2 = b.point;
            int d1 = m1 + a.steps;
            int d2 = m2 + b.steps;
            if (d1 < d2) {
                return -1;
            } else if (d1 == d2) {
                if (m1 < m2) return -1;
                if (m1 > m2) return 1;
                return 0;
            } else {
                return 1;
            }
        }
    }

    public Solver(Board initial){
        if(initial == null)
            throw new IllegalArgumentException("null");
        pq = new MinPQ<Node>(new Priority());
        pqTwin = new MinPQ<Node>(new Priority());
        solution = new Stack<Board>();
        Node node = new Node(initial, 0, null);
        Node nodeTwin = new Node(initial.twin(), 0, null);
        pq.insert(node);
        pqTwin.insert(nodeTwin);
        ArrayList<Board> neighbors;
        ArrayList<Board> neighborsTwin;
        while(!pq.isEmpty()){
            node = pq.delMin();
            // System.out.println(node.board.manhattan() + node.steps);
            nodeTwin = pqTwin.delMin();
            // pq = new MinPQ(new Priority());
            // pqTwin = new MinPQ(new Priority());

            if(nodeTwin.board.isGoal()){
                solvable = false;
                return;
            }
            if(node.board.isGoal()){
                while(node != null){
                    solution.push(node.board);
                    node = node.parent;
                    move++;
                }
                return;
            }
            // neighbors = node.board.neighbors();
            for(Board i : node.board.neighbors()){
                if(node.parent != null)
                    if(i.equals(node.parent.board)){
                        continue; //optimization 1
                    }
                pq.insert(new Node(i, node.steps + 1, node));
            }
            // neighborsTwin = nodeTwin.board.neighbors();
            for(Board i : nodeTwin.board.neighbors()){
                if(nodeTwin.parent != null)
                    if(i.equals(nodeTwin.parent.board)){
                        continue; //optimization 1
                    }
                pqTwin.insert(new Node(i, nodeTwin.steps + 1, nodeTwin));
            }
        }
    }           // find a solution to the initial board (using the A* algorithm)


    public boolean isSolvable(){
        return solvable;
    }            // is the initial board solvable?


    public int moves(){
        if(solvable == false){
            return -1;
        }else{
            return move;
        }
    }                     // min number of moves to solve initial board; -1 if unsolvable


    public Iterable<Board> solution(){
        if(solvable == false){
            return null;
        }else if(transfer == false){
            out = new ArrayList<Board>();
            while(!solution.isEmpty()) {
                out.add(solution.pop());
            }
            transfer = true;
            return out;
        }
        return out;
    }      // sequence of boards in a shortest solution; null if unsolvable


    public static void main(String[] args){
         // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
        StdOut.println("No solution possible");
    else {
        StdOut.println("Minimum number of moves = " + solver.moves());
        int count = 0;
        for (Board board : solver.solution()){
            StdOut.println(board);
            // StdOut.println("score:");
            // StdOut.println(board.manhattan() + count++);
        }
    } // solve a slider puzzle (given below)
}
}
