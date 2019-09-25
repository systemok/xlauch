package com.xlauch.utils.util.file;

import java.io.*;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import com.xlauch.utils.util.date.DateUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;


/**
 * <p>
 * 	类描述 : 文件工具类
 * </p>
 *
 * @author 伊凡
 * @since 2017/11/27
 * @version 0.1
 */ 
 
public class FileUtil {
	
	private final static String format_date = "yyyy-MM-dd";
	
	/** *********************判断*************************** */

	/**
	 * 检查文件夹是否存在（true存在，false不存在）
	 * 
	 * @param path
	 * @return boolean
	 */
	public static boolean checkFolder(String path) {
		File file = new File(path);
		return file.isDirectory();
	}

	/**
	 * 检查文件是否存在（true存在，false不存在）
	 * 
	 * @param path
	 * @return boolean
	 */
	public static boolean checkFile(String path) {
		File file = new File(path);
		return file.exists();
	}

	/** *********************返回字符串*************************** */

	/**
	 * 生成指定位数的字符串
	 * 
	 * @param rand
	 * @param pos
	 * @return String
	 */
	public static String toStringNum(Integer rand, Integer pos) {
		String Sn = String.valueOf(rand);
		if (Sn == null) {
			return "";
		}
		Integer i = pos - Sn.length();
		while (i > 0) {
			Sn = "0" + Sn;
			i--;
		}
		return Sn;
	}

