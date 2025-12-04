package Package.Exceptions;

public class InvalidDiscount extends Exception{

    public InvalidDiscount(){
        super(ExceptionsMessagesEnum.INVALID_DISCOUNT.getMessage());
    }
}
