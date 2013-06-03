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
//####[21]####
    /**
	 * Default constructor, sets the number of threads used to the number of
	 * cores available.
	 *///####[21]####
    public ParataskQuickSorter() {//####[21]####
        this(Runtime.getRuntime().availableProcessors());//####[22]####
    }//####[23]####
//####[31]####
    /**
	 * Constructor that takes the number of threads to use. Sets the granularity
	 * to default 250
	 * 
	 * @param numThreads
	 *///####[31]####
    public ParataskQuickSorter(int numThreads) {//####[31]####
        this(numThreads, 250);//####[32]####
    }//####[33]####
//####[43]####
    /**
	 * Full Constructor that sets both the number of threads to use and the
	 * granularity level to go down to before performing sequential insertion
	 * sort.
	 * 
	 * @param numThreads
	 * @param granularity
	 *///####[43]####
    public ParataskQuickSorter(int numThreads, int granularity) {//####[43]####
        ParaTask.setThreadPoolSize(numThreads);//####[44]####
        this.granularity = granularity;//####[45]####
    }//####[46]####
//####[55]####
    /**
	 * Method to sort an unsorted list using inplace quicksort Starts off the
	 * initial task to start parallel sections by creating an initial ParaTask
	 * Task and TaskGroup
	 * 
	 * @param unsorted
	 *///####[55]####
    public List<T> sort(List<T> unsorted) {//####[55]####
        list = new ArrayList<T>(unsorted);//####[56]####
        TaskID wholeList = run(0, list.size() - 1);//####[57]####
        TaskIDGroup g = new TaskIDGroup(1);//####[58]####
        g.add(wholeList);//####[59]####
        try {//####[60]####
            g.waitTillFinished();//####[61]####
        } catch (InterruptedException e) {//####[62]####
            System.out.println("Interrupted");//####[63]####
        } catch (ExecutionException e) {//####[64]####
            System.out.println("Execution");//####[65]####
        }//####[67]####
        return list;//####[68]####
    }//####[69]####
//####[79]####
    private Method __pt__run_int_int_method = null;//####[79]####
    private Lock __pt__run_int_int_lock = new ReentrantLock();//####[79]####
    /**
	 * The Paratask Task simply calls the qssort method
	 * this is done to allow the qssort method to be called
	 * as both a task and as a method on an existing task. 
	 * 
	 * @param left
	 * @param right
	 *///####[79]####
    public TaskID<Void> run(int left, int right)  {//####[79]####
//####[79]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[79]####
        return run(left, right, new TaskInfo());//####[79]####
    }//####[79]####
    /**
	 * The Paratask Task simply calls the qssort method
	 * this is done to allow the qssort method to be called
	 * as both a task and as a method on an existing task. 
	 * 
	 * @param left
	 * @param right
	 *///####[79]####
    public TaskID<Void> run(int left, int right, TaskInfo taskinfo)  {//####[79]####
        if (__pt__run_int_int_method == null) {//####[79]####
            try {//####[79]####
                __pt__run_int_int_lock.lock();//####[79]####
                if (__pt__run_int_int_method == null) //####[79]####
                    __pt__run_int_int_method = ParaTaskHelper.getDeclaredMethod(getClass(), "__pt__run", new Class[] {int.class, int.class});//####[79]####
            } catch (Exception e) {//####[79]####
                e.printStackTrace();//####[79]####
            } finally {//####[79]####
                __pt__run_int_int_lock.unlock();//####[79]####
            }//####[79]####
        }//####[79]####
//####[79]####
        Object[] args = new Object[] {left, right};//####[79]####
        taskinfo.setTaskArguments(args);//####[79]####
        taskinfo.setMethod(__pt__run_int_int_method);//####[79]####
        taskinfo.setInstance(this);//####[79]####
//####[79]####
        return TaskpoolFactory.getTaskpool().enqueue(taskinfo);//####[79]####
    }//####[79]####
    /**
	 * The Paratask Task simply calls the qssort method
	 * this is done to allow the qssort method to be called
	 * as both a task and as a method on an existing task. 
	 * 
	 * @param left
	 * @param right
	 *///####[79]####
    public void __pt__run(int left, int right) {//####[79]####
        qssort(left, right);//####[80]####
    }//####[81]####
