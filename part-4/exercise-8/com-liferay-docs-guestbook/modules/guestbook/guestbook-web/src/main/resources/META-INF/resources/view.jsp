<!-- 
When Liferay module-project is created, it'll generate this file. 
It contains default view for users when the portlet is added to 
the page.
 -->

<%@ include file="/init.jsp" %>

<portlet:renderURL var="addEntryURL">
    <portlet:param name="mvcPath" value="/edit_entry.jsp"></portlet:param>
</portlet:renderURL>

<jsp:useBean id="entries" class="java.util.ArrayList" scope="request"/>

<liferay-ui:search-container>
    <liferay-ui:search-container-results results="<%= entries %>" />

    <liferay-ui:search-container-row
        className="com.liferay.docs.guestbook.model.Entry"
        modelVar="entry">
        <liferay-ui:search-container-column-text property="message" />

        <liferay-ui:search-container-column-text property="name" />
    </liferay-ui:search-container-row>

    <liferay-ui:search-iterator />
</liferay-ui:search-container>


<aui:button-row>
  <aui:button value="Add Entry" onClick="<%= addEntryURL.toString() %>"></aui:button>
</aui:button-row>

<p>
	<b><liferay-ui:message key="guestbook.caption"/></b>
</p>