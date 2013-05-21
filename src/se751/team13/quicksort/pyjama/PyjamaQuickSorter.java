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
    public List<T> sort(List<T> unsorted) {//####[26]####
        {//####[26]####
            return unsorted;//####[27]####
        }//####[28]####
    }//####[29]####
//####[30]####
    private static ArrayList<ParIterator<?>> _omp_piVarContainer = new ArrayList<ParIterator<?>>();//####[30]####
//####[31]####
    private static AtomicBoolean _holderForPIFirst;//####[31]####
}//####[31]####
