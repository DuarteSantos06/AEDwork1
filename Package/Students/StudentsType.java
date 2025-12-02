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
