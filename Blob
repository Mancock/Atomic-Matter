public class Blob {

    private int x1; // x-coordinate of pixel
    private int y1; // y-coordinate of pixel
    private int count; // number of pixels added to blob
    private double cx; // center of mass x
    private double cy; // center of mass y

    //  creates an empty blob
    public Blob() {
    }

    //  adds pixel (x, y) to this blob
    public void add(int x, int y) {
        this.x1 = x1 + x;
        this.y1 = y1 + y;
        count++;
        this.cx = (double) x1 / count;
        this.cy = (double) y1 / count;
    }

    //  number of pixels added to this blob
    public int mass() {
        return this.count;
    }

    //  Euclidean distance between the center of masses of the two blobs
    public double distanceTo(Blob that) {

        double distanceX = Math.pow(this.cx - that.cx, 2);
        double distanceY = Math.pow(this.cy - that.cy, 2);
        return Math.sqrt(distanceX + distanceY);
    }

    //  string representation of this blob (see below)
    public String toString() {
        return String.format("%2d (%8.4f, %8.4f)", count, cx, cy);
    }

    public static void main(String[] args) {

        // create new Blob
        Blob spot = new Blob();

        // Add 3 pixels to Blob
        spot.add(6, 7);
        spot.add(3, 9);
        spot.add(4, 2);

        // Create second Blob and add 3 pixels
        Blob splotch = new Blob();
        splotch.add(8, 3);
        splotch.add(2, 6);
        splotch.add(8, 4);

        // Return mass and distance
        StdOut.println(spot.mass());
        StdOut.println(spot.distanceTo(splotch));
    }
}
