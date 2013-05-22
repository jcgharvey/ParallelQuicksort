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
import java.util.ArrayList;//####[21]####
import java.util.List;//####[22]####
import pi.reductions.Reducible;//####[24]####
import java.util.*;//####[25]####
//####[25]####
//-- ParaTask related imports//####[25]####
import paratask.runtime.*;//####[25]####
import java.util.concurrent.ExecutionException;//####[25]####
import java.util.concurrent.locks.*;//####[25]####
import java.lang.reflect.*;//####[25]####
import javax.swing.SwingUtilities;//####[25]####
//####[25]####
public class PyjamaQuickSorter<T extends Comparable<? super T>> implements Sorter<T> {//####[27]####
//####[27]####
    /*  ParaTask helper method to access private/protected slots *///####[27]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[27]####
        if (m.getParameterTypes().length == 0)//####[27]####
            m.invoke(instance);//####[27]####
        else if ((m.getParameterTypes().length == 1))//####[27]####
            m.invoke(instance, arg);//####[27]####
        else //####[27]####
            m.invoke(instance, arg, interResult);//####[27]####
    }//####[27]####
//####[28]####
    private Integer granularity;//####[28]####
//####[28]####
    private List<T> list;//####[28]####
//####[28]####
    public PyjamaQuickSorter() {//####[28]####
        this(200);//####[29]####
    }//####[30]####
//####[30]####
    public PyjamaQuickSorter(int granularity) {//####[30]####
        this.granularity = granularity;//####[31]####
    }//####[32]####
//####[33]####
    @Override//####[33]####
    public List<T> sort(List<T> unsorted) {//####[33]####
        {//####[33]####
            list = new ArrayList<T>(unsorted);//####[34]####
            qssort(0, list.size() - 1);//####[35]####
            return list;//####[36]####
        }//####[37]####
    }//####[38]####
//####[39]####
    private static ArrayList<ParIterator<?>> _omp_piVarContainer = new ArrayList<ParIterator<?>>();//####[39]####
//####[40]####
    private static AtomicBoolean _holderForPIFirst;//####[40]####
//####[42]####
    private void qssort(int left, int right) {//####[42]####
        {//####[42]####
            if (right - left <= granularity) //####[43]####
            {//####[44]####
                insertion(left, right);//####[45]####
            } else if (left < right) //####[46]####
            {//####[47]####
                int pivotIndex = left + (right - left) / 2;//####[48]####
                int pivotNewIndex = partition(left, right, pivotIndex);//####[49]####
                if (Pyjama.insideParallelRegion()) //####[51]####
                {//####[51]####
                    {//####[53]####
                        for (int _omp_i_0 = 0; _omp_i_0 < 1; _omp_i_0 = _omp_i_0 + 1) //####[54]####
                        {//####[55]####
                            switch(_omp_i_0) {//####[56]####
                                case 0://####[56]####
                                    {//####[58]####
                                        qssort(left, pivotNewIndex - 1);//####[59]####
                                    }//####[60]####
                                    break;//####[61]####
                            }//####[61]####
                        }//####[63]####
                    }//####[64]####
                } else {//####[65]####
                    JuMP_PackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[67]####
                    _omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 _omp__parallelRegionVarHolderInstance_0 = new _omp__parallelRegionVarHolderClass_PyjamaQuickSorter0();//####[70]####
                    _omp__parallelRegionVarHolderInstance_0.pivotIndex = pivotIndex;//####[71]####
                    _omp__parallelRegionVarHolderInstance_0.left = left;//####[72]####
                    _omp__parallelRegionVarHolderInstance_0.right = right;//####[73]####
                    _omp__parallelRegionVarHolderInstance_0.pivotNewIndex = pivotNewIndex;//####[74]####
                    JuMP_PackageOnly.setMasterThread(Thread.currentThread());//####[77]####
                    TaskID _omp__parallelRegionTaskID_0 = _ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[78]####
                    __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[79]####
                    try {//####[80]####
                        _omp__parallelRegionTaskID_0.waitTillFinished();//####[80]####
                    } catch (Exception __pt__ex) {//####[80]####
                        __pt__ex.printStackTrace();//####[80]####
                    }//####[80]####
                    JuMP_PackageOnly.setMasterThread(null);//####[82]####
                    _holderForPIFirst.set(true);//####[83]####
                    pivotIndex = _omp__parallelRegionVarHolderInstance_0.pivotIndex;//####[85]####
                    left = _omp__parallelRegionVarHolderInstance_0.left;//####[86]####
                    right = _omp__parallelRegionVarHolderInstance_0.right;//####[87]####
                    pivotNewIndex = _omp__parallelRegionVarHolderInstance_0.pivotNewIndex;//####[88]####
                    JuMP_PackageOnly.setThreadCountCurrentParallelRegion(1);//####[89]####
                }//####[90]####
                qssort(pivotNewIndex + 1, right);//####[93]####
            }//####[94]####
        }//####[95]####
    }//####[96]####
