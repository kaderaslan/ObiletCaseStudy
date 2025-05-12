package utility;

public class Config {
    public static final String BASE_URL = "https://www.obilet.com/";

    public static final int STEP_DELAY = 1000;

    public static void waitForObservation() {
        if (STEP_DELAY > 0) {
            try {
                Thread.sleep(STEP_DELAY);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
