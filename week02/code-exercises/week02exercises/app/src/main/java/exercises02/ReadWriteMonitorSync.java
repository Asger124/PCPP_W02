package exercises02;

// import java.util.concurrent.locks.Condition;
// import java.util.concurrent.locks.Lock;
// import java.util.concurrent.locks.ReentrantLock;

public class ReadWriteMonitorSync {
    private int readers = 0;
    private boolean writer = false;
    private Object o = new Object();
    // private Condition condition = lock.newCondition();

    //////////////////////////
    // Read lock operations //
    //////////////////////////

    public void readLock() throws InterruptedException {
        synchronized (o) {
            while (writer) {
                o.wait();
            }
            readers++;

        }
    }

    public void readUnlock() {
        synchronized (o) {
            readers--;
            if (readers == 0)
                o.notifyAll();

        }
    }

    ///////////////////////////
    // Write lock operations //
    ///////////////////////////

    public void writeLock() throws InterruptedException {
        synchronized (o) {
            while (writer)
                o.wait();
            writer = true;
            while (readers > 0)
                o.wait();

        }
    }

    public void writeUnlock() {
        synchronized (o) {
            writer = false;
            o.notifyAll();

        }

    }
}
