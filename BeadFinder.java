import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

public class BeadFinder {

    private List<Blob> blobs;
    private List<ArrayList<Integer>> searchedPixels;
    private double tau;
    private int w;
    private int h;
    private Picture picture;


    //  finds all blobs in the specified picture using luminance threshold tau
    public BeadFinder(Picture picture, double tau) {

        this.blobs = new ArrayList<Blob>();
        this.searchedPixels = new ArrayList<ArrayList<Integer>>();

        this.picture = picture;
        this.w = picture.width();
        this.h = picture.height();
        
        this.tau = tau;
    }

    // Find all clusters of white pixels(cluster only counts those pixels touching non-diagonally)
    private void findBlobs() {

        // Iterate over all pixels
        for (int i = 0; i < this.w; i++) {
            for (int j = 0; j < this.h; j++) {

                ArrayList<Integer> pixel = new ArrayList<Integer>();
                pixel.add(i);
                pixel.add(j);

                if (!this.searchedPixels.contains(pixel)) {

                    double luminance = this.getLuminance(picture, i, j);
                    
                    // If the pixel is above threshold, create a blob
                    if (luminance >= this.tau) {
                        Blob blob = new Blob();
                        this.checkSurroundings(blob, i, j);
                        this.blobs.add(blob);
                    }
                }
            }
        }
    }

    // Check surroundings of blob recursively
    private void checkSurroundings(Blob blob, int x, int y) {

        ArrayList<Integer> pixel = new ArrayList<Integer>();
        pixel.add(x);
        pixel.add(y);
        
        double luminance = this.getLuminance(this.picture, x, y);

        if (luminance >= this.tau && !this.searchedPixels.contains(pixel)) {

            blob.add(x, y);
            this.searchedPixels.add(pixel);

            if (x != 0) {
                checkSurroundings(blob, x-1, y);
            }
            if (y != 0) {
                checkSurroundings(blob, x, y-1);
            }
            if (x != this.w - 1) {
                checkSurroundings(blob, x+1, y);
            }
            if (y != this.h - 1) {
                checkSurroundings(blob, x, y+1);
            }
            
        }

    }

    // Returns the luminance of a given pixel
    private double getLuminance(Picture picture, int x, int y) {

        Color c = picture.get(x, y);
        int r = c.getRed();
        int g = c.getGreen();
        int b = c.getBlue();
        double luminance = 0.299 * r + 0.587 * g + 0.114 * b;

        return luminance;
    }

    //  returns all beads (blobs with >= min pixels)
    public Blob[] getBeads(int min) {

        this.findBlobs();

        List<Blob> temp = new ArrayList<Blob>();

        for (int i=0; i < this.blobs.size(); i++) {
            if (this.blobs.get(i).mass() > min) {
                temp.add(this.blobs.get(i));
            }
        }

        int arrSize = temp.size();

        Blob[] blobArray = new Blob[arrSize];

        for (int i=0; i<arrSize; i++) {
            blobArray[i] = temp.get(i);
        }

        return blobArray;
    }

    //  test client, as described below
    public static void main(String[] args) {

        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        Picture image = new Picture(args[2]);

        BeadFinder finder = new BeadFinder(image, tau);

        Blob[] beads = finder.getBeads(min);

        for (int i=0; i<beads.length; i++) {
            StdOut.println(beads);
        }
    }
}
