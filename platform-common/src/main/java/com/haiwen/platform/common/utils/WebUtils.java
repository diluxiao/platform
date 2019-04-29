package com.haiwen.platform.common.utils;

import com.haiwen.platform.common.dto.ResultData;
import com.haiwen.platform.common.exception.CodeMsg;
import com.haiwen.platform.common.exception.PlatformException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * Web工具
 *
 * @author tangjialin on 2017-06-14 0014.
 */
@Slf4j
public final class WebUtils {

    /**
     * 不可实例化
     */
    private WebUtils() {
    }

    /**
     * 获取HttpServletRequest
     *
     * @return HttpServletRequest
     */
    public static HttpServletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return requestAttributes != null && requestAttributes instanceof ServletRequestAttributes ? ((ServletRequestAttributes) requestAttributes).getRequest() : null;
    }

    /**
     * 获取HttpServletResponse
     *
     * @return HttpServletResponse
     */
    public static HttpServletResponse getResponse() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        return requestAttributes != null && requestAttributes instanceof ServletRequestAttributes ? ((ServletRequestAttributes) requestAttributes).getResponse() : null;
    }

    /**
     * 判断是否为AJAX请求
     *
     * @param request HttpServletRequest
     * @return 是否为AJAX请求
     */
    public static boolean isAjaxRequest(HttpServletRequest request) {
        String header = request.getHeader("X-Requested-With");
        return "XMLHttpRequest".equalsIgnoreCase(header);
    }

    /**
     * 获取服务器URL
     *
     * @param request
     * @return 返回服务器基础全路径地址, 末尾不含"/" <br>如:http://www.xxx.com/xxx
     */
    public static String getServerPath(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(128);
        sb.append(request.getScheme()).append("://").append(request.getServerName());
        // 端口为80时不添加端口(仅仅为了url美观而已)
        if (request.getServerPort() != 80) { sb.append(":").append(request.getServerPort()); }
        String contextPath = request.getContextPath();
        if (contextPath.endsWith("/")) {
            sb.append(contextPath.substring(0, contextPath.length() - 1));
        }
        return sb.toString();
    }

    /**
     * 获取请求的完整URL
     *
     * @param request
     * @return 返回服务器基础全路径地址 <br>如:http://www.xxx.com/xxx?sss
     */
    public static String getRequestFullUrl(HttpServletRequest request) {
        String serverPath = getServerPath(request);
        String requestURI = request.getRequestURI();
        String queryString = request.getQueryString();
        queryString = queryString == null ? "" : queryString;
        int len = serverPath.length() + requestURI.length() + queryString.length() + 1;
        StringBuilder sb = new StringBuilder(len);
        sb.append(serverPath).append(requestURI);
        if (!queryString.isEmpty()) {
            sb.append("?").append(queryString);
        }
        return sb.toString();
    }

    /**
     * 获取request请求的IP地址
     *
     * @param request request
     * @return IP地址
     */
    public static String getRequestIp(HttpServletRequest request) {
        String IP = request.getHeader("x-forwarded-for");
        if (IP == null || IP.length() == 0 || "unknown".equalsIgnoreCase(IP)) {
            IP = request.getHeader("Proxy-Client-IP");
        }
        if (IP == null || IP.length() == 0 || "unknown".equalsIgnoreCase(IP)) {
            IP = request.getHeader("WL-Proxy-Client-IP");
        }
        if (IP == null || IP.length() == 0 || "unknown".equalsIgnoreCase(IP)) {
            IP = request.getRemoteAddr();
        }
        String[] ips = IP == null || IP.equals("0:0:0:0:0:0:0:1") ? new String[]{"127.0.0.1"} : IP.split(",");
        return ips.length == 0 ? "127.0.0.1" : ips[0];
    }

    /**
     * 获取客户IP地址
     *
     * @param request
     * @return 返回客户IP
     */
    public static String getRequestFullIp(HttpServletRequest request) {
        StringBuilder sb = new StringBuilder(64);
        sb.append(request.getRemoteAddr());
        sb.append("|").append(request.getHeader("x-forwarded-for"));
        sb.append("|").append(request.getHeader("Proxy-Client-IP"));
        sb.append("|").append(request.getHeader("WL-Proxy-Client-IP"));
        return sb.toString();
    }

    /**
     * 获取浏览器代理信息
     *
     * @param request 有效的HTTP请求
     * @return 返回代理信息
     */
    public static String getUserAgent(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent");
        return userAgent == null ? "" : userAgent;
    }

    /**
     * 添加cookie
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param name     Cookie名称
     * @param value    Cookie值
     * @param maxAge   有效期(单位: 秒)
     * @param path     路径
     * @param domain   域
     * @param secure   是否启用加密
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge, String path, String domain, Boolean secure) {
        try {
            name = URLEncoder.encode(name, "UTF-8");
            value = URLEncoder.encode(value, "UTF-8");
            Cookie cookie = new Cookie(name, value);
            if (maxAge != null) {
                cookie.setMaxAge(maxAge);
            }
            if (StringUtils.isNotEmpty(path)) {
                cookie.setPath(path);
            }
            if (StringUtils.isNotEmpty(domain)) {
                cookie.setDomain(domain);
            }
            if (secure != null) {
                cookie.setSecure(secure);
            }
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new PlatformException(e.getMessage(), e);
        }
    }

    /**
     * 添加cookie
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param name     Cookie名称
     * @param value    Cookie值
     * @param maxAge   有效期(单位: 秒)
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, Integer maxAge) {
        addCookie(request, response, name, value, maxAge, "/", request.getHeader(""), null);
    }

    /**
     * 添加cookie
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param name     Cookie名称
     * @param value    Cookie值
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value) {
        String domain = request.getServerName();
        domain = domain.startsWith("www.") ? domain.substring(4) : domain;
        addCookie(request, response, name, value, null, "/", domain, null);
    }

    /**
     * 获取cookie
     *
     * @param request HttpServletRequest
     * @param name    Cookie名称
     * @return Cookie值，若不存在则返回null
     */
    public static String getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            try {
                name = URLEncoder.encode(name, "UTF-8");
                for (Cookie cookie : cookies) {
                    if (name.equals(cookie.getName())) {
                        return URLDecoder.decode(cookie.getValue(), "UTF-8");
                    }
                }
            } catch (UnsupportedEncodingException e) {
                throw new PlatformException(e.getMessage(), e);
            }
        }
        return null;
    }

    /**
     * 移除cookie
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param name     Cookie名称
     */
    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        try {
            name = URLEncoder.encode(name, "UTF-8");
            Cookie cookie = new Cookie(name, null);
            cookie.setMaxAge(0);
            cookie.setPath("/");
            response.addCookie(cookie);
        } catch (UnsupportedEncodingException e) {
            throw new PlatformException(e.getMessage(), e);
        }
    }

    /**
     * 服务器转发
     *
     * @param path     发转地址
     * @param request  HTTP请求信息
     * @param response HTTP响应信息
     * @return 返回异常, 如果没有异常则为空
     */
    public static Exception forward(String path, HttpServletRequest request, HttpServletResponse response) {
        Exception exception = null;
        String contextPath = request.getContextPath();
        if (!contextPath.endsWith("/")) {
            contextPath += "/";
        }
        path = path.startsWith("/") ? path.substring(1) : path;
        path = contextPath + path;
        try {
            request.getRequestDispatcher(path).forward(request, response);
        } catch (ServletException e) {
            exception = e;
            log.error("路径错误:{}", path, e);
        } catch (IOException e) {
            exception = e;
            log.error("IO异常:{}", path, e);
        }
        return exception;
    }

    /**
     * 错误信息包装
     *
     * @param redirectAction 重定向地址
     * @param code           错误码
     * @param message        错误信息
     * @param request        HttpServletRequest
     * @param response       HttpServletResponse
     */
    public static void errorPackage(String redirectAction, int code, String message, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (isAjaxRequest(request)) {
                ResultData responseMessage = ResultData.errot(new CodeMsg(code, message));
                response.setHeader("Content-Type", "application/json; charset=UTF-8");
                response.setHeader("Location", redirectAction);
                response.getOutputStream().write(responseMessage.toString().getBytes("UTF-8"));
                response.getOutputStream().close();
            } else {
                String contextPath = request.getContextPath();
                response.sendRedirect(contextPath + redirectAction);
            }
        } catch (Exception e) {
            log.error("错误信息包装异常", e);
        }
    }

    /**
     * 数据写出
     *
     * @param resData  写出的数据
     * @param response
     */
    public static void write(String resData, HttpServletResponse response) {
        byte[] bytes = null;
        try {
            bytes = resData.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            log.error("不支持的编码格式", e);
        }
        write(bytes, response);
    }

    /**
     * 数据写出
     *
     * @param resData  写出的数据
     * @param response
     */
    public static void write(byte[] resData, HttpServletResponse response) {
        if (resData == null) { return; }
        response.setContentLength(resData.length);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            out.write(resData);
        } catch (IOException e) {
            log.error("信息响应异常", e);
        } finally {
            IOUtils.closeQuietly(out);
        }
    }

    /**
     * 数据写出
     *
     * @param is       写出的数据
     * @param length   数据长度
     * @param response
     */
    public static void write(InputStream is, int length, HttpServletResponse response) {
        if (is == null || length <= 0) { return; }
        response.setContentLength(length);
        OutputStream out = null;
        try {
            out = response.getOutputStream();
            byte[] bytes = new byte[1024 * 8];
            int len = -1;
            while ((len = is.read(bytes)) != -1) {
                out.write(bytes, 0, len);
            }
        } catch (IOException e) {
            log.error("信息响应异常", e);
        } finally {
            IOUtils.closeQuietly(out);
            IOUtils.closeQuietly(is);
        }
    }

    /**
     * 数据写出
     *
     * @param is       写出的数据
     * @param fileName 文件名
     * @param length   数据长度
     * @param response
     */
    public static void write(InputStream is, String fileName, int length, HttpServletResponse response) {
        if (length <= 0) { return; }
        response.reset();
        response.setDateHeader("Expires", 0);
        response.addHeader("Cache-Control", "no-cache");
        response.addHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "No-cache");
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            log.error("不支持的编码格式", e);
        }
        response.setContentType("application/octet-stream;charset=UTF-8");
        write(is, length, response);
    }
}