package it.unibo.oop.lab.mvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ControllerImpl implements Controller {

    private final List<String> printedStringsHistory = new ArrayList<>();
    private String stringToPrint;

    /*
     * 1) A method for setting the next string to print. Null values are not
     * acceptable, and an exception should be produced
     * 
     * 2) A method for getting the next string to print
     * 
     * 3) A method for getting the history of the printed strings (in form of a List
     * of Strings)
     * 
     * 4) A method that prints the current string. If the current string is unset,
     * an IllegalStateException should be thrown
     * 
     */

    /**
     * {@inheritDoc}
     */
    @Override
    public void setStringToPrint(final String str) {
        this.stringToPrint = Objects.requireNonNull(str);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getStringToPrint() {
        return this.stringToPrint;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> getPrintedStringsHistory() {
        return List.copyOf(this.printedStringsHistory);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void printString() {
        if (this.stringToPrint == null) {
            throw new IllegalStateException("the current string is unset");
        }
        System.out.println(this.stringToPrint);
        updateHistory();
    }

    private void updateHistory() {
        this.printedStringsHistory.add(this.stringToPrint);
        this.stringToPrint = null;
    }

}
