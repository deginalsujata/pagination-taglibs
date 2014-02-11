package com.github.paginationspring.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

public class PaginationSortingUrlTag extends RequestContextAwareTag {
	private static Log log = LogFactory.getLog(PaginationSortingUrlTag.class);
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String value;
    
    private Object pparam;
    private Boolean ajax;
    private Boolean rewriteUrl;

    private String columnName;
    private String orderColumns;
    private String orderDirections;
    private String defaultSortName;
    private String defaultSortAscDesc;

    protected int doStartTagInternal() throws Exception {
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        String url = null;
        
        try {
            url = PaginationSortingUrl.sortingUrl(value, ajax, rewriteUrl, pparam, pageContext.getResponse().getCharacterEncoding(), columnName, orderColumns, orderDirections, defaultSortName, defaultSortAscDesc);
        } catch (Exception e) {
            log.error("",e);
        }
        
        // print the url to the writer
        try {
            pageContext.getOut().print(url);
        } catch (IOException e) {
            throw new JspException(e);
        }
        return EVAL_PAGE;
    }
    
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public void setOrderColumns(String orderColumns) {
        this.orderColumns = orderColumns;
    }

    public void setOrderDirections(String orderDirections) {
        this.orderDirections = orderDirections;
    }

	public void setDefaultSortName(String defaultSortName) {
		this.defaultSortName = defaultSortName;
	}

	public void setDefaultSortAscDesc(String defaultSortAscDesc) {
		this.defaultSortAscDesc = defaultSortAscDesc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Object getPparam() {
		return pparam;
	}

	public void setPparam(Object pparam) {
		this.pparam = pparam;
	}

	public Boolean getAjax() {
		return ajax;
	}

	public void setAjax(Boolean ajax) {
		this.ajax = ajax;
	}

	public Boolean getRewriteUrl() {
		return rewriteUrl;
	}

	public void setRewriteUrl(Boolean rewriteUrl) {
		this.rewriteUrl = rewriteUrl;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getOrderColumns() {
		return orderColumns;
	}

	public String getOrderDirections() {
		return orderDirections;
	}

	public String getDefaultSortName() {
		return defaultSortName;
	}

	public String getDefaultSortAscDesc() {
		return defaultSortAscDesc;
	}

}
