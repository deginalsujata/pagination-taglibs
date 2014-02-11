<%@tag pageEncoding="UTF-8"%>
<%@attribute name="pparam" type="com.github.paginationspring.bo.BoPaginationParam" required="true" %>
<%@attribute name="paginationResult" type="com.github.paginationspring.bo.BoPaginationResult" required="false" %>
<%@attribute name="searchContent" fragment="true" %>
<%@attribute name="controlButton" fragment="true" %>
<%@attribute name="columnsContent" fragment="true" %>
<%@attribute name="contentAfterLastRow" fragment="true" %>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="pg" uri="http://pagination/pagination-spring3.tld" %>

<c:url value="${paginationResult.pageLink}" var="pageLink"/>

<div id="pgformextra" style="display:none;">
</div>

<div id="paginationSearchDiv" style="width:${paginationResult.optionWidth}px;">
<jsp:invoke fragment="searchContent"/>
</div>

<div class="pagination_button">
<jsp:invoke fragment="controlButton"/>
</div>

<c:if test="${paginationResult.ajax}">
<div id="paginationTableDiv">
</div>
<script type="text/javascript">
//<![CDATA[
window.onload=function(){
    // load the pagination result for the first time
    $.ajax({
        dataType: "html",
        url: "${pageLink}",
        success: function(data) {
            var pagDiv = $('#paginationTableDiv');
            pagDiv.html(data);
        }
    });
    // change form submission
    $('#pgform').submit(function() { // catch the form's submit event
        $.ajax({ // create an AJAX call...
            data: $(this).serialize(), // get the form data
            type: $(this).attr('method'), // GET or POST
            url: "${pageLink}", // the file to call
            success: function(response) { // on success..
                $('#paginationTableDiv').html(response); // update the DIV
            }
        });
        $('#pgformextra').html('');
        return false; // cancel original event to prevent form submitting
    });
    $(":submit").click(function(event) {
        $('#pgformextra').html('<input type="hidden" name="buttonAction" value="'+$(this).attr('id')+'" />');
    });
    function getURLParameter(name) {
        return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
    }
    $("#clearButton").click(function(event) {
        $('#pgform')[0].reset();
    });
}
function ajaxUrl(pgurl) {
    $.ajax({
        dataType: "html",
        url: pgurl,
        success: function(data) {
            var pagDiv = $('#paginationTableDiv');
            pagDiv.html(data);
        }
    });
}
//]]>
</script>
</c:if>
<c:if test="${not paginationResult.ajax}">
<div id="paginationTableDiv">
    <pg:pagination_ajax paginationResult="${paginationResult}" pparam="${pparam}">
        <jsp:attribute name="columnsContent"><jsp:invoke fragment="columnsContent"/></jsp:attribute>
        <jsp:attribute name="contentAfterLastRow"><jsp:invoke fragment="contentAfterLastRow"/></jsp:attribute>
    </pg:pagination_ajax>
</div>
</c:if>
