package com.github.paginationspring.web;

import java.util.ArrayList;
import java.util.List;

import javax.el.ValueExpression;
import javax.faces.component.html.HtmlOutputLink;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class PaginationUrlLinkJsfComponent extends HtmlOutputLink {
	private static Log log = LogFactory.getLog(PaginationUrlLinkJsfComponent.class);

	private String url;
	private Boolean ajax;
	private Boolean rewriteUrl;
	private Object pparam;

	public Object getValue() {
//		log.debug("begin");
	    return PaginationUrl.url(getUrl(), getAjax(), getRewriteUrl(), getPparam(), getFacesContext().getExternalContext().getResponseCharacterEncoding());
	}
	
	public String getUrl() {
        if (null != this.url) {
            return this.url;
        }
        ValueExpression _ve = getValueExpression("url");
        if (_ve != null) {
            return (String) _ve.getValue(getFacesContext().getELContext());
        } else {
            return null;
        }
	}

	public void setUrl(String url) {
		this.url = url;
        handleAttribute("url", url);
	}

	public Boolean getAjax() {
        if (null != this.ajax) {
            return this.ajax;
        }
        ValueExpression _ve = getValueExpression("ajax");
        if (_ve != null) {
            return (Boolean) _ve.getValue(getFacesContext().getELContext());
        } else {
            return null;
        }
	}

	public void setAjax(Boolean ajax) {
		this.ajax = ajax;
        handleAttribute("ajax", ajax);
	}

	public Boolean getRewriteUrl() {
        if (null != this.rewriteUrl) {
            return this.rewriteUrl;
        }
        ValueExpression _ve = getValueExpression("rewriteUrl");
        if (_ve != null) {
            return (Boolean) _ve.getValue(getFacesContext().getELContext());
        } else {
            return null;
        }
	}

	public void setRewriteUrl(Boolean rewriteUrl) {
		this.rewriteUrl = rewriteUrl;
        handleAttribute("rewriteUrl", rewriteUrl);
	}

	public Object getPparam() {
        if (null != this.pparam) {
            return this.pparam;
        }
        ValueExpression _ve = getValueExpression("pparam");
        if (_ve != null) {
            return (Object) _ve.getValue(getFacesContext().getELContext());
        } else {
            return null;
        }
	}

	public void setPparam(Object pparam) {
		this.pparam = pparam;
        handleAttribute("pparam", pparam);
	}

    private static final String OPTIMIZED_PACKAGE = "javax.faces.component.";
    private void handleAttribute(String name, Object value) {
        List<String> setAttributes = null;
        String cname = this.getClass().getName();
        if (cname != null && cname.startsWith(OPTIMIZED_PACKAGE)) {
            setAttributes = (List<String>) this.getAttributes().get("javax.faces.component.UIComponentBase.attributesThatAreSet");
            if (setAttributes == null) {
                setAttributes = new ArrayList<String>(6);
                this.getAttributes().put("javax.faces.component.UIComponentBase.attributesThatAreSet", setAttributes);
            }
            if (value == null) {
                setAttributes.remove(name);
            } else if (!setAttributes.contains(name)) {
                setAttributes.add(name);
            }
        }
    }
}
