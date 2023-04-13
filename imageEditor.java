
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;

public class imageEditor extends Application{

    String filepath;
    int x = 0;
  
    public void start(Stage stage){
        //main scene
        HBox images = new HBox(100);
        images.setPadding(new Insets(0));
        BorderPane pane = new BorderPane();
        ImageView view = new ImageView();
        ImageView view2 = new ImageView();
        Label label = new Label("Image not found");
        label.setFont(new Font("Arial", 30));
        pane.setTop(label);
        label.setTranslateX(860);
        label.setTranslateY(25);
        
        //file chooser
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("JPG Files", "*.jpg", "*.JPG", "*.jpeg", ".JPEG");
        fc.getExtensionFilters().addAll(extFilter);
        fc.setTitle("Open Image");
        HBox buttons = new HBox(10);
        buttons.setAlignment(Pos.BOTTOM_CENTER);
        buttons.setPadding(new Insets(10));
        
        //buttons for all actions
        Button load = new Button("Load");
        Button save = new Button("Save");
        Button greyScale = new Button("Grey Scale");
        Button mirror = new Button("Mirror Image");
        Button brightnessUp = new Button("Brightness Up");
        Button brightnessDown = new Button("Brightness Down");
        Button sepia_tone = new Button("Sepia Tone");
        Button rotateClockwise = new Button("Rotate Clockwise");
        Button rotateCC = new Button("Rotate Counter Clockwise");
        Button restore = new Button("Undo Changes");
        Button ruin = new Button("Ruin");

        //event for loading image
        load.setOnAction(event -> {
            view2.setImage(null);
            File file = fc.showOpenDialog(null);
            filepath = file.getAbsolutePath();
            Image image = new Image(file.toURI().toString());
            view.setImage(image);
            view.setPreserveRatio(true);
            view.setFitHeight(800);
            view.setFitWidth(600);  
            label.setText("Image Found");
        });
        
        //event for greyscale
        greyScale.setOnAction(event ->{
            File file = new File(filepath);
            Image original = new Image(file.toURI().toString());
            ColorAdjust grayify = new ColorAdjust();
            view2.setImage(original);
            grayify.setSaturation(-1);
            view2.setEffect(grayify);
            view2.setPreserveRatio(true);
            view2.setFitHeight(800);
            view2.setFitWidth(600); 
        });
        
        //event for mirroring
        mirror.setOnAction(event ->{
            File file = new File(filepath);
            Image original = new Image(file.toURI().toString());
            view2.setImage(original);
            view2.setScaleX(-1);
            view2.setPreserveRatio(true);
            view2.setFitHeight(800);
            view2.setFitWidth(600); 
        });

        //event for brightnes u
        brightnessUp.setOnAction(event ->{
            File file = new File(filepath);
            Image original = new Image(file.toURI().toString());
            view2.setImage(original);
            ColorAdjust bright = new ColorAdjust();
            bright.setBrightness(0.2);
            view2.setEffect(bright);
            view2.setPreserveRatio(true);
            view2.setFitHeight(800);
            view2.setFitWidth(600); 
        });

        //event for brightness down
        brightnessDown.setOnAction(event ->{
            File file = new File(filepath);
            Image original = new Image(file.toURI().toString());
            view2.setImage(original);
            ColorAdjust dark = new ColorAdjust();
            dark.setBrightness(-0.2);
            view2.setEffect(dark);
            view2.setPreserveRatio(true);
            view2.setFitHeight(800);
            view2.setFitWidth(600); 
        });
        
        //sepia tone
        sepia_tone.setOnAction(event ->{    
            File file = new File(filepath);
            Image original = new Image(file.toURI().toString());
            view2.setImage(original);
            SepiaTone sepiaTone = new SepiaTone();
            sepiaTone.setLevel(0.9);
            view2.setEffect(sepiaTone);
            view2.setPreserveRatio(true);
            view2.setFitHeight(800);
            view2.setFitWidth(600); 
        });

        //restore image
        restore.setOnAction(event ->{   
            view2.setImage(null);  
            view2.setEffect(null);  
            view2.setRotate(0);
            view2.setScaleX(1);  
        });
        
        //rotateleft
        rotateCC.setOnAction(event ->{
            File file = new File(filepath);
            Image original = new Image(file.toURI().toString());
            view2.setImage(original);
            view2.setRotate(270);
            view2.setPreserveRatio(true);
            view2.setFitHeight(800);
            view2.setFitWidth(600); 
        });       
        
        rotateClockwise.setOnAction(event ->{
            view2.setImage(null);
            File file = new File(filepath);
            Image original = new Image(file.toURI().toString());
            view2.setImage(original);
            view2.setRotate(90);
            view2.setPreserveRatio(true);
            view2.setFitHeight(800);
            view2.setFitWidth(600); 
        }); 

        //ruin the image
        ruin.setOnAction(event ->{
            view2.setImage(null);
            File file = new File(filepath);
            Image original = new Image(file.toURI().toString());
            view2.setImage(original);
            ColorAdjust sharp = new ColorAdjust();
            SepiaTone sepia = new SepiaTone();
            sepia.setLevel(1);
            sharp.setBrightness(.2);
            sharp.setContrast(.5);
            sharp.setHue(-.05);
            sharp.setSaturation(1);
            view2.setEffect(sepia);
            view2.setEffect(sharp);
            view2.setPreserveRatio(true);
            view2.setFitHeight(800);
            view2.setFitWidth(600); 
        });
        
        //button for saving change address to what you want
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                
                File outputFile = new File("C:/CS_2300/photoEditor/PhotoEdits/formattedPicture"+x+".png");
                BufferedImage bImage = SwingFXUtils.fromFXImage(view2.snapshot(null, null), null);
                x = x + 1;
                try {
                    ImageIO.write(bImage, "png", outputFile);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                
            }
        });

        //displaying things
        buttons.getChildren().addAll(load, save, restore, greyScale, sepia_tone, brightnessUp, brightnessDown, ruin, mirror, rotateCC, rotateClockwise );
        pane.setBottom(buttons);
        
        // display scene
        images.getChildren().addAll(view, view2);
        pane.setCenter(images);
        images.setAlignment(Pos.CENTER);
        Scene scene = new Scene(pane, 1900, 1000);
        stage.setScene(scene);
        stage.show();   
    }
    
    //launch args
    public static void main(String args[]){
        launch(args);
    }
}
    
    
    
    
    
    
    
    
    

