/*

 *



 */
package gg.evlieye.hacks.newchunks;

import java.util.Set;

import net.minecraft.client.render.BufferBuilder.BuiltBuffer;
import net.minecraft.util.math.ChunkPos;

public interface NewChunksChunkRenderer
{
	public BuiltBuffer buildBuffer(Set<ChunkPos> chunks, int drawDistance);
}
