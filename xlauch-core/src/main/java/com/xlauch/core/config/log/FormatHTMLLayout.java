package com.xlauch.core.config.log;

import java.text.SimpleDateFormat;

import org.apache.log4j.HTMLLayout;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.helpers.Transform;
import org.apache.log4j.spi.LocationInfo;
import org.apache.log4j.spi.LoggingEvent;

/**
 * <p>
 * 类描述    : 定义输出html格式日志
 * </p>
 *
 * @author 伊凡
 * @version 0.1
 * @since 2017/11/15
 */
public class FormatHTMLLayout extends HTMLLayout {

    public FormatHTMLLayout() {
    }

    protected final int BUF_SIZE = 256;

    protected final int MAX_CAPACITY = 1024;

    static String TRACE_PREFIX = "<br>&nbsp;&nbsp;&nbsp;&nbsp;";

    // output buffer appended to when format() is invoked
    private StringBuffer sbuf = new StringBuffer(BUF_SIZE);

    String title="??2???";

    /**
     * A string constant used in naming the option for setting the the HTML
     * document title. Current value of this string constant is <b>Title</b>.
     */
    public static final String TITLE_OPTION = "Title";

    // Print no location info by default
    boolean locationInfo = true;

    @Override
    public String format(LoggingEvent event) {
        if (sbuf.capacity() > MAX_CAPACITY) {
            sbuf = new StringBuffer(BUF_SIZE);
        } else {
            sbuf.setLength(0);
        }
        sbuf.append(Layout.LINE_SEP + "<tr>" + Layout.LINE_SEP);

/*      sbuf.append("<td>");
        sbuf.append(String.valueOf(i));
        sbuf.append("</td>" + Layout.LINE_SEP);
*/
        sbuf.append("<td>");
        sbuf.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new java.util.Date()));
        sbuf.append("</td>" + Layout.LINE_SEP);

    /*  String escapedThread = Transform.escapeTags(event.getThreadName());
        sbuf.append("<td title=\"" + escapedThread + " thread\">");
        sbuf.append(escapedThread);
        sbuf.append("</td>" + Layout.LINE_SEP);
    */
        sbuf.append("<td title=\"??±?>");
        if (event.getLevel().equals(Level.FATAL)) {
            sbuf.append("<font color=\"#339933\">");
            sbuf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
            sbuf.append("</font>");
        } else if (event.getLevel().isGreaterOrEqual(Level.WARN)) {
            sbuf.append("<font color=\"#993300\"><strong>");
            sbuf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
            sbuf.append("</strong></font>");
        } else {
            sbuf.append("<font color=\"green\">");
            sbuf.append(Transform.escapeTags(String.valueOf(event.getLevel())));
            sbuf.append("</font>");
        }
        sbuf.append("</td>" + Layout.LINE_SEP);

/*      String escapedLogger = Transform.escapeTags(event.getLoggerName().substring(event.getLoggerName().lastIndexOf(".")));
        sbuf.append("<td title=\"`?\">");
        sbuf.append(escapedLogger);
        sbuf.append("</td>" + Layout.LINE_SEP);
*/
        if (locationInfo) {
            LocationInfo locInfo = event.getLocationInformation();
            sbuf.append("<td title=\"?o?\">");
            sbuf.append(Transform.escapeTags(locInfo.getFileName()));
            sbuf.append(':');
            sbuf.append(locInfo.getLineNumber());
            sbuf.append("</td>" + Layout.LINE_SEP);
        }


