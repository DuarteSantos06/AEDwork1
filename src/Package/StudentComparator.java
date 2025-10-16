package Package;

import Package.Students.Students;
import dataStructures.Comparator;

public class StudentComparator implements Comparator<Students>{

    public int compare(Students s1, Students s2){
        return s1.getName().compareTo(s2.getName());
    }
}
