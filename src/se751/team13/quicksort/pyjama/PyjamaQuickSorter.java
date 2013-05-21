package se751.team13.quicksort.pyjama;//####[1]####
//####[1]####
import jump.parser.ast.visitor.DummyClassToDetermineVariableType;//####[3]####
import paratask.runtime.*;//####[4]####
import java_omp.Pyjama;//####[5]####
import java_omp.JuMP_PackageOnly;//####[6]####
import java_omp.UniqueThreadIdGeneratorForOpenMP;//####[7]####
import pi.ParIteratorFactory;//####[8]####
import pi.ParIterator;//####[9]####
import pi.reductions.Reducible;//####[10]####
import pi.reductions.Reduction;//####[11]####
import java.util.concurrent.atomic.*;//####[12]####
import java.util.concurrent.*;//####[13]####
import java.awt.EventQueue;//####[14]####
import java.util.concurrent.ExecutorService;//####[15]####
import java.util.concurrent.Executors;//####[16]####
import java.util.concurrent.TimeUnit;//####[17]####
import javax.swing.SwingUtilities;//####[18]####
import jump.parser.ast.visitor.DummyClassToDetermineVariableType;//####[19]####
import se751.team13.quicksort.Sorter;//####[20]####
import pi.reductions.Reducible;//####[22]####
import java.util.*;//####[23]####
//####[23]####
//-- ParaTask related imports//####[23]####
import paratask.runtime.*;//####[23]####
import java.util.concurrent.ExecutionException;//####[23]####
import java.util.concurrent.locks.*;//####[23]####
import java.lang.reflect.*;//####[23]####
import javax.swing.SwingUtilities;//####[23]####
//####[23]####
public class PyjamaQuickSorter<T extends Comparable<? super T>> implements Sorter<T> {//####[25]####
//####[25]####
    /*  ParaTask helper method to access private/protected slots *///####[25]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[25]####
        if (m.getParameterTypes().length == 0)//####[25]####
            m.invoke(instance);//####[25]####
        else if ((m.getParameterTypes().length == 1))//####[25]####
            m.invoke(instance, arg);//####[25]####
        else //####[25]####
            m.invoke(instance, arg, interResult);//####[25]####
    }//####[25]####
//####[26]####
    public static void main(String[] args) {//####[26]####
        Pyjama.init();//####[27]####
        {//####[28]####
            System.out.println("Hello world from sequential code");//####[29]####
            if (Pyjama.insideParallelRegion()) //####[31]####
            {//####[31]####
                {//####[33]####
                    for (int i = 0; i < 10; i = i + 1) //####[34]####
                    {//####[35]####
                        System.out.println("Hello world from parallel code");//####[36]####
                        System.out.println("Sort");//####[37]####
                    }//####[38]####
                }//####[39]####
            } else {//####[40]####
                JuMP_PackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[42]####
                _omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 _omp__parallelRegionVarHolderInstance_0 = new _omp__parallelRegionVarHolderClass_PyjamaQuickSorter0();//####[45]####
                _omp__parallelRegionVarHolderInstance_0.args = args;//####[46]####
                JuMP_PackageOnly.setMasterThread(Thread.currentThread());//####[49]####
                TaskID _omp__parallelRegionTaskID_0 = _ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[50]####
                __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[51]####
                try {//####[52]####
                    _omp__parallelRegionTaskID_0.waitTillFinished();//####[52]####
                } catch (Exception __pt__ex) {//####[52]####
                    __pt__ex.printStackTrace();//####[52]####
                }//####[52]####
                JuMP_PackageOnly.setMasterThread(null);//####[54]####
                _holderForPIFirst.set(true);//####[55]####
                args = _omp__parallelRegionVarHolderInstance_0.args;//####[57]####
                JuMP_PackageOnly.setThreadCountCurrentParallelRegion(1);//####[58]####
            }//####[59]####
        }//####[62]####
    }//####[63]####
//####[64]####
    private static ArrayList<ParIterator<?>> _omp_piVarContainer = new ArrayList<ParIterator<?>>();//####[64]####
//####[65]####
    private static AtomicBoolean _holderForPIFirst;//####[65]####
//####[66]####
    private static AtomicBoolean _imFirst_2 = new AtomicBoolean(true);//####[66]####
//####[67]####
    private static AtomicInteger _imFinishedCounter_2 = new AtomicInteger(0);//####[67]####
//####[68]####
    private static CountDownLatch _waitBarrier_2 = new CountDownLatch(1);//####[68]####
//####[69]####
    private static CountDownLatch _waitBarrierAfter_2 = new CountDownLatch(1);//####[69]####
