public class Miner extends Thread {
    private Station station;

    public Miner(Station station) {
        this.station = station;
    }

    @Override
    public void run() {
        while (!this.isInterrupted()) {
            try {
                SemaphoreUtil.lockGemEmpty(station.getId());
                System.out.println("miner " + station.getId() + " start mine gem");
                sleep(Params.MINING_TIME);
                System.out.println("miner " + station.getId() + " mined a gem");
                SemaphoreUtil.unLockGemFull(station.getId());
            } catch (InterruptedException e) {
                this.interrupt();
            }
        }
    }

}
