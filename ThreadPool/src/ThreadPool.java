import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool{
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        for (int i = 1; i <= 100; i++) {
            int taskId = i;
            Runnable task = () -> {
                String threadName = Thread.currentThread().getName();
                System.out.println("[Task " + taskId + "] đang xử lý bởi: " + threadName);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
            executor.execute(task);
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
        }
        System.out.println("Tất cả 100 task đã hoàn thành!");
    }
}