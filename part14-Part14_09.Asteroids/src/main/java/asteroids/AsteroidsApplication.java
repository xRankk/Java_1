package asteroids;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.shape.Polygon;
import javafx.scene.input.KeyCode;
import java.util.Map;
import java.util.HashMap;
import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import javafx.scene.text.Text;
import java.util.concurrent.atomic.AtomicInteger;
import javafx.geometry.Pos;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.effect.ColorInput;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.CornerRadii;
import java.awt.Insets;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import java.awt.Toolkit;
import javax.imageio.ImageIO;
import java.io.File;
import javafx.stage.Screen;
import javafx.geometry.Rectangle2D;

public class AsteroidsApplication extends Application{

    public static int Height;
    public static int Width;
    public static Point2D center=new Point2D(Width/2,Height/2);
    public static void main(String[] args) {
        launch(AsteroidsApplication.class);
    }

    public static int partsCompleted() {
        return 4;
    }

    public void start(Stage window){
        //extra -- menu
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        Width = (int) bounds.getWidth();
        Height = (int) bounds.getHeight();
        window.setFullScreen(true);
        BorderPane menu=new BorderPane();
        menu.setPrefSize(Width, Height);
        VBox allButtons=new VBox();
        allButtons.setAlignment(Pos.CENTER);
        allButtons.setSpacing(20);
        Image image=new Image("file:C:\\Users\\calin\\OneDrive\\Desktop\\Asteroids-the-universe-39056253-1600-900.jpg");
        Image image2=new Image("file:C:\\Users\\calin\\OneDrive\\Desktop\\pngtree-galaxy-and-planet-background-with-falling-meteor-asteroid-illustrations-image_452589.jpg");
        //ImageView imageView=new ImageView(image);
        //BackgroundSize bSize = new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false);
        BackgroundImage bgImage=new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true)
        );
        BackgroundImage bgImage2=new BackgroundImage(
                image2,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, false, true)
        );
        //then i think a better mode to make the image on background ajutable is 
        Background bg=new Background(bgImage);
        Background bg2=new Background(bgImage2);
        menu.setBackground(bg2);
        
        
        Button play=new Button("Play");
        play.getStyleClass().add("custom-button");
        Button settings=new Button("Settings");
        settings.getStyleClass().add("custom-button");
        Button exit=new Button("Exit");
        exit.getStyleClass().add("custom-button");
        
        
        play.setPrefSize(100,50);
        settings.setPrefSize(100,50);
        exit.setPrefSize(100,50);
        allButtons.getChildren().addAll(play,settings,exit);
        
        menu.setCenter(allButtons);
        BorderPane finalMenu=new BorderPane();
        finalMenu.setPrefSize(Width, Height);
        
        VBox endButtons=new VBox();
        endButtons.setAlignment(Pos.CENTER);
        endButtons.setSpacing(20);
        
        Text score = new Text(10, 20, "Points: 0");
        Button mainMenuButton=new Button("Main menu");
        Button exit2=new Button("Exit");
        endButtons.getChildren().addAll(score,mainMenuButton,exit2);
        finalMenu.setCenter(endButtons);
        finalMenu.setBackground(bg);
        
        
        //app
        Pane pane = new Pane();
        pane.setPrefSize(Width, Height);
        Text text = new Text(10, 20, "Points: 0");
        
        AtomicInteger points = new AtomicInteger();
        
        finalMenu.setTop(text);
        
        Ship ship=new Ship(Width/2,Height/2);
        List<Asteroid> asteroids = new ArrayList<>();
        List<Projectile> projectiles = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Random rnd = new Random();
            Asteroid asteroid = new Asteroid(rnd.nextInt(Width/3), rnd.nextInt(Height));
            asteroids.add(asteroid);
        }

        
        
        pane.getChildren().add(text);
        pane.getChildren().add(ship.getCharacter());
        asteroids.forEach(asteroid->pane.getChildren().add(asteroid.getCharacter()));
        
        Scene scene=new Scene(pane);
        Scene mainMenu=new Scene(menu);
        Scene endMenu=new Scene(finalMenu);
        
        
        scene.getStylesheets().add("styles.css");
        mainMenu.getStylesheets().add("styles.css");
        endMenu.getStylesheets().add("styles.css");
        //buttons
        exit.setOnAction((event)->{
            System.exit(0);
        });
        play.setOnAction((event)->{
            window.setScene(scene);
            window.setFullScreen(true);
            window.setFullScreenExitHint("");
        });
        
        mainMenuButton.setOnAction((event)->{
            asteroids.stream().forEach(asteroid->asteroid.despawn());
            asteroids.stream()
                                .filter(a-> !a.isAlive())
                                .forEach(a -> pane.getChildren().remove(a.getCharacter()));
                        asteroids.removeAll(asteroids.stream()
                                .filter(a -> !a.isAlive())
                                .collect(Collectors.toList()));
                        projectiles.stream().forEach(projectile->projectile.setAlive(false));
                        projectiles.stream()
                                    .filter(projectile -> !projectile.isAlive())
                                    .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));
                        projectiles.removeAll(projectiles.stream()
                                    .filter(projectile -> !projectile.isAlive())
                                    .collect(Collectors.toList()));
                        projectiles.clear();
                        asteroids.clear();
                        asteroids.stream().forEach(a->System.out.println(a));
            asteroids.clear();
            projectiles.clear();
            ship.recenter(AsteroidsApplication.Width / 2, AsteroidsApplication.Height / 2);
            // Update the score variable and set the text of the score Text node
            score.setText("Points: " + points.get());
             // Reset the points variable and update the in-game score Text node
            points.set(0);
            text.setText("Points: 0");
            window.setScene(mainMenu);
            window.setFullScreen(true);
            window.setFullScreenExitHint("");
        });
        exit2.setOnAction((event)->{
            System.exit(0);
        });
        //
        Map<KeyCode, Boolean> pressedKeys = new HashMap<>();
        
        scene.setOnKeyPressed(event -> {
        pressedKeys.put(event.getCode(),Boolean.TRUE);
        });

        scene.setOnKeyReleased((event)->{
            pressedKeys.put(event.getCode(), Boolean.FALSE);
        });
        
        
        Point2D movement = new Point2D(1, 1);
        
        new AnimationTimer() {

            @Override
            public void handle(long now) {
                if(pressedKeys.getOrDefault(KeyCode.A, false)) {
                     ship.turnLeft();
                }

                if(pressedKeys.getOrDefault(KeyCode.D, false)) {
                    ship.turnRight();
                }
                if(pressedKeys.getOrDefault(KeyCode.W, false)) {
                    ship.accelerate();
                }
                if(pressedKeys.getOrDefault(KeyCode.S, false)) {
                    ship.deaccelerate();
                }
                if (pressedKeys.getOrDefault(KeyCode.SPACE, false)&& projectiles.size() < 3) {
                    // we shoot
                    Projectile projectile = new Projectile((int) ship.getCharacter().getTranslateX(), (int) ship.getCharacter().getTranslateY());
                    projectile.getCharacter().setRotate(ship.getCharacter().getRotate());
                    projectiles.add(projectile);
                    projectile.accelerate();
                    projectile.setMovement(projectile.getMovement().normalize().multiply(3));

                    
                    pane.getChildren().add(projectile.getCharacter());
                }
                ship.move();
                asteroids.forEach(asteroid->asteroid.move());
                projectiles.forEach(projectile -> projectile.move());
                asteroids.forEach(asteroid -> {
                    if (ship.collide(asteroid)) {
                        score.setText(text.getText());
                        points.set(0);
                        window.setScene(endMenu);
                        window.setFullScreen(true);
                        window.setFullScreenExitHint("");
                    }
                });
                projectiles.forEach(projectile -> {
                    asteroids.forEach(asteroid -> {
                        if(projectile.collide(asteroid)) {
                            projectile.setAlive(false);
                            asteroid.setAlive(false);
                            
                        }
                    });
                    if(!projectile.isAlive()) {
                        text.setText("Points: " + points.addAndGet(1000));
                    }
                });

                projectiles.stream()
                                    .filter(projectile -> !projectile.isAlive())
                                    .forEach(projectile -> pane.getChildren().remove(projectile.getCharacter()));
                projectiles.removeAll(projectiles.stream()
                        .filter(projectile -> !projectile.isAlive())
                        .collect(Collectors.toList()));

                asteroids.stream()
                                .filter(asteroid -> !asteroid.isAlive())
                                .forEach(asteroid -> pane.getChildren().remove(asteroid.getCharacter()));
                asteroids.removeAll(asteroids.stream()
                                .filter(asteroid -> !asteroid.isAlive())
                                .collect(Collectors.toList()));
                if(Math.random() < 0.005) {
                    Asteroid asteroid = new Asteroid(Width, Height);
                    if(!asteroid.collide(ship)) {
                        asteroids.add(asteroid);
                        pane.getChildren().add(asteroid.getCharacter());
                    }
                }
            }
        }.start();
        window.setScene(mainMenu);
        window.setTitle("Asteroids!");
        window.show();
    }
}
