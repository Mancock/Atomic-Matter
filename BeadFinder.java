import java.awt.Color;

public class BeadFinder {

    private final Stack<Blob> blobStack; // Stack of blobs
    private final Stack<int[]> searchedPixels; // Stack of searched pixels
    private final double tau; // luminance threshold
    private final Picture picture; // picture
    private int x; // x value of pixel
    private int y; // y value of pixel


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
                    this.dfs();
                    blobStack.push(blob);
                }
            }
        }
    }


    // locates all of the foreground pixels in the same blob as the foreground
    // pixel (x, y)
    private void dfs() {

        Blob blob = new Blob();

        int[] pixel = new int[2];
        pixel[0] = x;
        pixel[1] = y;

        Color c = picture.get(x, y);
        int w = picture.width();
        int h = picture.height();

        double luminance = Luminance.intensity(c);

        if (luminance >= this.tau) {

            blob.add(x, y);
            this.searchedPixels.push(pixel);

            if (x != 0) {
                this.x = x - 1;
                dfs();
            }
            if (y != 0) {
                this.y = y - 1;
                dfs();
            }
            if (x != w - 1) {
                this.x = x + 1;
                dfs();
            }
            if (y != h - 1) {
                this.y = y + 1;
                dfs();
            }

        }
    }


    //  returns all beads (blobs with >= min pixels)
    public Blob[] getBeads(int min) {

        this.dfs();

        Stack<Blob> temp = new Stack<Blob>();

        for (Blob blob : blobStack) {
            if (blob.mass() >= min) {
                temp.push(blob);
            }
        }

        int arrSize = temp.size();

        Blob[] blobArray = new Blob[arrSize];

        for (Blob blob : temp) {
            int i = 0;
            blobArray[i] = blob;
            i++;
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

        for (int i = 0; i < beads.length; i++) {
            StdOut.println(beads[i]);
        }
    }
}
