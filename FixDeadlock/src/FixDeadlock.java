public class FixDeadlock {
    private static final Object resource1 = new Object();
    private static final Object resource2 = new Object();
    public static void main(String[] args) {
        Thread threadA = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread A: Đã khóa Resource 1");
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                synchronized (resource2) {
                    System.out.println("Thread A: Đã chiếm được Resource 2 và hoàn thành");
                }
            }
        });
        Thread threadB = new Thread(() -> {
            synchronized (resource1) {
                System.out.println("Thread B: Đã khóa Resource 1");
                try { Thread.sleep(50); } catch (InterruptedException e) {}
                synchronized (resource2) {
                    System.out.println("Thread B: Đã chiếm được Resource 2 và hoàn thành");
                }
            }
        });

        threadA.start();
        threadB.start();
    }
}