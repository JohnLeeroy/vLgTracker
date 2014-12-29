package vlg.jli.tracker;

/**
 * Created by johnli on 12/7/14.
 */
public interface AsyncListener {
    public void onResult(Object response, boolean isSuccess);
}