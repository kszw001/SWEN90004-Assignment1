public class Operator extends Thread {
    private Elevator elevator;

    public Operator(Elevator elevator) {
        this.elevator = elevator;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                // Play the elevator (The operator may need more job)
                SemaphoreUtil.lockElevator();
                System.out.println("operator operate the elevator");
                elevator.operate();
                SemaphoreUtil.unLockElevator();

                sleep(Params.operatorPause());
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }
}
