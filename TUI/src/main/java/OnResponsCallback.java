import kong.unirest.HttpResponse;

/**
 * @author Rasmus Sander Larsen
 */
public interface OnResponsCallback {
    void OnSuccess (HttpResponse response);
    void OnFailure (HttpResponse response);
    void OnError(Exception e);
}
