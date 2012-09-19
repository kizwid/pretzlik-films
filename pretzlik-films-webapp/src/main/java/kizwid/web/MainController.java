package kizwid.web;

import kizwid.web.util.FormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * User: kizwid
 * Date: 2012-02-02
 */
public class MainController implements Controller {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);
    private static final String ACTION_HOME = "Home";
    private static final String VIEW_HOME = "home";
    private JdbcTemplate jdbcTemplate;


    public MainController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Map<String, Object> model = new HashMap<String, Object>();
        String action = request.getParameter("Action");
        logger.info("Action {} from {}", action, request.getRemoteHost());
        
        if (ACTION_HOME.equals(action)) {

            home(request, model);
            return new ModelAndView(VIEW_HOME, model);

        } else if ("xxx".equals(action)) {

            //extend as required
            home(request, model);
            return new ModelAndView(VIEW_HOME, model);

        } else {

            home(request, model);
            return new ModelAndView(VIEW_HOME, model);

        }


    }

    private Map<String, Object> home(HttpServletRequest request, Map<String, Object> model) throws NoSuchAlgorithmException {

        //current parameters
        int yyyymmdd = FormatUtils.parseDate(request.getParameter("BusinessDate"));
        model.put("BusinessDate", yyyymmdd);


        return model;
    }


    //detect changes in view
    String getLatestHash(){

        return "TBD";

    }

}
