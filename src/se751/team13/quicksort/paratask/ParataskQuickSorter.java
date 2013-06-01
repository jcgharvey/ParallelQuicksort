package se751.team13.quicksort.paratask;//####[1]####
//####[1]####
import se751.team13.quicksort.Sorter;//####[3]####
import java.util.ArrayList;//####[5]####
import java.util.List;//####[6]####
import paratask.runtime.ParaTask;//####[8]####
import paratask.runtime.TaskIDGroup;//####[9]####
//####[9]####
//-- ParaTask related imports//####[9]####
import paratask.runtime.*;//####[9]####
import java.util.concurrent.ExecutionException;//####[9]####
import java.util.concurrent.locks.*;//####[9]####
import java.lang.reflect.*;//####[9]####
import javax.swing.SwingUtilities;//####[9]####
//####[9]####
public class ParataskQuickSorter<T extends Comparable<? super T>> implements Sorter<T> {//####[12]####
//####[12]####
    /*  ParaTask helper method to access private/protected slots *///####[12]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[12]####
        if (m.getParameterTypes().length == 0)//####[12]####
            m.invoke(instance);//####[12]####
        else if ((m.getParameterTypes().length == 1))//####[12]####
            m.invoke(instance, arg);//####[12]####
        else //####[12]####
            m.invoke(instance, arg, interResult);//####[12]####
    }//####[12]####
//####[14]####
    private Integer granularity;//####[14]####
//####[15]####
    private List<T> list;//####[15]####
//####[17]####
    public ParataskQuickSorter() {//####[17]####
        this(Runtime.getRuntime().availableProcessors());//####[18]####
    }//####[19]####
//####[21]####
    public ParataskQuickSorter(int numThreads) {//####[21]####
        this(numThreads, 250);//####[22]####
    }//####[23]####
//####[25]####
    public ParataskQuickSorter(int numThreads, int granularity) {//####[25]####
        ParaTask.setThreadPoolSize(numThreads);//####[26]####
        this.granularity = granularity;//####[27]####
    }//####[28]####
//####[30]####
    public List<T> sort(List<T> unsorted) {//####[30]####
        list = new ArrayList<T>(unsorted);//####[31]####
        TaskID wholeList = run(0, list.size() - 1);//####[32]####
        TaskIDGroup g = new TaskIDGroup(1);//####[33]####
        g.add(wholeList);//####[34]####
        try {//####[35]####
            g.waitTillFinished();//####[36]####
        } catch (InterruptedException e) {//####[37]####
            System.out.println("Interrupted");//####[38]####
        } catch (ExecutionException e) {//####[39]####
            System.out.println("Execution");//####[40]####
        }//####[42]####
        return list;//####[43]####
    }//####[44]####
//####[46]####
    private Method __pt__run_int_int_method = null;//####[46]####
    private Lock __pt__run_int_int_lock = new ReentrantLock();//####[46]####
    public TaskID<Void> run(int left, int right)  {//####[46]####
//####[46]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[46]####
        return run(left, right, new TaskInfo());//####[46]####
    }//####[46]####
    public TaskID<Void> run(int left, int right, TaskInfo taskinfo)  {//####[46]####
        if (__pt__run_int_int_method == null) {//####[46]####
            try {//####[46]####
                __pt__run_int_int_lock.lock();//####[46]####
                if (__pt__run_int_int_method == null) //####[46]####
                    __pt__run_int_int_method = ParaTaskHelper.getDeclaredMethod(getClass(), "__pt__run", new Class[] {int.class, int.class});//####[46]####
            } catch (Exception e) {//####[46]####
                e.printStackTrace();//####[46]####
            } finally {//####[46]####
                __pt__run_int_int_lock.unlock();//####[46]####
            }//####[46]####
        }//####[46]####
//####[46]####
        Object[] args = new Object[] {left, right};//####[46]####
        taskinfo.setTaskArguments(args);//####[46]####
        taskinfo.setMethod(__pt__run_int_int_method);//####[46]####
        taskinfo.setInstance(this);//####[46]####
//####[46]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[46]####
    }//####[46]####
    public void __pt__run(int left, int right) {//####[46]####
        qssort(left, right);//####[47]####
    }//####[48]####
//####[48]####
//####[50]####
    private void qssort(int left, int right) {//####[50]####
        if (right - left <= granularity) //####[51]####
        {//####[51]####
            insertion(left, right);//####[52]####
        } else if (left < right) //####[53]####
        {//####[53]####
            int pivotIndex = left + (right - left) / 2;//####[54]####
            int pivotNewIndex = partition(left, right, pivotIndex);//####[55]####
            TaskIDGroup g = new TaskIDGroup(1);//####[57]####
            TaskID leftSubList = run(left, pivotNewIndex - 1);//####[59]####
            g.add(leftSubList);//####[60]####
            qssort(pivotNewIndex + 1, right);//####[66]####
            try {//####[67]####
                g.waitTillFinished();//####[68]####
            } catch (InterruptedException e) {//####[69]####
            } catch (ExecutionException e) {//####[71]####
            }//####[73]####
        }//####[74]####
    }//####[75]####
//####[77]####
    private int partition(int leftIndex, int rightIndex, int pivotIndex) {//####[78]####
        T pivot = list.get(pivotIndex);//####[80]####
        swap(pivotIndex, rightIndex);//####[81]####
        int storeIndex = leftIndex;//####[82]####
        for (int i = leftIndex; i < rightIndex; i++) //####[84]####
        {//####[84]####
            T t = list.get(i);//####[85]####
            if (t.compareTo(pivot) <= 0) //####[87]####
            {//####[87]####
                swap(i, storeIndex);//####[88]####
                storeIndex++;//####[89]####
            }//####[90]####
        }//####[91]####
        swap(storeIndex, rightIndex);//####[93]####
        return storeIndex;//####[94]####
    }//####[95]####
//####[97]####
    private void swap(int leftIndex, int rightIndex) {//####[97]####
        T t = list.get(leftIndex);//####[98]####
        list.set(leftIndex, list.get(rightIndex));//####[99]####
        list.set(rightIndex, t);//####[100]####
    }//####[101]####
//####[104]####
    private void insertion(int offset, int limit) {//####[104]####
        for (int i = offset; i < limit + 1; i++) //####[105]####
        {//####[105]####
            T valueToInsert = list.get(i);//####[106]####
            int hole = i;//####[107]####
            while (hole > 0 && valueToInsert.compareTo(list.get(hole - 1)) < 0) //####[109]####
            {//####[110]####
                list.set(hole, list.get(hole - 1));//####[111]####
                hole--;//####[112]####
            }//####[113]####
            list.set(hole, valueToInsert);//####[115]####
        }//####[116]####
    }//####[117]####
}//####[117]####
