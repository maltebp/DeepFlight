package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.IOException;
import java.util.HashMap;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity("tracks")
public class Track {
    @Id
    private String id;

    private String name;

    private String planetId;

    private String seed;

    private HashMap<String,Integer> times;








    // Default constructor is required for JSON serialization








    public JSONObject toJSON() throws JsonProcessingException {
        return new JSONObject(new ObjectMapper().writeValueAsString(this));
    }


    public static Track fromDocument(Document document) {
        JSONObject jsonObject = new JSONObject(document.toJson());
        jsonObject.put("id", jsonObject.get("_id"));
        jsonObject.remove("_id");

        try{
            return new ObjectMapper().readValue(jsonObject.toString(), Track.class);
        }catch(IOException e){
            e.printStackTrace();
            return null;
        }
    }
/*
    public Document toDocument(){
        JSONObject json = new JSONObject(this);
        json.put("_id", id);
        json.remove("id");
        return Document.parse(json.toString());
    }
*/

}
