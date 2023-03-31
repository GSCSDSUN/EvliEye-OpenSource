/*

 *



 */
package gg.evlieye.hacks.autolibrarian;

import gg.evlieye.settings.BookOffersSetting;
import gg.evlieye.settings.EnumSetting;

public final class UpdateBooksSetting
	extends EnumSetting<UpdateBooksSetting.UpdateBooks>
{
	public UpdateBooksSetting()
	{
		super("Update books",
			"Automatically updates the list of wanted books when a villager"
				+ " has learned to sell one of them.\n\n"
				+ "\u00a7lOff\u00a7r - Don't update the list.\n\n"
				+ "\u00a7lRemove\u00a7r - Remove the book from the list so"
				+ " that the next villager will learn a different book.\n\n"
				+ "\u00a7lPrice\u00a7r - Update the maximum price for the book"
				+ " so that the next villager has to sell it for a cheaper"
				+ " price.",
			UpdateBooks.values(), UpdateBooks.REMOVE);
	}
	
	public enum UpdateBooks
	{
		OFF("Off"),
		REMOVE("Remove"),
		PRICE("Price");
		
		private String name;
		
		private UpdateBooks(String name)
		{
			this.name = name;
		}
		
		public void update(BookOffersSetting wantedBooks, BookOffer offer)
		{
			int index = wantedBooks.indexOf(offer);
			
			switch(this)
			{
				case OFF:
				return;
				
				case REMOVE:
				wantedBooks.remove(index);
				break;
				
				case PRICE:
				if(offer.price() <= 1)
					wantedBooks.remove(index);
				else
					wantedBooks.replace(index, new BookOffer(offer.id(),
						offer.level(), offer.price() - 1));
				break;
			}
		}
		
		@Override
		public String toString()
		{
			return name;
		}
	}
}
