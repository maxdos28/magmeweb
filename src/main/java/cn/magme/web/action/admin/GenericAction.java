package cn.magme.web.action.admin;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Preparable;
/**
 * 可删除，在项目中无用
 * @author guozheng
 * @date 2011-6-3
 * @version $id$
 */
public abstract class GenericAction<T, PK extends Serializable> extends
		ActionSupport implements Preparable, ModelDriven<T> {

	private static final long serialVersionUID = -5102533743112353875L;
	protected T entity;
	protected String entityClassName = "";

	protected PK id;

	protected Object service;

	public String add() {
		if(service == null){
			System.out.println("service is null");
			Object bean = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext()).getBean("admin");
			if(bean == null){
				System.out.println("bean is null");
			} else {
				System.out.println(bean);
			}
		}
		try {
			System.out.println(service.getClass());
			Method add = service.getClass().getMethod("add",Object.class);
			add.invoke(service, entity);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return "success";
	}

	public String delete() {
		return "list";
	}

	public String list() {
		return "list";
	}

	public String detail() {
		return "";
	}

	public String edit() {
		return "";
	}

	public abstract String search();

	@SuppressWarnings("unchecked")
	public T instanceAnnotationObject() throws Exception {
		Class<?> cl = this.getClass();
		Type[] types = ((ParameterizedType) cl.getGenericSuperclass())
				.getActualTypeArguments();
		try {
			return ((Class<T>) types[0]).newInstance();
		} catch (Exception e) {
			throw e;
		}
	}

	@SuppressWarnings("unchecked")
	public void prepare() throws Exception {
		this.entity = this.instanceAnnotationObject();
		this.entityClassName = this.entity.getClass().getSimpleName();
		/*String id = this.request.getParameter("id");

		if (id != null) {
			this.id = (PK) Long.valueOf(id);
		}*/

	}

	public T getModel() {
		return entity;
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}

	public String getEntityClassName() {
		return entityClassName;
	}

	public void setEntityClassName(String entityClassName) {
		this.entityClassName = entityClassName;
	}

	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

}
