package cn.com.atnc.framework.shiro.session;

import javax.servlet.http.HttpServletRequest;

import cn.com.atnc.common.utils.IpUtils;
import cn.com.atnc.common.utils.ServletUtils;
import cn.com.atnc.common.utils.StringUtils;
import cn.com.atnc.project.monitor.online.domain.OnlineSession;
import cn.com.atnc.project.monitor.online.domain.UserOnline;
import cn.com.atnc.common.utils.IpUtils;
import cn.com.atnc.common.utils.ServletUtils;
import cn.com.atnc.common.utils.StringUtils;
import cn.com.atnc.project.monitor.online.domain.OnlineSession;
import cn.com.atnc.project.monitor.online.domain.UserOnline;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SessionContext;
import org.apache.shiro.session.mgt.SessionFactory;
import org.apache.shiro.web.session.mgt.WebSessionContext;
import org.springframework.stereotype.Component;
import cn.com.atnc.common.utils.ServletUtils;
import cn.com.atnc.common.utils.IpUtils;
import cn.com.atnc.common.utils.StringUtils;
import cn.com.atnc.project.monitor.online.domain.OnlineSession;
import cn.com.atnc.project.monitor.online.domain.UserOnline;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 自定义sessionFactory会话
 * 
 */
@Component
public class OnlineSessionFactory implements SessionFactory
{
    public Session createSession(UserOnline userOnline)
    {
        OnlineSession onlineSession = userOnline.getSession();
        if (StringUtils.isNotNull(onlineSession) && onlineSession.getId() == null)
        {
            onlineSession.setId(userOnline.getSessionId());
        }
        return userOnline.getSession();
    }

    @Override
    public Session createSession(SessionContext initData)
    {
        OnlineSession session = new OnlineSession();
        if (initData != null && initData instanceof WebSessionContext)
        {
            WebSessionContext sessionContext = (WebSessionContext) initData;
            HttpServletRequest request = (HttpServletRequest) sessionContext.getServletRequest();
            if (request != null)
            {
                UserAgent userAgent = UserAgent.parseUserAgentString(ServletUtils.getRequest().getHeader("User-Agent"));
                // 获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                // 获取客户端浏览器
                String browser = userAgent.getBrowser().getName();
                session.setHost(IpUtils.getIpAddr(request));
                session.setBrowser(browser);
                session.setOs(os);
            }
        }
        return session;
    }
}
