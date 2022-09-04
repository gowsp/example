package com.toss.controller;

import com.toss.bean.Response;
import com.toss.bean.VerifyContent;
import com.toss.bean.VerifyData;
import com.toss.bean.VerifyInfo;
import com.toss.util.ImageUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
public class Anonymous {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private ImageUtil imageUtil;
    @Autowired
    private HttpSession session;

    /**
     * 获取验证信息
     */
    @ResponseBody
    @RequestMapping(value = "/anonymous/verifyInfo")
    public Response getVerifyInfo(String key) throws IOException {
        BufferedImage img = imageUtil.randomBackground();
        List<VerifyData> datas = imageUtil.randomChar(img);
        List<String> list = getVerifyChar(key, datas);
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        ImageIO.write(img, "png", output);
        String uuid = getImageId(key);
        session.setAttribute(uuid, output);
        Response response = new Response();
        VerifyInfo verifyInfo = new VerifyInfo(uuid, list);
        response.setResult(verifyInfo);
        return response;
    }

    /**
     * 获取验证字符
     */
    private List<String> getVerifyChar(String key, List<VerifyData> datas) {
        long v = System.currentTimeMillis() % 3;
        List<VerifyData> dataToStore = new ArrayList<VerifyData>();
        List<String> list = new ArrayList<String>(datas.size());
        for (int i = 0; i < datas.size(); i++) {
            if (i != v) {
                VerifyData e = datas.get(i);
                dataToStore.add(e);
                list.add(e.getStrData());
            }
        }
        session.setAttribute(key, dataToStore);
        logger.info(dataToStore.toString());
        return list;
    }

    /**
     * 设置并清理图片ID信息
     */
    private String getImageId(String key) {
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String name = key + MediaType.IMAGE_PNG_VALUE;
        Object object = session.getAttribute(name);
        if (object != null) {
            // 清理旧ID
            session.removeAttribute(object.toString());
        }
        session.setAttribute(name, uuid);
        return uuid;
    }

    /**
     * 输出验证图片
     */
    @RequestMapping(value = "/anonymous/verifyImage")
    public void getVerifyImage(String id, HttpServletResponse response) throws IOException {
        response.setHeader("Cache-Control", "no-cache, no-store");
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        ByteArrayOutputStream output = (ByteArrayOutputStream) session.getAttribute(id);
        if (output == null) {
            response.setStatus(404);
            return;
        }
        response.getOutputStream().write(output.toByteArray());
        response.flushBuffer();
    }

    /**
     * 验证
     */
    @ResponseBody
    @RequestMapping(value = "/anonymous/verify", method = RequestMethod.POST)
    public Response verify(@RequestBody VerifyContent content) {
        boolean res = verdictValidCode(content);
        Response response = new Response();
        logger.info("验证结果：{}", res);
        response.setResult(res);
        return response;
    }

    private boolean verdictValidCode(VerifyContent verifyContent) {
        String mobile = verifyContent.getMobile();
        List<VerifyData> datas = (List<VerifyData>) session.getAttribute(mobile);
        if (datas == null) {
            return false;
        }
        List<Point> points = verifyContent.getPoints();
        if (points.size() != 3) {
            return false;
        }
        for (int i = 0; i < points.size(); i++) {
            Point point = points.get(i);
            VerifyData verifyData = datas.get(i);
            double distance = verifyData.getPoint().distance(point);
            if (distance > 30) {
                return false;
            }
        }
        return true;
    }
}
