
package sti.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.ResultSet;
import java.sql.SQLException;
import sti.DatabaseOperations.Database;
import sti.DatabaseOperations.DatabaseUtil;
import sti.classes.Membership;

public class AddMemberServlet extends HttpServlet 
{
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        String groupid = request.getParameter("groupid");
        String studentname = request.getParameter("studentname");
        String studentid = request.getParameter("studentid");
        Database db = new Database();
        ResultSet rs = db.RetrieveGroup(groupid);
        String message;
        String groupname = " ";
        try
        {
            rs.next();
            groupname = rs.getString("name");
        }
        catch(SQLException se)
        {
            System.out.println(se);
            System.out.println("addmemberservlet: error");
        }
        
        Membership mbp = new Membership(groupid, groupname, studentid, studentname);
        
        if(db.CreateRecord(mbp)>0)
        {
            message = "member added successfully";
        }
        else
            message = "could not add member";
        
        DatabaseUtil.closeResultSet(rs);
        db.closedatabase();
        
        response.setHeader("refresh", "2;URL=groupinfo");
        PrintWriter pw = response.getWriter();
        pw.print(message);
        pw.close();
        
    }
}
