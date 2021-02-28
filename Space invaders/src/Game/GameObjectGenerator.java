package Game;

import Exceptions.FileContentsException;
import Files.FileContentsVerifier;
import Objetos.*;

public class GameObjectGenerator {
	private static GameObject[] availableGameObjects = {
			new UCMShip(),
			new Ovni(),
			new RegularAlien(),
			new DestroyerAlien(),
			new Shockwave(),
			new Bomb(),
			new SuperMissile(),
			new UCMMissile(),
			new ExplosiveAlien()
		};

	public GameObjectGenerator() {
		
	}
	public static GameObject parse(String stringFromFile, Game game, FileContentsVerifier verifier) throws FileContentsException {		
		GameObject gameObject = null;
		for (GameObject go: availableGameObjects) {
			gameObject = go.parse(stringFromFile, game, verifier);
			if (gameObject != null) break;
		}
		return gameObject;
	}
}
