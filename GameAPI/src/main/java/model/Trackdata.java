package model;


import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity("trackdata")
public class Trackdata {


    @Id
    private String id;
    private byte[] data;

    public byte[] getTrackdata(){
        return data;
    }
}
