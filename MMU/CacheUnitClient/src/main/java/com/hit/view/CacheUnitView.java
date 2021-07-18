
package com.hit.view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;



public class CacheUnitView{

    private final JFrame frame;
    private final CacheUnitPanel panel;
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    JTextArea ta = new JTextArea();
    JLabel taLabel = new JLabel();

    public CacheUnitView()
    {
        frame = new JFrame();
        panel = new CacheUnitPanel();
    }
    public void start()
    {
        panel.run();
    }



    public void addPropertyChangeListener(PropertyChangeListener pcl) {
    	support.addPropertyChangeListener(pcl);
    }
    public void removePropertyChangeListener(PropertyChangeListener pcl) {
    	support.removePropertyChangeListener(pcl);
    }

	public <T> void updateUIData(T status){
    
        if (status.toString().equals("true")){
            ta.setText("\n Succeeded ");
        }
        
        else if (status.toString().equals("false") || status.toString().equals("Empty")){
            ta.setText("\n Failed \n"
                    + "Your MMU failed to get your json file, please check it and try again ");
        }
        else {
            String[] res = ((String) status).split(",");
            ta.setText("Capacity: " + res[1] + "\n\nAlgorithm: " + res[0] +
                    "\n\nTotal numbers of requests: " + res[3] +
                    "\n\nTotal number of DataModels\n(GET/DELETE/UPDATE requests): " + res[4]+
                    "\n\nTotal number of DataModel swaps\n(from cache to disk): " + res[2]);
        }

        ta.validate();
        taLabel.validate();
        panel.revalidate();
        panel.repaint();
    }

    public class CacheUnitPanel extends JPanel implements ActionListener{
        private static final long serialVersionUID = 1L;

        JButton statButton;
        JButton reqButton;
        JLabel wp;
        JLabel label1;

        @Override
        public void actionPerformed(ActionEvent arg0) {}

        public <T> void updateUIData(T t)
        {
            if (t.toString().equals("true"))
            {
                ta.setText("Succeeded ");
            }
            else if (t.toString().equals("false"))
            {
                ta.setText("Failed ");
            }
            else ta.setText(t.toString());  
            ta.invalidate();
        }


        public void run()
        {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setTitle("MMU Project-Yehudit Borer");
            frame.setBounds(500,500,370,350);
            frame.setContentPane(panel);
            panel.setLayout(null);
            ta.setBounds(40, 60, 250, 250);
            taLabel.setBounds(160,90,300,300);
            String taStr = "\nMemory Maneger United\n\nSeeing statistics-\nenter show statistics"
            		+ "\n\nSend new request-\nenter send new request";
            ta.setText(taStr);
            panel.add(taLabel);
            panel.add(ta);
            statButton = new JButton("Show Statistics");
            statButton.setBounds(200, 11, 150, 40);
            statButton.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    PropertyChangeEvent change;
                    change = new PropertyChangeEvent(CacheUnitView.this, "stats", null, "stats");
                    support.firePropertyChange(change);
                }
            });
            panel.add(statButton);

            reqButton = new JButton("Load a Request");
            reqButton.setBounds(10,11,150, 40);
            reqButton.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent e)
                {
                    JFileChooser fc = new JFileChooser();
                    fc.setCurrentDirectory(new File("."));
                    int result = fc.showOpenDialog(new JFrame());
                    if (result == JFileChooser.APPROVE_OPTION)
                    {
                        File selectedFile = fc.getSelectedFile();
                        if (selectedFile != null)
                        {
                            try
                            {
                                PropertyChangeEvent change;
                                change = new PropertyChangeEvent(CacheUnitView.this,"load",null,selectedFile.getPath());
                                support.firePropertyChange(change);

                            }
                            catch (Exception ex)
                            {
                                ex.printStackTrace();
                            }
                        }
                    }
                }

            });
            panel.add(reqButton);
            wp = new JLabel("");
            wp.setBounds(0, 0, screenSize.width, screenSize.height);
            panel.add(wp);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        }
  
    }
}


