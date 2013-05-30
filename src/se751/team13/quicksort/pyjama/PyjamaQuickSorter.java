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
import pi.reductions.Reducible;//####[23]####
import java.util.*;//####[24]####
//####[24]####
//-- ParaTask related imports//####[24]####
import paratask.runtime.*;//####[24]####
import java.util.concurrent.ExecutionException;//####[24]####
import java.util.concurrent.locks.*;//####[24]####
import java.lang.reflect.*;//####[24]####
import javax.swing.SwingUtilities;//####[24]####
//####[24]####
public class PyjamaQuickSorter<T extends Comparable<? super T>> implements Sorter<T> {//####[26]####
//####[26]####
    /*  ParaTask helper method to access private/protected slots *///####[26]####
    public void __pt__accessPrivateSlot(Method m, Object instance, TaskID arg, Object interResult ) throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {//####[26]####
        if (m.getParameterTypes().length == 0)//####[26]####
            m.invoke(instance);//####[26]####
        else if ((m.getParameterTypes().length == 1))//####[26]####
            m.invoke(instance, arg);//####[26]####
        else //####[26]####
            m.invoke(instance, arg, interResult);//####[26]####
    }//####[26]####
//####[27]####
    private Integer numThreads;//####[27]####
//####[27]####
    private Integer granularity;//####[27]####
//####[27]####
    private List<T> list;//####[27]####
//####[27]####
    public PyjamaQuickSorter() {//####[27]####
        this(Runtime.getRuntime().availableProcessors(), 200);//####[28]####
    }//####[29]####
//####[29]####
    public PyjamaQuickSorter(int numThreads) {//####[29]####
        this(numThreads, 200);//####[30]####
    }//####[31]####
//####[31]####
    public PyjamaQuickSorter(int numThreads, int granularity) {//####[31]####
        this.numThreads = numThreads;//####[32]####
        this.granularity = granularity;//####[33]####
    }//####[34]####
//####[35]####
    @Override//####[35]####
    public List<T> sort(List<T> unsorted) {//####[35]####
        {//####[35]####
            list = new ArrayList<T>(unsorted);//####[36]####
            qssort(0, list.size() - 1);//####[37]####
            return list;//####[38]####
        }//####[39]####
    }//####[40]####
//####[41]####
    private static ArrayList<ParIterator<?>> _omp_piVarContainer = new ArrayList<ParIterator<?>>();//####[41]####
//####[42]####
    private static AtomicBoolean _holderForPIFirst;//####[42]####
//####[44]####
    private void qssort(int left, int right) {//####[44]####
        {//####[44]####
            if (right - left <= granularity) //####[45]####
            {//####[46]####
                insertion(left, right);//####[47]####
            } else if (left < right) //####[48]####
            {//####[49]####
                int pivotIndex = left + (right - left) / 2;//####[50]####
                int pivotNewIndex = partition(left, right, pivotIndex);//####[51]####
                if (Pyjama.insideParallelRegion()) //####[53]####
                {//####[53]####
                    {//####[55]####
                        for (int _omp_i_0 = 0; _omp_i_0 < 2; _omp_i_0 = _omp_i_0 + 1) //####[56]####
                        {//####[57]####
                            switch(_omp_i_0) {//####[58]####
                                case 0://####[58]####
                                    {//####[60]####
                                        qssort(left, pivotNewIndex - 1);//####[61]####
                                    }//####[62]####
                                    break;//####[63]####
                                case 1://####[63]####
                                    {//####[65]####
                                        qssort(pivotNewIndex + 1, right);//####[66]####
                                    }//####[67]####
                                    break;//####[68]####
                            }//####[68]####
                        }//####[70]####
                    }//####[71]####
                } else {//####[72]####
                    int _omp_numOfThreads_to_create = numThreads;//####[74]####
                    JuMP_PackageOnly.setThreadCountCurrentParallelRegion(_omp_numOfThreads_to_create);//####[76]####
                    _omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 _omp__parallelRegionVarHolderInstance_0 = new _omp__parallelRegionVarHolderClass_PyjamaQuickSorter0();//####[79]####
                    _omp__parallelRegionVarHolderInstance_0.pivotIndex = pivotIndex;//####[80]####
                    _omp__parallelRegionVarHolderInstance_0.left = left;//####[81]####
                    _omp__parallelRegionVarHolderInstance_0.right = right;//####[82]####
                    _omp__parallelRegionVarHolderInstance_0.pivotNewIndex = pivotNewIndex;//####[83]####
                    JuMP_PackageOnly.setMasterThread(Thread.currentThread());//####[86]####
                    TaskID _omp__parallelRegionTaskID_0 = _ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[87]####
                    __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[88]####
                    try {//####[89]####
                        _omp__parallelRegionTaskID_0.waitTillFinished();//####[89]####
                    } catch (Exception __pt__ex) {//####[89]####
                        __pt__ex.printStackTrace();//####[89]####
                    }//####[89]####
                    JuMP_PackageOnly.setMasterThread(null);//####[91]####
                    _holderForPIFirst.set(true);//####[92]####
                    pivotIndex = _omp__parallelRegionVarHolderInstance_0.pivotIndex;//####[94]####
                    left = _omp__parallelRegionVarHolderInstance_0.left;//####[95]####
                    right = _omp__parallelRegionVarHolderInstance_0.right;//####[96]####
                    pivotNewIndex = _omp__parallelRegionVarHolderInstance_0.pivotNewIndex;//####[97]####
                    JuMP_PackageOnly.setThreadCountCurrentParallelRegion(1);//####[98]####
                }//####[99]####
            }//####[102]####
        }//####[103]####
    }//####[104]####
//####[105]####
    private AtomicBoolean _imFirst_2 = new AtomicBoolean(true);//####[105]####
//####[106]####
    private AtomicInteger _imFinishedCounter_2 = new AtomicInteger(0);//####[106]####
//####[107]####
    private CountDownLatch _waitBarrier_2 = new CountDownLatch(1);//####[107]####
//####[108]####
    private CountDownLatch _waitBarrierAfter_2 = new CountDownLatch(1);//####[108]####
//####[109]####
    private ParIterator<Integer> _pi_2 = null;//####[109]####
//####[110]####
    private Integer _lastElement_2 = null;//####[110]####
//####[111]####
    private _ompWorkSharedUserCode_PyjamaQuickSorter2_variables _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance = null;//####[111]####
//####[112]####
    private void _ompWorkSharedUserCode_PyjamaQuickSorter2(_ompWorkSharedUserCode_PyjamaQuickSorter2_variables __omp_vars) {//####[112]####
        int pivotIndex = __omp_vars.pivotIndex;//####[114]####
        int left = __omp_vars.left;//####[115]####
        int right = __omp_vars.right;//####[116]####
        int pivotNewIndex = __omp_vars.pivotNewIndex;//####[117]####
        Integer _omp_i_0;//####[118]####
        while (_pi_2.hasNext()) //####[119]####
        {//####[119]####
            _omp_i_0 = _pi_2.next();//####[120]####
            {//####[122]####
                switch(_omp_i_0) {//####[123]####
                    case 0://####[123]####
                        {//####[125]####
                            qssort(left, pivotNewIndex - 1);//####[126]####
                        }//####[127]####
                        break;//####[128]####
                    case 1://####[128]####
                        {//####[130]####
                            qssort(pivotNewIndex + 1, right);//####[131]####
                        }//####[132]####
                        break;//####[133]####
                }//####[133]####
            }//####[135]####
        }//####[136]####
        __omp_vars.pivotIndex = pivotIndex;//####[138]####
        __omp_vars.left = left;//####[139]####
        __omp_vars.right = right;//####[140]####
        __omp_vars.pivotNewIndex = pivotNewIndex;//####[141]####
    }//####[142]####
//####[146]####
    private Method __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method = null;//####[146]####
    private Lock __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock = new ReentrantLock();//####[146]####
    private TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars)  {//####[146]####
//####[146]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[146]####
        return _ompParallelRegion_0(__omp_vars, new TaskInfo());//####[146]####
    }//####[146]####
    private TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars, TaskInfo taskinfo)  {//####[146]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method == null) {//####[146]####
            try {//####[146]####
                __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock.lock();//####[146]####
                if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method == null) //####[146]####
                    __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method = ParaTaskHelper.getDeclaredMethod(getClass(), "__pt___ompParallelRegion_0", new Class[] {_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0.class});//####[146]####
            } catch (Exception e) {//####[146]####
                e.printStackTrace();//####[146]####
            } finally {//####[146]####
                __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock.unlock();//####[146]####
            }//####[146]####
        }//####[146]####
