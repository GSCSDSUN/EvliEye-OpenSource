/*

 *



 */
package gg.evlieye.ai;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

public class PathQueue
{
	private final PriorityQueue<PathQueue.Entry> queue =
		new PriorityQueue<>(Comparator.comparing(e1 -> e1.priority));
	
	private static class Entry
	{
		private PathPos pos;
		private float priority;
		
		public Entry(PathPos pos, float priority)
		{
			this.pos = pos;
			this.priority = priority;
		}
	}
	
	public boolean isEmpty()
	{
		return queue.isEmpty();
	}
	
	public boolean add(PathPos pos, float priority)
	{
		return queue.add(new Entry(pos, priority));
	}
	
	public PathPos[] toArray()
	{
		PathPos[] array = new PathPos[size()];
		Iterator<Entry> itr = queue.iterator();
		
		for(int i = 0; i < size() && itr.hasNext(); i++)
			array[i] = itr.next().pos;
		
		return array;
	}
	
	public int size()
	{
		return queue.size();
	}
	
	public void clear()
	{
		queue.clear();
	}
	
	public PathPos poll()
	{
		return queue.poll().pos;
	}
}
