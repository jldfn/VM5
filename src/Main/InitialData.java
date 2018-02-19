package Main;

import java.util.function.Function;

public class InitialData {
    private final static InitialData instance = new InitialData();
    private Function<Point, Double> func = (point) -> Math.cos(point.getX()) * point.getY();
    //private Function<Point, Double> func = (point) -> 10*point.getX()-2*point.getY();
   //private Function<Point, Double> func = (point) ->point.getX()*point.getX() -2*point.getY();
    private Point[][] sets;

    private InitialData() {
    }

    public static InitialData getInstance() {
        return instance;
    }
    public Function<Point, Double> getFunc() {
        return func;
    }
    public String getFuncToString() {
        return "y''=x+y+y'";
        //return "y'=x^2-2y";
        //return "y'=10x - 2y(x)";
    }
    public Point[] getSet(int n) {
        if ((n < 0) || (n > 3)) {
            throw new IllegalArgumentException();
        }
        return sets[n];
    }
}