package ultimatedimension.fileIO;

import arc.files.Fi;
import arc.math.Rand;
import arc.util.Log;

import static ultimatedimension.UD.logInfo;
import static ultimatedimension.UD.udDirectory;

public class Password {

    public static void inputPassWord() {
        //writeFile();
        //w();
    }
    private static void w() {
        Fi readMe = udDirectory.child("这是留给你的话" + ".txt");
        readMe.writeString("你好，当你看到这条信息时，就说明你已经完成了最终的任务。");
    }

    private static void writeFile() {
        int num = 1;
		//boolean zzhh = true;
		//boolean yynn = false;
		/*
		System.out.println("是否要转换为二进制?默认是[y/n]");
		char zh = p.next().charAt(0);
		if(zh == 'n'|| yn == 'N') {
			zzhh = false;
		}*/
		while(num <= 1) {
			//这里生成十进制密钥
            Rand random = new Rand();
			//Random random = new Random();
			Fi mima = udDirectory.child("三万位服务器启动密钥" + num + ".txt");
			//try {
				while(mima.length() < 30000) {
					//int my = random.nextInt(10);
                    int my = random.nextInt(10);
					String str = my + "";
					mima.writeString(str, true);
				}
            Log.info(logInfo(("\n第" + num + "个三万位服务器启动密钥已随机生成！")));
			//这里由前面的十进制密钥转换成二进制密钥，由zzhh变量控制
			/*if(zzhh) {
				String str1 = "/storage/emulated/0/Download/javaDemo/bd/三万位服务器启动密钥" + num +".txt";
				byte b[]=fileToByte(str1);
				String file1Path = "/storage/emulated/0/Download/javaDemo/erjinzhi/三万位服务器启动密钥二进制文件" + num + ".txt";
				FileOutputStream file1OutputStream = null;
   				try {
   					file1OutputStream = new FileOutputStream(file1Path);
   					for(int i=0;i<b.length;i++) {
						String str = String.format("%08d",Integer.parseInt(Integer.toBinaryString(0xff & b[i])));
						//测试,新加二进制生成过程
						if(yynn) {
							file1OutputStream.write(str.getBytes());
							System.out.print(str);
						} else {
							file1OutputStream.write(str.getBytes());
						}
   					}
   				} catch(IOException e) {
   					e.printStackTrace();
   				} finally {
   					try {
   						fileOutputStream.close();
   					} catch(IOException e) {
   						e.printStackTrace();
   					}
   				}
   				System.out.println("\n第" + num + "个三万位服务器启动密钥二进制文件已转换！");
   			}*/
   			//当第一个文件已全部生成完毕，则加一以生成第二个文件
			num += 1;
		}
    }
}
