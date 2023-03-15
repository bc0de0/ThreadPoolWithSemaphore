import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class ThreadPoolExample {
    private static final int THREAD_POOL_SIZE = 5;
    private static final int SEMAPHORE_PERMITS = 2;

    static final Semaphore semaphore = new Semaphore(SEMAPHORE_PERMITS);

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        for(int i = 0; i < 10; i++){
            Runnable task = new Task(1);
            executorService.execute(task);
        }
        executorService.shutdown();
    }

    private static class Task implements Runnable {

        private int taskId;

        public Task(int id){
            this.taskId=id;
        }

        public void run(){
            try {
                semaphore.acquire();
                System.out.println("Task #" + taskId + " is running.");
                System.out.println("Thread # " + Thread.currentThread().getName() + " is running");
                Thread.sleep(1000);
                System.out.println("Task #" + taskId + " is complete.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }

        }
    }
}
