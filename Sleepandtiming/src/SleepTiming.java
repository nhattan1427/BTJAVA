import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

class TimeWorker implements Runnable {
    private String name;
    private int sleepMillis;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    public TimeWorker(String name, int sleepSeconds) {
        this.name = name;
        this.sleepMillis = sleepSeconds * 1000;
    }
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            String timeStamp = LocalTime.now().format(formatter);
            System.out.println("[" + timeStamp + "] " + name + " đang chạy (lần " + i + ")");
            try {
                Thread.sleep(sleepMillis);
            } catch (InterruptedException e) {
                System.out.println(name + " bị ngắt.");
            }
        }
    }
}
public class SleepTiming {
    public static void main(String[] args) {
        System.out.println("Bắt đầu chương trình...");
        Thread t1 = new Thread(new TimeWorker("Thread 1", 1));
        Thread t2 = new Thread(new TimeWorker("Thread 2", 2));
        Thread t3 = new Thread(new TimeWorker("Thread 3", 3));

        t1.start();
        t2.start();
        t3.start();
    }
}