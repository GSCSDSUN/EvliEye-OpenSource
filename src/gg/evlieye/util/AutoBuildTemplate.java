/*

 *



 */
package gg.evlieye.util;

import java.io.IOException;
import java.nio.file.Path;
import java.util.LinkedHashSet;

import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import gg.evlieye.settings.FileSetting;
import gg.evlieye.util.json.JsonException;
import gg.evlieye.util.json.JsonUtils;
import gg.evlieye.util.json.WsonObject;

public final class AutoBuildTemplate
{
	private final Path path;
	private final String name;
	private final int[][] blocks;
	
	private AutoBuildTemplate(Path path, int[][] blocks)
	{
		this.path = path;
		String fileName = path.getFileName().toString();
		name = fileName.substring(0, fileName.lastIndexOf("."));
		this.blocks = blocks;
	}
	
	public static AutoBuildTemplate load(Path path)
		throws IOException, JsonException
	{
		WsonObject json = JsonUtils.parseFileToObject(path);
		int[][] blocks =
			JsonUtils.GSON.fromJson(json.getElement("blocks"), int[][].class);
		
		if(blocks == null)
			throw new JsonException("Template has no blocks!");
		
		for(int i = 0; i < blocks.length; i++)
		{
			int length = blocks[i].length;
			
			if(length < 3)
				throw new JsonException("Entry blocks[" + i
					+ "] doesn't have X, Y and Z offset. Only found " + length
					+ " values");
		}
		
		return new AutoBuildTemplate(path, blocks);
	}
	
	public LinkedHashSet<BlockPos> getPositions(BlockPos startPos,
		Direction direction)
	{
		Direction front = direction;
		Direction left = front.rotateYCounterclockwise();
		LinkedHashSet<BlockPos> positions = new LinkedHashSet<>();
		
		for(int[] block : blocks)
		{
			BlockPos pos = startPos;
			pos = pos.offset(left, block[0]);
			pos = pos.up(block[1]);
			pos = pos.offset(front, block[2]);
			positions.add(pos);
		}
		
		return positions;
	}
	
	public int size()
	{
		return blocks.length;
	}
	
	public boolean isSelected(FileSetting setting)
	{
		return path.equals(setting.getSelectedFile());
	}
	
	public Path getPath()
	{
		return path;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int[][] getBlocks()
	{
		return blocks;
	}
}
