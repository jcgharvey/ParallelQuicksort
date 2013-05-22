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
                        for (int _omp_i_0 = 0; _omp_i_0 < 2; _omp_i_0 = _omp_i_0 + 1) //####[54]####
                        {//####[55]####
                            switch(_omp_i_0) {//####[56]####
                                case 0://####[56]####
                                    {//####[58]####
                                        qssort(left, pivotNewIndex - 1);//####[59]####
                                    }//####[60]####
                                    break;//####[61]####
                                case 1://####[61]####
                                    {//####[63]####
                                        qssort(pivotNewIndex + 1, right);//####[64]####
                                    }//####[65]####
                                    break;//####[66]####
                            }//####[66]####
                        }//####[68]####
                    }//####[69]####
                } else {//####[70]####
                    JuMP_PackageOnly.setThreadCountCurrentParallelRegion(Pyjama.omp_get_num_threads());//####[72]####
                    _omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 _omp__parallelRegionVarHolderInstance_0 = new _omp__parallelRegionVarHolderClass_PyjamaQuickSorter0();//####[75]####
                    _omp__parallelRegionVarHolderInstance_0.pivotIndex = pivotIndex;//####[76]####
                    _omp__parallelRegionVarHolderInstance_0.left = left;//####[77]####
                    _omp__parallelRegionVarHolderInstance_0.right = right;//####[78]####
                    _omp__parallelRegionVarHolderInstance_0.pivotNewIndex = pivotNewIndex;//####[79]####
                    JuMP_PackageOnly.setMasterThread(Thread.currentThread());//####[82]####
                    TaskID _omp__parallelRegionTaskID_0 = _ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[83]####
                    __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderInstance_0);//####[84]####
                    try {//####[85]####
                        _omp__parallelRegionTaskID_0.waitTillFinished();//####[85]####
                    } catch (Exception __pt__ex) {//####[85]####
                        __pt__ex.printStackTrace();//####[85]####
                    }//####[85]####
                    JuMP_PackageOnly.setMasterThread(null);//####[87]####
                    _holderForPIFirst.set(true);//####[88]####
                    pivotIndex = _omp__parallelRegionVarHolderInstance_0.pivotIndex;//####[90]####
                    left = _omp__parallelRegionVarHolderInstance_0.left;//####[91]####
                    right = _omp__parallelRegionVarHolderInstance_0.right;//####[92]####
                    pivotNewIndex = _omp__parallelRegionVarHolderInstance_0.pivotNewIndex;//####[93]####
                    JuMP_PackageOnly.setThreadCountCurrentParallelRegion(1);//####[94]####
                }//####[95]####
            }//####[98]####
        }//####[99]####
    }//####[100]####
//####[101]####
    private AtomicBoolean _imFirst_2 = new AtomicBoolean(true);//####[101]####
//####[102]####
    private AtomicInteger _imFinishedCounter_2 = new AtomicInteger(0);//####[102]####
//####[103]####
    private CountDownLatch _waitBarrier_2 = new CountDownLatch(1);//####[103]####
//####[104]####
    private CountDownLatch _waitBarrierAfter_2 = new CountDownLatch(1);//####[104]####
//####[105]####
    private ParIterator<Integer> _pi_2 = null;//####[105]####
//####[106]####
    private Integer _lastElement_2 = null;//####[106]####
//####[107]####
    private _ompWorkSharedUserCode_PyjamaQuickSorter2_variables _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance = null;//####[107]####
