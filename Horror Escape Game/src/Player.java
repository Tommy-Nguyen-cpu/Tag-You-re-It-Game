import javafx.animation.AnimationTimer ;
import javafx.scene.Scene ;
import javafx.scene.image.Image ;
import javafx.scene.image.ImageView ;
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
 * @version 1.0.0 2020-12-26 Initial implementation
 */
public class Player
    {

    private Rectangle collisionDetectionForPlayer ;
    private final Image playerImageVariable = new Image(Main.class.getResourceAsStream( "PlayerImage.png" ));
    private final ImageView playerImage =
                                    new ImageView( this.playerImageVariable) ;
    private Pane sceneRoot ;
    private Pane mapBorderRoot ;

    private SpriteAnimation spriteMovement ;
    private final int totalNumberOfFramesInImage = 3 ;
    private final int numberOfFramesInEachRow = 3 ;
    private final int widthOfPlayer = 70 ;
    private final int heightOfPlayer = 70 ;

    // Player Data
    private int playerLifePoint = 100 ;
    private int initialPlayerLifePoint = this.playerLifePoint ;
    private Text displayingLifePoints ;

    // DONE: Create a health bar and stamina bar to replace the health point text
    // system.
    // Should add more details once finished with the basics (like stamina bar,
    // health effects, etc).
    private Rectangle redLifeBar = new Rectangle( 440, 50, javafx.scene.paint.Color.RED ) ;
    private Rectangle blueLifeBar = new Rectangle( 440,
                                           50,
                                           javafx.scene.paint.Color.BLUE ) ;


    private Player()
        {

        }


    public Player( final int playerPositionX,
                   final int playerPositionY,
                   final Pane rootOfScene,
                   final Scene sceneForController,
                   final Pane rootOfMapBorder, Shop shop, MediaPlayer music )
        {
        this.sceneRoot = rootOfScene ;
        this.mapBorderRoot = rootOfMapBorder ;
        displayLifePointOfPlayer() ;
        playerBorder( playerPositionX, playerPositionY ) ;
        this.spriteMovement = new SpriteAnimation( this.playerImage,
                                                   this.totalNumberOfFramesInImage,
                                                   this.numberOfFramesInEachRow,
                                                   0,
                                                   0,
                                                   this.widthOfPlayer,
                                                   this.heightOfPlayer ) ;
        this.sceneRoot.getChildren()
                      .addAll( this.collisionDetectionForPlayer, this.playerImage ) ;

        final Controller controllerToMovePlayer = new Controller( this,
                                                                  sceneForController,
                                                                  rootOfMapBorder, shop, music ) ;

        }


    private void playerBorder( final int x,
                               final int y )
        {
        final Rectangle playerRectangle = new Rectangle( 20, 20, Color.TRANSPARENT ) ;
        playerRectangle.setX( x + 30 ) ;
        playerRectangle.setY( y + 35 ) ;
        this.collisionDetectionForPlayer = playerRectangle ;
        this.playerImage.setX( x ) ;
        this.playerImage.setY( y ) ;

        }


    // TODO: Will need to use this when changing scenes/locations.
    public void setPlayerPosition( final int x,
                                   final int y )
        {
        this.playerImage.setX( x ) ;
        this.playerImage.setY( y ) ;
        this.collisionDetectionForPlayer.setX( x ) ;
        this.collisionDetectionForPlayer.setY( y ) ;
        }


    // DONE: Used this for CollisionTestingClass and Controller Class.
    public Rectangle getPlayerRectangle()
        {
        return this.collisionDetectionForPlayer ;
        }


    // DONE: Will need "getRootOfScene()" for the Enemy class.
    public Pane getRootOfScene()
        {
        return this.sceneRoot ;
        }


    public Pane getMapBorderRoot()
        {
        return this.mapBorderRoot ;
        }


    public ImageView getPlayerSprite()
        {
        return this.playerImage ;
        }


    public SpriteAnimation getSpriteAnimation()
        {
        return this.spriteMovement ;
        }


    private void displayLifePointOfPlayer()
        {
        this.blueLifeBar.setX( 200 ) ;
        this.blueLifeBar.setY( 30 ) ;
        this.blueLifeBar.setOpacity( .7 ) ;
        this.redLifeBar.setX( 200 ) ;
        this.redLifeBar.setY( 30 ) ;
        this.redLifeBar.setOpacity( .5 ) ;
        this.displayingLifePoints = new Text( "Health: " + this.playerLifePoint ) ;
        this.displayingLifePoints.setX( 350 ) ;
        this.displayingLifePoints.setY( 55 ) ;
        this.displayingLifePoints.setFill( Color.BLACK ) ;
        this.displayingLifePoints.setScaleX( 4 ) ;
        this.displayingLifePoints.setScaleY( 4 ) ;
        this.sceneRoot.getChildren()
                      .addAll( this.redLifeBar,
                               this.blueLifeBar,
                               this.displayingLifePoints ) ;
        }


    public int getLifePoint()
        {
        return this.playerLifePoint ;
        }


    public int getInitialLifePoint()
        {
        return this.initialPlayerLifePoint ;
        }
    public void addPlayerToRoot(Pane root) {
    this.sceneRoot.getChildren()
    .addAll( this.collisionDetectionForPlayer, this.playerImage,this.redLifeBar,this.blueLifeBar,this.displayingLifePoints ) ;
    
    }


    // DONE: Use this in the enemy class and display it on screen.
    public void updatePlayerLifePoint( final int takeAway )
        {
        if ( this.playerLifePoint != 0)
            {

            this.blueLifeBar.setWidth( this.blueLifeBar.getWidth() +
                                       ( takeAway * 5 ) ) ;
            this.playerLifePoint = this.playerLifePoint + takeAway ;
            this.displayingLifePoints.setText( "Health: " + this.playerLifePoint ) ;
            }
        }
    public Rectangle getBlueLifeBar() {
    return this.blueLifeBar;
    }
    }
// end class Player