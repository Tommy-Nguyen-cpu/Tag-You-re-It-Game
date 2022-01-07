import java.util.Random ;

import javafx.animation.AnimationTimer ;
import javafx.application.Application ;
import javafx.geometry.Insets ;
import javafx.scene.Scene ;
import javafx.scene.image.ImageView ;
import javafx.scene.layout.Background ;
import javafx.scene.layout.BackgroundFill ;
import javafx.scene.layout.CornerRadii ;
import javafx.scene.layout.Pane ;
import javafx.scene.media.MediaPlayer ;
import javafx.scene.paint.Color ;
import javafx.scene.shape.Rectangle ;
import javafx.scene.text.Text ;
import javafx.stage.Stage ;

/**
 *
 */

/**
 * @author nguyent68
 * @version 1.0.0 2020-12-26 Initial implementation
 */

// This class serves no purpose other than testing how things are looking and working
// so far.
public class Main extends Application
    {

    private Stage windowOfScreen ;
    private final Rectangle entranceOrExit =
                                    new Rectangle( 100,
                                                   100,
                                                   javafx.scene.paint.Color.YELLOW ) ;
    private Player playerVariable ;

    private int indexOfMapBorderToRemove = -1 ;

    private final Color[] borderColors = { Color.AQUAMARINE, Color.CORAL,
                                           Color.GREENYELLOW, Color.ORANGERED,
                                           Color.HOTPINK } ;

    private final Random generator = new Random() ;
    private int noCollisionCounter = 0 ;

    private int playerPoints = 0 ;
    private Shop gameShop ;
    private MediaPlayer musicForShop ;

    private Text playerPointDisplay ;

    private Enemy[] enemyArray ;


    // Change of plans. It's still going to be an endless runner, but the player has
    // the option to attack the enemy.
    // There will be safe places, but the places can only last for so long while the
    // player is within the safe area.
    // Once the timer runs out, the safe place will "shut down" (It should look like
    // an area of effect and as the timer shrinks, the area of the safe place will
    // also shrink).
    // NOTE: ALL BORDER AND OBJECTS NEED TO BE A MULTIPLE OF 5 IN ORDER FOR COLLISION
    // TO WORK.
    // If you want to make it so the player can increase their speed, you'll need to
    // change the multiple of 5 to something thats better.
    public Pane mapBorder()
        {
        final Pane mapBorderRoot = new Pane() ;
        for ( int i = 0 ; i < 1125 ; i = i + 75 )
            {
            int yGenerator = this.generator.nextInt( 800 ) ;
            final Rectangle rectangle = new Rectangle( 40,
                                                       40,
                                                       this.borderColors[ this.generator.nextInt( 5 ) ] ) ;
            rectangle.setX( i % 800 ) ;
            while ( ( yGenerator % 10 ) != 0 )
                {

                yGenerator = this.generator.nextInt( 800 ) ;
                }
            rectangle.setY( yGenerator ) ;
            mapBorderRoot.getChildren().forEach( e ->
                {
                if ( !rectangle.getBoundsInLocal()
                               .intersects( e.getBoundsInLocal() ) )
                    {
                    this.noCollisionCounter++ ;
                    }
                } ) ;
            if ( this.noCollisionCounter == mapBorderRoot.getChildren().size() )
                {
                mapBorderRoot.getChildren().add( rectangle ) ;
                }
            this.noCollisionCounter = 0 ;
            }
        return mapBorderRoot ;
        }


    private void entranceOrExitToNewMap()
        {
        final boolean isLeftHorizontalOrRightHorizontal =
                                        this.generator.nextBoolean() ;
        if ( isLeftHorizontalOrRightHorizontal )
            {
            this.entranceOrExit.setX( this.generator.nextBoolean()
                ? -60
                    : 770 ) ;
            this.entranceOrExit.setY( this.generator.nextInt( 700 ) ) ;
            }
        else
            {
            this.entranceOrExit.setY( this.generator.nextBoolean()
                ? -60
                    : 770 ) ;
            this.entranceOrExit.setX( this.generator.nextInt( 700 ) ) ;
            }

        }


    private void checkingForPlayerAndMapBorderCollision( final Pane mapBorder )
        {
        mapBorder.getChildren().forEach( e ->
            {
            if ( e.getBoundsInLocal()
                  .intersects( this.playerVariable.getPlayerRectangle()
                                                  .getBoundsInLocal() ) )
                {
                this.indexOfMapBorderToRemove = mapBorder.getChildren()
                                                         .indexOf( e ) ;
                }
            else if ( e.getBoundsInLocal()
                       .intersects( this.entranceOrExit.getBoundsInLocal() ) )
                {
                this.indexOfMapBorderToRemove = mapBorder.getChildren()
                                                         .indexOf( e ) ;
                }
            } ) ;
        if ( ( this.indexOfMapBorderToRemove > -1 ) &&
             ( this.indexOfMapBorderToRemove < mapBorder.getChildren().size() ) )
            {
            mapBorder.getChildren().remove( this.indexOfMapBorderToRemove ) ;
            }
        }

    // DONE: Something is wrong. Whenever the player moves to a different map,
    // their life points keeps on decreasing.


    // The issue arise because each "lifePointIntegrity" method was focused on the
    // specific
    // instance of the "player" class, not on the one currently on the screen.
    // As such, when the PREVIOUS enemy hits the PREVIOUS player, it resulted in a
    // game over
    // because the enemy kept on hitting the previous player and we couldn't see it.
    public void creatingMap( final Pane rootOfScene,
                             final Scene sceneOfWindow,
                             final Shop shopForGame,
                             final MediaPlayer music )
        {

        this.musicForShop = music ;
        this.gameShop = shopForGame ;
        final Text playerPointText = generatingTextForPlayerPoints() ;
        final int differenceInLifePoint = this.playerVariable == null
            ? 0
                : this.playerVariable.getLifePoint() -
                  this.playerVariable.getInitialLifePoint() ;
        rootOfScene.setBackground( new Background( new BackgroundFill( Color.BLACK,
                                                                       CornerRadii.EMPTY,
                                                                       Insets.EMPTY ) ) ) ;
        final Pane borderForMapTESTING = mapBorder() ;
        entranceOrExitToNewMap() ;
        rootOfScene.getChildren()
                   .addAll( borderForMapTESTING,
                            this.entranceOrExit,
                            playerPointText ) ;
        this.playerVariable = new Player( 200,
                                          200,
                                          rootOfScene,
                                          sceneOfWindow,
                                          borderForMapTESTING,
                                          this.gameShop,
                                          music ) ;
        final AssistantItems helpingPlayer =
                                        new AssistantItems( rootOfScene,
                                                            this.playerVariable ) ;
        this.playerVariable.updatePlayerLifePoint( differenceInLifePoint ) ;
        checkingForPlayerAndMapBorderCollision( borderForMapTESTING ) ;
        lifePointIntegrity() ;
        checkingForCollisionWithEntranceOrExit( rootOfScene, sceneOfWindow ) ;
        playerPointsUpdateIfItemsBoughtInShop( rootOfScene ) ;
        generatingEnemy( this.playerVariable ) ;
        this.gameShop.setIceFlag( false ) ;
        this.gameShop.setShieldFlag( false ) ;
        }


    public void lifePointIntegrity()
        {
        final AnimationTimer timerForLifePoint = new AnimationTimer()
            {

            @Override
            public void handle( final long now )
                {
                // TODO Auto-generated method stub
                if ( Main.this.playerVariable.getBlueLifeBar().getWidth() < 0 )
                    {
                    // DONE: Literally all I had to do was move the
                    // lifePointIntegrity method to the
                    // main class instead of being in the player class.
                    final GameOverScreen gameOver = new GameOverScreen( Main.this.playerVariable.getRootOfScene() ) ;
                    }
                }
            } ;
        timerForLifePoint.start() ;
        }


    private Text generatingTextForPlayerPoints()
        {
        this.playerPointDisplay = new Text( "Points: " + this.playerPoints ) ;
        this.playerPointDisplay.setX( 400 ) ;
        this.playerPointDisplay.setY( 100 ) ;
        this.playerPointDisplay.setScaleX( 2 ) ;
        this.playerPointDisplay.setScaleY( 2 ) ;
        this.playerPointDisplay.setFill( Color.WHITE ) ;
        return this.playerPointDisplay ;
        }


    private void generatingEnemy( final Player playerForEnemyToFollowAndEliminate )
        {
        final int amountOfEnemyToGenerate = this.generator.nextInt( 5 ) ;
        this.enemyArray = new Enemy[ amountOfEnemyToGenerate ] ;
        for ( int i = 0 ; i < amountOfEnemyToGenerate ; i++ )
            {
            final Enemy enemyToGetPlayer = new Enemy( playerForEnemyToFollowAndEliminate,
                                                      this.gameShop.getShieldBoughtFlag() ) ;
            this.enemyArray[ i ] = enemyToGetPlayer ;
            }
        }


    @Override
    public void start( final Stage primaryStage ) throws Exception
        {

        final Pane root = new Pane() ;
        final Scene scene = new Scene( root, 800, 800 ) ;
        // TODO Auto-generated method stub
        this.windowOfScreen = primaryStage ;
        this.windowOfScreen.setScene( scene ) ;
        final TitleScreen titleScreenOfGame = new TitleScreen( root, scene ) ;
        this.windowOfScreen.show() ;
        this.windowOfScreen.setResizable( false ) ;
        }


    private void checkingForCollisionWithEntranceOrExit( final Pane rootOfScene,
                                                         final Scene sceneOfWindow )
        {
        final AnimationTimer continuouslyCheckingToSeeIfPlayerTouchesTheEntrance =
                                        new AnimationTimer()
                                            {

                                            @Override
                                            public void handle( final long now )
                                                {
                                                if ( Main.this.playerVariable.getPlayerRectangle()
                                                                             .getBoundsInLocal()
                                                                             .intersects( Main.this.entranceOrExit.getBoundsInLocal() ) )
                                                    {
                                                    Main.this.gameShop.setClosedFlag( true ) ;
                                                    Enemy.setMovingEnemyFlag( true ) ;
                                                    Main.this.playerPoints++ ;
                                                    Main.this.gameShop.setPlayerPoint( Main.this.playerPoints ) ;
                                                    Main.this.playerVariable.getRootOfScene()
                                                                            .getChildren()
                                                                            .clear() ;
                                                    creatingMap( rootOfScene,
                                                                 sceneOfWindow,
                                                                 Main.this.gameShop,
                                                                 Main.this.musicForShop ) ;

                                                    }
                                                }

                                            } ;
        continuouslyCheckingToSeeIfPlayerTouchesTheEntrance.start() ;
        }


    private void playerPointsUpdateIfItemsBoughtInShop( final Pane root )
        {
        // TODO: Figure out why this method isn't working. Player Point isn't
        // updated.
        final AnimationTimer timerToCheckPoints = new AnimationTimer()
            {

            @Override
            public void handle( final long now )
                {
                // TODO Auto-generated method stub
                if ( Main.this.gameShop.getShieldBoughtFlag() &&
                     Main.this.gameShop.getClosedFlag() )
                    {
                    Main.this.playerPoints = Main.this.playerPoints -
                                             Main.this.gameShop.getCostForShield() ;
                    root.getChildren().remove( Main.this.playerPointDisplay ) ;
                    root.getChildren().add( generatingTextForPlayerPoints() ) ;
                    setEnemyFlag( Main.this.gameShop.getShieldBoughtFlag() ) ;
                    settingShieldIcon( root ) ;
                    Main.this.gameShop.setShieldFlag( false ) ;
                    }
                else if ( Main.this.gameShop.getIceBoughtFlag() &&
                          Main.this.gameShop.getClosedFlag() )
                    {
                    Main.this.playerPoints = Main.this.playerPoints -
                                             Main.this.gameShop.getCostForIce() ;
                    root.getChildren().remove( Main.this.playerPointDisplay ) ;
                    root.getChildren().add( generatingTextForPlayerPoints() ) ;
                    Enemy.setMovingEnemyFlag( false ) ;
                    settingIceIcon( root ) ;
                    }
                }

            } ;
        timerToCheckPoints.start() ;
        }


    private void settingShieldIcon( final Pane root )
        {
        final ImageView shield = this.gameShop.getShieldIcon() ;
        shield.setScaleX( .3 ) ;
        shield.setScaleY( .3 ) ;
        shield.setX( 600 ) ;
        shield.setY( 100 ) ;
        root.getChildren().add( shield ) ;
        }


    private void settingIceIcon( final Pane root )
        {
        final ImageView ice = this.gameShop.getIceIcon() ;
        ice.setScaleX( .3 ) ;
        ice.setScaleY( .3 ) ;
        ice.setX( 600 ) ;
        ice.setY( 150 ) ;
        root.getChildren().add( ice ) ;
        Main.this.gameShop.setIceFlag( false ) ;
        }


    private void setEnemyFlag( final boolean shouldNotTakeDamage )
        {
        for ( final Enemy enemy : this.enemyArray )
            {
            enemy.shouldDamagedBeTaken( shouldNotTakeDamage ) ;
            }
        }


    public int getPlayerPoint()
        {
        return this.playerPoints ;
        }


    public void setPlayerPoint( final int subtractPoint )
        {
        this.playerPoints = this.playerPoints - subtractPoint ;
        }


    /**
     * @param args
     */
    public static void main( final String[] args )
        {
        launch() ;
        }

    }
// end class TestingClass