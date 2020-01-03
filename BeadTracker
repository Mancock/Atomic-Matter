public class BeadTracker {
    public static void main(String[] args) {
        int min = Integer.parseInt(args[0]);
        double tau = Double.parseDouble(args[1]);
        double delta = Double.parseDouble(args[2]);
        Picture image = new Picture(args[3]);

        BeadFinder finder = new BeadFinder(image, tau);
        Blob[] beads = finder.getBeads(min);


        double t = 0; // time

        for (int i = 0; i < beads.length; i++) {
            Blob bead = beads[i];
            Blob blob1 = beads[i + 1];

            double distance = bead.distanceTo(blob1);
            if (distance > delta) {
                break;
            }
            StdOut.println(distance);
            t += delta;
        }

    }
}
