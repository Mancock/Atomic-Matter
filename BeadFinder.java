import java.awt.Color;

public class BeadFinder {

    private final Stack<Blob> blobStack; // Stack of blobs
    private final boolean[][] searchedPixels; // Stack of searched pixels
    private final double tau; // luminance threshold
    private final Picture picture; // picture

    //  finds all blobs in the specified picture using luminance threshold tau
    public BeadFinder(Picture picture, double tau) {

        this.blobStack = new Stack<Blob>();


        this.picture = picture;
        int w = picture.width();
        int h = picture.height();

        this.searchedPixels = new boolean[w][h];
        this.tau = tau;

        // figure out a way to store the pixels

        // Iterate over all pixels
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                if (searchedPixels[i][j] = false) {

                    Color c = picture.get(i, j);
                    double luminance = Luminance.intensity(c);

                    // If the pixel is above threshold, create a blob
                    if (luminance >= tau) {
                        Blob blob = new Blob();
                        this.dfs(blob, i, j);
                        blobStack.push(blob);
                    }
                    else searchedPixels[i][j] = true;
                }
            }
        }
    }

    // locates all of the foreground pixels in the same blob as the foreground
    // pixel (x, y)
    private void dfs(Blob blob, int x, int y) {

        blob.add(x, y);

        Color c = picture.get(x, y);
        int w = picture.width();
        int h = picture.height();

        double luminance = Luminance.intensity(c);


        for (int i = 0; i <= searchedPixels.length; i++) {
            for (int j = 0; j <= searchedPixels.length; j++) {
                if (!searchedPixels[i][j] && luminance >= this.tau) {


                    blob.add(x, y);
                    // this.searchedPixels.push(pixel);

                    if (x != 0) {
                        dfs(blob, x - 1, y);
                    }
                    if (y != 0) {
                        dfs(blob, x, y - 1);
                    }
                    if (x != w - 1) {
                        dfs(blob, x + 1, y);
                    }
                    if (y != h - 1) {
                        dfs(blob, x, y + 1);


                    }
                }


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

    //  test client, as described below
    public static void main(String[] args) {
        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        Picture image = new Picture(args[2]);

        BeadFinder finder = new BeadFinder(image, tau);
        Blob[] beads = finder.getBeads(min);

        for (int i = 0; i < beads.length; i++) {
            StdOut.println(beads[i].toString());
        }
    }
}

// Arrays.asList(myBooleanArray).contains(true);
