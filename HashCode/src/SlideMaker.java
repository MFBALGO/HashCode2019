
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;


public class SlideMaker {
    
    ArrayList<Photo> Horizontal = new ArrayList<>();//contains Horizontal Photos
    ArrayList<Photo> Vertical = new ArrayList<>();//contains Vertical Photos
    ArrayList<Slide> HorizontalSlides = new ArrayList<>();
    ArrayList<Slide> VerticalSlides = new ArrayList<>();//contains a slide with a pair of Vertical Photos
    
    ArrayList<Slide> FinalSlideshow = new ArrayList<>();

    
    public void Setup() throws FileNotFoundException, IOException{
        BufferedReader scan = new BufferedReader(new FileReader("a_example.txt"));
        String firstLine = scan.readLine();
        long totalPhotos = Integer.parseInt(firstLine);

        for (int i=0; i<totalPhotos;i++){
            String photoLineInfo = scan.readLine();
            String photoLineInfoArray[] = photoLineInfo.split(" ");
            
            char picType = photoLineInfoArray[0].charAt(0);
            int totalTags = Integer.valueOf(photoLineInfoArray[1]);
            ArrayList<String> tagArray= new ArrayList<String>();
                
            for(int j=0;j<totalTags;j++){
                tagArray.add(photoLineInfoArray[j+2]);
            }
                
            Photo p = new Photo(picType, totalTags, tagArray,i);
                
            if (p.picType=='H'){
                Horizontal.add(p);
            }
            else {
                Vertical.add(p);
            }
        }
    }
    
    
    public int check(Slide s1, Slide s2){
        int x = compareSimilarTag(s1,s2);
        int y = compareUniqueTagS1(s1,s2);
        int z = compareUniqueTagS2(s1,s2);
        return Math.min(Math.min(x,y), z);
    }

    public int compareSimilarTagPhoto(Photo p1, Photo p2){
        int count=0;
        for(int i=0;i<p1.tags.size();i++){
            for(int j=0;j<p2.tags.size();j++){
                if (p1.tags.get(i).equals(p2.tags.get(j)))
                    count++;
            }
        }
        return count;
    }
    
    public int compareSimilarTag(Slide s1, Slide s2){
        int count=0;
        for(int i=0;i<s1.totalTag.size();i++){
            for(int j=0;j<s2.totalTag.size();j++){
                if (s1.totalTag.get(i).equals(s2.totalTag.get(j)))
                    count++;
            }
        }
        return count;
    }
    public int compareUniqueTagS1(Slide s1, Slide s2){
        int count=0;
        for(int i=0;i<s2.totalTag.size();i++){
            if (!s1.totalTag.contains(s2.totalTag.get(i)))
                    count++;
        }
        return count;
    }
    public int compareUniqueTagS2(Slide s1, Slide s2){
        int count=0;
        for(int i=0;i<s1.totalTag.size();i++){
            if (!s2.totalTag.contains(s1.totalTag.get(i)))
                    count++;
        }
        return count;
    }
    
    public void slideSort(){
        FinalSlideshow.add(HorizontalSlides.get(0));
        HorizontalSlides.remove(0);
        
        Slide lastSlide=null;
        int lastIndex;
        
        while(HorizontalSlides.size()!=0||VerticalSlides.size()!=0){
            if(HorizontalSlides.size()==0){
                lastSlide = FinalSlideshow.get(FinalSlideshow.size()-1);
                lastIndex = BestVSlide(lastSlide);
                FinalSlideshow.add(VerticalSlides.get(lastIndex));
                VerticalSlides.remove(lastIndex);
            }
            else{
                lastSlide = FinalSlideshow.get(FinalSlideshow.size()-1);
                lastIndex = BestHSlide(lastSlide);
                FinalSlideshow.add(HorizontalSlides.get(lastIndex));
                HorizontalSlides.remove(lastIndex);
            }
        }
    }
    
    public Slide photoToSlide(Photo p){
        Slide s = new Slide(p);
        s.createTagList();
        return s;
    }
    
