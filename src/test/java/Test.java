import javafx.concurrent.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Administrator on 2018/1/19.
 */
public class Test {
    private  static List<Test> list = new ArrayList<Test>();


    public static void main(String[] args) {
        ThreadTest threadTest = new ThreadTest();
        ThreadTest.DeadClass deadClass = threadTest.new DeadClass();
        deadClass.deadRun();

        Task task = new Task() {
            @Override
            protected Object call() throws Exception {
                return null;
            }
        };

        new Thread(task);



}

}
class TestClassLoad extends  ClassLoader{
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {

        return super.loadClass(name, resolve);
    }
}
class callTest implements Callable{

    public Integer  call(){
        return 0;
    }
}

//多线程测试
class ThreadTest implements   Runnable{
    private static Integer key = 0;

      static Integer getkey(){
         synchronized (ThreadTest.class){
              key++;
              try {
                  Thread.sleep(1110);
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }
             return  key;
          }


    }


    public void run() {
        try {

               // Thread.sleep(5000);
                System.out.println(Thread.currentThread().getName() + ":" + getkey());

        }catch (Exception e){

        }

    }
    class LockThread3 implements Runnable {

        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        Lock lock = new ReentrantLock();
        {

        }

        public void run() {
           Lock readLock1 =  readWriteLock.readLock();
            Lock readLock = readWriteLock.readLock();
            Lock writeLock = readWriteLock.writeLock();
            readWriteLock.readLock();
            Thread.currentThread().getName();
            try {

            }catch (Exception e){

            }finally {
                readWriteLock.readLock().unlock();
            }
        }
    }
//死锁
   class DeadClass{
            Object ob1 = new Object();
            Object ob2 = new Object();
          void deadRun(){
                new Thread(new Runnable() {
                    public void run() {
                        synchronized (ob1){
                            System.out.println(Thread.currentThread().getName()+"ob1s--start");
                           try {
                               Thread.sleep(5000);
                               System.out.println(Thread.currentThread().getName()+"ob1--end");
                           }catch (Exception e){

                           }

                        synchronized (ob2){
                            System.out.println(Thread.currentThread().getName()+"ob2--start");
                        }
                        }
                    }
                },"thread1").start();
                new Thread(new Runnable() {
                    public void run() {
                        synchronized (ob2){
                            System.out.println(Thread.currentThread().getName()+"ob2--start");
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println(Thread.currentThread().getName()+"ob2--end");

                        synchronized (ob1){
                            System.out.println(Thread.currentThread().getName()+"ob1===");
                        }
                        }

                    }
                },"thread2").start();

          }
}

}