//####[97]####
    private AtomicBoolean _imFirst_2 = new AtomicBoolean(true);//####[97]####
//####[98]####
    private AtomicInteger _imFinishedCounter_2 = new AtomicInteger(0);//####[98]####
//####[99]####
    private CountDownLatch _waitBarrier_2 = new CountDownLatch(1);//####[99]####
//####[100]####
    private CountDownLatch _waitBarrierAfter_2 = new CountDownLatch(1);//####[100]####
//####[101]####
    private ParIterator<Integer> _pi_2 = null;//####[101]####
//####[102]####
    private Integer _lastElement_2 = null;//####[102]####
//####[103]####
    private _ompWorkSharedUserCode_PyjamaQuickSorter2_variables _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance = null;//####[103]####
//####[104]####
    private void _ompWorkSharedUserCode_PyjamaQuickSorter2(_ompWorkSharedUserCode_PyjamaQuickSorter2_variables __omp_vars) {//####[104]####
        int pivotIndex = __omp_vars.pivotIndex;//####[106]####
        int left = __omp_vars.left;//####[107]####
        int right = __omp_vars.right;//####[108]####
        int pivotNewIndex = __omp_vars.pivotNewIndex;//####[109]####
        Integer _omp_i_0;//####[110]####
        while (_pi_2.hasNext()) //####[111]####
        {//####[111]####
            _omp_i_0 = _pi_2.next();//####[112]####
            {//####[114]####
                switch(_omp_i_0) {//####[115]####
                    case 0://####[115]####
                        {//####[117]####
                            qssort(left, pivotNewIndex - 1);//####[118]####
                        }//####[119]####
                        break;//####[120]####
                }//####[120]####
            }//####[122]####
        }//####[123]####
        __omp_vars.pivotIndex = pivotIndex;//####[125]####
        __omp_vars.left = left;//####[126]####
        __omp_vars.right = right;//####[127]####
        __omp_vars.pivotNewIndex = pivotNewIndex;//####[128]####
    }//####[129]####
//####[133]####
    private Method __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method = null;//####[133]####
    private Lock __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock = new ReentrantLock();//####[133]####
    private TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars)  {//####[133]####
//####[133]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[133]####
        return _ompParallelRegion_0(__omp_vars, new TaskInfo());//####[133]####
    }//####[133]####
    private TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars, TaskInfo taskinfo)  {//####[133]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method == null) {//####[133]####
            try {//####[133]####
                __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock.lock();//####[133]####
                if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method == null) //####[133]####
                    __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method = ParaTaskHelper.getDeclaredMethod(getClass(), "__pt___ompParallelRegion_0", new Class[] {_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0.class});//####[133]####
            } catch (Exception e) {//####[133]####
                e.printStackTrace();//####[133]####
            } finally {//####[133]####
                __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock.unlock();//####[133]####
            }//####[133]####
        }//####[133]####
//####[133]####
        Object[] args = new Object[] {__omp_vars};//####[133]####
        taskinfo.setTaskArguments(args);//####[133]####
        taskinfo.setMethod(__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method);//####[133]####
        taskinfo.setInstance(this);//####[133]####
