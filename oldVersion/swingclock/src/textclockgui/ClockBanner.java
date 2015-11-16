/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package textclockgui;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author hvd
 */
public class ClockBanner extends JPanel {
    
    private final JLabel titleLabel;

    public ClockBanner() {
        
        setLayout(new FlowLayout(FlowLayout.CENTER));
        setBackground(Color.black);
        
        titleLabel = new JLabel("Fontys SEBI Venlo");
        titleLabel.setFont( new Font( "Arial", Font.BOLD, 24 ) );
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBackground( Color.BLACK);
        add(titleLabel);
    }
}
