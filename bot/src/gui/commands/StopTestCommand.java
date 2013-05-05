package gui.commands;

import external.ThreadManager;

/**
 * Zastavenie experimenta.
 * @author Marek Hlav·Ë <mark.hlavac@gmail.com>
 *
 */
public class StopTestCommand {

	public StopTestCommand(){
		ThreadManager.stopThreads();
	}
}
