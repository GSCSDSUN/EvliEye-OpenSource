/*

 *



 */
package gg.evlieye.altmanager;

import java.awt.Component;
import java.awt.HeadlessException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import gg.evlieye.util.SwingUtils;

public final class ExportAltsFileChooser extends JFileChooser
{
	public static void main(String[] args)
	{
		SwingUtils.setLookAndFeel();
		
		int response = JOptionPane.showConfirmDialog(null,
			"This will create an unencrypted (plain text) copy of your alt list.\n"
				+ "Storing passwords in plain text is risky because they can easily be stolen by a virus.\n"
				+ "Store this copy somewhere safe and keep it outside of your Minecraft folder!",
			"Warning", JOptionPane.OK_CANCEL_OPTION,
			JOptionPane.WARNING_MESSAGE);
		
		if(response != JOptionPane.OK_OPTION)
			return;
		
		JFileChooser fileChooser = new ExportAltsFileChooser();
		
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setAcceptAllFileFilterUsed(false);
		
		FileNameExtensionFilter txtFilter =
			new FileNameExtensionFilter("TXT file (username:password)", "txt");
		fileChooser.addChoosableFileFilter(txtFilter);
		
		FileNameExtensionFilter jsonFilter =
			new FileNameExtensionFilter("JSON file", "json");
		fileChooser.addChoosableFileFilter(jsonFilter);
		
		if(fileChooser.showSaveDialog(null) != JFileChooser.APPROVE_OPTION)
			return;
		
		String path = fileChooser.getSelectedFile().getAbsolutePath();
		FileFilter fileFilter = fileChooser.getFileFilter();
		
		if(fileFilter == txtFilter && !path.endsWith(".txt"))
			path += ".txt";
		else if(fileFilter == jsonFilter && !path.endsWith(".json"))
			path += ".json";
		
		System.out.println(path);
	}
	
	@Override
	protected JDialog createDialog(Component parent) throws HeadlessException
	{
		JDialog dialog = super.createDialog(parent);
		dialog.setAlwaysOnTop(true);
		return dialog;
	}
	
}
