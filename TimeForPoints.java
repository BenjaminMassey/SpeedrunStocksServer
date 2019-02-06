package sss;

public class TimeForPoints implements Runnable {
	
	private static boolean running = false;
	
	public static void start() {
		running = true;
	}
	
	public void run() {
		while(TwitchChat.connected && running) {
			// For every channel running SpeedrunStocks...
			String[] viewers = TwitchChat.getViewers(null);
			for(String viewer : viewers) {
				// Give points
			}
			try {
				Thread.sleep(19000);
			}catch(Exception e) {
				System.err.println("Couldn't wait in TimeForPoints: " + e);
				stop();
			}
		}
		try {
			Thread.sleep(1000);
		}catch(Exception e) {
			System.err.println("Couldn't wait in TimeForPoints: " + e);
			stop();
		}
	}
	
	public static void stop() {
		running = false;
	}
}
