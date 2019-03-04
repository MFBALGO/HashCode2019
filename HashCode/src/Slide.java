
import java.util.ArrayList;



public class Slide {
    Photo photo1;
    Photo photo2;
    
    ArrayList<String> totalTag= new ArrayList<String>();

    public Slide(Photo photo1, Photo photo2) {
        this.photo1 = photo1;
        this.photo2 = photo2;
    }
    
    public Slide(Photo photo1) {
        this.photo1 = photo1;
    }

    public Slide() {
    }
    
    public void createTagList(){
        for(int i=0;i<photo1.totalTags;i++){
            totalTag.add(photo1.tags.get(i));
        }
        
        if(photo2!=null){
            for(int i=0;i<photo2.totalTags;i++){
                if (!totalTag.contains(photo2.tags.get(i)))
                    totalTag.add(photo2.tags.get(i));
            }
        }
    }     
}
