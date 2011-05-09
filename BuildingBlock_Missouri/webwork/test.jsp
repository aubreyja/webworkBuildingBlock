<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!-- Tag library necessary to extract the info from the context -->
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c"%>
<%@ taglib uri="/bbNG" prefix="bbNG"%>

		<c:forEach var='parameter' items='${paramValues}'>
			<ul>
				<li>
					<b>
						<c:out value='${parameter.key}' />
					</b>
					:
				</li>

				<c:forEach var='value' items='${parameter.value}'>
					<c:out value='${value}' />
				</c:forEach>
				
			</ul>
		</c:forEach>