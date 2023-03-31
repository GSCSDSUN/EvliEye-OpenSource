/*

 *



 */
package gg.evlieye.commands;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import gg.evlieye.command.CmdError;
import gg.evlieye.command.CmdException;
import gg.evlieye.command.CmdSyntaxError;
import gg.evlieye.command.Command;
import gg.evlieye.util.ChatUtils;

public final class EnchantCmd extends Command
{
	public EnchantCmd()
	{
		super("enchant", "Enchants an item with everything,\n"
			+ "except for silk touch and curses.", ".enchant");
	}
	
	@Override
	public void call(String[] args) throws CmdException
	{
		if(!MC.player.getAbilities().creativeMode)
			throw new CmdError("Creative mode only.");
		
		if(args.length > 1)
			throw new CmdSyntaxError();
		
		ItemStack stack = getHeldItem();
		enchant(stack);
		
		ChatUtils.message("Item enchanted.");
	}
	
	private ItemStack getHeldItem() throws CmdError
	{
		ItemStack stack = MC.player.getInventory().getMainHandStack();
		
		if(stack.isEmpty())
			throw new CmdError("There is no item in your hand.");
		
		return stack;
	}
	
	private void enchant(ItemStack stack)
	{
		for(Enchantment enchantment : Registries.ENCHANTMENT)
		{
			if(enchantment == Enchantments.SILK_TOUCH)
				continue;
			
			if(enchantment.isCursed())
				continue;
			
			if(enchantment == Enchantments.QUICK_CHARGE)
			{
				stack.addEnchantment(enchantment, 5);
				continue;
			}
			
			stack.addEnchantment(enchantment, 127);
		}
	}
	
	@Override
	public String getPrimaryAction()
	{
		return "Enchant Held Item";
	}
	
	@Override
	public void doPrimaryAction()
	{
		WURST.getCmdProcessor().process("enchant");
	}
}
