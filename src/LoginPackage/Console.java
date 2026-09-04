package LoginPackage;

import java.util.Scanner;

/**
 * Single shared Scanner for all console (System.in) input.
 *
 * Java's Scanner reads System.in in internally buffered chunks, so creating
 * a new Scanner(System.in) part-way through a session can trap already
 * -read-but-unconsumed input inside the old Scanner's buffer, making it
 * invisible to the new one. Every console-based class in this app should
 * read through this shared instance instead of constructing its own
 * Scanner, so no input is ever silently lost.
 */
public final class Console {

    public static final Scanner IN = new Scanner(System.in);

    private Console() {
    }
}
