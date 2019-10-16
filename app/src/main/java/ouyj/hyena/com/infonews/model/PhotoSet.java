package ouyj.hyena.com.infonews.model;

import java.io.Serializable;
import java.util.List;

public class PhotoSet implements Serializable {

    private List<Photo> photos;
    public List<Photo> getPhotos(){
        return this.photos;
    }
}
