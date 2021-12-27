package it.unibo.oop.lab.advanced;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class ResourceLoader {

    private static final int DEFAULT_MIN = 0;
    private static final int DEFAULT_MAX = 100;
    private static final int DEFAULT_ATTEMPTS = 10;

    private int min = DEFAULT_MIN;
    private int max = DEFAULT_MAX;
    private int attempts = DEFAULT_ATTEMPTS;
    private final String configFileName;

    /**
     * Builds a new {@link ResourceLoader}.
     * @param configFileName
     *              the configuration file to be used to get the resources
     */
    ResourceLoader(final String configFileName) {
        this.configFileName = configFileName;
    }

    /**
     * Loads the resources from the configuration file.
     * @throws IOException
     *              if reading fails
     */
    void load() throws IOException {
        try (var reader = new BufferedReader(
                new InputStreamReader(ClassLoader.getSystemResourceAsStream(this.configFileName)))) {
            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                final StringTokenizer tokenizer = new StringTokenizer(line, ":", false);
                if (tokenizer.countTokens() == 2) {
                    final String name = tokenizer.nextToken();
                    final int value = Integer.parseInt(tokenizer.nextToken().strip());
                    switch (name) {
                    case "minimum":
                        setMin(value);
                        break;

                    case "maximum":
                        setMax(value);
                        break;

                    case "attempts":
                        setAttempts(value);
                        break;

                    default:
                        throw new IllegalStateException("Error in configuration file format."
                                + " Default configuration is set");
                    }
                } else {
                    throw new IllegalStateException("Error in configuration file format."
                            + " Default configuration is set");
                }
            }
        }
    }

    private void setMin(final int min) {
        this.min = min;
    }

    private void setMax(final int max) {
        this.max = max;
    }

    private void setAttempts(final int attempts) {
        this.attempts = attempts;
    }

    /**
     * @return
     *      min value loaded
     */
    int getMin() {
        return this.min;
    }

    /**
     * @return
     *      max value loaded
     */
    int getMax() {
        return this.max;
    }

    /**
     * @return
     *      attempts value loaded
     */
    int getAttempts() {
        return this.attempts;
    }

}
