package com.toss.util;

import com.toss.bean.VerifyData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

/**
 * 验证码图片工具
 */
@Component
public class ImageUtil {
    private final Font font;
    private final Resource background;

    /**
     * 初始化资源
     */
    @Autowired
    public ImageUtil(ResourceLoader resourceLoader) {
        String location = ResourceLoader.CLASSPATH_URL_PREFIX + "pic/verify_background_image.jpg";
        background = resourceLoader.getResource(location);
        font = new Font("宋体", Font.BOLD, 20);
    }

    /**
     * 随机背景图片
     */
    public BufferedImage randomBackground() throws IOException {
        InputStream inputStream = background.getInputStream();
        BufferedImage src = ImageIO.read(inputStream);
        Iterator<ImageReader> it = ImageIO.getImageReadersByFormatName("jpg");
        ImageReader reader = it.next();
        ImageInputStream iis = ImageIO.createImageInputStream(inputStream);
        reader.setInput(iis, true);
        Random random = new Random();
        int x = random.nextInt(src.getWidth() - 320);
        int y = random.nextInt(src.getHeight() - 160);
        return src.getSubimage(x, y, 320, 160);
    }

    /**
     * 随机汉字
     */
    public String randomStr() {
        int hightPos, lowPos; // 定义高低位
        Random random = new Random();
        hightPos = (176 + Math.abs(random.nextInt(39)));// 获取高位值
        lowPos = (161 + Math.abs(random.nextInt(93)));// 获取低位值
        byte[] b = new byte[2];
        b[0] = (new Integer(hightPos).byteValue());
        b[1] = (new Integer(lowPos).byteValue());
        try {
            return new String(b, "GBK");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成随机验证字符
     */
    public java.util.List<VerifyData> randomChar(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        java.util.List<VerifyData> verifyDatas = new ArrayList<VerifyData>();
        Random random = new Random();
        Point point;
        VerifyData v;
        for (int i = 0; i < 4; i++) {
            point = getVerifyPoint(verifyDatas, random, width, height);
            v = rotateStr(random, point, image.getGraphics());
            verifyDatas.add(v);
        }
        return verifyDatas;
    }

    /**
     * 取不重复的坐标
     */
    private Point getVerifyPoint(java.util.List<VerifyData> verifyDatas, Random random, int width, int height) {
        int x = random.nextInt(width) % (280 - 30 + 1) + 30;
        int y = random.nextInt(height) % (120 - 30 + 1) + 30;
        for (VerifyData verifyData : verifyDatas) {
            if (verifyData.getPoint().distance(x, y) < 50) {
                return getVerifyPoint(verifyDatas, random, width, height);
            }
        }
        return new Point(x, y);
    }

    /**
     * 拉伸旋转字体
     */
    private VerifyData rotateStr(Random random, Point point, Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setFont(font);
        g2d.setColor(getColor());
        g2d.translate(point.x, point.y);
        g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        // 拉伸
        g2d.scale(0.7 + Math.random(), 0.7 + Math.random());
        double degree = random.nextInt(50);
        degree = random.nextBoolean() ? degree : -degree;
        // 旋转
        g2d.rotate(degree * Math.PI / 180);
        String str = randomStr();
        g2d.drawString(str, 0, 0);
        return new VerifyData(str, point);
    }

    /**
     * 获取随机颜色
     */
    private Color getColor() {
        int R = (int) (Math.random() * 255);
        int G = (int) (Math.random() * 255);
        int B = (int) (Math.random() * 255);
        return new Color(R, G, B);
    }
}