	/**
	 * 根据日前生成字符串
	 * 
	 * @param pos
	 * @return String
	 */
	public static String toStringNum(Integer pos) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date currentTime = new Date();// 得到当前系统时间
		String FILE_ADDR = (String) formatter.format(currentTime);
		Random random = new Random();
		Integer rand = random.nextInt(999999999);
		String Sn = String.valueOf(rand);
		if (Sn == null) {
			return "";
		}
		Integer i = pos - Sn.length();
		while (i > 0) {
			Sn = "0" + Sn;
			i--;
		}
		return FILE_ADDR + Sn;
	}

	/**
	 * 根据日前生成字符串
	 * 
	 * @param pos
	 *            随机几位数
	 * @return String
	 */
	public static String getRandNumString(Integer pos) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		Date currentTime = new Date();// 得到当前系统时间
		String FILE_ADDR = (String) formatter.format(currentTime);
		Random random = new Random();
		Integer rand = random.nextInt(999999999);
		String Sn = String.valueOf(rand);
		if (Sn == null) {
            return "";
        }
		Integer i = pos - Sn.length();
		while (i > 0) {
			Sn = "0" + Sn;
			i--;
		}
		return FILE_ADDR + Sn;
	}

	/**
	 * 返回随机临时文件名
	 * 
	 * @param ext
	 *            后缀名 如"doc"
	 * @return String
	 */
	public static synchronized String getFileTempNameByExt(String ext) {
		if (ext == null){
			ext = "txt";
		}
		Date dt = new Date(System.currentTimeMillis());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = fmt.format(dt);
		Random rand = new Random();// 生成随机数
		int random = rand.nextInt();
		fileName = fileName + "_"
				+ String.valueOf(random > 0 ? random : (-1) * random) + ext;
		return fileName;
	}

	/**
	 * 返回随机临时文件名
	 * 
	 * @param filename
	 *            文件全名
	 * @return String
	 */
	public static synchronized String getFileTempName(String filename) {
		String ext = "";
		if (filename.lastIndexOf(".") != -1) {
			ext = filename.substring(filename.lastIndexOf("."));
		}
		Date dt = new Date(System.currentTimeMillis());
		SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		String fileName = fmt.format(dt);
		Random rand = new Random();// 生成随机数
		int random = rand.nextInt();
		fileName = fileName + "_"
				+ String.valueOf(random > 0 ? random : (-1) * random) + ext;
		return fileName;
	}

	/**
	 * 根据文件名返回后缀名
	 * 
	 * @param fileName
	 * @return String
	 */
	public static String getPostFix(String fileName) {
		String x[] = fileName.split("\\.");
		if (x.length < 1){
			return "";
		}
		return x[x.length - 1];
	}

	/**
	 * 获取文件大小
	 * 
	 * @param filesize
	 * @return String
	 */
	public static String FormatFileSize(long filesize) {
		DecimalFormat formater = new DecimalFormat(".##");
		formater.applyPattern(".00");
		BigDecimal filesizeBD = new BigDecimal(filesize);
		BigDecimal num = null;
		if (filesize < 0) {
			return "";
		} else if (filesize >= 1024 * 1024 * 1024) {
			// 文件大小大于或等于1024MB
			num = new BigDecimal(1024 * 1024 * 1024);
			filesizeBD = filesizeBD.divide(num, 2, BigDecimal.ROUND_HALF_UP);
			return formater.format(filesizeBD) + " GB";
		} else if (filesize >= 1024 * 1024) {
			// 文件大小大于或等于1024KB
			num = new BigDecimal(1024 * 1024);
			filesizeBD = filesizeBD.divide(num, 2, BigDecimal.ROUND_HALF_UP);
			return formater.format(filesizeBD) + " MB";
		} else if (filesize >= 1024) {
			// 文件大小大于等于1024bytes
			num = new BigDecimal(1024);
			filesizeBD = filesizeBD.divide(num, 2, BigDecimal.ROUND_HALF_UP);
			return formater.format(filesizeBD) + " KB";
		} else {
			return formater.format(filesizeBD) + " bytes";
		}
	}

	/** *********************返回集合*************************** */

	/**
	 * 遍历文件夹
	 * 
	 * @param path
	 */
	public static List<String> getFolderName(String path) {
		List<String> list = new ArrayList<String>();
		try {
			File dir = new File(path);
			String[] fs = dir.list();
			if (fs != null && fs.length > 0) {
				for (int i = 0; i < fs.length; i++) {
					if (fs[i].indexOf(".") != -1) {
					} else {
						list.add(fs[i] + "");
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	/**
	 * 遍历文件(集合中只有文件名)
	 * 
	 * @param path
	 */
	public static List<String> getFileName(String path) {
		List<String> list = new ArrayList<String>();
		File d = new File(path);// 建立当前目录中文件的File对象
		File[] fl = d.listFiles();// 取得目录中所有文件的File对象数组
		if (fl != null && fl.length > 0) {
			for (int i = 0; i < fl.length; i++) {
				// 目录下的文件：
				File e = fl[i];
				if (e.isFile()) {
					list.add(e.getName() + "");
				}
			}
		}
		return list;
	}

	/**
	 * 遍历文件(集合中有文件对象)
	 * @param path
	 * @return List<File>
	 */
	public static List<File> getFileList(String path) {
		List<File> list = new ArrayList<File>();
		File file = new File(path);
		if (file.exists() && file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				list.add(file2);
			}
		}
		return list;
	}

	/** *********************创建*************************** */

	/**
	 * 创建文件夹
	 * 
	 * @param path
	 *            路径
	 */
	public static void createFolder(String path) {
		File folder = new File(path);
		if (!folder.exists()) {
			folder.mkdirs();
		}
	}

	/**
	 * 按时间(年月)来创建分时文件夹
	 * 
	 * @param Path
	 * @return String
	 */
	public static String makeDateDir(String Path) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format_date);
		String str = formatter.format(date);
		File dir = null;
		Path = Path + File.separator + str + File.separator;
		dir = new File(Path);
		if (!dir.exists()) {
			dir.mkdirs(); // 检查dir目录是否存在,没有则建立dir目录
		}
		return Path;
	}

	
	/**
	 * 追加当前日期
	 * @param path
	 * @return
	 */
	public static String appendCurrentDate(String path) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format_date);
		String str = formatter.format(date);
		path = path + "/" + str + "/";
		return path ;
	}
	

	/**
	 * 写入文件
	 * 
	 * @param dirStart
	 *            复制起始目录文件
	 * @param dirEnd
	 *            复制目标目录文件
	 */
	public static void writeFile(String dirStart, String dirEnd) {
		try {
			File file = new File(dirEnd);
			if (!file.exists()) {
				BufferedInputStream bis = new BufferedInputStream(
						new FileInputStream(dirStart));
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(dirEnd));
				int i = 0;
				byte[] b = new byte[1024];
				while ((i = bis.read(b, 0, b.length)) != -1) {
					bos.write(b, 0, i);
				}
				bos.flush();
				bos.close();
				bis.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 复制文件(用流)开始路径文件没删除
	 * 
	 * @param dirStart
	 *            开始路径(E:\\entProcessFile\\FA5DD6D4459E4B60BFBFD212D4D17AE2)
	 * @param dirEnd
	 *            目的路径(E:\\entOfficialFile\\AAADD6D4459E4B60BFBFD212D4D17AE2)
	 * @return String
	 */
	public static String fileCopyIO(String dirStart, String dirEnd) {
		try {
			if (dirStart == null || "".equals(dirStart) || dirEnd == null
					|| "".equals(dirEnd)) {
				return "路径为空!";
			}
			FileUtil.createFolder(dirEnd);
			List<File> fileList = FileUtil.getFileList(dirStart);
			if (fileList == null || fileList.size() <= 0) {
				return "开始路径下没有文件!";
			}
			for (int i = 0; i < fileList.size(); i++) {
				String filename = fileList.get(i).getName();
				String fileStart = dirStart + "\\" + filename;
				String fileEnd = dirEnd + "\\" + filename;
				FileUtil.writeFile(fileStart, fileEnd);
			}
		} catch (Exception e) {
			return "复制文件异常!";
		}
		return "success";
	}

	/**
	 * 复制文件(用File.renameTo)开始路径文件删除
	 * 
	 * @param dirStart
	 *            开始路径(E:\\entProcessFile\\FA5DD6D4459E4B60BFBFD212D4D17AE2)
	 * @param dirEnd
	 *            目的路径(E:\\entOfficialFile\\AAADD6D4459E4B60BFBFD212D4D17AE2)
	 * @return String
	 */
	public static String fileCopy(String dirStart, String dirEnd) {
		try {
			if (dirStart == null || "".equals(dirStart) || dirEnd == null
					|| "".equals(dirEnd)) {
				return "路径为空!";
			}
			// 移动时如果E:\\entOfficialFile文件夹不存在，无法移动，要初始好最终文件夹前一级
			String[] dirArr = dirEnd.split("\\\\");
			if (dirArr == null || dirArr.length <= 0) {
				return "路径错误!";
			}
			String pathEnd = "";
			for (int i = 0; i < dirArr.length - 1; i++) {
				pathEnd += dirArr[i] + File.separator;
			}
			FileUtil.createFolder(pathEnd);
			// 移动文件
			File file = new File(dirStart);
			File file2 = new File(dirEnd);
			file.renameTo(file2);
		} catch (Exception e) {
			return "复制文件异常";
		}
		return "success";
	}

	/** *********************删除*************************** */

	/**
	 * 递归删除文件
	 * 
	 * @param f
	 */
	public static void deleteFile(File f) {
		if (f.isDirectory()) {
			File[] list = f.listFiles();
			for (int i = 0; i < list.length; i++) {
				deleteFile(list[i]);
			}
		}
		f.delete();
	}

	/**
	 * 通过文件路径删除文件
	 * 
	 * @param path
	 * @return boolean
	 */
	public static boolean delFileByPath(String path) {
		if (path == null || "".equals(path)) {
			return true;
		}
		File file = new File(path);
		if (file.exists()) {
			if (file.delete()) {
				return true;
			} else {
				return false;
			}
		}
		return true;
	}
	
	
   	
   	/**
   	 * 删除临时生成的文件
   	 * @param rootPath		指定根目录
   	 * @param days			删除超出天数
   	 * @throws Exception   	异常
   	 */
	public static void deleteTmpeFile(String rootPath , int days) throws Exception {
		File root = new File(rootPath);
		if(root.exists() && root.isDirectory()){
			for(File file : root.listFiles()) {
				String fileName = file.getName() ;
				//验证是否是日期格式的文件夹
				try {
					SimpleDateFormat sdf = new SimpleDateFormat(format_date);
					sdf.parse(fileName);
				} catch (Exception e) {
					continue ;
				}
				//int day = DateUtils.getDays(fileName , FxzUtils.getsysdate());
				
				
				int day = DateUtil.daysBetween(fileName, DateUtil.getDate());
				if(day > days){
					deleteFile(file);
				}
			}
		}
	}
	
	/**
	 * 文件名存在时，自动重命名
	 * @param uploadPath
	 * @param name
	 * @return
	 */
	public static String getFileReName(String uploadPath , String name) {
		// 文件名存在时，自动重命名
		File f = new File(uploadPath, name);
		int count = 1;
		// 获取文件后缀
		String ext = FilenameUtils.getExtension(name);
		String rename = name;
		while (f.exists()) {
			if (StringUtils.isNotEmpty(ext)) {
				rename = name.substring(0, name.lastIndexOf("."));
				rename = rename + "(" + count + ")." + ext;
			} else {
				rename = rename + "(" + count + ")";
			}

			f = new File(uploadPath, rename);
			count++;
		}
		
		return rename ;
	}

	/**
	 * 获取文件编码
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String getEncodeing(String path) throws Exception {
		/*String encoding = "GBK" ;
		File file = new File(path);
		InputStream in= new java.io.FileInputStream(file);
		byte[] b = new byte[3];
		in.read(b);
		in.close();
		if (b[0] == -17 && b[1] == -69 && b[2] == -65) {
			return "UTF-8" ;
		}
		return encoding ;*/
		return EncodingDetect.getEncode(path);
	}

	/**
	 * 读取文件内容
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static String readFile(String path) throws Exception {
		String encoding = getEncodeing(path) ;
		return readFile(path , encoding);
	}
	/**
	 * 读取文件内容
	 * @param path
	 * @param encoding
	 * @return
	 * @throws Exception
	 */
	public static String readFile(String path , String encoding) throws Exception {
		if (StringUtils.isEmpty(encoding)) {
			encoding = FileUtil.getEncodeing(path) ;
		}
		return FileUtils.readFileToString(new File(path) , encoding);
	}

	/**
	 * 写入文件
	 * @param path
	 * @param content
	 * @param encoding
	 * @throws Exception
	 */
	public static void writeFile(String path , String content , String encoding) throws Exception {
		File file = new File(path);
		if(!file.exists()){
			file.mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(file);
		OutputStreamWriter out = new OutputStreamWriter(fos, encoding);
		out.write(content);
		out.flush();

		fos.close();
		out.close();
	}
}