//####[70]####
    private static ParIterator<Integer> _pi_2 = null;//####[70]####
//####[71]####
    private static Integer _lastElement_2 = null;//####[71]####
//####[72]####
    private static _ompWorkSharedUserCode_PyjamaQuickSorter2_variables _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance = null;//####[72]####
//####[73]####
    private static void _ompWorkSharedUserCode_PyjamaQuickSorter2(_ompWorkSharedUserCode_PyjamaQuickSorter2_variables __omp_vars) {//####[73]####
        String[] args = __omp_vars.args;//####[75]####
        Integer i;//####[76]####
        while (_pi_2.hasNext()) //####[77]####
        {//####[77]####
            i = _pi_2.next();//####[78]####
            {//####[80]####
                System.out.println("Hello world from parallel code");//####[81]####
                System.out.println("Sort");//####[82]####
            }//####[83]####
        }//####[84]####
        __omp_vars.args = args;//####[86]####
    }//####[87]####
//####[91]####
    private static Method __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method = null;//####[91]####
    private static Lock __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock = new ReentrantLock();//####[91]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars)  {//####[91]####
//####[91]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[91]####
        return _ompParallelRegion_0(__omp_vars, new TaskInfo());//####[91]####
    }//####[91]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars, TaskInfo taskinfo)  {//####[91]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method == null) {//####[91]####
            try {//####[91]####
                __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock.lock();//####[91]####
                if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method == null) //####[91]####
                    __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_0", new Class[] {_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0.class});//####[91]####
            } catch (Exception e) {//####[91]####
                e.printStackTrace();//####[91]####
            } finally {//####[91]####
                __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock.unlock();//####[91]####
            }//####[91]####
        }//####[91]####
//####[91]####
        Object[] args = new Object[] {__omp_vars};//####[91]####
        taskinfo.setTaskArguments(args);//####[91]####
        taskinfo.setMethod(__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method);//####[91]####
//####[91]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[91]####
    }//####[91]####
    public static void __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars) {//####[91]####
        String[] args = __omp_vars.args;//####[93]####
        {//####[94]####
            if (Pyjama.insideParallelRegion()) //####[95]####
            {//####[95]####
                boolean _omp_imFirst = _imFirst_2.getAndSet(false);//####[97]####
                _holderForPIFirst = _imFirst_2;//####[98]####
                if (_omp_imFirst) //####[99]####
                {//####[99]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance = new _ompWorkSharedUserCode_PyjamaQuickSorter2_variables();//####[100]####
                    int __omp_size_ = 0;//####[101]####
                    for (int i = 0; i < 10; i = i + 1) //####[103]####
                    {//####[103]####
                        _lastElement_2 = i;//####[104]####
                        __omp_size_++;//####[105]####
                    }//####[106]####
                    _pi_2 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[107]####
                    _omp_piVarContainer.add(_pi_2);//####[108]####
                    _pi_2.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[109]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.args = args;//####[110]####
                    _waitBarrier_2.countDown();//####[111]####
                } else {//####[112]####
                    try {//####[113]####
                        _waitBarrier_2.await();//####[113]####
                    } catch (InterruptedException __omp__ie) {//####[113]####
                        __omp__ie.printStackTrace();//####[113]####
                    }//####[113]####
                }//####[114]####
                _ompWorkSharedUserCode_PyjamaQuickSorter2(_ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance);//####[115]####
                if (_imFinishedCounter_2.incrementAndGet() == JuMP_PackageOnly.getThreadCountCurrentParallelRegion()) //####[116]####
                {//####[116]####
                    _waitBarrierAfter_2.countDown();//####[117]####
                } else {//####[118]####
                    try {//####[119]####
                        _waitBarrierAfter_2.await();//####[120]####
                    } catch (InterruptedException __omp__ie) {//####[121]####
                        __omp__ie.printStackTrace();//####[122]####
                    }//####[123]####
                }//####[124]####
            } else {//####[126]####
                for (int i = 0; i < 10; i = i + 1) //####[128]####
                {//####[129]####
                    System.out.println("Hello world from parallel code");//####[130]####
                    System.out.println("Sort");//####[131]####
                }//####[132]####
            }//####[133]####
        }//####[135]####
        __omp_vars.args = args;//####[137]####
    }//####[138]####
//####[138]####
//####[139]####
    public List<T> sort(List<T> unsorted) {//####[139]####
        {//####[139]####
            return unsorted;//####[140]####
        }//####[141]####
    }//####[142]####
}//####[142]####
