<%@tag pageEncoding="UTF-8"%>
<%@attribute name="pparam" type="com.github.paginationspring.bo.BoPaginationParam" required="true" %>
<%@attribute name="paginationResult" type="com.github.paginationspring.bo.BoPaginationResult" required="true" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="pg" uri="http://pagination/pagination-spring3.tld" %>

<c:url value="${paginationResult.pageLink}" var="pageLink"/>

<c:if test="${paginationResult.totalSizeOfResult>0 && paginationResult.totalNoOfPage!=1}">
    <div id="paginationLinkDiv" class="paginationLink" style="width:${paginationResult.optionWidth}px;">
        <table class="pagination_page">
            <tr>
                <td></td>
                <td class="paginationLinkFirst">
                    <c:if test="${paginationResult.previousPageExists}">
                        <a href="<pg:url value="${pageLink}?resultIndex=0" ajax="${paginationResult.ajax}" rewriteUrl="${paginationResult.rewriteUrl}" pparam="${pparam}"/>" title="First"><span id="paginationLinkFirstCharacter" class="paginationLinkFirstCharacter">&laquo;</span></a>
                    </c:if>
                    <c:if test="${!paginationResult.previousPageExists}">
                        <span id="paginationLinkFirstCharacterDisabled" class="paginationLinkFirstCharacter">&laquo;</span>
                    </c:if>
                </td>
                <td class="paginationLinkPrevious">
                    <c:if test="${paginationResult.previousPageExists}">
                        <a href="<pg:url value="${pageLink}?resultIndex=${paginationResult.resultIndexOfPreviousPage}" ajax="${paginationResult.ajax}" rewriteUrl="${paginationResult.rewriteUrl}" pparam="${pparam}"/>" title="Previous"><span id="paginationLinkPreviousCharacter" class="paginationLinkPreviousCharacter">&lsaquo;</span></a>
                    </c:if>
                    <c:if test="${!paginationResult.previousPageExists}">
                        <span id="paginationLinkPreviousCharacterDisabled" class="paginationLinkPreviousCharacter">&lsaquo;</span>
                    </c:if>
                </td>
                <td class="paginationLinkDotDotBegin">
                    <c:if test="${paginationResult.showDotDotBegin}"><span id="paginationLinkDotDotBeginCharacter" class="paginationLinkDotDotBeginCharacter">...</span></c:if>
                </td>
                <td class="paginationLinkPages">
                    <c:forEach var="puPage" items="${paginationResult.listOfPageNos}" varStatus="status">
                        <c:if test="${puPage.currentPage}"><span id="paginationLinkActivePageCharacter" class="paginationLinkActivePageCharacter">${puPage.pageNo}</span></c:if>
                        <c:if test="${!puPage.currentPage}">
                            <a href="<pg:url value="${pageLink}?resultIndex=${paginationResult.recordPerPage*(puPage.pageNo-1)}" ajax="${paginationResult.ajax}" rewriteUrl="${paginationResult.rewriteUrl}" pparam="${pparam}"/>"><span class="paginationLinkPagesCharacter">${puPage.pageNo}</span></a>
                        </c:if>
                        <span class="paginationLinkBetweenPages"></span>
                    </c:forEach>
                </td>
                <td class="paginationLinkDotDotEnd">
                    <c:if test="${paginationResult.showDotDotEnd}"><span id="paginationLinkDotDotEndCharacter" class="paginationLinkDotDotEndCharacter">...</span></c:if>
                </td>
                <td class="paginationLinkNext">
                    <c:if test="${paginationResult.nextPageExists}">
                        <a href="<pg:url value="${pageLink}?resultIndex=${paginationResult.resultIndexOfNextPage}" ajax="${paginationResult.ajax}" rewriteUrl="${paginationResult.rewriteUrl}" pparam="${pparam}"/>" title="Next"><span id="paginationLinkNextCharacter" class="paginationLinkNextCharacter">&rsaquo;</span></a>
                    </c:if>
                    <c:if test="${!paginationResult.nextPageExists}">
                        <span id="paginationLinkNextCharacterDisabled" class="paginationLinkNextCharacter">&rsaquo;</span>
                    </c:if>
                </td>
                <td class="paginationLinkLast">
                    <c:if test="${paginationResult.nextPageExists}">
                        <a href="<pg:url value="${pageLink}?resultIndex=${paginationResult.resultIndexOfLastPage}" ajax="${paginationResult.ajax}" rewriteUrl="${paginationResult.rewriteUrl}" pparam="${pparam}"/>" title="Last"><span id="paginationLinkLastCharacter" class="paginationLinkLastCharacter">&raquo;</span></a>
                    </c:if>
                    <c:if test="${!paginationResult.nextPageExists}">
                        <span id="paginationLinkLastCharacterDisabled" class="paginationLinkLastCharacter">&raquo;</span>
                    </c:if>
                </td>
            </tr>
        </table>
    </div>
</c:if>
