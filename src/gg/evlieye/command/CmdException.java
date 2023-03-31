/*

 *



 */
package gg.evlieye.command;

public abstract class CmdException extends Exception
{
	public CmdException()
	{}
	
	public CmdException(String message)
	{
		super(message);
	}
	
	public abstract void printToChat(Command cmd);
}
