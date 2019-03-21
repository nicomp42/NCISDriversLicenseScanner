package headShots;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		HeadShots hs = new HeadShots();
		hs.setImagePath("./Images");
		BufferedImage img = null;
		final File folder = new File(hs.getImagePath());
		hs.ReadImageFiles(folder);
		hs.ReadImageList();
	}
}
