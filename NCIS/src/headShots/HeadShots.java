package headShots;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class HeadShots {
	private ArrayList<File> fileList;
	private ArrayList<BufferedImage> imgList;
	private String imagePath;
	
	public String setImagePath(String imagePath) {
		this.imagePath = imagePath;
		return imagePath;
	}
	public String getImagePath() {
		return imagePath;
	}
	public HeadShots(){
		fileList = new ArrayList<File>();
		imgList = new ArrayList<BufferedImage>();
	}

	public ArrayList<BufferedImage> getImageList() {
		return imgList;
	}
	public ArrayList getFileList() {
		return fileList;
	}
	public void ReadImageFiles(final File folder){
		    for (final File fileEntry : folder.listFiles()) {
		        if (fileEntry.isDirectory()) {
		        	ReadImageFiles(fileEntry);
		        } else {
		        	fileList.add(fileEntry);
		            //System.out.println(fileEntry.getName());
		        }
		    }
		}
	public void ReadImageList() {
		BufferedImage tmpBufferedImage;
		// Read the images into an ArrayList for faster processing
		try {
			for (int i = 0; i < fileList.size(); i++) {
				//String fileName = ((File)(fileList.get(i))).getName();
				//imgList.add(ImageIO.read(fileList.get(i)));
				tmpBufferedImage = ImageIO.read(fileList.get(i));
				if (tmpBufferedImage != null) {
					imgList.add(ResizeImage(tmpBufferedImage, 500, 0));
				}
			}
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
		}
	}	
    /**
     * Resize a Buffered Image
     * @param bimage
     * @param width
     * @param height
     * @return The resized image
     */
    public static BufferedImage ResizeImage(BufferedImage bimage, int width, int height) {
    	BufferedImage resizedImage = null;
    	if (bimage != null) {
    		if (height == 0) {
				height = (int) (((float)width/bimage.getWidth()) * bimage.getHeight());
    		} else if (width == 0) {
				width = (int) (((float)height/bimage.getHeight()) * bimage.getWidth());
    		}
	    	int type = bimage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bimage.getType();
	    	resizedImage = new BufferedImage(width, height, type);
	    	try {
		    	Graphics2D g = resizedImage.createGraphics();
		    	g.setComposite(AlphaComposite.Src);
		
		    	g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		    	g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		    	g.drawImage(bimage, 0, 0, width, height, null);
		    	g.dispose();    
	    	} catch (Exception ex) {
	    		resizedImage = null;
	    	}
    	} else {	
    		resizedImage = null;
    	}
    	return resizedImage;
    }
}
