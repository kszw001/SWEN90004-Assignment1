public class Consumer extends Thread {

    // the elevator that carts are taken from
    private Elevator elevator;

    // create a new consumer
    public Consumer(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    // carts are removed from the elevator at random intervals
    public void run() {
        while (!this.isInterrupted()) {
            try {
                // remove a cart from the elevator
                SemaphoreUtil.lockElevator();
                if (!elevator.isEmpty() && elevator.getCart().getGem() == Params.STATIONS) {
                    Cart c = this.elevator.depart();
                    System.out.println(c.toString() + " departs from mine");
                    SemaphoreUtil.unLockWorkSpaceEmpty();
                }
                SemaphoreUtil.unLockElevator();

                // pause before removing a further cart
                sleep(Params.departurePause());
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
