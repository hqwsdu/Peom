package com.gqq.tangpoem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

public class PoemApplication extends Application {

	private static final String FileTag = "PoemApplicationFileSystem";
	public static final String POEMDB = "tangshi.db";
	public static final String CONTENT = "tangshiapp";
	private final static String DB_DIR = "/data" + Environment.getDataDirectory().getAbsolutePath() + File.separator + "com.gqq.tangpoem"
			+ File.separator + "databases" + File.separator;
	final static String SD_CARD_DIR = Environment.getExternalStorageDirectory().getPath() + File.separator + CONTENT + File.separator;

	final static String DB_PATH = DB_DIR + POEMDB;
	final static String SD_PATH = SD_CARD_DIR + File.separator + POEMDB;

	@Override
	public void onCreate() {

		initDB();
	}

	public static void backupDB() throws IOException {
		File dbfile = new File(DB_PATH);
		if (!dbfile.exists()) {
			throw new IOException("数据库文件不存在");
		}

		// 获取sd卡的路径，如果目录不存在，则新建一个目录
		Log.d(MainActivity.TAG_FILE, "SD_CARD_DIR:" + SD_CARD_DIR);
		File sdDir = new File(SD_CARD_DIR);
		if (!sdDir.exists())
			sdDir.mkdirs();

		// 新建文件
		File newfile = new File(SD_PATH);
		Log.d(MainActivity.TAG_FILE, "newfile:" + newfile);

		// 如果新文件存在，先删除该文件
		if (newfile.exists()) {
			Log.d(MainActivity.TAG_FILE, "file exists");
			newfile.delete();
			Log.d(MainActivity.TAG_FILE, "file has been deleted");
		}
		// 将文件copy到sd卡上(sd卡的tangshiapp目录下)
		nioBufferCopy(dbfile, newfile);
	}

	public static void restoreDB() throws IOException {
		// sd file
		File sdFile = new File(SD_PATH);
		if (!sdFile.exists()) {
			throw new IOException("数据库文件不存在");
		}
		Log.d(MainActivity.TAG_FILE, "sdfile:" + sdFile);

		// db file
		File dbfile = new File(DB_PATH);
		dbfile.mkdirs();
		Log.d(MainActivity.TAG_FILE, "dbfile:" + DB_PATH);

		// 将文件copy到data/data目录下(sd卡的tangshiapp目录下)
		if (dbfile.exists()) {
			Log.d(MainActivity.TAG_FILE, "file exists");
			dbfile.delete();
			Log.d(MainActivity.TAG_FILE, "file has been deleted");
		}
		nioBufferCopy(sdFile, dbfile);
	}

	private static void nioBufferCopy(File source, File target) throws IOException {
		FileChannel in = null;
		FileChannel out = null;
		FileInputStream inStream = null;
		FileOutputStream outStream = null;
		try {
			inStream = new FileInputStream(source);
			outStream = new FileOutputStream(target);
			in = inStream.getChannel();
			out = outStream.getChannel();
			ByteBuffer buffer = ByteBuffer.allocate(4096);
			while (in.read(buffer) != -1) {
				buffer.flip();
				out.write(buffer);
				buffer.clear();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			inStream.close();
			in.close();
			outStream.close();
			out.close();
		}
	}

	private void initDB() {

		Log.i(FileTag, DB_PATH);
		File db = new File(DB_PATH);

		if (!db.exists()) {
			// if (db.exists())
			// db.delete();
			Log.i(FileTag, "file is not exsits");
			try {
				db.getParentFile().mkdirs();
				db.createNewFile();
				InputStream is = getAssets().open(POEMDB);
				FileOutputStream fos = new FileOutputStream(db, false);
				int len = -1;
				byte[] buffer = new byte[1024];
				while ((len = is.read(buffer)) != -1) {
					fos.write(buffer, 0, len);
					fos.flush();
				}
				fos.close();
				is.close();
				Log.i(FileTag, "city.db已经被成功拷贝。地址为：" + DB_PATH);
			} catch (Exception e) {
				Log.i(FileTag, e.getMessage());
				e.printStackTrace();
				System.exit(0);
			}

		} else {
			Log.i(FileTag, "file has already exsited");
		}
	}
}
