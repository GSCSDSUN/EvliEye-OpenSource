/*

 *



 */
package gg.evlieye.command;

import java.util.Objects;

import gg.evlieye.Category;
import gg.evlieye.Feature;
import gg.evlieye.util.ChatUtils;

public abstract class Command extends Feature
{
	private final String name;
	private final String description;
	private final String[] syntax;
	private Category category;
	
	public Command(String name, String description, String... syntax)
	{
		this.name = Objects.requireNonNull(name);
		this.description = Objects.requireNonNull(description);
		
		Objects.requireNonNull(syntax);
		if(syntax.length > 0)
			syntax[0] = "Syntax: " + syntax[0];
		this.syntax = syntax;
	}
	
	public abstract void call(String[] args) throws CmdException;
	
	@Override
	public final String getName()
	{
		return "." + name;
	}
	
	@Override
	public String getPrimaryAction()
	{
		return "";
	}
	
	@Override
	public final String getDescription()
	{
		String description = this.description;
		
		if(syntax.length > 0)
			description += "\n";
		
		for(String line : syntax)
			description += "\n" + line;
		
		return description;
	}
	
	public final String[] getSyntax()
	{
		return syntax;
	}
	
	public final void printHelp()
	{
		for(String line : description.split("\n"))
			ChatUtils.message(line);
		
		for(String line : syntax)
			ChatUtils.message(line);
	}
	
	@Override
	public final Category getCategory()
	{
		return category;
	}
	
	protected final void setCategory(Category category)
	{
		this.category = category;
	}
}
