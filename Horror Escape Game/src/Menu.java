import javafx.application.Platform ;
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
 * @version 1.0.0 2021-01-18 Initial implementation
 */
public class Menu
    {

    // TODO: After finishing everything, change the way you get images, music, and
    // etc from what you already have
    // to (name of the class that is using the item).class.getResourceAsStream(
    // "image, sound, music file name" )
    private final Pane menuPane = new Pane() ;

    private Pane rootOfWindow ;
    private Controller helpingPlayerMoveAgainBySettingBooleanFlagToTrue ;

    private final String[] textButton = { "RESUME", "SHOP", "QUIT" } ;
    private Shop gameShop ;
    private MediaPlayer musicForShop ;


    private Menu()
        {

        }


    public Menu( final Pane mainRootOfGame,
                 final Controller makingPlayerMoveAgain,
                 final Shop shop,
                 final MediaPlayer music )
        {
        this.musicForShop = music ;
        this.gameShop = shop ;
        this.rootOfWindow = mainRootOfGame ;
        this.helpingPlayerMoveAgainBySettingBooleanFlagToTrue =
                                        makingPlayerMoveAgain ;
        }


    public void openingMenu()
        {
        this.gameShop.setClosedFlag( false ) ;
        final Rectangle menuShading = new Rectangle( 900, 900, Color.BLACK ) ;
        menuShading.setOpacity( .7 ) ;

        this.menuPane.getChildren().add( menuShading ) ;
        this.rootOfWindow.getChildren().add( this.menuPane ) ;
        textOptionsForPlayer() ;
        }


    public void closeMenu()
        {
        this.gameShop.setClosedFlag( true ) ;
        this.menuPane.getChildren().clear() ;
        this.rootOfWindow.getChildren().remove( this.menuPane ) ;

        }


    private void textOptionsForPlayer()
        {
        for ( int i = 0 ; i < 3 ; i++ )
            {
            final Text textForMenu = new Text( this.textButton[ i ] ) ;
            textForMenu.setScaleX( 5 ) ;
            textForMenu.setScaleY( 5 ) ;
            textForMenu.setX( 360 ) ;
            textForMenu.setY( ( i + 2 ) * 100 ) ;
            textForMenu.setFill( Color.WHITE ) ;
            this.menuPane.getChildren().add( textForMenu ) ;
            textForMenu.setOnMouseEntered( e -> textForMenu.setFill( Color.YELLOW ) ) ;
            textForMenu.setOnMouseExited( e -> textForMenu.setFill( Color.WHITE ) ) ;
            textForMenu.setOnMouseClicked( e ->
                {
                if ( textForMenu.getText().equals( "RESUME" ) )
                    {
                    closeMenu() ;
                    this.helpingPlayerMoveAgainBySettingBooleanFlagToTrue.setCanPlayerMoveOrNot( true ) ;
                    Enemy.setMovingEnemyFlag( true ) ;
                    this.helpingPlayerMoveAgainBySettingBooleanFlagToTrue.resetCounterForOpeningMenu() ;
                    }
                else if ( textForMenu.getText().equals( "SHOP" ) )
                    {
                    // TODO: Implement the shop after everything else is done.
                    this.musicForShop.stop() ;
                    this.gameShop.shopWindow( this.menuPane ) ;
                    this.gameShop.backgroundMusicForShop( this.musicForShop ) ;
                    }
                else if ( textForMenu.getText().equals( "QUIT" ) )
                    {
                    Platform.exit() ;
                    }
                } ) ;
            }

        }

    // TODO: This class should pop up a menu that displays a bunch of
    // buttons/options.

    // The player should be able to see how long they've stayed alive for.

    // The player should be able to see their current health.

    // FUTURE REFERENCE: Once you're done with everything, add a shop option.
    }
// end class Menu