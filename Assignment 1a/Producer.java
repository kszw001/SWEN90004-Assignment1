public class Producer extends Thread {

	// the elevator for new carts
	private Elevator elevator;
	
	// create a new producer
	public Producer(Elevator elevator) {
		this.elevator = elevator;
	}

	@Override
	// carts are sent to the elevator at random intervals
	public void run() {
		while(!this.isInterrupted()) {
			try {
				// create a new cart and send to elevator
				SemaphoreUtil.lockWorkSpaceEmpty();
				SemaphoreUtil.lockElevator();
				if (!elevator.isEmpty()){
					SemaphoreUtil.unLockElevator();
					SemaphoreUtil.unLockWorkSpaceEmpty();
					continue;
				}
				Cart cart = Cart.getNewCart();
				this.elevator.arrive(cart);
				System.out.println(cart.toString() + " arrives at the mine");
				SemaphoreUtil.unLockElevator();
				
				// pause before sending another cart
				sleep(Params.arrivalPause());
			}
			catch (InterruptedException e) {
				this.interrupt();
			}
		}
	}
}
