package se751.team13.quicksort.paratask;//####[1]####
//####[1]####
import se751.team13.quicksort.Sorter;//####[3]####
import java.util.ArrayList;//####[5]####
import java.util.List;//####[6]####
import paratask.runtime.TaskIDGroup;//####[8]####
//####[8]####
//-- ParaTask related imports//####[8]####
import paratask.runtime.*;//####[8]####
import java.util.concurrent.ExecutionException;//####[8]####
import java.util.concurrent.locks.*;//####[8]####
import java.lang.reflect.*;//####[8]####
import javax.swing.SwingUtilities;//####[8]####
//####[8]####
public class ParataskQuickSorter<T extends Comparable<? super T>> implements Sorter<T> {//####[11]####
//####[11]####
    /*  ParaTask helper method to access private/protected slots *///####[11]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[11]####
        if (m.getParameterTypes().length == 0)//####[11]####
            m.invoke(instance);//####[11]####
        else if ((m.getParameterTypes().length == 1))//####[11]####
            m.invoke(instance, arg);//####[11]####
        else //####[11]####
            m.invoke(instance, arg, interResult);//####[11]####
    }//####[11]####
//####[13]####
    private Integer nThreads;//####[13]####
//####[14]####
    private Integer granularity;//####[14]####
//####[15]####
    private List<T> list;//####[15]####
//####[17]####
    public ParataskQuickSorter() {//####[17]####
        this(200);//####[18]####
    }//####[19]####
//####[21]####
    public ParataskQuickSorter(int granularity) {//####[21]####
        this.granularity = granularity;//####[22]####
    }//####[23]####
//####[25]####
    public List<T> sort(List<T> unsorted) {//####[25]####
        list = new ArrayList<T>(unsorted);//####[26]####
        TaskID wholeList = run(0, list.size() - 1);//####[27]####
        TaskIDGroup g = new TaskIDGroup(1);//####[28]####
        g.add(wholeList);//####[29]####
        try {//####[30]####
            g.waitTillFinished();//####[31]####
        } catch (InterruptedException e) {//####[32]####
            System.out.println("Interrupted");//####[33]####
        } catch (ExecutionException e) {//####[34]####
            System.out.println("Execution");//####[35]####
        }//####[37]####
        return list;//####[38]####
    }//####[39]####
//####[41]####
    private Method __pt__run_int_int_method = null;//####[41]####
    private Lock __pt__run_int_int_lock = new ReentrantLock();//####[41]####
    public TaskID<Void> run(int left, int right)  {//####[41]####
//####[41]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[41]####
        return run(left, right, new TaskInfo());//####[41]####
    }//####[41]####
    public TaskID<Void> run(int left, int right, TaskInfo taskinfo)  {//####[41]####
        if (__pt__run_int_int_method == null) {//####[41]####
            try {//####[41]####
                __pt__run_int_int_lock.lock();//####[41]####
                if (__pt__run_int_int_method == null) //####[41]####
                    __pt__run_int_int_method = ParaTaskHelper.getDeclaredMethod(getClass(), "__pt__run", new Class[] {int.class, int.class});//####[41]####
            } catch (Exception e) {//####[41]####
                e.printStackTrace();//####[41]####
            } finally {//####[41]####
                __pt__run_int_int_lock.unlock();//####[41]####
            }//####[41]####
        }//####[41]####
//####[41]####
        Object[] args = new Object[] {left, right};//####[41]####
        taskinfo.setTaskArguments(args);//####[41]####
        taskinfo.setMethod(__pt__run_int_int_method);//####[41]####
        taskinfo.setInstance(this);//####[41]####
//####[41]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[41]####
    }//####[41]####
    public void __pt__run(int left, int right) {//####[41]####
        qssort(left, right);//####[42]####
    }//####[43]####
//####[43]####
//####[45]####
    private void qssort(int left, int right) {//####[45]####
        if (right - left <= granularity) //####[46]####
        {//####[46]####
            insertion(left, right);//####[47]####
        } else if (left < right) //####[48]####
        {//####[48]####
            int pivotIndex = left + (right - left) / 2;//####[49]####
            int pivotNewIndex = partition(left, right, pivotIndex);//####[50]####
            TaskIDGroup g = new TaskIDGroup(1);//####[52]####
            TaskID leftSubList = run(left, pivotNewIndex - 1);//####[54]####
            g.add(leftSubList);//####[55]####
            qssort(pivotNewIndex + 1, right);//####[61]####
            try {//####[62]####
                g.waitTillFinished();//####[63]####
            } catch (InterruptedException e) {//####[64]####
            } catch (ExecutionException e) {//####[66]####
            }//####[68]####
        }//####[69]####
    }//####[70]####
//####[72]####
    private int partition(int leftIndex, int rightIndex, int pivotIndex) {//####[73]####
        T pivot = list.get(pivotIndex);//####[75]####
        swap(pivotIndex, rightIndex);//####[76]####
        int storeIndex = leftIndex;//####[77]####
        for (int i = leftIndex; i < rightIndex; i++) //####[79]####
        {//####[79]####
            T t = list.get(i);//####[80]####
            if (t.compareTo(pivot) <= 0) //####[82]####
            {//####[82]####
                swap(i, storeIndex);//####[83]####
                storeIndex++;//####[84]####
            }//####[85]####
        }//####[86]####
        swap(storeIndex, rightIndex);//####[88]####
        return storeIndex;//####[89]####
    }//####[90]####
//####[92]####
    private void swap(int leftIndex, int rightIndex) {//####[92]####
        T t = list.get(leftIndex);//####[93]####
        list.set(leftIndex, list.get(rightIndex));//####[94]####
        list.set(rightIndex, t);//####[95]####
    }//####[96]####
//####[99]####
    private void insertion(int offset, int limit) {//####[99]####
        for (int i = offset; i < limit + 1; i++) //####[100]####
        {//####[100]####
            T valueToInsert = list.get(i);//####[101]####
            int hole = i;//####[102]####
            while (hole > 0 && valueToInsert.compareTo(list.get(hole - 1)) < 0) //####[104]####
            {//####[105]####
                list.set(hole, list.get(hole - 1));//####[106]####
                hole--;//####[107]####
            }//####[108]####
            list.set(hole, valueToInsert);//####[110]####
        }//####[111]####
    }//####[112]####
}//####[112]####
