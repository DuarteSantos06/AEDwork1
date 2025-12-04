/**
 //@author Duarte Santos (70847) djp.santos@campus.fct.unl.pt
 //@author Rodrigo Marcelino (71260) r.marcelino@campus.fct.unl.pt */

package Package.Students;

public enum StudentsType {

    BOOKISH,
    THRIFTY,
    OUTGOING;

    public static StudentsType fromString(String text){
        if(text!=null){
            for(StudentsType s: StudentsType.values()){
                if(text.equalsIgnoreCase(s.name())){
                    return s;
                }
            }
        }
        return null;
    }

    public String toString(){
        return this.name().toLowerCase();
    }




}
