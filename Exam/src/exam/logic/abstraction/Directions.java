package exam.logic.abstraction;

public enum Directions {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UPLEFT(-1, -1),
    UPRIGHT(-1, 1),
    DOWNLEFT(1, -1),
    DOWNRIGHT(1, 1);

    protected int x;
    protected int y;

    Directions(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Directions opposite() {
        switch (this) {
            case UP: return DOWN;
            case DOWN: return UP;
            case LEFT: return RIGHT;
            case RIGHT: return LEFT;
            case UPLEFT: return DOWNLEFT;
            case UPRIGHT: return DOWNRIGHT;
            case DOWNLEFT: return UPLEFT;
            case DOWNRIGHT: return UPRIGHT;
            default: return this;
        }
    }

    public boolean isForward() {
        return this.equals(UP) || this.equals(UPLEFT) || this.equals(UPRIGHT);
    }

    public boolean isDiagonal() {
        return this.equals(UPLEFT) || this.equals(UPRIGHT) || this.equals(DOWNLEFT) || this.equals(DOWNRIGHT);
    }

    @Override
    public String toString() {
        return name().substring(0, 1) + name().toLowerCase().substring(1, name().length());
    }

}