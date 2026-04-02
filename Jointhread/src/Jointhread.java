public class Jointhread {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("Thread 1: Đang nhập dữ liệu");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 1: Gửi kết quả");
        });
        Thread t2 = new Thread(() -> {
            System.out.println("Thread 2: Đang lấy dữ liệu từ Thread 1 và xử lí");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Thread 2: Lấy và xử lí dữ liệu thành công");
        });
        t1.start();
        t2.start();
        System.out.println("Main Thread: Chờ kết quả ");
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Main Thread:Tất cả task đã xong");
        System.out.println("Exit");
    }
}