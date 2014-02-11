package com.github.paginationspring.web;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class PaginationSortingUrl {
	private static Log log = LogFactory.getLog(PaginationSortingUrl.class);

	private PaginationSortingUrl() {
		
	}
	
	public static String sortingUrl(String value, Boolean ajax, Boolean rewriteUrl, Object pparam, String encoding, String columnName, String orderColumns, String orderDirections, String defaultSortName, String defaultSortAscDesc) {
		try {
	        StringBuilder sb = new StringBuilder(value);
	        if ( !StringUtils.isEmpty(orderColumns) ) {
	            if ( value.indexOf('?') == -1 ) {
	                sb.append('?');
	            } else {
	                sb.append('&');
	            }
	            sb.append("sortName=");
	            sb.append(columnName);
	            String pparam_sortName = (String) runMethod(pparam, "getSortName", null, null );
	            if ( columnName.equals(pparam_sortName) ) {
	                String pparam_sortAscDesc = (String) runMethod(pparam, "getSortAscDesc", null, null );
	                sb.append('&');
	                sb.append("sortAscDesc=");
	                sb.append(_reverseSortAscDesc(pparam_sortAscDesc));
	            } else {
	                sb.append('&');
	                sb.append("sortAscDesc=");
	                if ( StringUtils.isEmpty(pparam_sortName) && columnName.equals(defaultSortName) && !StringUtils.isEmpty(defaultSortAscDesc) ) {
	                    sb.append(_reverseSortAscDesc(defaultSortAscDesc));
	                } else if ( StringUtils.isEmpty(orderDirections) ) {
	                    sb.append("a");
	                } else if ( orderDirections.toLowerCase().startsWith("desc") ) {
	                    sb.append("d");
	                } else {
	                    sb.append("a");
	                }
	            }
	        }
	        
	        return PaginationUrl.url(sb.toString(), ajax, rewriteUrl, pparam, encoding);
		} catch (Exception e) {
			log.error("",e);
		}
		return null;
	}

    private static String _reverseSortAscDesc(String sortAscDesc) {
        if ( StringUtils.isEmpty(sortAscDesc) ) return "a";
        if ( "a".equals(sortAscDesc) ) return "d";
        return "a";
    }

    private static Object runMethod(Object object, String methodname, Class[] classarr, Object[] methodargs) {
        try {
            Method method = object.getClass().getMethod(methodname, classarr );
            return method.invoke(object, methodargs );
        } catch (Exception e) {
//            log.error("Cannot run method "+methodname+" in bean "+object.getClass().getName());
        }
        return null;
    }
}
