package model;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import org.bson.types.ObjectId;
import org.json.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
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

    public JSONObject toJSON() {
        return new JSONObject(this);
    }

}
