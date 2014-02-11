package com.github.paginationspring.web;

import java.io.IOException;

import javax.servlet.jsp.JspException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.tags.RequestContextAwareTag;

public class PaginationUrlTag extends RequestContextAwareTag {
	private static Log log = LogFactory.getLog(PaginationUrlTag.class);

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String value;
    
    private Object pparam;
    private Boolean ajax;
    private Boolean rewriteUrl;

    protected int doStartTagInternal() throws Exception {
        return EVAL_BODY_INCLUDE;
    }

    public int doEndTag() throws JspException {
        String url = null;
        
        try {
            url = PaginationUrl.url(value, ajax, rewriteUrl, pparam, pageContext.getResponse().getCharacterEncoding());
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

    public void setValue(String value) {
        this.value = value;
    }

    public void setPparam(Object pparam) {
        this.pparam = pparam;
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

	public String getValue() {
        return value;
    }

    public Object getPparam() {
        return pparam;
    }
}
