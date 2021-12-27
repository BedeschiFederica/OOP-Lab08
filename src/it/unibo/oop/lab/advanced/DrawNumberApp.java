package it.unibo.oop.lab.advanced;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final DrawNumber model;
    private final List<DrawNumberView> views;

    /**
     * Builds a new {@link DrawNumberApp}.
     * @param configFileName
     *              the configuration file to be used to get the resources
     * @param views
     *              the views of the application
     */
    public DrawNumberApp(final String configFileName, final DrawNumberView... views) {
        this.views = List.of(views);
        for (final DrawNumberView view: this.views) {
            view.setObserver(this);
            view.start();
        }
        final ResourceLoader resourceLoader = new ResourceLoader(configFileName);
        try {
            resourceLoader.load();
        } catch (IOException | IllegalStateException e) {
            for (final DrawNumberView view: this.views) {
                view.displayError(e.getMessage());
            }
        }
        this.model = new DrawNumberImpl(resourceLoader.getMin(),
                resourceLoader.getMax(), resourceLoader.getAttempts());
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView view: this.views) {
                view.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView view: this.views) {
                view.numberIncorrect();
            }
        } catch (AttemptsLimitReachedException e) {
            for (final DrawNumberView view: this.views) {
                view.limitsReached();
            }
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        System.exit(0);
    }

    /**
     * @param args
     *            ignored
     * @throws FileNotFoundException
     *            if an error occurs in opening the stream
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp("config.yml", new DrawNumberViewImpl(), new DrawNumberViewOnPrintStream("logfile.txt"));
    }

}