    public int BestHSlide(Slide s){
        int indexOfBestHSlide=0;
        //Slide bestSlide;
        //to start things up
        s = FinalSlideshow.get(FinalSlideshow.size()-1);
        int score = 0;
        
        for(int i=0; i<HorizontalSlides.size();i++){
            if (score<check(s,HorizontalSlides.get(i))){
                score = check(s,HorizontalSlides.get(i));
                indexOfBestHSlide=i;
            }
        }
        return indexOfBestHSlide;
    }
    public int BestVSlide(Slide s){
        int indexOfBestVSlide=0;
        //Slide bestSlide;
        //to start things up
        s = FinalSlideshow.get(FinalSlideshow.size()-1);
        int score = 0;
        
        for(int i=0; i<HorizontalSlides.size();i++){
            if (score<check(s,VerticalSlides.get(i))){
                score = check(s,VerticalSlides.get(i));
                indexOfBestVSlide=i;
            }
        }
        return indexOfBestVSlide;
    }
    
    
    public void horizontalSort(){
        for(int i=0;i<Horizontal.size();i++){
            HorizontalSlides.add(photoToSlide(Horizontal.get(i)));
        }
    }
    public void verticalSort(){
        Slide tempslide = null;
        
        int pointsleast = 900000;
        
        for(int i = 1; i<Vertical.size();i++){
           
            int points = compareSimilarTagPhoto(Vertical.get(0),Vertical.get(i));
            
            if(points == 0){
                Slide s = new Slide(Vertical.get(0),Vertical.get(i));
                Vertical.remove(i);
                Vertical.remove(0);
                VerticalSlides.add(s);
            } 
            else if(points < pointsleast){
                pointsleast = points;
                tempslide = new Slide(Vertical.get(0),Vertical.get(i));
                Vertical.remove(i);
                Vertical.remove(0);
                VerticalSlides.add(tempslide);
            }
        }
    }
    
    
    //PrintStuff (for Debugging)
    public void printVericalSlidesWithTags(){
        for(int i=0;i<VerticalSlides.size();i++){
            System.out.print(VerticalSlides.get(i).photo1.index + " ");
            for(int j=0;j<VerticalSlides.get(i).photo1.totalTags-1;j++){
                System.out.print(VerticalSlides.get(i).photo1.tags.get(j) + " ");
            }
            System.out.println();
            
            System.out.print(VerticalSlides.get(i).photo2.index + " ");
            for(int j=0;j<VerticalSlides.get(i).photo2.totalTags-1;j++){
                System.out.print(VerticalSlides.get(i).photo2.tags.get(j) + " ");
            }
            System.out.println();
            System.out.println();
        }
    }
    public void printVericalPhotosWithTags(){
        for(int i=0;i<Vertical.size();i++){
            System.out.print(Vertical.get(i).index + " ");
            for(int j=0;j<Vertical.get(i).totalTags-1;j++){
                System.out.print(Vertical.get(i).tags.get(j) + " ");
            }
            System.out.println();
            
        }
    }
    public void printHorizontalSlidesWithTags(){
        for(int i=0;i<HorizontalSlides.size();i++){
            System.out.print(HorizontalSlides.get(i).photo1.index + " ");
            for(int j=0;j<HorizontalSlides.get(i).photo1.totalTags-1;j++){
                System.out.print(HorizontalSlides.get(i).photo1.tags.get(j) + " ");
            }
            System.out.println();
            
        }
    }
    public void printFinalSlidesWithTags(){
        for(int i=0;i<FinalSlideshow.size();i++){
            System.out.print(FinalSlideshow.get(i).photo1.index + " ");
            for(int j=0;j<FinalSlideshow.get(i).photo1.totalTags-1;j++){
                System.out.print(FinalSlideshow.get(i).photo1.tags.get(j) + " ");
            }
            System.out.println();
        }
    }
    
    
    
    public void SubmitFile() throws FileNotFoundException, IOException{
        BufferedWriter scan = new BufferedWriter(new FileWriter("Submittion File.txt"));
        
        scan.write(FinalSlideshow.size()+"");
        scan.newLine();
        
        for(int i=0; i<FinalSlideshow.size();i++){
            if(FinalSlideshow.get(i).photo2==null){
                scan.write(FinalSlideshow.get(i).photo1.index + " ");
                scan.newLine();
            }
            else{
                scan.write(FinalSlideshow.get(i).photo1.index + " ");
                scan.write(FinalSlideshow.get(i).photo2.index +"");
                scan.newLine();
            }
        }
        scan.close();
    }
}
