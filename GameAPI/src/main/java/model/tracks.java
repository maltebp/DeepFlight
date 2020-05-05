package model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.morphia.annotations.Id;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.IOException;
import java.util.List;
import javax.persistence.*;



@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class tracks {
    @Id
    private ObjectId _id;

    private String name;

    private String planetId;

    private String seed;

    private List<String> times;









    // Default constructor is required for JSON serialization








    public JSONObject toJSON() throws JsonProcessingException {
        return new JSONObject(new ObjectMapper().writeValueAsString(this));
    }


    public static tracks fromDocument(Document document) {
        JSONObject jsonObject = new JSONObject(document.toJson());
        jsonObject.put("id", jsonObject.get("_id"));
        jsonObject.remove("_id");

        try{
            return new ObjectMapper().readValue(jsonObject.toString(), tracks.class);
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
