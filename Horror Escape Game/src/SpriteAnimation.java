import javafx.animation.Animation ;
import javafx.animation.Interpolator ;
import javafx.animation.Transition ;
import javafx.geometry.Rectangle2D ;
import javafx.scene.image.ImageView ;
import javafx.util.Duration ;

/**
 *
 */

/**
 * @author nguyent68
 * @version 1.0.0 2020-12-26 Initial implementation
 */



// NOTE FOR FUTURE SELF: Basically what "setViewport" does, is that it
// literally crops the image (or makes us see only a portion of the image.
// The reason why we create a separate class for animation is because A, it
// is cleaner and B, we need to extend our class so that it can use the
// interpolation class.
// Interpolation is the idea of constantly checking and editing the results
// so that we are able to accurately view the sprite.
public class SpriteAnimation extends Transition
    {

    private ImageView playerImageSheet ;
    private int totalNumberOfFrames ;
    private int numberOfFramesInEachRow ;
    private int offsetX ;
    private int offsetY ;
    private int width ;
    private int height ;


    private SpriteAnimation()
        {

        }


    public SpriteAnimation( final ImageView characterSheetForPlayer,
                            final int numberOfFrames,
                            final int numberOfFramesInEachRow,
                            final int offsetForPlayerX,
                            final int offsetForPlayerY,
                            final int widthOfPlayer,
                            final int heightOfPlayer )
        {
        this.playerImageSheet = characterSheetForPlayer ;
        this.totalNumberOfFrames = numberOfFrames ;
        this.numberOfFramesInEachRow = numberOfFramesInEachRow ;
        this.offsetX = offsetForPlayerX ;
        this.offsetY = offsetForPlayerY ;
        this.width = widthOfPlayer ;
        this.height = heightOfPlayer ;

        // Refers to how long it should take to go through one cycle.
        setCycleDuration( Duration.millis( 250 ) ) ;

        // Refers to how long the animation will continue to cycle.
        // In our case, the animation will continue to cycle forever because we're
        // making a game (not a gif or media animation).
        setCycleCount( Animation.INDEFINITE ) ;

        // We're telling the program that we want our animation to be constant in
        // speed.
        // The "setInterpolator" method basically is there to tell the program how
        // fast we want our animation to be (the rate at which our animation moves).
        // Do we want it to continue at a constant rate? Or do we want our speed to
        // grow in speed?
        setInterpolator( Interpolator.LINEAR ) ;

        // We're literally telling the program where we want to view the image and
        // how much we want to see it.
        // Do you want to view it in the top right? Bottom right? Top left? Bottom
        // left? Middle? At (600,100)? etc.
        // OffsetX and offsetY tells the program how much we want our shrunken image
        // to shift by.
        // The offsetX shifts the image horizontally where as offsetY shifts
        // vertically.
        this.playerImageSheet.setViewport( new Rectangle2D( offsetForPlayerX,
                                                            offsetForPlayerY,
                                                            widthOfPlayer,
                                                            heightOfPlayer ) ) ;
        }


    public void setoffX( final int x )
        {
        this.offsetX = x ;
        }


    public void setoffY( final int y )
        {
        this.offsetY = y ;
        }


    @Override
    // Try to figure out a much more efficient and less codey way of doing this.
    // By using this formula (and setOffY and setOffX), I'm added A LOT more code
    // which I do not want.
    protected void interpolate( final double frac )
        {
        // TODO Auto-generated method stub
        // Using the number of frames and the frac (current position from 0 to 1), we
        // are trying to find the minimum value between the current position we found
        // using frac and the total number of frames
        // and the position 1 less than the total frames.
        final int index = (int) Math.min( this.totalNumberOfFrames * frac,
                                          this.totalNumberOfFrames - 1 ) ;

        // Determines where our Rectandle2D will look at. In particular, we will look
        // at the image in the specified coordinates x and y.
        final int x = ( ( index % this.numberOfFramesInEachRow ) * this.width ) +
                      this.offsetX ;
        final int y = ( ( index / this.numberOfFramesInEachRow ) * this.height ) +
                      this.offsetY ;
        this.playerImageSheet.setViewport( new Rectangle2D( x,
                                                            y,
                                                            this.width,
                                                            this.height ) ) ;
        }

    }
// end class SpriteAnimation