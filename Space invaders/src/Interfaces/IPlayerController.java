package Interfaces;

import Exceptions.CommandExecuteException;

public interface IPlayerController {

	// PLAYER ACTIONS	
	public boolean move (int numCells, String dir) throws CommandExecuteException;
	public boolean shootLaser();
	public boolean shockWave();
	
	// CALLBACKS
	public void receivePoints(int points);
	public void enableShockWave();
	public void enableMissile();
}
