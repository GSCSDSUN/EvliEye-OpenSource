/*

 *



 */
package gg.evlieye.hacks.chestesp;

public enum ChestEspStyle
{
	BOXES("Boxes only", true, false),
	LINES("Lines only", false, true),
	LINES_AND_BOXES("Lines and boxes", true, true);
	
	private final String name;
	private final boolean boxes;
	private final boolean lines;
	
	private ChestEspStyle(String name, boolean boxes, boolean lines)
	{
		this.name = name;
		this.boxes = boxes;
		this.lines = lines;
	}
	
	public boolean hasBoxes()
	{
		return boxes;
	}
	
	public boolean hasLines()
	{
		return lines;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
