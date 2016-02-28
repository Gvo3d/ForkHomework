import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * Created by Gvozd on 28.02.2016.
 */
public class SorterClass {
    private ArrayList<Integer> data;
    private int cpus;

    public SorterClass(ArrayList<Integer> data) {
        this.data = new ArrayList<Integer>(data);
        cpus = Runtime.getRuntime().availableProcessors();
    }

    public List<Integer> calculate() {
        int resultSize = data.size();
        int processesDataQuantity = resultSize / cpus;
        ForkJoinPool pool = new ForkJoinPool();
        RecursiveActionTask task = new RecursiveActionTask(data, processesDataQuantity);
        ArrayList<Integer> result = pool.invoke(task);
        return result;
    }
}

class RecursiveActionTask extends RecursiveTask<ArrayList<Integer>> {
    private ArrayList<Integer> array;
    private int singleDataQuantity;

    public RecursiveActionTask(ArrayList<Integer> data, int processesDataQuantity) {
        this.array = data;
        this.singleDataQuantity = processesDataQuantity;
    }

    @Override
    protected ArrayList<Integer> compute() {
        if (array.size() <= singleDataQuantity) {
            ArrayList<Integer> newArr = new ArrayList<>();
            newArr.addAll(array);
            newArr.sort(new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    if (o1 < o2) return -1;
                    if (o1 > o2) return 1;
                    return 0;
                }
            });
            return newArr;
        } else {
            List<Integer> newArrayLeft = array.subList(0, singleDataQuantity);
            ArrayList<Integer> newArrayLeftA = new ArrayList<>();
            newArrayLeftA.addAll(newArrayLeft);
            RecursiveActionTask left = new RecursiveActionTask(newArrayLeftA, singleDataQuantity);
            left.fork();

            List<Integer> newArrayRight = array.subList(singleDataQuantity, array.size());
            ArrayList<Integer> newArrayRightA = new ArrayList<>();
            newArrayRightA.addAll(newArrayRight);
            RecursiveActionTask right = new RecursiveActionTask(newArrayRightA, singleDataQuantity);
            ArrayList<Integer> rightBranch = right.compute();
            ArrayList<Integer> leftBranch = left.join();
            return mergeSort(leftBranch, rightBranch);
        }
    }

    private ArrayList<Integer> mergeSort(ArrayList<Integer> left, ArrayList<Integer> right) {
        int leftPointer = 0;
        int rightPointer = 0;
        int leftSize= left.size();
        int rightSize= right.size();
        int fullResultSize = left.size()+right.size();
        ArrayList<Integer> sortResult= new ArrayList<>();

        for (int i=0; i<fullResultSize; i++){
            if ((leftPointer<leftSize)&&(left.get(leftPointer)!=null)&&(left.get(leftPointer)<right.get(rightPointer))){
                sortResult.add(left.get(leftPointer));
                leftPointer++;
            } else if ((rightPointer<rightSize)&&(right.get(rightPointer)!=null)){
                sortResult.add(right.get(rightPointer));
                rightPointer++;
            }
        }
        return sortResult;
    }
}