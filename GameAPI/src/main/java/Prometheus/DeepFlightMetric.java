package Prometheus;

public class DeepFlightMetric {

    private static int downloadCounter = 0;
    private static int randUpdateCounter = 0;
    private static int loginCounter = 0;


    public static void incrementdownloadCounter(){
        downloadCounter++;
    }

    public static void incremtUpdateTimeCounter(){
        randUpdateCounter++;
    }


    public static int getDownloadCounter() {
        return downloadCounter;
    }


    public static int getRandUpdateCounter() {
        return randUpdateCounter;
    }

}
