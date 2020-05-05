package database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

/** JUST A CLASS FOR TESTING */
public class TrackDataReader {


    /**
     * Loads Track block data from a test file in the 'testtrackdata' folder
     */
    public static byte[] getTrackData(String filename){
        // Append folder name
        filename = "testtrackdata/"+filename;

        ClassLoader classloader = Thread.currentThread().getContextClassLoader();


        byte[] bytes = null;


        try ( InputStream inputStream = classloader.getResourceAsStream(filename) ) {
            URL url = classloader.getResource(filename);
            if( url == null )
                throw new FileNotFoundException(String.format("Couldn't find file '%s' in resources.", filename));

            String path = url.getPath();

            long fileSize = new File(path).length();

            System.out.println("\nLoading Track Data");
            System.out.println("File: " + path);
            System.out.println("Size: " + fileSize + " bytes");


            ArrayList<Byte> allBytes = new ArrayList<>();
            int input = 0;
            while( (input = inputStream.read()) != -1 ){
                allBytes.add( (byte) input );
            }

            bytes = new byte[allBytes.size()];
            int i = 0;
            for( Byte b : allBytes ){
                bytes[i++] = b;
            }

            System.out.println("Bytes read: " + allBytes.size());

        } catch (IOException ex) {
            ex.printStackTrace();
        }


        return bytes;
    }


}
