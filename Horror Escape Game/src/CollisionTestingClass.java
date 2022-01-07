import javafx.scene.layout.Pane ;

/**
 *
 */

/**
 * @author nguyent68
 * @version 1.0.0 2020-12-26 Initial implementation
 */
public class CollisionTestingClass
    {

    // MapBorders Pane will be used to test for collision for enemy and player.
    private Pane mapBorders ;
    private Player player ;

    // Variable for enemy collision detection.
    private Enemy enemy ;
    private boolean movingUpOrDown = true ;


    // Pretty much done with the CollisionTestingClass. May need to work on it more
    // in the future.
    public CollisionTestingClass( final Enemy enemyNPC, final Pane mapLayoutPane )
        {
        this.enemy = enemyNPC ;
        this.mapBorders = mapLayoutPane ;
        }


    public CollisionTestingClass( final Pane rootOfMapBorder,
                                  final Player playerCharacter )
        {
        this.mapBorders = rootOfMapBorder ;
        this.player = playerCharacter ;
        }


    public CollisionTestingClass()
        {

        }


    // Checks to see if the player collides with the map borders.
    public void CollisionDetection()
        {
        this.mapBorders.getChildren().forEach( e ->
            {
            if ( this.player.getPlayerRectangle()
                            .intersects( e.getBoundsInLocal() ) )
                {
                if ( this.player.getPlayerRectangle().getBoundsInLocal().getMaxX() ==
                     e.getBoundsInLocal().getMinX() )
                    {
                    this.player.getPlayerSprite()
                               .setX( this.player.getPlayerSprite().getX() - 5 ) ;
                    this.player.getPlayerRectangle()
                               .setX( this.player.getPlayerRectangle().getX() - 5 ) ;
                    }
                else if ( this.player.getPlayerRectangle()
                                     .getBoundsInLocal()
                                     .getMinX() ==
                          e.getBoundsInLocal().getMaxX() )
                    {
                    this.player.getPlayerSprite()
                               .setX( this.player.getPlayerSprite().getX() + 5 ) ;
                    this.player.getPlayerRectangle()
                               .setX( this.player.getPlayerRectangle().getX() + 5 ) ;
                    }
                else if ( this.player.getPlayerRectangle()
                                     .getBoundsInLocal()
                                     .getMaxY() ==
                          e.getBoundsInLocal().getMinY() )
                    {
                    this.player.getPlayerSprite()
                               .setY( this.player.getPlayerSprite().getY() - 5 ) ;
                    this.player.getPlayerRectangle()
                               .setY( this.player.getPlayerRectangle().getY() - 5 ) ;
                    }
                else if ( this.player.getPlayerRectangle()
                                     .getBoundsInLocal()
                                     .getMinY() ==
                          e.getBoundsInLocal().getMaxY() )
                    {
                    this.player.getPlayerSprite()
                               .setY( this.player.getPlayerSprite().getY() + 5 ) ;
                    this.player.getPlayerRectangle()
                               .setY( this.player.getPlayerRectangle().getY() + 5 ) ;
                    }
                }
            } ) ;
        if ( (int) this.player.getPlayerRectangle().getX() == 0 )
            {
            this.player.getPlayerRectangle()
                       .setX( this.player.getPlayerRectangle().getX() + 5 ) ;
            this.player.getPlayerSprite()
                       .setX( this.player.getPlayerSprite().getX() + 5 ) ;
            }
        else if ( (int) this.player.getPlayerRectangle().getX() == 800 )
            {
            this.player.getPlayerRectangle()
                       .setX( this.player.getPlayerRectangle().getX() - 5 ) ;
            this.player.getPlayerSprite()
                       .setX( this.player.getPlayerSprite().getX() - 5 ) ;
            }
        if ( (int) this.player.getPlayerRectangle().getY() == 0 )
            {
            this.player.getPlayerRectangle()
                       .setY( this.player.getPlayerRectangle().getY() + 5 ) ;
            this.player.getPlayerSprite()
                       .setY( this.player.getPlayerSprite().getY() + 5 ) ;
            }
        else if ( (int) this.player.getPlayerRectangle().getY() == 800 )
            {
            this.player.getPlayerRectangle()
                       .setY( this.player.getPlayerRectangle().getY() - 5 ) ;
            this.player.getPlayerSprite()
                       .setY( this.player.getPlayerSprite().getY() - 5 ) ;
            }
        }


    // NOTE: Will no longer combine "Player" and "Enemy" class. Combining classes
    // will only make the code more confusing.
    // This method checks to see if the enemy collides with the borders laid
    // throughout the map.
    // The risk of having a repetitive method is far less than having confusing code.
    public void CollisionDetectionForEnemy()
        {
        this.mapBorders.getChildren().forEach( e ->
            {
            if ( this.enemy.getEnemyRectangle().intersects( e.getBoundsInLocal() ) )
                {
                if ( this.enemy.getEnemyRectangle().getBoundsInLocal().getMaxX() ==
                     e.getBoundsInLocal().getMinX() )
                    {
                    this.movingUpOrDown = true ;
                    this.enemy.getEnemySprite()
                              .setX( this.enemy.getEnemySprite().getX() - 5 ) ;
                    this.enemy.getEnemyRectangle()
                              .setX( this.enemy.getEnemyRectangle().getX() - 5 ) ;
                    }
                else if ( this.enemy.getEnemyRectangle()
                                    .getBoundsInLocal()
                                    .getMinX() ==
                          e.getBoundsInLocal().getMaxX() )
                    {
                    this.movingUpOrDown = true ;
                    this.enemy.getEnemySprite()
                              .setX( this.enemy.getEnemySprite().getX() + 5 ) ;
                    this.enemy.getEnemyRectangle()
                              .setX( this.enemy.getEnemyRectangle().getX() + 5 ) ;
                    }
                else if ( this.enemy.getEnemyRectangle()
                                    .getBoundsInLocal()
                                    .getMaxY() ==
                          e.getBoundsInLocal().getMinY() )
                    {
                    this.movingUpOrDown = false ;
                    this.enemy.getEnemySprite()
                              .setY( this.enemy.getEnemySprite().getY() - 5 ) ;
                    this.enemy.getEnemyRectangle()
                              .setY( this.enemy.getEnemyRectangle().getY() - 5 ) ;
                    }
                else if ( this.enemy.getEnemyRectangle()
                                    .getBoundsInLocal()
                                    .getMinY() ==
                          e.getBoundsInLocal().getMaxY() )
                    {
                    this.movingUpOrDown = false ;
                    this.enemy.getEnemySprite()
                              .setY( this.enemy.getEnemySprite().getY() + 5 ) ;
                    this.enemy.getEnemyRectangle()
                              .setY( this.enemy.getEnemyRectangle().getY() + 5 ) ;
                    }
                }
            } ) ;
        }


    public boolean gettingBooleanFlagForMovingUpOrDown()
        {
        return this.movingUpOrDown ;
        }


    public void settingBooleanFlagForMovingUpOrDown( final boolean flagChange )
        {
        this.movingUpOrDown = flagChange ;
        }


    // This method is different from the other two so it's fine. This method checks
    // to see if the enemy collided with the player.
    // if it does, the enemy is moved away from the player so that the enemy doesn't
    // overlap the player.
    public void enemyPlayerCollision( final Player player, boolean damageShouldBeTaken )
        {
        if ( this.enemy.getEnemyRectangle()
                       .intersects( player.getPlayerRectangle()
                                          .getBoundsInLocal() ) )
            {
            if(!damageShouldBeTaken) {
            player.updatePlayerLifePoint( -1 ) ;
            
            }
            if ( this.enemy.getEnemyRectangle().getBoundsInLocal().getMaxX() ==
                 player.getPlayerRectangle().getBoundsInLocal().getMinX() )
                {
                this.enemy.getEnemySprite()
                          .setX( this.enemy.getEnemySprite().getX() - 10 ) ;
                this.enemy.getEnemyRectangle()
                          .setX( this.enemy.getEnemyRectangle().getX() - 10 ) ;
                }
            else if ( this.enemy.getEnemyRectangle().getBoundsInLocal().getMinX() ==
                      player.getPlayerRectangle().getBoundsInLocal().getMaxX() )
                {
                this.enemy.getEnemySprite()
                          .setX( this.enemy.getEnemySprite().getX() + 10 ) ;
                this.enemy.getEnemyRectangle()
                          .setX( this.enemy.getEnemyRectangle().getX() + 10 ) ;
                }
            else if ( this.enemy.getEnemyRectangle().getBoundsInLocal().getMaxY() ==
                      player.getPlayerRectangle().getBoundsInLocal().getMinY() )
                {
                this.enemy.getEnemySprite()
                          .setY( this.enemy.getEnemySprite().getY() - 10 ) ;
                this.enemy.getEnemyRectangle()
                          .setY( this.enemy.getEnemyRectangle().getY() - 10 ) ;
                }
            else if ( this.enemy.getEnemyRectangle().getBoundsInLocal().getMinY() ==
                      player.getPlayerRectangle().getBoundsInLocal().getMaxY() )
                {
                this.enemy.getEnemySprite()
                          .setY( this.enemy.getEnemySprite().getY() + 10 ) ;
                this.enemy.getEnemyRectangle()
                          .setY( this.enemy.getEnemyRectangle().getY() + 10 ) ;
                }
            }
        }
    }
// end class CollisionTestingClass