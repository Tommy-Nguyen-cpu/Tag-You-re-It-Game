import javafx.scene.Scene ;
import javafx.scene.control.TextField ;
import javafx.scene.image.Image ;
import javafx.scene.image.ImageView ;
import javafx.scene.input.KeyCode ;
import javafx.scene.layout.Pane ;
import javafx.scene.media.MediaPlayer ;
import javafx.scene.paint.Color ;
import javafx.scene.shape.Rectangle ;
import javafx.scene.text.Text ;

/**
 *
 */

/**
 * @author nguyent68
 * @version 1.0.0 2021-05-06 Initial implementation
 */
public class Option
    {

    /**
     * @param rootOfScreen
     * @param sceneOfWindow
     */

    private final Pane optionWindowPane = new Pane() ;
    private static int openedOptionsMenu = 1 ;
    private final Image backButton = new Image( Main.class.getResourceAsStream( "BackButton.png" ) ) ;
    private final ImageView backButtonOnScreen = new ImageView( this.backButton ) ;
    private final TextField volumeSize = new TextField() ;
    private final Text info = new Text( "Volume Of the Music(0 = mute, 100 = full volume):" ) ;


    private Option()
        {}


    public Option( final Pane root,
                   final MediaPlayer music,
                   final Scene windowScene )
        {
        creatingBlackBox( windowScene ) ;
        if ( openedOptionsMenu == 1 )
            {
            openedOptionsMenu++ ;
            this.backButtonOnScreen.setX( -310 ) ;
            this.backButtonOnScreen.setY( -240 ) ;
            this.backButtonOnScreen.setScaleX( .17 ) ;
            this.backButtonOnScreen.setScaleY( .17 ) ;
            this.backButtonOnScreen.setOnMouseClicked( e ->
                {
                this.optionWindowPane.getChildren().clear() ;
                root.getChildren().remove( this.optionWindowPane ) ;
                openedOptionsMenu = 1 ;
                } ) ;
            this.optionWindowPane.getChildren().add( this.backButtonOnScreen ) ;
            root.getChildren().add( this.optionWindowPane ) ;
            adjustVolume( music ) ;
            }
        }


    private void adjustVolume( final MediaPlayer music )
        {
        // DONE: Create a visual/box so that the player can adjust the volume of the
        // music.
        this.volumeSize.setLayoutX( 300 ) ;
        this.volumeSize.setLayoutY( 400 ) ;
        this.info.setX( 230 ) ;
        this.info.setY( 350 ) ;
        this.info.setFill( Color.WHITE ) ;
        this.optionWindowPane.getChildren().addAll( this.volumeSize, this.info ) ;
        getText( music, this.volumeSize ) ;
        }


    private void getText( final MediaPlayer music,
                          final TextField textBox )
        {
        textBox.setOnKeyPressed( e ->
            {
            if ( e.getCode() == KeyCode.ENTER )
                {
                final String textToGetDigits = textBox.getText() ;
                final int result = toDigit( textToGetDigits ) ;
                if ( ( result <= 100 ) && ( result > -1 ) )
                    {
                    final double adjustToThisVolume = result / 100.0 ;
                    music.setVolume( adjustToThisVolume ) ;
                    textBox.clear() ;
                    }
                }
            } ) ;
        }


    private static int toDigit( final String text )
        {
        int integerResult = 0 ;
        for ( int i = 0 ; i < text.length() ; i++ )
            {
            final char currentCharacter = text.charAt( i ) ;
            if ( Character.isDigit( currentCharacter ) )
                {
                integerResult = ( integerResult * 10 ) + ( currentCharacter - '0' ) ;
                }
            else
                {
                return -1 ;
                }
            }
        return integerResult ;
        }


    private void creatingBlackBox( final Scene windowScene )
        {
        final Rectangle rectangle = new Rectangle() ;
        rectangle.setFill( Color.BLACK ) ;
        rectangle.setWidth( windowScene.getWidth() ) ;
        rectangle.setHeight( windowScene.getHeight() ) ;
        this.optionWindowPane.getChildren().add( rectangle ) ;
        }
    }
// end class Option