//####[108]####
    private void _ompWorkSharedUserCode_PyjamaQuickSorter2(_ompWorkSharedUserCode_PyjamaQuickSorter2_variables __omp_vars) {//####[108]####
        int pivotIndex = __omp_vars.pivotIndex;//####[110]####
        int left = __omp_vars.left;//####[111]####
        int right = __omp_vars.right;//####[112]####
        int pivotNewIndex = __omp_vars.pivotNewIndex;//####[113]####
        Integer _omp_i_0;//####[114]####
        while (_pi_2.hasNext()) //####[115]####
        {//####[115]####
            _omp_i_0 = _pi_2.next();//####[116]####
            {//####[118]####
                switch(_omp_i_0) {//####[119]####
                    case 0://####[119]####
                        {//####[121]####
                            qssort(left, pivotNewIndex - 1);//####[122]####
                        }//####[123]####
                        break;//####[124]####
                    case 1://####[124]####
                        {//####[126]####
                            qssort(pivotNewIndex + 1, right);//####[127]####
                        }//####[128]####
                        break;//####[129]####
                }//####[129]####
            }//####[131]####
        }//####[132]####
        __omp_vars.pivotIndex = pivotIndex;//####[134]####
        __omp_vars.left = left;//####[135]####
        __omp_vars.right = right;//####[136]####
        __omp_vars.pivotNewIndex = pivotNewIndex;//####[137]####
    }//####[138]####
//####[142]####
    private Method __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method = null;//####[142]####
    private Lock __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock = new ReentrantLock();//####[142]####
    private TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars)  {//####[142]####
//####[142]####
        //-- execute asynchronously by enqueuing onto the taskpool//####[142]####
        return _ompParallelRegion_0(__omp_vars, new TaskInfo());//####[142]####
    }//####[142]####
    private TaskIDGroup<Void> _ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars, TaskInfo taskinfo)  {//####[142]####
        if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method == null) {//####[142]####
            try {//####[142]####
                __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock.lock();//####[142]####
                if (__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method == null) //####[142]####
                    __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method = ParaTaskHelper.getDeclaredMethod(getClass(), "__pt___ompParallelRegion_0", new Class[] {_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0.class});//####[142]####
            } catch (Exception e) {//####[142]####
                e.printStackTrace();//####[142]####
            } finally {//####[142]####
                __pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_lock.unlock();//####[142]####
            }//####[142]####
        }//####[142]####
//####[142]####
        Object[] args = new Object[] {__omp_vars};//####[142]####
        taskinfo.setTaskArguments(args);//####[142]####
        taskinfo.setMethod(__pt___ompParallelRegion_0__omp__parallelRegionVarHolderClass_PyjamaQuickSorter0_method);//####[142]####
        taskinfo.setInstance(this);//####[142]####
