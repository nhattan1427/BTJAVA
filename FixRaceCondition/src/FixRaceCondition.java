class CounterSync {
    private int count = 0;
    public synchronized void increment() {
        count++;
    }
    public int getCount() {
        return count;
    }
}
public class FixRaceCondition {
    public static void main(String[] args) throws InterruptedException {
        CounterSync counter = new CounterSync();
        Runnable task = () -> {
            for (int i = 0; i < 100000; i++) {
                counter.increment();
            }
        };
        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("Kết quả sau khi fix: " + counter.getCount());
    }
}