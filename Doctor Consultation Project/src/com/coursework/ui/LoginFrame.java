package com.coursework.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends MyWindowAdapter implements ActionListener{
    private JFrame jFrame;
    private JButton login;
    private JButton register,guest,myBookings;
    public LoginFrame(){
        initialize();
    }
    private void initialize(){
        jFrame = new JFrame();
        jFrame.getContentPane().setBackground(new Color(0, 0, 0));
        jFrame.setBounds(100, 100, 1200, 570);
        jFrame.addWindowListener(this);
        jFrame.getContentPane().setLayout(null);
        jFrame.setVisible(true);

        Font f = new Font(Font.SANS_SERIF, Font.BOLD, 30);

        ImageIcon logo = new ImageIcon("logo.png");
        JLabel logoLabel = new JLabel("");
        logoLabel.setIcon(logo);
        logoLabel.setBounds(350,240,500,200);

        login = new JButton("Login");
        login.setBounds(195,100,275,40);
        login.addActionListener(this);
        login.setBackground(Color.cyan);

        register = new JButton("Register");
        register.setBounds(480,100,275,40);
        register.addActionListener(this);
        register.setBackground(Color.cyan);

        guest = new JButton("Guest");
        guest.setBounds(765,100,275,40);
        guest.addActionListener(this);
        guest.setBackground(Color.cyan);

        myBookings = new JButton("Bookings");
        myBookings.setBounds(300,130,275,40);
        myBookings.addActionListener(this);
        myBookings.setBackground(Color.cyan);

        jFrame.getContentPane().add(register);
        jFrame.getContentPane().add(login);
        jFrame.getContentPane().add(logoLabel);
        jFrame.getContentPane().add(guest);
        jFrame.getContentPane().add(myBookings);
        login.setFont(f);register.setFont(f);guest.setFont(f);myBookings.setFont(f);
    }

    /**
     * This method is used to get the frame
     * @return jFrame
     */
    public JFrame getJFrame() {
        return jFrame;
    }

    /**
     * This method is used to get the guest button object
     * @return guest
     */
    public JButton getGuest() {
        return guest;
    }

    /**
     * This method is used to get the login button
     * @return login
     */
    public JButton getLogin() {
        return login;
    }

    /**
     * This method is used to get the register button
     * @return register
     */
    public JButton getRegister() {
        return register;
    }

    /**
     * This method is used to get the myBookings button
     * @return
     */
    public JButton getMyBookings() {return myBookings;}

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == login){
            jFrame.setVisible(false);
            LoginDetailsFrame loginDetailsFrame = new LoginDetailsFrame();
            loginDetailsFrame.getJFrame().setVisible(true);
            jFrame.dispose();
        }

        if (e.getSource() == register){
            jFrame.setVisible(false);
            RegistrationFrame registrationFrame = new RegistrationFrame();
            registrationFrame.getJFrame().setVisible(true);
            jFrame.dispose();
        }

        if (e.getSource() == guest){
            jFrame.setVisible(false);
            GuestFrame guestFrame = new GuestFrame();
            guestFrame.getJFrame().setVisible(true);
            jFrame.dispose();
        }

        if (e.getSource() == myBookings){
            jFrame.setVisible(false);
            BookingSearchFrame bookingSearchFrame = new BookingSearchFrame();
            bookingSearchFrame.getjFrame().setVisible(true);
            jFrame.dispose();

        }
    }
}
