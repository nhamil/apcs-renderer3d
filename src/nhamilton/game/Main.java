package nhamilton.game;

import nhamilton.game.util.Console;


/**
 * 
 * Launching class, runs a Game object.
 * 
 * @author Nicholas Hamilton
 *
 */

public class Main
{
    public static void main(String args[])
    {
        Console.setLevel(Console.STATS);
        Console.show();
        new Game().run();
    }
}
