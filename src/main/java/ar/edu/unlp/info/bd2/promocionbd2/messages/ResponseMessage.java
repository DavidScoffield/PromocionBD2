package ar.edu.unlp.info.bd2.promocionbd2.messages;

/*TODO delete class*/
public class ResponseMessage {
  private String message;

  public ResponseMessage(String message) {
    this.message = message;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }
}
