
import java.util.ArrayList;


public class Photo {
    int index;
    char picType;
    int totalTags;
    ArrayList<String> tags = new ArrayList<String>();

    public Photo(char picType, int totalTags, ArrayList<String> tags,int index) {
        this.picType = picType;
        this.totalTags = totalTags;
        this.tags = tags;
        this.index=index;
    }

    public Photo() {
    }
    
    public int comparePhotos(Photo p1, Photo p2){
         return -1;
    }
    
}
