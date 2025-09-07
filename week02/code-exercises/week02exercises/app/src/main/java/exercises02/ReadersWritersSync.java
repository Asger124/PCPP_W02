package exercises02;

public class ReadersWritersSync {

    public ReadersWritersSync() {
        ReadWriteMonitorSync m = new ReadWriteMonitorSync();

        final int numReadersWriters = 10;

        for (int i = 0; i < numReadersWriters; i++) {

            // start a reader
            new Thread(() -> {
                try {
                    m.readLock();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(" Reader " + Thread.currentThread().getId() + " started reading");
                // read
                System.out.println(" Reader " + Thread.currentThread().getId() + " stopped reading");
                m.readUnlock();
            }).start();

            // start a writer
            new Thread(() -> {
                try {
                    m.writeLock();
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.println(" Writer " + Thread.currentThread().getId() + " started writing");
                // write
                System.out.println(" Writer " + Thread.currentThread().getId() + " stopped writing");
                m.writeUnlock();
            }).start();

        }
    }

    public static void main(String[] args) {
        new ReadersWritersSync();
    }

}