//####[146]####
        Object[] args = new Object[] {__omp_vars};//####[146]####
        taskinfo.setTaskArguments(args);//####[146]####
        taskinfo.setMethod(__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method);//####[146]####
        taskinfo.setInstance(this);//####[146]####
//####[146]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[146]####
    }//####[146]####
    public void __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars) {//####[146]####
        int pivotIndex = __omp_vars.pivotIndex;//####[148]####
        int left = __omp_vars.left;//####[149]####
        int right = __omp_vars.right;//####[150]####
        int pivotNewIndex = __omp_vars.pivotNewIndex;//####[151]####
        {//####[152]####
            if (Pyjama.insideParallelRegion()) //####[153]####
            {//####[153]####
                boolean _omp_imFirst = _imFirst_2.getAndSet(false);//####[155]####
                _holderForPIFirst = _imFirst_2;//####[156]####
                if (_omp_imFirst) //####[157]####
                {//####[157]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance = new _ompWorkSharedUserCode_PyjamaQuickSorter2_variables();//####[158]####
                    int __omp_size_ = 0;//####[159]####
                    for (int _omp_i_0 = 0; _omp_i_0 < 2; _omp_i_0 = _omp_i_0 + 1) //####[161]####
                    {//####[161]####
                        _lastElement_2 = _omp_i_0;//####[162]####
                        __omp_size_++;//####[163]####
                    }//####[164]####
                    _pi_2 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 1, false);//####[165]####
                    _omp_piVarContainer.add(_pi_2);//####[166]####
                    _pi_2.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[167]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.pivotIndex = pivotIndex;//####[168]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.left = left;//####[169]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.right = right;//####[170]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.pivotNewIndex = pivotNewIndex;//####[171]####
                    _waitBarrier_2.countDown();//####[172]####
                } else {//####[173]####
                    try {//####[174]####
                        _waitBarrier_2.await();//####[174]####
                    } catch (InterruptedException __omp__ie) {//####[174]####
                        __omp__ie.printStackTrace();//####[174]####
                    }//####[174]####
                }//####[175]####
                _ompWorkSharedUserCode_PyjamaQuickSorter2(_ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance);//####[176]####
                if (_imFinishedCounter_2.incrementAndGet() == JuMP_PackageOnly.getThreadCountCurrentParallelRegion()) //####[177]####
                {//####[177]####
                    _waitBarrierAfter_2.countDown();//####[178]####
                } else {//####[179]####
                    try {//####[180]####
                        _waitBarrierAfter_2.await();//####[181]####
                    } catch (InterruptedException __omp__ie) {//####[182]####
                        __omp__ie.printStackTrace();//####[183]####
                    }//####[184]####
                }//####[185]####
            } else {//####[187]####
                for (int _omp_i_0 = 0; _omp_i_0 < 2; _omp_i_0 = _omp_i_0 + 1) //####[189]####
                {//####[190]####
                    switch(_omp_i_0) {//####[191]####
                        case 0://####[191]####
                            {//####[193]####
                                qssort(__omp_vars.left, __omp_vars.pivotNewIndex - 1);//####[194]####
                            }//####[195]####
                            break;//####[196]####
                        case 1://####[196]####
                            {//####[198]####
                                qssort(__omp_vars.pivotNewIndex + 1, __omp_vars.right);//####[199]####
                            }//####[200]####
                            break;//####[201]####
                    }//####[201]####
                }//####[203]####
            }//####[204]####
        }//####[206]####
        __omp_vars.pivotIndex = pivotIndex;//####[208]####
        __omp_vars.left = left;//####[209]####
        __omp_vars.right = right;//####[210]####
        __omp_vars.pivotNewIndex = pivotNewIndex;//####[211]####
    }//####[212]####
