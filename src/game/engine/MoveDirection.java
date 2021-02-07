package game.engine;

public enum MoveDirection {
    LEFT,UP,RIGHT,DOWN;
    public String toString(){
        switch(this){
            case LEFT:
                return "Left";
            case UP:
                return "Up";
            case RIGHT:
                return "Right";
            default:
                return "Down";
        }
    }
}
