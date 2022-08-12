package pl.pabilo8.kraftwerk.utils;

import java.io.IOException;
import java.nio.file.*;

/**
 * taken from slf4j
 * http://www.slf4j.org/
 */
public class FileWatcher {

	private Thread thread;
	private WatchService watchService;

	public interface Callback {
		void run() throws Exception;
	}

	/**
	 * Starts watching a file and the given path and calls the callback when it is changed.
	 * A shutdown hook is registered to stop watching. To control this yourself, create an
	 * instance and use the start/stop methods.
	 */
	public static FileWatcher onFileChange(Path file, Callback callback) throws IOException
	{
		FileWatcher fileWatcher = new FileWatcher();
		fileWatcher.start(file, callback);
		Runtime.getRuntime().addShutdownHook(new Thread(fileWatcher::stop));
		return fileWatcher;
	}

	public void start(Path file, Callback callback) throws IOException {
		watchService = FileSystems.getDefault().newWatchService();
		Path parent = file.getParent();
		parent.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_DELETE);

		thread = new Thread(() -> {
			while (true) {
				WatchKey wk = null;
				try {
					wk = watchService.take();
					Thread.sleep(500); // give a chance for duplicate events to pile up
					for (WatchEvent<?> event : wk.pollEvents()) {
						Path changed = parent.resolve((Path) event.context());
						if (Files.exists(changed) && Files.isSameFile(changed, file)) {
							callback.run();
							break;
						}
					}
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					break;
				} catch (Exception ignored) {

				} finally {
					if (wk != null) {
						wk.reset();
					}
				}
			}
		});
		thread.start();
	}

	public void stop() {
		thread.interrupt();
		try {
			watchService.close();
		} catch (IOException ignored) {

		}
	}

}
