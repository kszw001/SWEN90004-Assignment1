import java.util.Objects;

public class Engine extends Thread {

    private enum Status {
        STATION_TO_STATION, ELEVATOR_TO_STATION, STATION_TO_ELEVATOR
    }

    private final Status status;
    private Station station1;
    private Station station2;
    private Elevator elevator;

    public Engine(Station station, Station station1) {
        this.status = Status.STATION_TO_STATION;
        this.station1 = station;
        this.station2 = station1;
    }

    public Engine(Elevator elevator, Station station1) {
        this.status = Status.ELEVATOR_TO_STATION;
        this.elevator = elevator;
        this.station2 = station1;
    }

    public Engine(Station station, Elevator elevator) {
        this.status = Status.STATION_TO_ELEVATOR;
        this.elevator = elevator;
        this.station1 = station;
    }

    private boolean loadGem() {
        if (station1.getCart().getGem() == station1.getId() + 1) return true;

        SemaphoreUtil.lockGemFull(station1.getId());
        station1.getCart().setGem(station1.getId() + 1);
        System.out.println(station1.getCart() + " load a gem");
        SemaphoreUtil.unLockGemEmpty(station1.getId());

        return true;
    }


    private void work() {
        switch (status) {
            case STATION_TO_STATION -> {
                SemaphoreUtil.lockStationCart(station1.getId());
                SemaphoreUtil.lockStationCart(station2.getId());
                if (Objects.isNull(station1.getCart()) || !loadGem() || Objects.nonNull(station2.getCart())) {
                    SemaphoreUtil.unLockStationCart(station1.getId());
                    SemaphoreUtil.unLockStationCart(station2.getId());
                    return;
                }


                Cart cart = station1.getCart();
                station1.setCart(null);
                System.out.println(cart + " departs from station [" + station1.getId() + "]");
                SemaphoreUtil.unLockStationCart(station1.getId());


                station2.setCart(cart);
                System.out.println(cart + " arrive at station [" + station2.getId() + "]");
                SemaphoreUtil.unLockStationCart(station2.getId());
            }
            case STATION_TO_ELEVATOR -> {
                SemaphoreUtil.lockElevator();
                SemaphoreUtil.lockStationCart(station1.getId());

                if (Objects.isNull(station1.getCart()) || !loadGem() || !elevator.isEmpty()) {
                    SemaphoreUtil.unLockElevator();
                    SemaphoreUtil.unLockStationCart(station1.getId());
                    return;
                }


                Cart cart = station1.getCart();
                station1.setCart(null);
                System.out.println(cart + " departs from station [" + station1.getId() + "]");
                SemaphoreUtil.unLockStationCart(station1.getId());

                elevator.pushCartFromStation(cart);
                System.out.println(cart + " arrive at elevator");
                SemaphoreUtil.unLockElevator();
            }
            case ELEVATOR_TO_STATION -> {
                SemaphoreUtil.lockElevator();
                SemaphoreUtil.lockStationCart(station2.getId());

                if (elevator.isEmpty() || Objects.nonNull(station2.getCart()) || elevator.getCart().getGem() == Params.STATIONS) {
                    SemaphoreUtil.unLockElevator();
                    SemaphoreUtil.unLockStationCart(station2.getId());
                    return;
                }

                Cart cart = elevator.popCartToStation();
                System.out.println(cart + " departs from elevator");
                SemaphoreUtil.unLockElevator();

                station2.setCart(cart);
                System.out.println(cart + " arrive at station [" + station2.getId() + "]");
                SemaphoreUtil.unLockStationCart(station2.getId());
            }
        }

    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                work();
                sleep(Params.ENGINE_TIME);
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
