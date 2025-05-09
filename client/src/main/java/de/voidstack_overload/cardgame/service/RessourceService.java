package de.voidstack_overload.cardgame.service;

import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

public class RessourceService {
    /**
     * This member resembles all images that can be used throughout the game
     */
    private static final Image[] imageBuffer = new Image[ImageKey.values().length];

    /**
     * Getter image buffer size.
     *
     * @return the image buffer size
     */
    public static int getImageBufferSize()
    {
        return imageBuffer.length;
    }

    /**
     * Creates a card image according to the number which identifies each card so it will be cached or will just return the card if it already created and cached
     *
     * @param imKey     The ImageKey enum of a given Image
     * @return          the newly cached image
     */
    public static Image getImage(ImageKey imKey)
    {
        if (imageBuffer[imKey.index] == null)
        {
            Image image = new Image(imKey.getPath(), 200, 10000, true, true);
            imageBuffer[imKey.index] = image;
        }
        return imageBuffer[imKey.index];
    }

    /**
     * Creates a card image according to the number which identifies each card so it will be cached or will just return the card if it already created and cached
     *
     * @param nr    the id of the image
     * @return      the newly cached image
     */
    public static Image getImage(int nr)
    {
        if(nr < 0 || nr >= imageBuffer.length || nr == 66) {
            nr = 66;
            Image BLANK = new WritableImage(128,192);
            imageBuffer[nr] = BLANK;
            return imageBuffer[nr];
        }
        if (imageBuffer[nr] == null)
        {
            for (ImageKey imKey : ImageKey.values())
            {
                if (imKey.index == nr)
                {
                    Image image = new Image(imKey.getPath(), 200, 10000, true, true);
                    imageBuffer[nr] = image;
                    break;
                }
            }
        }
        return imageBuffer[nr];
    }

