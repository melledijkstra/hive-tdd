package game;

/**
 * game.Hive game.
 */
public interface Hive {
    /**
     * Play a new tile.
     *
     * @param tileType Tile to play
     * @param q    Q coordinate of hexagon to play to
     * @param r    R coordinate of hexagon to play to
     * @throws IllegalMove If the tile could not be played
     */
    void play(TileType tileType, int q, int r) throws IllegalMove;

    /**
     * Move an existing tile.
     *
     * @param fromQ Q coordinate of the tile to move
     * @param fromR R coordinate of the tile to move
     * @param toQ   Q coordinate of the hexagon to move to
     * @param toR   R coordinare of the hexagon to move to
     * @throws IllegalMove If the tile could not be moved
     */
    void move(int fromQ, int fromR, int toQ, int toR) throws IllegalMove;

    /**
     * Pass the turn.
     *
     * @throws IllegalMove If the turn could not be passed
     */
    void pass() throws IllegalMove;

    /**
     * Check whether the given player is the winner.
     *
     * @param player Player to check
     * @return Boolean
     */
    boolean isWinner(Player player);

    /**
     * Check whether the game is a draw.
     *
     * @return Boolean
     */
    boolean isDraw();

    /**
     * Illegal move exception.
     */
    class IllegalMove extends Exception {
        public IllegalMove() {
            super();
        }

        public IllegalMove(String message) {
            super(message);
        }
    }

    /**
     * Types of tiles.
     */
    enum TileType {QUEEN_BEE, SPIDER, BEETLE, GRASSHOPPER, SOLDIER_ANT}

    /**
     * Players.
     */
    enum Player {WHITE, BLACK}
}