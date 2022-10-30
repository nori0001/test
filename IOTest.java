package test1;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class IOTest {
	public static void main(String[] args){

		FileInputStream fi = null;
		BufferedInputStream bi = null;
		ByteArrayOutputStream bos = null;

		try {
			fi = new FileInputStream("C:\\Users\\USER\\Desktop\\moji\\SHIFT_JIS2.txt");
			bi = new BufferedInputStream(fi);
			bos = new ByteArrayOutputStream();

			int tmp;

			// 1バイトずつ読み込み
			while((tmp=bi.read())!=-1){

				// 読み込んだデータをバイト配列に溜める。
				bos.write(tmp);

			}

			// バイト配列を書き出し
			byte bytetmp[] = bos.toByteArray();

			boolean rt = isUTF8(bytetmp);
			System.out.println("判定結果："+ rt);

			// Stringクラスのコンストラクタを利用して文字コード変換
			String st = new String(bytetmp,"UTF8");
			System.out.println(st);

		} catch (Exception e){
			e.printStackTrace();
		}finally{

			try {
				bi.close();
				bos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	// 半角英数以外がある場合、String(Unicode)に変換してgetBytesで元に戻したときの
	// 差分でUTF-8か判定する
	public static boolean isUTF8(byte[] src){
		try {

			System.out.println("変換前：16進数：" + toHex(src));

			// String(Unicode)に変換してgetBytesで元に戻す
			byte[] tmp = new String(src, "UTF8").getBytes("UTF8");

			System.out.println("再変換：16進数：" + toHex(tmp));

			return Arrays.equals(tmp, src);
		}
		catch(UnsupportedEncodingException e) {
			return false;
	    }
	}

	// 16進数へ変換
	public static String toHex(byte[] src){
		String result =
				IntStream.range(0, src.length)
				.mapToObj(i -> String.format("%02x", src[i]))
				.collect(Collectors.joining(" "));
		return result;
	}
}
