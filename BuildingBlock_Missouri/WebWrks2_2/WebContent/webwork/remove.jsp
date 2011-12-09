<%@ page autoFlush="true" session="true"%>

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
		