package com.github.paginationspring.web;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public final class PaginationUrl {
	private static Log log = LogFactory.getLog(PaginationUrl.class);
    private static final Pattern PARAM_PATTERN = Pattern.compile("[\\?\\&](\\w+)\\=([^\\?\\&\\=]*)", Pattern.CASE_INSENSITIVE);
    private static final String PARAM_NAME_RESULTINDEX  = "resultIndex";
    private static final String PARAM_NAME_SORTNAME     = "sortName";
    private static final String PARAM_NAME_SORTASCDESC  = "sortAscDesc";

    private PaginationUrl() {
    }
	
	public static String url(String value, Boolean ajax, Boolean rewriteUrl, Object pparam, String encoding) {
//		log.debug("encoding="+encoding);
		try {
	        StringBuilder sb = new StringBuilder();
	        
	        if ( value.indexOf("?") == -1) {
	            sb.append(value);
	        } else {
	            Map<String,List<String>> paramsInTag = new HashMap<String,List<String>>();
	            // putting incoming url into paramsInTag
	            Matcher matcher = PARAM_PATTERN.matcher(value);
	            while (matcher.find()) {
	                String pn = matcher.group(1);
	                String pv = matcher.group(2);
	                if ( paramsInTag.containsKey(pn) ) {
	                    paramsInTag.get(pn).add(pv);
	                } else {
	                    List<String> valuelist = new ArrayList<String>();
	                    valuelist.add(pv);
	                    paramsInTag.put(pn, valuelist);
	                }
	            }
	
	            // putting Pagination Parameter into paramsInTag
	            assignParamsInTag(paramsInTag, pparam);
	            
	            String uri = value.substring(0, value.indexOf("?"));
	            if ( uri.lastIndexOf('/') == -1 ) {
	                throw new IllegalArgumentException("URL must have '/' character.");
	            }
	            String prefix = uri.substring(0, uri.lastIndexOf('/'));
	            String suffix = uri.substring(uri.lastIndexOf('/'));
	            sb.append(prefix);
	            if ( rewriteUrl != null && rewriteUrl ) {
		            if ( ajax != null && !ajax ) {
			            for ( String pkey : paramsInTag.keySet() ) {
			                if ( PARAM_NAME_RESULTINDEX.equals(pkey) ) {
			                    for ( String pvalue : paramsInTag.get(pkey) ) {
			                    	if ( !"0".equals(pvalue) ) {
			                    		sb.append("/"+pvalue);
			                            break;
			                    	}
			                    }
			                }
			            }
			            for ( String pkey : paramsInTag.keySet() ) {
			                if ( PARAM_NAME_SORTNAME.equals(pkey) ) {
			                    for ( String pvalue : paramsInTag.get(pkey) ) {
			                        sb.append("/sort/"+pvalue);
			                        break;
			                    }
			                }
			            }
			            for ( String pkey : paramsInTag.keySet() ) {
			                if ( PARAM_NAME_SORTASCDESC.equals(pkey) ) {
			                    for ( String pvalue : paramsInTag.get(pkey) ) {
			                        sb.append("/"+pvalue);
			                        break;
			                    }
			                }
			            }
		            }
	            }
	            sb.append(suffix);
	
	            StringBuilder psb = new StringBuilder();
	            int noOfParam = 0;
	            for ( String pkey : paramsInTag.keySet() ) {
		            if ( rewriteUrl != null && rewriteUrl ) {
		                if ( ajax != null && !ajax ) {
			                if ( PARAM_NAME_RESULTINDEX.equals(pkey) ) continue;
			                if ( PARAM_NAME_SORTNAME.equals(pkey) ) continue;
			                if ( PARAM_NAME_SORTASCDESC.equals(pkey) ) continue;
		                }
		            }
	                for ( String pvalue : paramsInTag.get(pkey) ) {
	                    if ( noOfParam > 0 ) psb.append("&");
	                    psb.append(URLEncoder.encode(pkey, encoding));
	                    psb.append('=');
	                    psb.append(URLEncoder.encode(pvalue, encoding));
	                    noOfParam++;
	                }
	//                log.warn("The following query parameter failed to find any appropriate match in BoPaginationParam or inherited object equivalent: "+pn+".");
	            }
	            if ( noOfParam > 0 ) {
	                sb.append('?');
	                sb.append(psb);
	            }
	        }
	        if ( ajax != null && ajax ) {
	        	return "javascript:ajaxUrl('"+sb.toString()+"')";
	        }
	        return sb.toString();
		} catch (Exception e) {
			log.error("",e);
		}
		return null;
    }
    
    private static void assignParamsInTag(Map<String,List<String>> paramsInTag, Object pparam) throws Exception {
        // putting Pagination Parameter into paramsInTag
        Class<?> curcls = pparam.getClass();
        // loop for all the superclass until instanceof Object
        while ( curcls != null && !curcls.getName().equals(Object.class.getName()) ) {
            // all the declared methods
            Method[] methods = curcls.getDeclaredMethods();
            for ( int i =0; methods!= null && i<methods.length; i++ ) {
                Method method = methods[i];
                // all the getter
                if ( method.getName().startsWith("get") || method.getName().startsWith("is") ) {
                    try {
                        Pattern pattern = Pattern.compile("[A-Z].*");
                        Matcher matcher = pattern.matcher(method.getName());
                        String varname = null;
                        if ( matcher.find() ) {
                            varname = StringUtils.uncapitalize(matcher.group());
                        }
                        
                        if ( !paramsInTag.containsKey(varname) ) {
                            Object obj = method.invoke(pparam, (Object[]) null );
                            if ( obj != null ) {
                                List<String> valuelist = new ArrayList<String>();
                                if ( obj.getClass().isArray() ) {
                                    int length = Array.getLength(obj);
                                    for (int k = 0; k < length; k ++) {
                                        Object arrayElement = Array.get(obj, k);
                                        valuelist.add(String.valueOf(arrayElement));
                                    }
                                    paramsInTag.put(varname, valuelist);
                                } else {
                                    if ( !StringUtils.isEmpty(String.valueOf(obj)) ) {
                                        valuelist.add(String.valueOf(obj));
                                        paramsInTag.put(varname, valuelist);
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        log.error("",e);
                    }
                }
            }
            curcls = curcls.getSuperclass();
        }
    }
}
