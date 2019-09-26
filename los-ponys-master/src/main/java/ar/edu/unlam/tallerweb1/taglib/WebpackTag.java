package ar.edu.unlam.tallerweb1.taglib;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;
import java.util.Map;

import java.io.FileReader;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class WebpackTag extends SimpleTagSupport {
  private String value;
  private ServletContext servletContext;

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public void doTag() throws JspException, IOException {
    PageContext pageContext = (PageContext) getJspContext();
    HttpServletRequest request = (HttpServletRequest) pageContext.getRequest();
    InputStream in = request.getSession().getServletContext().getResourceAsStream("/dist/manifest.json");
    JSONParser jsonParser = new JSONParser();
    try {
      JSONObject jsonObject = (JSONObject)jsonParser.parse(
          new InputStreamReader(in, "UTF-8")
      );
      String js = (String) jsonObject.get(this.value);
      JspWriter out = getJspContext().getOut();
      out.println(js);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ParseException e) {
      e.printStackTrace();
    }
  }
}