//####[81]####
//####[92]####
    /**
	 * Runs inplace quicksort implementation of the section of
	 * the list between the given left and right indices. 
	 * When calling the recursive part of the quicksort algorithm, 
	 * it keeps the right section of the list as its own, and creates
	 * a task for the left part of the list
	 * @param left
	 * @param right
	 *///####[92]####
    private void qssort(int left, int right) {//####[92]####
        if (right - left <= granularity) //####[93]####
        {//####[93]####
            insertion(left, right);//####[94]####
        } else if (left < right) //####[95]####
        {//####[95]####
            int pivotIndex = left + (right - left) / 2;//####[96]####
            int pivotNewIndex = partition(left, right, pivotIndex);//####[97]####
            TaskIDGroup g = new TaskIDGroup(1);//####[99]####
            TaskID leftSubList = run(left, pivotNewIndex - 1);//####[101]####
            g.add(leftSubList);//####[102]####
            qssort(pivotNewIndex + 1, right);//####[104]####
            try {//####[105]####
                g.waitTillFinished();//####[106]####
            } catch (InterruptedException e) {//####[107]####
            } catch (ExecutionException e) {//####[109]####
            }//####[111]####
        }//####[112]####
    }//####[113]####
//####[127]####
    /**
	 * Partition the array around a specified elements Swapping until all
	 * elements greater than the pivot are one side of the pivot and all
	 * elements smaller than the pivot are on the other side. Takes in a
	 * left and right index to indicate what section of the array it is
	 * working on.
	 * 
	 * @param leftIndex
	 * @param rightIndex
	 * @param pivotIndex
	 * @return
	 *///####[127]####
    private int partition(int leftIndex, int rightIndex, int pivotIndex) {//####[128]####
        T pivot = list.get(pivotIndex);//####[130]####
        swap(pivotIndex, rightIndex);//####[131]####
        int storeIndex = leftIndex;//####[132]####
        for (int i = leftIndex; i < rightIndex; i++) //####[134]####
        {//####[134]####
            T t = list.get(i);//####[135]####
            if (t.compareTo(pivot) <= 0) //####[137]####
            {//####[137]####
                swap(i, storeIndex);//####[138]####
                storeIndex++;//####[139]####
            }//####[140]####
        }//####[141]####
        swap(storeIndex, rightIndex);//####[143]####
        return storeIndex;//####[144]####
    }//####[145]####
//####[154]####
    /**
	 * Swaps the elements at two positions
	 * 
	 * @param array
	 * @param leftIndex
	 * @param rightIndex
	 *///####[154]####
    private void swap(int leftIndex, int rightIndex) {//####[154]####
        T t = list.get(leftIndex);//####[155]####
        list.set(leftIndex, list.get(rightIndex));//####[156]####
        list.set(rightIndex, t);//####[157]####
    }//####[158]####
//####[167]####
    /**
	 * Insertion sort a section
	 * 
	 * @param array
	 * @param offset
	 * @param limit
	 *///####[167]####
    private void insertion(int offset, int limit) {//####[167]####
        for (int i = offset; i < limit + 1; i++) //####[168]####
        {//####[168]####
            T valueToInsert = list.get(i);//####[169]####
            int hole = i;//####[170]####
            while (hole > 0 && valueToInsert.compareTo(list.get(hole - 1)) < 0) //####[172]####
            {//####[173]####
                list.set(hole, list.get(hole - 1));//####[174]####
                hole--;//####[175]####
            }//####[176]####
            list.set(hole, valueToInsert);//####[178]####
        }//####[179]####
    }//####[180]####
}//####[180]####
