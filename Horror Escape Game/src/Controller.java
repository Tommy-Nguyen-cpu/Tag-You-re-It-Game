import java.util.HashMap ;

import javafx.animation.AnimationTimer ;
import javafx.scene.Scene ;
import javafx.scene.input.KeyCode ;
import javafx.scene.layout.Pane ;
import javafx.scene.media.MediaPlayer ;

/**
 *
 */

/**
 * @author nguyent68
 * @version 1.0.0 2020-12-26 Initial implementation
 */
public class Controller
    {

    private Player playerCharacter ;
    private Scene sceneOfPlayerToTestToMove ;
    private Pane rootOfBorder ;
    private CollisionTestingClass collisionDetection ;
    private boolean canPlayerMove = true;

    private HashMap<KeyCode, Boolean> keysCollection = new HashMap<>() ;
    private Menu gameMenu;
    private int counterForOpeningWindow = 0;
    private Shop playerShop;
    
    //TODO: Collision doesn't seem to work when the player moves by 15 or 10. You may need to adjust the collision
    //so that it is a multiple of 10 instead of 5.
    private int movingByThisAmount = 5;


    private Controller()
        {

        }


    public Controller( final Player characterOfPlayer,
                       final Scene scene,
                       final Pane borderRoot, Shop shop, MediaPlayer music )
        {
        this.playerShop = shop;
        this.playerCharacter = characterOfPlayer ;
        this.sceneOfPlayerToTestToMove = scene ;
        this.rootOfBorder = borderRoot ;
        gameMenu = new Menu(playerCharacter.getRootOfScene(), this, shop, music);
        this.collisionDetection = new CollisionTestingClass( this.rootOfBorder,
                                                             this.playerCharacter ) ;
        updatingToSeeIfAKeyIsPressed() ;
        }
    
    public void setCanPlayerMoveOrNot(boolean playerCanMoveOrNot) {
    this.canPlayerMove = playerCanMoveOrNot;
    }
    public void resetCounterForOpeningMenu() {
    this.counterForOpeningWindow = 0;
    }


    public void updatingToSeeIfAKeyIsPressed()
        {
        // DONE Change the sprite of the player. Sprite should have player moving
        // left on top, right in the middle, up and down towards the end.
        // DONE: Work on the offsetX and offsetY or change the formula to finding
        // where the position of the sprite is at.

        // DONE: Fix the lag. For some reason it takes the player more than a second
        // to change direction.
        // The issue is most likely in the controller class.
        // The reason why the player lags is most likely a more fundamental reason:
        // your implementation isn't efficient enough.
        // NOTE: In your RPG project, your implementation was different and required
        // a hashmap. Find out why they did that and you might be able to solve the
        // issue.
        
        // Even though this may seem like a lot of code for nothing, it's actually A
        // LOT more efficient and quicker than what I had done prior. Before this,
        // there was an obvious lag when turning which isn't what I wanted.
        // It's a lot quicker to check to see if a key is in the hashmap than if a
        // player pressed a button directly.
        
        this.sceneOfPlayerToTestToMove.setOnKeyPressed( e ->{
        this.keysCollection.put( e.getCode(),
                                 true );
        if(e.getCode() == KeyCode.Q && this.counterForOpeningWindow < 1) {
        //TODO: Create a menu class that when clicking ESCAPE, will open a menu.
        playerCharacter.getSpriteAnimation().stop();
        Enemy.setMovingEnemyFlag( false );
        setCanPlayerMoveOrNot(false);
        gameMenu.openingMenu();
        this.counterForOpeningWindow++;
        }
        else if(e.getCode() == KeyCode.ESCAPE) {
        //TODO: Figure this out. I'm trying to make the enemy freeze but it never seems to work.
        //We need to have some flag that this if statement checks.
        if(!this.playerShop.getIceBoughtFlag() && this.playerShop.getShopPane().getChildren().isEmpty()) {
        Enemy.setMovingEnemyFlag( true );
        }
        setCanPlayerMoveOrNot(true);
        gameMenu.closeMenu();
        this.counterForOpeningWindow = 0;
        }
        }) ;
        final AnimationTimer keyPressingTimer = new AnimationTimer()
            {

            @Override
            public void handle( final long now )
                {
                // TODO Auto-generated method stub
                if(Controller.this.canPlayerMove) {
                Controller.this.collisionDetection.CollisionDetection() ;

                if ( isPressed( KeyCode.LEFT ) )
                    {
                    movePlayer( false, -10, 70, -1*movingByThisAmount ) ;
                    }
                else if ( isPressed( KeyCode.RIGHT ) )
                    {
                    movePlayer( false, -10, 130, movingByThisAmount ) ;
                    }

                else if ( isPressed( KeyCode.UP ) )
                    {
                    movePlayer( true, -10, 200, -1*movingByThisAmount ) ;
                    }
                else if ( isPressed( KeyCode.DOWN ) )
                    {
                    movePlayer( true, -10, 0, movingByThisAmount ) ;
                    }
                else {
                //TODO: Figure out a way to fix this: When you press a single button once, the player moves, but the sprite doesn't change.
                Controller.this.playerCharacter.getSpriteAnimation().stop() ;
                }
                }
                }

            } ;
        keyPressingTimer.start() ;

        this.sceneOfPlayerToTestToMove.setOnKeyReleased( e ->
            {
            this.keysCollection.put( e.getCode(), false ) ;
            } ) ;

        }


    // This method basically tests to see if a key is pressed. The "false" is just
    // what we set the boolean's default value to be if the key cannot be found in
    // the hashmap.
    // In other words, if the key isn't in the hashmap, then the value will be false.
    private boolean isPressed( final KeyCode keyPressed )
        {
        return this.keysCollection.getOrDefault( keyPressed, false ) ;
        }


    // Created this method to decrease the repetitive amount of code in the run
    // method.
    private void movePlayer( final boolean isMovingUpOrDown,
                             final int offsetX,
                             final int offsetY,
                             final int movingInDirectionByThisAmount )
        {
        this.playerCharacter.getSpriteAnimation().setoffY( offsetY ) ;
        this.playerCharacter.getSpriteAnimation().setoffX( offsetX ) ;
        this.playerCharacter.getSpriteAnimation().play() ;
        if ( isMovingUpOrDown )
            {
            this.playerCharacter.getPlayerSprite()
                                .setY( this.playerCharacter.getPlayerSprite()
                                                           .getY() +
                                       movingInDirectionByThisAmount ) ;

            this.playerCharacter.getPlayerRectangle()
                                .setY( this.playerCharacter.getPlayerRectangle()
                                                           .getY() +
                                       movingInDirectionByThisAmount ) ;
            }
        else
            {
            this.playerCharacter.getPlayerSprite()
                                .setX( this.playerCharacter.getPlayerSprite()
                                                           .getX() +
                                       movingInDirectionByThisAmount ) ;
            this.playerCharacter.getPlayerRectangle()
                                .setX( this.playerCharacter.getPlayerRectangle()
                                                           .getX() +
                                       movingInDirectionByThisAmount ) ;
            }

        }
    }
// end class Controller