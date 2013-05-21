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
    public ParataskQuickSorter(int granularity) {//####[17]####
        this.granularity = granularity;//####[18]####
    }//####[19]####
//####[22]####
    public List<T> sort(List<T> unsorted) {//####[22]####
        list = new ArrayList<T>(unsorted);//####[23]####
        TaskID wholeList = run(0, list.size() - 1);//####[24]####
        TaskIDGroup g = new TaskIDGroup(1);//####[25]####
        g.add(wholeList);//####[26]####
        try {//####[27]####
            g.waitTillFinished();//####[28]####
        } catch (InterruptedException e) {//####[29]####
            System.out.println("Interrupted");//####[30]####
        } catch (ExecutionException e) {//####[31]####
            System.out.println("Execution");//####[32]####
        }//####[34]####
        return list;//####[35]####
    }//####[36]####
//####[38]####
    private Method __pt__run_int_int_method = null;//####[38]####
    private Lock __pt__run_int_int_lock = new ReentrantLock();//####[38]####
    public TaskID<Void> run(int left, int right)  {//####[38]####
//####[38]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[38]####
        return run(left, right, new TaskInfo());//####[38]####
    }//####[38]####
    public TaskID<Void> run(int left, int right, TaskInfo taskinfo)  {//####[38]####
        if (__pt__run_int_int_method == null) {//####[38]####
            try {//####[38]####
                __pt__run_int_int_lock.lock();//####[38]####
                if (__pt__run_int_int_method == null) //####[38]####
                    __pt__run_int_int_method = ParaTaskHelper.getDeclaredMethod(getClass(), "__pt__run", new Class[] {int.class, int.class});//####[38]####
            } catch (Exception e) {//####[38]####
                e.printStackTrace();//####[38]####
            } finally {//####[38]####
                __pt__run_int_int_lock.unlock();//####[38]####
            }//####[38]####
        }//####[38]####
//####[38]####
        Object[] args = new Object[] {left, right};//####[38]####
        taskinfo.setTaskArguments(args);//####[38]####
        taskinfo.setMethod(__pt__run_int_int_method);//####[38]####
        taskinfo.setInstance(this);//####[38]####
//####[38]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[38]####
    }//####[38]####
    public void __pt__run(int left, int right) {//####[38]####
        qssort(left, right);//####[39]####
    }//####[40]####
//####[40]####
//####[42]####
    private void qssort(int left, int right) {//####[42]####
        if (right - left <= granularity) //####[43]####
        {//####[43]####
            insertion(left, right);//####[44]####
        } else if (left < right) //####[45]####
        {//####[45]####
            int pivotIndex = left + (right - left) / 2;//####[46]####
            int pivotNewIndex = partition(left, right, pivotIndex);//####[47]####
            TaskIDGroup g = new TaskIDGroup(1);//####[49]####
            TaskID leftSubList = run(left, pivotNewIndex - 1);//####[51]####
            g.add(leftSubList);//####[52]####
            qssort(pivotNewIndex + 1, right);//####[58]####
            try {//####[59]####
                g.waitTillFinished();//####[60]####
            } catch (InterruptedException e) {//####[61]####
            } catch (ExecutionException e) {//####[63]####
            }//####[65]####
        }//####[66]####
    }//####[67]####
//####[69]####
    private int partition(int leftIndex, int rightIndex, int pivotIndex) {//####[70]####
        T pivot = list.get(pivotIndex);//####[72]####
        swap(pivotIndex, rightIndex);//####[73]####
        int storeIndex = leftIndex;//####[74]####
        for (int i = leftIndex; i < rightIndex; i++) //####[76]####
        {//####[76]####
            T t = list.get(i);//####[77]####
            if (t.compareTo(pivot) <= 0) //####[79]####
            {//####[79]####
                swap(i, storeIndex);//####[80]####
                storeIndex++;//####[81]####
            }//####[82]####
        }//####[83]####
        swap(storeIndex, rightIndex);//####[85]####
        return storeIndex;//####[86]####
    }//####[87]####
//####[89]####
    private void swap(int leftIndex, int rightIndex) {//####[89]####
        T t = list.get(leftIndex);//####[90]####
        list.set(leftIndex, list.get(rightIndex));//####[91]####
        list.set(rightIndex, t);//####[92]####
    }//####[93]####
//####[96]####
    private void insertion(int offset, int limit) {//####[96]####
        for (int i = offset; i < limit + 1; i++) //####[97]####
        {//####[97]####
            T valueToInsert = list.get(i);//####[98]####
            int hole = i;//####[99]####
            while (hole > 0 && valueToInsert.compareTo(list.get(hole - 1)) < 0) //####[101]####
            {//####[102]####
                list.set(hole, list.get(hole - 1));//####[103]####
                hole--;//####[104]####
            }//####[105]####
            list.set(hole, valueToInsert);//####[107]####
        }//####[108]####
    }//####[109]####
}//####[109]####
