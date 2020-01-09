package apigateway.demo.exception;

public class RateLimitException extends  RuntimeException{
    private Integer code;

    public RateLimitException(){

    }
    public RateLimitException(Integer code,String message){
        super(message);
        this.code = code ;
    }
}
