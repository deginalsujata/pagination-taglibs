<%@tag pageEncoding="UTF-8"%>
<%@attribute name="pparam" type="com.github.paginationspring.bo.BoPaginationParam" required="true" %>
<%@attribute name="paginationResult" type="com.github.paginationspring.bo.BoPaginationResult" required="true" %>
<%@attribute name="columnsContent" fragment="true" %>
<%@attribute name="contentAfterLastRow" fragment="true" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="pg" uri="http://pagination/pagination-spring3.tld" %>

<c:url value="${paginationResult.pageLink}" var="pageLink"/>

<c:if test="${paginationResult.totalSizeOfResult>0}">
<div id="paginationRecordCountDiv" class="paginationRecordCount" style="width:${paginationResult.optionWidth}px;">
    <span class="paginationRecordCountCharacter">Records ${pparam.resultIndex+1} - ${pparam.resultIndex+paginationResult.noOfRecordOnCurrentPage} of ${paginationResult.totalSizeOfResult}</span>
</div>
</c:if>

<div>
<input id="resultIndex" name="resultIndex" type="hidden" value="<c:out value="${pparam.resultIndex}"/>"/>
<input id="recordPerPage" name="recordPerPage" type="hidden" value="<c:out value="${pparam.recordPerPage}"/>"/>
<input id="sortName" name="sortName" type="hidden" value="<c:out value="${pparam.sortName}"/>"/>
<input id="sortAscDesc" name="sortAscDesc" type="hidden" value="<c:out value="${pparam.sortAscDesc}"/>"/>
</div>

<c:if test="${paginationResult.totalSizeOfResult==0}">
    <div class="paginationNoRecordText" style="width:${paginationResult.optionWidth}px;">
        No records can be found.
    </div>
</c:if>
<c:if test="${paginationResult.totalSizeOfResult>0}">
    <div id="paginationRecordsDiv" style="width:${paginationResult.optionWidth}px;">
    <table class="sltable" style="width:${paginationResult.optionWidth}px;">
        <tr class="header">
            <c:if test="${paginationResult.optionDisplaySerialNo}">
                <th class="header"><div class="serialNoHeader"></div></th>
            </c:if>
            <c:if test="${paginationResult.optionDisplayCheckbox}">
                <th class="header"><input id="selectall" class="paginationCheckbox" type="checkbox" value="Y" name="selectall"/><div class="checkboxHeader"></div></th>
            </c:if>
            <c:forEach var="col" items="${paginationResult.columns}" varStatus="status">
            <th class="header">
                <c:if test="${empty col.orderColumns}">
                    <c:out value="${col.columnName}"/>
                </c:if>
                <c:if test="${!empty col.orderColumns}">
                    <a href="<pg:sortingUrl value="${pageLink}" pparam="${pparam}" ajax="${paginationResult.ajax}" rewriteUrl="${paginationResult.rewriteUrl}" columnName="${col.columnName}" orderColumns="${col.orderColumns}" orderDirections="${col.orderDirections}" defaultSortName="${paginationResult.defaultSortName}" defaultSortAscDesc="${paginationResult.defaultSortAscDesc}"/>" class="header">
                    <span class="paginationColumnHeader"><c:out value="${col.columnName}"/></span>
                    <c:if test="${col.columnName==pparam.sortName}">
                        <c:if test="${empty pparam.sortAscDesc}">
                            <span class="sortarrow">&#9650;</span>
                        </c:if>
                        <c:if test="${!empty pparam.sortAscDesc && fn:startsWith(pparam.sortAscDesc,'a')}">
                            <span class="sortarrow">&#9650;</span>
                        </c:if>
                        <c:if test="${!empty pparam.sortAscDesc && fn:startsWith(pparam.sortAscDesc,'d')}">
                            <span class="sortarrow">&#9660;</span>
                        </c:if>
                    </c:if>
                    <c:if test="${empty pparam.sortName && not empty paginationResult.defaultSortName && not empty paginationResult.defaultSortAscDesc && col.columnName==paginationResult.defaultSortName}">
                        <c:if test="${fn:startsWith(paginationResult.defaultSortAscDesc,'a')}">
                            <span class="sortarrow">&#9650;</span>
                        </c:if>
                        <c:if test="${fn:startsWith(paginationResult.defaultSortAscDesc,'d')}">
                            <span class="sortarrow">&#9660;</span>
                        </c:if>
                    </c:if>
                    </a>
                </c:if>
                <c:if test="${col.width != null}">
                    <div style="height:1px;padding-left:${col.width}px;"></div>
                </c:if>
            </th>
            </c:forEach>
        </tr>
        <c:forEach var="item" items="${paginationResult.resultBoList}" varStatus="status">
        <c:if test="${status.count%2==0}">
            <c:set var="rowcls" value="evenrow"/>
        </c:if>
        <c:if test="${status.count%2==1}">
            <c:set var="rowcls" value="oddrow"/>
        </c:if>
        <tr class="${rowcls}">
            <c:if test="${paginationResult.optionDisplaySerialNo}">
                <td class="cell">${status.count + pparam.resultIndex}</td>
            </c:if>
            <c:if test="${paginationResult.optionDisplayCheckbox}">
                <td class="cell"><input class="paginationCheckbox" type="checkbox" value="${item.pk}" name="selectedIds"/></td>
            </c:if>
            <c:set var="bo" value="${item}" scope="request"></c:set>
            <jsp:invoke fragment="columnsContent"/>
        </tr>
        </c:forEach>
        <tr style="border-left:0px;">
            <c:if test="${paginationResult.optionDisplaySerialNo && paginationResult.optionDisplayCheckbox}">
                <td id="ie-padding-problem" style="height:20px;display:none;" colspan="${fn:length(paginationResult.columns)+2}"></td>
            </c:if>
            <c:if test="${!paginationResult.optionDisplaySerialNo && paginationResult.optionDisplayCheckbox}">
                <td id="ie-padding-problem" style="height:20px;display:none;" colspan="${fn:length(paginationResult.columns)+1}"></td>
            </c:if>
            <c:if test="${paginationResult.optionDisplaySerialNo && !paginationResult.optionDisplayCheckbox}">
                <td id="ie-padding-problem" style="height:20px;display:none;" colspan="${fn:length(paginationResult.columns)+1}"></td>
            </c:if>
            <c:if test="${!paginationResult.optionDisplaySerialNo && !paginationResult.optionDisplayCheckbox}">
                <td id="ie-padding-problem" style="height:20px;display:none;" colspan="${fn:length(paginationResult.columns)}"></td>
            </c:if>
        </tr>
    </table>
    </div>
</c:if>
<jsp:invoke fragment="contentAfterLastRow"/>
<pg:pagination_page_control paginationResult="${paginationResult}" pparam="${pparam}">
</pg:pagination_page_control>
