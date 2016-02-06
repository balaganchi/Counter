package com.sunil.web.filecounter.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sunil.web.filecounter.dao.FileDao;

/**
 * Servlet implementation class FileCounter
 */
@WebServlet("/FileCounter")
public class FileCounter extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	int count;
	private FileDao dao;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    @Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
    	//Set a cookie, for the user, so that the counter does not increate
    	//everytime the user refreshes the page
    	HttpSession session = request.getSession(true);
    	//Set session valid for 5 secs
    	session.setMaxInactiveInterval(5);
    	response.setContentType("text/plain");
    	PrintWriter out = response.getWriter();
    	if(session.isNew()){
    		count++;
    	}
		out.println("This site has been accessed " + count + " times.");
	}

	@Override
	public void init() throws ServletException {
		dao = new FileDao();
		try{
			count = dao.getCount();
		} catch(Exception e){
			getServletContext().log("An exception occured in Filecounter",e);
			throw new ServletException("An exception occured in Filecounter" +e.getMessage());
		}
	}
	
	public void destroy(){
		super.destroy();
		try{
			dao.save(count);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

}
