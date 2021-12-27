package it.unibo.oop.lab.advanced;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

public class DrawNumberViewOnPrintStream implements DrawNumberView {

    private static final String NEW_GAME = ": a new game starts!";

    private DrawNumberViewObserver observer;
    private final PrintStream outStream;

    /**
     * Builds a new {@link DrawNumberViewOnPrintStream}.
     * @param fileOutName
     *                  the output file to use
     * @throws FileNotFoundException
     *                  if an error occurs in opening the stream
     */
    public DrawNumberViewOnPrintStream(final String fileOutName) throws FileNotFoundException {
        this.outStream = new PrintStream(new FileOutputStream(new File(fileOutName)));
    }

    /**
     * Builds a new {@link DrawNumberViewOnPrintStream}.
     * @param outStream
     *              the output stream to use
     */
    public DrawNumberViewOnPrintStream(final PrintStream outStream) {
        this.outStream = outStream;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setObserver(final DrawNumberViewObserver observer) {
        this.observer = observer;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void numberIncorrect() {
        this.outStream.println("Incorrect Number.. try again");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void result(final DrawResult res) {
        switch (res) {
        case YOURS_HIGH:
        case YOURS_LOW:
            this.outStream.println(res.getDescription());
            return;
        case YOU_WON:
            this.outStream.println(res.getDescription() + NEW_GAME);
            break;
        default:
            throw new IllegalStateException("Unexpected result: " + res);
        }
        this.observer.resetGame();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void limitsReached() {
        this.outStream.println("You lost" + NEW_GAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayError(final String message) {
        this.outStream.println(message);
    }

}
