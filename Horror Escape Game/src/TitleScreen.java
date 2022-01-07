import java.net.URISyntaxException ;

import javafx.animation.FadeTransition ;
import javafx.scene.Scene ;
import javafx.scene.image.Image ;
import javafx.scene.layout.Background ;
import javafx.scene.layout.BackgroundImage ;
import javafx.scene.layout.BackgroundRepeat ;
import javafx.scene.layout.BackgroundSize ;
import javafx.scene.layout.Pane ;
import javafx.scene.media.Media ;
import javafx.scene.media.MediaPlayer ;
import javafx.scene.paint.Color ;
import javafx.scene.text.Text ;
import javafx.util.Duration ;

/**
 *
 */

/**
 * @author nguyent68
 * @version 1.0.0 2021-02-06 Initial implementation
 */
public class TitleScreen extends Main
    {

    private final String[] selectionOnTitleScreen = { "START", "STORE", "OPTION" } ;
    private MediaPlayer playMusic ;
    private final Shop shopForGame = new Shop() ;


    private TitleScreen()
        {}


    public TitleScreen( final Pane rootOfScreen, final Scene sceneOfWindow )
        {
        // DONE: Create background image for title screen.
        backgroundImageForTitle( rootOfScreen ) ;
        creatingTextForTitleScreen( rootOfScreen, sceneOfWindow ) ;
        fadeTransitionForTitleScreen( rootOfScreen ) ;
        backgroundMusic() ;
        }


    private void backgroundMusic()
        {
        Media backgroundMusicForTitleScreen ;
        try
            {
            backgroundMusicForTitleScreen = new Media( Main.class.getResource( "Shattered-Mind.mp3" )
                                                                 .toURI()
                                                                 .toString() ) ;
            this.playMusic = new MediaPlayer( backgroundMusicForTitleScreen ) ;
            this.playMusic.setOnEndOfMedia( () -> TitleScreen.this.playMusic.seek( Duration.ZERO ) ) ;
            this.playMusic.setAutoPlay( true ) ;
            this.playMusic.play() ;
            }
        catch ( final URISyntaxException e )
            {
            // TODO Auto-generated catch block
            e.printStackTrace() ;
            }
        }


    private void backgroundImageForTitle( final Pane root )
        {
        final Image backgroundImageOfTitleScreen =
                                        new Image( TitleScreen.class.getResourceAsStream( "TitleScreenPicture.jpg" ),
                                                   900,
                                                   900,
                                                   false,
                                                   false ) ;
        root.setBackground( new Background( new BackgroundImage( backgroundImageOfTitleScreen,
                                                                 BackgroundRepeat.NO_REPEAT,
                                                                 BackgroundRepeat.NO_REPEAT,
                                                                 null,
                                                                 BackgroundSize.DEFAULT ) ) ) ;

        }


    private void creatingTextForTitleScreen( final Pane root,
                                             final Scene sceneOfWindow )
        {

        for ( int i = 0 ; i < 3 ; i++ )
            {
            final Text textForMenu = new Text( this.selectionOnTitleScreen[ i ] ) ;
            textForMenu.setScaleX( 3 ) ;
            textForMenu.setScaleY( 3 ) ;
            textForMenu.setX( 100 ) ;
            textForMenu.setY( ( i + 4 ) * 90 ) ;
            textForMenu.setFill( Color.WHITE ) ;
            root.getChildren().add( textForMenu ) ;
            textForMenu.setOnMouseEntered( e -> textForMenu.setFill( Color.YELLOW ) ) ;
            textForMenu.setOnMouseExited( e -> textForMenu.setFill( Color.WHITE ) ) ;
            textForMenu.setOnMouseClicked( e ->
                {
                if ( textForMenu.getText().equals( "START" ) )
                    {
                    this.playMusic.stop() ;
                    root.getChildren().clear() ;
                    backgroundMusicForGame() ;
                    super.creatingMap( root,
                                       sceneOfWindow,
                                       this.shopForGame,
                                       this.playMusic ) ;
                    }
                else if ( textForMenu.getText().equals( "STORE" ) )
                    {
                    // DONE: Create shop class.
                    this.shopForGame.setPlayerPoint( super.getPlayerPoint() ) ;
                    this.playMusic.stop() ;
                    this.shopForGame.shopWindow( root ) ;
                    this.shopForGame.backgroundMusicForShop( this.playMusic ) ;
                    }
                else if ( textForMenu.getText().equals( "OPTION" ) )
                    {
                    // DONE: Create an option window.
                    final Option optionMenu = new Option( root,
                                                          this.playMusic,
                                                          sceneOfWindow ) ;
                    }
                } ) ;
            }
        }


    private void backgroundMusicForGame()
        {
        Media backgroundMusicForGame ;
        final double adjustToThisVolume = this.playMusic.getVolume() ;
        try
            {
            backgroundMusicForGame = new Media( Main.class.getResource( "2019-03-17_-_Too_Crazy_-_David_Fesliyan.mp3" )
                                                          .toURI()
                                                          .toString() ) ;
            this.playMusic = new MediaPlayer( backgroundMusicForGame ) ;
            this.playMusic.setVolume( adjustToThisVolume ) ;
            this.playMusic.setOnEndOfMedia( () -> TitleScreen.this.playMusic.seek( Duration.ZERO ) ) ;
            this.playMusic.setAutoPlay( true ) ;
            this.playMusic.play() ;
            }
        catch ( final URISyntaxException e )
            {
            // TODO Auto-generated catch block
            e.printStackTrace() ;
            }
        }


    private void fadeTransitionForTitleScreen( final Pane root )
        {
        final FadeTransition fadeTransitionForTitleScreen =
                                        new FadeTransition( Duration.millis( 3000 ),
                                                            root ) ;
        fadeTransitionForTitleScreen.setFromValue( 0 ) ;
        fadeTransitionForTitleScreen.setToValue( 1 ) ;
        fadeTransitionForTitleScreen.play() ;
        }

    }
// end class TitleScreen