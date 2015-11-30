package einarmigerbandit;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

/**
 * Dies ist die einzige Klasse des "Einarmigen Banditen". Sie generiert alle Zahlen und zeigt diese graphisch an.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Automat {
	
	private JFrame frame1 = new JFrame("Einarmiger Bandit");
	private JLabel eins = new JLabel("0");
	private JLabel zwei = new JLabel("0");
	private JLabel drei = new JLabel("0");
	private ArrayList<JLabel> labels = new ArrayList<JLabel>();
	private boolean start = false;
	
	public Automat() {
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setSize(400,200);
		frame1.setResizable(false);
		Container cp = frame1.getContentPane();
		cp.setLayout(new GridLayout(1,4));
		
		Font font = new Font("Arial", Font.BOLD,70);
		labels.add(eins); labels.add(zwei); labels.add(drei);
		
		for(JLabel l:labels) {
			l.setHorizontalAlignment(SwingConstants.CENTER);
			l.setFont(font);
			l.setBorder(BorderFactory.createLineBorder(Color.black, 3));
			l.setOpaque(true);
			l.setBackground(Color.orange);
			l.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(!start) {
						zufall();
					} else {
						auswertung();
					}
				}
			});
			frame1.add(l);
		}
		
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
	
	/**
	 * Diese Methode generiert automatisch alle 50 Millisekunden drei neue Zahlen und zeigt diese an.
	 */
	private void zufall() {
		start = true;
		Random wuerfel = new Random();
		Thread thread = new Thread(new Runnable() {
			  @Override
			  public void run() {
				  while(start) {
					  try {
						  Thread.sleep(50);
						  int x = wuerfel.nextInt(10);
						  int y = wuerfel.nextInt(10);
						  int z = wuerfel.nextInt(10);
						  SwingUtilities.invokeAndWait(new Runnable() {
							  @Override
							  public void run() {
								  eins.setText(String.valueOf(x));
								  zwei.setText(String.valueOf(y));
								  drei.setText(String.valueOf(z));
		                        }
		                  });
						  } catch(InterruptedException | InvocationTargetException e) {}
					  }
				  }
			  }
		);
		thread.start();
	}
	
	/**
	 * Diese Methode setzt den Start-Boolean auf false zurueck, um den Thread zu stoppen.<br>
	 * Sie zeigt ausserdem dem Spieler an, wenn alle drei Zahlen gleich sind und er gewonnen hat.
	 */
	private void auswertung() {
		start = false;
		if(eins.getText().equals(zwei.getText()) && zwei.getText().equals(drei.getText())) {
			JOptionPane.showMessageDialog(null,"Alle Zahlen sind gleich! Du hast gewonnen!","Gewonnen", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void main(String[] args) {
		new Automat();
	}
}