//      Map session = ActionContext.getContext().getSession();
//      if(session!=null){
////            User user = (User) session.get(Constants.USER_IN_SESSION);
////            sbuf.append("<td>"+user.getName()+"</td>");
//      }else{
//          sbuf.append("<td>&nbsp;???/td>");
//      }
//      sbuf.append("<td>&nbsp;???/td>");


        sbuf.append("<td title=\"??х?\">");
        sbuf.append(Transform.escapeTags(event.getRenderedMessage()));
        sbuf.append("</td>" + Layout.LINE_SEP);
        sbuf.append("</tr>" + Layout.LINE_SEP);

        if (event.getNDC() != null) {
            sbuf.append("<tr><td bgcolor=\"#EEEEEE\" style=\"font-size : xx-small;\" colspan=\"6\" title=\"Nested Diagnostic Context\">");
            sbuf.append("NDC: " + Transform.escapeTags(event.getNDC()));
            sbuf.append("</td></tr>" + Layout.LINE_SEP);
        }

        String[] s = event.getThrowableStrRep();
        if (s != null) {
            sbuf.append("<tr><td bgcolor=\"#993300\" style=\"color:White; font-size : xx-small;\" colspan=\"4\">");
            appendThrowableAsHTML(s, sbuf);
            sbuf.append("</td></tr>" + Layout.LINE_SEP);
        }
        return sbuf.toString();
    }

    private void appendThrowableAsHTML(String[] s, StringBuffer sbuf) {
        if (s != null) {
            int len = s.length;
            if (len == 0){
                return;
            }
            sbuf.append(Transform.escapeTags(s[0]));
            sbuf.append(Layout.LINE_SEP);
            for (int i = 1; i < len; i++) {
                sbuf.append(TRACE_PREFIX);
                sbuf.append(Transform.escapeTags(s[i]));
                sbuf.append(Layout.LINE_SEP);
            }
        }
    }

    /**
     * Returns appropriate HTML headers.
     */
    @Override
    public String getHeader() {
        StringBuffer sbuf = new StringBuffer();
        sbuf.append("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">" + Layout.LINE_SEP);
        sbuf.append("<html>" + Layout.LINE_SEP);
        sbuf.append("<head>" + Layout.LINE_SEP);
        //  sbuf.append("<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\">");
        sbuf.append("<title>" + title + "</title>" + Layout.LINE_SEP);
        sbuf.append("<style type=\"text/css\">" + Layout.LINE_SEP);
        sbuf.append("<!--" + Layout.LINE_SEP);
        sbuf.append("body, table {font-family: '??',arial,sans-serif; font-size: 12px;}" + Layout.LINE_SEP);
        sbuf.append("th {background: #336699; color: #FFFFFF; text-align: left;}" + Layout.LINE_SEP);
        sbuf.append("-->" + Layout.LINE_SEP);
        sbuf.append("</style>" + Layout.LINE_SEP);
        sbuf.append("</head>" + Layout.LINE_SEP);
        sbuf.append("<body bgcolor=\"#FFFFFF\" topmargin=\"6\" leftmargin=\"6\">" + Layout.LINE_SEP);
        //  sbuf.append("<hr size=\"1\" noshade>" + Layout.LINE_SEP);
        //  sbuf.append("Log session start time " + new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(new java.util.Date()) + "<br>" + Layout.LINE_SEP);
        //  sbuf.append("<p>" + Layout.LINE_SEP);
        sbuf.append("<table cellspacing=\"0\" cellpadding=\"4\" border=\"1\" bordercolor=\"#224466\" width=\"100%\">" + Layout.LINE_SEP);
        sbuf.append("<tr>" + Layout.LINE_SEP);
        //  sbuf.append("<th>??</th>" + Layout.LINE_SEP);
        sbuf.append("<th>?????th>" + Layout.LINE_SEP);
        sbuf.append("<th>??±?h>" + Layout.LINE_SEP);
        //  sbuf.append("<th>??`</th>" + Layout.LINE_SEP);
        if (locationInfo) {
            sbuf.append("<th>???</th>" + Layout.LINE_SEP);
        }
//      sbuf.append("<th>2??/th>");
        sbuf.append("<th>х?</th>" + Layout.LINE_SEP);
        sbuf.append("</tr>" + Layout.LINE_SEP);
        sbuf.append("<br></br>" + Layout.LINE_SEP);
        return sbuf.toString();
    }

}