//####[133]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[133]####
    }//####[133]####
    public void __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars) {//####[133]####
        int pivotIndex = __omp_vars.pivotIndex;//####[135]####
        int left = __omp_vars.left;//####[136]####
        int right = __omp_vars.right;//####[137]####
        int pivotNewIndex = __omp_vars.pivotNewIndex;//####[138]####
        {//####[139]####
            if (Pyjama.insideParallelRegion()) //####[140]####
            {//####[140]####
                boolean _omp_imFirst = _imFirst_2.getAndSet(false);//####[142]####
                _holderForPIFirst = _imFirst_2;//####[143]####
                if (_omp_imFirst) //####[144]####
                {//####[144]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance = new _ompWorkSharedUserCode_PyjamaQuickSorter2_variables();//####[145]####
                    int __omp_size_ = 0;//####[146]####
                    for (int _omp_i_0 = 0; _omp_i_0 < 1; _omp_i_0 = _omp_i_0 + 1) //####[148]####
                    {//####[148]####
                        _lastElement_2 = _omp_i_0;//####[149]####
                        __omp_size_++;//####[150]####
                    }//####[151]####
                    _pi_2 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 1, false);//####[152]####
                    _omp_piVarContainer.add(_pi_2);//####[153]####
                    _pi_2.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[154]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.pivotIndex = pivotIndex;//####[155]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.left = left;//####[156]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.right = right;//####[157]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.pivotNewIndex = pivotNewIndex;//####[158]####
                    _waitBarrier_2.countDown();//####[159]####
                } else {//####[160]####
                    try {//####[161]####
                        _waitBarrier_2.await();//####[161]####
                    } catch (InterruptedException __omp__ie) {//####[161]####
                        __omp__ie.printStackTrace();//####[161]####
                    }//####[161]####
                }//####[162]####
                _ompWorkSharedUserCode_PyjamaQuickSorter2(_ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance);//####[163]####
                if (_imFinishedCounter_2.incrementAndGet() == JuMP_PackageOnly.getThreadCountCurrentParallelRegion()) //####[164]####
                {//####[164]####
                    _waitBarrierAfter_2.countDown();//####[165]####
                } else {//####[166]####
                    try {//####[167]####
                        _waitBarrierAfter_2.await();//####[168]####
                    } catch (InterruptedException __omp__ie) {//####[169]####
                        __omp__ie.printStackTrace();//####[170]####
                    }//####[171]####
                }//####[172]####
            } else {//####[174]####
                for (int _omp_i_0 = 0; _omp_i_0 < 1; _omp_i_0 = _omp_i_0 + 1) //####[176]####
                {//####[177]####
                    switch(_omp_i_0) {//####[178]####
                        case 0://####[178]####
                            {//####[180]####
                                qssort(__omp_vars.left, __omp_vars.pivotNewIndex - 1);//####[181]####
                            }//####[182]####
                            break;//####[183]####
                    }//####[183]####
                }//####[185]####
            }//####[186]####
        }//####[188]####
        __omp_vars.pivotIndex = pivotIndex;//####[190]####
        __omp_vars.left = left;//####[191]####
        __omp_vars.right = right;//####[192]####
        __omp_vars.pivotNewIndex = pivotNewIndex;//####[193]####
    }//####[194]####
//####[194]####
//####[195]####
    private int partition(int leftIndex, int rightIndex, int pivotIndex) {//####[195]####
        {//####[195]####
            T pivot = list.get(pivotIndex);//####[196]####
            swap(pivotIndex, rightIndex);//####[197]####
            int storeIndex = leftIndex;//####[198]####
            for (int i = leftIndex; i < rightIndex; i++) //####[199]####
            {//####[200]####
                T t = list.get(i);//####[201]####
                if (t.compareTo(pivot) <= 0) //####[202]####
                {//####[203]####
                    swap(i, storeIndex);//####[204]####
                    storeIndex++;//####[205]####
                }//####[206]####
            }//####[207]####
            swap(storeIndex, rightIndex);//####[208]####
            return storeIndex;//####[209]####
        }//####[210]####
    }//####[211]####
//####[213]####
    private void swap(int leftIndex, int rightIndex) {//####[213]####
        {//####[213]####
            T t = list.get(leftIndex);//####[214]####
            list.set(leftIndex, list.get(rightIndex));//####[215]####
            list.set(rightIndex, t);//####[216]####
        }//####[217]####
    }//####[218]####
//####[220]####
    private void insertion(int offset, int limit) {//####[220]####
        {//####[220]####
            for (int i = offset; i < limit + 1; i++) //####[221]####
            {//####[222]####
                T valueToInsert = list.get(i);//####[223]####
                int hole = i;//####[224]####
                while (hole > 0 && valueToInsert.compareTo(list.get(hole - 1)) < 0) //####[225]####
                {//####[226]####
                    list.set(hole, list.get(hole - 1));//####[227]####
                    hole--;//####[228]####
                }//####[229]####
                list.set(hole, valueToInsert);//####[230]####
            }//####[231]####
        }//####[232]####
    }//####[233]####
}//####[233]####
