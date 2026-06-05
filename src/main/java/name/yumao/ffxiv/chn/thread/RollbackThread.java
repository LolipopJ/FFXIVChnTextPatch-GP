package name.yumao.ffxiv.chn.thread;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import name.yumao.ffxiv.chn.swing.PercentPanel;
import name.yumao.ffxiv.chn.swing.TextPatchPanel;

public class RollbackThread implements Runnable {

	private String resourceFolder;
	private TextPatchPanel textPatchPanel;
	private boolean showDialog;
	private boolean rollbackFont;
	private boolean rollbackText;

	public RollbackThread(String resourceFolder, TextPatchPanel textPatchPanel, boolean showDialog, boolean rollbackFont, boolean rollbackText) {
		this.resourceFolder = resourceFolder;
		this.textPatchPanel = textPatchPanel;
		this.showDialog = showDialog;
		this.rollbackFont = rollbackFont;
		this.rollbackText = rollbackText;
	}

	public void run() {
		Logger log = Logger.getLogger("GPLogger");
		try {
			this.textPatchPanel.rollbackTextButton.setEnabled(false);
			this.textPatchPanel.rollbackFontButton.setEnabled(false);
			log.info("[Rollback] Start rollback.");
			
			java.util.List<String> resourceNamesList = new java.util.ArrayList<>();
			if (this.rollbackFont) {
				resourceNamesList.add("000000.win32.dat0");
				resourceNamesList.add("000000.win32.index");
				resourceNamesList.add("000000.win32.index2");
			}
			if (this.rollbackText) {
				resourceNamesList.add("0a0000.win32.dat0");
				resourceNamesList.add("0a0000.win32.index");
				resourceNamesList.add("0a0000.win32.index2");
			}
			String[] resourceNames = resourceNamesList.toArray(new String[0]);
			
			boolean hasBackup = false;
			for (String resourceName : resourceNames) {
				File backupFile = new File("backup" + File.separator + resourceName);
				if (backupFile.exists() && backupFile.isFile()) {
					hasBackup = true;
					break;
				}
			}
			
			if (!hasBackup) {
				log.warning("[Rollback] No backup files found to restore.");
				if (this.showDialog) {
					JOptionPane.showMessageDialog(null, "<html><body>没有找到相关的备份文件，无法还原！</body></html>", "无备份", JOptionPane.WARNING_MESSAGE);
				}
				this.textPatchPanel.rollbackTextButton.setEnabled(true);
				this.textPatchPanel.rollbackFontButton.setEnabled(true);
				return;
			}
			
			int fileCount = 0;
			PercentPanel percentPanel = new PercentPanel("资源还原");
			percentPanel.progressShow("正在还原……", "");
			
			for (String resourceName : resourceNames) {
				File backupFile = new File("backup" + File.separator + resourceName);
				if (backupFile.exists() && backupFile.isFile()) {
					log.info(String.format("[Rollback] %s, %dKB", resourceName, backupFile.length() / 1024));
					File targetFile = new File(this.resourceFolder + File.separator + backupFile.getName());
					
					// 逐块读取以更新进度条
					try (java.io.FileInputStream fis = new java.io.FileInputStream(backupFile);
					     java.io.FileOutputStream fos = new java.io.FileOutputStream(targetFile)) {
						long totalBytes = backupFile.length();
						long copiedBytes = 0;
						byte[] buffer = new byte[1024 * 1024]; // 1MB 缓冲区
						int length;
						
						while ((length = fis.read(buffer)) > 0) {
							fos.write(buffer, 0, length);
							copiedBytes += length;
							
							// 计算单文件进度，加上之前已完成文件的基础进度
							double fileProgress = (double) copiedBytes / totalBytes;
							double overallProgress = ((double) fileCount + fileProgress) / resourceNames.length;
							percentPanel.percentShow(overallProgress);
						}
					} catch (Exception e) {
						log.severe("Failed to copy file: " + resourceName);
						e.printStackTrace();
					}
				}
				fileCount++;
			}
			
			percentPanel.percentShow(1.0);
			percentPanel.dispose();
			if (this.showDialog)
				JOptionPane.showMessageDialog(null, "<html><body>还原完毕</body></html>", "提示", -1);
			this.textPatchPanel.rollbackTextButton.setEnabled(true);
			this.textPatchPanel.rollbackFontButton.setEnabled(true);
			this.textPatchPanel.patchTextButton.setEnabled(true);
			this.textPatchPanel.patchFontButton.setEnabled(true);
			log.info("[Rollback] Rollback completed.");
		} catch (Exception exception) {
			JOptionPane.showMessageDialog(null, "<html><body>程序错误！</body></html>", "还原错误", 0);
			log.log(Level.SEVERE, "Error Messages:", exception);
			exception.printStackTrace();
		}
	}
}
