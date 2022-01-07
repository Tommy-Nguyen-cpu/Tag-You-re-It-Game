import java.util.Random ;

import javafx.animation.AnimationTimer ;
import javafx.geometry.Rectangle2D ;
import javafx.scene.image.Image ;
import javafx.scene.image.ImageView ;
import javafx.scene.layout.Pane ;

/**
 *
 */

/**
 * @author nguyent68
 * @version 1.0.0 2021-01-22 Initial implementation
 */
public class AssistantItems
    {
    // This is a class that will generate assistant items for the player to use
    // throughout the map.

    // Create a 2D array that holds the x and y value for each position of the
    // potions
    // Within the spritesheet.
    private final Image assistantItemSpreadSheet =
                                    new Image( AssistantItems.class.getResourceAsStream( "potions.png" ) ) ;
    private static // x y x y x y x y
    int[][] OffsetXAndYCoordinatesOfEachPotions = { { 0, 0 }, { 25, 0 }, { 49, 0 },
                                                    { 75, 0 } } ;
    private static int indexOfCollision = -1 ;
    private final static Pane paneItem = new Pane() ;


    private AssistantItems()
        {}


    // Call generateHealingItems method.
    public AssistantItems( final Pane mapRoot, final Player player )
        {
        generateHealingItems( mapRoot ) ;
        checkingForCollisionWithAssistantItem( player ) ;
        }


    private void generateHealingItems( final Pane rootPane )
        {
        // DONE: Figure out the issue.
        final Random generator = new Random() ;
        for ( int i = 0 ; i < 2 ; i++ )
            {
            final int potionToGenerate = generator.nextInt( 4 ) ;
            final int xPosition = generator.nextInt( 700 ) ;
            final int yPosition = generator.nextInt( 700 ) ;
            final Image potions = new Image( Main.class.getResourceAsStream( "potions.png" ) ) ;
            final ImageView potionGenerated = new ImageView( potions ) ;
            potionGenerated.setX( xPosition ) ;
            potionGenerated.setY( yPosition ) ;
            potionGenerated.setViewport( new Rectangle2D( OffsetXAndYCoordinatesOfEachPotions[ potionToGenerate ][ 0 ],
                                                          OffsetXAndYCoordinatesOfEachPotions[ potionToGenerate ][ 1 ],
                                                          22,
                                                          22 ) ) ;
            paneItem.getChildren().add( potionGenerated ) ;
            }
        rootPane.getChildren().add( paneItem ) ;
        }


    private static void checkingForCollisionWithAssistantItem( final Player player )
        {
        final AnimationTimer timer = new AnimationTimer()
            {

            @Override
            public void handle( final long now )
                {
                // DONE Create collision detection For potions.
                // DONE: Figure out the index out of bounds issue.
                paneItem.getChildren().forEach( e ->
                    {
                    if ( player.getPlayerRectangle()
                               .getBoundsInLocal()
                               .intersects( e.getBoundsInLocal() ) )
                        {
                        // Gets the index of the object inside the pane.
                        indexOfCollision = paneItem.getChildren().indexOf( e ) ;
                        if ( ( player.getLifePoint() != 100 ) &&
                             ( ( player.getLifePoint() + 5 ) <= 100 ) )
                            {
                            player.updatePlayerLifePoint( 5 ) ;
                            }
                        }
                    } ) ;
                // DONE: The issue lies in the fact that the pane is apparently
                // empty.
                // This doesn't make sense, since when I check the size of the pane,
                // it says there are 5 items in it.
                // paneItem.getChildren().remove( indexOfCollision );
                if ( !paneItem.getChildren().isEmpty() && ( indexOfCollision > -1 ) )
                    {
                    paneItem.getChildren()
                            .remove( paneItem.getChildren()
                                             .get( indexOfCollision ) ) ;
                    indexOfCollision = -1 ;
                    }

                // TODO: Implement effect for each potions.

                }

            } ;
        timer.start() ;
        }

    // generateHealingItems method
    // Randomly generates a position for the item.
    // if item collides with mapRoot nodes, generate another position.

    // effectsOfItems Method
    // Continuously check this method (AnimationTimer).
    // If player collides with items, effects should come into play.
    // Effects should depend on the index of the array (each index holding diff
    // pictures of items).
    }
// end class AssistantItems