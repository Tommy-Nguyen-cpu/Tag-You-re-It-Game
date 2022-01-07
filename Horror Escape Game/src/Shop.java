import java.net.URISyntaxException ;
import java.util.Random ;
import java.util.Timer ;
import java.util.TimerTask ;

import javafx.scene.image.Image ;
import javafx.scene.image.ImageView ;
import javafx.scene.layout.Pane ;
import javafx.scene.media.Media ;
import javafx.scene.media.MediaPlayer ;
import javafx.scene.paint.Color ;
import javafx.scene.shape.Rectangle ;
import javafx.scene.text.Text ;
import javafx.util.Duration ;

/**
 *
 */

/**
 * @author nguyent68
 * @version 1.0.0 2021-05-08 Initial implementation
 */
public class Shop
    {

    private final Image backButton = new Image( Main.class.getResourceAsStream( "BackButton.png" ) ) ;
    private final ImageView backButtonOnScreen = new ImageView( this.backButton ) ;
    private final ImageView shieldIcon =
                                    new ImageView( new Image( Main.class.getResourceAsStream( "shield.png" ) ) ) ;
    private final ImageView iceIcon =
                                    new ImageView( new Image( Main.class.getResourceAsStream( "ice.png" ) ) ) ;

    private MediaPlayer playMusic ;
    private Pane rootOfWindow ;
    private final Pane shopRoot = new Pane() ;

    private static int openedShop = 1 ;
    private final int costForShield = 3 ;
    private final int costForIce = 5 ;

    private int playerPointsGathered = 0 ;

    // Used to determine whether the items were bought or not.
    private boolean shieldBought = false ;
    private boolean iceBought = false ;

    private Text displayPoints ;

    // "isClosed" is used to determine if the menu is open or closed.
    private boolean isClosed = true ;

    private final Color[] colors = { Color.RED, Color.ORANGE, Color.LIGHTSKYBLUE,
                                     Color.LIGHTGREEN } ;


    public Shop()
        {

        }


    public void setClosedFlag( final boolean changeState )
        {
        this.isClosed = changeState ;
        }


    public boolean getClosedFlag()
        {
        return this.isClosed ;
        }


    public void shopWindow( final Pane root )
        {
        this.shieldIcon.setScaleX( 1 ) ;
        this.shieldIcon.setScaleY( 1 ) ;
        this.iceIcon.setScaleX( 1 ) ;
        this.iceIcon.setScaleY( 1 ) ;
        this.rootOfWindow = root ;
        borderForShop() ;
        displayCost() ;
        addingShopItems() ;
        actionOnItems() ;
        }


    private void borderForShop()
        {

        if ( openedShop == 1 )
            {
            openedShop++ ;
            final Rectangle shopWindow = new Rectangle( 750, 750, Color.ROSYBROWN ) ;
            shopWindow.setX( 30 ) ;
            shopWindow.setY( 30 ) ;
            shopWindow.setStroke( Color.CHARTREUSE ) ;
            this.shopRoot.getChildren().add( shopWindow ) ;
            this.backButtonOnScreen.setX( -340 ) ;
            this.backButtonOnScreen.setY( -230 ) ;
            this.backButtonOnScreen.setScaleX( .17 ) ;
            this.backButtonOnScreen.setScaleY( .17 ) ;
            this.backButtonOnScreen.setOnMouseClicked( e ->
                {
                this.shopRoot.getChildren().clear() ;
                this.rootOfWindow.getChildren().remove( this.shopRoot ) ;
                openedShop = 1 ;
                } ) ;
            this.shopRoot.getChildren().add( this.backButtonOnScreen ) ;
            this.rootOfWindow.getChildren().add( this.shopRoot ) ;
            displayPlayerPoints() ;
            }
        }


    private void displayPlayerPoints()
        {
        this.displayPoints = new Text( "Points: " + this.playerPointsGathered ) ;
        this.displayPoints.setX( 400 ) ;
        this.displayPoints.setY( 100 ) ;
        this.displayPoints.setScaleX( 3 ) ;
        this.displayPoints.setScaleY( 3 ) ;
        final Timer timer = new Timer() ;
        timer.scheduleAtFixedRate( new TimerTask()
            {

            @Override
            public void run()
                {
                final Random generator = new Random() ;
                final int generateColorIndex = generator.nextInt( 4 ) ;
                Shop.this.displayPoints.setFill( Shop.this.colors[ generateColorIndex ] ) ;
                }
            }, 0, 1000 ) ;
        this.shopRoot.getChildren().add( this.displayPoints ) ;
        }


    private void displayCost()
        {
        final Text shieldCost = new Text( "Cost: " + this.costForShield ) ;
        shieldCost.setX( 100 ) ;
        shieldCost.setY( 500 ) ;
        shieldCost.setScaleX( 2 ) ;
        shieldCost.setScaleY( 2 ) ;
        shieldCost.setFill( Color.WHITE ) ;

        final Text iceCost = new Text( "Cost: " + this.costForIce ) ;
        iceCost.setX( 480 ) ;
        iceCost.setY( 500 ) ;
        iceCost.setScaleX( 2 ) ;
        iceCost.setScaleY( 2 ) ;
        iceCost.setFill( Color.WHITE ) ;

        this.shopRoot.getChildren().addAll( shieldCost, iceCost ) ;
        }


    private void addingShopItems()
        {

        final Text shieldName = new Text( "Shield of Courage" ) ;
        shieldName.setFill( Color.WHITE ) ;
        shieldName.setScaleX( 2 ) ;
        shieldName.setScaleY( 2 ) ;
        shieldName.setX( 130 ) ;
        shieldName.setY( 140 ) ;
        final Rectangle BorderForShieldIcon =
                                        new Rectangle( 300, 300, Color.DODGERBLUE ) ;
        BorderForShieldIcon.setX( 60 ) ;
        BorderForShieldIcon.setY( 155 ) ;

        final Text iceName = new Text( "Freeze Ice" ) ;
        iceName.setFill( Color.WHITE ) ;
        iceName.setScaleX( 2 ) ;
        iceName.setScaleY( 2 ) ;
        iceName.setX( 560 ) ;
        iceName.setY( 140 ) ;
        final Rectangle BorderForIceIcon = new Rectangle( 300, 300, Color.CRIMSON ) ;
        BorderForIceIcon.setX( 460 ) ;
        BorderForIceIcon.setY( 155 ) ;

        this.shieldIcon.setX( 100 ) ;
        this.shieldIcon.setY( 200 ) ;

        this.iceIcon.setX( 455 ) ;
        this.iceIcon.setY( 200 ) ;

        this.shopRoot.getChildren()
                     .addAll( iceName,
                              shieldName,
                              BorderForShieldIcon,
                              BorderForIceIcon,
                              this.shieldIcon,
                              this.iceIcon ) ;
        }


    private void actionOnItems()
        {
        final Text shieldDescription = descriptionWindow( true ) ;
        final Text iceDescription = descriptionWindow( false ) ;
        final Rectangle descriptionWindow = new Rectangle() ;
        descriptionWindow.setFill( Color.BLACK ) ;
        descriptionWindow.setOpacity( .5 ) ;
        this.shieldIcon.setOnMouseEntered( e ->
            {
            this.shieldIcon.setScaleX( 1.4 ) ;
            this.shieldIcon.setScaleY( 1.4 ) ;
            descriptionWindow.setWidth( 700 ) ;
            descriptionWindow.setHeight( 50 ) ;
            descriptionWindow.setY( 555 ) ;
            descriptionWindow.setX( 50 ) ;
            this.shopRoot.getChildren()
                         .addAll( descriptionWindow, shieldDescription ) ;
            } ) ;
        this.shieldIcon.setOnMouseExited( e ->
            {
            this.shieldIcon.setScaleX( 1 ) ;
            this.shieldIcon.setScaleY( 1 ) ;
            this.shopRoot.getChildren().remove( shieldDescription ) ;
            this.shopRoot.getChildren().remove( descriptionWindow ) ;
            } ) ;
        this.shieldIcon.setOnMouseClicked( e ->
            {
            if ( ( this.playerPointsGathered - this.costForShield ) >= 0 )
                {
                this.playerPointsGathered = this.playerPointsGathered -
                                            this.costForShield ;
                this.shopRoot.getChildren().remove( this.displayPoints ) ;
                displayPlayerPoints() ;
                this.shieldBought = true ;
                }
            } ) ;

        this.iceIcon.setOnMouseEntered( e ->
            {
            this.iceIcon.setScaleX( 1.4 ) ;
            this.iceIcon.setScaleY( 1.4 ) ;
            descriptionWindow.setWidth( 450 ) ;
            descriptionWindow.setHeight( 50 ) ;
            descriptionWindow.setY( 555 ) ;
            descriptionWindow.setX( 200 ) ;
            this.shopRoot.getChildren().addAll( descriptionWindow, iceDescription ) ;
            } ) ;
        this.iceIcon.setOnMouseExited( e ->
            {
            this.iceIcon.setScaleX( 1 ) ;
            this.iceIcon.setScaleY( 1 ) ;
            this.shopRoot.getChildren().remove( iceDescription ) ;
            this.shopRoot.getChildren().remove( descriptionWindow ) ;
            } ) ;
        this.iceIcon.setOnMouseClicked( e ->
            {
            if ( ( this.playerPointsGathered - this.costForIce ) >= 0 )
                {
                this.playerPointsGathered = this.playerPointsGathered -
                                            this.costForIce ;
                this.shopRoot.getChildren().remove( this.displayPoints ) ;
                displayPlayerPoints() ;
                this.iceBought = true ;
                }
            } ) ;
        }


    public boolean getShieldBoughtFlag()
        {
        return this.shieldBought ;
        }


    public boolean getIceBoughtFlag()
        {
        return this.iceBought ;
        }


    public void setIceFlag( final boolean flag )
        {
        this.iceBought = flag ;
        }


    public void setShieldFlag( final boolean flag )
        {
        this.shieldBought = flag ;
        }


    public int getCostForIce()
        {
        return this.costForIce ;
        }


    public int getCostForShield()
        {
        return this.costForShield ;
        }


    public ImageView getShieldIcon()
        {
        return this.shieldIcon ;
        }


    public ImageView getIceIcon()
        {
        return this.iceIcon ;
        }


    private Text descriptionWindow( final boolean isShield )
        {
        final Text description = new Text() ;
        description.setFill( Color.WHITE ) ;
        if ( isShield )
            {
            // DONE: Add a pane that holds this text. Add the pane to the shopRoot.
            description.setText( "This item allows the player to temporarily nullify damage taken from enemies (can only be used once)." ) ;
            description.setX( 50 ) ;
            description.setY( 600 ) ;
            }
        else
            {
            description.setText( "This item freezes all enemies temporarily (will only work one time)." ) ;
            description.setX( 200 ) ;
            description.setY( 600 ) ;
            }
        return description ;
        }


    public void backgroundMusicForShop( final MediaPlayer musicForShop )
        {
        this.playMusic = musicForShop ;
        Media backgroundMusicForGame ;
        final double adjustToThisVolume = this.playMusic.getVolume() ;
        try
            {
            backgroundMusicForGame = new Media( Main.class.getResource( "Misty-Bog_remixed.mp3" )
                                                          .toURI()
                                                          .toString() ) ;
            this.playMusic = new MediaPlayer( backgroundMusicForGame ) ;
            this.playMusic.setVolume( adjustToThisVolume ) ;
            this.playMusic.setOnEndOfMedia( () -> Shop.this.playMusic.seek( Duration.ZERO ) ) ;
            this.playMusic.setAutoPlay( true ) ;
            this.playMusic.play() ;
            }
        catch ( final URISyntaxException e )
            {
            // TODO Auto-generated catch block
            e.printStackTrace() ;
            }
        }


    public void setPlayerPoint( final int playerPointsEarned )
        {
        this.playerPointsGathered = playerPointsEarned ;
        }


    public Pane getShopPane()
        {
        return this.shopRoot ;
        }
    }
// end class Shop