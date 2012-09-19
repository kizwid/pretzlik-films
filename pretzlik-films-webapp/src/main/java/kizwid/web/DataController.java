package kizwid.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * User: kizwid
 * Date: 2012-02-02
 */
public class DataController extends MainController {

    private static final Logger logger = LoggerFactory.getLogger(DataController.class);
    public DataController(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate
        );
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        PrintWriter out = response.getWriter();
        String action = request.getParameter("Action");
        logger.info("Action [{}] from {}", action, request.getRemoteHost());

        if ("GetJobStats".equals(action)) {
            response.setContentType("text/plain");
            String jobStatsJson = "TBD";
            out.println(jobStatsJson);
            out.flush();
        }else if("LatestId".equals(action)){
            response.setContentType("text/plain");
            out.println(super.getLatestHash());
            out.flush();
        }

        return null;
    }


}