//####[212]####
//####[213]####
    private int partition(int leftIndex, int rightIndex, int pivotIndex) {//####[213]####
        {//####[213]####
            T pivot = list.get(pivotIndex);//####[214]####
            swap(pivotIndex, rightIndex);//####[215]####
            int storeIndex = leftIndex;//####[216]####
            for (int i = leftIndex; i < rightIndex; i++) //####[217]####
            {//####[218]####
                T t = list.get(i);//####[219]####
                if (t.compareTo(pivot) <= 0) //####[220]####
                {//####[221]####
                    swap(i, storeIndex);//####[222]####
                    storeIndex++;//####[223]####
                }//####[224]####
            }//####[225]####
            swap(storeIndex, rightIndex);//####[226]####
            return storeIndex;//####[227]####
        }//####[228]####
    }//####[229]####
//####[231]####
    private void swap(int leftIndex, int rightIndex) {//####[231]####
        {//####[231]####
            T t = list.get(leftIndex);//####[232]####
            list.set(leftIndex, list.get(rightIndex));//####[233]####
            list.set(rightIndex, t);//####[234]####
        }//####[235]####
    }//####[236]####
//####[238]####
    private void insertion(int offset, int limit) {//####[238]####
        {//####[238]####
            for (int i = offset; i < limit + 1; i++) //####[239]####
            {//####[240]####
                T valueToInsert = list.get(i);//####[241]####
                int hole = i;//####[242]####
                while (hole > 0 && valueToInsert.compareTo(list.get(hole - 1)) < 0) //####[243]####
                {//####[244]####
                    list.set(hole, list.get(hole - 1));//####[245]####
                    hole--;//####[246]####
                }//####[247]####
                list.set(hole, valueToInsert);//####[248]####
            }//####[249]####
        }//####[250]####
    }//####[251]####
}//####[251]####
