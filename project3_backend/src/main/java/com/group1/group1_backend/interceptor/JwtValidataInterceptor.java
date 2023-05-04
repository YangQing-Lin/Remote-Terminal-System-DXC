package com.group1.group1_backend.interceptor;

import com.alibaba.fastjson2.JSON;
import com.group1.group1_backend.comm.utils.JwtUtil;
import com.group1.group1_backend.comm.vo.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component
@Slf4j
public class JwtValidataInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;

    // JWT拦截器
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("X-Token");
        log.debug(request.getRequestURI() + "需要验证token：" + token);
        if (token != null) {
            try {
                jwtUtil.parseToken(token);
                log.debug(request.getRequestURI() + "验证通过");
                return true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        log.debug(request.getRequestURI() + "验证失败，禁止访问");
        // 将报错信息返回给前端
        response.setContentType("application/json;charset=utf-8");
        Result<Object> fail = Result.fail(20003, "jwt无效，请重新登陆");
        response.getWriter().write(JSON.toJSONString(fail));
        return false;  // 拦截
    }
}
