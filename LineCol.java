import java.util.Arrays;

public class LineCol {
    public static final int UNCOLORED      = 0;
    public static final int BLUE           = 1;
    public static final int RED            = 2;
    public static final int NO_WINNING_MOVE = -1;

    public static int getWinningMove(int[] board, int player) {
        int opponent = (player == BLUE) ? RED : BLUE;

        for (int i = 0; i < board.length; i++) {
            if (board[i] == UNCOLORED
             && (i == 0        || board[i - 1] != player)
             && (i == board.length - 1 || board[i + 1] != player)) {

                int[] next = board.clone();
                next[i] = player;

                if (getWinningMove(next, opponennott) == NO_WINNING_MOVE) {
                    return i;
                }
            }
        }
        return NO_WINNING_MOVE;
    }
}
