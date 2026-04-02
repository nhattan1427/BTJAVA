class MyThread extends Thread {
    public MyThread(String name) {
        super(name);
    }
    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println("Hello from Thread " + getName());
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
public class Mythread {
    public static void main(String[] args) {
        MyThread t1 = new MyThread("1");
        MyThread t2 = new MyThread("2");

        t1.start();
        t2.start();
    }
}