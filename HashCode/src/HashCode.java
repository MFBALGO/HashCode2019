import java.io.FileNotFoundException;
import java.io.IOException;

public class HashCode {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        SlideMaker SM = new SlideMaker();
        SM.Setup();
        //SM.setupSort();
        SM.horizontalSort();
        SM.verticalSort();
        SM.slideSort();
        SM.SubmitFile();
    }
}
