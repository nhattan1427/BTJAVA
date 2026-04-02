public class DeadlockSimulation{
    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();
    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread A: Đã khóa Resource 1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
                System.out.println("Thread A: Đang chờ Resource 2");
                synchronized (resource2) {
                    System.out.println("Thread A: Đã chiếm được Resource 2");
                }
            }
        }, "Thread-A");
        Thread threadB = new Thread(() -> {
            synchronized (resource2) {
                System.out.println("Thread B: Đã khóa Resource 2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {}
                System.out.println("Thread B: Đang chờ Resource 1");
                synchronized (resource1) {
                    System.out.println("Thread B: Đã chiếm được Resource 1");
                }
            }
        }, "Thread-B");
        threadA.start();
        threadB.start();
    }
}