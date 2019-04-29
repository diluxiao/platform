package com.haiwen.platform.common.utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 验证码生成
 * Created by tangjialin on 2016-08-17 0017.
 */
public class AuthImg {
	public static final AuthImg utils = new AuthImg();
	private final Font mFont = new Font("Arial Black", Font.PLAIN, 16);
	private final int IMG_WIDTH = 70;
	private final int IMG_HEIGTH = 22;
	private final char[] codeSequence = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	private AuthImg() {}

	private Color getRandColor(int fc, int bc) {
		Random random = new Random();
		if (fc > 255) fc = 255;
		if (bc > 255) bc = 255;
		int r = fc + random.nextInt(bc - fc);
		int g = fc + random.nextInt(bc - fc);
		int b = fc + random.nextInt(bc - fc);
		return new Color(r, g, b);
	}

	/**
	 * 生成图片
	 */
	public BufferedImage images(String sRand) {
		BufferedImage image = new BufferedImage(IMG_WIDTH, IMG_HEIGTH, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.getGraphics();
		Random random = new Random();
		g.setColor(getRandColor(200, 250));
		g.fillRect(1, 1, IMG_WIDTH - 1, IMG_HEIGTH - 1);
		g.setColor(new Color(102, 102, 102));
		g.drawRect(0, 0, IMG_WIDTH - 1, IMG_HEIGTH - 1);
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 80; i++) {
			int x = random.nextInt(IMG_WIDTH - 1);
			int y = random.nextInt(IMG_HEIGTH - 1);
			int xl = random.nextInt(6) + 1;
			int yl = random.nextInt(12) + 1;
			g.drawLine(x, y, x + xl, y + yl);
		}
		g.setColor(getRandColor(160, 200));
		for (int i = 0; i < 80; i++) {
			int x = random.nextInt(IMG_WIDTH - 1);
			int y = random.nextInt(IMG_HEIGTH - 1);
			int xl = random.nextInt(12) + 1;
			int yl = random.nextInt(6) + 1;
			g.drawLine(x, y, x - xl, y - yl);
		}
		g.setFont(mFont);

		for (int i = 0, len = sRand.length(); i < len; i ++) {
			g.setColor(new Color(20 + random.nextInt(110)
					, 20 + random.nextInt(110)
					, 20 + random.nextInt(110)));
			g.drawString(String.valueOf(sRand.charAt(i)), 15 * i + 10, 15);
		}
		g.dispose();
		return image;
	}

	/**
	 * 生成指定长度随机字符串
	 * @param len 需要生成的字符串长度
	 * @return 返回生成的字符串
	 */
	public String getRandomChar(int len) {
		Random random = new Random();
		StringBuilder rand = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			rand.append(codeSequence[random.nextInt(codeSequence.length)]);
		}
		return rand.toString().toUpperCase();
	}

	/**
	 * 生成长度为4的随机字符串
	 * @return 返回生成的字符串
	 */
	public String getRandomChar() {
		return getRandomChar(4);
	}
}