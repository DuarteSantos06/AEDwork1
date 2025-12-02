package Package.Services;

public enum ServicesType {

    EATING,
    LEISURE,
    LODGING;

    public static ServicesType fromString(String text){
        if(text!=null){
            for(ServicesType s: ServicesType.values()){
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
