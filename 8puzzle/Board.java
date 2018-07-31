import java.util.ArrayList;

public class Board {
    private int[][] blocks;
    private int dimension;

    public Board(int[][] blocks){
        dimension = blocks.length;
        this.blocks = new int[dimension][dimension];
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                this.blocks[i][j] = blocks[i][j];
            }
        }
    }           // construct a board from an n-by-n array of blocks
                                           // (where blocks[i][j] = block in row i, column j)
    public int dimension(){
        return dimension;
    }                 // board dimension n


    public int hamming(){
        int count = 0;
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(blocks[i][j] == 0){
                    continue;
                }
                if(blocks[i][j] != i * dimension + j + 1){
                    count++;
                }
            }
        }
        return count;
    }                   // number of blocks out of place


    public int manhattan(){
        int count = 0;
        int tx, ty;
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(blocks[i][j] == 0){
                    continue;
                }
                if(blocks[i][j] != i * dimension + j + 1){
                    ty = Math.abs(i - (blocks[i][j] - 1) / dimension);
                    tx = Math.abs(j - (blocks[i][j] - 1) % dimension);
                    count = count + tx + ty;
                }
            }
        }
        return count;
    }                 // sum of Manhattan distances between blocks and goal


    public boolean isGoal(){
        int temp = blocks[dimension - 1][dimension - 1];
        blocks[dimension - 1][dimension - 1] = dimension * dimension;
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(blocks[i][j] != i * dimension + j + 1){
                    blocks[dimension - 1][dimension - 1] = temp;
                    return false;
                }
            }
        }
        blocks[dimension - 1][dimension - 1] = temp;
        return true;
    }                // is this board the goal board?


    public Board twin(){
        Board res = new Board(blocks);
        int temp = res.blocks[0][0];
        res.blocks[0][0] = res.blocks[0][1];
        res.blocks[0][1] = temp;
        return res;
    }                    // a board that is obtained by exchanging any pair of blocks


    public boolean equals(Object y){
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                if(blocks[i][j] != ((Board)y).blocks[i][j]){
                    return false;
                }
            }
        }
        return true;
    }        // does this board equal y?


    public Iterable<Board> neighbors(){
        ArrayList<Board> neighbors = new ArrayList<Board>();
        int tx = 0, ty = 0, count;
        for(int i = 0; i < dimension; i++){
                for(int j = 0; j < dimension; j++){
                    if(blocks[i][j] == 0){
                        tx = i;
                        ty = j;
                        break;
                    }
                }
            }
            Board temp;
            int swap;
            if(isValid(tx - 1, ty)){
                temp = new Board(blocks);
                swap = temp.blocks[tx][ty];
                temp.blocks[tx][ty] = temp.blocks[tx - 1][ty];
                temp.blocks[tx - 1][ty] =swap;
                neighbors.add(temp);
            }
            if(isValid(tx + 1, ty)){
                temp = new Board(blocks);
                swap = temp.blocks[tx][ty];
                temp.blocks[tx][ty] = temp.blocks[tx + 1][ty];
                temp.blocks[tx + 1][ty] =swap;
                neighbors.add(temp);
            }
            if(isValid(tx, ty - 1)){
                temp = new Board(blocks);
                swap = temp.blocks[tx][ty];
                temp.blocks[tx][ty] = temp.blocks[tx][ty - 1];
                temp.blocks[tx][ty - 1] =swap;
                neighbors.add(temp);
            }
            if(isValid(tx, ty + 1)){
                temp = new Board(blocks);
                swap = temp.blocks[tx][ty];
                temp.blocks[tx][ty] = temp.blocks[tx][ty + 1];
                temp.blocks[tx][ty + 1] =swap;
                neighbors.add(temp);
            }
            return neighbors;
    }     // all neighboring boards

    private boolean isValid(int x, int y){
        if(x < dimension && x > -1 && y < dimension && y > -1)
            return true;
        else
            return false;
    }


    public String toString(){
        String output = new String("");
        for(int i = 0; i < dimension; i++){
            for(int j = 0; j < dimension; j++){
                output = output + " " + String.valueOf(blocks[i][j]) + " ";
            }
            output = output + "\r\n";
        }
        return output;
    }               // string representation of this board (in the output format specified below)

    public static void main(String[] args){
        int[][] test = {{0,1,3},{4,2,5},{7,8,6}};
        System.out.println(test[0].length);
        Board testBoard = new Board(test);
        System.out.println(testBoard);
        System.out.println(testBoard.twin());
        System.out.println(testBoard.manhattan());
        System.out.println(testBoard.hamming());
        System.out.println(testBoard.neighbors());
    } // unit tests (not graded)
}
