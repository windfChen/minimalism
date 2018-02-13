package com.windf.module.development.modle.file.java;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.CollectionUtils;

import com.windf.core.util.CollectionUtil;

public abstract class AbstractType {
	
	protected Comment comment;
	protected List<Annotation> annotations;
	
	/**
	 * 设置注释
	 * @param comment
	 */
	public void setComment(Comment comment) {
		this.comment = comment;
	}

	/**
	 *  get comment codes
	 * @return
	 */
	public List<String> getComment() {
		List<String> result = new ArrayList<String>(); 
		if (comment != null) {
			result.addAll(comment.write());
		}
		return result;
	}

	public Comment getComments() {
		return this.comment;
	}
	
	/**
	 * setAnnotation
	 * @param annotation
	 */
	void setAnnotations(List<String> annotationsCodes) {
		List<Annotation> annotations = null;
		
		if (CollectionUtil.isNotEmpty(annotationsCodes)) {
			 annotations = new ArrayList<Annotation>();
			 
			 for (String codes : annotationsCodes) {
				 Annotation annotation = new Annotation(codes);
				 annotations.add(annotation);
			}
		}
		
		this.annotations = annotations;
	}

	/**
	 * get annotation codes
	 * @param tabCount 
	 * @return
	 */
	public List<String> getAnnotationsString(int tabCount) {
		List<String> result = new ArrayList<String>(); 
		if (!CollectionUtils.isEmpty(annotations)) {
			 for (Annotation annotation : annotations) {
				 result.add(annotation.write(tabCount));
			}
		}
		return result;
	}
	
	public List<Annotation> getAnnotations() {
		return this.annotations;
	}
	
	public Annotation getAnnotationByName(String annotationName) {
		Annotation result = null;
		
		if (this.annotations != null) {
			for (Annotation annotation : annotations) {
				if (annotation.getName().equals(annotationName)) {
					result = annotation;
					break;
				}
			}
		}
		
		return result;
	}
	
	public void addAnnotation(Annotation annotation) {
		if (annotations == null) {
			annotations = new ArrayList<Annotation>();
		}
		annotations.add(annotation);
	}
	
	public void setAnnotation(Annotation annotation) {
		annotations = new ArrayList<Annotation>();
		addAnnotation(annotation);
	}

	abstract List<String> write();
	
}
