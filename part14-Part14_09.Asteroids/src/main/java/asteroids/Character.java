/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package asteroids;

/**
 *
 * @author calin
 */
import javafx.geometry.Point2D;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;
public abstract class Character {
    
    private Polygon character;
    private Point2D movement;
    private boolean isAlive;
    
    public Character(Polygon polygon, int x, int y) {
        this.character = polygon;
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);

        this.movement = new Point2D(0, 0);
        this.isAlive = true;
    }

     public void setAlive(boolean isAlive){
        this.isAlive = isAlive;
    }
     
    public boolean isAlive(){
        return this.isAlive;
    }

    public Polygon getCharacter() {
        return character;
    }

    public void turnLeft() {
        this.character.setRotate(this.character.getRotate() - 5);
    }

    public void turnRight() {
        this.character.setRotate(this.character.getRotate() + 5);
    }


    
    public void move() {
        this.character.setTranslateX(this.character.getTranslateX() + this.movement.getX());
        this.character.setTranslateY(this.character.getTranslateY() + this.movement.getY());

        if (this.character.getTranslateX() < 0) {
            this.character.setTranslateX(this.character.getTranslateX() + AsteroidsApplication.Width);
        }

        if (this.character.getTranslateX() > AsteroidsApplication.Width) {
            this.character.setTranslateX(this.character.getTranslateX() % AsteroidsApplication.Width);
        }

        if (this.character.getTranslateY() < 0) {
            this.character.setTranslateY(this.character.getTranslateY() + AsteroidsApplication.Height);
        }

        if (this.character.getTranslateY() > AsteroidsApplication.Height) {
            this.character.setTranslateY(this.character.getTranslateY() % AsteroidsApplication.Height);
        }
    }

    
    public Point2D getMovement() {
        return this.movement;
    }

    public void setMovement(Point2D newPoint) {
        this.movement = newPoint;
    }
    
    public void recenter(double x, double y) {
        this.character.setTranslateX(x);
        this.character.setTranslateY(y);
        this.movement = new Point2D(0, 0); // Reset movement to (0, 0)
    }

    public void accelerate() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

        changeX *= 0.05;
        changeY *= 0.05;

        this.movement = this.movement.add(changeX, changeY);
    }
    
    
    public void deaccelerate() {
        double changeX = Math.cos(Math.toRadians(this.character.getRotate()));
        double changeY = Math.sin(Math.toRadians(this.character.getRotate()));

        changeX *= -0.05;
        changeY *= -0.05;

        this.movement = this.movement.add(changeX, changeY);
    }
    
    public boolean collide(Character other) {
        Shape collisionArea = Shape.intersect(this.character, other.getCharacter());
        return collisionArea.getBoundsInLocal().getWidth() != -1;
    }
    
    public void despawn(){
        isAlive=false;
    }
}
