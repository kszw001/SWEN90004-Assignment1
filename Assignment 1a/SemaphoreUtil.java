import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class SemaphoreUtil {
    private static final Semaphore elevator;
    private static final Semaphore workSpaceEmpty;
    private static final List<Semaphore> station;
    private static final List<Semaphore> gemEmpty;
    private static final List<Semaphore> gemFull;

    static {
        workSpaceEmpty = new Semaphore(Params.STATIONS);
        elevator = new Semaphore(1);
        station = new ArrayList<>();
        gemEmpty = new ArrayList<>();
        gemFull = new ArrayList<>();
        for (int i = 0; i < Params.STATIONS; i++) {
            station.add(new Semaphore(1));
            gemEmpty.add(new Semaphore(1));
            gemFull.add(new Semaphore(0));
        }
    }

    public static void lockElevator() {
        try {
            elevator.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unLockElevator() {
        elevator.release();
    }

    public static void lockWorkSpaceEmpty() {
        try {
            workSpaceEmpty.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unLockWorkSpaceEmpty() {
        workSpaceEmpty.release();
    }

    public static void lockStationCart(int id) {
        try {
            station.get(id).acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unLockStationCart(int id) {
        station.get(id).release();
    }

    public static void lockGemFull(int id) {
        try {
            gemFull.get(id).acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unLockGemFull(int id) {
        gemFull.get(id).release();
    }

    public static void lockGemEmpty(int id) {
        try {
            gemEmpty.get(id).acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void unLockGemEmpty(int id) {
        gemEmpty.get(id).release();
    }
}
