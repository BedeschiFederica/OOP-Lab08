package it.unibo.oop.lab.advanced;

import java.io.IOException;

import it.unibo.oop.lab.mvc.SimpleGUI;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final DrawNumber model;
    private final DrawNumberView view;

    /**
     * Builds a new {@link DrawNumberApp}.
     * @param configFileName
     *              the configuration file to be used to get the resources
     */
    public DrawNumberApp(final String configFileName) {
        this.view = new DrawNumberViewImpl();
        this.view.setObserver(this);
        this.view.start();
        final ResourceLoader resourceLoader = new ResourceLoader(configFileName);
        try {
            resourceLoader.load();
        } catch (IOException | IllegalStateException e) {
            this.view.displayError(e.getMessage());
        }
        this.model = new DrawNumberImpl(resourceLoader.getMin(),
                resourceLoader.getMax(), resourceLoader.getAttempts());
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            this.view.result(result);
        } catch (IllegalArgumentException e) {
            this.view.numberIncorrect();
        } catch (AttemptsLimitReachedException e) {
            view.limitsReached();
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
     */
    public static void main(final String... args) {
        new DrawNumberApp("config.yml");
    }

}
