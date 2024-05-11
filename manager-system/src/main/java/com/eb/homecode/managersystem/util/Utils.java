package com.eb.homecode.managersystem.util;

import com.alibaba.fastjson.JSON;
import com.eb.homecode.managersystem.vo.ResultMessage;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Utils {
    public static void overrideResponse(HttpServletResponse response, boolean result, String message) {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        ResultMessage rm = new ResultMessage(result, message);
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            rm = new ResultMessage(false, "响应异常，请稍候重试");
        }

        out.println(JSON.toJSONString(rm));
        out.flush();
        out.close();
    }

    public static File loadFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }
}