//####[142]####
        return TaskpoolFactory.getTaskpool().enqueueMulti(taskinfo, Pyjama.omp_get_num_threads() - 1);//####[142]####
    }//####[142]####
    public void __pt___ompParallelRegion_0(_omp__parallelRegionVarHolderClass_PyjamaQuickSorter0 __omp_vars) {//####[142]####
        int pivotIndex = __omp_vars.pivotIndex;//####[144]####
        int left = __omp_vars.left;//####[145]####
        int right = __omp_vars.right;//####[146]####
        int pivotNewIndex = __omp_vars.pivotNewIndex;//####[147]####
        {//####[148]####
            if (Pyjama.insideParallelRegion()) //####[149]####
            {//####[149]####
                boolean _omp_imFirst = _imFirst_2.getAndSet(false);//####[151]####
                _holderForPIFirst = _imFirst_2;//####[152]####
                if (_omp_imFirst) //####[153]####
                {//####[153]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance = new _ompWorkSharedUserCode_PyjamaQuickSorter2_variables();//####[154]####
                    int __omp_size_ = 0;//####[155]####
                    for (int _omp_i_0 = 0; _omp_i_0 < 2; _omp_i_0 = _omp_i_0 + 1) //####[157]####
                    {//####[157]####
                        _lastElement_2 = _omp_i_0;//####[158]####
                        __omp_size_++;//####[159]####
                    }//####[160]####
                    _pi_2 = ParIteratorFactory.createParIterator(0, __omp_size_, 1, Pyjama.omp_get_num_threads(), ParIterator.Schedule.DYNAMIC, 1, false);//####[161]####
                    _omp_piVarContainer.add(_pi_2);//####[162]####
                    _pi_2.setThreadIdGenerator(new UniqueThreadIdGeneratorForOpenMP());//####[163]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.pivotIndex = pivotIndex;//####[164]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.left = left;//####[165]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.right = right;//####[166]####
                    _ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance.pivotNewIndex = pivotNewIndex;//####[167]####
                    _waitBarrier_2.countDown();//####[168]####
                } else {//####[169]####
                    try {//####[170]####
                        _waitBarrier_2.await();//####[170]####
                    } catch (InterruptedException __omp__ie) {//####[170]####
                        __omp__ie.printStackTrace();//####[170]####
                    }//####[170]####
                }//####[171]####
                _ompWorkSharedUserCode_PyjamaQuickSorter2(_ompWorkSharedUserCode_PyjamaQuickSorter2_variables_instance);//####[172]####
                if (_imFinishedCounter_2.incrementAndGet() == JuMP_PackageOnly.getThreadCountCurrentParallelRegion()) //####[173]####
                {//####[173]####
                    _waitBarrierAfter_2.countDown();//####[174]####
                } else {//####[175]####
                    try {//####[176]####
                        _waitBarrierAfter_2.await();//####[177]####
                    } catch (InterruptedException __omp__ie) {//####[178]####
                        __omp__ie.printStackTrace();//####[179]####
                    }//####[180]####
                }//####[181]####
            } else {//####[183]####
                for (int _omp_i_0 = 0; _omp_i_0 < 2; _omp_i_0 = _omp_i_0 + 1) //####[185]####
                {//####[186]####
                    switch(_omp_i_0) {//####[187]####
                        case 0://####[187]####
                            {//####[189]####
                                qssort(__omp_vars.left, __omp_vars.pivotNewIndex - 1);//####[190]####
                            }//####[191]####
                            break;//####[192]####
                        case 1://####[192]####
                            {//####[194]####
                                qssort(__omp_vars.pivotNewIndex + 1, __omp_vars.right);//####[195]####
                            }//####[196]####
                            break;//####[197]####
                    }//####[197]####
                }//####[199]####
            }//####[200]####
        }//####[202]####
        __omp_vars.pivotIndex = pivotIndex;//####[204]####
        __omp_vars.left = left;//####[205]####
        __omp_vars.right = right;//####[206]####
        __omp_vars.pivotNewIndex = pivotNewIndex;//####[207]####
    }//####[208]####
//####[208]####
//####[209]####
    private int partition(int leftIndex, int rightIndex, int pivotIndex) {//####[209]####
        {//####[209]####
            T pivot = list.get(pivotIndex);//####[210]####
            swap(pivotIndex, rightIndex);//####[211]####
            int storeIndex = leftIndex;//####[212]####
            for (int i = leftIndex; i < rightIndex; i++) //####[213]####
            {//####[214]####
                T t = list.get(i);//####[215]####
                if (t.compareTo(pivot) <= 0) //####[216]####
                {//####[217]####
                    swap(i, storeIndex);//####[218]####
                    storeIndex++;//####[219]####
                }//####[220]####
            }//####[221]####
            swap(storeIndex, rightIndex);//####[222]####
            return storeIndex;//####[223]####
        }//####[224]####
    }//####[225]####
//####[227]####
    private void swap(int leftIndex, int rightIndex) {//####[227]####
        {//####[227]####
            T t = list.get(leftIndex);//####[228]####
            list.set(leftIndex, list.get(rightIndex));//####[229]####
            list.set(rightIndex, t);//####[230]####
        }//####[231]####
    }//####[232]####
//####[234]####
    private void insertion(int offset, int limit) {//####[234]####
        {//####[234]####
            for (int i = offset; i < limit + 1; i++) //####[235]####
            {//####[236]####
                T valueToInsert = list.get(i);//####[237]####
                int hole = i;//####[238]####
                while (hole > 0 && valueToInsert.compareTo(list.get(hole - 1)) < 0) //####[239]####
                {//####[240]####
                    list.set(hole, list.get(hole - 1));//####[241]####
                    hole--;//####[242]####
                }//####[243]####
                list.set(hole, valueToInsert);//####[244]####
            }//####[245]####
        }//####[246]####
    }//####[247]####
}//####[247]####
