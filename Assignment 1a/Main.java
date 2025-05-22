public class Main {

    public static void main(String [] args) {
    	int n = Params.STATIONS;
    	
    	// create the elevator
    	Elevator elevator = new Elevator();
    	
    	// create an array for the stations
    	Station[] station = new Station[n];
    	
    	// create the stations
    	for (int i = 0; i < n; i++) {
    		station[i] = new Station(i);
    	}	
    	
    	// create the producer, consumer, and elevator operator
    	Producer producer = new Producer(elevator);
    	Consumer consumer = new Consumer(elevator);
    	Operator operator = new Operator(elevator);
    	
    	// create an array for the miners
    	Miner[] miner = new Miner[n];
    	
    	// create (and start) the miners
    	for (int i = 0; i < n; i++) {
    		miner[i] = new Miner(station[i]);
    		miner[i].start();
    	}
    	
    	// create an array for the engines
    	Engine[] engine = new Engine[n-1];
    	
    	// create (and start) the engines
    	for (int i = 0; i < n-1; i++) {
    		engine[i] = new Engine(station[i], station[i+1]);
    		engine[i].start();
    	}
    	
    	// create engines for the bottom of the elevator
    	Engine firstEngine = new Engine(elevator, station[0]);
    	Engine lastEngine = new Engine(station[n-1], elevator);
    	    	
    	// start remaining components
    	firstEngine.start();
    	lastEngine.start();
    	producer.start();
    	consumer.start();
    	operator.start();

    }
}