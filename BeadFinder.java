import java.awt.Color;
import java.util.Stack;


public class BeadFinder {


    private final Stack<Blob> blobStack; // Stack of blobs
    private final Stack<int[]> searchedPixels; // Stack of searched pixels
    private final double tau; // luminance threshold
    private final Picture picture; // picture

    //  finds all blobs in the specified picture using luminance threshold tau
    public BeadFinder(Picture picture, double tau) {

        this.blobStack = new Stack<Blob>();
        this.searchedPixels = new Stack<int[]>();

        this.picture = picture;
        int w = picture.width();
        int h = picture.height();

        this.tau = tau;

        // Iterate over all pixels
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {

                Color c = picture.get(i, j);
                double luminance = Luminance.intensity(c);

                // If the pixel is above threshold, create a blob
                if (luminance >= tau) {
                    Blob blob = new Blob();
                    this.dfs(blob, i, j);
                    blobStack.push(blob);
                }
            }
        }
    }



    // locates all of the foreground pixels in the same blob as the foreground
    // pixel (x, y)
    private void dfs(Blob blob, int x, int y) {

        int[] pixel = new int[2];
        pixel[0] = x;
        pixel[1] = y;

        Color c = picture.get(x, y);
        int w = picture.width();
        int h = picture.height();

        double luminance = Luminance.intensity(c);

        if (luminance >= this.tau && this.searchedPixels.contains(pixel)) {

            blob.add(x, y);
            this.searchedPixels.push(pixel);

            if (x != 0) {
                dfs(blob, x-1, y);
            }
            if (y != 0) {
                dfs(blob, x, y-1);
            }
            if (x != w - 1) {
                dfs(blob, x+1, y);
            }
            if (y != h - 1) {
                dfs(blob, x, y+1);
            }
        }
    }


    //  returns all beads (blobs with >= min pixels)
    public Blob[] getBeads(int min) {

        Stack<Blob> temp = new Stack<Blob>();

        for (Blob blob : blobStack) {
            if (blob.mass() >= min) {
                temp.push(blob);
                System.out.println(blob.mass());
            }
        }

        int arrSize = temp.size();

        Blob[] blobArray = new Blob[arrSize];

        int i = 0;

        for (Blob blob : temp) {
            blobArray[i] = blob;
            i++;
        }

        return blobArray;
    }

    //  test client, as described below
    public static void main(String[] args) {

        int min = 0;
        double tau = 180.0;
        Picture image = new Picture("Data/run_1/frame00001.jpg");

        BeadFinder finder = new BeadFinder(image, tau);
        Blob[] beads = finder.getBeads(min);

        for (int i = 0; i < beads.length; i++) {
            System.out.println(beads[i]);
        }
    }
}
