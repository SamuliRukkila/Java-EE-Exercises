<!-- Encode to UTF-8 -->
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!-- Bring an external library to be able to use JSTL-syntax -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Use class DiceClass as a bean in this page -->
<jsp:useBean id="dice" scope="session" class="classes.DiceClass" />

<!DOCTYPE HTML>
<html>
	<head>
		<style>
			span {
				background-color: red;
				border-radius: 150px;
				color: white;
				padding: 3px 6px;
			}
		</style>
		<title>Heitä noppaa</title>
	</head>
	<body>
	
		<%-- Conditional statement checking the status of dice throwing --%>
		<c:choose>
			<%-- If user throwed the dice, class-function will be called which'll
			throw a random number. This value will be printed to the page afterwards --%>
			<c:when test="${param.throwDice}">
				${dice.throwDice()}
				<p>Silmäluvuksi tuli: <c:out value="${dice.getResult()}"></c:out></p>
			</c:when>
			<%-- If user wants to reset the total amount of points gotten from dices --%>
			<c:when test="${param.resetSum}">
				${dice.resetSum()}
				<p>Nollasit noppien kokonaisarvon</p>
			</c:when>
			<%-- If user wants reset the amount of dice-throws --%>
			<c:when test="${param.resetThrows}">
				${dice.resetThrows()}
				<p>Nollasit heittojen määrät</p>
			</c:when>
			<%-- If user wants to reset the average number of dice-throws --%>
			<c:when test="${param.resetAverage}">
				${dice.resetAverage()}
				<p>Nollasit keskiarvon</p>
			</c:when>
			<%-- If user hasn't thrown the dice or has resetted the dice-throwing --%>
			<c:otherwise>
				<p>Noppaa ei ole heitetty</p>
			</c:otherwise>
		</c:choose>
		
		<hr>
		
		<%-- Prints the amount of throws --%>
		<p>Noppaa on heitetty yhteensä 
			<c:out value="${dice.getThrowAmount()}"></c:out> kertaa
		</p>
		<%-- Prints the total sum of dices values --%>
		<p>Heittojen summa on nyt:	
			<span><c:out value="${dice.getSum()}"></c:out></span>
		</p>
		<%-- Prints the average number of throws --%>
		<p>Heittojen keskiarvo: 
			<c:out value="${dice.getAverage()}"></c:out>
		</p>
		
		<%-- Using JSTL-library to create URLs with parameters --%>
		<c:url value="dice.jsp" var="throwDice">
			<c:param name="throwDice" value="true"></c:param>
		</c:url>
		<c:url value="dice.jsp" var="resetSum">
			<c:param name="resetSum" value="true"></c:param>
		</c:url>
		<c:url value="dice.jsp" var="resetThrows">
			<c:param name="resetThrows" value="true"></c:param>
		</c:url>
		<c:url value="dice.jsp" var="resetAverage">
			<c:param name="resetAverage" value="true"></c:param>
		</c:url>
		
		<%-- URLs created above will be printed to <a> -tags --%>
		<p><a href="${throwDice}">Heitä noppaa</a></p>
		<p><a href="${resetSum}">Nollaa summa</a></p>
		<p><a href="${resetThrows}">Nollaa heitot</a></p>
		<p><a href="${resetAverage}">Nollaa keskiarvo</a></p>


	</body>
</html>