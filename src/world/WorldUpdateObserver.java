package world;

public interface WorldUpdateObserver {
	
	public void worldUpdated(World w, double timeElapsed);
}