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
import pi.reductions.Reducible;//####[21]####
import java.util.*;//####[22]####
//####[22]####
//-- ParaTask related imports//####[22]####
import paratask.runtime.*;//####[22]####
import java.util.concurrent.ExecutionException;//####[22]####
import java.util.concurrent.locks.*;//####[22]####
import java.lang.reflect.*;//####[22]####
import javax.swing.SwingUtilities;//####[22]####
//####[22]####
public class PyjamaQuickSorter {//####[24]####
//####[24]####
    /*  ParaTask helper method to access private/protected slots *///####[24]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[24]####
        if (m.getParameterTypes().length == 0)//####[24]####
            m.invoke(instance);//####[24]####
        else if ((m.getParameterTypes().length == 1))//####[24]####
            m.invoke(instance, arg);//####[24]####
        else //####[24]####
            m.invoke(instance, arg, interResult);//####[24]####
    }//####[24]####
//####[25]####
    public static void main(String[] args) {//####[25]####
        Pyjama.init();//####[26]####
        {//####[27]####
            System.out.println("Hello world from sequential code");//####[28]####
            if (Pyjama.insideParallelRegion()) //####[30]####
            {//####[30]####
                {//####[32]####
                    for (int i = 0; i < 10; i = i + 1) //####[33]####
                    {//####[34]####
                        System.out.println("Hello world from parallel code");//####[35]####
                    }//####[36]####
                }//####[37]####
            } else {//####[38]####
                JuMP_PackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[40]####
                _omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 _omp__parallelRegionVarHolderInstance_0 = new _omp__parallelRegionVarHolderClass_PyjamaQuickSorter0();//####[43]####
                _omp__parallelRegionVarHolderInstance_0.args = args;//####[44]####
                JuMP_PackageOnly.setMasterThread(Thread.currentThread());//####[47]####
                TaskID _omp__parallelRegionTaskID_0 = _ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[48]####
                __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[49]####
                try {//####[50]####
                    _omp__parallelRegionTaskID_0.waitTillFinished();//####[50]####
                } catch (Exception __pt__ex) {//####[50]####
                    __pt__ex.printStackTrace();//####[50]####
                }//####[50]####
                JuMP_PackageOnly.setMasterThread(null);//####[52]####
                _holderForPIFirst.set(true);//####[53]####
                args = _omp__parallelRegionVarHolderInstance_0.args;//####[55]####
                JuMP_PackageOnly.setThreadCountCurrentParallelRegion(1);//####[56]####
            }//####[57]####
        }//####[60]####
    }//####[61]####
//####[62]####
    private static ArrayList<ParIterator<?>> _omp_piVarContainer = new ArrayList<ParIterator<?>>();//####[62]####
//####[63]####
    private static AtomicBoolean _holderForPIFirst;//####[63]####
//####[64]####
    private static AtomicBoolean _imFirst_2 = new AtomicBoolean(true);//####[64]####
//####[65]####
    private static AtomicInteger _imFinishedCounter_2 = new AtomicInteger(0);//####[65]####
//####[66]####
    private static CountDownLatch _waitBarrier_2 = new CountDownLatch(1);//####[66]####
//####[67]####
    private static CountDownLatch _waitBarrierAfter_2 = new CountDownLatch(1);//####[67]####
//####[68]####
    private static ParIterator<Integer> _pi_2 = null;//####[68]####
//####[69]####
    private static Integer _lastElement_2 = null;//####[69]####
//####[70]####
    private static _ompWorkSharedUserCode_PyjamaQuickSorter2_variables _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance = null;//####[70]####
//####[71]####
    private static void _ompWorkSharedUserCode_PyjamaQuickSorter2(_ompWorkSharedUserCode_PyjamaQuickSorter2_variables __omp_vars) {//####[71]####
        String[] args = __omp_vars.args;//####[73]####
        Integer i;//####[74]####
        while (_pi_2.hasNext()) //####[75]####
        {//####[75]####
            i = _pi_2.next();//####[76]####
            {//####[78]####
                System.out.println("Hello world from parallel code");//####[79]####
            }//####[80]####
        }//####[81]####
        __omp_vars.args = args;//####[83]####
    }//####[84]####
//####[88]####
    private static Method __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method = null;//####[88]####
    private static Lock __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock = new ReentrantLock();//####[88]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars)  {//####[88]####
//####[88]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[88]####
        return _ompParallelRegion_0(__omp_vars, new TaskInfo());//####[88]####
    }//####[88]####
    private static TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars, TaskInfo taskinfo)  {//####[88]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method == null) {//####[88]####
            try {//####[88]####
                __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock.lock();//####[88]####
                if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method == null) //####[88]####
                    __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method = ParaTaskHelper.getDeclaredMethod(new ParaTaskHelper.ClassGetter().getCurrentClass(), "__pt___ompParallelRegion_0", new Class[] {_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0.class});//####[88]####
            } catch (Exception e) {//####[88]####
                e.printStackTrace();//####[88]####
            } finally {//####[88]####
                __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock.unlock();//####[88]####
            }//####[88]####
        }//####[88]####
//####[88]####
        Object[] args = new Object[] {__omp_vars};//####[88]####
        taskinfo.setTaskArguments(args);//####[88]####
        taskinfo.setMethod(__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method);//####[88]####
//####[88]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[88]####
    }//####[88]####
    public static void __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars) {//####[88]####
        String[] args = __omp_vars.args;//####[90]####
        {//####[91]####
            if (Pyjama.insideParallelRegion()) //####[92]####
            {//####[92]####
                boolean _omp_imFirst = _imFirst_2.getAndSet(false);//####[94]####
                _holderForPIFirst = _imFirst_2;//####[95]####
                if (_omp_imFirst) //####[96]####
                {//####[96]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance = new _ompWorkSharedUserCode_PyjamaQuickSorter2_variables();//####[97]####
                    int __omp_size_ = 0;//####[98]####
                    for (int i = 0; i < 10; i = i + 1) //####[100]####
                    {//####[100]####
                        _lastElement_2 = i;//####[101]####
                        __omp_size_++;//####[102]####
                    }//####[103]####
                    _pi_2 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, ParIterator.DEFAULT_CHUNKSIZE, false);//####[104]####
                    _omp_piVarContainer.add(_pi_2);//####[105]####
                    _pi_2.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[106]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.args = args;//####[107]####
                    _waitBarrier_2.countDown();//####[108]####
                } else {//####[109]####
                    try {//####[110]####
                        _waitBarrier_2.await();//####[110]####
                    } catch (InterruptedException __omp__ie) {//####[110]####
                        __omp__ie.printStackTrace();//####[110]####
                    }//####[110]####
                }//####[111]####
                _ompWorkSharedUserCode_PyjamaQuickSorter2(_ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance);//####[112]####
                if (_imFinishedCounter_2.incrementAndGet() == JuMP_PackageOnly.getThreadCountCurrentParallelRegion()) //####[113]####
                {//####[113]####
                    _waitBarrierAfter_2.countDown();//####[114]####
                } else {//####[115]####
                    try {//####[116]####
                        _waitBarrierAfter_2.await();//####[117]####
                    } catch (InterruptedException __omp__ie) {//####[118]####
                        __omp__ie.printStackTrace();//####[119]####
                    }//####[120]####
                }//####[121]####
            } else {//####[123]####
                for (int i = 0; i < 10; i = i + 1) //####[125]####
                {//####[126]####
                    System.out.println("Hello world from parallel code");//####[127]####
                }//####[128]####
            }//####[129]####
        }//####[131]####
        __omp_vars.args = args;//####[133]####
    }//####[134]####
//####[134]####
}//####[134]####
