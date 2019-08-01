package io.summer.apigatewayzuul.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import javax.servlet.http.HttpServletRequest;

/**
 * @author summer
 */
public class TokenFilter  extends ZuulFilter {

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 0;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    String token = request.getParameter("token");
    if (null == token || token.isEmpty()) {
      ctx.setSendZuulResponse(false);
      ctx.setResponseStatusCode(401);
      ctx.setResponseBody("token is empty");
    }
    return null;
  }
}
