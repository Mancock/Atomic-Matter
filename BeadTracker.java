public class BeadTracker {
    public static void main(String[] args) {
        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        double delta = Double.parseDouble(args[2]);

        // read in frames
        Picture[] images = new Picture[args.length];
        for (int i = 3; i < args.length; i++) {
            Picture picture = new Picture(args[i]);
            images[i] = picture;
        }

        // track distance between frame and previous frame
        for (int i = 3; i < images.length - 1; i++) {
            Picture frame1 = images[i + 1];
            Picture frame2 = images[i];

            // create beadfinder and array of beads for frame1
            BeadFinder finder1 = new BeadFinder(frame1, tau);
            Blob[] beads1 = finder1.getBeads(min);

            // create beadfinder and array of beads for frame2
            BeadFinder finder2 = new BeadFinder(frame2, tau);
            Blob[] beads2 = finder2.getBeads(min);


            for (int j = 0; j < beads1.length; j++) {
                int count = 0; // keeps track of number of beads tracked
                double distanceMin = Double.POSITIVE_INFINITY;

                for (int k = 0; k < beads2.length; k++) {
                    Blob blob1 = beads1[j];
                    Blob blob2 = beads2[k];

                    // calculate distance between blobs
                    double distance = blob2.distanceTo(blob1);

                    // if blob is within frame, update smallest distance
                    if (distance <= delta) {
                        if (distance < distanceMin) {
                            distanceMin = distance;
                            count++;
                        }
                    }
                }
                // if >= 1 bead is tracked, print the minimum distance
                if (count > 0) {
                    StdOut.printf("%.4f\n", distanceMin);
                }
            }
        }
    }
}
