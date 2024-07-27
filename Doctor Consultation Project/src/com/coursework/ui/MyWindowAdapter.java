package com.coursework.ui;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MyWindowAdapter implements WindowListener {
    /**
     * @param e the event to be processed
     */
    @Override
    public void windowOpened(WindowEvent e) {

    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void windowClosing(WindowEvent e) {
        e.getWindow().dispose();
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void windowClosed(WindowEvent e) {

    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void windowIconified(WindowEvent e) {

    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void windowActivated(WindowEvent e) {

    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
