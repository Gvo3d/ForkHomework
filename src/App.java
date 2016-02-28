import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

/**
 * Created by Gvozd on 28.02.2016.
 */
public class App {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ArrayList<Integer> data = new ArrayList<>();
        generate(data, 100, 500);
        SorterClass sc = new SorterClass(data);
        List<Integer> resultData = sc.calculate();
        for (Integer intdata : resultData) {
            System.out.println(intdata);
        }
        System.out.println("Starting array has " + data.size() + " elements. Resulting array has " + resultData.size() + " elements.");
    }

    public static void generate(ArrayList target, int capacity, int maxValue) {
        Random random = new Random();
        for (int i = 0; i < capacity; i++) {
            int value = random.nextInt(maxValue);
            target.add(value);
        }
    }
}
