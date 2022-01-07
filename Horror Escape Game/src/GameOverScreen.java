import javafx.animation.AnimationTimer ;
import javafx.animation.FadeTransition ;
import javafx.geometry.Insets ;
import javafx.scene.layout.Background ;
import javafx.scene.layout.BackgroundFill ;
import javafx.scene.layout.CornerRadii ;
import javafx.scene.layout.Pane ;
import javafx.scene.paint.Color ;
import javafx.scene.text.Text ;
import javafx.util.Duration ;

/**
 * 
 */

/**
 * 
 *
 * @author nguyent68
 * @version 1.0.0 2021-04-10 Initial implementation
 *
 */
public class GameOverScreen
    {
    private GameOverScreen() {}
    
    public GameOverScreen(Pane rootOfScreen) {
    rootOfScreen.getChildren().clear();
    rootOfScreen.setBackground( new Background(new BackgroundFill(Color.BLACK,  
                                                                 CornerRadii.EMPTY, Insets.EMPTY)) );
    gameOverFeatures(rootOfScreen);
    }
    
    
    private void gameOverFeatures(Pane rootOfScreen) {
    Text gameOverText = new Text("Game Over...");
    gameOverText.setY( 400 );
    gameOverText.setX( 350 );
    gameOverText.setScaleX( 4 );
    gameOverText.setScaleY( 4 );
    gameOverText.setFill( Color.ORANGERED );
    rootOfScreen.getChildren().add( gameOverText );
    //TODO: Add options for the players here.
    
    //1. Can go back to main menu.
    
    //2. Can go to shop.
    
    //OPTIONAL TODO: Add a picture to the background to make it look better.
    
    //TODO: Display score for player.
    
    fadeAnimationForGameOverScreen(gameOverText);
    }
    
    private void fadeAnimationForGameOverScreen(Text gameOverText) {
    final FadeTransition fadeTransitionForTitleScreen =
                                    new FadeTransition( Duration.millis( 3000 ),
                                                        gameOverText ) ;
    fadeTransitionForTitleScreen.setFromValue( 0 ) ;
    fadeTransitionForTitleScreen.setToValue( 1 ) ;
    
    //There's a minor glitch with the fade transition. There is a moment where the text is
    //shown on screen immediately, disappears, and fades in.
    //TODO: I want the texts to fade in, NOT appear, disappear, then fade in.
    fadeTransitionForTitleScreen.play() ;
    }
    }
	// end class GameOverScreen