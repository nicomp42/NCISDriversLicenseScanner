/*
 * Bill Nicholson
 * nicholdw@ucmail.uc.edu
 * A dumb NCIS simulator
 */
// Swing timer adapted from http://www.javapractices.com/topic/TopicAction.do?Id=160
package headShots;

import java.awt.AlphaComposite; 
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JButton;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class MyGUI extends JFrame {

	/**
	 * 
	 */
	private final int imageWidth = 600, imageHeight = 500;
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static boolean paintMe;
	private static HeadShots hs = new HeadShots();
	private JLabel lblOK;
	private JLabel picLabel, targetPicLabel;
	private int headShotIdx;
	BufferedImage targetImage;
	String targetFile;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					paintMe = false;
					MyGUI frame = new MyGUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	/**
	 * Create the frame.
	 */
	public MyGUI() {
		setTitle("NCIS Top Secret");
		
		hs.setImagePath("./Images");
		BufferedImage img = null;
		final File folder = new File(hs.getImagePath());
		hs.ReadImageFiles(folder);
		hs.ReadImageList();
		
		headShotIdx = 0;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(10, 10, 800, 550);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		picLabel = new JLabel();
		picLabel.setBounds(50,  10,  imageWidth,  imageHeight);
		picLabel.setVisible(true);
		getContentPane().add(picLabel);

		targetPicLabel = new JLabel();
		targetPicLabel.setBounds(10,  425,  50,  50);
		targetPicLabel.setVisible(true);
		getContentPane().add(targetPicLabel);

		JButton btnGo = new JButton("Go");
		btnGo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				startHeadShotUpdaterTimer();
				DisplayHeadShots();
			}
		});
		btnGo.setBounds(10, 489, 91, 23);
		contentPane.add(btnGo);
		
		lblOK = new JLabel(".");
		lblOK.setBounds(10, 34, 46, 14);
		contentPane.add(lblOK);
		
		btnBrowse = new JButton("Browse...");
		btnBrowse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				ChooseFile();
			}
		});
		btnBrowse.setBounds(115, 489, 91, 23);
		contentPane.add(btnBrowse);
		
		JCheckBox cbRandomSelection = new JCheckBox("Pick Someone");
		cbRandomSelection.setSelected(false);
		cbRandomSelection.setVisible(true);
		cbRandomSelection.setLocation(150, 489);
		contentPane.add(cbRandomSelection);
		
		
	}
	private void RepaintMe() {
		super.repaint();
	}
	private void ChooseFile() {
	    JFileChooser chooser = new JFileChooser();
	    FileNameExtensionFilter filter = new FileNameExtensionFilter(
	        "JPG & GIF Images", "jpg", "gif");
	    chooser.setFileFilter(filter);
	    int returnVal = chooser.showOpenDialog(getParent());
	    if(returnVal == JFileChooser.APPROVE_OPTION) {
	    	try {
	    		String targetFile = chooser.getSelectedFile().getAbsolutePath();
	    		targetImage = ImageIO.read(new File(targetFile));
	    		targetImage = HeadShots.ResizeImage(targetImage, 50, 50);
		    	//System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
				ImageIcon tmpImageIcon = new ImageIcon(targetImage);
				targetPicLabel.setIcon(tmpImageIcon);
				RepaintMe();
			} catch (IOException e) {
				System.out.println("ChooseFile(): " + e.getLocalizedMessage());
			}
	    }
	}
	ImageIcon tmpImageIcon;
	private void DisplayHeadShots() {
		tmpImageIcon = new ImageIcon();
		paintMe = true;
		
		//if (headShotIdx < hs.getImageList().size()-1) { 
			headShotIdx++;
			headShotIdx %= (hs.getImageList().size()-1);
			//if (paintMe == false){i = i - 1; continue;}
			tmpImageIcon = new ImageIcon();
			// Skip any images that are null. They will cause an exception.
			if (hs.getImageList().get(headShotIdx) != null)
				tmpImageIcon.setImage((BufferedImage)(hs.getImageList()).get(headShotIdx));
			
			picLabel.setIcon(tmpImageIcon);
    		paintMe = false;
    		lblOK.setText(Integer.toString(headShotIdx));
    		//super.revalidate();
			super.repaint();
		    //if (!worker.isDone()) {
		    //    return;
			// }
		//	} else {
		//		fTimer.stop(); //stops notifying registered listeners
		//	    fTimer.removeActionListener(fHeadShotUpdater); //removes the one registered listener
		//	    fHeadShotUpdater = null;
		//	    fTimer = null;		
		//	}
		}
    public void paint(Graphics g) {
    	/*
    	if (paintMe == true) {
    		JLabel picLabel = new JLabel(new ImageIcon((BufferedImage)(hs.getImageList()).get(0)));
    		picLabel.setBounds(100,  5,  100,  100);
    		picLabel.setVisible(true);
    		add(picLabel);
    		paintMe = false;
    		lblOK.setText("OK");
    	}
    	*/
    	super.paint(g);
		paintMe = true;
    }
    private javax.swing.Timer fTimer;
    private ActionListener fHeadShotUpdater;
    // ToDo: Tweak the UPDATE_FREQ value to increase/decrease the update rate of the images
    // ToDo: make this configurable in the GUI
    private static final int UPDATE_FREQ = 2 * 50; //Consts.MILLISECONDS_PER_SECOND;
    private static final long SLEEP_INTERVAL = 10;
    private JButton btnBrowse;
    private void startHeadShotUpdaterTimer(){
        //SwingWorker isn't used here, since the action happens more than once,
        //and the task doesn't take very long
        fHeadShotUpdater = new ActionListener() {
          public void actionPerformed(ActionEvent evt) {
            //this returns quickly; it won't lock up the GUI
        	  DisplayHeadShots();
          }
        };
        fTimer = new javax.swing.Timer(UPDATE_FREQ, fHeadShotUpdater);
        fTimer.start();    
    }
}
