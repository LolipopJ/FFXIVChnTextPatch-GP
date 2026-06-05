package name.yumao.ffxiv.chn.thread;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import name.yumao.ffxiv.chn.replace.ReplaceEXDF;
import name.yumao.ffxiv.chn.replace.ReplaceFont;
import name.yumao.ffxiv.chn.swing.PercentPanel;
import name.yumao.ffxiv.chn.swing.TextPatchPanel;
import name.yumao.ffxiv.chn.util.res.Config;

public class ReplaceThread implements Runnable {
	private String resourceFolder;
	private TextPatchPanel textPatchPanel;
	// private List<TeemoUpdateVo> updates;
	// private String slang; // not used
	private String flang;
	private boolean patchFont;
	private boolean patchText;

	public ReplaceThread(String resourceFolder, TextPatchPanel textPatchPanel, boolean patchFont, boolean patchText) {
		this.resourceFolder = resourceFolder;
		this.textPatchPanel = textPatchPanel;
		this.flang = Config.getProperty("FLanguage");
		this.patchFont = patchFont;
		this.patchText = patchText;
	}

	public static boolean hasCsvFiles(String directoryPath) {
		File directory = new File(directoryPath);
		if (!directory.exists() || !directory.isDirectory()) {
			return false;
		}

		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					if (hasCsvFiles(file.getAbsolutePath())) {
						return true;
					}
				} else if (file.isFile() && file.getName().toLowerCase().endsWith(".csv")) {
					return true;
				}
			}
		}

		return false;
	}

	public void run() {
		Logger log = Logger.getLogger("GPLogger");

		try {
			this.textPatchPanel.patchTextButton.setEnabled(false);
			this.textPatchPanel.patchFontButton.setEnabled(false);
			PercentPanel percentPanel = new PercentPanel("汉化进度");
			if (this.patchFont) {
				new ReplaceFont(this.resourceFolder + File.separator + "000000.win32.index",
						"resource" + File.separator + "font", percentPanel).replace();
			} else {
				log.info("Skip replacing font files.");
			}
			if (this.patchText) {
				if ((this.flang.equals("CSV")) && hasCsvFiles("resource" + File.separator + "rawexd")) {
					log.info("Start patching with CSV files.");
					(new ReplaceEXDF(this.resourceFolder + File.separator + "0a0000.win32.index",
							"resource" + File.separator + "rawexd" + File.separator + "Achievement.csv", percentPanel))
							.replace();
				} else if (!(this.flang.equals("CSV"))
						&& (new File("resource" + File.separator + "text" + File.separator + "0a0000.win32.index"))
								.exists()) {
					log.info("Start patching with 0a0000 files.");
					(new ReplaceEXDF(this.resourceFolder + File.separator + "0a0000.win32.index",
							"resource" + File.separator + "text" + File.separator + "0a0000.win32.index", percentPanel))
							.replace();
				} else {
					System.out.println("No resource files detected!");
					log.severe("No resource files detected!");
				}
			} else {
				log.info("Skip replacing text.");
			}
			JOptionPane.showMessageDialog(null, "<html><body>汉化完毕</body></html>", "提示", -1);
			log.info("Patch finished.");
			percentPanel.dispose();
			this.textPatchPanel.patchTextButton.setEnabled(true);
			this.textPatchPanel.patchFontButton.setEnabled(true);
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "<html><body>程序错误！</body></html>", "汉化错误", 0);
			log.severe("Patch failed!");
			log.log(Level.SEVERE, "Error Messages:", exception);
			exception.printStackTrace();
		}
	}
}
