import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class InterruptDemo {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(()->{
                for (;;) {
                    System.out.println("running");
                }
        });
        executorService.shutdownNow();
    }
}
