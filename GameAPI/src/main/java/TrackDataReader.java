import java.io.*;
import java.net.URL;

/** JUST A CLASS FOR TESTING */
public class TrackDataReader {


    /**
     * Loads Track block data from a test file in the 'testtrackdata' folder
     */
    public static byte[] getTrackData(String filename){

        // Append folder name
        filename = "testtrackdata/"+filename;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();


        byte[] allBytes = null;

        try ( InputStream inputStream = classloader.getResourceAsStream(filename) ) {
            URL url = classloader.getResource(filename);
            if( url == null )
                throw new FileNotFoundException(String.format("Couldn't find file '%s' in resources.", filename));

            String path = url.getPath();
            long fileSize = new File(path).length();

            System.out.println("\nLoading Track Data");
            System.out.println("File: " + path);
            System.out.println("Size: " + fileSize + " bytes");

            allBytes = new byte[(int) fileSize];

            int bytesRead = inputStream.read(allBytes);
            System.out.println("Bytes read: " + bytesRead);

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return allBytes;

    }


}
