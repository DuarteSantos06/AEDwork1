//@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
//@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt
package Package;

import Package.Students.Students;
import dataStructures.Comparator;

public class StudentComparator implements Comparator<Students>{

    public int compare(Students s1, Students s2){
        return s1.getName().compareTo(s2.getName());
    }
}
