public class Point {
    private float x;
    private float y;
    public Point(float x, float y){
        this.x = x;
        this.y = y;
    }
    public float getX(){return x;}
    public float getY(){return y;}

    public void update(float x, float y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y + ")";
    }
}
public class PointTest {
    public static void main(String[] args) {
        Point p1 = new Point(10.0f, 20.0f);
        System.out.println("Point 1: " + p1);

        p1.update(15.0f, 25.0f);
        System.out.println("Point 1 updated: " + p1);
    }
}