    /**
     * Enum for mapping textures to networkIndex values
     */
    public enum ImageKey {
        CLUB_SYMBOL ("/textures/icons/club_symbol.png", 0),
        CLUB_GLOW_SYMBOL ("/textures/icons/club_glow.png",1),
        DIAMOND_SYMBOL ("/textures/icons/diamond_symbol.png", 2),
        DIAMOND_GLOW_SYMBOL ("/textures/icons/diamond_glow.png", 3),
        HEART_SYMBOL ("/textures/icons/heart_symbol.png", 4),
        HEART_GLOW_SYMBOL ("/textures/icons/heart_glow.png", 5),
        SPADE_SYMBOL ("/textures/icons/spade_symbol.png", 6),
        SPADE_GLOW_SYMBOL ("/textures/icons/spade_glow.png", 7),
        CARD_BACK_HIGH_SAT ("/textures/cards/card_back_highsat.png", 8),
        CARD_BACK_LOW_SAT ("/textures/cards/card_back_lowsat.png", 9),
        CARD_BLANK ("/textures/cards/card_blank.png", 10),
        //LEAVE KEYS INTACT FOR CARDS (REFERENCED BY SERVER)
        //CLUBS
        TWO_OF_CLUBS ("/textures/cards/clubs/two_of_clubs.png", 11),
        THREE_OF_CLUBS ("/textures/cards/clubs/three_of_clubs.png", 12),
        FOUR_OF_CLUBS ("/textures/cards/clubs/four_of_clubs.png", 13),
        FIVE_OF_CLUBS ("/textures/cards/clubs/five_of_clubs.png", 14),
        SIX_OF_CLUBS ("/textures/cards/clubs/six_of_clubs.png", 15),
        SEVEN_OF_CLUBS ("/textures/cards/clubs/seven_of_clubs.png", 16),
        EIGHT_OF_CLUBS ("/textures/cards/clubs/eight_of_clubs.png", 17),
        NINE_OF_CLUBS ("/textures/cards/clubs/nine_of_clubs.png", 18),
        TEN_OF_CLUBS ("/textures/cards/clubs/ten_of_clubs.png", 19),
        JACK_OF_CLUBS ("/textures/cards/clubs/jack_of_clubs.png", 20),
        QUEEN_OF_CLUBS ("/textures/cards/clubs/queen_of_clubs.png", 21),
        KING_OF_CLUBS ("/textures/cards/clubs/king_of_clubs.png", 22),
        ACE_OF_CLUBS ("/textures/cards/clubs/ace_of_clubs.png", 23),
        //DIAMONDS
        TWO_OF_DIAMONDS ("/textures/cards/diamonds/two_of_diamonds.png", 24),
        THREE_OF_DIAMONDS ("/textures/cards/diamonds/three_of_diamonds.png", 25),
        FOUR_OF_DIAMONDS ("/textures/cards/diamonds/four_of_diamonds.png", 26),
        FIVE_OF_DIAMONDS ("/textures/cards/diamonds/five_of_diamonds.png", 27),
        SIX_OF_DIAMONDS ("/textures/cards/diamonds/six_of_diamonds.png", 28),
        SEVEN_OF_DIAMONDS ("/textures/cards/diamonds/seven_of_diamonds.png", 29),
        EIGHT_OF_DIAMONDS ("/textures/cards/diamonds/eight_of_diamonds.png", 30),
        NINE_OF_DIAMONDS ("/textures/cards/diamonds/nine_of_diamonds.png", 31),
        TEN_OF_DIAMONDS ("/textures/cards/diamonds/ten_of_diamonds.png", 32),
        JACK_OF_DIAMONDS ("/textures/cards/diamonds/jack_of_diamonds.png", 33),
        QUEEN_OF_DIAMONDS ("/textures/cards/diamonds/queen_of_diamonds.png", 34),
        KING_OF_DIAMONDS ("/textures/cards/diamonds/king_of_diamonds.png", 35),
        ACE_OF_DIAMONDS ("/textures/cards/diamonds/ace_of_diamonds.png", 36),
        //HEARTS
        TWO_OF_HEARTS ("/textures/cards/hearts/two_of_hearts.png", 37),
        THREE_OF_HEARTS ("/textures/cards/hearts/three_of_hearts.png", 38),
        FOUR_OF_HEARTS ("/textures/cards/hearts/four_of_hearts.png", 39),
        FIVE_OF_HEARTS ("/textures/cards/hearts/five_of_hearts.png", 40),
        SIX_OF_HEARTS ("/textures/cards/hearts/six_of_hearts.png", 41),
        SEVEN_OF_HEARTS ("/textures/cards/hearts/seven_of_hearts.png", 42),
        EIGHT_OF_HEARTS ("/textures/cards/hearts/eight_of_hearts.png", 43),
        NINE_OF_HEARTS ("/textures/cards/hearts/nine_of_hearts.png", 44),
        TEN_OF_HEARTS ("/textures/cards/hearts/ten_of_hearts.png", 45),
        JACK_OF_HEARTS ("/textures/cards/hearts/jack_of_hearts.png", 46),
        QUEEN_OF_HEARTS ("/textures/cards/hearts/queen_of_hearts.png", 47),
        KING_OF_HEARTS ("/textures/cards/hearts/king_of_hearts.png", 48),
        ACE_OF_HEARTS ("/textures/cards/hearts/ace_of_hearts.png", 49),
        //SPADES
        TWO_OF_SPADES ("/textures/cards/spades/two_of_spades.png", 50),
        THREE_OF_SPADES ("/textures/cards/spades/three_of_spades.png", 51),
        FOUR_OF_SPADES ("/textures/cards/spades/four_of_spades.png", 52),
        FIVE_OF_SPADES ("/textures/cards/spades/five_of_spades.png", 53),
        SIX_OF_SPADES ("/textures/cards/spades/six_of_spades.png", 54),
        SEVEN_OF_SPADES ("/textures/cards/spades/seven_of_spades.png", 55),
        EIGHT_OF_SPADES ("/textures/cards/spades/eight_of_spades.png", 56),
        NINE_OF_SPADES ("/textures/cards/spades/nine_of_spades.png", 57),
        TEN_OF_SPADES ("/textures/cards/spades/ten_of_spades.png", 58),
        JACK_OF_SPADES ("/textures/cards/spades/jack_of_spades.png", 59),
        QUEEN_OF_SPADES ("/textures/cards/spades/queen_of_spades.png", 60),
        KING_OF_SPADES ("/textures/cards/spades/king_of_spades.png", 61),
        ACE_OF_SPADES ("/textures/cards/spades/ace_of_spades.png", 62),
        // ADD ADDITIONAL TEXTURES HERE (DO NOT REUSE KEYS)
        LOCK ("/textures/icons/lock.png", 63),
        LOGO ("/textures/logo/logo.png", 64),
        SETTING_ICON ("/textures/icons/settingIcon.png", 65),
        EMPTY_IMAGE ("DO NOT TOUCH", 66);


        private final String  path;
        private final int index;

        ImageKey(String s, int index) {
            this.path = s;
            this.index = index;
        }

        public String getPath() {
            return path;
        }

        public int getIndex() {
            return index;
        }


    }
}
