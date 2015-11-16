/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.fontys.sebivenlo.numbercollector;

import static java.text.CharacterIterator.DONE;
import java.text.StringCharacterIterator;

/**
 * State machine implementation to collect double from a
 * StringCharacterIterator.
 * Helper to read svg path strings.
 * @author hom
 */
public class DoubleCollector {

    private final StringCharacterIterator source;
    private StringBuilder sb = null;
    /** True until encounter of '%period;' or  'e'.*/
    private boolean integral = true;

    public DoubleCollector( StringCharacterIterator source ) {
        this.source = source;
    }

    public DoubleCollector(String s) {
        this(new StringCharacterIterator(s));
    }
    /**
     * Advance to and get the next character of this.iterator.
     *
     * @return the new character.
     */
    public char next() {
        return source.next();
    }

    /**
     * Get the character this collector is looking at.
     *
     * @return the looked at character
     */
    public char lookingAt() {
        return source.current();
    }

    /**
     * Advance the iterator until looking at is no more whitespace.
     */
    public void skipOverWhiteSpace() {
        while ( Character.isWhitespace( lookingAt() ) ) {
            next();
        }
    }

    /**
     * Get the next double if any.
     *
     * @return the next double if any.
     */
    public double nextDouble() {
        integral = true;
        state = Character.isWhitespace( lookingAt() ) ? lookingAtWhiteSpace
                : start;
        sb = new StringBuilder();
        while ( state != complete ) {
            state.accept();
        }
        return Double.parseDouble( sb.toString() );
    }

    /**
     * Get the current numeric string as a long.
     *
     * @return the collect long value.
     */
    public long getAslong() {
        return Long.parseLong( sb.toString() );
    }

    /**
     * Get the current numeric string as a long.
     *
     * @return the int value
     */
    public int getAsInt() {
        return Integer.parseInt( sb.toString() );
    }

    /**
     * Append the current character to the output buffer and advance the
     * iterator.
     *
     * @param peek
     */
    private void consume( char peek ) {
        sb.append( peek );
        next();
        if ( lookingAt() == DONE ) {
            state = complete;
        }
    }

    public boolean isIntegral() {
        return integral;
    }

    private interface State {

        default void accept() {
        }
    }
    public boolean hasNext(){
        return DONE != lookingAt();
    }

    private final State complete = new StateComplete();
    private final State digits = new AcceptingDigits();
    private final State periodOrDigit = new AcceptingPeriodOrDigits();
    private final State start = new AcceptingDigitsSignOrPeriod();
    private final State lookingAtWhiteSpace = new LookingAtWhiteSpace();
    private final State exponentDigitsSign = new ExponentDigitsSign();
    private final State exponentDigits = new ExponentDigits();
    private State state = start;

    private final class ExponentDigitsSign implements State {

        @Override
        public void accept() {
            char peek = lookingAt();

            if ( Character.isDigit( peek ) || peek == '+' || peek
                    == '-' ) {
                state = exponentDigits;
                consume( peek );
            } else {
                state = complete;
            }
        }

    }

    private final class ExponentDigits implements State {

        @Override
        public void accept() {
            char peek = lookingAt();

            if ( Character.isDigit( peek ) ) {
                consume( peek );
            } else {
                state = complete;
            }
        }

    }


    private final class LookingAtWhiteSpace implements State {

        @Override
        public void accept() {

            if ( !Character.isWhitespace( lookingAt() ) ) {
                state = start;
            } else {
                next();
            }
        }
    }

    public String getState(){
        return state.getClass().getSimpleName();
    }
    private final class AcceptingPeriodOrDigits implements State {

        @Override
        public void accept() {
            char peek = lookingAt();
            if ( Character.isDigit( peek ) ) {
                consume( peek );
            } else if ( peek == '.' ) {
                integral = false;
                state = digits;
                consume( peek );
            } else if ( peek == 'e' || peek == 'E' ) {
                integral = false;
                state = exponentDigitsSign;
                consume( peek );
            } else if ( peek == DONE ) {
                state = complete;
            } else {
                state = complete;
            }
        }
    }

    private final class AcceptingDigits implements State {

        @Override
        public void accept() {
            char peek = lookingAt();
            if ( Character.isDigit( peek ) ) {
                consume( peek );
            } else if ( peek == 'e' || peek == 'E' ) {
                integral = false;
                state = exponentDigitsSign;
                consume( peek );
            } else {
                state = complete;
            }
        }
    }

    private final class AcceptingDigitsSignOrPeriod implements State {

        @Override
        public void accept() {
            char peek = lookingAt();
            if ( Character.isDigit( peek ) || peek == '+' || peek
                    == '-' ) {
                state = periodOrDigit;
                consume( peek );
            } else if ( peek == '.' ) {
                integral = false;
                state = digits;
                consume( peek );
            } else if ( peek == 'e' || peek == 'E' ) {
                state = exponentDigitsSign;
                consume( peek );
            } else if ( peek == DONE ) {
                state = complete;
            } else {
                state = complete;
            }
        }

    }

    private class StateComplete implements State {
    }
}
