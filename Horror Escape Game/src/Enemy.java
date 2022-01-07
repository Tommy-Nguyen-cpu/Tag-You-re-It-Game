import java.util.Random ;

import javafx.animation.AnimationTimer ;
import javafx.scene.image.Image ;
import javafx.scene.image.ImageView ;
import javafx.scene.paint.Color ;
import javafx.scene.shape.Rectangle ;

/**
 *
 */

/**
 * @author nguyent68
 * @version 1.0.0 2020-12-26 Initial implementation
 */
public class Enemy
    {

    private Player playerForEnemyToFollow ;

    private final int totalNumberOfFramesInImage = 3 ;
    private final int numberOfFramesInEachRow = 4 ;
    private final int widthOfEnemy = 35 ;
    private final int heightOfEnemy = 40 ;
    private final Image enemy = new Image( Main.class.getResourceAsStream( "EnemyMob.jpg" ) ) ;
    private final ImageView enemySprite = new ImageView( this.enemy ) ;
    private SpriteAnimation enemyAnimation ;
    private Rectangle borderToCheckForCollisionForEnemy ;

    private CollisionTestingClass collisionDetection ;

    private static boolean pauseOrContinue = true ;

    private boolean shouldDamageBeTaken = false ;


    // Should create an opponent that follows the player until it touches the player
    // or the player reaches a safe point at the end of a map.
    private Enemy()
        {

        }


    // DONE: implement.
    // Should call the other two methods (enemySettings and followPlayer).
    public Enemy( final Player characterOfPlayer, final boolean damageShouldBeTaken )
        {
        this.playerForEnemyToFollow = characterOfPlayer ;
        this.enemyAnimation = new SpriteAnimation( this.enemySprite,
                                                   this.totalNumberOfFramesInImage,
                                                   this.numberOfFramesInEachRow,
                                                   0,
                                                   0,
                                                   this.widthOfEnemy,
                                                   this.heightOfEnemy ) ;
        this.shouldDamageBeTaken = damageShouldBeTaken ;

        enemySettings() ;
        followPlayer() ;
        }


    public void shouldDamagedBeTaken( final boolean damageShouldBeTakenFlag )
        {
        this.shouldDamageBeTaken = damageShouldBeTakenFlag ;
        followPlayer() ;
        }


    private void enemySettings()
        {
        this.borderToCheckForCollisionForEnemy =
                                        new Rectangle( 70, 70, Color.TRANSPARENT ) ;
        this.playerForEnemyToFollow.getRootOfScene()
                                   .getChildren()
                                   .addAll( this.borderToCheckForCollisionForEnemy,
                                            this.enemySprite ) ;
        randomLocationOfEnemy() ;

        }


    private void randomLocationOfEnemy()
        {
        final Random generator = new Random() ;
        final boolean isLeftOrRight = generator.nextBoolean() ;
        final boolean isNegative = generator.nextBoolean() ;
        int randomPosition = generator.nextInt( 800 ) ;
        while ( ( randomPosition % 5 ) != 0 )
            {
            randomPosition = generator.nextInt( 800 ) ;
            }
        if ( isLeftOrRight )
            {
            this.enemySprite.setX( isNegative
                ? -50
                    : 750 ) ;
            this.enemySprite.setY( randomPosition ) ;

            this.borderToCheckForCollisionForEnemy.setX( isNegative
                ? -50
                    : 750 ) ;
            this.borderToCheckForCollisionForEnemy.setY( randomPosition ) ;
            }
        else
            {
            this.enemySprite.setY( isNegative
                ? -50
                    : 750 ) ;
            this.enemySprite.setX( randomPosition ) ;

            this.borderToCheckForCollisionForEnemy.setY( isNegative
                ? -50
                    : 750 ) ;
            this.borderToCheckForCollisionForEnemy.setX( randomPosition ) ;
            }
        }


    // DONE: implement.
    // This should be a method that the Enemy class continues to test to see if the
    // enemy has reached the player.
    // In other words, we should use an animationTimer for this in the constructor.
    // If the enemy touches the player, the player should lose a life.
    private void followPlayer()
        {
        this.collisionDetection = new CollisionTestingClass( this,
                                                             this.playerForEnemyToFollow.getMapBorderRoot() ) ;
        final AnimationTimer tryingToCollideWithPlayer = new AnimationTimer()
            {

            @Override
            public void handle( final long now )
                {
                if ( Enemy.pauseOrContinue )
                    {
                    Enemy.this.collisionDetection.CollisionDetectionForEnemy() ;
                    Enemy.this.collisionDetection.enemyPlayerCollision( Enemy.this.playerForEnemyToFollow,
                                                                        Enemy.this.shouldDamageBeTaken ) ;

                    moveEnemy() ;
                    }
                else
                    {
                    Enemy.this.enemyAnimation.stop() ;
                    }

                }

            } ;
        tryingToCollideWithPlayer.start() ;

        }

    // DONE: FIND A WAY TO MAKE IT SO THAT THE ENEMY MOVES EVEN WHEN THE PLAYER
    // DOESN'T.

    // DONE: Find a way to fix the sprite animation for the enemy. It should look
    // like the enemy is going left when it moves left, right when moves right, and
    // etc.
    // TODO: Adjust the sprite animation a little bit. Looks kind of weird whent he
    // enemy is running.


    // TODO: This is for a future implementation, but when you are done with
    // everything, try and balance out the enemy and character.
    // Currently, the enemy moves WAY too fast for the player to catch up.
    // The best case scenario (and probably the most easiest) would be to solve the
    // setOnKeyPress lag issue.
    private void moveEnemy()
        {

        // NOTE: Attempted to fix this with if-else statements and it worked.
        // HOWEVER, not only was it incredibly messy, but the NPC became unable to
        // move once it hit a barrier.
        // IE. Even if the player is literally right next to the NPC, the NPC doesn't
        // move.

        // The reason why it's not working is because the enemy moves left and right
        // without any conditionals.
        // In other words, the sprite animation changes from left or right to up or
        // down almost immediately.
        final double distanceFromEnemyToPlayerX =
                                        Enemy.this.playerForEnemyToFollow.getPlayerRectangle()
                                                                         .getX() -
                                                  Enemy.this.borderToCheckForCollisionForEnemy.getX() ;
        final double distanceFromEnemyToPlayerY =
                                        Enemy.this.playerForEnemyToFollow.getPlayerRectangle()
                                                                         .getY() -
                                                  Enemy.this.borderToCheckForCollisionForEnemy.getY() ;

        // DONE: Fix this issue. When the enemy hits a border, the enemy just stops
        // moving entirely until the players Y is the same as the enemies Y (figure
        // out what to change the second half of the if statement into).
        // NOTE: Technically fixed this issue, may want to come back to this in the
        // near future though.
        if ( ( (int) distanceFromEnemyToPlayerY != 0 ) &&
             this.collisionDetection.gettingBooleanFlagForMovingUpOrDown() )
            {
            // DONE: Need some sort of conditional statement to check so the sprite
            // animation can act accordingly.
            // Otherwise, the sprite will just go crazy.
            // Moving enemy up or down.

            // DONE: Adjust the offsets.
            // If distanceFromEnemyToPlayerY is greater than 0 (meaning the player is
            // farther
            // Down the stage than the enemy), then setOffY, setOffX, and setY will
            // adjust accordingly.
            this.enemyAnimation.setoffY( distanceFromEnemyToPlayerY > 0
                ? 0
                    : 145 ) ;
            this.enemyAnimation.setoffX( distanceFromEnemyToPlayerY > 0
                ? -5
                    : 27 ) ;
            this.enemyAnimation.play() ;
            this.borderToCheckForCollisionForEnemy.setY( this.borderToCheckForCollisionForEnemy.getY() +
                                                         ( distanceFromEnemyToPlayerY >
                                                           0
                                                               ? 5
                                                                   : -5 ) ) ;
            this.enemySprite.setY( this.enemySprite.getY() +
                                   ( distanceFromEnemyToPlayerY > 0
                                       ? 5
                                           : -5 ) ) ;
            }
        else
            {
            // Moving enemy left or right.

            // If distanceFromEnemyToPlayerX is greater than 0 (meaning the player is
            // farther
            // to the right of the stage than the enemy), then setOffY, setOffX, and
            // setY will adjust accordingly.
            this.enemyAnimation.setoffY( distanceFromEnemyToPlayerX > 0
                ? 99
                    : 50 ) ;
            this.enemyAnimation.setoffX( distanceFromEnemyToPlayerX > 0
                ? -10
                    : -2 ) ;
            this.enemyAnimation.play() ;

            this.enemySprite.setX( this.enemySprite.getX() +
                                   ( distanceFromEnemyToPlayerX > 0
                                       ? 5
                                           : -5 ) ) ;
            this.borderToCheckForCollisionForEnemy.setX( this.borderToCheckForCollisionForEnemy.getX() +
                                                         ( distanceFromEnemyToPlayerX >
                                                           0
                                                               ? 5
                                                                   : -5 ) ) ;
            if ( distanceFromEnemyToPlayerX == 0 )
                {
                this.collisionDetection.settingBooleanFlagForMovingUpOrDown( true ) ;
                }
            }

        }


    public static void setMovingEnemyFlag( final boolean shouldPauseOrMove )
        {
        pauseOrContinue = shouldPauseOrMove ;
        }


    public Rectangle getEnemyRectangle()
        {
        return this.borderToCheckForCollisionForEnemy ;
        }


    public ImageView getEnemySprite()
        {
        return this.enemySprite ;
        }
    }
// end class Enemy