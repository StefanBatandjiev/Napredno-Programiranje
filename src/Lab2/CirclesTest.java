package src.Lab2;

import com.sun.jdi.InterfaceType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

enum TYPE {
    POINT,
    CIRCLE
}

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
}
interface Movable{

    public void moveUp();
    public void moveDown();
    public void moveRight();
    public void moveLeft();
    int getCurrentXPosition();
    int getCurrentYPosition();
}

class MovablePoint implements Movable{

    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;

    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
    public void moveUp() {

    }

    @Override
    public void moveDown() {

    }

    @Override
    public void moveRight() {

    }

    @Override
    public void moveLeft() {

    }

    @Override
    public int getCurrentXPosition() {
        return x;
    }

    @Override
    public int getCurrentYPosition() {
        return y;
    }

    public String toString(){
        return String.format("Movable point with coordinates (%d,%d)",x,y);
    }
}

class MovableCircle implements Movable{

    private int radius;
    private MovablePoint center;

    public MovableCircle(int radius, MovablePoint center) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public void moveUp() {

    }

    @Override
    public void moveDown() {

    }

    @Override
    public void moveRight() {

    }

    @Override
    public void moveLeft() {

    }

    @Override
    public int getCurrentXPosition() {
        return 0;
    }

    @Override
    public int getCurrentYPosition() {
        return 0;
    }

    public String toString(){
        return String.format("Movable circle with center coordinates (%d,%d) and radius %d",
                center.getCurrentXPosition(),center.getCurrentYPosition(),radius);
    }
}

class MovablesCollection{

    private Movable [] movables;
    private static int x_MAX = 0;
    private static int y_MAX = 0;

    public MovablesCollection(int x_MAX,int y_MAX){
        MovablesCollection.x_MAX = x_MAX;
        MovablesCollection.y_MAX = y_MAX;
        this.movables =  new Movable[0];
    }


    public static void setxMax(int i) {
        MovablesCollection.x_MAX = i;
    }

    public static void setyMax(int i) {
        MovablesCollection.y_MAX = i;
    }

    public void addMovableObject(Movable m) throws MovableObjectNotFittableException {
        if(m.getCurrentXPosition()<0&&m.getCurrentXPosition()>x_MAX&&m.getCurrentYPosition()<0&&m.getCurrentYPosition()>y_MAX)
            throw new MovableObjectNotFittableException();
        else movables[movables.length+1] = m;
    }

    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction){

    }

    public String toString(){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Collection of movable objects with size ["+movables.length+"]:\n");
        for(Movable movable:movables){
            stringBuilder.append(movable.toString()+"\n");
        }
        return stringBuilder.toString();
    }
}

class MovableObjectNotFittableException extends Exception{
    public MovableObjectNotFittableException() {
        super("Movable object is not fittable");
    }
}


public class CirclesTest {

    public static void main(String[] args) throws MovableObjectNotFittableException {

        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);

            if (Integer.parseInt(parts[0]) == 0) { //point
                collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
            } else { //circle
                int radius = Integer.parseInt(parts[5]);
                collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
            }

        }
        System.out.println(collection.toString());

        System.out.println("MOVE POINTS TO THE LEFT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES DOWN");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
        System.out.println(collection.toString());

        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);

        System.out.println("MOVE POINTS TO THE RIGHT");
        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
        System.out.println(collection.toString());

        System.out.println("MOVE CIRCLES UP");
        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
        System.out.println(collection.toString());
    }

}

